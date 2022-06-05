/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.items.wands;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.HashSet;

public class WandOfShatteredFireblast extends DamageWand {

	{
		image = ItemSpriteSheet.SHATTERED_FIRE;

		collisionProperties = Ballistica.STOP_TERRAIN;
	}

	//1x/1.5x/2.25x damage
	public int min(int lvl){
		return (int)Math.round((1+lvl) * Math.pow(1.5f, chargesPerCast()-1));
	}

	//1x/1.5x/2.25x damage
	public int max(int lvl){
		return (int)Math.round((5+3*lvl) * Math.pow(1.5f, chargesPerCast()-1));
	}

	//the actual affected cells
	private HashSet<Integer> affectedCells;
	//the cells to trace fire shots to, for visual effects.
	private HashSet<Integer> visualCells;
	private int direction = 0;

	@Override
	protected void onZap( Ballistica bolt ) {

		for( int cell : affectedCells){

			//ignore caster cell
			if (cell == bolt.sourcePos){
				continue;
			}

			//only ignite cells directly near caster if they are flammable
			if (!Dungeon.level.adjacent(bolt.sourcePos, cell)
					|| Level.flamable[cell]){
				GameScene.add( Blob.seed( cell, 1+chargesPerCast(), Fire.class ) );
			}

			Char ch = Actor.findChar( cell );
			if (ch != null) {

				processSoulMark(ch, chargesPerCast());
				ch.damage(damageRoll(), this);
				Buff.affect( ch, Burning.class ).set(5f);
				switch(chargesPerCast()){
					case 1:
						break; //no effects
					case 2:
						Buff.affect(ch, Cripple.class, 4f); break;
					case 3:
						Buff.affect(ch, Paralysis.class, 4f); break;
				}
			}
		}
	}

	//burn... BURNNNNN!.....
	private void spreadFlames(int cell, float strength){
		if (strength >= 0 && (Level.passable[cell] || Level.flamable[cell])){
			affectedCells.add(cell);
			if (strength >= 1.5f) {
				visualCells.remove(cell);
				spreadFlames(cell + PathFinder.NEIGHBOURS8[left(direction)], strength - 1.5f);
				spreadFlames(cell + PathFinder.NEIGHBOURS8[direction], strength - 1.5f);
				spreadFlames(cell + PathFinder.NEIGHBOURS8[right(direction)], strength - 1.5f);
			} else {
				visualCells.add(cell);
			}
		} else if (!Level.passable[cell])
			visualCells.add(cell);
	}

	private int left(int direction){
		return direction == 0 ? 7 : direction-1;
	}

	private int right(int direction){
		return direction == 7 ? 0 : direction+1;
	}

	@Override
	protected void fx( Ballistica bolt, Callback callback ) {
		//need to perform flame spread logic here so we can determine what cells to put flames in.
		affectedCells = new HashSet<>();
		visualCells = new HashSet<>();

		// 4/6/9 distance
		int maxDist = (int)(4 * Math.pow(1.5,(chargesPerCast()-1)));
		int dist = Math.min(bolt.dist, maxDist);

		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++){
			if (bolt.sourcePos+PathFinder.NEIGHBOURS8[i] == bolt.path.get(1)){
				direction = i;
				break;
			}
		}

		float strength = maxDist;
		for (int c : bolt.subPath(1, dist)) {
			strength--; //as we start at dist 1, not 0.
			affectedCells.add(c);
			if (strength > 1) {
				spreadFlames(c + PathFinder.NEIGHBOURS8[left(direction)], strength - 1);
				spreadFlames(c + PathFinder.NEIGHBOURS8[direction], strength - 1);
				spreadFlames(c + PathFinder.NEIGHBOURS8[right(direction)], strength - 1);
			} else {
				visualCells.add(c);
			}
		}

		//going to call this one manually
		visualCells.remove(bolt.path.get(dist));

		for (int cell : visualCells){
			//this way we only get the cells at the tip, much better performance.
			MagicMissile.shatteredfire(curUser.sprite.parent, bolt.sourcePos, cell, null);
		}
		MagicMissile.shatteredfire( curUser.sprite.parent, bolt.sourcePos, bolt.path.get(dist), callback );
		Sample.INSTANCE.play( Assets.SND_ZAP );
	}

	@Override
	protected int chargesPerCast() {
		//consumes 30% of current charges, rounded up, with a minimum of one.
		return Math.max(1, (int)Math.ceil(curCharges*0.3f));
	}

	@Override
	public String statsDesc() {
		if (levelKnown)
			return Messages.get(this, "stats_desc", chargesPerCast(), min(), max());
		else
			return Messages.get(this, "stats_desc", chargesPerCast(), min(0), max(0));
	}


}

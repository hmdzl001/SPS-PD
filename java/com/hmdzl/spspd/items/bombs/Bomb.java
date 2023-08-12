/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.items.bombs;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.BlastParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class Bomb extends Item {

	{
		//name = "bomb";
		image = ItemSpriteSheet.NULLWARN;
		
		defaultAction = AC_LIGHTTHROW;
		usesTargeting = true;
		
		stackable = true;
	}

	public Fuse fuse;

	// FIXME using a static variable for this is kinda gross, should be a better
	// way
	private static boolean lightingFuse = false;

	private static final String AC_LIGHTTHROW = "LIGHTTHROW";
	

	@Override
	public boolean isSimilar(Item item) {
		return super.isSimilar(item) && this.fuse == ((Bomb) item).fuse;
	}
	
	public boolean explodesDestructively(){
		return true;
	}	

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_LIGHTTHROW);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_LIGHTTHROW)) {
			lightingFuse = true;
			action = AC_THROW;
		} else {
			lightingFuse = false;
		}

		super.execute(hero, action);
	}

	@Override
	protected void onThrow( int cell ) {
		if (!Level.pit[ cell ] && lightingFuse) {
			Actor.addDelayed(fuse = new Fuse().ignite(this), 2);
		}
		if (Actor.findChar( cell ) != null && !(Actor.findChar( cell ) instanceof Hero) ){
			ArrayList<Integer> candidates = new ArrayList<>();
			//for (int i : PathFinder.NEIGHBOURS8)
				//if (Dungeon.level.passable[cell + i])
					//candidates.add(cell + i);
			//int newCell = candidates.isEmpty() ? cell : Random.element(candidates);
			int newCell = cell;
			Dungeon.level.drop( this, newCell ).sprite.drop( cell );
		} else
			super.onThrow( cell );
	}

	@Override
	public boolean doPickUp(Hero hero) {
		if (fuse != null) {
			GLog.w( Messages.get(this, "snuff_fuse"));
			fuse = null;
		}
		return super.doPickUp(hero);
	}

	public void explode(int cell) {
		this.fuse = null;

		Sample.INSTANCE.play(Assets.SND_BLAST);

		if (Dungeon.visible[cell]) {
			CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
		}

	//	boolean terrainAffected = false;
	//	for (int n : Level.NEIGHBOURS9) {
		//	int c = cell + n;
		//	if (c >= 0 && c < Level.getLength()) {
			//	if (Dungeon.visible[c]) {
			//		CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
			//	}

			//	if (Level.flamable[c]) {
			//		Level.set(c, Terrain.EMBERS);
			//		GameScene.updateMap(c);
			//		terrainAffected = true;
			//	}

			//	Heap heap = Dungeon.level.heaps.get(c);
			//	if (heap != null) heap.explode();
									
				
			//	Char ch = Actor.findChar(c);
			//	if (ch != null) {
					// those not at the center of the blast take damage less
					// consistently.
				//	int minDamage = c == cell ? Dungeon.depth + 5 : 1;
				//	int maxDamage = 10 + Dungeon.depth * 2;

				//	int dmg = Random.NormalIntRange(minDamage, maxDamage)
				//			- Math.max(ch.drRoll(),0);
					//if (dmg > 0) {
				//		ch.damage(dmg, this);
				//	}

				//	if (ch == Dungeon.hero && !ch.isAlive())
						// constant is used here in the rare instance a player
						// is killed by a double bomb.
						//Dungeon.fail(ResultDescriptions.ITEM);
				//}
			//}
		//}

		//if (terrainAffected) {
			//Dungeon.observe();
	//	}
		
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return fuse != null ? new ItemSprite.Glowing(0xFF0000, 0.6f) : null;
	}

	@Override
	public int price() {
		return 20 * quantity;
	}

	@Override
	public String info() {
		if (fuse == null)
			return super.desc();
		else
			return Messages.get(this, "desc_burning");
	}

	private static final String FUSE = "fuse";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(FUSE, fuse);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(FUSE))
			Actor.add(fuse = ((Fuse) bundle.get(FUSE)).ignite(this));
	}

	public static class Fuse extends Actor {

		private Bomb bomb;

		public Fuse ignite(Bomb bomb) {
			this.bomb = bomb;
			return this;
		}

		@Override
		protected boolean act() {

			// something caused our bomb to explode early, or be defused. Do
			// nothing.
			if (bomb.fuse != this) {
				Actor.remove(this);
				return true;
			}

			// look for our bomb, remove it from its heap, and blow it up.
			for (Heap heap : Dungeon.level.heaps.values()) {
				if (heap.items.contains(bomb)) {
					heap.items.remove(bomb);

					if (heap.items.isEmpty()){
						heap.destroy();
					}
					
					bomb.explode(heap.pos);

					Actor.remove(this);
					return true;
				}
			}

			// can't find our bomb, this should never happen, throw an
			// exception.
			bomb.fuse = null;
			Actor.remove( this );
			return true;
		}
	}
}

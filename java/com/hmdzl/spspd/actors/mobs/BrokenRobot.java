/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.TarGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.BlastParticle;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.artifacts.RobotDMT;
import com.hmdzl.spspd.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BrokenRobotSprite;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.LIGHT_DAMAGE;

public class BrokenRobot extends Mob {

	private static final String TXT_DEATHGAZE_KILLED = "%s's deathray killed you...";
	private static final float SPAWN_DELAY = 2f;

	{
		spriteClass = BrokenRobotSprite.class;

		HP = HT = 120+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 20+adj(1);
		viewDistance = Light.DISTANCE;

		EXP = 13;
		maxLvl = 25;

		
		lootOther = new StoneOre();
		lootChanceOther = 0.25f;
		
		loot = new ScrollOfRecharging();
		lootChance = 0.25f; // by default, see die()
		
		properties.add(Property.MAGICER);
		properties.add(Property.MECH);
	}

	private Ballistica beam;

	@Override
	public Item SupercreateLoot(){
		return new RobotDMT();
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}

	@Override
	public boolean act() {
        GameScene.add(Blob.seed(pos,30, TarGas.class));
		if(enemySeen){
		  switch (Random.Int(50)) {
		  case 1:
			GLog.n(Messages.get(this,"explode") );
			explode(pos);
			if (HP<1){destroy();}
		  break;
		 }
		}

		return super.act();
	}



	@Override
	protected boolean canAttack(Char enemy) {

		beam = new Ballistica( pos, enemy.pos, Ballistica.STOP_TERRAIN);

		return beam.subPath(1, beam.dist).contains(enemy.pos);
	}

	@Override
	public int hitSkill(Char target) {
		return 20+adj(0);
	}

	@Override
	protected float attackDelay() {
		return 3f;
	}
	
	@Override
	protected boolean doAttack(Char enemy) {

		spend(attackDelay());

		boolean rayVisible = false;

		for (int i : beam.subPath(0, beam.dist)) {
			if (Dungeon.visible[i]) {
				rayVisible = true;
			}
		}

		if (rayVisible) {
			sprite.attack(beam.collisionPos);
			return false;
		} else {
			attack(enemy);
			return true;
		}
	}

	@Override
	public boolean attack(Char enemy) {

		for (int pos : beam.subPath(1, beam.dist)) {

			Char ch = Actor.findChar( pos );
			if (ch == null) {
				continue;
			}

			if (hit(this, ch, true)) {
				ch.damage(Random.NormalIntRange(2, 8+adj(0)), LIGHT_DAMAGE);

				if (Dungeon.visible[pos]) {
					ch.sprite.flash();
					CellEmitter.center(pos).burst(PurpleParticle.BURST,
							Random.IntRange(1, 2));
				}

				if (!ch.isAlive() && ch == Dungeon.hero) {
					Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
					//GLog.n(Messages.get(this, "kill"));
				}
			} else {
				ch.sprite.showStatus(CharSprite.NEUTRAL, ch.defenseVerb());
			}
		}

		return true;
	}

	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}
	
	public static BrokenRobot spawnAt(int pos) {
		
		BrokenRobot b = new BrokenRobot();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
	
	
	public void explode(int cell) {
		// We're blowing up, so no need for a fuse anymore.
	
		Sample.INSTANCE.play(Assets.SND_BLAST, 2);

		if (Dungeon.visible[cell]) {
			CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
		}

		boolean terrainAffected = false;
		for (int n : Level.NEIGHBOURS9) {
			int c = cell + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}

				if (Level.flamable[c]) {
					Level.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
					terrainAffected = true;
				}

				// destroys items / triggers bombs caught in the blast.
				Heap heap = Dungeon.level.heaps.get(c);
				if (heap != null)
					heap.explode();

				Char ch = Actor.findChar(c);
				if (ch != null) {
					// those not at the center of the blast take damage less
					// consistently.
					int minDamage = c == cell ? Dungeon.depth + 5 : 1;
					int maxDamage = 10 + Dungeon.depth * 2;

					int dmg = Random.NormalIntRange(minDamage, maxDamage)
							- Math.max(ch.drRoll(),0);
					if (dmg > 0) {
						ch.damage(dmg, this);
					}
 
					if (ch == this && HP<1){	
						die(this);
					}
				}
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}
	}
	
	{
		
		immunities.add(Terror.class);
		immunities.add(ToxicGas.class);
	}

}

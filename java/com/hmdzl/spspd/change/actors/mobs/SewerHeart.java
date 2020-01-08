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
package com.hmdzl.spspd.change.actors.mobs;


import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Badges.Badge;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.GooWarn;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.GrowSeed;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.effects.particles.PurpleParticle;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.eggs.EasterEgg;
import com.hmdzl.spspd.change.items.eggs.Egg;
import com.hmdzl.spspd.change.items.journalpages.Sokoban1;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.SewerBossLevel;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.plants.Rotberry;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.GooSprite;
import com.hmdzl.spspd.change.sprites.SewerHeartSprite;
import com.hmdzl.spspd.change.sprites.SewerLasherSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class SewerHeart extends Mob {

	{
		spriteClass = SewerHeartSprite.class;

		HP = HT = 500;
		evadeSkill = 0;

		EXP = 30;

		loot = new Rotberry.Seed();
		lootChance = 0.2f;
		
		lootOther = Generator.Category.SEED;
		lootChanceOther = 1f;
		
		properties.add(Property.PLANT);
		properties.add(Property.BOSS);
	}

	private Ballistica beam;
	private int beamTarget = -1;
	private int breaks=0;
	private int beamCooldown=0;
	public boolean beamCharged;

	@Override
	public void notice() {
		super.notice();
		//yell("GLURP-GLURP!");
		((SewerBossLevel) Dungeon.level).seal();
		if (!spawnedLasher){
			Buff.affect(hero, LasherSpawner.class);;
			spawnedLasher = true;
		}
		state = PASSIVE;
	}


	@Override
	public boolean act() {

		if( 5 - breaks > 6 * HP / HT ) {
			breaks++;
			return true;
		}

		if (breaks ==3 && state == PASSIVE){
			state = HUNTING;
		}

		if (beamCharged && state != HUNTING){
			beamCharged = false;
		}
		if (beam == null && beamTarget != -1) {
			beam = new Ballistica(pos, beamTarget, Ballistica.STOP_TERRAIN);
			sprite.turnTo(pos, beamTarget);
		}
		if (beamCooldown > 0)
			beamCooldown--;
		return super.act();
	}

	@Override
	public void damage(int dmg, Object src) {
		if( (5 - breaks) > 6 * HP / HT ) {
			int newPos = -1;
					for (int i = 0; i < 20; i++) {
					newPos = Dungeon.level.randomRespawnCellMob();
					if (newPos != -1) {
						break;
					}
				}
				if (newPos != -1){
					Actor.freeCell(pos);
					CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
					pos = newPos;
					sprite.place(pos);
					sprite.visible = Dungeon.visible[pos];
					GLog.n(Messages.get(this, "blink"));
				}		
				if (Dungeon.level.mobs.size()< hero.lvl*2){
				SewerLasher.spawnAroundChance(newPos);
				}
			}
			
		super.damage(dmg, src);
		
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		GameScene.add(Blob.seed(pos, 20, ToxicGas.class));

		return super.defenseProc(enemy, damage);
	}

	@Override
	protected boolean getCloser(int target) {
		return false;
	}

	@Override
	public void destroy() {
		super.destroy();
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[Dungeon.level.mobs.size()])){
			if (mob instanceof SewerLasher){
				mob.die(null);
			}
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		if (beamCooldown == 0) {
			Ballistica aim = new Ballistica(pos, enemy.pos, Ballistica.STOP_TERRAIN);

			if (enemy.invisible == 0 && !isCharmedBy(enemy) && Level.fieldOfView[enemy.pos] && aim.subPath(1, aim.dist).contains(enemy.pos)){
				beam = aim;
				beamTarget = aim.collisionPos;
				return true;
			} else
				//if the beam is charged, it has to attack, will aim at previous location of target.
				return beamCharged;
		} else
			return super.canAttack(enemy);
	}

	@Override
	protected boolean doAttack(Char enemy) {
		if (beamCooldown > 0) {
			return super.doAttack(enemy);
		} else if (!beamCharged){
			((SewerHeartSprite)sprite).charge( enemy.pos );
			spend( attackDelay()*2f );
			beamCharged = true;
			return true;
		} else {

			spend( attackDelay() );

			beam = new Ballistica(pos, beamTarget, Ballistica.STOP_TERRAIN);
			if (Level.fieldOfView[pos] || Level.fieldOfView[beam.collisionPos] ) {
				sprite.zap( beam.collisionPos );
				return false;
			} else {
				deathGaze();
				return true;
			}
	}}

	public void deathGaze(){
		if (!beamCharged || beamCooldown > 0 || beam == null)
			return;

		beamCharged = false;
		beamCooldown = Random.IntRange(3, 6);

		boolean terrainAffected = false;

		for (int pos : beam.subPath(1, beam.dist)) {

			if (Dungeon.level.flamable[pos]) {

				Dungeon.level.destroy( pos );
				GameScene.updateMap( pos );
				terrainAffected = true;

			}

			Char ch = Actor.findChar( pos );
			if (ch == null) {
				continue;
			}

			if (hit( this, ch, true )) {
				ch.damage( Random.NormalIntRange( 20, 35 ), this );

				if (Level.fieldOfView[pos]) {
					ch.sprite.flash();
					CellEmitter.center( pos ).burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
				}

				if (!ch.isAlive() && ch == Dungeon.hero) {
					Dungeon.fail( Messages.format(ResultDescriptions.BURNING));
				}
			} else {
				ch.sprite.showStatus( CharSprite.NEUTRAL,  ch.defenseVerb() );
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}

		beam = null;
		beamTarget = -1;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		((SewerBossLevel) Dungeon.level).unseal();

		GameScene.bossSlain();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		Badges.validateBossSlain();

		Buff.detach(hero, LasherSpawner.class);
		
		Badges.Badge badgeToCheck = null;
		switch (hero.heroClass) {
				case WARRIOR:
					badgeToCheck = Badge.MASTERY_WARRIOR;
					break;
				case MAGE:
					badgeToCheck = Badge.MASTERY_MAGE;
					break;
				case ROGUE:
					badgeToCheck = Badge.MASTERY_ROGUE;
					break;
				case HUNTRESS:
					badgeToCheck = Badge.MASTERY_HUNTRESS;
					break;
			case PERFORMER:
				badgeToCheck = Badge.MASTERY_PERFORMER;
				break;
			case SOLDIER:
					badgeToCheck = Badge.MASTERY_SOLDIER;
					break;	
				case FOLLOWER:
					badgeToCheck = Badge.MASTERY_FOLLOWER;
					break;
					}		
		Dungeon.level.drop(new Sokoban1(), pos).sprite.drop();
	}

	@Override
	public int damageRoll() {
		int min = (HP*2 <= HT) ? 5 : 2;
		int max = (HP*2 <= HT) ? 16 : 8;
		return Random.NormalIntRange( min, max );

	}

	@Override
	public int hitSkill( Char target ) {
		return 30;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}
	
	protected boolean spawnedLasher = false;
	

	private static final String BEAM_TARGET     = "beamTarget";
	private static final String BEAM_COOLDOWN   = "beamCooldown";
	private static final String BEAM_CHARGED    = "beamCharged";    
	private static final String BREAKS	= "breaks";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( BEAM_TARGET, beamTarget);
		bundle.put( BEAM_COOLDOWN, beamCooldown );
		bundle.put( BEAM_CHARGED, beamCharged ); 
		bundle.put( BREAKS, breaks );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(BEAM_TARGET))
			beamTarget = bundle.getInt(BEAM_TARGET);
		beamCooldown = bundle.getInt(BEAM_COOLDOWN);
		beamCharged = bundle.getBoolean(BEAM_CHARGED);
		breaks = bundle.getInt( BREAKS );
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( ToxicGas.class );
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	public static class LasherSpawner extends Buff {

		int spawnPower = 0;

		@Override
		public boolean act() {
			spawnPower++;
			int lasher = 1; //we include the wraith we're trying to spawn
			for (Mob mob : Dungeon.level.mobs){
				if (mob instanceof SewerLasher){
					lasher++;
				}
			}

			int powerNeeded = Math.min(25, lasher);

			if (powerNeeded <= spawnPower){
				spawnPower -= powerNeeded;
				int pos = 0;
				do{
					pos = Random.Int(Dungeon.level.randomRespawnCellMob());
				} while (!Dungeon.level.passable[pos] || Actor.findChar( pos ) != null);
				SewerLasher.spawnAt(pos);
				Sample.INSTANCE.play(Assets.SND_BURNING);
			}

			spend(TICK);
			return true;
		}

		public void dispel(){
			detach();
			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])){
				if (mob instanceof SewerLasher){
					mob.die(null);
				}
			}
		}

		private static String SPAWNPOWER = "spawnpower";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( SPAWNPOWER, spawnPower );
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			spawnPower = bundle.getInt( SPAWNPOWER );
		}
	}
	public static class SewerLasher extends Mob {

		protected static final float SPAWN_DELAY = 2f;

		{
			spriteClass = SewerLasherSprite.class;

			HP = HT = 60;
			evadeSkill = 0;

			EXP = 1;

			loot = Generator.Category.SEED;
			lootChance = 0.2f;

			state = HUNTING;

			properties.add(Property.PLANT);
			properties.add(Property.MINIBOSS);
			//properties.add(Property.IMMOVABLE);
		}

		@Override
		protected boolean act() {
			if (enemy == null || !Level.adjacent(pos, enemy.pos)) {
				HP = Math.min(HT, HP + 3);
			}
			return super.act();
		}

		@Override
		public void damage(int dmg, Object src) {
			if (src instanceof Burning) {
				destroy();
				sprite.die();
			} else {

				super.damage(dmg, src);
			}
		}

		@Override
		public int attackProc( Char enemy, int damage) {
			damage = super.attackProc(enemy, damage);
			if (Random.Int(5) < 1) {
				Buff.affect(enemy, Cripple.class, 2f);
			} else
			if (Random.Int(4) < 1) {
				Buff.affect(enemy, GrowSeed.class).reignite(enemy);
			} else
			if (Random.Int(3) < 1) {
				Buff.affect(enemy, Bleeding.class).set(damage);}

			return super.attackProc(enemy, damage);

		}
		@Override
		protected boolean getCloser(int target) {
			return true;
		}

		@Override
		protected boolean getFurther(int target) {
			return true;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(4, 12);
		}

		@Override
		public int hitSkill( Char target ) {
			return 15;
		}

		@Override
		public int drRoll() {
			return Random.NormalIntRange(2, 8);
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
		static {
			IMMUNITIES.add( ToxicGas.class );
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}

		public static void spawnAround(int pos) {
			for (int n : Level.NEIGHBOURS8) {
				int cell = pos + n;
				if (Level.passable[cell] && Actor.findChar(cell) == null) {
					spawnAt(cell);
				}
			}
		}

		public static void spawnAroundChance(int pos) {
			for (int n : Level.NEIGHBOURS4) {
				int cell = pos + n;
				if (Level.passable[cell] && Actor.findChar(cell) == null && Random.Float() < 0.75f) {
					spawnAt(cell);
				}
			}
		}

		public static SewerLasher spawnAt(int pos) {

			SewerLasher b = new SewerLasher();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}


	}

}

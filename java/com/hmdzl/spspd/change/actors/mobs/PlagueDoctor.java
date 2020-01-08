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
import com.hmdzl.spspd.change.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.change.actors.blobs.DarkGas;
import com.hmdzl.spspd.change.actors.blobs.GooWarn;
import com.hmdzl.spspd.change.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.AttackDown;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Speed;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.effects.particles.PurpleParticle;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.artifacts.AlchemistsToolkit;
import com.hmdzl.spspd.change.items.eggs.EasterEgg;
import com.hmdzl.spspd.change.items.eggs.Egg;
import com.hmdzl.spspd.change.items.journalpages.Sokoban1;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.change.items.wands.WandOfLight;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.SewerBossLevel;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.GooSprite;
import com.hmdzl.spspd.change.sprites.PlagueDoctorSprite;
import com.hmdzl.spspd.change.sprites.SewerHeartSprite;
import com.hmdzl.spspd.change.sprites.ShadowRatSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class PlagueDoctor extends Mob {

	{
		spriteClass = PlagueDoctorSprite.class;

		HP = HT = 500;
		evadeSkill = 5;
		baseSpeed = 0.75f;

		EXP = 30;

		loot = new AlchemistsToolkit();
		lootChance = 0.2f;

		lootOther = Generator.Category.POTION;
		lootChanceOther = 1f;
		
		FLEEING = new Fleeing();

		properties.add(Property.HUMAN);
		properties.add(Property.BOSS);
	}

	private int breaks=0;

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
		((SewerBossLevel) Dungeon.level).seal();
		if (!spawnedshadow) {
			Buff.affect(hero, ShadowRatSummon.class);
			spawnedshadow = true;
		}
		//state = PASSIVE;
	}


	@Override
	public boolean act() {

		if (3 - breaks > 4 * HP / HT) {
			breaks++;
			if (breaks > 1){
				GLog.i(Messages.get(this, "crazy"));
				yell(Messages.get(this, "yell2"));
			}
			return true;
		}

	    if (breaks == 1){
		    state = FLEEING;
			GameScene.add(Blob.seed(pos, 20, ToxicGas.class));
		}
		
		if (breaks == 2){
			state = HUNTING;
		}		
		
		return super.act();
	}

	@Override
	public int attackProc(Char enemy, int damage) {
	
		if (breaks == 0){
			if (Random.Int(2) == 0) {
				switch (Random.Int (4)) {
					case 0:
						enemy.HP += (int)((enemy.HT)/10);
						enemy.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,1);
						break;
					case 1:
						Buff.affect(enemy, AttackUp.class, 5f).level(20);
						break;
					case 2:
						Buff.affect(enemy, DefenceUp.class, 5f).level(20);
						break;
					case 3:
						Buff.affect(enemy, Speed.class, 5f);
						break;
					default:
						break;
				}
			}
				damage = 0 ;
			if (Random.Int(3) == 0) {
			yell(Messages.get(this, "yell"));
			}
		}
	
		
		if (breaks == 2){
			if (Random.Int(2) == 0) {
				Buff.affect(enemy, Bleeding.class).set(5);
		    }
		}
		
		if (breaks == 3){
			Buff.affect(this,AttackUp.class,5f).level(50);
			Buff.affect(this,DefenceUp.class,5f).level(25);
		}

		return damage;
	}		
	
	@Override
	public int defenseProc(Char enemy, int damage) {
		if (breaks == 1 && Random.Int(2) == 0){
			switch (Random.Int (4)) {
				case 0:
					GameScene.add(Blob.seed(pos, 25, ToxicGas.class));
					break;
				case 1:
					GameScene.add(Blob.seed(pos, 25, ConfusionGas.class));
					break;
				case 2:
					GameScene.add(Blob.seed(pos, 25, ParalyticGas.class));
					break;
				case 3:
					GameScene.add(Blob.seed(pos, 25, DarkGas.class));
					break;
				default:
					break;
			}
		}
		return super.defenseProc(enemy, damage);
	}
	
	@Override
	public void damage(int dmg, Object src) {
		super.damage(dmg, src);
	}

	@Override
	public void destroy() {
		super.destroy();
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[Dungeon.level.mobs.size()])) {
			if (mob instanceof ShadowRat) {
				mob.die(null);
			}
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return super.canAttack(enemy);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		((SewerBossLevel) Dungeon.level).unseal();

		GameScene.bossSlain();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		Badges.validateBossSlain();

		Buff.detach(hero, ShadowRatSummon.class);

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
		int min = 6;
		int max = 19;
		return Random.NormalIntRange(min, max);

	}

	@Override
	public int hitSkill(Char target) {
		return 30;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add(ToxicGas.class );
		IMMUNITIES.add(ParalyticGas.class);
		IMMUNITIES.add(DarkGas.class);
		IMMUNITIES.add(ConfusionGas.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

    private static final String BREAKS	= "breaks";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( BREAKS, breaks );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        breaks = bundle.getInt( BREAKS );
    }

	protected boolean spawnedshadow = false;

	public static class ShadowRatSummon extends Buff {

		int spawnPower = 0;

		@Override
		public boolean act() {
			spawnPower++;
			int srat = 1; //we include the wraith we're trying to spawn
			for (Mob mob : Dungeon.level.mobs) {
				if (mob instanceof ShadowRat) {
					srat++;
				}
			}

			int powerNeeded = Math.min(10, srat);

			if (powerNeeded <= spawnPower) {
				spawnPower -= powerNeeded;
				int pos = 0;
				do {
					pos = Random.Int(Dungeon.level.randomRespawnCellMob());
				} while (!Dungeon.level.passable[pos] || Actor.findChar(pos) != null);
				ShadowRat.spawnAt(pos);
				Sample.INSTANCE.play(Assets.SND_BURNING);
			}

			spend(TICK);
			return true;
		}

		public void dispel() {
			detach();
			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
				if (mob instanceof ShadowRat) {
					mob.die(null);
				}
			}
		}

		private static String SPAWNPOWER = "spawnpower";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(SPAWNPOWER, spawnPower);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			spawnPower = bundle.getInt(SPAWNPOWER);
		}
	}

	public static class ShadowRat extends Mob {


		private static final float SPAWN_DELAY = 2f;

		{
			spriteClass = ShadowRatSprite.class;

			HP = HT = 60;
			evadeSkill = 3;
            EXP = 1;
			
			loot = new StoneOre();
			lootChance = 0.2f;

			properties.add(Property.ELEMENT);
			properties.add(Property.MINIBOSS);
		}


		@Override
		public void damage(int dmg, Object src) {
			if (src instanceof WandOfLight) {
				destroy();
				sprite.die();
			} else {

				super.damage(dmg, src);
			}
		}

		@Override
		public int attackProc( Char enemy, int damage) {
			damage = super.attackProc(enemy, damage);
			if (Random.Int(3) < 1) {
				Buff.prolong(enemy, AttackDown.class, 10f).level(25);
			} else
			if (Random.Int(3) < 1) {
				Buff.prolong(enemy, Vertigo.class, 5f);
			}
			return super.attackProc(enemy, damage);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(6, 9);
		}

		@Override
		public int hitSkill(Char target) {
			return 25;
		}

		@Override
		public int drRoll() {
			return 0;
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
		static {
			IMMUNITIES.add( ToxicGas.class );
			IMMUNITIES.add(Poison.class);
			IMMUNITIES.add(Burning.class);
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

		public static ShadowRat spawnAt(int pos) {

			ShadowRat b = new ShadowRat();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}
	}

}
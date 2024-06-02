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
package com.hmdzl.spspd.actors.mobs;


import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Badges.Badge;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.DarkGas;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.SpeedUp;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.mobs.npcs.RatKing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.artifacts.AlchemistsToolkit;
import com.hmdzl.spspd.items.journalpages.Sokoban1;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.misc.PotionOfMage;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.items.weapon.rockcode.Dpotion;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.PlagueDoctorSprite;
import com.hmdzl.spspd.sprites.ShadowRatSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

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
		Dungeon.depth.seal();
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
						enemy.HP += (enemy.HT)/10;
						enemy.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,1);
						break;
					case 1:
						Buff.affect(enemy, AttackUp.class, 5f).level(20);
						break;
					case 2:
						Buff.affect(enemy, DefenceUp.class, 5f).level(20);
						break;
					case 3:
						Buff.affect(enemy, SpeedUp.class, 5f);
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
		for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[Dungeon.depth.mobs.size()])) {
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
		Dungeon.depth.unseal();

		GameScene.bossSlain();
		Dungeon.depth.drop(new SkeletonKey(Dungeon.dungeondepth), pos).sprite.drop();
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
			case ASCETIC:
					badgeToCheck = Badge.MASTERY_ASCETIC;
					break;					
		}
		Dungeon.depth.drop(new Sokoban1(), pos).sprite.drop();
		Dungeon.depth.drop(new Gold(1500), pos).sprite.drop();

		ArrayList<Mob> mobs = new ArrayList<>();

		Mob mob = new RatKing();
		mob.state = mob.WANDERING;
		mob.pos = pos;
		GameScene.add( mob, 1f );
		mobs.add( mob );
		ScrollOfTeleportation.appear(mob, mob.pos);
		mob.yell(Messages.get(this,"doctor"));

		if (Dungeon.hero.heroClass == HeroClass.PERFORMER && Hero.skins == 7)
			Dungeon.depth.drop(new Dpotion(), Dungeon.hero.pos).sprite.drop();
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
	
	@Override
	public Item SupercreateLoot(){
			return new PotionOfMage().identify().dounique();
	}	

	{
		immunities.add(ToxicGas.class );
		immunities.add(ParalyticGas.class);
		immunities.add(DarkGas.class);
		immunities.add(ConfusionGas.class);
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

		int shadowRat = 0;

		@Override
		public boolean act() {
			shadowRat++;
			int srat = 1; //we include the wraith we're trying to spawn
			for (Mob mob : Dungeon.depth.mobs) {
				if (mob instanceof ShadowRat) {
					srat++;
				}
			}

			int powerNeeded = Math.min(10, srat);

			if (powerNeeded <= shadowRat) {
				shadowRat -= powerNeeded;
				int pos = 0;
				do {
					pos = Random.Int(Dungeon.depth.randomRespawnCellMob());
				} while (!Floor.passable[pos] || Actor.findChar(pos) != null);
				ShadowRat.spawnAt(pos);
				Sample.INSTANCE.play(Assets.SND_BURNING);
			}

			spend(TICK);
			return true;
		}

		public void dispel() {
			detach();
			for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
				if (mob instanceof ShadowRat) {
					mob.die(null);
				}
			}
		}

		private static String SHADOWRAT = "shadowrat";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(SHADOWRAT, shadowRat);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			shadowRat = bundle.getInt(SHADOWRAT);
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

			properties.add(Property.UNKNOW);
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

		{
			immunities.add( ToxicGas.class );
			immunities.add(Poison.class);
			immunities.add(Burning.class);
		}


		public static void spawnAround(int pos) {
			for (int n : Floor.NEIGHBOURS8) {
				int cell = pos + n;
				if (Floor.passable[cell] && Actor.findChar(cell) == null) {
					spawnAt(cell);
				}
			}
		}

		public static void spawnAroundChance(int pos) {
			for (int n : Floor.NEIGHBOURS4) {
				int cell = pos + n;
				if (Floor.passable[cell] && Actor.findChar(cell) == null && Random.Float() < 0.75f) {
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
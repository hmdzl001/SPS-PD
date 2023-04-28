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
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.ArmorKit;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.artifacts.GlassTotem;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.journalpages.Sokoban4;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.misc.DanceLion;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.items.weapon.rockcode.Lbox;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BatteryTombSprite;
import com.hmdzl.spspd.sprites.LichDancerSprite;
import com.hmdzl.spspd.sprites.SeekingBombSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;

public class LichDancer extends Mob {

	private static final int MAX_ARMY_SIZE = 4;


	{
		spriteClass = LichDancerSprite.class;

		HP = HT = 1000;
		EXP = 60;
		evadeSkill = 25; 
		baseSpeed = 1f;
		
		loot = new GlassTotem().identify();
		lootChance = 0.2f;
		
		lootOther = Generator.Category.MUSICWEAPON;
		lootChanceOther= 1f;

		properties.add(Property.UNDEAD);
		properties.add(Property.MAGICER);
		properties.add(Property.BOSS);

	}

	private int breaks=0;
	
	public void spawnTomb() {
		BatteryTomb a = new BatteryTomb();
		a.pos = Terrain.PEDESTAL;
			do {
				a.pos = Random.Int(Dungeon.level.randomRespawnCellMob());
			} while (Dungeon.level.map[a.pos] != Terrain.PEDESTAL
					|| Actor.findChar(a.pos) != null);
			GameScene.add(a);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 38);
	}

	@Override
	public int hitSkill(Char target) {
		return 65;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 20);
	}

	@Override
	protected boolean act() {
		
        if( 3 - breaks > 4 * HP / HT ) {
			breaks++;
			jump();
            return true;
        }

		 if (Random.Int(5) == 0 && breaks > 0){
			ArrayList<Integer> spawnPoints = new ArrayList<>();

			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = enemy.pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
					spawnPoints.add( p );
				}
			}

			if (spawnPoints.size() > 0) {
				Mob	m = new LinkBomb();
				if (m != null) {
					GameScene.add(m);
					ScrollOfTeleportation.appear(m, Random.element(spawnPoints));
				}
			}
		}

		if (HP < HT) {
			//sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP = HP + 3;
		}
		
		return super.act();
	}

	private void jump() {
		int newPos;
		do {
			newPos = Random.Int(Level.getLength());
		} while (Dungeon.level.map[newPos] != Terrain.WELL && Dungeon.level.map[newPos] != Terrain.STATUE_SP);
		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}
		spawnTomb();
		spend(1 / speed());
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.prolong(enemy, Vertigo.class,3f);
		}
		
		enemy.damage(damageRoll()/2, ENERGY_DAMAGE);
		damage = damage/2;

        return damage;
	}

	@Override
	public Item SupercreateLoot(){
			return new DanceLion().identify().dounique();
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

	@Override
	public void die(Object cause) {

		 Dungeon.level.drop(new Sokoban4(), pos).sprite.drop();
		 
		 GameScene.bossSlain();
		Dungeon.level.unseal();

		Dungeon.level.drop(new ArmorKit(), pos).sprite.drop();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		Dungeon.level.drop(new Gold(Random.Int(1000, 2000)), pos).sprite.drop();

		if (Dungeon.hero.heroClass == HeroClass.PERFORMER && Dungeon.skins == 7)
			Dungeon.level.drop(new Lbox(), Dungeon.hero.pos).sprite.drop();

		Badges.validateBossSlain();
		//summonLiches(findTomb);
		//GLog.n(Messages.get(this, "liches"));

		super.die(cause);

	}

	private int maxArmySize() {
		return 2;
	}
	
    public boolean checkBattery() {

        int batteryAlive = 0;
        if (Dungeon.level.mobs != null) {
            for (Mob mob : Dungeon.level.mobs) {
                if (mob instanceof BatteryTomb) {
                    batteryAlive++;
                }
            }
        }
        return batteryAlive > 0;
    }	
	
	@Override
	public void damage(int dmg, Object src) {
		if (checkBattery()) {
			dmg = Random.Int(10);
			//Buff.affect(this,ShieldArmor.class).level(100);
		}
		super.damage(dmg, src);
        }	
	
	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,"notice"));
	}

	{
		resistances.add(ToxicGas.class);
		resistances.add(EnchantmentDark.class);
		
		resistances.add(WandOfDisintegration.class);

		immunities.add(Paralysis.class);
		immunities.add(Vertigo.class);
	}

	
    public static class BatteryTomb extends Mob  {

	{
		spriteClass = BatteryTombSprite.class;

		HP = HT = 200;
		evadeSkill = 0;

		EXP = 10;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new StoneOre();
		lootChance = 0.05f;

		properties.add(Property.MECH);
		properties.add(Property.MINIBOSS);
	}
	
	@Override
    public boolean act() {

        if( Random.Int(20) == 0 && Dungeon.level.mobs.size()< 6) {
			ManySkeleton.spawnAround(pos);
        } 
        return super.act();
    }		
	
	@Override
	public void beckon(int cell) {
		// Do nothing
	}
	
	@Override
	public void add(Buff buff) {
	}
	
	
	@Override
	public int damageRoll() {
		return 0;
	}
	
	@Override
	public int hitSkill(Char target) {
		return 0;
	}

	@Override
	public int drRoll() {
		return 0;
		
	}

	@Override
	public void damage(int dmg, Object src) {
	    if ( dmg > 50 ) dmg = 50;
		super.damage(dmg, src);

	}
}
    public static class LinkBomb extends Mob {
        {
            spriteClass = SeekingBombSprite.class;

            HP = HT = 1;
            evadeSkill = 0;
            baseSpeed = 1f;
            EXP = 0;

            state = PASSIVE;

            properties.add(Property.MECH);
            properties.add(Property.MINIBOSS);
        }

		private int bombtime=3;
		private static final String BOMBTIME	= "bombtime";

		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle(bundle);
			bundle.put( BOMBTIME, bombtime );
		}

		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle(bundle);
			bombtime = bundle.getInt( BOMBTIME );
		}

		@Override
        public int drRoll() {
            return 0;
        }

        @Override
        public boolean act() {
            yell(""+bombtime+"!");
            if (bombtime < 1){
                DungeonBomb bomb = new DungeonBomb();
                bomb.explode(pos);
                yell("KA-BOOM!!!");
                destroy();
                sprite.die();
            }
			bombtime --;
            return super.act();
        }

    }
}

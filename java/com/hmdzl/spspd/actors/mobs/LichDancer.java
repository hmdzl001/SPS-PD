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
import com.hmdzl.spspd.actors.buffs.SelfDestroy;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.ArmorKit;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.artifacts.GlassTotem;
import com.hmdzl.spspd.items.bombs.AddBomb;
import com.hmdzl.spspd.items.bombs.XBomb;
import com.hmdzl.spspd.items.journalpages.Sokoban4;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.misc.DanceLion;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.items.weapon.rockcode.Lbox;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BatteryTombSprite;
import com.hmdzl.spspd.sprites.LichDancerSprite;
import com.hmdzl.spspd.sprites.SeekingBombSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

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
				a.pos = Random.Int(Dungeon.depth.randomRespawnCellMob());
			} while (Dungeon.depth.map[a.pos] != Terrain.PEDESTAL
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
		
        if( 2 - breaks > 3 * HP / HT ) {
			breaks++;
			jump();
			addbomb();
            return true;
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
			newPos = Random.Int(Floor.getLength());
		} while (Dungeon.depth.map[newPos] != Terrain.WELL && Dungeon.depth.map[newPos] != Terrain.STATUE_SP);
		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}
		spawnTomb();
		spend(1 / speed());
	}

	private void addbomb() {
		int bombPos;
		do {
			bombPos = Random.Int(Floor.getLength());
		} while (!Floor.fieldOfView[bombPos] || !Floor.passable[bombPos]
				|| Floor.adjacent(bombPos, enemy.pos)
				|| Actor.findChar(bombPos) != null);

		Mob	m = new LinkAddBomb();
		GameScene.add(m);
		ScrollOfTeleportation.appear(m, bombPos);
	}



	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.prolong(enemy, Vertigo.class,3f);
		}
		
		enemy.damage(damageRoll()/2, ENERGY_DAMAGE,2);
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

		 Dungeon.depth.drop(new Sokoban4(), pos).sprite.drop();
		 
		 GameScene.bossSlain();
		Dungeon.depth.unseal();

		Dungeon.depth.drop(new ArmorKit(), pos).sprite.drop();
		Dungeon.depth.drop(new SkeletonKey(Dungeon.dungeondepth), pos).sprite.drop();
		Dungeon.depth.drop(new Gold(Random.Int(1000, 2000)), pos).sprite.drop();

		if (Dungeon.hero.heroClass == HeroClass.PERFORMER && Hero.skins == 7)
			Dungeon.depth.drop(new Lbox(), Dungeon.hero.pos).sprite.drop();

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
        if (Dungeon.depth.mobs != null) {
            for (Mob mob : Dungeon.depth.mobs) {
                if (mob instanceof BatteryTomb) {
                    batteryAlive++;
                }
            }
        }
        return batteryAlive > 0;
    }	
	
	@Override
	public void damage(int dmg, Object src, int type) {
		if (checkBattery()) {
			dmg = Random.Int(10);
			//Buff.affect(this,ShieldArmor.class).level(100);
		}
		super.damage(dmg, src,type);
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

        if( Random.Int(20) == 0 && Dungeon.depth.mobs.size()< 6) {
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
	public void damage(int dmg, Object src, int type) {
	    if ( dmg > 50 ) dmg = 50;
		super.damage(dmg, src,type);

	}
}
    public static class LinkAddBomb extends Mob {
        {
            spriteClass = SeekingBombSprite.class;

            HP = HT = 8;
            evadeSkill = 0;
            baseSpeed = 1f;
            EXP = 0;

            state = PASSIVE;

            properties.add(Property.MECH);
            properties.add(Property.MINIBOSS);
        }

		@Override
        public int drRoll() {
            return 0;
        }

        @Override
        public boolean act() {
            yell(""+HP/2+"!");
            return super.act();
        }

		@Override
		public void damage(int dmg, Object src, int type) {
			if (!(src instanceof SelfDestroy) ) dmg = 0;
			super.damage(dmg, src,type);
		}

		public void die(Object cause) {
			AddBomb addbomb = new AddBomb();
			addbomb.explode(pos);
			for (Mob mob : Dungeon.depth.mobs) {
				if (mob instanceof LichDancer) {
					Mob	ma = new LinkXBomb();
					GameScene.add(ma);
					ScrollOfTeleportation.appear(ma, pos);
				}
			}
			super.die(cause);
		}
	}

	public static class LinkXBomb extends Mob {
		{
			spriteClass = SeekingBombSprite.class;

			HP = HT = 8;
			evadeSkill = 0;
			baseSpeed = 1f;
			EXP = 0;

			state = PASSIVE;

			properties.add(Property.MECH);
			properties.add(Property.MINIBOSS);
		}

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		public boolean act() {
			yell(""+HP/2+"!");
			return super.act();
		}

		@Override
		public void damage(int dmg, Object src, int type) {
			if (!(src instanceof SelfDestroy) ) dmg = 0;
			super.damage(dmg, src,type);
		}

		public void die(Object cause) {
			XBomb xbomb = new XBomb();
			xbomb.explode(pos);
			for (Mob mob : Dungeon.depth.mobs) {
				if (mob instanceof LichDancer) {
					Mob	mb = new LinkAddBomb();
					GameScene.add(mb);
					ScrollOfTeleportation.appear(mb, pos);
				}
			}
			super.die(cause);
		}
	}
}

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
package com.hmdzl.spspd.change.actors.mobs;

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.ShatteredPixelDungeon;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.items.ArmorKit;
import com.hmdzl.spspd.change.items.DolyaSlate;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.RedDewdrop;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.artifacts.GlassTotem;
import com.hmdzl.spspd.change.items.bombs.DungeonBomb;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.effects.Flare;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.journalpages.Sokoban4;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.CityBossLevel;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.BatteryTombSprite;
import com.hmdzl.spspd.change.sprites.DwarfKingTombSprite;
import com.hmdzl.spspd.change.sprites.KingSprite;
import com.hmdzl.spspd.change.sprites.LichDancerSprite;
import com.hmdzl.spspd.change.sprites.SeekingBombSprite;
import com.hmdzl.spspd.change.sprites.UndeadSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class LichDancer extends Mob {

	private static final int MAX_ARMY_SIZE = 4;
	private static final int REGEN = 3;


	{
		spriteClass = LichDancerSprite.class;

		HP = HT = 1000;
		EXP = 60;
		evadeSkill = 25; 
		baseSpeed = 1f;
		
		loot = new GlassTotem().identify();
		lootChance = 0.2f;
		
		lootOther = Generator.Category.MUSICWEAPON;
		lootChance = 1f;		

		properties.add(Property.UNDEAD);
		properties.add(Property.BOSS);

	}

    private int tombAlive = 0;
	private int breaks=0;
	
	public void spawnTomb() {
		BatteryTomb a = new BatteryTomb();
		BatteryTomb b = new BatteryTomb();
		a.pos = Terrain.PEDESTAL;
		b.pos = Terrain.PEDESTAL;
		do {
			a.pos = Random.Int(Dungeon.level.randomRespawnCellMob());
		} while (Dungeon.level.map[a.pos] != Terrain.PEDESTAL
				|| Actor.findChar(a.pos) != null);
		do {
			b.pos = Random.Int(Dungeon.level.randomRespawnCellMob());
		} while (Dungeon.level.map[b.pos] != Terrain.PEDESTAL
				|| Actor.findChar(b.pos) != null);
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
			spawnTomb();

				int newPos = -1;
				for (int i = 0; i < 750; i++) {
					newPos = Dungeon.level.wellRespawnCellMob();
					if (newPos != -1) {
						break;
					}
				}
				if (newPos != -1) {
					Actor.freeCell(pos);
					CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
					pos = newPos;
					sprite.place(pos);
					sprite.visible = Dungeon.visible[pos];
					//GLog.n(Messages.get(this, "blink"));
				}


            return true;
        } 		
		
		if (HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP = HP + REGEN;
		}
		
		return super.act();
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.prolong(enemy, Vertigo.class,3f);
		}

		if (Random.Int(5) == 0){
			ArrayList<Integer> spawnPoints = new ArrayList<>();

			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = enemy.pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar( p ) == null && (Dungeon.level.passable[p] || Dungeon.level.avoid[p])) {
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
        return damage;
	}



	private static final String BREAKS	= "breaks";
	private static final String TOMBALIVE	= "tombAlive";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( BREAKS, breaks );
		bundle.put( TOMBALIVE, tombAlive );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        breaks = bundle.getInt( BREAKS );
		tombAlive  = bundle.getInt( TOMBALIVE );
    }	

	@Override
	public void die(Object cause) {

		 Dungeon.level.drop(new Sokoban4(), pos).sprite.drop();
		 
		 if (!Dungeon.limitedDrops.journal.dropped()){ 
			  Dungeon.level.drop(new DolyaSlate(), pos).sprite.drop();
			  Dungeon.limitedDrops.journal.drop();
			}
		 
		 GameScene.bossSlain();
		((CityBossLevel) Dungeon.level).unseal();
	
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		Dungeon.level.drop(new Gold(Random.Int(1000, 2000)), pos).sprite.drop();

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
        if (batteryAlive > 0) {
            return true;
        } else {
            return false;
        }
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

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(EnchantmentDark.class);
		
		RESISTANCES.add(WandOfDisintegration.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Paralysis.class);
		IMMUNITIES.add(Vertigo.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
    public static class BatteryTomb extends Mob  {

	{
		spriteClass = BatteryTombSprite.class;

		HP = HT = 500;
		evadeSkill = 0;

		EXP = 10;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new StoneOre();
		lootChance = 0.05f;

		properties.add(Property.MECH);
		properties.add(Property.BOSS);
	}
	
	@Override
    public boolean act() {

        if( Random.Int(20) == 0 && Dungeon.level.mobs.size()< 9) {
			DwarfLich.spawnAround(pos);
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

        private static final int BOMB_DELAY = 3;
        private int timeToBomb = BOMB_DELAY;
        {
            spriteClass = SeekingBombSprite.class;

            HP = HT = 1;
            evadeSkill = 0;
            baseSpeed = 1f;
            timeToBomb = BOMB_DELAY;
            EXP = 0;

            state = PASSIVE;

            properties.add(Property.MECH);
            properties.add(Property.MINIBOSS);
        }

        @Override
        public void die(Object cause) {
            DungeonBomb bomb = new DungeonBomb();
            bomb.explode(pos);
            super.die(cause);

        }

        @Override
        public int drRoll() {
            return 0;
        }

        @Override
        public boolean act() {
            yell(""+timeToBomb+"!");
            if (timeToBomb == 0){
                DungeonBomb bomb = new DungeonBomb();
                bomb.explode(pos);
                yell("KA-BOOM!!!");
                destroy();
                sprite.die();
            }
			timeToBomb --;
            return super.act();
        }

    }
}

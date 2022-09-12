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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.AdamantRing;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.misc.GnollMark;
import com.hmdzl.spspd.items.quest.GnollClothes;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.GnollKeeperSprite;
import com.hmdzl.spspd.sprites.GnollKingSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class GnollKing extends Mob {

    protected int breaks = 0;
	
	{
		spriteClass = GnollKingSprite.class;

		HP = HT = 1000;
		evadeSkill = 0;

		EXP = 30;
		
		baseSpeed = 0.75f;

		loot = new GnollClothes();
		lootChance = 1f;

		FLEEING = new Fleeing();

		properties.add(Property.ORC);
		properties.add(Property.BOSS);
	}

	@Override
	public Item SupercreateLoot(){
		return new GnollMark().dounique();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15,25);
	}

	@Override
	public int hitSkill(Char target) {
		return 100;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}
	
	@Override
	public float speed() {
		if (breaks == 1) return 4*super.speed();
		else return super.speed();
	}

	
	@Override
    public boolean act() {

        if( 3 - breaks > 4 * HP / HT ) {
            breaks++;
			yell(Messages.get(this,"angry"));
            return true;
        } 
		
	    if (breaks == 1){
		    state = FLEEING;
		}
		
		if (breaks == 2){
			state = HUNTING;
		}
		
		if (breaks > 0){
			int newPos = -1;
				for (int i = 0; i < 10; i++) {
				newPos = Dungeon.level.randomRespawnCellMob();
				if (newPos != -1) {
					break;
				    }
			    }
		    if (Dungeon.level.mobs.size()<4){
				GnollKeeper.spawnAroundChance(newPos);
		    }  	
		}
        return super.act();
    }
	
	@Override
	public int attackProc(Char enemy, int damage) {
	
		if (breaks == 0){
		    if (Random.Int(2) == 0) {
			    if(enemy == Dungeon.hero){
			      Buff.prolong(enemy, Cripple.class, 3 );
			    }
		    }
		}
	
		
		if (breaks == 2){
			if (Random.Int(2) == 0) {
			    if(enemy == Dungeon.hero){
			       Buff.prolong(enemy, Roots.class, 3 );
			    }
		    }
		}
		
		if (breaks == 3){
			if (Random.Int(2) == 0) {
			    if(enemy == Dungeon.hero){
			    Buff.prolong(enemy, STRdown.class, 3);
			    }
		    }
		}

		return damage;
	}	
	
	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = Random.IntRange(0, 20);
				
		if (breaks == 3){
		    if (dmg > 0 || (Random.Int(3) == 0 )) {
			enemy.damage(dmg, this);
		    }
        }
		return super.defenseProc(enemy, damage);
	}

	@Override
	public void damage(int dmg, Object src) {
	
		dmg = Math.min(dmg,20);
		
		if (breaks == 2){
		    if (dmg > 15){
			    GameScene.add(Blob.seed(pos, 30, CorruptGas.class));
			}
		}
		super.damage(dmg, src);
	}	
	
	@Override
	protected boolean canAttack(Char enemy) {
		
		if (breaks == 2){
		return Level.distance( pos, enemy.pos ) <= 2 ;}
		else return Level.distance( pos, enemy.pos ) <= 1;
	}

	@Override
	public void die(Object cause) {
		
        super.die(cause);
		
		GameScene.bossSlain();
		Dungeon.level.drop(new AdamantRing(), pos).sprite.drop();
		Dungeon.level.drop(new Gold(Random.Int(1000, 1500)), pos).sprite.drop();
		
		
		Dungeon.gnollkingkilled=true;
		
		yell(Messages.get(this,"die"));
		
	}
	
	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,"notice"));
	}
	
	
    {
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		//resistances.add(EnchantmentDark.class);
		immunities.add(EnchantmentDark.class);
		immunities.add(Terror.class);
		immunities.add(Amok.class);
		immunities.add(Charm.class);
		immunities.add(Sleep.class);
		immunities.add(Burning.class);
		immunities.add(ToxicGas.class);
		immunities.add(ScrollOfPsionicBlast.class);
		immunities.add(Vertigo.class);
		immunities.add(Paralysis.class);
	    immunities.add(Bleeding.class);
		immunities.add(CorruptGas.class);
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
	public static class GnollKeeper extends Mob {
		
		private static final float SPAWN_DELAY = 10f;
		
		{
			//name = "Gnoll Keeper";
			spriteClass = GnollKeeperSprite.class;

			HP = HT = 5;
			evadeSkill = 10;

			EXP = 0;

			state = WANDERING;
		}

		@Override
		public int hitSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(0, 0);
		}

		@Override
		public int drRoll() {
			return 1;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			yell(Messages.get(this ,"safe"));
			return damage;
		}

		@Override
		public boolean act() {
			
			return super.act();
		}
		
		public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
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
	
	public static GnollKeeper spawnAt(int pos) {

		GnollKeeper g1 = new GnollKeeper();
    	
			g1.pos = pos;
			g1.state = g1.HUNTING;
			GameScene.add(g1, SPAWN_DELAY);

			return g1;
     
        }

	}

}
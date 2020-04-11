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

import java.util.HashSet;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.items.DolyaSlate;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.artifacts.ChaliceOfBlood;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.items.weapon.missiles.Skull;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.Flare;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.journalpages.Sokoban4;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.CityBossLevel;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.DwarfKingTombSprite;
import com.hmdzl.spspd.sprites.KingSprite;
import com.hmdzl.spspd.sprites.UndeadSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class King extends Mob {

	private static final int MAX_ARMY_SIZE = 5;
	private static final int REGEN = 3;


	{
		spriteClass = KingSprite.class;

		HP = HT = 1500;
		EXP = 60;
		evadeSkill = 25; 
		baseSpeed = 0.75f;

		loot = new ChaliceOfBlood().identify();
		lootChance = 0.2f;
		
		lootOther = new Skull(5);
		lootChanceOther = 1f;
		
		properties.add(Property.DWARF);
		properties.add(Property.BOSS);

		Undead.count = 0;

	}

	private boolean nextPedestal = true;
	
    private int tombAlive = 0;
	private static final String TOMBALIVE	= "tombAlive";
	private static final String PEDESTAL = "pedestal";
	
	public void spawnTomb() {
		DwarfKingTomb a = new DwarfKingTomb();

		a.pos = Terrain.EMPTY_WELL;
		do {
			a.pos = Random.Int(Dungeon.level.randomRespawnCellMob());
		} while (Dungeon.level.map[a.pos] != Terrain.EMPTY_WELL
				|| Actor.findChar(a.pos) != null);
		GameScene.add(a);
	}		

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(PEDESTAL, nextPedestal);
		bundle.put( TOMBALIVE, tombAlive );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		nextPedestal = bundle.getBoolean(PEDESTAL);
		tombAlive  = bundle.getInt( TOMBALIVE );
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 38);
	}

	@Override
	public int hitSkill(Char target) {
		return 62;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 14);
	}

	@Override
	protected boolean getCloser(int target) {
		return canTryToSummon() ? super.getCloser(CityBossLevel
				.pedestal(nextPedestal)) : super.getCloser(target);
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return canTryToSummon() ? pos == CityBossLevel.pedestal(nextPedestal)
				: Level.adjacent(pos, enemy.pos);
	}

	private boolean canTryToSummon() {
		if (Undead.count < maxArmySize()) {
			Char ch = Actor.findChar(CityBossLevel.pedestal(nextPedestal));
			return ch == this || ch == null;
		} else {
			return false;
		}
	}

	@Override
	public boolean attack(Char enemy) {
		if (canTryToSummon() && pos == CityBossLevel.pedestal(nextPedestal)) {
			summon();
			return true;
		} else {
			if (Actor.findChar(CityBossLevel.pedestal(nextPedestal)) == enemy) {
				nextPedestal = !nextPedestal;
			}
			return super.attack(enemy);
		}
	}


	@Override
	protected boolean act() {
		
		if (tombAlive < 1){
			spawnTomb();
			tombAlive++;
		}		
		
		if (HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP = HP + REGEN;
		}
		
		return super.act();
	}
	
	
	private void summonLiches (int pos){
		 DwarfLich.spawnAround(pos);
	}

	@Override
	public void die(Object cause) {
		            
		int findTomb=Dungeon.hero.pos;
		yell(Messages.get(this, "cannot"));
		 for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof DwarfKingTomb){findTomb=mob.pos;}
		 }
		 
		 Dungeon.level.drop(new Sokoban4(), pos).sprite.drop();
		 
		 if (!Dungeon.limitedDrops.journal.dropped()){ 
			  Dungeon.level.drop(new DolyaSlate(), pos).sprite.drop();
			  Dungeon.limitedDrops.journal.drop();
			}
		 
		summonLiches(findTomb);
		GLog.n(Messages.get(this, "liches"));

		super.die(cause);

	}

	private int maxArmySize() {
		return 1 + MAX_ARMY_SIZE * (HT - HP) / HT;
	}

	private void summon() {

		nextPedestal = !nextPedestal;

		sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.4f, 2);
		Sample.INSTANCE.play(Assets.SND_CHALLENGE);

		boolean[] passable = Level.passable.clone();
		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				passable[((Char) actor).pos] = false;
			}
		}

		int undeadsToSummon = maxArmySize() - Undead.count;

		PathFinder.buildDistanceMap(pos, passable, undeadsToSummon);
		PathFinder.distance[pos] = Integer.MAX_VALUE;
		int dist = 1;

		undeadLabel: for (int i = 0; i < undeadsToSummon; i++) {
			do {
				for (int j = 0; j < Level.getLength(); j++) {
					if (PathFinder.distance[j] == dist) {

						Undead undead = new Undead();
						undead.pos = j;
						GameScene.add(undead);

						ScrollOfTeleportation.appear(undead, j);
						new Flare(3, 32).color(0x000000, false).show(
								undead.sprite, 2f);

						PathFinder.distance[j] = Integer.MAX_VALUE;

						continue undeadLabel;
					}
				}
				dist++;
			} while (dist < undeadsToSummon);
		}

		yell(Messages.get(this, "arise"));
		HP += Random.Int(1, HT - HP);
		sprite.emitter().burst(ElmoParticle.FACTORY, 5);
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,"meeting"));
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

	public static class Undead extends Mob {

		public static int count = 0;

		{
			//name = "undead dwarf";
			spriteClass = UndeadSprite.class;

			HP = HT = 100;
			evadeSkill = 15;

			EXP = 0;

			state = WANDERING;
			

		properties.add(Property.UNDEAD);
		properties.add(Property.BOSS);			
		}

		@Override
		protected void onAdd() {
			count++;
			super.onAdd();
		}

		@Override
		protected void onRemove() {
			count--;
			super.onRemove();
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(12, 16);
		}

		@Override
		public int hitSkill(Char target) {
			return 49;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(MAX_ARMY_SIZE) == 0) {
				Buff.prolong(enemy, Paralysis.class, 1);
			}

			return damage;
		}

		@Override
		public void damage(int dmg, Object src) {
			super.damage(dmg, src);
			if (src instanceof ToxicGas) {
				((ToxicGas) src).clear(pos);
			}
		}

		@Override
		public void die(Object cause) {
			super.die(cause);

			if (Dungeon.visible[pos]) {
				Sample.INSTANCE.play(Assets.SND_BONES);
			}
		}

		@Override
		public int drRoll() {
			return 5;
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(EnchantmentDark.class);
			IMMUNITIES.add(Paralysis.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	
    public static class DwarfKingTomb extends Mob  {

	{
		spriteClass = DwarfKingTombSprite.class;

		HP = HT = 1000;
		evadeSkill = 5;

		EXP = 10;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new StoneOre();
		lootChance = 0.05f;

		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
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

	
	public boolean checkKing(){

		int kingAlive=0;
		if(Dungeon.level.mobs!=null){
       for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof King){
				kingAlive++;
			   }
			}
		}
       if (kingAlive>0){
		return true;
       } else {
      return false;
       }
	}

	@Override
	public void damage(int dmg, Object src) {
		if(checkKing()){
			yell(Messages.get(this , "impossible"));
		} else {
		super.damage(dmg, src);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {
		
        super.die(cause);
		
		for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
			if (mob instanceof DwarfLich || mob instanceof King || mob instanceof King.Undead || mob instanceof Wraith) {
				mob.die(cause);
			}
		}
		
		GameScene.bossSlain();
		((CityBossLevel) Dungeon.level).unseal();
	
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		Dungeon.level.drop(new Gold(Random.Int(1000, 2000)), pos).sprite.drop();

		Badges.validateBossSlain();
	
	}

		
}	
}

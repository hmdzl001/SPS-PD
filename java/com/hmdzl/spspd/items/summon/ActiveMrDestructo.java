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
package com.hmdzl.spspd.items.summon;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.sprites.MrDestructo2dot0Sprite;
import com.hmdzl.spspd.sprites.MrDestructoSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class ActiveMrDestructo extends Item {

	{
		//name = "mr destructo";
		image = ItemSpriteSheet.ACTIVEMRD;
		defaultAction = AC_ACTIVE;
		stackable = true;
	}

	
	private static boolean activate = false;

	private static final String AC_ACTIVE = "ACTIVE";

	

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_ACTIVE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_ACTIVE)) {
			activate = true;
			action = AC_THROW;
		} else
			activate = false;

		super.execute(hero, action);
	}

	@Override
	protected void onThrow(int cell) {
		
		if (Actor.findChar(cell) != null) {
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int i : Floor.NEIGHBOURS8)
				if (Floor.passable[cell + i])
					candidates.add(cell + i);
			int newCell = candidates.isEmpty() ? cell : Random
					.element(candidates);
			
			   if (!Floor.pit[newCell] && activate) {
			   	 if (Dungeon.hero.subClass == HeroSubClass.LEADER){
					 MrDestructo2dot0.spawnAt(newCell);
				 } else MrDestructo.spawnAt(newCell);
			   } else {
			   Dungeon.depth.drop(this, newCell).sprite.drop(cell);
			   }
			   
		} else if (!Floor.pit[cell] && activate) {
            if (Dungeon.hero.subClass == HeroSubClass.LEADER){
                MrDestructo2dot0.spawnAt(cell);
            } else
				MrDestructo.spawnAt(cell);
		} else {
			
			super.onThrow(cell);
		}

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
	public int price() {
		return 100 * quantity;
	}

	public static class MrDestructo extends Mob {

		{
			spriteClass = MrDestructoSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT= 100;
			evadeSkill = 3;
			ally=true;

			properties.add(Property.MECH);
		}

		private Ballistica beam;

		private static final float SPAWN_DELAY = 0.1f;

		@Override
		public int drRoll() {
			return 0;
		}


		@Override
		protected boolean act() {

			for (int n : Floor.NEIGHBOURS8) {
				int c = pos + n;
				if (c< Floor.getLength() && c>0){
					Char ch = Actor.findChar(c);
				}
			}
			//Level.fieldOfView[Dungeon.hero.pos] &&

			boolean result = super.act();
			return result;
		}

		@Override
		public void move(int step) {
		}

		@Override
		protected Char chooseEnemy() {

			if (enemy == null || !enemy.isAlive()) {
				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.depth.mobs) {
					if (mob.hostile && Floor.fieldOfView[mob.pos]) {
						enemies.add(mob);
					}
				}

				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}

			return enemy;
		}



		public static MrDestructo spawnAt(int pos) {

			MrDestructo b = new MrDestructo();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}

		private int hitCell;

		@Override
		protected boolean canAttack(Char enemy) {

			beam = new Ballistica( pos, enemy.pos, Ballistica.STOP_TERRAIN);

			return beam.subPath(1, beam.dist).contains(enemy.pos);
		}

		@Override
		public int hitSkill(Char target) {
			return 20+(Dungeon.dungeondepth);
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
				sprite.attack(beam.collisionPos );
				return false;
			} else {
				attack(enemy);
				return true;
			}
		}

		@Override
		public boolean attack(Char enemy) {

			for (int pos : beam.subPath(1, beam.dist)) {

				Char ch = Actor.findChar(pos);
				if (ch == null) {
					continue;
				}

				if (hit(this, ch, true)) {
					ch.damage(Random.NormalIntRange(Dungeon.dungeondepth, Dungeon.dungeondepth +12), this,2);
					damage(Random.NormalIntRange(5, 10), this,3);
					Buff.affect(ch,ArmorBreak.class,3f).level(30);

					if (Dungeon.visible[pos]) {
						ch.sprite.flash();
						CellEmitter.center(pos).burst(PurpleParticle.BURST,
								Random.IntRange(1, 2));
					}

					//if (!ch.isAlive() && ch == Dungeon.hero) {
					//	Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
						//GLog.n(Messages.get(this, "kill"));
					//}
				} else {
					ch.sprite.showStatus(CharSprite.NEUTRAL, ch.defenseVerb());
				}
			}

			return true;
		}

		@Override
		public void beckon(int cell) {
		}

        {
			resistances.add(EnchantmentDark.class);
			immunities.add(Terror.class);
			immunities.add(ToxicGas.class);
		}

	}
	public static class MrDestructo2dot0 extends Mob {

		{
			spriteClass = MrDestructo2dot0Sprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT= 200;
			evadeSkill = 35;
			ally=true;

			properties.add(Property.MECH);
		}

		private Ballistica beam;

		private static final float SPAWN_DELAY = 1f;

		@Override
		public int drRoll() {
			return 0;
		}


		@Override
		protected boolean act() {

			for (int n : Floor.NEIGHBOURS8) {
				int c = pos + n;
				if (c< Floor.getLength() && c>0){
					Char ch = Actor.findChar(c);
				}
			}
			//Level.fieldOfView[Dungeon.hero.pos] &&

			boolean result = super.act();
			return result;
		}

		@Override
		public void move(int step) {
		}

		@Override
		protected Char chooseEnemy() {

			if (enemy == null || !enemy.isAlive()) {
				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.depth.mobs) {
					if (mob.hostile && Floor.fieldOfView[mob.pos]) {
						enemies.add(mob);
					}
				}

				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}

			return enemy;
		}



		public static MrDestructo2dot0 spawnAt(int pos) {

			MrDestructo2dot0 b = new MrDestructo2dot0();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}

		private int hitCell;

		@Override
		protected boolean canAttack(Char enemy) {

			beam = new Ballistica( pos, enemy.pos, Ballistica.STOP_TERRAIN);

			return beam.subPath(1, beam.dist).contains(enemy.pos);
		}

		@Override
		public int hitSkill(Char target) {
			return 30+(Dungeon.dungeondepth);
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
				sprite.attack( beam.collisionPos );
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
					Buff.affect(ch,ArmorBreak.class,3f).level(50);
					ch.damage(Random.NormalIntRange(Dungeon.dungeondepth +20, Dungeon.dungeondepth +32), this,2);
					damage(Random.NormalIntRange(5, 10), this,3);

					if (Dungeon.visible[pos]) {
						ch.sprite.flash();
						CellEmitter.center(pos).burst(PurpleParticle.BURST,
								Random.IntRange(1, 2));
					}

					//if (!ch.isAlive() && ch == Dungeon.hero) {
					//	Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
						//GLog.n(Messages.get(this, "kill"));
					//}
				} else {
					ch.sprite.showStatus(CharSprite.NEUTRAL, ch.defenseVerb());
				}
			}

			return true;
		}

		@Override
		public void beckon(int cell) {
		}

        {
			resistances.add(EnchantmentDark.class);
			immunities.add(Terror.class);
			immunities.add(ToxicGas.class);
		}

	}

}

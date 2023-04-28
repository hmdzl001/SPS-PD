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
package com.hmdzl.spspd.actors.mobs.pets;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.meatfood.MeatFood;
import com.hmdzl.spspd.items.potions.PotionOfMending;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.FlySprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

import static com.hmdzl.spspd.Dungeon.hero;

public class Fly extends PET {
	
	{
		//name = "Fly";
		spriteClass = FlySprite.class;
        //flying=true;
		state = HUNTING;
		type = 203;
		cooldown=50;
		oldcooldown=30;
		properties.add(Property.BEAST);

	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof MeatFood;
	}


	@Override
	public void updateStats()  {
		evadeSkill = (int)(hero.petLevel*1.5);
		HT = 150 + 2*hero.petLevel;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange((int)(5+hero.petLevel*0.5), (int)(5+hero.petLevel*1.5));
	}

	@Override
	public Item SupercreateLoot(){
		return new PotionOfMending();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(hero.petLevel*2,hero.petLevel*5);
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 5;
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0) {
			Buff.affect(enemy, Slow.class, 5f);
		}
		return damage;
	}	
	
	@Override
	public int defenseProc(Char enemy, int damage) {
       
		for (int n : Level.NEIGHBOURS4) {
			int cell = enemy.pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null && cooldown == 0) {
				shatter(null, cell);
				cooldown = Math.max(10,30 - hero.petLevel);
			}
		}
		 if (cooldown > 0) {
		   cooldown--;
		} 

		return damage;
	}		
	
	public void shatter(Char owner, int pos) {
		int newPos = pos;
		if (Actor.findChar(pos) != null) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			for (int n : Level.NEIGHBOURS4) {
				int c = pos + n;
				if (passable[c] && Actor.findChar(c) == null) {
					candidates.add(c);
				}
			}
			newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
		}

		if (newPos != -1) {
			if (hero.subClass == HeroSubClass.LEADER) {
				FlyTwo mob = new FlyTwo();
				mob.spawn();
				mob.HP = mob.HT = hero.petLevel*5;
				mob.pos = newPos;

				GameScene.add(mob);
				Actor.addDelayed(new Pushing(mob, pos, newPos), -1f);

				mob.sprite.alpha(0);
				mob.sprite.parent.add(new AlphaTweener(mob.sprite, 1, 0.15f));

				Sample.INSTANCE.play(Assets.SND_BEE);

			} else {
				FlyTwo mob = new FlyTwo();
				mob.spawn();
				mob.HP = mob.HT = 1;
				mob.pos = newPos;

				GameScene.add(mob);
				Actor.addDelayed(new Pushing(mob, pos, newPos), -1f);

				mob.sprite.alpha(0);
				mob.sprite.parent.add(new AlphaTweener(mob.sprite, 1, 0.15f));

				Sample.INSTANCE.play(Assets.SND_BEE);

			}

		}

	}
	
	
	public static class FlyTwo extends NPC {

		{
			spriteClass = FlySprite.class;
			viewDistance = 6;
			ally=true;
			flying = true;
			state = WANDERING;
		}

		public void spawn() {
			HT = 1;
			evadeSkill = 15 + hero.petLevel;
		}
		@Override
		public int hitSkill(Char target) {
			return evadeSkill*2;
		}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange((int)(5+hero.petLevel*0.5), (int)(5+hero.petLevel*1.5));
	}
		@Override
		protected boolean canAttack(Char enemy) {
			return super.canAttack(enemy);
		}

		@Override
		protected boolean getCloser(int target) {
			if (state == WANDERING
					|| Level.distance(target, hero.pos) > 6)
				this.target = target = hero.pos;
			return super.getCloser(target);
		}
		@Override
		protected Char chooseEnemy() {
			if (enemy == null || !enemy.isAlive() || state == WANDERING) {

				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.level.mobs) {
					if (mob.hostile && Level.fieldOfView[mob.pos]
							&& mob.state != mob.PASSIVE) {
						enemies.add(mob);
					}
				}
				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}
			return enemy;
		}

		@Override
		public boolean interact() {
			if (Level.passable[pos] || hero.flying) {
			int curPos = pos;

			moveSprite(pos, hero.pos);
			move(hero.pos);

			hero.sprite.move(hero.pos, curPos);
			hero.move(curPos);

			hero.spend(1 / hero.speed());
			hero.busy();
			return true;
			} else {
				return false;
			}
		}
	}			
}
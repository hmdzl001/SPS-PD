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
package com.hmdzl.spspd.items.weapon.missiles.throwing;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.sprites.LynnSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;
import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;

public class LynnDoll extends TossWeapon {


	{
		//name = "HoneyArrow";
		image = ItemSpriteSheet.LYNN_DOLL;

		STR = 10;

		MIN = 1;
		MAX = 1;
	}

	public LynnDoll() {
		this(1);
	}

	public LynnDoll(int number) {
		super();
		quantity = number;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
			int cell = defender.pos;
			shatter(defender, cell);
		super.proc(attacker, defender, damage);
	}

	@Override
	public Item random() {
		quantity = Random.Int(1, 2);
		return this;
	}

	@Override
	public int price() {
		return 20 * quantity;
	}

	public void shatter(Char owner, int pos) {

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_SHATTER);
			Splash.at(pos, 0xffd500, 5);
		}
		int newPos =  Dungeon.level.randomCell();
		if (newPos != -1) {
			LynnDoll.CurseDoll doll = new LynnDoll.CurseDoll();
			    doll.spawn();
				doll.setPotInfo(pos, owner);
				doll.HP = doll.HT;
				doll.pos = newPos;

				GameScene.add(doll);
				Actor.addDelayed(new Pushing(doll, pos, newPos), -1f);

				doll.sprite.alpha(0);
				doll.sprite.parent.add(new AlphaTweener(doll.sprite, 1, 0.15f));

				Sample.INSTANCE.play(Assets.SND_BEE);
			}

	}

	public static class CurseDoll extends Mob {

		{
			spriteClass = LynnSprite.class;

			HT = 10000;
			evadeSkill = 0;
			viewDistance = 8;
			baseSpeed = 3;
			flying = true;
			state = WANDERING;

			properties.add(Property.UNKNOW);
		}
		private static final float SPAWN_DELAY = 1f;

		public void spawn() {
			;
		}
		// -1 refers to a pot that has gone missing.
		private int potPos;
		// -1 for no owner
		private int potHolder;

		private static final String POTPOS = "potpos";
		private static final String POTHOLDER = "potholder";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(POTPOS, potPos);
			bundle.put(POTHOLDER, potHolder);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);

			potPos = bundle.getInt(POTPOS);
			potHolder = bundle.getInt(POTHOLDER);
		}


		public void setPotInfo(int potPos, Char potHolder) {
			this.potPos = potPos;
			if (potHolder == null)
				this.potHolder = -1;
			else
				this.potHolder = potHolder.id();
		}

		@Override
		public int hitSkill(Char target) {
			return 1000;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(4 * hero.lvl, 8 * hero.lvl);
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			int dmg = damageRoll();
			if (enemy instanceof Mob) {
				((Mob) enemy).aggro(this);
			}

			enemy.damage(dmg, ENERGY_DAMAGE);
			damage = 0;
			if (dmg > enemy.HP){
				destroy();
			    this.sprite.die();
			}
			return damage;
		}

		@Override
		protected Char chooseEnemy() {
			if (Actor.findById(potHolder) != null)
				return (Char) Actor.findById(potHolder);
			else return null;

		}

		@Override
		protected boolean getCloser(int target) {
			if (enemy != null && Actor.findById(potHolder) == enemy) {
				target = enemy.pos;
			} else if (potPos != -1
					&& (state == WANDERING || Level.distance(target, potPos) > 3))
				this.target = target = potPos;
			return super.getCloser(target);
		}


		{
			immunities.add(Amok.class);
			immunities.add(Charm.class);
		}
	}

}

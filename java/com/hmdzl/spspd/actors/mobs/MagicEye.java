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
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.MagicEyeSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.LIGHT_DAMAGE;

public class MagicEye extends Mob {

	private static final String TXT_DEATHGAZE_KILLED = "%s's deathgaze killed you...";
	protected static final float SPAWN_DELAY = 2f;

	{
		spriteClass = MagicEyeSprite.class;

		HP = HT = 100+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 40+adj(1);
		viewDistance = Light.DISTANCE;

		EXP = 16;
	
		flying = true;

		loot = Generator.Category.SEED;
		lootChance = 0.1f;
		
		properties.add(Property.DEMONIC);
		properties.add(Property.MAGICER);
		properties.add(Property.ELEMENT);
	}

	private Ballistica beam;

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 20);
	}

	@Override
	protected boolean canAttack(Char enemy) {

		beam = new Ballistica( pos, enemy.pos, Ballistica.STOP_TERRAIN);

		return beam.subPath(1, beam.dist).contains(enemy.pos);
	}

	@Override
	public int hitSkill(Char target) {
		return 30+adj(0);
	}

	@Override
	protected float attackDelay() {
		return 2f;
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		
		enemy.damage(damageRoll()*3/4, LIGHT_DAMAGE,2);
		damage = damage/4;

		return damage;
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
			attack( enemy );
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
				ch.damage(Random.NormalIntRange(20, 50),LIGHT_DAMAGE,2);
				damage(Random.NormalIntRange(20, 50),LIGHT_DAMAGE,2 );
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

	public static void spawnAroundChance(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null && Random.Float()<0.50f) {
				spawnAt(cell);
			}
		}
	}

	public static MagicEye spawnAt(int pos) {
		if (Floor.passable[pos] && Actor.findChar(pos) == null) {
          
			MagicEye e = new MagicEye();
			e.pos = pos;
			e.state = e.HUNTING;
			GameScene.add(e, SPAWN_DELAY);

			e.sprite.alpha(0);
			e.sprite.parent.add(new AlphaTweener(e.sprite, 1, 0.5f));

		return e;
  			
		} else {
			return null;
		}
	}

    {
		resistances.add(WandOfDisintegration.class);
		resistances.add(EnchantmentDark.class);

		immunities.add(Terror.class);
	}

}

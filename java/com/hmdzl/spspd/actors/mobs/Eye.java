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
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.EyeSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.LIGHT_DAMAGE;

public class Eye extends Mob {

	private static final String TXT_DEATHGAZE_KILLED = "%s's deathgaze killed you...";
	protected static final float SPAWN_DELAY = 2f;

	{
		spriteClass = EyeSprite.class;

		HP = HT = 200+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 20+adj(1);
		viewDistance = Light.DISTANCE;

		EXP = 15;
		maxLvl = 35;

		flying = true;

		loot = new PotionOfHealing();
		lootChance = 0.1f;
		
		lootOther = new MysteryMeat();
		lootChanceOther = 0.5f; // by default, see die()
		
		properties.add(Property.DEMONIC);
		properties.add(Property.MAGICER);
	}

	private Ballistica beam;

	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( Generator.random(Generator.Category.POTION), Generator.random(Generator.Category.WAND));
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(20, 30);
	}

	private int hitCell;

	@Override
	protected boolean canAttack(Char enemy) {

		beam = new Ballistica( pos, enemy.pos, Ballistica.STOP_TERRAIN);

		return beam.subPath(1, beam.dist).contains(enemy.pos);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(5, 25);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		
		enemy.damage(damageRoll()*3/4, LIGHT_DAMAGE,2);
		damage = damage/4;

		return damage;
	}	

	@Override
	public int hitSkill(Char target) {
		return 30+adj(0);
	}

	@Override
	protected float attackDelay() {
		return 1.6f;
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
			sprite.attack(beam.collisionPos);
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
				ch.damage(Random.NormalIntRange(14, 20+adj(0)), LIGHT_DAMAGE,2);

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

	public static Eye spawnAt(int pos) {
		if (Floor.passable[pos] && Actor.findChar(pos) == null) {
          
			Eye e = new Eye();
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

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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.MirrorShield;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Boomerang;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.sprites.MissileSprite;
import com.hmdzl.spspd.ui.QuickSlotButton;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class LinkSword extends MeleeWeapon {
	public Buff passiveBuff;
	{
		//name = "LinkSword";
		image = ItemSpriteSheet.S_AND_S;
		defaultAction = AC_COURAGE;
		usesTargeting = true;
	}

	public LinkSword() {
		super(1, 1f, 1f, 1);
		unique = true;
		reinforced = true;
	}

	@Override
	public Item upgrade(boolean enchant) {
		MIN += 1;
		MAX += 3;
		return super.upgrade(enchant);
	}

	public final int fullCharge = 30;
	public int charge = 0;
	public int uptime = 0;
	private static final String CHARGE = "charge";
	private static final String UPTIME = "uptime";

	public static final String AC_POWER = "POWER";
	public static final String AC_WISDOM = "WISDOM";
	public static final String AC_COURAGE = "COURAGE";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
	
		actions.add(AC_COURAGE);
		
		if (charge > 20) {
			actions.add(AC_WISDOM);
		}
		if (Dungeon.hero.STR - this.STR >2) {
			actions.add(AC_POWER);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_POWER)) {
			curUser = hero;
			uptime++;
			GLog.w(Messages.get(LinkSword.class,"power"));
			curUser.sprite.operate(curUser.pos);
			curUser.sprite.centerEmitter().start(PurpleParticle.BURST, 0.05f, 10);
			curUser.spendAndNext(1f);
		} else if (action.equals(AC_WISDOM)) {
			curUser = hero;
			Buff.affect(hero, MirrorShield.class,2f);
			curUser.sprite.operate(curUser.pos);
			curUser.spendAndNext(1f);
			charge-=20;
		} else if (action.equals(AC_COURAGE)) {
			curUser = hero;
			curItem = this;
			GameScene.selectCell( zapper );
		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
		bundle.put(UPTIME,uptime);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
		uptime = bundle.getInt(UPTIME);
	}
	protected LinkBuff passiveBuff() {
		return new LinkCharge();
	}
	public class LinkBuff extends Buff {
		public int level() {
			return level;
		}
		public boolean isCursed() {
			return cursed;
		}
	}
	@Override
	public boolean doEquip(Hero hero) {
		activate(hero);
		return super.doEquip(hero);
	}
	@Override
	public void activate(Hero hero) {
		passiveBuff = passiveBuff();
		passiveBuff.attachTo(hero);
	}
	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {
			if (passiveBuff != null){
				passiveBuff.detach();
				passiveBuff = null;
			}
			hero.belongings.weapon = null;
			return true;
		} else {
			return false;
		}
	}
	public class LinkCharge extends LinkBuff {
		@Override
		public boolean act() {
			if (Dungeon.hero.HP>=Dungeon.hero.HT){
				LinkSword.this.RCH = 4;
			} else LinkSword.this.RCH = 1;
			if (uptime> 0) {
				STR = 10 + 2 * uptime;
				ACU = Math.min(1.6f,1+0.1f * uptime);
				DLY = Math.max(0.7f,1-0.05f * uptime);
			}
			spend(TICK);
			return true;
		}
		@Override
		public String toString() {
			return "LinkCharge";
		}
		@Override
		public void detach() {
			charge = 0;
			super.detach();
		}
	}
	
	
	@Override
	public int damageRoll(Hero hero) {

		int damage = super.damageRoll(hero);
		
		damage += Random.Int(uptime,2*uptime);
		
		return Math.round(damage);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		int exdmg = Dungeon.hero.damageRoll();
		if (Random.Int(100) > 60) {
			switch (Random.Int(7)) {
				case 0:
					defender.damage(Random.Int(exdmg / 4, exdmg / 2), this);
					break;
				case 1:
					Buff.affect(defender, Bleeding.class).set(Random.Int(3, damage));
					break;
				case 2:
					Buff.prolong(defender, Paralysis.class, 2);
					break;
				case 3:
					Buff.affect(defender, Cripple.class, 3);
					break;
				case 4:
					int p = attacker.pos;
					for (int n : Level.NEIGHBOURS8) {
						Char ch = Actor.findChar(n + p);
						if (ch != null && ch != defender && ch != attacker && ch.isAlive()) {

							int effectiveDamage = Math.max(exdmg, 0);

							ch.damage(effectiveDamage / 2, this);
						}
					}
					break;
				case 5:
					if (attacker.buff(ShieldArmor.class) == null){
					    Buff.affect(attacker, ShieldArmor.class).level(attacker.HT/10);
					}
					break;
				case 6:
					if (charge > 25 && Random.Int(10)==0) {
						defender.damage(2*damage, this);
					}
					break;
				default:
					break;
			}

			if (charge < fullCharge) {
				charge+=1;
			}
			
			if (enchantment != null) {
				enchantment.proc(this, attacker, defender, damage);
			}
			if ( defender.HP <= damage && Random.Int(10) == 0) {
				Dungeon.level.drop(Generator.random(Generator.Category.LINKDROP), defender.pos).sprite.drop();
			}
		}
	}

	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(this, "charge",charge,fullCharge);
		return info;
	}

	protected static CellSelector.Listener zapper = new  CellSelector.Listener() {

		@Override
		public void onSelect(final Integer target ) {
			Item proto = new Boomerang();
			if (target != null) {

				final LinkSword ls = (LinkSword) LinkSword.curItem;

				final Ballistica shot = new Ballistica( curUser.pos, target, Ballistica.MAGIC_BOLT);
				int cell = shot.collisionPos;

				if (target == curUser.pos || cell == curUser.pos) {
					GLog.i( Messages.get(Wand.class, "self_target") );
					return;
				}

				curUser.sprite.zap(cell);

				//attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
				if (Actor.findChar(target) != null)
					QuickSlotButton.target(Actor.findChar(target));
				else
					QuickSlotButton.target(Actor.findChar(cell));

				if (ls.charge >= 0) {

					curUser.busy();
					Callback callback = new Callback() {
						@Override
						public void call() {
							Char ch = Actor.findChar(target);
							if (ch != null) {
								if (ch.isAlive() && Random.Int(2) == 0) {
									Buff.affect(ch, Vertigo.class, 5f);
								}
								if(ls.charge > 10) {
									ls.charge-=10;
									ch.damage(ch.HT / 20, this);
								}
							}
						}
					};
					((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).reset(curUser.pos, target, proto,callback);
					Dungeon.hero.spendAndNext(1.5f);
				} else {
					GLog.w( Messages.get(Wand.class, "fizzles") );
				}

			}
		}

		@Override
		public String prompt() {
			return Messages.get(Wand.class, "prompt");
		}
	};
}

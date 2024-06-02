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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Shocked2;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Beam;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.DewVial;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.artifacts.UnstableSpellbook;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.QuickSlotButton;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DewWaterGun extends MeleeWeapon {

	public static final String AC_ZAP = "ZAP";
	public static final String AC_ADD = "ADD";
	private static final float TIME_TO_ZAP	= 1f;
	protected int collisionProperties =Ballistica.STOP_TERRAIN;

	{
		//name = "EleKatana";
		image = ItemSpriteSheet.DEW_WATER_GUN;
		defaultAction = AC_ZAP;

		usesTargeting = true;
	}

	public int charge = 0;
	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}

	public DewWaterGun() {
		super(3, 1f, 1f, 1);
		MIN = 5;
		MAX = 15;
		reinforced = true;
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_ZAP);
		actions.add(AC_ADD);
		return actions;
	}
	
	//protected abstract void onZap( Ballistica attack );

	@Override
	public String status() {
		if (levelKnown) {
			return charge + "/" + 5;
		} else {
			return null;
		}
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals( AC_ZAP )) {
			curUser = hero;
			curItem = this;
			if (!isEquipped(hero)) {
				GLog.i(Messages.get(Weapon.class, "need_to_equip"));
			} else if (this.charge > 0){
				GameScene.selectCell( zapper );
			} else {
				DewVial vial = Dungeon.hero.belongings.getItem(DewVial.class);
				if (vial.checkVolEx() >  (5-charge) * 2) {
					vial.upbook((5-charge) * 2);
					hero.sprite.operate(hero.pos);
					hero.busy();
					hero.spend(2f);
					Sample.INSTANCE.play(Assets.SND_BURNING);
					hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
					charge = 5;
					GLog.w( Messages.get(this, "full") );
				} else {
					GLog.w(Messages.get(this, "dew_empty"));
				}
			}
		} if (action.equals( AC_ADD )) {
			curUser = hero;
			curItem = this;
			if (!isEquipped(hero)) {
				GLog.i(Messages.get(Weapon.class, "need_to_equip"));
			} else {
				charge = 5;
				DewVial vial = Dungeon.hero.belongings.getItem(DewVial.class);
				if (vial.checkVolEx() >  (5-charge) * 2) {
					vial.upbook((5-charge) * 2);
					hero.sprite.operate(hero.pos);
					hero.busy();
					hero.spend(2f);
					Sample.INSTANCE.play(Assets.SND_BURNING);
					hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
					charge = 5;
					GLog.w(Messages.get(this, "full"));
				} else {
					GLog.w(Messages.get(this, "dew_empty"));
				}
			}
		} else {
			super.execute( hero, action );
		}
        
		//super.execute(hero, action);
	}

	public Item upgrade(boolean enchant) {
		MIN+=1;
        MAX+=2;
		if (ACU < 2.5f){
			ACU+=0.1f;
		}
		if (DLY > 0.5f) {
			DLY -= 0.05f;
		}
		super.upgrade(enchant);
		return this;
	}

	private int targetPos;

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
	    Buff.affect(defender, Wet.class,5f);
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	}

	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(Weapon.class, "charge",charge,5);
		return info;
	}

	protected static CellSelector.Listener zapper = new  CellSelector.Listener() {

		@Override
		public void onSelect( Integer target ) {

			if (target != null) {

				final DewWaterGun curWand = (DewWaterGun) DewWaterGun.curItem;

				final Ballistica shot = new Ballistica( curUser.pos, target, curWand.collisionProperties);
				int cell = shot.collisionPos;

				if (target == curUser.pos || cell == curUser.pos) {
					GLog.i( Messages.get(Wand.class, "self_target") );
					return;
				}
				
				if (curWand.charge < 1) {
					GLog.w( Messages.get(DewWaterGun.class, "no") );
                    return;
				}
				
				curUser.sprite.zap(cell);

				if (Actor.findChar(target) != null) {
					QuickSlotButton.target(Actor.findChar(target));
				} else {
					QuickSlotButton.target(Actor.findChar(cell));
				}

				curUser.busy();
					curWand.fx(shot, new Callback() {
						@Override
						public void call() {
							//Char ch = Actor.findChar(target);
							//QuickSlotButton.target(Actor.findChar(cell));
							curWand.onZap(shot);
							curWand.wandUsed();
						}
					});

					Invisibility.dispel();
			}
		}

		@Override
		public String prompt() {
			return Messages.get(DewWaterGun.class, "prompt");
		}
	};

	protected void onZap(Ballistica beam) {
		ArrayList<Char> chars = new ArrayList<>();
		for (int c : beam.path) {
			Char ch;
			if ((ch = Actor.findChar( c )) != null && ch != curUser) {
				chars.add( ch );
			}
		}
		for (Char ch : chars) {

			if (Floor.distance(beam.sourcePos, ch.pos) < 5) {
				ch.damage(Random.Int(16+level,20+4*level), DamageType.ICE_DAMAGE);
				ch.sprite.centerEmitter().burst(PurpleParticle.BURST, Random.IntRange(1, 2));
				ch.sprite.flash();
			}
		}
	}
	protected void fx(Ballistica beam, Callback callback) {
		curUser.sprite.parent.add(new Beam.WaterRay(curUser.sprite.center(), DungeonTilemap.tileCenterToWorld(beam.collisionPos)));
		callback.call();
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	protected void wandUsed() {
		charge -= 1;
		updateQuickslot();
		 curUser.spendAndNext(TIME_TO_ZAP);
	}
}

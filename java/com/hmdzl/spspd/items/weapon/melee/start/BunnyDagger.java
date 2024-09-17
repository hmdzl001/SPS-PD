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
package com.hmdzl.spspd.items.weapon.melee.start;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.BunnyCombo;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.HolyStun;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.NewCombo;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Shocked2;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Beam;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.misc.GunOfSoldier;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BunnyDagger extends MeleeWeapon {
	{
		
		image = ItemSpriteSheet.BUNNY_DAGGER;
		defaultAction = AC_CHOOSE;
		usesTargeting = true;
	}

	protected int collisionProperties =(Ballistica.STOP_TARGET | Ballistica.STOP_TERRAIN);

	public static final String AC_CHOOSE = "CHOOSE";
	public static final String AC_EYE_HIT = "EYE_HIT";
	public static final String AC_BLEED_HIT = "BLEED_HIT";
	public static final String AC_DASH_HIT = "DASH_HIT";

	public BunnyDagger() {
		super(1, 2f, 1f, 1);
		MIN = 5;
		MAX = 10;
		unique = true;
		reinforced = true;
		cursed = true;
	}

	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		BunnyCombo bunnyCombo = Dungeon.hero.buff(BunnyCombo.class);
		if (Dungeon.hero.buff(BunnyCombo.class) != null) {
			if (bunnyCombo.count > 4) {
				actions.add(AC_EYE_HIT);
				actions.add(AC_BLEED_HIT);
				actions.add(AC_DASH_HIT);
			}
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals( AC_CHOOSE )){
			GameScene.show(new WndItem(null, this, true));
		} else if (action.equals(AC_EYE_HIT)) {

			curUser = hero;
			GameScene.selectCell( eyehit );
			//GameScene.selectItem(itemSelector, WndBag.Mode.HOLY_MACE , Messages.get(this, "prompt2"));

		} else if (action.equals(AC_BLEED_HIT)) {
			curUser = hero;
			GameScene.selectCell( bleedhit );

		} else if (action.equals(AC_DASH_HIT)) {
			curUser = hero;
			curItem = this;
			GameScene.selectCell( dashhit );
		} else {

			super.execute(hero, action);

		}
	}
	
	@Override
	public Item upgrade(boolean enchant) {
		MIN += 1;
		MAX += 1;
		return super.upgrade(enchant);
	}

	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
        int exdmg = Dungeon.hero.damageRoll();
		defender.damage(Random.Int(exdmg/2,exdmg), this,3);

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	
	}

	private CellSelector.Listener eyehit = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
			if (cell == null) return;
			final Char enemy = Actor.findChar( cell );
			if (enemy == null || !Dungeon.hero.canAttack(enemy) || Dungeon.hero.isCharmedBy( enemy )){
				GLog.w( Messages.get(NewCombo.class, "bad_target") );
			} else {
				Dungeon.hero.sprite.attack(cell, new Callback() {
					@Override
					public void call() {
						BunnyCombo bunnyCombo = Dungeon.hero.buff(BunnyCombo.class);
						Buff.affect(enemy, Blindness.class,5f);
						Buff.affect(enemy, BeOld.class).set( 5 + bunnyCombo.count);
						int dmg = skilldamage();
						enemy.damage( dmg, this,1 );

						if (Dungeon.hero.buff(BunnyCombo.class) != null) {
							bunnyCombo.count -= 4;
						}
					}
				});
			}
		}

		@Override
		public String prompt() {
			return Messages.get(GunOfSoldier.class, "prompt");
		}
	};

	private CellSelector.Listener bleedhit = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
			if (cell == null) return;
			final Char enemy = Actor.findChar( cell );
			if (enemy == null || !Dungeon.hero.canAttack(enemy) || Dungeon.hero.isCharmedBy( enemy )){
				GLog.w( Messages.get(NewCombo.class, "bad_target") );
			} else {
				Dungeon.hero.sprite.attack(cell, new Callback() {
					@Override
					public void call() {
						BunnyCombo bunnyCombo = Dungeon.hero.buff(BunnyCombo.class);

						Buff.affect(enemy, Cripple.class,5f);
						Buff.affect(enemy, Bleeding.class).set(bunnyCombo.count*3);
						int dmg = skilldamage();
						enemy.damage( dmg, this,1 );

						if (Dungeon.hero.buff(BunnyCombo.class) != null) {
							bunnyCombo.count -=4;
						}
					}
				});
			}
		}

		@Override
		public String prompt() {
			return Messages.get(GunOfSoldier.class, "prompt");
		}
	};

	protected static CellSelector.Listener dashhit = new  CellSelector.Listener() {

		@Override
		public void onSelect( Integer target ) {

			if (target != null) {

				final BunnyDagger curWand = (BunnyDagger) BunnyDagger.curItem;

				final Ballistica shot = new Ballistica( curUser.pos, target, curWand.collisionProperties);
				int cell = shot.collisionPos;

				if (target == curUser.pos || cell == curUser.pos) {
					GLog.i( Messages.get(Wand.class, "self_target") );
					return;
				}

				curUser.sprite.zap(cell);

				curUser.busy();
				curWand.fx(shot, new Callback() {
						@Override
						public void call() {
							curWand.onZap(shot);
							curWand.wandUsed();
							curUser.pos = shot.collisionPos;
							curUser.sprite.place(curUser.pos);
							//curUser.sprite.visible = Dungeon.visible[shot.collisionPos];
						}
					});

					Invisibility.dispel();



			}
		}

		@Override
		public String prompt() {
			return Messages.get(EleKatana.class, "prompt");
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

			int dmg = skilldamage();
			dmg -= Random.IntRange( 0, ch.drRoll() );
			ch.damage( dmg, this,1 );
			Buff.affect(ch, Silent.class,5f);
			Buff.affect(ch, Locked.class,5f);
			Buff.affect(ch, Terror.class,5f);
			Buff.affect(ch, Vertigo.class,5f);

			ch.sprite.centerEmitter().burst( PurpleParticle.BURST, Random.IntRange( 1, 2 ) );
			ch.sprite.flash();
		}
	}
	protected void fx(Ballistica beam, Callback callback) {
		curUser.sprite.parent.add(
				new Beam.LightRay(curUser.sprite.center(), DungeonTilemap.tileCenterToWorld(beam.collisionPos)));
		callback.call();
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	protected void wandUsed() {
		BunnyCombo bunnyCombo = Dungeon.hero.buff(BunnyCombo.class);
		if (Dungeon.hero.buff(BunnyCombo.class) != null) {
			bunnyCombo.count -=4;
		}
		updateQuickslot();
		curUser.spendAndNext(1f);
	}

	protected int skilldamage () {
		return  Random.IntRange(MIN,MAX);
	}

}

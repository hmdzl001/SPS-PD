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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.BloodImbue;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.BunnyCombo;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.FrostImbue;
import com.hmdzl.spspd.actors.buffs.HolyStun;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.NewCombo;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.GreatRune;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.Torch;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.items.misc.GunOfSoldier;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.AttackIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BunnySpanner extends MeleeWeapon {
	{
		
		image = ItemSpriteSheet.BUNNY_SPANNER;
		defaultAction = AC_CHOOSE;
		usesTargeting = true;
	}

	private int targetPos;

	public static final String AC_CHOOSE = "CHOOSE";
	public static final String AC_FIRST_SMASH = "FIRST_SMASH";
	public static final String AC_FINAL_SMASH = "FINAL_SMASH";
	public static final String AC_THROW_SMASH = "THROW_SMASH";

	public BunnySpanner() {
		super(1, 2f, 1.5f, 2);
		MIN = 8;
		MAX = 15;
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
			if (bunnyCombo.count > 0 && bunnyCombo.count <5 ) {
				actions.add(AC_FIRST_SMASH);
			}
			if (bunnyCombo.count > 1){
				actions.add(AC_FINAL_SMASH);
				actions.add(AC_THROW_SMASH);
			}
		}
		return actions;
	}
	
	@Override
	public Item upgrade(boolean enchant) {
		MIN += 2;
		MAX += 3;
		return super.upgrade(enchant);
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals( AC_CHOOSE )){
			GameScene.show(new WndItem(null, this, true));
		} else if (action.equals(AC_FIRST_SMASH)) {

			curUser = hero;
			GameScene.selectCell( firstsmash );
			//GameScene.selectItem(itemSelector, WndBag.Mode.HOLY_MACE , Messages.get(this, "prompt2"));

		} else if (action.equals(AC_FINAL_SMASH)) {
			curUser = hero;
			GameScene.selectCell( finalsmash );

		} else if (action.equals(AC_THROW_SMASH)) {
			curUser = hero;
			curItem = this;
			GameScene.selectCell( shooter );
		} else {

			super.execute(hero, action);

		}
	}

	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
        if (Random.Int(100) < 30) {
			Buff.prolong(defender, Paralysis.class, 2);
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	
	}

	public BunnySpanner.BunnyAmmo Ammo(){
		return new BunnySpanner.BunnyAmmo();
	}

	public class BunnyAmmo extends MissileWeapon {

		{
			image = ItemSpriteSheet.BUNNY_SPANNER;
			ACU = 1000f;
			DLY = 3f;
		}

		public int damageRoll(Hero owner) {
			return skilldamage();
		}

		@Override
		protected void onThrow( int cell ) {
			Char enemy = Actor.findChar( cell );
			if (enemy == null || enemy == curUser) {
				parent = null;
				Splash.at( cell, 0xCC99FFFF, 1 );
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
				}
			}
		}

		@Override
		public void proc(Char attacker, Char defender, int damage) {

			int oppositeDefender = defender.pos + (defender.pos - attacker.pos);
			Ballistica trajectory = new Ballistica(defender.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
			WandOfFlow.throwChar(defender, trajectory, 2);
			Buff.prolong(defender, Paralysis.class,3f);

			Buff.detach(attacker,BunnyCombo.class);

			super.proc(attacker, defender, damage);
		}

		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			BunnySpanner.this.targetPos = cell;
			super.cast(user, dst);
		}

	}
	private CellSelector.Listener shooter = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				Ammo().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(GunOfSoldier.class, "prompt");
		}
	};

	private CellSelector.Listener firstsmash = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
			if (cell == null) return;
			final Char enemy = Actor.findChar( cell );
			if (enemy == null || Floor.distance(Dungeon.hero.pos, enemy.pos) > 2 || Dungeon.hero.isCharmedBy( enemy )){
				GLog.w( Messages.get(NewCombo.class, "bad_target") );
			} else {
				Dungeon.hero.sprite.attack(cell, new Callback() {
					@Override
					public void call() {
						int dmg = skilldamage()/2;
						enemy.damage( dmg, this,1 );
						Buff.affect(enemy, HolyStun.class,2f);
						BunnyCombo bunnyCombo = Dungeon.hero.buff(BunnyCombo.class);
						if (Dungeon.hero.buff(BunnyCombo.class) != null) {
							bunnyCombo.count = 5;
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

	private CellSelector.Listener finalsmash = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
			if (cell == null) return;
			final Char enemy = Actor.findChar( cell );
			if (enemy == null || Floor.distance(Dungeon.hero.pos, enemy.pos) > 2 || Dungeon.hero.isCharmedBy( enemy )){
				GLog.w( Messages.get(NewCombo.class, "bad_target") );
			} else {
				Dungeon.hero.sprite.attack(cell, new Callback() {
					@Override
					public void call() {
						BunnyCombo bunnyCombo = Dungeon.hero.buff(BunnyCombo.class);
						int dmg = skilldamage()/2 * bunnyCombo.count;

						enemy.damage( dmg, this,1 );

						Buff.detach(Dungeon.hero,BunnyCombo.class);

					}
				});
			}
		}

		@Override
		public String prompt() {
			return Messages.get(GunOfSoldier.class, "prompt");
		}
	};

	protected int skilldamage () {
		return  Random.IntRange(MIN,MAX);
	}

}

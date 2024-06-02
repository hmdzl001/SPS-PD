/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.hmdzl.spspd.items;

import android.annotation.SuppressLint;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;

import java.util.ArrayList;

public class TransmutationBall extends Item {
	
	private static final String AC_USE = "USE";
	
	{
		image = ItemSpriteSheet.TRAN_BALL;
		
	    stackable = true;

		defaultAction = AC_USE;
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_USE);
		return actions;
	}

	@SuppressLint("SuspiciousIndentation")
	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_USE)) {

			curUser = hero;
            GameScene.selectItem(itemSelector,
			WndBag.Mode.TRANMSUTABLE, 
			Messages.get(TransmutationBall.class, "prompt"));

		} else {

			super.execute(hero, action);

		}
	}	
		
	private void use(Item item) {
  
		detach(curUser.belongings.backpack);

		curUser.sprite.operate(curUser.pos);
		//curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 0);
		curUser.spend(1f);
		curUser.busy();
		
	}
		
		
	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			curUser = Dungeon.hero;
			Item result;
			if (item != null) {
				if (item instanceof Weapon) {
					result = changeWeapon((Weapon) item);
				} else if (item instanceof Armor) {
					result = changeArmor((Armor) item);
				} else if (item instanceof Ring) {
					result = changeRing((Ring) item);
				} else if (item instanceof Wand) {
					result = changeWand((Wand) item);
				} else if (item instanceof Artifact) {
					result = changeArtifact((Artifact) item);
				} else if (item instanceof StrBottle) {
					result = new MitBottle();
				} else {
					result = null;
				}
				item.detach(Dungeon.hero.belongings.backpack);
				Dungeon.depth.drop(result, Dungeon.hero.pos).sprite.drop();
				TransmutationBall.this.use(item);
			}
		}
	  };	

	
		private Weapon changeWeapon(Weapon w) {
		Weapon n;
		do {
			n = (Weapon) Generator.random(Generator.Category.MELEEWEAPON);
		} while (n.getClass() == w.getClass());
		n.level = 0;
		int level = w.level;
		if (level > 0) {
			n.upgrade(level);
		} else if (level < 0) {
			n.degrade(-level);
		}
		n.enchantment = w.enchantment;
		n.reinforced = w.reinforced;
		n.levelKnown = w.levelKnown;
		n.cursedKnown = w.cursedKnown;
		n.cursed = w.cursed;
		return n;
	}


		private Armor changeArmor(Armor r) {
			Armor n;
			do {
				n = (Armor) Generator.random(Generator.Category.ARMOR);
			} while (n.getClass() == r.getClass());

			n.level = 0;

			int level = r.level;
			if (level > 0) {
				n.upgrade(level);
			} else if (level < 0) {
				n.degrade(-level);
			}
			n.glyph = r.glyph;
			n.reinforced = r.reinforced;
			n.levelKnown = r.levelKnown;
			n.cursedKnown = r.cursedKnown;
			n.cursed = r.cursed;

			return n;
		}


		private Ring changeRing(Ring r) {
			Ring n;
			do {
				n = (Ring) Generator.random(Generator.Category.RING);
			} while (n.getClass() == r.getClass());

			n.level = 0;

			int level = r.level;
			if (level > 0) {
				n.upgrade(level);
			} else if (level < 0) {
				n.degrade(-level);
			}
			n.reinforced = r.reinforced;
			n.levelKnown = r.levelKnown;
			n.cursedKnown = r.cursedKnown;
			n.cursed = r.cursed;

			return n;
		}

		private Artifact changeArtifact(Artifact a) {
			Artifact n;
			do {
				n = (Artifact) Generator.random(Generator.Category.ARTIFACT);
			} while (n.getClass() == a.getClass());

			if (n != null) {
				n.cursedKnown = a.cursedKnown;
				n.cursed = a.cursed;
				n.levelKnown = a.levelKnown;
				n.transferUpgrade(a.visiblyUpgraded());
			}

			return n;
		}

		private Wand changeWand(Wand w) {

			Wand n;
			do {
				n = (Wand) Generator.random(Generator.Category.WAND);
			} while (n.getClass() == w.getClass());

			n.level = 0;
			n.updateLevel();
			n.upgrade(w.level);

			n.reinforced = w.reinforced;
			n.levelKnown = w.levelKnown;
			n.cursedKnown = w.cursedKnown;
			n.cursed = w.cursed;

			return n;
		}

	
	@Override
	public int price() {
		return  50;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}	
}

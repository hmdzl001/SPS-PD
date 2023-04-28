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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.scrolls.ScrollOfRemoveCurse;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndOptions;

import java.util.ArrayList;

public class GreatRune extends Item {

	private static final float TIME_TO_INSCRIBE = 2;

	private static final String AC_INSCRIBE = "INSCRIBE";

	{
		//name = "greater arcane stylus";
		image = ItemSpriteSheet.GREATRUNE;

		stackable = true;

		 
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_INSCRIBE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_INSCRIBE) {

			curUser = hero;
			detach(curUser.belongings.backpack);
			GameScene.selectItem(itemSelector,WndBag.Mode.ENCHANTABLE ,Messages.get(this, "prompt"));

		} else {

			super.execute(hero, action);

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
		return 30 * quantity;
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {

		@Override
		public void onSelect(final Item item) {
			
			if (item instanceof Weapon){
				
				final Weapon.Enchantment enchants1 = Weapon.Enchantment.random();
				final Weapon.Enchantment enchants2 = Weapon.Enchantment.random();
				final Weapon.Enchantment enchants3 = Weapon.Enchantment.random();

				GameScene.show(new WndOptions(Messages.titleCase(Messages.get(GreatRune.class, "name")),
						Messages.get(GreatRune.class, "weapon") +
						"\n\n" +
						Messages.get(GreatRune.class, "cancel_warn"),
						enchants1.name(null),
						enchants2.name(null),
						enchants3.name(null),
						Messages.get(GreatRune.class, "cancel")){
					
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							((Weapon) item).enchant(enchants1);
						}else if (index == 1) {
							((Weapon) item).enchant(enchants2);
						}else if(index == 2){
							((Weapon) item).enchant(enchants3);
						}
						ScrollOfRemoveCurse.uncurse(Dungeon.hero, item);
						item.identify();
						GLog.p(Messages.get(GreatRune.class, "item"));
					}

					@Override
					public void onBackPressed() {
						//do nothing, reader has to cancel
					}
				});
			
			} else if (item instanceof Armor) {
				
				final Armor.Glyph glyphs1 = Armor.Glyph.random();
				final Armor.Glyph glyphs2 = Armor.Glyph.random();
				final Armor.Glyph glyphs3 = Armor.Glyph.random();
				
				GameScene.show(new WndOptions(
						Messages.titleCase(Messages.get(GreatRune.class, "name")),
						Messages.get(GreatRune.class, "armor") +
						"\n\n" +
						Messages.get(GreatRune.class, "cancel_warn"),
						glyphs1.name(null),
						glyphs2.name(null),
						glyphs3.name(null),
						Messages.get(GreatRune.class, "cancel")){
					
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							((Armor) item).inscribe(glyphs1);
						}else if (index == 1) {
							((Armor) item).inscribe(glyphs2);
						}else if(index == 2) {
							((Armor) item).inscribe(glyphs3);
						}
						ScrollOfRemoveCurse.uncurse(Dungeon.hero, item);
						item.identify();
						GLog.p(Messages.get(GreatRune.class, "item"));
						}

					@Override
					public void onBackPressed() {
						//do nothing, reader has to cancel
					}
				});
			} 
			}

	};
}

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
package com.hmdzl.spspd.change.actors.mobs.npcs;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.items.AdamantArmor;
import com.hmdzl.spspd.change.items.AdamantRing;
import com.hmdzl.spspd.change.items.AdamantWand;
import com.hmdzl.spspd.change.items.AdamantWeapon;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.quest.DarkGold;
import com.hmdzl.spspd.change.items.rings.Ring;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ElectricwelderSprite;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBlacksmith2;
import com.hmdzl.spspd.change.windows.WndQuest;

public class Blacksmith2 extends NPC {


	private static final String TXT_LOOKS_BETTER = "your %s pulsates with magical energy. ";
	private static final String TXT_GET_LOST = "I'm busy. Get lost!";
	private static final String TXT2 = "My brother and I make all the items in this dungeon. "
			                          +"He melts down two upgraded items to enhance one of them. "
			                          +"My specialty is reinforcing items with adamantite. "
			                          +"Come back to me when you have 50 dark gold and some adamantite for me to work with. " ;
	
	private static final String TXT3 = "Oh ho! Looks like you have some adamantite there. "
                                     +"I can reinforce an item with adamantite if you wish. "
                                     +"Reinforced items can handle higher levels of magical upgrade. "
                                     +"It'll cost you though!. "
                                     +"Come back to me when you have 50 dark gold. " ;
	
	

	{
		//name = "troll blacksmith named Bip";
		spriteClass = ElectricwelderSprite.class;
		properties.add(Property.TROLL);
        properties.add(Property.IMMOVABLE);
	}
	

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}

	@Override
	public boolean interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
		
		
		DarkGold gold = Dungeon.hero.belongings.getItem(DarkGold.class);
		if (!checkAdamant()) {
			tell(Messages.get(Blacksmith2.class, "himself"));
		} else if (gold == null || gold.quantity() < 50) {
			tell(Messages.get(Blacksmith2.class, "adamantite"));
		} else if (checkAdamant() && gold != null && gold.quantity() > 49){
		GameScene.show(new WndBlacksmith2(this, Dungeon.hero));
		} else {
			tell(Messages.get(Blacksmith2.class, "himself"));
		}
		return false;
	}

	public static String verify(Item item1, Item item2) {
	
		if (item1 == item2) {
			return Messages.get(Blacksmith2.class, "same_item");
		}

		if (!item1.isIdentified()) {
			return Messages.get(Blacksmith2.class, "un_ided");
		}

		if (item1.cursed) {
			return Messages.get(Blacksmith2.class, "cursed");
		}
		
		if (item1.reinforced) {
			return Messages.get(Blacksmith2.class, "already_reforge");
		}

		if (item1.level < 0) {
			return Messages.get(Blacksmith2.class, "degraded");
		}

		if (!item1.isUpgradable()) {
			return Messages.get(Blacksmith2.class, "cant_reforge");
		}
		
		if(item1 instanceof Armor && item2 instanceof AdamantArmor){
			return null;			
		}
		
		if(item1 instanceof MeleeWeapon && item2 instanceof AdamantWeapon){
			return null;
		}

		if(item1 instanceof GunWeapon && item2 instanceof AdamantWeapon){
			return null;
		}
		
		if(item1 instanceof Wand && item2 instanceof AdamantWand){
			return null;
		}
		
		if(item1 instanceof Ring && item2 instanceof AdamantRing){
			return null;
		}
		
		return Messages.get(Blacksmith2.class, "cant_work");
		
	}
	
	public static void upgrade(Item item1, Item item2) {
		
		item1.reinforced=true;
		item2.detach(Dungeon.hero.belongings.backpack);
		DarkGold gold = Dungeon.hero.belongings.getItem(DarkGold.class);
		if (gold != null && gold.quantity() > 49 ) {
			gold.detach(Dungeon.hero.belongings.backpack,50);
			if(!(Dungeon.hero.belongings.getItem(DarkGold.class).quantity() > 0)){
				gold.detachAll(Dungeon.hero.belongings.backpack);
			}
		}		
		
		GLog.p(TXT_LOOKS_BETTER, item1.name());
		Dungeon.hero.spendAndNext(2f);
		Badges.validateItemLevelAquired(item1);
		
	}
	
	
	private void tell(String text) {
		GameScene.show(new WndQuest(this, text));		
	}

	
	public static boolean checkAdamant() {
		AdamantArmor armor1 = Dungeon.hero.belongings.getItem(AdamantArmor.class);
		AdamantWeapon weapon1 = Dungeon.hero.belongings.getItem(AdamantWeapon.class);
		AdamantRing ring1 = Dungeon.hero.belongings.getItem(AdamantRing.class);
		AdamantWand wand1 = Dungeon.hero.belongings.getItem(AdamantWand.class);
		
		if(armor1!=null ||  weapon1!=null || ring1!=null || wand1!=null) {
			return true;
		}
		   return false;		
	}
	
	
	

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}
	
}

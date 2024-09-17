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
package com.hmdzl.spspd.actors.mobs.giftnpc;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.TestCloak;
import com.hmdzl.spspd.items.armor.specialarmor.TestArmor;
import com.hmdzl.spspd.items.artifacts.AlienBag;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.eggs.CocoCatEgg;
import com.hmdzl.spspd.items.food.completefood.NutCake;
import com.hmdzl.spspd.items.potions.PotionOfMixing;
import com.hmdzl.spspd.items.wands.WandOfTest;
import com.hmdzl.spspd.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.items.weapon.spammo.GoldAmmo;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CoconutSprite;
import com.hmdzl.spspd.windows.WndNewNpcMessage;

import static com.hmdzl.spspd.Dungeon.hero;

public class GiftCoconut extends GiftNpc {

	{
		//name = Messages.get(this,"name");
		spriteClass = CoconutSprite.class;
		properties.add(Property.MECH);
		properties.add(Property.BEAST);
		
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public boolean loveitem(Item item) {
		return item instanceof NutCake || item instanceof AlienBag ||
				item instanceof CocoCatEgg || item instanceof GoldAmmo ||
				item instanceof PotionOfMixing ||
				item instanceof TestArmor || item instanceof TestWeapon || item instanceof WandOfTest || item instanceof TestCloak;
	}


	@Override
	public Item SupercreateLoot(){
		return new DungeonBomb();
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public void givereward(NPC npc) {
		this.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.2f, 5);
		switch (this.FRIEND) {
			case 30 :
				hero.hitSkill++;
				friend(this, Messages.get(this, "reward2"));
				break;

			case 50 :
				hero.evadeSkill++;
				friend(this, Messages.get(this, "reward3"));
				break;

			case 70 :
				hero.magicSkill++;
				friend(this, Messages.get(this, "reward4"));
				break;

			case 100 :
				hero.STR++;
				friend(this, Messages.get(this, "reward5"));
				break;

			default:
				if (this.FRIEND % 40 == 0){
					Dungeon.depth.drop(new DungeonBomb(), hero.pos).sprite.drop();
					friend(this, Messages.get(this, "reward1"));
				} else {
					friend(this, Messages.get(this, "thank1"));
				}
				break;
		}

	}

	private void friend(NPC npc,String text ) {
		GameScene.show(new WndNewNpcMessage(npc,text));
	}


}

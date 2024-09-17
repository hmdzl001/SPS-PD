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
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.fruit.Fruit;
import com.hmdzl.spspd.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.ReNepenth;
import com.hmdzl.spspd.plants.Seedpod;
import com.hmdzl.spspd.plants.StarEater;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.FarmerSprite;
import com.hmdzl.spspd.windows.WndNewNpcMessage;

public class GiftFruitWorker extends GiftNpc {

	{
		//name = Messages.get(this,"name");
		spriteClass = FarmerSprite.class;
		properties.add(Property.MECH);
		
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public boolean loveitem(Item item) {
		return item instanceof Vegetable ||
				item instanceof Fruit;
	}


	@Override
	public Item SupercreateLoot(){
		return new ActiveMrDestructo();
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public void givereward(NPC npc) {
		this.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.2f, 5);
		switch (this.FRIEND) {
			case 100 :
				Dungeon.depth.drop(new ReNepenth.Seed(),Dungeon.hero.pos).sprite.drop();
				Dungeon.depth.drop(new StarEater.Seed(),Dungeon.hero.pos).sprite.drop();
				Dungeon.depth.drop(new Seedpod.Seed(),Dungeon.hero.pos).sprite.drop();
				friend(this, Messages.get(this, "reward2"));
				break;

			default:
				if (this.FRIEND % 40 == 0){
					Dungeon.depth.drop(Generator.random(Generator.Category.HIGHFOOD),Dungeon.hero.pos).sprite.drop();
					friend(this, Messages.get(this, "reward1"));
				} else  {
					friend(this, Messages.get(this, "thank1"));
				}
				break;
		}

	}

	private void friend(NPC npc,String text ) {
		GameScene.show(new WndNewNpcMessage(npc,text));
	}

}

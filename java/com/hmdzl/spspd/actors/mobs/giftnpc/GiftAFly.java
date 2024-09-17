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
import com.hmdzl.spspd.items.food.completefood.AflyFood;
import com.hmdzl.spspd.items.food.completefood.CompleteFood;
import com.hmdzl.spspd.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.weapon.melee.special.AFlySock;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.AFlySprite;
import com.hmdzl.spspd.windows.WndNewNpcMessage;

public class GiftAFly extends GiftNpc {

	{
		//name = Messages.get(this,"name");
		spriteClass = AFlySprite.class;
		properties.add(Property.ELF);
		
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public boolean loveitem(Item item) {
		return item instanceof CompleteFood;
	}


	@Override
	public Item SupercreateLoot(){
		return new AFlySock();
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
				Dungeon.depth.drop(new AflyFood(),Dungeon.hero.pos).sprite.drop();
				Dungeon.depth.drop(new AflyFood(),Dungeon.hero.pos).sprite.drop();
				Dungeon.depth.drop(new AflyFood(),Dungeon.hero.pos).sprite.drop();
                friend(this, Messages.get(this, "reward2"));
				break;

			default:
				if (this.FRIEND % 30 == 0){
					Dungeon.depth.drop(new ScrollOfPsionicBlast(),Dungeon.hero.pos).sprite.drop();
					Dungeon.depth.drop(new PotionOfMindVision(),Dungeon.hero.pos).sprite.drop();
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

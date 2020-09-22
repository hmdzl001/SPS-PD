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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.items.potions.Potion;

import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Seedpod;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class UpgradeEatBall extends Item {
	
	private static final String AC_USE = "USE";
	
	{
		image = ItemSpriteSheet.UP_EATER;
		
	    stackable = true;
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_USE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_USE) {
			curUser = hero;
            GameScene.selectItem(itemSelector,
			WndBag.Mode.EATABLE,
			Messages.get(UpgradeEatBall.class, "prompt"));

		} else {

			super.execute(hero, action);

		}
	}	
		
	private void use(Item item) {
        if (!(Dungeon.hero.heroClass == HeroClass.FOLLOWER ) || (Dungeon.hero.heroClass == HeroClass.FOLLOWER && Random.Int(10)>=1 ))
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
		        if (item.isUpgradable()) {
					result = eatUpgradable((Item) item);
		        } else if (item instanceof Scroll
				      || item instanceof Potion
				      || item instanceof Stylus) {
					result = eatStandard((Item) item);
		        } else {
					result = null;
				}
				item.detach(Dungeon.hero.belongings.backpack);
				Dungeon.level.drop(result, Dungeon.hero.pos).sprite.drop();
				UpgradeEatBall.this.use(item);
			}
		}
	  };	

		private Item eatUpgradable(Item w) {

		int ups = w.level;
		
		Item n = null;

		if (Random.Float()<(ups/10)){
			
			n = new UpgradeBlobViolet();
			
		} else if (Random.Float()<(ups/5)) {
			
			n =  new UpgradeBlobRed();
			
        } else if (Random.Float()<(ups/3)) {
			
			n =  new UpgradeBlobYellow();
		
		} else {
			
			n =new Seedpod.Seed() ;
		}
		
		return n;
	}
	
	private Item eatStandard(Item w) {

		Item n = null;
        
		if (Random.Float()<0.1f){
			n = new UpgradeBlobYellow();
		} else {
			n = new Seedpod.Seed() ;
		}
		
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

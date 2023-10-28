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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.journalpages.JournalPage;
import com.hmdzl.spspd.items.journalpages.SafeSpotPage;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndOtiluke;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class DolyaSlate extends Item {
	
	public final float TIME_TO_USE = 1;
	public final int fullCharge = 1000;
	
	
	public static final String AC_RETURN = "RETURN";
	public static final String AC_ADD = "ADD";
	public static final String AC_PORT = "READ";

	protected WndBag.Mode mode = WndBag.Mode.JOURNALPAGES;

	
	public int returnDepth = -1;
	public int returnPos;
	
	public int charge = 0;
	
	public int reqCharges(){
			
		int calcCharges = Math.round(fullCharge);
		return calcCharges;
		
	}
	

	public boolean[] rooms = new boolean[10];	
	public boolean[] firsts = new boolean[10];	
		
	{
		//name = "Otiluke's journal";
		image = ItemSpriteSheet.OTILUKES_JOURNAL;

		stackable= true;
		unique = true;
		
		//rooms[0] = true;
		//firsts[0] = true;
	}
		
	private static final String DEPTH = "depth";
	private static final String POS = "pos";
	private static final String ROOMS = "rooms";
	private static final String FIRSTS = "firsts";
	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DEPTH, returnDepth);
		bundle.put(ROOMS, rooms);
		bundle.put(CHARGE, charge);
		bundle.put(FIRSTS, firsts);
		if (returnDepth != -1) {
			bundle.put(POS, returnPos);
		}
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		returnDepth = bundle.getInt(DEPTH);
		returnPos = bundle.getInt(POS);
		charge = bundle.getInt(CHARGE);
		rooms = bundle.getBooleanArray(ROOMS);
		firsts = bundle.getBooleanArray(FIRSTS);
	}

	@Override
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)) {
            Dungeon.level.drop(new SafeSpotPage().identify(),hero.pos);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);

		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		actions.add(AC_ADD);
		
		if (returnDepth > 0 && (Dungeon.depth<56 || Dungeon.depth==66 || Dungeon.depth==67 || Dungeon.depth==68) && Dungeon.depth>49 ){
		actions.add(AC_RETURN);
		}

		if ((charge >= 500 || Badges.checkOtilukeRescued() )&& Dungeon.depth<26){
		actions.add(AC_PORT);
		}
				
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_PORT)) {
			PocketBallFull.removePet(hero);
			if (Dungeon.bossLevel()) {
				hero.spend(TIME_TO_USE);
				GLog.w(Messages.get(Item.class, "not_here"));
				return;
			}
		}

		if (action.equals(AC_PORT)) {
			PocketBallFull.removePet(hero);
			GameScene.show(new WndOtiluke(rooms, this));
		}
              
       if (action.equals(AC_RETURN)) {
		   if (Dungeon.depth != 50){
		   PocketBallFull.removePet(hero);
		   }
    	   hero.spend(TIME_TO_USE);
    	       IronKey key = hero.belongings.getKey(IronKey.class, Dungeon.depth);
			   if (key!=null){key.detachAll(Dungeon.hero.belongings.backpack);}
			   updateQuickslot();
			   
				InterlevelScene.mode = InterlevelScene.Mode.RETURN;	
				InterlevelScene.returnDepth = returnDepth;
				InterlevelScene.returnPos = returnPos;
				Game.switchScene(InterlevelScene.class);
				returnDepth=-1;
			}
               
       if (action.equals(AC_ADD)) {

    	   GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));
			
		}		
					
		 else {

			super.execute(hero, action);

		}
	}

	@Override
	public int price() {
		return 300*quantity;
	}
	
	public void reset() {
		returnDepth = -1;
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
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(this, "charge",charge,reqCharges());
		return info;
	}
	
	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof JournalPage) {
				Hero hero = Dungeon.hero;
				int room = ((JournalPage) item).room;
			
				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(2f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);

				item.detach(hero.belongings.backpack);
				GLog.h(Messages.get(DolyaSlate.class,"add_page"));
				level++;
				
				if(charge<(fullCharge-500)){
					charge=fullCharge;
				}  else {
					charge+=500; 
				}
				
				rooms[room] = true;
				firsts[room] = true;
				
		}
	 }
	};

	@Override
	public String status() {
		return Messages.format("%d%%", charge/10);
	}


}

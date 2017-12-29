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
package com.hmdzl.spspd.change.items;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.Water;
import com.hmdzl.spspd.change.actors.buffs.Bless;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.ExitFind;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.Levitation;
import com.hmdzl.spspd.change.actors.buffs.Light;
import com.hmdzl.spspd.change.actors.buffs.MindVision;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Tar;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.items.bags.Bag;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.hmdzl.spspd.change.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class DewVial extends Item {

	private static final int MAX_VOLUME = 120;
	private static final int EXT_VOLUME = 350;
	private static final int BLESS_VOLUME = 20;
	
	
	private static final int MAX_VOLUME(){
		return Dungeon.wings ? EXT_VOLUME : MAX_VOLUME;
	}

	//private static final String AC_SIP = "SIP";
	private static final String AC_DRINK = "DRINK";
	private static final String AC_WATER = "WATER";
	private static final String AC_SPLASH = "SPLASH";
	private static final String AC_BLESS = "BLESS";
	private static final String AC_LIGHT = "LIGHT";
    private static final String AC_POUR = "POUR";
	private static final String AC_PEEK = "PEEK";

	private static final float TIME_TO_DRINK = 1f;
	private static final float TIME_TO_PEEK = 2f;
	private static final float TIME_TO_WATER = 3f;
	protected static final float TIME_TO_BLESS = 1f;

	private static final String TXT_VALUE = "%+dHP";
	private static final String TXT_STATUS = "%d/%d";

	//private static final String TXT_AUTO_DRINK = "The dew vial was emptied to heal your wounds.";
	private static final String TXT_COLLECTED = "You collected a dewdrop into your dew vial.";
	private static final String TXT_WATERED = "The dungeon regrows around you.";
	private static final String TXT_REFRESHED = "You splash the dew on your face.";
	private static final String TXT_BLESSED = "You feel the dew blessing items in your backpack.";
	private static final String TXT_PROCCED = "Your pack glows with a cleansing light, and a malevolent energy disperses.";
	private static final String TXT_NOT_PROCCED = "Your pack glows with a cleansing light, but nothing was uncursed.";
	private static final String TXT_FULL = "Your dew vial is full!";
	private static final String TXT_EMPTY = "Your dew vial is empty!";
	private static final String TXT_LOOKS_BETTER = "your %s certainly looks better now";
	private static final String TXT_SELECT = "Select an item to upgrade";

	{
		//name = "dew vial";
		image = ItemSpriteSheet.VIAL;

		defaultAction = AC_DRINK;

		unique = true;
	}

	private int volume = 0;
	
	public int checkVol () {
		return volume;
	}

	public void setVol (int vol) {
		volume=vol;		
	}
	
	private static final String VOLUME = "volume";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(VOLUME, volume);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		volume = bundle.getInt(VOLUME);
	}
	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (volume > 99) {
			actions.add(AC_DRINK);
					
			if (Dungeon.dewWater || Dungeon.dewDraw){
			actions.add(AC_WATER);
			actions.add(AC_BLESS);
			}   
			if (Dungeon.dewNorn){
			actions.add(AC_POUR);
			actions.add(AC_SPLASH);
			}
			actions.add(AC_LIGHT);
			actions.add(AC_PEEK);
			
		}	
		else if (volume > 49) {
			actions.add(AC_DRINK);
			if (Dungeon.dewWater || Dungeon.dewDraw){
		    actions.add(AC_WATER);
			}
			if (Dungeon.dewNorn){
			actions.add(AC_POUR);
			actions.add(AC_SPLASH);
			}
			actions.add(AC_LIGHT);
			actions.add(AC_PEEK);
		}
		
		else if (volume > 29) {
			actions.add(AC_DRINK);
			if (Dungeon.dewNorn){
			actions.add(AC_SPLASH);
			actions.add(AC_POUR);
			}
			actions.add(AC_LIGHT);
			actions.add(AC_PEEK);
		}
		else if (volume > 1) {
			actions.add(AC_DRINK);
		} 
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		
         if (action.equals(AC_DRINK)) {
			 
			if (volume > 0) {

				int value = 1 + (Dungeon.depth - 1) / 5;
				if (hero.heroClass == HeroClass.HUNTRESS) {
					value++;
				}
				value = volume;
				value = (int) Math.max(hero.HT, value);
				int effect = Math.min(hero.HT - hero.HP, value);
				if (effect > 0) {
					hero.HP += (effect*9/10);
					hero.sprite.emitter().burst(Speck.factory(Speck.HEALING),
							volume > 5 ? 2 : 1);
					hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "value", effect));
				}

				if (volume < 10) {

					volume = 0;

				} else {

					volume = volume - 10;
				}

				hero.spend(TIME_TO_DRINK);
				hero.busy();

				Sample.INSTANCE.play(Assets.SND_DRINK);
				hero.sprite.operate(hero.pos);

				updateQuickslot();

			} else {
				GLog.w(Messages.get(this, "empty"));
			}

		} else if (action.equals(AC_WATER)) {
			
			Statistics.waters++;			
			int positive = 0;
			int negative = 0;

			int distance = 1 + positive + negative;

			if (distance <= 0) {
				level /= 2 - distance;
				distance = 1;
			}

			int cx = hero.pos % Level.getWidth();
			int cy = hero.pos / Level.getWidth();
			int ax = cx - distance;
			if (ax < 0) {
				ax = 0;
			}
			int bx = cx + distance;
			if (bx >= Level.getWidth()) {
				bx = Level.getWidth() - 1;
			}
			int ay = cy - distance;
			if (ay < 0) {
				ay = 0;
			}
			int by = cy + distance;
			if (by >= Level.HEIGHT) {
				by = Level.HEIGHT - 1;
			}

			
			for (int y = ay; y <= by; y++) {
				for (int x = ax, p = ax + y * Level.getWidth(); x <= bx; x++, p++) {

					if (Dungeon.visible[p]) {
						int c = Dungeon.level.map[p];
						
						if (c == Terrain.GRASS) {
							GameScene.add(Blob.seed(p, (2) * 20, Water.class));
						}
					}
				}
			}
			volume = volume - 15;
			GLog.i(Messages.get(this, "watered"));
			hero.sprite.operate(hero.pos);
			hero.busy();
			hero.spend(TIME_TO_WATER);
		
		} else if (action.equals(AC_SPLASH)) {	
			Buff.affect(hero, Haste.class, Haste.DURATION);
			//Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
			if(Dungeon.wings && Dungeon.depth<51 ){
				Buff.affect(hero, Levitation.class, Levitation.DURATION);
			    GLog.i(Messages.get(this, "fly"));
			}
			//GLog.i(Messages.get(this, "invisible"));
			GLog.i(Messages.get(this, "fast"));
			volume = volume - 15;
			
		} else if (action.equals(AC_BLESS) && !Dungeon.dewDraw) {	

			boolean procced = uncurse(hero, hero.belongings.backpack.items.toArray(new Item[0]));
			procced = uncurse(hero, hero.belongings.weapon,
					hero.belongings.armor, hero.belongings.misc1,
					hero.belongings.misc2, hero.belongings.misc3)
					|| procced;
			
			if (procced) {
				GLog.p(Messages.get(this, "remove_curse"));
			} else {
				GLog.i(Messages.get(this, "curse"));
			}
													
			volume = volume - 60;	
			
		} else if (action.equals(AC_BLESS) && Dungeon.dewDraw) {	

			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.UPGRADEDEW,	Messages.get(this, "select"));
													
		} else if (action.equals(AC_LIGHT)) {	
			Buff.affect(hero, Light.class, 100f);
			Buff.affect(hero, Invisibility.class, 20f);
			GLog.i(Messages.get(this, "invisible"));
			GLog.i(Messages.get(this, "light"));
			volume = volume - 10;
							
		} else if (action.equals(AC_POUR)) {
             Buff.detach(hero, Burning.class);
             Buff.detach(hero, Ooze.class);
             Buff.detach(hero, Tar.class);
             Buff.detach(hero, Weakness.class);
             Buff.detach(hero, Vertigo.class);

             if (Random.Int(8) == 0) {
                 volume = volume - 20;
                 Buff.affect(hero, Bless.class, 30f);
                 hero.sprite.operate(hero.pos);
                 hero.busy();
                 hero.spend(TIME_TO_WATER);
             } else { volume = volume - 5;
                 hero.sprite.operate(hero.pos);
                 hero.busy();
                 hero.spend(TIME_TO_WATER);}


		} else if (action.equals(AC_PEEK)) {
			Buff.affect(hero, MindVision.class, 2f);
			Buff.affect(hero, ExitFind.class, 2f);
			volume = volume - 5;
			hero.sprite.operate(hero.pos);
			hero.busy();
			hero.spend(TIME_TO_PEEK);
		} else {

			super.execute(hero, action);

		}
	}

	public static boolean uncurse(Hero hero, Item... items) {
		
		
        int levelLimit = Math.max(5, 5+Math.round(Statistics.deepestFloor/3));
        if (hero.heroClass == HeroClass.MAGE){levelLimit++;}
        
        float lvlchance = 0.33f;
        if (hero.heroClass == HeroClass.MAGE){lvlchance = 0.38f;}
        
        boolean procced = false;
		boolean proccedUp = false;
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (item != null && item.cursed) {
				item.uncurse();
				if(item.level<0){item.upgrade(-item.level);} //upgrade to even
				if (item.cursed==false) {procced = true;}
				hero.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10);
			}
			
			if (item != null && Random.Float()<lvlchance && item.isUpgradable() && item.level < levelLimit){
			    item.upgrade();
			    proccedUp = true;
			    hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
			    //GLog.p(Messages.get(DewVial.class, "looks_better",item.name()));
			    Badges.validateItemLevelAquired(item);
			}
			
			if (item instanceof Bag) {
				for (Item bagItem: ((Bag)item).items){
                   if (bagItem != null && bagItem.cursed) {
                	   bagItem.uncurse();
                	   if(bagItem.level<0){bagItem.upgrade(-bagItem.level);}
                	   if (bagItem.cursed==false) {procced = true;}
                   }
                   
                   if (bagItem != null && Random.Float()<lvlchance && bagItem.isUpgradable() && bagItem.level < levelLimit){
                	   bagItem.upgrade();
					    proccedUp = true;
					    hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
					    //GLog.p(Messages.get(DewVial.class, "looks_better", bagItem.name()));
					    Badges.validateItemLevelAquired(bagItem);
					}
				}   
			}			
		}
		
		if (proccedUp){GLog.i(Messages.get(DewVial.class, "blessed"));}
					
		return procced;
	}
			
  private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				upgrade(item);
				volume = volume - 70;
			}
		}
	};
	
	private void upgrade(Item item) {

		//GLog.w(Messages.get(this, "looks_better", item.name()));

		item.upgrade();item.identify();
		
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
		Badges.validateItemLevelAquired(item);
		
		curUser.busy();
		
	}

	
	public void empty() {
		volume = volume - 10;
		updateQuickslot();
	}
	
	public void sip() {
		volume = volume - 1;
		updateQuickslot();
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	public boolean isFullBless() {
		return volume >= BLESS_VOLUME;
	}
	

	public boolean isFull() {
		return volume >= MAX_VOLUME();
	}

	public void collectDew(Dewdrop dew) {

		//GLog.i(Messages.get(DewVial.class, "collected"));
		volume += dew.quantity;
		if (volume >= MAX_VOLUME()) {
			volume = MAX_VOLUME();
			GLog.p(Messages.get(DewVial.class, "full"));
		}

		updateQuickslot();
	}

	public void collectDew(RedDewdrop dew) {

		//GLog.i(Messages.get(DewVial.class, "collect"));
		volume += (dew.quantity*10);
		if (volume >= MAX_VOLUME()) {
			volume = MAX_VOLUME();
			GLog.p(Messages.get(DewVial.class, "full"));
		}

		updateQuickslot();
	}
	
	public void collectDew(YellowDewdrop dew) {

		//GLog.i(Messages.get(DewVial.class, "collect"));
		volume += (dew.quantity*5);
		if (volume >= MAX_VOLUME()) {
			volume = MAX_VOLUME();
			GLog.p(Messages.get(DewVial.class, "full"));
		}

		updateQuickslot();
	}

	public void collectDew(VioletDewdrop dew) {

		//GLog.i(Messages.get(DewVial.class, "collect"));
		volume += (dew.quantity*50);
		if (volume >= MAX_VOLUME()) {
			volume = MAX_VOLUME();
			GLog.p(Messages.get(DewVial.class, "full"));
		}

		updateQuickslot();
	}
	
	
	public void fill() {
		volume = MAX_VOLUME();
		updateQuickslot();
	}

	// removed as people need a bigger distinction to realize the dew vial
	// doesn't revive.
	/*
	 * private static final Glowing WHITE = new Glowing( 0xFFFFCC );
	 * 
	 * @Override public Glowing glowing() { return isFull() ? WHITE : null; }
	 */

	@Override
	public String status() {
		return Messages.format(TXT_STATUS, volume, MAX_VOLUME());
	}

	@Override
	public String toString() {
		return super.toString() + " (" + status() + ")";
	}
}

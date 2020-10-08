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

import java.util.ArrayList;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.Water;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.ExitFind;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.MindVision;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.buffs.Weakness;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.items.food.WaterItem;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
 
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

public class DewVial extends Item {

	private static final int MAX_VOLUME = 120;
	private static final int EXT_VOLUME = 350;
	private static final int BLESS_VOLUME = 20;
	
	
	private static final int MAX_VOLUME(){
		return Dungeon.wings ? EXT_VOLUME : MAX_VOLUME;
	}


	private static final String AC_DRINK = "DRINK";
	private static final String AC_WATER = "WATER";
	private static final String AC_SPLASH = "SPLASH";
	private static final String AC_BLESS = "BLESS";
	private static final String AC_LIGHT = "LIGHT";
    private static final String AC_POUR = "POUR";
	private static final String AC_PEEK = "PEEK";
	private static final String AC_REFINE = "REFINE";
	private static final String AC_CHOOSE = "CHOOSE";

	private static final float TIME_TO_LIGHT = 1f;
	private static final float TIME_TO_DRINK = 2f;
	private static final float TIME_TO_WATER = 3f;

	private static final String TXT_STATUS = "%d";
	private static final String TXT_STATUS2 = "%d/%d";

	{
		//name = "dew vial";
		image = ItemSpriteSheet.VIAL;

		defaultAction = AC_CHOOSE;
		unique = true;
	}

	private int volume = 0;

	private int rejection = Dungeon.isChallenged(Challenges.DEW_REJECTION)? 10 : 0 ;

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
			actions.add(AC_REFINE);
			//actions.add(AC_ESCAPE);
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
			actions.add(AC_REFINE);
		}
		
		else if (volume > 29) {
			actions.add(AC_DRINK);
			if (Dungeon.dewNorn){
			actions.add(AC_SPLASH);
			actions.add(AC_POUR);
			}
			actions.add(AC_LIGHT);
			actions.add(AC_PEEK);
			actions.add(AC_REFINE);
		}
		else if (volume > 1) {
			actions.add(AC_DRINK);
		} 
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals( AC_CHOOSE )) {

			GameScene.show(new WndItem(null, this, true));

		} else	if (action.equals(AC_DRINK)) {
			 if (volume > 350 ){
				 hero.damage(hero.HT,this);
				 if (!hero.isAlive()) {
					 Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				 }
			 }
			if (volume > 0) {

				//20 drops for a full heal normally, 15 for the warden
				float dropHealPercent = hero.subClass == HeroSubClass.WARDEN ? 0.04f : 0.025f;
				float missingHealthPercent = 1f - (hero.HP / (float)hero.HT);

				//trimming off 0.01 drops helps with floating point errors
				int dropsNeeded = (int)Math.ceil((missingHealthPercent / dropHealPercent) - 0.01f);
				dropsNeeded = (int) GameMath.gate(1, dropsNeeded, volume);

				int heal = Math.round( hero.HT * dropHealPercent * dropsNeeded );

				int effect = Math.min( hero.HT - hero.HP, heal );
				if (effect > 0) {
					if (Dungeon.hero.subClass == HeroSubClass.PASTOR) {
						hero.HP += (int)(effect*1.5);
					} else hero.HP += effect;
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 + dropsNeeded/5 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "value", effect) );
				}

				volume -= dropsNeeded;

				hero.spend(TIME_TO_DRINK);
				hero.busy();

				Sample.INSTANCE.play(Assets.SND_DRINK);
				hero.sprite.operate(hero.pos);

				updateQuickslot();

			} else {
				GLog.w(Messages.get(this, "empty"));
			}

		} else if (action.equals(AC_WATER)) {
			 if (volume > 350 ){
				 hero.damage(hero.HT,this);
				 if (!hero.isAlive()) {
					 Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				 }
			 }
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
			volume = volume - 15 - rejection;
			GLog.i(Messages.get(this, "watered"));
			hero.sprite.operate(hero.pos);
			hero.busy();
			hero.spend(TIME_TO_WATER);
			 updateQuickslot();
		
		} else if (action.equals(AC_SPLASH)) {
         	if (volume > 350 ){
			 hero.damage(hero.HT,this);
			 if (!hero.isAlive()) {
				 Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
			 }
		 }
			Buff.affect(hero, HasteBuff.class, HasteBuff.DURATION);
			//Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
			if(Dungeon.wings && Dungeon.depth<51 ){
				Buff.affect(hero, Levitation.class, Levitation.DURATION);
			    GLog.i(Messages.get(this, "fly"));
			}
			//GLog.i(Messages.get(this, "invisible"));
			GLog.i(Messages.get(this, "fast"));
			volume = volume - 15 - rejection;
			 updateQuickslot();
			
		} else if (action.equals(AC_BLESS) && !Dungeon.dewDraw) {
			 if (volume > 350 ){
				 hero.damage(hero.HT,this);
				 if (!hero.isAlive()) {
					 Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				 }
			 }
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
													
			volume = volume - 70 - rejection;
			 updateQuickslot();
			
		} else if (action.equals(AC_BLESS) && Dungeon.dewDraw) {
			 if (volume > 350 ){
				 hero.damage(hero.HT,this);
				 if (!hero.isAlive()) {
					 Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				 }
			 }
			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.UPGRADEDEW,	Messages.get(DewVial.class, "select"));
			 //updateQuickslot();
													
		} else if (action.equals(AC_LIGHT)) {
			 if (volume > 350 ){
				 hero.damage(hero.HT,this);
				 if (!hero.isAlive()) {
					 Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				 }
			 }
			Buff.affect(hero, Light.class, 80f);
			Buff.affect(hero, Invisibility.class, 20f);
			GLog.i(Messages.get(this, "light"));
			 hero.spend(TIME_TO_LIGHT);
			volume = volume - 10 - rejection;
			 updateQuickslot();
							
		} else if (action.equals(AC_POUR)) {
			 if (volume > 350 ){
				 hero.damage(hero.HT,this);
				 if (!hero.isAlive()) {
					 Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				 }
			 }
             Buff.detach(hero, Burning.class);
             Buff.detach(hero, Ooze.class);
             Buff.detach(hero, Tar.class);
             Buff.detach(hero, Weakness.class);
             Buff.detach(hero, Vertigo.class);

             if (Random.Int(8) == 0) {
                 volume = volume - 20  - rejection;
                 Buff.affect(hero, Bless.class, 30f);
                 hero.sprite.operate(hero.pos);
                 hero.busy();
                 hero.spend(TIME_TO_WATER);
             } else { volume = volume - 5  - rejection;
                 hero.sprite.operate(hero.pos);
                 hero.busy();
                 hero.spend(TIME_TO_WATER);}

			 updateQuickslot();

		} else if (action.equals(AC_PEEK)) {
         	if (volume > 350 ){
         		hero.damage(hero.HT,this);
				if (!hero.isAlive()) {
					Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				}
			}
			Buff.affect(hero, MindVision.class, 2f);
			Buff.affect(hero, ExitFind.class, 2f);
			volume = volume - 5  - rejection;
			hero.sprite.operate(hero.pos);
			hero.busy();
			hero.spend(TIME_TO_LIGHT);
			 updateQuickslot();
		} else if (action.equals(AC_REFINE)) {
			 if (volume > 350 ){
				 hero.damage(hero.HT,this);
				 if (!hero.isAlive()) {
					 Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				 }
			 }
			 volume = volume - 10 - rejection;
			 hero.sprite.operate(hero.pos);
			 hero.busy();
			 hero.spend(TIME_TO_DRINK);
			 WaterItem wateritem = new WaterItem();
			 if (wateritem.doPickUp(Dungeon.hero)) {
				 GLog.i( Messages.get(Dungeon.hero, "you_now_have", wateritem.name()));
			 } else {
				 Dungeon.level.drop(wateritem, hero.pos).sprite
						 .drop();
			 }
			 updateQuickslot();
		// } else if (action.equals(AC_ESCAPE)) {
         	//if (Dungeon.depth < 26){
         		//GLog.n(Messages.get(this,"not_escape"));
			//} else {
				//volume = volume - volume;
				//InterlevelScene.mode = InterlevelScene.Mode.SOKOBANFAIL;
				//Game.switchScene(InterlevelScene.class);}
		 }else {
			super.execute(hero, action);
		}
	}

	public static boolean uncurse(Hero hero, Item... items) {
		
		
        int levelLimit = Math.max(2, 2+Math.round(Statistics.deepestFloor/3));
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
				volume = volume - 70 - rejection;
			}
		}
	};
	
	private void upgrade(Item item) {
        int n = Random.Int(Math.min(1,(int)(Statistics.deepestFloor/24)) , Math.max(2,(int)(Statistics.deepestFloor/6)));

		//GLog.w(Messages.get(this, "looks_better", item.name()));
		for(int i=0; i<n; i++) {
			item.upgrade();
		}
		item.upgrade();
		item.identify();
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
		Badges.validateItemLevelAquired(item);
		
		curUser.busy();
		updateQuickslot();
		
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
		return Messages.format(TXT_STATUS, volume);
	}

	public String status2() {
		return Messages.format(TXT_STATUS2, volume, MAX_VOLUME());
	}
	@Override
	public String toString() {
		return super.toString() + " (" + status2() + ")";
	}

	@Override
	public String info() {

		String desc = desc();

		if (Dungeon.dewWater || Dungeon.dewDraw)
			desc += "\n" + Messages.get(this, "desc_v1");

		if(Dungeon.dewNorn)
			desc += "\n" + Messages.get(this, "desc_v2");

		if(Dungeon.wings)
			desc += "\n" + Messages.get(this, "desc_v3");

		return desc;

	}

}

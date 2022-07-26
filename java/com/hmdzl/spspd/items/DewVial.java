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
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.Water;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.ExitFind;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.buffs.MindVision;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.items.food.WaterItem;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

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

	private static int dewpoint = 0;

	private int rejection = Dungeon.isChallenged(Challenges.DEW_REJECTION)? 10 : 0 ;

	public int checkVol () {
		return dewpoint;
	}

	public void setVol (int vol) {
		dewpoint =vol;
	}
	
	private static final String VOLUME = "dewpoint";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(VOLUME, dewpoint);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		dewpoint = bundle.getInt(VOLUME);
	}
	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (dewpoint > 99) {
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
		else if (dewpoint > 49) {
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

		else if (dewpoint > 29) {
			actions.add(AC_DRINK);
			if (Dungeon.dewNorn){
			actions.add(AC_SPLASH);
			actions.add(AC_POUR);
			}
			actions.add(AC_LIGHT);
			actions.add(AC_PEEK);
			actions.add(AC_REFINE);
		}
		else if (dewpoint > 1) {
			actions.add(AC_DRINK);
			actions.add(AC_LIGHT);
		} 
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action){
        CrystalVial cv = hero.belongings.getItem(CrystalVial.class);

		if (action.equals( AC_CHOOSE )) {

			GameScene.show(new WndItem(null, this, true));

		} else	if (action.equals(AC_DRINK)) {
			if (dewpoint > 0) {

				//20 drops for a full heal normally, 15 for the warden
				float dropHealPercent = hero.subClass == HeroSubClass.WARDEN ? 0.04f : 0.025f;
				float missingHealthPercent = 1f - (hero.HP / (float)hero.HT);

				//trimming off 0.01 drops helps with floating point errors
				int dropsNeeded = (int)Math.ceil((missingHealthPercent / dropHealPercent) - 0.01f);
				dropsNeeded = (int) GameMath.gate(1, dropsNeeded, dewpoint);

				int heal = Math.round( hero.HT * dropHealPercent * dropsNeeded );

				int effect = Math.min( hero.HT - hero.HP, heal );
				if (effect > 0) {
					if (Dungeon.hero.subClass == HeroSubClass.PASTOR) {
						hero.HP += (int)(effect*1.5);
					} else hero.HP += effect;
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 + dropsNeeded/5 );
					hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(this, "value", effect) );
				}

				dewpoint -= dropsNeeded;
                if (cv!=null && cv.volume<50) {cv.volume+=5;}

				hero.spend(TIME_TO_DRINK);
				hero.busy();

				Sample.INSTANCE.play(Assets.SND_DRINK);
				hero.sprite.operate(hero.pos);

				updateQuickslot();

			} else {
				GLog.w(Messages.get(this, "empty"));
			}

		} else if (action.equals(AC_WATER)) {
			//Statistics.waters++;
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
			dewpoint = dewpoint - 15 - rejection;
            if (cv!=null && cv.volume<50) {cv.volume+=5;}
			GLog.i(Messages.get(this, "watered"));
			hero.sprite.operate(hero.pos);
			hero.busy();
			hero.spend(TIME_TO_WATER);
			 updateQuickslot();
		
		} else if (action.equals(AC_SPLASH)) {
			Buff.affect(hero, HasteBuff.class, HasteBuff.DURATION);

			if(Dungeon.wings && Dungeon.depth<51 ){
				Buff.affect(hero, Levitation.class, Levitation.DURATION);
			    GLog.i(Messages.get(this, "fly"));
			}
			//GLog.i(Messages.get(this, "invisible"));
			GLog.i(Messages.get(this, "fast"));
			dewpoint = dewpoint - 15 - rejection;
            if (cv!=null && cv.volume<50) {cv.volume+=5;}
			 updateQuickslot();
			
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
													
			dewpoint = dewpoint - 70 - rejection;
            if (cv!=null && cv.volume<50) {cv.volume+=10;}
			 updateQuickslot();
			
		} else if (action.equals(AC_BLESS) && Dungeon.dewDraw) {
			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.UPGRADEDEW,	Messages.get(DewVial.class, "select"));
			 //updateQuickslot();
													
		} else if (action.equals(AC_LIGHT)) {
			if (hero.buff(DewLight.class) == null) {
				Buff.affect(hero, DewLight.class);
				return;
			} else {
				Buff.detach(hero, DewLight.class);
				return;
			}
			 //hero.spend(TIME_TO_LIGHT);
			// updateQuickslot();
							
		} else if (action.equals(AC_POUR)) {
             Buff.detach(hero, Burning.class);
             Buff.detach(hero, Ooze.class);
             Buff.detach(hero, Tar.class);
             Buff.detach(hero, STRdown.class);
             Buff.detach(hero, Vertigo.class);
            Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
             dewpoint = dewpoint - 20  - rejection;
             if (cv!=null && cv.volume<50) {cv.volume+=5;}
             Buff.affect(hero, Bless.class, 30f);
             hero.sprite.operate(hero.pos);
             hero.busy();
             hero.spend(TIME_TO_WATER);
			 updateQuickslot();

		} else if (action.equals(AC_PEEK)) {
			Buff.affect(hero, MindVision.class, 2f);
			Buff.affect(hero, ExitFind.class, 2f);
			dewpoint = dewpoint - 5  - rejection;
            if (cv!=null && cv.volume<50) {cv.volume+=5;}
			hero.sprite.operate(hero.pos);
			hero.busy();
			hero.spend(TIME_TO_LIGHT);
			 updateQuickslot();
		} else if (action.equals(AC_REFINE)) {
			 dewpoint = dewpoint - 10 - rejection;
            //if (cv!=null && cv.dewpoint<50) {cv.dewpoint+=5;}
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
				dewpoint = dewpoint - 70 - rejection;
                CrystalVial cv = hero.belongings.getItem(CrystalVial.class);
                if (cv!=null && cv.volume<50) {cv.volume+=5;}
			}
		}
	};
	
	private void upgrade(Item item) {
        int n = Random.Int(Math.min(1, Statistics.deepestFloor/24) , Math.max(2, Statistics.deepestFloor/6));

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
		dewpoint = dewpoint - 10;
		updateQuickslot();
	}

	public void sip() {
		dewpoint = dewpoint - 1;
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
		return dewpoint >= BLESS_VOLUME;
	}
	

	public boolean isFull() {
		return dewpoint >= MAX_VOLUME();
	}

	public void collectDew(Dewdrop dew) {

		//GLog.i(Messages.get(DewVial.class, "collected"));
		dewpoint += dew.quantity;
		if (dewpoint >= MAX_VOLUME()) {
			dewpoint = MAX_VOLUME();
			GLog.p(Messages.get(DewVial.class, "full"));
		}

		updateQuickslot();
	}

	public void collectDew(RedDewdrop dew) {

		//GLog.i(Messages.get(DewVial.class, "collect"));
		dewpoint += (dew.quantity*10);
		if (dewpoint >= MAX_VOLUME()) {
			dewpoint = MAX_VOLUME();
			GLog.p(Messages.get(DewVial.class, "full"));
		}

		updateQuickslot();
	}
	
	public void collectDew(YellowDewdrop dew) {

		//GLog.i(Messages.get(DewVial.class, "collect"));
		dewpoint += (dew.quantity*5);
		if (dewpoint >= MAX_VOLUME()) {
			dewpoint = MAX_VOLUME();
			GLog.p(Messages.get(DewVial.class, "full"));
		}

		updateQuickslot();
	}

	public void collectDew(VioletDewdrop dew) {

		//GLog.i(Messages.get(DewVial.class, "collect"));
		dewpoint += (dew.quantity*50);
		if (dewpoint >= MAX_VOLUME()) {
			dewpoint = MAX_VOLUME();
			GLog.p(Messages.get(DewVial.class, "full"));
		}

		updateQuickslot();
	}
	
	
	public void fill() {
		dewpoint = MAX_VOLUME();
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
		return Messages.format(TXT_STATUS, dewpoint);
	}

	public String status2() {
		return Messages.format(TXT_STATUS2, dewpoint, MAX_VOLUME());
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

	public static class DewLight extends Buff {

		private int left;
		{
			type = buffType.NEUTRAL;
		}

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)) {
			if (Dungeon.level != null) {
				target.viewDistance = Math.max(Dungeon.level.viewDistance,
						6);
				Dungeon.observe();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void detach() {
		target.viewDistance = Dungeon.level.viewDistance;
		Dungeon.observe();
		super.detach();
	}		
		
		@Override
		public boolean act(){
			left--;
			if (left <= 0){
				dewpoint--;
				if (dewpoint < 0) {
					dewpoint = 0;
				detach();
					GLog.w(Messages.get(DewVial.class, "no_charge"));
					((Hero) target).interrupt();
				} else {
					left = 10;
				}
			}

			spend( TICK );

			return true;
		}

		@Override
		public int icon() {
			return BuffIndicator.LIGHT;
		}

		@Override
		public void fx(boolean on) {
			if (on) target.sprite.add(CharSprite.State.ILLUMINATED);
			else target.sprite.remove(CharSprite.State.ILLUMINATED);
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc");
		}

		private static final String LEFT = "left";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(LEFT, left);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			left = bundle.getInt(LEFT);
		}
	}
}

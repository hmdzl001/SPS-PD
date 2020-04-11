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
package com.hmdzl.spspd.windows;

import java.util.Locale;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.potions.PotionOfFrost;
import com.hmdzl.spspd.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.items.food.completefood.Garbage;
import com.hmdzl.spspd.items.weapon.missiles.EscapeKnive;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.ui.HealthBar;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.Window;
import com.hmdzl.spspd.utils.GLog;
 
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Button;

public class WndHero extends WndTabbed {

	private static final String TXT_STATS = "Stats";
	private static final String TXT_LEVELSTATS = "Level";
	private static final String TXT_BUFFS = "Buffs";
	private static final String TXT_PET = "Pet";

	private static final String TXT_HEALS = "%+dHP";
	
	private static final String TXT_EXP = "Experience";
	private static final String TXT_STR = "Strength";
	private static final String TXT_BREATH = "Breath Weapon";
	private static final String TXT_SPIN = "Spinneretes";
	private static final String TXT_STING = "Stinger";
	private static final String TXT_FEATHERS = "Feathers";
	private static final String TXT_SPARKLE = "Wand Attack";
	private static final String TXT_FANGS = "Fangs";
	private static final String TXT_ATTACK = "Attack Skill";
	private static final String TXT_HEALTH = "Health";
	private static final String TXT_GOLD = "Gold Collected";
	private static final String TXT_DEPTH = "Maximum Depth";
	private static final String TXT_MOVES = "Game Moves";
	private static final String TXT_MOVES2 = "Floor Moves";
	private static final String TXT_MOVES3 = "Floor Move Goal";
	private static final String TXT_MOVES4 = "Prev Under Goal";
	private static final String TXT_HUNGER = "Hunger";
	private static final String TXT_MOVES_DEW = "Dew Charge Moves";
	private static final String TXT_PETS = "Pets Lost";

	private static final int WIDTH = 100;
	private static final int TAB_WIDTH = 40;

	private StatsTab stats;
	private LevelStatsTab levelstats;
	private PetTab pet;
	private BuffsTab buffs;

	private SmartTexture icons;
	private TextureFilm film;
	
	
	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}

	public WndHero() {

		super();

		icons = TextureCache.get(Assets.BUFFS_LARGE);
		film = new TextureFilm(icons, 16, 16);

		stats = new StatsTab();
		add(stats);
		
		if(Dungeon.dewDraw || Dungeon.dewWater){
		  levelstats = new LevelStatsTab();
		  add(levelstats);
		}
		PET heropet = checkpet();
		
		if (heropet!=null){
		  pet = new PetTab(heropet);
		  add(pet);
		}
		
		buffs = new BuffsTab();
		add(buffs);
		
		
		add(new LabeledTab(Messages.get(this, "stats")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				stats.visible = stats.active = selected;
			};
		});
		
		if(Dungeon.dewDraw || Dungeon.dewWater){
		add(new LabeledTab(Messages.get(this, "dew")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				levelstats.visible = levelstats.active = selected;
			};
		});
		}

		if (heropet!=null){
		add(new LabeledTab(Messages.get(this, "pet")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				pet.visible = pet.active = selected;
			};
		});
		}

		add(new LabeledTab(Messages.get(this, "buffs")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				buffs.visible = buffs.active = selected;
			};
		});
		
		resize(WIDTH, (int) Math.max(stats.height(), buffs.height()));

		layoutTabs();

		select(0);
	}

	private class StatsTab extends Group {

		private static final String TXT_TITLE = "Level %d %s";
		private static final String TXT_CATALOGUS = "Catalogus";
		private static final String TXT_JOURNAL = "Journal";

		private static final int GAP = 4;

		private float pos;

		public StatsTab() {

			Hero hero = Dungeon.hero;

			IconTitle title = new IconTitle();
			title.icon(HeroSprite.avatar(hero.heroClass, hero.useskin()));
            title.label( Messages.get(this, "title", hero.lvl, hero.className() )
			     .toUpperCase(Locale.ENGLISH));
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			/*MemoryButton btnmemory = new MemoryButton();
			btnmemory.setRect(60,0,0,0);
			add(btnmemory);*/

			RedButton btnCatalogus = new RedButton(Messages.get(this, "catalogs")) {
				@Override
				protected void onClick() {
				    hide();
					GameScene.show(new WndCatalogus());
				};
			};
			btnCatalogus.setRect(0, title.height(),
					btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2);
			add(btnCatalogus);

			RedButton btnJournal = new RedButton(Messages.get(this, "journal")) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndJournal());
				}
			};
			btnJournal.setRect(btnCatalogus.right() + 1, btnCatalogus.top(),
					btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2);
			add(btnJournal);

			pos = btnJournal.bottom() + GAP;

			statSlot(Messages.get(this, "str"), hero.STR());
			statSlot(Messages.get(this, "health"), hero.HP + "/" + hero.HT );
			statSlot(Messages.get(this, "exp"), hero.exp + "/" + hero.maxExp());

			statSlot(Messages.get(this, "time"),  ((int)(Statistics.time/60) + ":" + (int)(Statistics.time%60)));

			pos += GAP;

			statSlot(Messages.get(this, "gold"), Statistics.goldCollected);
			statSlot(Messages.get(this, "depth"), Statistics.deepestFloor);
			statSlot(Messages.get(this, "atkskill"), hero.hitSkill);
			statSlot(Messages.get(this, "defskill"), hero.evadeSkill);
			statSlot(Messages.get(this, "magskill"), hero.magicSkill());

			if(Dungeon.hero.buff(Hunger.class) != null){
				statSlot(Messages.get(this, "hunger") , Dungeon.hero.buff(Hunger.class).hungerLevel());
			}			
			
			
			pos += GAP;
		}

		private void statSlot(String label, String value) {

			RenderedText txt = PixelScene.renderText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.renderText(value, 8);
			//txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.5f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}
	
	private class LevelStatsTab extends Group {

		private static final String TXT_TITLE = "Level %d %s";
		private static final String TXT_CATALOGUS = "Catalogus";
		private static final String TXT_JOURNAL = "Journal";

		private static final int GAP = 4;

		private float pos;

		public LevelStatsTab() {

			Hero hero = Dungeon.hero;

			IconTitle title = new IconTitle();
			title.icon(HeroSprite.avatar(hero.heroClass, hero.useskin()));
			title.label( Messages.get(this, "title", hero.lvl, hero.className())
					.toUpperCase(Locale.ENGLISH));
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			RedButton btnCatalogus = new RedButton(Messages.get(this, "catalogs")) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndCatalogus());
				};
			};
			btnCatalogus.setRect(0, title.height(),
					btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2);
			add(btnCatalogus);

			RedButton btnJournal = new RedButton(Messages.get(this, "journal")) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show(new WndJournal());
				}
			};
			btnJournal.setRect(btnCatalogus.right() + 1, btnCatalogus.top(),
					btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2);
			add(btnJournal);

			pos = btnCatalogus.bottom() + GAP;
				
			
			if (Dungeon.depth<26 && (Dungeon.dewDraw || Dungeon.dewWater)){
			statSlot(Messages.get(this, "level_move"), Dungeon.level.currentmoves);
			statSlot(Messages.get(this, "level_max"), Dungeon.pars[Dungeon.depth]);
			statSlot(Messages.get(this, "level_prefect"),Statistics.prevfloormoves);
			/*if (Dungeon.hero.buff(Dewcharge.class) != null) {
				float dewration = Dungeon.hero.buff(Dewcharge.class).dispTurns();
			    statSlot(Messages.get(this, "level_left"), dewration);	
			  }*/
			}
			
			
			pos += GAP;
		}

		private void statSlot(String label, String value) {

			RenderedText txt = PixelScene.renderText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.renderText(value, 8);
			//txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.5f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}
	

	
	
	private class BuffsTab extends Group {

		private static final int GAP = 4;

		private float pos;

		public BuffsTab() {
			for (Buff buff : Dungeon.hero.buffs()) {
				if (buff.icon() != BuffIndicator.NONE) {
					BuffSlot slot = new BuffSlot(buff);
					slot.setRect(0, pos, WIDTH, slot.icon.height());
					add(slot);
					pos += GAP + slot.height();
				}
			}
		}

		public float height() {
			return pos;
		}		
		
		private class BuffSlot extends Button {

			private Buff buff;

			Image icon;
			RenderedText txt;

			public BuffSlot( Buff buff ){
				super();
				this.buff = buff;
				int index = buff.icon();

				icon = new Image( icons );
				icon.frame( film.get( index ) );
				icon.y = this.y;
				add( icon );

				txt = PixelScene.renderText( buff.toString(), 8 );
				txt.x = icon.width + GAP;
				txt.y = this.y + (int)(icon.height - txt.baseLine()) / 2;
				add( txt );

			}

			@Override
			protected void layout() {
				super.layout();
				icon.y = this.y;
				txt.x = icon.width + GAP;
				txt.y = pos + (int)(icon.height - txt.baseLine()) / 2;
			}

			@Override
			protected void onClick() {
				GameScene.show( new WndInfoBuff( buff ));
			}
		}
		/*private void buffSlot(Buff buff) {

			int index = buff.icon();

			if (index != BuffIndicator.NONE) {

				Image icon = new Image(icons);
				icon.frame(film.get(index));
				icon.y = pos;
				add(icon);

				RenderedText txt = PixelScene.renderText(buff.toString(), 8);
				txt.x = icon.width + GAP;
				txt.y = pos + (int) (icon.height - txt.baseLine()) / 2;
				add(txt);

				pos += GAP + icon.height;
			}
		}

		public float height() {
			return pos;
		}*/
	}
	
	private class PetTab extends Group {

		private static final String TXT_TITLE = "Level %d %s";
		private static final String TXT_FEED = "Feed";
		private static final String TXT_CALL = "Call";
		private static final String TXT_STAY = "Stay";
		private static final String TXT_RELEASE = "Release";
		private static final String TXT_SELECT = "What do you want to feed your pet?";
		
		private CharSprite image;
		private RenderedText name;
		private HealthBar health;
		private BuffIndicator buffs;

		private static final int GAP = 4;

		private float pos;
		
				
		public PetTab(final PET heropet) {		
						
			//name = PixelScene.renderText(heropet.name);
			//name.hardlight(TITLE_COLOR);
			//name.measure();
			//add(name);

			image = heropet.sprite();
			add(image);

			health = new HealthBar();
			health.level((float) heropet.HP / heropet.HT);
			add(health);

			buffs = new BuffIndicator(heropet);
			add(buffs);
		
			
			
			IconTitle title = new IconTitle();
			title.icon(image);
			title.label( Messages.get(this, "title", heropet.level, heropet.name).toUpperCase(Locale.ENGLISH), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			RedButton btnFeed = new RedButton(Messages.get(this, "pet_feed")) {
				@Override
				protected void onClick() {
					hide();
					GameScene.selectItem(itemSelector, WndBag.Mode.ALL, Messages.get(WndHero.class, "choose_food"));
				}
			};
			btnFeed.setRect(0, title.height(),
					btnFeed.reqWidth() + 2, btnFeed.reqHeight() + 2);
			add(btnFeed);
			
			RedButton btnCall = new RedButton(Messages.get(this, "pet_call")) {
				@Override
				protected void onClick() {
					hide();
					heropet.callback = true;
					heropet.stay = false;
				}
			};
			btnCall.setRect(btnFeed.right() + 1, btnFeed.top(),
					btnCall.reqWidth() + 2, btnCall.reqHeight() + 2);
			add(btnCall);
			
			RedButton btnStay = new RedButton(heropet.stay ? Messages.get(this, "pet_move") : Messages.get(this, "pet_stay")) {
				@Override
				protected void onClick() {
					hide();
					if (heropet.stay){
					   heropet.stay = false;
					} else {
						heropet.stay = true;
					}
				}
			};

			btnStay.setRect(btnCall.right() + 1, btnCall.top(),
					btnStay.reqWidth() + 2, btnStay.reqHeight() + 2);
			
			add(btnStay);

			pos = btnStay.bottom() + GAP;

			statSlot(Messages.get(this, "pet_attack"), heropet.hitSkill(null));
			statSlot(Messages.get(this, "pet_hp"), heropet.HP + "/" + heropet.HT);
			statSlot(Messages.get(this, "pet_exp"), heropet.level<30 ? heropet.experience + "/" + (heropet.level*(heropet.level+1) ): "MAX");
			statSlot(Messages.get(this, "pet_skills"), heropet.cooldown==0 ? Messages.get(this, "pet_skills_ready") : heropet.cooldown + Messages.get(this, "pet_skills_turn"));
		
			pos += GAP;

			
		}

		private void statSlot(String label, String value) {

			RenderedText txt = PixelScene.renderText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.renderText(value, 8);
			//txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.5f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}
	
	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				feed(item);
			}
		}
	};
	
	private boolean checkpetNear(){
		for (int n : Level.NEIGHBOURS8) {
			int c = Dungeon.hero.pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}
	
	private void feed(Item item) {
						
		PET heropet = checkpet();
		boolean nomnom = checkFood(heropet.type, item);
		boolean nearby = checkpetNear();
	
		if (nomnom && nearby){
		  int effect = heropet.HT-heropet.HP;
		  if (effect > 0){
		    heropet.HP+= (int)(effect*0.8);
		    heropet.sprite.emitter().burst(Speck.factory(Speck.HEALING),2);
		    heropet.sprite.showStatus(CharSprite.POSITIVE,Messages.get(WndHero.class,"heals", effect));
		  }
	      heropet.cooldown=1;
			heropet.experience+= 120;
		  item.detach(Dungeon.hero.belongings.backpack);
		  Dungeon.hero.spend(1f);
		  Dungeon.hero.busy();
		  Dungeon.hero.sprite.operate(Dungeon.hero.pos);
		  GLog.n(Messages.get(WndHero.class, "pet_eat",item.name()));
		}else if (!nearby){
			GLog.n(Messages.get(WndHero.class, "pet_faraway"));
		} else {
		  GLog.n(Messages.get(WndHero.class, "pet_noeat",item.name()));
		  
		}		
	}

	private boolean checkFood(Integer petType, Item item){
		boolean nomnom = false;
		
		if (petType==1){ //Spider
			if (item instanceof Meat
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		} 

		if (petType==3){//Velocirooster 
			if (item instanceof Plant.Seed
				|| item instanceof Nut
				|| item instanceof PetFood){
				nomnom=true;
			}
		}			
		if (petType==4){//red dragon - fire
			if (item instanceof Meat
				|| item instanceof PotionOfLiquidFlame
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		
		if (petType==5){//green dragon - lit
			if (item instanceof Meat
				|| item instanceof ScrollOfRecharging
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		
		if (petType==6){//violet dragon - poison
			if (item instanceof Meat
				|| item instanceof PotionOfToxicGas
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==7){//blue dragon - ice
			if (item instanceof Meat
				|| item instanceof PotionOfFrost
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		
		if (petType==8){ //scorpion
			if (item instanceof Meat
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		} 
		
		if (petType==9){//Vorpal Bunny 
			if (item instanceof Plant.Seed
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==10){//Fairy
			if (item instanceof Potion
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==11){//Sugarplum Fairy
			if (item instanceof Potion
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==12){//shadow dragon
			if (item instanceof Meat
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==13){//CocoCat
			if (item instanceof Nut
				|| item instanceof Nut
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}			
		if (petType==14){//LeryFire
			if (item instanceof Scroll
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==15){//GoldDragon
			if (item instanceof Meat
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==16){//snake
			if (item instanceof Meat
				|| item instanceof MysteryMeat
		        || item instanceof PetFood){				
				nomnom=true;
			}
		}	
		if (petType==17){//fly
			if (item instanceof Meat
				|| item instanceof MysteryMeat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}	
		if (petType==18){//stone
			if (item instanceof EscapeKnive
				|| item instanceof PetFood
				|| item instanceof StoneOre
				|| item instanceof Garbage){
				nomnom=true;
			}
		}	
		if (petType==19){//monkey
			if (item instanceof Plant.Seed
				|| item instanceof Nut
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==20){//GentleCrab
			if (item instanceof Meat
				|| item instanceof MysteryMeat
				|| item instanceof Plant.Seed
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==21){//RibbonRat
			if (item instanceof Plant.Seed
				|| item instanceof Nut
				|| item instanceof Meat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		if (petType==22){//YearPet
			if (item instanceof Meat
				|| item instanceof PetFood){				
				nomnom=true;
			}
		}
		
	return nomnom;		
	}
	
}

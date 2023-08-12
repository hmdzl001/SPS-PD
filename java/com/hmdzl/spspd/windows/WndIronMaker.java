/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.EquipableItem;
import com.hmdzl.spspd.items.Garbage;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.GreatRune;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.PocketBall;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.Stylus;
import com.hmdzl.spspd.items.Torch;
import com.hmdzl.spspd.items.Weightstone;
import com.hmdzl.spspd.items.bombs.BuildBomb;
import com.hmdzl.spspd.items.bombs.HugeBomb;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.WaterItem;
import com.hmdzl.spspd.items.food.completefood.FruitCandy;
import com.hmdzl.spspd.items.food.completefood.Gel;
import com.hmdzl.spspd.items.food.completefood.Mediummeat;
import com.hmdzl.spspd.items.food.completefood.MixPizza;
import com.hmdzl.spspd.items.food.completefood.NutCookie;
import com.hmdzl.spspd.items.food.fruit.Fruit;
import com.hmdzl.spspd.items.food.meatfood.MeatFood;
import com.hmdzl.spspd.items.food.staplefood.StapleFood;
import com.hmdzl.spspd.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.quest.DarkGold;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfIdentify;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfRemoveCurse;
import com.hmdzl.spspd.items.weapon.spammo.BattleAmmo;
import com.hmdzl.spspd.items.weapon.spammo.BlindAmmo;
import com.hmdzl.spspd.items.weapon.spammo.DewAmmo;
import com.hmdzl.spspd.items.weapon.spammo.DreamAmmo;
import com.hmdzl.spspd.items.weapon.spammo.EmptyAmmo;
import com.hmdzl.spspd.items.weapon.spammo.EvolveAmmo;
import com.hmdzl.spspd.items.weapon.spammo.FireAmmo;
import com.hmdzl.spspd.items.weapon.spammo.GoldAmmo;
import com.hmdzl.spspd.items.weapon.spammo.HeavyAmmo;
import com.hmdzl.spspd.items.weapon.spammo.IceAmmo;
import com.hmdzl.spspd.items.weapon.spammo.MossAmmo;
import com.hmdzl.spspd.items.weapon.spammo.RotAmmo;
import com.hmdzl.spspd.items.weapon.spammo.SandAmmo;
import com.hmdzl.spspd.items.weapon.spammo.StarAmmo;
import com.hmdzl.spspd.items.weapon.spammo.StormAmmo;
import com.hmdzl.spspd.items.weapon.spammo.SunAmmo;
import com.hmdzl.spspd.items.weapon.spammo.ThornAmmo;
import com.hmdzl.spspd.items.weapon.spammo.WoodenAmmo;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.BlandfruitBush;
import com.hmdzl.spspd.plants.Blindweed;
import com.hmdzl.spspd.plants.Dewcatcher;
import com.hmdzl.spspd.plants.Dreamfoil;
import com.hmdzl.spspd.plants.Earthroot;
import com.hmdzl.spspd.plants.Fadeleaf;
import com.hmdzl.spspd.plants.Firebloom;
import com.hmdzl.spspd.plants.Freshberry;
import com.hmdzl.spspd.plants.Icecap;
import com.hmdzl.spspd.plants.NutPlant;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.ReNepenth;
import com.hmdzl.spspd.plants.Rotberry;
import com.hmdzl.spspd.plants.Seedpod;
import com.hmdzl.spspd.plants.Sorrowmoss;
import com.hmdzl.spspd.plants.StarEater;
import com.hmdzl.spspd.plants.Starflower;
import com.hmdzl.spspd.plants.Stormvine;
import com.hmdzl.spspd.plants.Sungrass;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.Icons;
import com.hmdzl.spspd.ui.ItemSlot;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class WndIronMaker extends Window {

	private WndBlacksmith.ItemButton[] inputs = new WndBlacksmith.ItemButton[5];
	private ItemSlot output;

	private Emitter smokeEmitter;
	private Emitter bubbleEmitter;

	private RedButton btnCombine;

	private static final int WIDTH_P = 116;
	private static final int WIDTH_L = 160;

	private static final int BTN_SIZE	= 28;

	public WndIronMaker(){

		int w = WIDTH_P;

		int h = 0;

		IconTitle titlebar = new IconTitle();
		titlebar.icon(HeroSprite.avatar(hero.heroClass, hero.useskin()));
		titlebar.label( Messages.get(this, "title") );
		titlebar.setRect( 0, 0, w, 0 );
		add( titlebar );

		h += titlebar.height() + 2;

		RenderedTextMultiline desc = PixelScene.renderMultiline(6);
		desc.text( Messages.get(this, "text") );
		desc.setPos(0, h);
		desc.maxWidth(w);
		add(desc);

		h += desc.height() + 6;

		for (int i = 0; i < (inputs.length ); i++) {
			inputs[i] = new WndBlacksmith.ItemButton(){
				@Override
				protected void onClick() {
					super.onClick();
					if (item != null){
						if (!item.collect()){
							Dungeon.level.drop(item, hero.pos);
						}
						item = null;
						slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
					}
					GameScene.selectItem( itemSelector, WndBag.Mode.IRON_MAKE, Messages.get(WndAlchemy.class, "select") );
				}
			};
			inputs[i].setRect(15, h, BTN_SIZE, BTN_SIZE);
			add(inputs[i]);
			h += BTN_SIZE + 2;
		}

		Image arrow = Icons.get(Icons.RESUME);
		arrow.hardlight(0, 0, 0);
		arrow.x = (w - arrow.width)/2f;
		arrow.y = inputs[1].top() + (inputs[1].height() - arrow.height)/2f;
		PixelScene.align(arrow);
		add(arrow);

		output = new ItemSlot(){
			@Override
			protected void onClick() {
				super.onClick();
				/*if (visible){
					GameScene.show(new WndInfoItem);
				}*/
			}
		};
		output.setRect(w - BTN_SIZE - 15, inputs[1].top(), BTN_SIZE, BTN_SIZE);

		ColorBlock outputBG = new ColorBlock(output.width(), output.height(), 0x9991938C);
		outputBG.x = output.left();
		outputBG.y = output.top();
		add(outputBG);

		add(output);
		output.visible = false;

		bubbleEmitter = new Emitter();
		smokeEmitter = new Emitter();
		bubbleEmitter.pos(outputBG.x + (BTN_SIZE-16)/2f, outputBG.y + (BTN_SIZE-16)/2f, 16, 16);
		smokeEmitter.pos(bubbleEmitter.x, bubbleEmitter.y, bubbleEmitter.width, bubbleEmitter.height);
		bubbleEmitter.autoKill = false;
		smokeEmitter.autoKill = false;
		add(bubbleEmitter);
		add(smokeEmitter);

		h += 4;

		float btnWidth = (w-14)/2f;

		btnCombine = new RedButton(Messages.get(this, "combine")){
			@Override
			protected void onClick() {
				super.onClick();
				combine();
			}
		};
		btnCombine.setRect(5, h, btnWidth, 18);
		PixelScene.align(btnCombine);
		btnCombine.enable(false);
		add(btnCombine);

		RedButton btnCancel = new RedButton(Messages.get(this, "cancel")){
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnCancel.setRect(w - 5 - btnWidth, h, btnWidth, 18);
		PixelScene.align(btnCancel);
		add(btnCancel);

		h += btnCancel.height();

		resize(w, h);
	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				Garbage gb = hero.belongings.getItem(Garbage.class);
				if (item instanceof Garbage && gb.quantity() > 5) {
					for (int i = 0; i < 5; i++) {
						inputs[i].item(item.detach(hero.belongings.backpack));
					}
				} else {
					for (int i = 0; i < (inputs.length); i++) {
						if (inputs[i].item == null) {
							inputs[i].item(item.detach(hero.belongings.backpack));
							break;
						}
					}
				}
			}
			updateState();
		}
	};

	private<T extends Item> ArrayList<T> filterInput(Class<? extends T> itemClass){
		ArrayList<T> filtered = new ArrayList<>();
		for (int i = 0; i < (inputs.length); i++){
			Item item = inputs[i].item;
			if (item != null && itemClass.isInstance(item)){
				filtered.add((T)item);
			}
		}
		return filtered;
	}

	private void updateState(){
        if (filterInput(EquipableItem.class).size() == 2){
			output.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
			output.visible = true;
			btnCombine.enable(true);
		} else if(filterInput(EquipableItem.class).size() == 1 && filterInput(WaterItem.class).size() > 0){
			output.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
			output.visible = true;
			btnCombine.enable(true);
		} else if(filterInput(EquipableItem.class).size() == 1){
			output.item(new WndBag.Placeholder(ItemSpriteSheet.ERROR_FOOD));
			output.visible = true;
			btnCombine.enable(true);
		} else if(filterInput(Item.class).size() > 0){
				output.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
				output.visible = true;
				btnCombine.enable(true);
			}  else {
			btnCombine.enable(false);
			output.visible = false;
		}
	}

	private void combine(){
		ArrayList<Garbage> garbage = filterInput(Garbage.class);
		ArrayList<StoneOre> ore = filterInput(StoneOre.class);
		ArrayList<WaterItem> water = filterInput(WaterItem.class);
		ArrayList<EquipableItem> equip = filterInput(EquipableItem.class);
		
		ArrayList<Weightstone> ws = filterInput(Weightstone.class);
		ArrayList<Stylus> sty = filterInput(Stylus.class);
		
	    ArrayList<Icecap.Seed> iceseed = filterInput(Icecap.Seed.class);
        ArrayList<Firebloom.Seed> fireseed = filterInput(Firebloom.Seed.class);
        ArrayList<Blindweed.Seed> blindseed = filterInput(Blindweed.Seed.class);
        ArrayList<Stormvine.Seed> stormseed = filterInput(Stormvine.Seed.class);
        ArrayList<Dreamfoil.Seed> dreamseed = filterInput(Dreamfoil.Seed.class);
        ArrayList<Sorrowmoss.Seed> mossseed = filterInput(Sorrowmoss.Seed.class);
        ArrayList<Starflower.Seed> starseed = filterInput(Starflower.Seed.class);
		ArrayList<Sungrass.Seed> sunseed = filterInput(Sungrass.Seed.class);
		ArrayList<Dewcatcher.Seed> dewseed = filterInput(Dewcatcher.Seed.class);
		ArrayList<Fadeleaf.Seed> sandseed = filterInput(Fadeleaf.Seed.class);
		ArrayList<Seedpod.Seed> seedseed = filterInput(Seedpod.Seed.class);
		ArrayList<Freshberry.Seed> freseed = filterInput( Freshberry.Seed.class);
		ArrayList<Rotberry.Seed> rotseed = filterInput(Rotberry.Seed.class);
		ArrayList<Earthroot.Seed> rootseed = filterInput(Earthroot.Seed.class);
		ArrayList<BlandfruitBush.Seed> blandseed = filterInput(BlandfruitBush.Seed.class);
		ArrayList<StarEater.Seed> trapseed = filterInput(StarEater.Seed.class);
		ArrayList<ReNepenth.Seed> phaseseed = filterInput(ReNepenth.Seed.class);
		ArrayList<NutPlant.Seed> nutseed = filterInput(NutPlant.Seed.class);
		
		ArrayList<Plant.Seed> seeds = filterInput(Plant.Seed.class);

		ArrayList<Potion> potions = filterInput(Potion.class);
		ArrayList<Scroll> scrolls = filterInput(Scroll.class);
        ArrayList<Gel> gels = filterInput(Gel.class);
		ArrayList<BuildBomb> bbomb = filterInput(BuildBomb.class);

		ArrayList<Vegetable> vegetables = filterInput(Vegetable.class);
		ArrayList<Nut> nut = filterInput(Nut.class);
		ArrayList<Fruit> fruits = filterInput(Fruit.class);
		ArrayList<StapleFood> staplefoods = filterInput(StapleFood.class);
		ArrayList<MeatFood> meatfoods = filterInput(MeatFood.class);

		ArrayList<DarkGold> darkgold = filterInput(DarkGold.class);

		ArrayList<ScrollOfMagicalInfusion> miscroll = filterInput(ScrollOfMagicalInfusion.class);

		ArrayList<Item> item = filterInput(Item.class);

		Item item1 = inputs[0].item;

		Item result = null;

		if (garbage.size() + ore.size() == 5 ){
			result = Generator.random();
		} else if ( nut.size() == 5 ){
			result = new NutCookie(6);
		} else if (meatfoods.size()==1 && ore.size() == 1 && staplefoods.size() ==1 && fruits.size() == 1 && vegetables.size() == 1 ){
			result = new MixPizza(8);
		} else if (potions.size()==1 && ore.size() == 1 && scrolls.size() ==1 && water.size() == 1 && seeds.size() == 1 ){
			result = new ScrollOfRemoveCurse();
		} else if ( potions.size()==1 && ore.size() == 1 && scrolls.size() ==1 && water.size() == 1 ){
			result = new ScrollOfIdentify();
		} else if ( ore.size() == 1 && seeds.size() == 1 && scrolls.size() == 1 ){
			result = new BuildBomb();
		} else if ( water.size() == 1 && fruits.size() == 1 && ore.size() == 1 ){
			result = new FruitCandy(2);
		} else if ( bbomb.size() == 1 && seeds.size() == 2 ){
			result = Generator.random( Generator.Category.BOMBS );
	    } else if ( bbomb.size() == 2 ){
		    result = new HugeBomb();
		} else if ( darkgold.size() == 3  && ore.size() == 1){
			result = new PocketBall();
        } else if ( ore.size() == 2 ){
            result = new HeavyAmmo();
		} else if ( ore.size() == 1 && nutseed.size()==1){
			result = new WoodenAmmo();
        } else if ( ore.size() == 1 && fireseed.size() == 1 ){
            result = new FireAmmo();
        } else if ( ore.size() == 1 && iceseed.size() == 1 ){
            result = new IceAmmo();
        } else if (ore.size() == 1 && stormseed.size() == 1  ){
            result = new StormAmmo();
        } else if (ore.size() == 1 && mossseed.size() == 1 ){
            result = new MossAmmo();
        } else if ( ore.size() == 1 && blindseed.size() == 1 ){
            result = new BlindAmmo();
        } else if ( ore.size() == 1 && starseed.size() == 1 ){
            result = new StarAmmo();
        } else if ( ore.size() == 1 && dreamseed.size() == 1  ){
			result = new DreamAmmo();
		} else if ( ore.size() == 1 && dewseed.size() == 1  ){
			result = new DewAmmo();
		} else if ( ore.size() == 1 && sunseed.size() == 1  ){
			result = new SunAmmo();
		} else if ( ore.size() == 1 && sandseed.size() == 1  ){
			result = new SandAmmo();
		} else if ( ore.size() == 1 && seedseed.size() == 1  ){
			result = new GoldAmmo();
		} else if ( ore.size() == 1 && (rotseed.size() == 1 || freseed.size() == 1 )){
			result = new RotAmmo();
		} else if ( ore.size() == 1 && rootseed.size() == 1  ){
			result = new ThornAmmo();
		} else if ( ore.size() == 1 && blandseed.size() == 1  ){
			result = new EmptyAmmo();
		} else if ( ore.size() == 1 && phaseseed.size() == 1  ){
			result = new EvolveAmmo();
		} else if ( ore.size() == 1 && trapseed.size() == 1  ){
			result = new BattleAmmo();
		} else if ( ws.size() == 1 && sty.size() == 1  ){
			result = new GreatRune();
		} else if ( water.size() == 1 && meatfoods.size() == 1  ){
			result = new Mediummeat();
		} else if ( miscroll.size() == 1  ){
			result = new GreatRune();
		} else if ( gels.size() == 1  ){
			result = new Torch();
		} else if (inputs[1].item != null && item1.getClass() == inputs[1].item.getClass()) {
			result = item1.isUpgradable() ? item1.upgrade(1) : new Garbage(2);
		} else if (equip.size() == 1 && water.size() > 0 ){
				result = (water.size() * 15 > Random.Int(100))? item1.upgrade(1) : new Garbage();
		} else if (equip.size() == 1){
			result = new Garbage(2);
		} else result = new Garbage(item.size());

		if (result != null){
			bubbleEmitter.start(Speck.factory( Speck.BUBBLE ), 0.2f, 10 );
			smokeEmitter.burst(Speck.factory( Speck.WOOL ), 10 );
			Sample.INSTANCE.play( Assets.SND_PUFF );

			output.item(result);
			//if (!result.collect()){
            Dungeon.level.drop(result, hero.pos);
			//}
			for (int i = 0; i < (inputs.length ); i++){
				inputs[i].slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
				inputs[i].item = null;
			}

			btnCombine.enable(false);
		}

	}

	@Override
	public void onBackPressed() {
		for (int i = 0; i < (inputs.length ); i++) {
			if (inputs[i].item != null){
				if (!inputs[i].item.collect()){
					Dungeon.level.drop(inputs[i].item, hero.pos);
				}
			}
		}
		super.onBackPressed();
	}
}
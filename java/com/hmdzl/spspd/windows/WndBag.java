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

import android.graphics.RectF;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.hero.Belongings;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.items.AdamantArmor;
import com.hmdzl.spspd.items.AdamantRing;
import com.hmdzl.spspd.items.AdamantWand;
import com.hmdzl.spspd.items.AdamantWeapon;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.ChangeEquip;
import com.hmdzl.spspd.items.EquipableItem;
import com.hmdzl.spspd.items.Garbage;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.GreatRune;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.SpecialCoin;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.StrBottle;
import com.hmdzl.spspd.items.Stylus;
import com.hmdzl.spspd.items.Torch;
import com.hmdzl.spspd.items.Weightstone;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.bags.ArrowCollecter;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.items.bags.HeartOfScarecrow;
import com.hmdzl.spspd.items.bags.KeyRing;
import com.hmdzl.spspd.items.bags.PotionBandolier;
import com.hmdzl.spspd.items.bags.ScrollHolder;
import com.hmdzl.spspd.items.bags.SeedPouch;
import com.hmdzl.spspd.items.bags.ShoppingCart;
import com.hmdzl.spspd.items.bags.WandHolster;
import com.hmdzl.spspd.items.bombs.BuildBomb;
import com.hmdzl.spspd.items.challengelists.ChallengeList;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.WaterItem;
import com.hmdzl.spspd.items.food.completefood.Gel;
import com.hmdzl.spspd.items.food.fruit.Fruit;
import com.hmdzl.spspd.items.food.meatfood.MeatFood;
import com.hmdzl.spspd.items.food.staplefood.StapleFood;
import com.hmdzl.spspd.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.items.journalpages.JournalPage;
import com.hmdzl.spspd.items.medicine.Greaterpill;
import com.hmdzl.spspd.items.nornstone.NornStone;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.potions.PotionOfStrength;
import com.hmdzl.spspd.items.quest.DarkGold;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.summon.Honeypot;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.Handcannon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.missiles.MegaCannon;
import com.hmdzl.spspd.items.weapon.missiles.TaurcenBow;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.MeleeThrowWeapon;
import com.hmdzl.spspd.items.weapon.ranges.RangeWeapon;
import com.hmdzl.spspd.items.weapon.rockcode.RockCode;
import com.hmdzl.spspd.items.weapon.spammo.SpAmmo;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.Plant.Seed;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.Icons;
import com.hmdzl.spspd.ui.ItemSlot;
import com.hmdzl.spspd.ui.QuickSlotButton;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class WndBag extends WndTabbed {

	public enum Mode {
		ALL,
		NOTEQUIP,
		UNIDENTIFED, 
		UPGRADEABLE, 
		QUICKSLOT, 
		FOR_SALE, 
		WEAPON, 
		ARMOR, 
		ENCHANTABLE, 
		WAND, 
		SEED, 
		FOOD, 
		POTION, 
		SCROLL, 
		EQUIPMENT, 
		ADAMANT, 
		REINFORCED, 
		UPGRADEABLESIMPLE,
		NOTREINFORCED, 
		UPGRADEDEW, 
		JOURNALPAGES, 
		SHOES, 
		COOKING, 
		CHALLENGELIST,
		CANBEMIX,
		STONE,
		TRANMSUTABLE,
		AMMO,
		EATABLE,
		HOLY_MACE,
		IRON_MAKE,
		COOKING_A,

		TEST_RECOVER,
		ROCK_CODE
    }

	protected static final int COLS_P = 5;
	protected static final int COLS_L = 8;

	//protected static final int SLOT_SIZE = 26;
	protected static final int SLOT_SIZE = 20;
	protected static final int SLOT_MARGIN = 1;

	protected static final int TITLE_HEIGHT = 12;

	private Listener listener;
	private WndBag.Mode mode;
	private String title;

	private ItemSelector selector;

	private int nCols;
	private int nRows;

	protected int count;
	protected int col;
	protected int row;

	private static Mode lastMode;
	private static Bag lastBag;

	public WndBag(Bag bag, Listener listener, Mode mode, String title) {

		super();

		this.listener = listener;
		this.mode = mode;
		this.title = title;

		lastMode = mode;
		lastBag = bag;

		nCols = ShatteredPixelDungeon.landscape() ? COLS_L : COLS_P;
		nRows = (Belongings.BACKPACK_SIZE + 5 + 2) / nCols
						+ ((Belongings.BACKPACK_SIZE + 5 + 2) % nCols > 0 ? 1 : 0);

		int slotsWidth = SLOT_SIZE * nCols + SLOT_MARGIN * (nCols -1 );
		int slotsHeight = SLOT_SIZE * nRows + SLOT_MARGIN * (nRows -1 );

		RenderedText txtTitle = PixelScene.renderText(title != null ? title
				: Messages.titleCase( bag.name() ), 9);
		txtTitle.hardlight(TITLE_COLOR);
		txtTitle.x = (int) (slotsWidth - txtTitle.width()) / 2;
		txtTitle.y = (int) (TITLE_HEIGHT - txtTitle.height()) / 2;
		add(txtTitle);

		placeItems(bag);

		resize(slotsWidth, slotsHeight + TITLE_HEIGHT);

		Belongings stuff = hero.belongings;
		
		ArrayList<Bag> bags = new ArrayList<>();
		
        bags.add( stuff.backpack );
        bags.add( stuff.getItem(SeedPouch.class));
        bags.add( stuff.getItem(ScrollHolder.class));
        bags.add( stuff.getItem(PotionBandolier.class) );
        bags.add( stuff.getItem(WandHolster.class) );
        bags.add( stuff.getItem(KeyRing.class));
        bags.add( stuff.getItem(ShoppingCart.class));
        bags.add( stuff.getItem(HeartOfScarecrow.class));	
		bags.add( stuff.getItem(ArrowCollecter.class));

        while(bags.remove(null));

        int tabWidth = ( slotsWidth + 12 ) / bags.size() ;

		for (Bag b : bags) {
            BagTab tab = new BagTab( b );
            tab.setSize( tabWidth, tabHeight() );
            add( tab );

            tab.select( b == bag );
		}
			
		/*Bag[] bags = { stuff.backpack, stuff.getItem(SeedPouch.class),
				stuff.getItem(ScrollHolder.class),
				stuff.getItem(PotionBandolier.class),
				stuff.getItem(WandHolster.class), 
				stuff.getItem(KeyRing.class), 
				stuff.getItem(ShoppingCart.class),
				stuff.getItem(HeartOfScarecrow.class)};

		for (Bag b : bags) {
			if (b != null) {
				BagTab tab = new BagTab(b);
				add(tab);
				tab.select(b == bag);
			}
		}

		layoutTabs();*/
	}

	public static WndBag lastBag(Listener listener, Mode mode, String title) {

		if (mode == lastMode && lastBag != null
				&& hero.belongings.backpack.contains(lastBag)) {

			return new WndBag(lastBag, listener, mode, title);

		} else {

			return new WndBag(hero.belongings.backpack, listener, mode,
					title);

		}
	}

	public static WndBag getBag(Class<? extends Bag> bagClass,
			Listener listener, Mode mode, String title) {
		Bag bag = hero.belongings.getItem(bagClass);
		return bag != null ? new WndBag(bag, listener, mode, title) : lastBag(
				listener, mode, title);
	}

	protected void placeItems(Bag container) {

		// Equipped items
		Belongings stuff = hero.belongings;
		placeItem(stuff.weapon != null ? stuff.weapon : new Placeholder(
				ItemSpriteSheet.WEAPON_HOLDER));
		placeItem(stuff.armor != null ? stuff.armor : new Placeholder(
				ItemSpriteSheet.ARMOR_HOLDER));
		placeItem(stuff.misc1 != null ? stuff.misc1 : new Placeholder(
				ItemSpriteSheet.RING_HOLDER));
		placeItem(stuff.misc2 != null ? stuff.misc2 : new Placeholder(
				ItemSpriteSheet.RING_HOLDER));
		placeItem(stuff.misc3 != null ? stuff.misc3 : new Placeholder(
				ItemSpriteSheet.RING_HOLDER));

		if (ShatteredPixelDungeon.landscape()) {
			placeItem(new ChangeEquip());
			placeItem(stuff.weapon_two != null ? stuff.weapon_two : new Placeholder(
					ItemSpriteSheet.WEAPON_HOLDER));
			placeItem(stuff.armor_two != null ? stuff.armor_two : new Placeholder(
					ItemSpriteSheet.ARMOR_HOLDER));
		} else {
			placeItem(stuff.weapon_two != null ? stuff.weapon_two : new Placeholder(
					ItemSpriteSheet.WEAPON_HOLDER));
			placeItem(stuff.armor_two != null ? stuff.armor_two : new Placeholder(
					ItemSpriteSheet.ARMOR_HOLDER));
			placeItem(new ChangeEquip());
		}
		boolean backpack = (container == hero.belongings.backpack);
		if (ShatteredPixelDungeon.landscape()) {
			count = nCols;
			col = 0;
			row = 1;
		} else {
			count = nCols;
			col = 0;
			row = 2;
		}

		if (container != Dungeon.hero.belongings.backpack){
			placeItem(container);
			count--; //don't count this one, as it's not actually inside of itself
		}

		// Items in the bag
		for (Item item : container.items.toArray(new Item[0])) {
			//placeItem(item);
			if (!(item instanceof Bag)) {
				placeItem( item );
			} else {
				count++;
				//hero.belongings.backpack.size++;
			}
		}

		// Free Space
		while (count - (backpack ? 5 : nCols) < container.size) {
			placeItem(null);
		}

		// Gold
		if (ShatteredPixelDungeon.landscape()) {
			row = nRows - 1;
			col = nCols - 1;
			placeItem(new Gold(Dungeon.gold));
		} else  {
			row = 1;
			col = 4;
			placeItem(new Gold(Dungeon.gold));
		}

		if (ShatteredPixelDungeon.landscape()) {
			row = nRows - 1;
			col = nCols - 2;
			placeItem(new SpecialCoin(0));
		} else  {
			row = 1;
			col = 3;
			placeItem(new SpecialCoin(0));
		}
	}

	protected void placeItem(final Item item) {

		int x = col * (SLOT_SIZE + SLOT_MARGIN);
		int y = TITLE_HEIGHT + row * (SLOT_SIZE + SLOT_MARGIN);

		add(new ItemButton(item).setPos(x, y));

		if (++col >= nCols) {
			col = 0;
			row++;
		}

		count++;
	}

	@Override
	public void onMenuPressed() {
		if (listener == null) {
			hide();
		}
	}

	@Override
	public void onBackPressed() {
		if (listener != null) {
			listener.onSelect(null);
		}
		super.onBackPressed();
	}

	@Override
	protected void onClick(Tab tab) {
		hide();
		GameScene.show(new WndBag(((BagTab) tab).bag, listener, mode, title));
	}

	@Override
	protected int tabHeight() {
		//return 20;
		return 24;
	}

	private class BagTab extends Tab {

		private Image icon;

		private Bag bag;

		public BagTab(Bag bag) {
			super();

			this.bag = bag;

			icon = icon();
			add(icon);
		}

		@Override
		protected void select(boolean value) {
			super.select(value);
			icon.am = selected ? 1.0f : 0.6f;
		}

		@Override
		protected void layout() {
			super.layout();

			icon.copy(icon());
			icon.x = x + (width - icon.width) / 2;

			icon.y = y + (height - icon.height) / 2 - 2 - (selected ? 0 : 1);

			if (!selected && icon.y < y + CUT) {
				RectF frame = icon.frame();
				frame.top += (y + CUT - icon.y) / icon.texture.height;
				icon.frame(frame);
				icon.y = y + CUT;
			}
		}

		private Image icon() {
			if (bag instanceof SeedPouch) {
				return Icons.get(Icons.SEED_POUCH);
			} else if (bag instanceof ScrollHolder) {
				return Icons.get(Icons.SCROLL_HOLDER);
			} else if (bag instanceof WandHolster) {
				return Icons.get(Icons.WAND_HOLSTER);
			} else if (bag instanceof PotionBandolier) {
				return Icons.get(Icons.POTION_BANDOLIER);
			} else if (bag instanceof ShoppingCart) {
				return Icons.get(Icons.SHOP_CART);
			} else if (bag instanceof KeyRing) {
				return Icons.get(Icons.KEYRING);
			} else if (bag instanceof HeartOfScarecrow) {
				return Icons.get(Icons.HOS);	
			} else if (bag instanceof ArrowCollecter) {
				return Icons.get(Icons.ARROW_C);
			} else {
				return Icons.get(Icons.BACKPACK);
			}
		}
	}

	public static class Placeholder extends Item {
		{
			name = null;
		}

		public Placeholder(int image) {
			this.image = image;
		}

		@Override
		public boolean isIdentified() {
			return true;
		}

		@Override
		public boolean isEquipped(Hero hero) {
			return true;
		}
	}

	private class ItemButton extends ItemSlot {

		private static final int NORMAL = 0xFF4A4D44;
		private static final int EQUIPPED = 0xFF63665B;

		private Item item;
		private ColorBlock bg;

		public ItemButton(Item item) {

			super(item);

			this.item = item;
			if (item instanceof Gold || item instanceof SpecialCoin || item instanceof ChangeEquip || item instanceof Bag) {
				bg.visible = false;
			}

			width = height = SLOT_SIZE;
		}

		@Override
		protected void createChildren() {
			bg = new ColorBlock(SLOT_SIZE, SLOT_SIZE, NORMAL);
			add(bg);

			super.createChildren();
		}

		@Override
		protected void layout() {
			bg.x = x;
			bg.y = y;

			super.layout();
		}

		@Override
		public void item(Item item) {

			super.item(item);
			if (item != null) {

				bg.texture(TextureCache.createSolid(item
						.isEquipped(hero) ? EQUIPPED : NORMAL));
				if (item.cursed && item.cursedKnown) {
					bg.ra = +0.2f;
					bg.ga = -0.1f;
				} else if (!item.isIdentified()) {
					bg.ra = 0.1f;
					bg.ba = 0.1f;
				}

				if (item.name() == null) {
					enable(false);
				} else {
					
					 int levelLimit = Math.max(3, 3+Math.round((Statistics.deepestFloor-2)/2));
				     if (hero.heroClass == HeroClass.MAGE){levelLimit++;}
					
					enable(
					mode == Mode.FOR_SALE 
					    && (item.price() > 0) && (!item.isEquipped(hero) || !item.cursed)
				 || mode == Mode.UPGRADEABLE
			            && ((item.isUpgradable() && item.level<15 && !item.isReinforced()) || item.isUpgradable() && item.isReinforced())
				 || mode == Mode.UPGRADEDEW
						&& (item.isUpgradable() && item.level < levelLimit)	
				 || mode == Mode.UPGRADEABLESIMPLE
						&& item.isUpgradable()			
				 || mode == Mode.ADAMANT
						&& (item instanceof AdamantArmor || item instanceof AdamantRing || item instanceof AdamantWand || item instanceof AdamantWeapon)
				 || mode == Mode.REINFORCED
						&& item.isReinforced()
				 || mode == Mode.NOTREINFORCED
						&& (!item.isReinforced() && item.isUpgradable())
				 || mode == Mode.UNIDENTIFED
						&& !item.isIdentified()
				 || mode == Mode.QUICKSLOT
						&& (item.defaultAction != null)
				 || mode == Mode.WEAPON
						&& ((item instanceof MeleeWeapon || item instanceof Boomerang || item instanceof ManyKnive || item instanceof RangeWeapon || item instanceof TaurcenBow || item instanceof MegaCannon || item instanceof MeleeThrowWeapon)&& !(item instanceof Handcannon))
				 || mode == Mode.ARMOR
						&& (item instanceof Armor)
				 || mode == Mode.ENCHANTABLE
						&& (item instanceof MeleeWeapon || item instanceof Boomerang || item instanceof MeleeThrowWeapon || item instanceof RangeWeapon || item instanceof Armor || item instanceof ManyKnive || item instanceof TaurcenBow  || item instanceof MegaCannon)
				 || mode == Mode.JOURNALPAGES
						&& (item instanceof JournalPage)
                 || mode == Mode.WAND
				        && (item instanceof Wand)
				 || mode == Mode.SEED 
				        && (item instanceof Seed)
				 || mode == Mode.FOOD 
				        && (item instanceof Food)
				 || mode == Mode.POTION 
				        && (item instanceof Potion)
				 || mode == Mode.SCROLL 
				        && (item instanceof Scroll)
				 || mode == Mode.EQUIPMENT
						&& (item instanceof EquipableItem)
				 || mode == Mode.COOKING
						&& (item instanceof Food ||item instanceof Plant.Seed ||item instanceof StoneOre || item instanceof Honeypot
							|| item instanceof Honeypot.ShatteredPot || item instanceof Potion || item instanceof Scroll )
				 || mode == Mode.CHALLENGELIST
						&& (item instanceof ChallengeList)
				 || mode == Mode.CANBEMIX
							&& ( !item.isEquipped(hero) && (item instanceof Ring || item instanceof Wand))
				 || mode == Mode.STONE
							&& ( item instanceof NornStone || item instanceof StoneOre)
				|| mode == Mode.TRANMSUTABLE
							&& (!item.isEquipped(hero) && ( item instanceof MeleeWeapon || item instanceof Ring || item instanceof Wand || item instanceof Artifact
							|| item instanceof Armor || item instanceof StrBottle || item instanceof MeleeThrowWeapon))
				|| mode == Mode.AMMO
						&& (item instanceof SpAmmo)
				|| mode == Mode.ROCK_CODE
						&& (item instanceof RockCode)
				|| mode == Mode.EATABLE
						&& (!item.isEquipped(hero) && (item instanceof MeleeWeapon || item instanceof Ring || item instanceof Wand || item instanceof Armor || item instanceof MeleeThrowWeapon || item instanceof RangeWeapon || item instanceof GunWeapon))
				|| mode == Mode.HOLY_MACE
						&& (item instanceof Torch || item instanceof GreatRune || item instanceof Greaterpill || item instanceof PotionOfStrength)
				|| mode == Mode.IRON_MAKE
						&& ((item.isUpgradable() && !item.isEquipped(hero)) || item instanceof WaterItem || item instanceof StoneOre || item instanceof Garbage
							|| item instanceof Plant.Seed || item instanceof BuildBomb ||item instanceof Weightstone ||item instanceof Stylus
							|| item instanceof Gel || item instanceof Scroll || item instanceof Potion || item instanceof DarkGold || item instanceof Nut
							|| item instanceof Fruit || item instanceof Vegetable || item instanceof StapleFood || item instanceof MeatFood )
				|| mode == Mode.COOKING_A
						&& (item instanceof Food ||item instanceof Plant.Seed ||item instanceof StoneOre || item instanceof Honeypot
							|| item instanceof Honeypot.ShatteredPot || item instanceof Potion || item instanceof Scroll || item instanceof Ankh)
				|| mode == Mode.TEST_RECOVER
							&& (!item.isunique() && item instanceof EquipableItem)

				|| mode == Mode.NOTEQUIP
							&& (!item.isEquipped(hero))
				||mode == Mode.ALL );
				}
			} else {
				bg.color(NORMAL);
			}
		}

		@Override
		protected void onTouchDown() {
			bg.brightness(1.5f);
			Sample.INSTANCE.play(Assets.SND_CLICK, 0.7f, 0.7f, 1.2f);
		}

        @Override
		protected void onTouchUp() {
			bg.brightness(1.0f);
		}

        @Override
		protected void onClick() {
			if (listener != null) {

				hide();
				listener.onSelect(item);

			} else {

				WndBag.this.add(new WndItem(WndBag.this, item));

			}
		}

		@Override
		protected boolean onLongClick() {
			if (listener == null && item.defaultAction != null) {
				hide();
				Dungeon.quickslot.setSlot(0, item);
				QuickSlotButton.refresh();
				return true;
			} else {
				return false;
			}
		}
	}

	public interface Listener {
		void onSelect(Item item);
	}

	public abstract static class ItemSelector {
		public abstract String textPrompt();
		public Class<?extends Bag> preferredBag(){
			return null; //defaults to last bag opened
		}
		public abstract boolean itemSelectable( Item item );
		public abstract void onSelect( Item item );
	}

}

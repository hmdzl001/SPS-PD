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
package com.hmdzl.spspd.change.windows;

import android.graphics.RectF;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ShatteredPixelDungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.hero.Belongings;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.items.AdamantArmor;
import com.hmdzl.spspd.change.items.AdamantRing;
import com.hmdzl.spspd.change.items.AdamantWand;
import com.hmdzl.spspd.change.items.AdamantWeapon;
import com.hmdzl.spspd.change.items.EquipableItem;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.food.WaterItem;
import com.hmdzl.spspd.change.items.bags.HeartOfScarecrow;
import com.hmdzl.spspd.change.items.rings.Ring;
import com.hmdzl.spspd.change.items.summon.Honeypot;
import com.hmdzl.spspd.change.items.bombs.BuildBomb;
import com.hmdzl.spspd.change.items.challengelists.ChallengeList;
import com.hmdzl.spspd.change.items.misc.JumpH;
import com.hmdzl.spspd.change.items.misc.JumpM;
import com.hmdzl.spspd.change.items.misc.JumpR;
import com.hmdzl.spspd.change.items.misc.JumpS;
import com.hmdzl.spspd.change.items.misc.JumpW;
import com.hmdzl.spspd.change.items.misc.Jumpshoes;
import com.hmdzl.spspd.change.items.weapon.melee.special.Handcannon;
import com.hmdzl.spspd.change.items.misc.MissileShield;
import com.hmdzl.spspd.change.items.weapon.spammo.SpAmmo;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.bags.ShoppingCart;
import com.hmdzl.spspd.change.items.bags.Bag;
import com.hmdzl.spspd.change.items.bags.KeyRing;
import com.hmdzl.spspd.change.items.bags.PotionBandolier;
import com.hmdzl.spspd.change.items.bags.ScrollHolder;
import com.hmdzl.spspd.change.items.bags.SeedPouch;
import com.hmdzl.spspd.change.items.bags.WandHolster;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.items.journalpages.JournalPage;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.plants.Plant.Seed;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.Icons;
import com.hmdzl.spspd.change.ui.ItemSlot;
import com.hmdzl.spspd.change.ui.QuickSlotButton;
 
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class WndBag extends WndTabbed {

	public static enum Mode {
		ALL, 
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
		AMMO;
	}

	protected static final int COLS_P = 5;
	protected static final int COLS_L = 6;

	//protected static final int SLOT_SIZE = 26;
	protected static final int SLOT_SIZE = 24;
	protected static final int SLOT_MARGIN = 1;

	protected static final int TITLE_HEIGHT = 12;

	private Listener listener;
	private WndBag.Mode mode;
	private String title;

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
		nRows = (Belongings.BACKPACK_SIZE + 5 + 1) / nCols
				+ ((Belongings.BACKPACK_SIZE + 5 + 1) % nCols > 0 ? 1 : 0);

		int slotsWidth = SLOT_SIZE * nCols + SLOT_MARGIN * (nCols - 1);
		int slotsHeight = SLOT_SIZE * nRows + SLOT_MARGIN * (nRows - 1);

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

		boolean backpack = (container == hero.belongings.backpack);
		if (!backpack) {
			count = nCols;
			col = 0;
			row = 1;
		}

		// Items in the bag
		for (Item item : container.items) {
			placeItem(item);
		}

		// Free Space
		while (count - (backpack ? 5 : nCols) < container.size) {
			placeItem(null);
		}

		// Gold
		if (container == hero.belongings.backpack) {
			row = nRows - 1;
			col = nCols - 1;
			placeItem(new Gold(Dungeon.gold));
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
			if (item instanceof Gold) {
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
					
					 int levelLimit = Math.max(2, 2+Math.round(Statistics.deepestFloor/3));
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
						&& ((item instanceof MeleeWeapon || item instanceof Boomerang || item instanceof MissileShield)&& !(item instanceof Handcannon))
				 || mode == Mode.ARMOR
						&& (item instanceof Armor)
				 || mode == Mode.ENCHANTABLE
						&& (item instanceof MeleeWeapon	|| item instanceof Boomerang || item instanceof Armor )
				 || mode == Mode.JOURNALPAGES
						&& (item instanceof JournalPage)
				 || mode == Mode.SHOES
						&& (item instanceof JumpW || item instanceof JumpM || item instanceof JumpR || item instanceof JumpH || item instanceof JumpS || item instanceof Jumpshoes)
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
						&& (item instanceof Food ||item instanceof Plant.Seed ||item instanceof WaterItem ||item instanceof StoneOre || item instanceof Honeypot || item instanceof Honeypot.ShatteredPot || item instanceof Potion || item instanceof Scroll || item instanceof BuildBomb)
				 || mode == Mode.CHALLENGELIST
						&& (item instanceof ChallengeList)
				 || mode == Mode.CANBEMIX
							&& ( !item.isEquipped(hero) && (item instanceof Ring || item instanceof Wand))
				 || mode == Mode.AMMO
						&& (item instanceof SpAmmo)
				 || mode == Mode.ALL);
				}
			} else {
				bg.color(NORMAL);
			}
		}

		@Override
		protected void onTouchDown() {
			bg.brightness(1.5f);
			Sample.INSTANCE.play(Assets.SND_CLICK, 0.7f, 0.7f, 1.2f);
		};

		@Override
		protected void onTouchUp() {
			bg.brightness(1.0f);
		};

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
}

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
package com.hmdzl.spspd.change.ui;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.keys.Key;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.rings.Ring;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
 
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Button;

public class ItemSlot extends Button {

	public static final int DEGRADED = 0xFF4444;
	public static final int UPGRADED = 0x44FF44;
	public static final int WARNING = 0xFF8800;

	private static final float ENABLED = 1.0f;
	private static final float DISABLED = 0.3f;

	protected ItemSprite icon;
	protected BitmapText topLeft;
	protected BitmapText topRight;
	protected BitmapText bottomRight;
	protected Image      bottomRightIcon;
	protected BitmapText bottomLeft;
	protected Image      bottomLeftIcon;
    protected boolean    iconVisible = true;
	protected boolean    iconVisible2 = true;

	private static final String TXT_STRENGTH = ":%d";
	private static final String TXT_TYPICAL_STR = "%d?";
	private static final String TXT_KEY_DEPTH = "\u007F%d";

	private static final String TXT_LEVEL = "%+d";
	private static final String TXT_CURSED = "";// "-";

	// Special "virtual items"
	public static final Item CHEST = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.CHEST;
		};
	};
	public static final Item LOCKED_CHEST = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.LOCKED_CHEST;
		};
	};
	public static final Item CRYSTAL_CHEST = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.CRYSTAL_CHEST;
		};
	};
	public static final Item TOMB = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.TOMB;
		};
	};
	public static final Item SKELETON = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.BONES;
		};
	};
	public static final Item REMAINS = new Item() {
		@Override
		public int image() {
			return ItemSpriteSheet.REMAINS;
		};
	};

	public ItemSlot() {
		super();
	}

	public ItemSlot(Item item) {
		this();
		item(item);
	}

	@Override
	protected void createChildren() {

		super.createChildren();

		icon = new ItemSprite();
		add(icon);

		topLeft = new BitmapText(PixelScene.font1x);
		add(topLeft);

		topRight = new BitmapText(PixelScene.font1x);
		add(topRight);

		bottomRight = new BitmapText(PixelScene.font1x);
		add(bottomRight);
		
		bottomLeft = new BitmapText(PixelScene.font1x);
		add(bottomLeft);
	}

	@Override
	protected void layout() {
		super.layout();

		icon.x = x + (width - icon.width) / 2;
		icon.y = y + (height - icon.height) / 2;

		if (topLeft != null) {
			topLeft.x = x;
			topLeft.y = y;
		}

		if (topRight != null) {
			topRight.x = x + (width - topRight.width());
			topRight.y = y;
		}

		if (bottomRight != null) {
			bottomRight.x = x + (width - bottomRight.width());
			bottomRight.y = y + (height - bottomRight.height());
		}
		
		if (bottomRightIcon != null) {
			bottomRightIcon.x = x + (width - bottomRightIcon.width()) -1;
			bottomRightIcon.y = y + (height - bottomRightIcon.height());
		}
		
		if (bottomLeft != null) {
			bottomLeft.x = x ;
			bottomLeft.y = y + (height - bottomLeft.height());
		}
		
		if (bottomLeftIcon != null) {
			bottomLeftIcon.x = x + 1;
			bottomLeftIcon.y = y + (height - bottomLeftIcon.height());
		}		
	}

	public void item(Item item ) {
		
		if (bottomRightIcon != null){
			remove(bottomRightIcon);
			bottomRightIcon = null;
		}
		
		if (bottomLeftIcon != null){
			remove(bottomLeftIcon);
			bottomLeftIcon = null;
		}		
		
		if (item == null) {

			active = false;
			icon.visible = topLeft.visible = topRight.visible = bottomRight.visible = bottomLeft.visible = false;

		} else {

			active = true;
			icon.visible = topLeft.visible = topRight.visible = bottomRight.visible = bottomLeft.visible = true;

			icon.view(item.image(), item.glowing());

			topLeft.text(item.status());

			boolean isArmor = item instanceof Armor;
			boolean isWeapon = item instanceof Weapon;
			if (isArmor || isWeapon) {

				if (item.levelKnown
						|| (isWeapon && !(item instanceof MeleeWeapon))) {

					int str = isArmor ? ((Armor) item).STR()
							: ((Weapon) item).STR();
					topRight.text( Messages.format( TXT_STRENGTH, str ));
					if (str > Dungeon.hero.STR()) {
						topRight.hardlight(DEGRADED);
					} else {
						topRight.resetColor();
					}

				} else {

					topRight.text(Messages.format(TXT_TYPICAL_STR,
							isArmor ? ((Armor) item).typicalSTR()
									: ((MeleeWeapon) item).typicalSTR()));
					topRight.hardlight(WARNING);

				}
				topRight.measure();

			} else if (item instanceof Key && !(item instanceof SkeletonKey)) {
				topRight.text(Messages.format(TXT_KEY_DEPTH, ((Key) item).depth));
				topRight.measure();
			} else {

				topRight.text(null);

			}

			int level = item.visiblyUpgraded();

			if (level != 0) {
				bottomRight.text(item.levelKnown ? Messages.format(TXT_LEVEL,
						level) : TXT_CURSED);
				bottomRight.measure();
				bottomRight.hardlight(level > 0 ? UPGRADED : DEGRADED);
				
		        } else if (item instanceof Scroll || item instanceof Potion) {
				
			        bottomRight.text( null );

			    Integer iconInt;
			    if (item instanceof Scroll){
				    iconInt = ((Scroll) item).initials();
			    } else {
				    iconInt = ((Potion) item).initials();
			    }
			    if (iconInt != null && iconVisible) {
				    bottomRightIcon = new Image(Assets.CONS_ICONS);
				    int left = iconInt*7;
				    int top = item instanceof Potion ? 0 : 7;
				    bottomRightIcon.frame(left, top, 7, 7);
				    add(bottomRightIcon);
			    } else {
			        bottomRight.text( null );
		        }

		    }
			layout();
		}
	}

	public void enable(boolean value) {

		active = value;

		float alpha = value ? ENABLED : DISABLED;
		icon.alpha(alpha);
		topLeft.alpha(alpha);
		topRight.alpha(alpha);
		bottomRight.alpha(alpha);
		bottomLeft.alpha(alpha);
		if (bottomLeftIcon != null) bottomRightIcon.alpha( alpha );
		if (bottomLeftIcon != null) bottomLeftIcon.alpha( alpha );
	}

	public void showParams( boolean TL, boolean TR, boolean BR, boolean BF ) {
		if (TL) add( topLeft );
		else remove( topLeft );

		if (TR) add( topRight );
		else remove( topRight );

		if (BR) add( bottomRight );
		else remove( bottomRight );
		
		if (BF) add( bottomLeft );
		else remove( bottomLeft );
 		
		iconVisible = BR;
		iconVisible2 = BF;
	}
}

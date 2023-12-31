/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.infos.NewCatalog;
import com.hmdzl.spspd.infos.NewDocument;
import com.hmdzl.spspd.infos.NewMobCatalog;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.sprites.MobSprite;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.ScrollPane;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;
import java.util.HashMap;

//import com.watabou.noosa.BitmapText;

//FIXME lots of copy-pasta from WndDocument here. should generalize some of this. Primarily ListItem2
public class WndDocument extends WndTabbed {
	
	private static final int WIDTH_P    = 120;
	private static final int HEIGHT_P   = 160;
	
	private static final int WIDTH_L    = 160;
	private static final int HEIGHT_L   = 128;
	
	private static final int ITEM_HEIGHT	= 18;

	private NotesTab notesTab;
	private CatalogTab catalogTab;
	private MobCatalogTab mobcatalogTab;
	
	public static int last_index = 0;
	
	public WndDocument(){
		
		int width = ShatteredPixelDungeon.landscape() ? WIDTH_L : WIDTH_P;
		int height = ShatteredPixelDungeon.landscape() ? HEIGHT_L : HEIGHT_P;
		
		resize(width, height);

		notesTab = new NotesTab();
		add(notesTab);
		notesTab.setRect(0, 0, width, height);
		notesTab.updateList();
		
		catalogTab = new CatalogTab();
		add(catalogTab);
		catalogTab.setRect(0, 0, width, height);
		catalogTab.updateList();
		
		mobcatalogTab = new MobCatalogTab();
		add(mobcatalogTab);
		mobcatalogTab.setRect(0, 0, width, height);
		mobcatalogTab.updateList();		
		
		Tab[] tabs = {
				new LabeledTab( Messages.get(WndDocument.class, "notes") ) {
					protected void select( boolean value ) {
						super.select( value );
						notesTab.active = notesTab.visible = value;
						if (value) last_index = 0;
					}
				},
				new LabeledTab( Messages.get(WndDocument.class, "items") ) {
					protected void select( boolean value ) {
						super.select( value );
						catalogTab.active = catalogTab.visible = value;
						if (value) last_index = 1;
					}
				},
				new LabeledTab( Messages.get(WndDocument.class, "mobs") ) {
					protected void select( boolean value ) {
						super.select( value );
						mobcatalogTab.active = mobcatalogTab.visible = value;
						if (value) last_index = 2;
					}
				}				
		};
		
		for (Tab tab : tabs) {
			add( tab );
		}
		
		layoutTabs();
		
		select(last_index);
	}
	
	private static class ListItem2 extends Component {
		
		protected RenderedTextMultiline label;
		//protected BitmapText depth;
		protected ColorBlock line;
		protected Image icon;
		
		public ListItem2( Image icon, String text ) {
			this(icon, text, -1);
		}
		
		public ListItem2( Image icon, String text, int d ) {
			super();
			
			this.icon.copy(icon);
			
			label.text( text );
			
			if (d >= 0) {
				//depth.text(Integer.toString(d));
				//depth.measure();
				
				if (d == Dungeon.dungeondepth) {
					label.hardlight(TITLE_COLOR);
					//depth.hardlight(TITLE_COLOR);
				}
			}
		}
		
		@Override
		protected void createChildren() {
			label = PixelScene.renderMultiline( 7 );
			add( label );
			
			icon = new Image();
			add( icon );
			
		//	depth = new BitmapText( PixelScene.pixelFont);
			//add( depth );
			
			line = new ColorBlock( 1, 1, 0xFF222222);
			add(line);
			
		}
		
		@Override
		protected void layout() {
			
			icon.y = y + 1 + (height() - 1 - icon.height()) / 2f;
			PixelScene.align(icon);
			
			//depth.x = icon.x + (icon.width - depth.width()) / 2f;
			//depth.y = icon.y + (icon.height - depth.height()) / 2f + 1;
			//PixelScene.align(depth);
			
			line.size(width, 1);
			line.x = 0;
			line.y = y;
			
			label.maxWidth((int)(width - icon.width() - 8 - 1));
			label.setPos(icon.x + icon.width() + 1, y + 1 + (height() - label.height()) / 2f);
			PixelScene.align(label);
		}
	}

	private static class ListItem extends Component {

		private Item item;
		private boolean identified;

		private ItemSprite sprite;
		private RenderedText label;

		public ListItem(Class<? extends Item> cl) {
			super();

			try {
				item = cl.newInstance();
				if (identified = item.isIdentified()) {
					sprite.view(item.image(), null);
					label.text(item.name());
				} else {
					sprite.view(0, null);
					label.text(item.trueName());
					label.hardlight(0xCCCCCC);
				}
			} catch (Exception e) {
				// Do nothing
			}
		}

		@Override
		protected void createChildren() {
			sprite = new ItemSprite();
			add(sprite);

			label = PixelScene.renderText(8);
			add(label);
		}

		@Override
		protected void layout() {
			sprite.y = PixelScene.align(y + (height - sprite.height) / 2);

			label.x = sprite.x + sprite.width;
			label.y = PixelScene.align(y + (height - label.baseLine()) / 2);
		}

		public boolean onClick(float x, float y) {
			if (identified && inside(x, y)) {
				GameScene.show(new WndInfoItem(item));
				return true;
			} else {
				return false;
			}
		}
	}
	
	private static class GuideTab extends Component {
		
		private ScrollPane list;
		
		@Override
		protected void createChildren() {
			list = new ScrollPane( new Component() );
			add( list );
		}
		
		@Override
		protected void layout() {
			super.layout();
			list.setRect( 0, 0, width, height);
		}

	}

	private static class GuideItem extends ListItem2 {

		private boolean found = false;
		private String page;

		public GuideItem( String page ){
			super( new ItemSprite( ItemSpriteSheet.NULLWARN, null),
					Messages.titleCase(NewDocument.STORY_GUIDE.pageTitle(page)), -1);

			this.page = page;
			found = NewDocument.STORY_GUIDE.hasPage(page);

			if (!found) {
				icon.hardlight( 0.5f, 0.5f, 0.5f);
				label.text( Messages.titleCase(Messages.get( this, "missing" )));
				label.hardlight( 0x999999 );
			}

		}

		public boolean onClick( float x, float y ) {
			if (inside( x, y ) && found) {
				GameScene.show( new WndStory( NewDocument.STORY_GUIDE.pageBody(page) ));
				return true;
			} else {
				return false;
			}
		}

	}
	
	private static class NotesTab extends Component {
		
		private ScrollPane list;
		private ArrayList<GuideItem> pages = new ArrayList<>();
		
		@Override
		protected void createChildren() {
			list = new ScrollPane( new Component() ){
				@Override
				public void onClick( float x, float y ) {
					int size = pages.size();
					for (int i=0; i < size; i++) {
						if (pages.get( i ).onClick( x, y )) {
							break;
						}
					}
				}
			};
			add( list );
		}
		
		@Override
		protected void layout() {
			super.layout();
			list.setRect( 0, 0, width, height);
		}
		
		private void updateList(){
			Component content = list.content();
			
			float pos = 0;
			
			ColorBlock line = new ColorBlock( width(), 1, 0xFF222222);
			line.y = pos;
			content.add(line);
			
			RenderedTextMultiline title = PixelScene.renderMultiline(NewDocument.STORY_GUIDE.title(), 9);
			title.hardlight(TITLE_COLOR);
			title.maxWidth( (int)width() - 2 );
			title.setPos( (width() - title.width())/2f, pos + 1 + ((ITEM_HEIGHT) - title.height())/2f);
			PixelScene.align(title);
			content.add(title);
			
			pos += Math.max(ITEM_HEIGHT, title.height());
			
			for (String page : NewDocument.STORY_GUIDE.pages()){
				GuideItem item = new GuideItem( page );
				
				item.setRect( 0, pos, width(), ITEM_HEIGHT );
				content.add( item );
				
				pos += item.height();
				pages.add(item);
			}
			
			content.setSize( width(), pos );
			list.setSize( list.width(), list.height() );
		}

	}	
	
	private static class CatalogTab extends Component{
		
		private RedButton[] itemButtons;
		private static final int NUM_BUTTONS = 7;
		
		private static int currentItemIdx   = 0;
		
		private static final int WEAPON_IDX = 0;
		private static final int ARMOR_IDX  = 1;
		private static final int WAND_IDX   = 2;
		private static final int SPECIALS_IDX   = 3;
		private static final int ARTIF_IDX  = 4;
		private static final int FOOD_IDX = 5;
		private static final int PILL_IDX = 6;
		
		private ScrollPane list;
		
		private ArrayList<CatalogItem> items = new ArrayList<>();
		private ArrayList<ListItem> items2 = new ArrayList<WndDocument.ListItem>();
		
		@Override
		protected void createChildren() {
			itemButtons = new RedButton[NUM_BUTTONS];
			for (int i = 0; i < NUM_BUTTONS; i++){
				final int idx = i;
				itemButtons[i] = new RedButton( "" ){
					@Override
					protected void onClick() {
						currentItemIdx = idx;
						updateList();
					}
				};
				//itemButtons[i].icon(new ItemSprite(ItemSpriteSheet.WEAPON_HOLDER + i, null));
				itemButtons[i].icon(new ItemSprite(ItemSpriteSheet.INFO + i , null));
				add( itemButtons[i] );
			}
			
			list = new ScrollPane( new Component() ) {
				@Override
				public void onClick( float x, float y ) {
					int size = items.size();
					for (int i=0; i < size; i++) {
						if (items.get( i ).onClick( x, y )) {
							break;
						}
					}
				}
			};
			add( list );
		}
		
		private static final int BUTTON_HEIGHT = 17;
		
		@Override
		protected void layout() {
			super.layout();
			
			int perRow = NUM_BUTTONS;
			float buttonWidth = width()/perRow;
			
			for (int i = 0; i < NUM_BUTTONS; i++) {
				itemButtons[i].setRect((i%perRow) * (buttonWidth), (i/perRow) * (BUTTON_HEIGHT + 1),
						buttonWidth, BUTTON_HEIGHT);
				PixelScene.align(itemButtons[i]);
			}
			
			list.setRect(0, itemButtons[NUM_BUTTONS-1].bottom() + 1, width,
					height - itemButtons[NUM_BUTTONS-1].bottom() - 1);
		}
		
		private void updateList() {
			items.clear();
			for (int i = 0; i < NUM_BUTTONS; i++){
				if (i == currentItemIdx){
					itemButtons[i].icon().color(TITLE_COLOR);
				} else {
					itemButtons[i].icon().resetColor();
				}
			}
			
			Component content = list.content();
			content.clear();
			list.scrollTo( 0, 0 );
			
			ArrayList<Class<? extends Item>> itemClasses;
			final HashMap<Class<?  extends Item>, Boolean> known = new HashMap<>();
			if (currentItemIdx == WEAPON_IDX) {
				itemClasses = new ArrayList<>(NewCatalog.WEAPONS.items());
                for (Class<? extends Item> cls : itemClasses){
                    known.put(cls, true);
                }
            } else if (currentItemIdx == ARMOR_IDX){
				itemClasses = new ArrayList<>(NewCatalog.ARMOR.items());
				for (Class<? extends Item> cls : itemClasses){
                    known.put(cls, true);
				} 
			} else if (currentItemIdx == WAND_IDX){
				itemClasses = new ArrayList<>(NewCatalog.WANDS.items());
				for (Class<? extends Item> cls : itemClasses) known.put(cls, true);
			} else if (currentItemIdx == SPECIALS_IDX){
				itemClasses = new ArrayList<>(NewCatalog.SPECIALS.items());
				for (Class<? extends Item> cls : itemClasses) known.put(cls, true);
			} else if (currentItemIdx == ARTIF_IDX){
				itemClasses = new ArrayList<>(NewCatalog.ARTIFACTS.items());
				for (Class<? extends Item> cls : itemClasses) known.put(cls, true);
			} else if (currentItemIdx == FOOD_IDX){
				itemClasses = new ArrayList<>(NewCatalog.FOODS.items());
				for (Class<? extends Item> cls : itemClasses) known.put(cls,true);
			} else if (currentItemIdx == PILL_IDX) {
				itemClasses = new ArrayList<>(NewCatalog.PILLS.items());
				for (Class<? extends Item> cls : itemClasses) known.put(cls, true);
			} else {
				itemClasses = new ArrayList<>();
			}
			
			/*Collections.sort(itemClasses, new Comparator<Class<? extends Item>>() {
				@Override
				public int compare(Class<? extends Item> a, Class<? extends Item> b) {
					int result = 0;

					//specifically known items appear first, then seen items, then unknown items.
					if (known.get(a) && NewCatalog.isSeen(a)) result -= 2;
					if (known.get(b) && NewCatalog.isSeen(b)) result += 2;
					if (NewCatalog.isSeen(a))                 result --;
					if (NewCatalog.isSeen(b))                 result ++;

					return result;
				}
			});*/
			
			float pos = 0;
			for (Class<? extends Item> itemClass : itemClasses) {
				try{
					CatalogItem item = new CatalogItem(itemClass.newInstance());
					item.setRect( 0, pos, width, ITEM_HEIGHT );
					content.add( item );
					items.add( item );
					
					pos += item.height();
				} catch (Exception e) {
					ShatteredPixelDungeon.reportException(e);
				}
			}
			
			content.setSize( width, pos );
			list.setSize( list.width(), list.height() );
		}
		
		private static class CatalogItem extends ListItem2 {
			
			private Item item;
			
			public CatalogItem(Item item ) {
				super( new ItemSprite(item), Messages.titleCase(item.trueName()));
				
				this.item = item;
				icon.copy( new ItemSprite( item.image, null) );
				label.hardlight( 0xCCCCCC );
				
			}
			
			public boolean onClick( float x, float y ) {
				if (inside( x, y )) {
					GameScene.show(new WndTitledMessage( new Image(icon),
								Messages.titleCase(item.trueName()), item.desc() ));
					return true;
				} else {
					return false;
				}
			}
		}
		
		
		
	}
	
private static class MobCatalogTab extends Component{
		
		private RedButton[] itemButtons;
		private static final int NUM_BUTTONS = 7;
		
		private static int currentItemIdx   = 0;
		
		private static final int SEWER_IDX = 0;
		private static final int PRISON_IDX  = 1;
		private static final int CAVE_IDX   = 2;
		private static final int CITY_IDX   = 3;
		private static final int HALL_IDX  = 4;
		private static final int EX_IDX = 5;
		private static final int ETC_IDX = 6;
		
		private ScrollPane list;
		
		private ArrayList<MobCatalogItem> mobs = new ArrayList<>();
		private ArrayList<ListItem> items2 = new ArrayList<WndDocument.ListItem>();
		
		@Override
		protected void createChildren() {
			itemButtons = new RedButton[NUM_BUTTONS];
			for (int i = 0; i < NUM_BUTTONS; i++){
				final int idx = i;
				itemButtons[i] = new RedButton( "" ){
					@Override
					protected void onClick() {
						currentItemIdx = idx;
						updateList();
					}
				};
				//itemButtons[i].icon(new ItemSprite(ItemSpriteSheet.WEAPON_HOLDER + i, null));
				itemButtons[i].icon(new ItemSprite(ItemSpriteSheet.INFO2 + i , null));
				add( itemButtons[i] );
			}
			
			list = new ScrollPane( new Component() ) {
				@Override
				public void onClick( float x, float y ) {
					int size = mobs.size();
					for (int i=0; i < size; i++) {
						if (mobs.get( i ).onClick( x, y )) {
							break;
						}
					}
				}
			};
			add( list );
		}
		
		private static final int BUTTON_HEIGHT = 17;
		
		@Override
		protected void layout() {
			super.layout();
			
			int perRow = NUM_BUTTONS;
			float buttonWidth = width()/perRow;
			
			for (int i = 0; i < NUM_BUTTONS; i++) {
				itemButtons[i].setRect((i%perRow) * (buttonWidth), (i/perRow) * (BUTTON_HEIGHT + 1),
						buttonWidth, BUTTON_HEIGHT);
				PixelScene.align(itemButtons[i]);
			}
			
			list.setRect(0, itemButtons[NUM_BUTTONS-1].bottom() + 1, width,
					height - itemButtons[NUM_BUTTONS-1].bottom() - 1);
		}
		
		private void updateList() {
			mobs.clear();
			for (int i = 0; i < NUM_BUTTONS; i++){
				if (i == currentItemIdx){
					itemButtons[i].icon().color(TITLE_COLOR);
				} else {
					itemButtons[i].icon().resetColor();
				}
			}
			
			Component content = list.content();
			content.clear();
			list.scrollTo( 0, 0 );
			
			ArrayList<Class<? extends Mob>> mobClass;
			final HashMap<Class<? extends Mob>, Boolean> known = new HashMap<>();
			if (currentItemIdx == SEWER_IDX) {
				mobClass = new ArrayList<>(NewMobCatalog.SEWER.mob());
                for (Class<? extends Mob> cls : mobClass){
                    known.put(cls, true);
                }
            } else if (currentItemIdx == PRISON_IDX){
				mobClass = new ArrayList<>(NewMobCatalog.PRISON.mob());
				for (Class<? extends Mob> cls : mobClass){
                    known.put(cls, true);
				} 
			} else if (currentItemIdx == CAVE_IDX){
				mobClass = new ArrayList<>(NewMobCatalog.CAVE.mob());
				for (Class<? extends Mob> cls : mobClass) known.put(cls, true);
			} else if (currentItemIdx == CITY_IDX){
				mobClass = new ArrayList<>(NewMobCatalog.CITY.mob());
				for (Class<? extends Mob> cls : mobClass) known.put(cls, true);
			} else if (currentItemIdx == HALL_IDX){
				mobClass = new ArrayList<>(NewMobCatalog.HALL.mob());
				for (Class<? extends Mob> cls : mobClass) known.put(cls, true);
			} else if (currentItemIdx == EX_IDX){
				mobClass = new ArrayList<>(NewMobCatalog.EX.mob());
				for (Class<? extends Mob> cls : mobClass) known.put(cls,true);
			} else if (currentItemIdx == ETC_IDX) {
				mobClass = new ArrayList<>(NewMobCatalog.ETC.mob());
				for (Class<? extends Mob> cls : mobClass) known.put(cls, true);
			} else {
				mobClass = new ArrayList<>();
			}
			
			/*Collections.sort(itemClasses, new Comparator<Class<? extends Item>>() {
				@Override
				public int compare(Class<? extends Item> a, Class<? extends Item> b) {
					int result = 0;

					//specifically known items appear first, then seen items, then unknown items.
					if (known.get(a) && NewCatalog.isSeen(a)) result -= 2;
					if (known.get(b) && NewCatalog.isSeen(b)) result += 2;
					if (NewCatalog.isSeen(a))                 result --;
					if (NewCatalog.isSeen(b))                 result ++;

					return result;
				}
			});*/
			
			float pos = 0;
			for (Class<? extends Mob> itemClass : mobClass) {
				try{
					MobCatalogItem mob = new MobCatalogItem(itemClass.newInstance());
					mob.setRect( 0, pos, width, ITEM_HEIGHT );
					content.add( mob );
					mobs.add( mob );
					
					pos += mob.height();
				} catch (Exception e) {
					ShatteredPixelDungeon.reportException(e);
				}
			}
			
			content.setSize( width, pos );
			list.setSize( list.width(), list.height() );
		}
		
		private static class MobCatalogItem extends ListItem2 {
			
			private Mob mob;
			
			public MobCatalogItem(Mob mob ) {
				super( new MobSprite(), Messages.titleCase(mob.name));
				this.mob = mob;
				//icon.copy( new ItemSprite(new Item(){ {image = ItemSpriteSheet.SOMETHING; }}));
				icon.copy(mob.sprite());
				label.hardlight( 0xCCCCCC );
				
			}
			
			public boolean onClick( float x, float y ) {
				if (inside( x, y )) {
					GameScene.show(new WndTitledMessage( new Image(icon),
								Messages.titleCase(mob.name), mob.description() ));
					return true;
				} else {
					return false;
				}
			}
		}
		
		
		
	}	
	
}



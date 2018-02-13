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
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.ShatteredPixelDungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.scenes.CellSelector;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.windows.WndBag;
import com.hmdzl.spspd.change.windows.WndCatalogus;
import com.hmdzl.spspd.change.windows.WndHero;
import com.hmdzl.spspd.change.windows.WndInfoCell;
import com.hmdzl.spspd.change.windows.WndInfoItem;
import com.hmdzl.spspd.change.windows.WndInfoMob;
import com.hmdzl.spspd.change.windows.WndInfoPlant;
import com.hmdzl.spspd.change.windows.WndMessage;
import com.hmdzl.spspd.change.windows.WndTradeItem;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.Game;
import com.watabou.noosa.Gizmo;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;

public class Toolbar extends Component {

	private Tool btnWait;
	private Tool btnSearch;
	//private Tool btnInfo;
	private Tool btnResume;
	//private Tool btnJump;
	private Tool btnInventory;
	private Tool btnQuick;
	private Tool btnQuick2;
	private Tool btnQuick3;
	private Tool btnQuick4;
	private Tool btnQuick5;
	private Tool btnQuick6;
	public static int QuickSlots;

	private PickedUpItem pickedUp;

	private boolean lastEnabled = true;
	public boolean examining = false;


	public Toolbar() {
		super();

		QuickSlots = ShatteredPixelDungeon.quickSlots();

		height = btnInventory.height();
	}
	
	

	@Override
	protected void createChildren() {

		add(btnWait = new Tool(0, 7, 20, 24) {
			@Override
			protected void onClick() {
				examining = false;
				Dungeon.hero.rest(false);
			};

			@Override
			protected boolean onLongClick() {
				examining = false;
				Dungeon.hero.rest(true);
				return true;
			};
		});
		
		add(btnSearch = new Tool(20, 7, 20, 24) {
			@Override
			protected void onClick() {
				
                if(!examining) {
                    GameScene.selectCell(informer);
					examining = true;
                } else {
					informer.onSelect(null);
                    Dungeon.hero.search(true);
                }
			}
            @Override
            protected boolean onLongClick() {
                Dungeon.hero.search(true); 
				return true;
            };
		} );

		/*add(btnInfo = new Tool(40, 7, 21, 24) {
			@Override
			protected void onClick() {
				GameScene.selectCell(informer);
			};
			
			@Override
			protected boolean onLongClick() {
				Dungeon.hero.rest(true);
				return true;
			};
			
		});

		 * add( btnResume = new Tool( 61, 7, 21, 24 ) {
		 * 
		 * @Override protected void onClick() { Dungeon.hero.resume(); } } );
		 */
		 
		/*add(btnJump = new Tool(40,7,21,24){
			@Override
			protected void onClick() {
				GameScene.selectCell(jumper);
			};
			@Override
            protected boolean onLongClick() {
                Dungeon.hero.search(true); 
				return true;
			};
		});*/
		

		add(btnInventory = new Tool(82, 7, 23, 24) {
			private GoldIndicator gold;

			@Override
			protected void onClick() {
				GameScene.show(new WndBag(Dungeon.hero.belongings.backpack,
						null, WndBag.Mode.ALL, null));
			}

			@Override
			protected boolean onLongClick() {
				GameScene.show(new WndCatalogus());
				return true;
			};

			@Override
			protected void createChildren() {
				super.createChildren();
				gold = new GoldIndicator();
				add(gold);
			};

			@Override
			protected void layout() {
				super.layout();
				gold.fill(this);
			};
		});

		add(btnQuick = new QuickslotTool(105, 7, 22, 24, 0));

		btnQuick2 = new QuickslotTool(105, 7, 22, 24, 1);
		
		btnQuick3 = new QuickslotTool(105, 7, 22, 24, 2);
		
		add(btnQuick4 = new QuickslotTool(105, 7, 22, 24, 3));
		
		btnQuick5 = new QuickslotTool(105, 7, 22, 24, 4);
		
		add(btnQuick6 = new QuickslotTool(105, 7, 22, 24, 5));

		add(pickedUp = new PickedUpItem());
		

	}

	@Override
	protected void layout() {
		btnWait.setPos(x, y);
		btnSearch.setPos(btnWait.right(), y);
		//btnInfo.setPos(btnSearch.right(), y);
		//btnResume.setPos(btnInfo.right(), y);
		//btnRoll.setPos(btnSearch.right(), y);
		btnInventory.setPos( width - btnInventory.width(), y );
		btnQuick.setPos(width - btnInventory.width()*2, y);
		btnQuick2.setPos(width - btnQuick.width(), btnQuick.height()*4);
		btnQuick3.setPos(width - btnQuick.width(), btnQuick.height()*3);
		btnQuick4.setPos(width - btnQuick.width(), btnQuick.height()*2);
		btnQuick5.setPos(width - btnInventory.width()*4, y);
		btnQuick6.setPos(width - btnInventory.width()*3, y);
		if (QuickSlots == 2) {
			add(btnQuick2);
			btnQuick2.visible = btnQuick2.active = true;
			add(btnQuick3);
			btnQuick3.visible = btnQuick3.active = true;
			add(btnQuick5);
			btnQuick5.visible = btnQuick5.active = true;		
		} else {
			remove(btnQuick2);
			btnQuick2.visible = btnQuick2.active = false;
			remove(btnQuick5);
			btnQuick5.visible = btnQuick5.active = false;
			remove(btnQuick3);
			btnQuick3.visible = btnQuick3.active = false;
		}
	}

	@Override
	public void update() {
		super.update();

		if (lastEnabled != Dungeon.hero.ready) {
			lastEnabled = Dungeon.hero.ready;

			for (Gizmo tool : members) {
				if (tool instanceof Tool) {
					((Tool) tool).enable(lastEnabled);
				}
			}
		}

		// btnResume.visible = Dungeon.hero.lastAction != null;

		if (!Dungeon.hero.isAlive()) {
			btnInventory.enable(true);
		}

		// If we have 2 slots, and 2nd one isn't visible, or we have 1, and 2nd
		// one is visible...
		if ((QuickSlots == 1) == btnQuick2.visible) {
			layout();
		}
	}

	public void pickup(Item item) {
		pickedUp.reset(item, btnInventory.centerX(), btnInventory.centerY());
	}

	private static CellSelector.Listener informer = new CellSelector.Listener() {
		@Override
		public void onSelect(Integer cell) {
			//instance.examining = false;
			GameScene.examineCell( cell );
		}

		@Override
		public String prompt() {
			return Messages.get(Toolbar.class, "examine_prompt");
		}
	};

	private static class Tool extends Button {

		private static final int BGCOLOR = 0x7B8073;

		private Image base;

		public Tool(int x, int y, int width, int height) {
			super();

			base.frame(x, y, width, height);

			this.width = width;
			this.height = height;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			base = new Image(Assets.TOOLBAR);
			add(base);
		}

		@Override
		protected void layout() {
			super.layout();

			base.x = x;
			base.y = y;
		}

		@Override
		protected void onTouchDown() {
			base.brightness(1.4f);
		}

		@Override
		protected void onTouchUp() {
			if (active) {
				base.resetColor();
			} else {
				base.tint(BGCOLOR, 0.7f);
			}
		}

		public void enable(boolean value) {
			if (value != active) {
				if (value) {
					base.resetColor();
				} else {
					base.tint(BGCOLOR, 0.7f);
				}
				active = value;
			}
		}
	};

	private static class QuickslotTool extends Tool {

		private QuickSlotButton slot;

		public QuickslotTool(int x, int y, int width, int height, int slotNum) {
			super(x, y, width, height);

			slot = new QuickSlotButton(slotNum);
			add(slot);
		}

		@Override
		protected void layout() {
			super.layout();
			slot.setRect(x + 1, y + 2, width - 2, height - 2);
		}

		@Override
		public void enable(boolean value) {
			super.enable(value);
			slot.enable(value);
		}
	}

	private static class PickedUpItem extends ItemSprite {

		private static final float DISTANCE = DungeonTilemap.SIZE;
		private static final float DURATION = 0.2f;

		private float dstX;
		private float dstY;
		private float left;

		public PickedUpItem() {
			super();

			originToCenter();

			active = visible = false;
		}

		public void reset(Item item, float dstX, float dstY) {
			view(item.image(), item.glowing());

			active = visible = true;

			this.dstX = dstX - ItemSprite.SIZE / 2;
			this.dstY = dstY - ItemSprite.SIZE / 2;
			left = DURATION;

			x = this.dstX - DISTANCE;
			y = this.dstY - DISTANCE;
			alpha(1);
		}

		@Override
		public void update() {
			super.update();

			if ((left -= Game.elapsed) <= 0) {

				visible = active = false;

			} else {
				float p = left / DURATION;
				scale.set((float) Math.sqrt(p));
				float offset = DISTANCE * p;
				x = dstX - offset;
				y = dstY - offset;
			}
		}
	}
}

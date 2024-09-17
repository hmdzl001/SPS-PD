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
package com.hmdzl.spspd.ui;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.items.ChangeEquip;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndCatalogus;
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
	private Tool btnQuick7;
	private Tool btnQuick8;
	private Tool btnQuick9;
	private Tool btnQuick10;
	private Tool btnQuick11;
	private Tool btnQuick12;
	public static int quicktype;
	public static int basetooltype;

	private static Toolbar instance;

	private PickedUpItem pickedUp;

	private boolean lastEnabled = true;
	public boolean examining = false;


	public Toolbar() {
		super();

		quicktype = ShatteredPixelDungeon.quicktypes();
		basetooltype = ShatteredPixelDungeon.basetooltypes();

		height = btnInventory.height();
	}
	
	

	@Override
	protected void createChildren() {

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
		
		add(btnQuick = new QuickslotTool(105, 7, 22, 24, 0));

		add(btnQuick2 = new QuickslotTool(105, 7, 22, 24, 1));
		
		add(btnQuick3 = new QuickslotTool(105, 7, 22, 24, 2));
		
		add(btnQuick4 = new QuickslotTool(105, 7, 22, 24, 3));

		add(btnQuick5 = new QuickslotTool(105, 7, 22, 24, 4));
		
		add(btnQuick6 = new QuickslotTool(105, 7, 22, 24, 5));
		
		add(btnQuick7 = new QuickslotTool(105, 7, 22, 24, 6));
		
		add(btnQuick8 = new QuickslotTool(105, 7, 22, 24, 7));
		
		add(btnQuick9 = new QuickslotTool(105, 7, 22, 24, 8));

		add(btnQuick10 = new QuickslotTool(105, 7, 22, 24, 9));

		add(btnQuick11 = new QuickslotTool(105, 7, 22, 24, 10));

		add(btnQuick12 = new QuickslotTool(105, 7, 22, 24, 11));		

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
			}

            @Override
			protected void createChildren() {
				super.createChildren();
				gold = new GoldIndicator();
				add(gold);
			}

            @Override
			protected void layout() {
				super.layout();
				gold.fill(this);
			}
        });

		add(pickedUp = new PickedUpItem());

		add(btnWait = new Tool(0, 7, 20, 24) {
			@Override
			protected void onClick() {
				examining = false;
				Dungeon.hero.rest(false);
			}

			@Override
			protected boolean onLongClick() {
				examining = false;
				Dungeon.hero.rest(true);
				return true;
			}
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
				//Dungeon.hero.search(true);
				ChangeEquip item = new ChangeEquip();
				item.execute(Dungeon.hero,ChangeEquip.AC_CHANGE);
				return true;
			}
		} );
		

	}

	@Override
	protected void layout() {
	    switch (quicktype) {
			case 0:
				btnQuick10.setPos(width - btnInventory.width() * 2 + 2, y);
				btnQuick4.setPos(width - btnQuick.width(), btnQuick.height() * 2);
				btnQuick12.setPos(width - btnInventory.width() * 4 + 4, y);
				btnQuick11.setPos(width - btnInventory.width() * 3 + 3, y);
				btnQuick8.setPos(0, btnInventory.width() * 2 + 4);

				if (ShatteredPixelDungeon.landscape()) {
					btnQuick5.setPos(width - btnQuick.width(), btnQuick.height() * 3 - 2);
					btnQuick6.setPos(width - btnInventory.width() * 7 + 7, y);
					btnQuick7.setPos(width - btnInventory.width() * 5 + 5, y);
					btnQuick9.setPos(width - btnInventory.width() * 6 + 6, y);
					btnQuick.setPos(width - btnInventory.width() * 6 + 6, y);
					btnQuick2.setPos(width - btnInventory.width() * 6 + 6, y);
					btnQuick3.setPos(width - btnInventory.width() * 6 + 6, y);
				} else {
					btnQuick5.setPos(width - btnQuick.width(), btnQuick.height() * 3 - 2);
					btnQuick6.setPos(width - btnQuick.width(), btnQuick.height() * 4 - 3);
					btnQuick7.setPos(0, btnInventory.width() * 3 + 4);
					btnQuick9.setPos(0, btnInventory.width() * 4 + 4);
					btnQuick.setPos(width - btnInventory.width() * 2 + 2, y);
					btnQuick2.setPos(width - btnInventory.width() * 3 + 3, y);
					btnQuick3.setPos(width - btnInventory.width() * 4 + 4, y);
				}
				break;
            case 1:
                //btnQuick.setPos(width - btnInventory.width()+1, y - btnQuick.height()/2);
                //btnQuick2.setPos(width - btnInventory.width()*2+2, y - btnQuick.height()/2 );
                //btnQuick3.setPos(width - btnInventory.width()*3+3, y - btnQuick.height()/2);
                //btnQuick4.setPos(width - btnInventory.width()*4+4, y - btnQuick.height()/2);
                //btnQuick5.setPos(width - btnInventory.width()*5+5, y - btnQuick.height()/2);
                //btnQuick6.setPos(width - btnInventory.width()*6+6, y - btnQuick.height()/2);

                btnQuick7.setPos(width - btnInventory.width() + 1, y);
                btnQuick8.setPos(width - btnInventory.width() * 2 + 2, y);
                btnQuick9.setPos(width - btnInventory.width() * 3 + 3, y);
                btnQuick10.setPos(width - btnInventory.width() * 4 + 4, y);
                btnQuick11.setPos(width - btnInventory.width() * 5 + 5, y);
                btnQuick12.setPos(width - btnInventory.width() * 6 + 6, y);

                if (ShatteredPixelDungeon.landscape()) {
                    btnQuick.setPos(width - btnInventory.width() * 7 + 7, y);
                    btnQuick2.setPos(width - btnInventory.width() * 8 + 8, y);
                    btnQuick3.setPos(width - btnInventory.width() * 9 + 9, y);
                    btnQuick4.setPos(width - btnInventory.width() * 10 + 10, y);
                    btnQuick5.setPos(width - btnInventory.width() * 11 + 11, y);
                    btnQuick6.setPos(width - btnInventory.width() * 12 + 12, y);
                } else {
                    btnQuick.setPos(width - btnInventory.width() + 1, y - btnQuick.height() / 2);
                    btnQuick2.setPos(width - btnInventory.width() * 2 + 2, y - btnQuick.height() / 2);
                    btnQuick3.setPos(width - btnInventory.width() * 3 + 3, y - btnQuick.height() / 2);
                    btnQuick4.setPos(width - btnInventory.width() * 4 + 4, y - btnQuick.height() / 2);
                    btnQuick5.setPos(width - btnInventory.width() * 5 + 5, y - btnQuick.height() / 2);
                    btnQuick6.setPos(width - btnInventory.width() * 6 + 6, y - btnQuick.height() / 2);
                }
                break;
            case 2:
                //btnWait.setPos(x, y);
               // btnSearch.setPos(btnWait.right(), y);
               // btnInventory.setPos(width - btnInventory.width(), y);
                btnQuick10.setPos(width - btnInventory.width() * 2 + 2, y);
                btnQuick4.setPos(width - btnQuick.width(), btnQuick.height() * 2);
                btnQuick12.setPos(width - btnInventory.width() * 4 + 4, y);
                btnQuick11.setPos(width - btnInventory.width() * 3 + 3, y);
                btnQuick8.setPos(0, btnInventory.width() * 2 + 4);

                if (ShatteredPixelDungeon.landscape()) {
                    btnQuick5.setPos(width - btnQuick.width(), btnQuick.height() * 3 - 2);
                    btnQuick6.setPos(width - btnInventory.width() * 7 + 7, y);
                    btnQuick7.setPos(width - btnInventory.width() * 5 + 5, y);
                    btnQuick9.setPos(width - btnInventory.width() * 6 + 6, y);
                    btnQuick.setPos(width - btnInventory.width() * 10 + 10, y);
                    btnQuick2.setPos(width - btnInventory.width() * 8 + 8, y);
                    btnQuick3.setPos(width - btnInventory.width() * 9 + 9, y);
                } else {
                    btnQuick5.setPos(width - btnQuick.width(), btnQuick.height() * 3 - 2);
                    btnQuick6.setPos(width - btnQuick.width(), btnQuick.height() * 4 - 3);
                    btnQuick7.setPos(0, btnInventory.width() * 3 + 4);
                    btnQuick9.setPos(0, btnInventory.width() * 4 + 4);
                    btnQuick.setPos(width - btnInventory.width() * 2 + 2, y - btnQuick.height() / 2);
                    btnQuick2.setPos(width - btnInventory.width() * 3 + 3, y - btnQuick.height() / 2);
                    btnQuick3.setPos(width - btnInventory.width() * 4 + 4, y - btnQuick.height() / 2);
                }
                break;
			case 3:
				//btnWait.setPos(x, y);
				// btnSearch.setPos(btnWait.right(), y);
				// btnInventory.setPos(width - btnInventory.width(), y);
				btnQuick10.setPos(width - btnInventory.width() * 2 + 2, y);
				btnQuick4.setPos(width - btnQuick.width(), btnQuick.height() * 2);
				btnQuick12.setPos(width - btnInventory.width() * 4 + 4, y);
				btnQuick11.setPos(width - btnInventory.width() * 3 + 3, y);
				btnQuick8.setPos(0, btnInventory.width() * 2 + 4);

				if (ShatteredPixelDungeon.landscape()) {
					btnQuick5.setPos(width - btnQuick.width(), btnQuick.height() * 3 - 2);
					btnQuick6.setPos(width - btnInventory.width() * 6 + 6, y);
					btnQuick7.setPos(width - btnInventory.width() * 5 + 5, y);
					btnQuick9.setPos(width - btnInventory.width() * 6 + 6, y);
					btnQuick.setPos(width - btnInventory.width() * 6 + 6, y);
					btnQuick2.setPos(width - btnInventory.width() * 6 + 6, y);
					btnQuick3.setPos(width - btnInventory.width() * 6 + 6, y);
				} else {
					btnQuick5.setPos(width - btnQuick.width(), btnQuick.height() * 3 - 2);
					btnQuick6.setPos(width - btnQuick.width(), btnQuick.height() * 4 - 3);
					btnQuick7.setPos(width - btnQuick.width(), btnQuick.height() * 5 - 4);
					btnQuick9.setPos(width - btnQuick.width(), btnQuick.height() * 5 - 4);
					btnQuick.setPos(width - btnInventory.width() * 2 + 2, y);
					btnQuick2.setPos(width - btnInventory.width() * 3 + 3, y);
					btnQuick3.setPos(width - btnInventory.width() * 4 + 4, y);
				}
				break;
			case 4:
				btnQuick7.setPos(width - btnInventory.width() + 1, y);
				btnQuick8.setPos(width - btnInventory.width() * 2 + 2, y);
				btnQuick9.setPos(width - btnInventory.width() * 3 + 3, y);
				btnQuick10.setPos(width - btnInventory.width() * 4 + 4, y);
				btnQuick11.setPos(width - btnInventory.width() * 5 + 5, y);
				btnQuick12.setPos(width - btnInventory.width() * 6 + 6, y);

				if (ShatteredPixelDungeon.landscape()) {
					btnQuick.setPos(width - btnInventory.width() * 1 + 1, y);
					btnQuick2.setPos(width - btnInventory.width() * 2 + 2, y);
					btnQuick3.setPos(width - btnInventory.width() * 3 + 3, y);
					btnQuick4.setPos(width - btnInventory.width() * 4 + 4, y);
					btnQuick5.setPos(width - btnInventory.width() * 5 + 5, y);
					btnQuick6.setPos(width - btnInventory.width() * 6 + 6, y);
				} else {
					btnQuick.setPos(width - btnInventory.width() + 1, y );
					btnQuick2.setPos(width - btnInventory.width() * 2 + 2, y);
					btnQuick3.setPos(width - btnInventory.width() * 3 + 3, y);
					btnQuick4.setPos(width - btnInventory.width() * 4 + 4, y);
					btnQuick5.setPos(width - btnInventory.width() * 5 + 5, y);
					btnQuick6.setPos(width - btnInventory.width() * 6 + 6, y);
				}
				break;
        }

        switch (basetooltype) {
			case 0:
				btnWait.setPos(0, btnInventory.width() * 2 + 4);
				btnSearch.setPos(0, btnInventory.width() * 3 + 4);
				btnInventory.setPos(width - btnQuick.width(), btnQuick.height() * 2);
				break;
			case 1 :
				btnWait.setPos(x, y);
				btnSearch.setPos(btnWait.right(), y);
				btnInventory.setPos(width - btnInventory.width(), y);
				break;
			case 2 :
				btnWait.setPos(x, y);
				btnSearch.setPos(btnWait.right(), y);
				btnInventory.setPos(btnSearch.right()-1, y);
				break;
			case 3 :
				btnWait.setPos(width - btnWait.width() - btnInventory.width() - btnSearch.width(), y);
				btnSearch.setPos(width - btnInventory.width() - btnSearch.width(), y);
				btnInventory.setPos(width - btnInventory.width(), y);
				break;
			case 4 :
				btnWait.setPos(width - btnQuick.width(), btnQuick.height() * 2);
				btnSearch.setPos(width - btnQuick.width(), btnQuick.height() * 3);
				btnInventory.setPos(0, btnInventory.width() * 2 + 4);
				break;
		}

	}
	
	public static void updateLayout(){
		if (instance != null) instance.layout();
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
	}

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

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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.items.PocketBallFull;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.ScrollPane;
import com.hmdzl.spspd.ui.StateIndicator;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class WndHero extends WndTabbed {

	private static final int WIDTH = 100;
	private static final int TAB_WIDTH = 40;
	private static final int HEIGHT		= 180;

	private StatsTab stats;
	private PetTab pet;
	private BuffsTab buffs;

	private SmartTexture icons;

	private SmartTexture state_icons;
	private TextureFilm film;

	private TextureFilm state_film;
	
	//private ScrollPane list;
	
	
	private PET checkpet(){
		for (Mob mob : Dungeon.depth.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}

	public WndHero() {

		super();
		
		resize( WIDTH, HEIGHT );

		icons = TextureCache.get(Assets.BUFFS_LARGE);
		state_icons = TextureCache.get(Assets.STATE_ICON);
		film = new TextureFilm(icons, 16, 16);
		state_film = new TextureFilm(state_icons, 7, 7);

		stats = new StatsTab();
		add(stats);

		pet = new PetTab();
		add(pet);

		buffs = new BuffsTab();
		add(buffs);
		buffs.setRect(0, 0, WIDTH, HEIGHT);
		buffs.setupList();
		
		
		add(new LabeledTab(Messages.get(this, "stats")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				stats.visible = stats.active = selected;
			}
        });

		add(new LabeledTab(Messages.get(this, "pet")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				pet.visible = pet.active = selected;
			}
        });


		add(new LabeledTab(Messages.get(this, "buffs")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				buffs.visible = buffs.active = selected;
			}
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
			title.color(SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			/*MemoryButton btnmemory = new MemoryButton();
			btnmemory.setRect(60,0,0,0);
			add(btnmemory);*/

			RedButton btnCatalogus = new RedButton(""){
				protected void onClick() {
					hide();
					GameScene.show(new WndCatalogus());
				}
			};
			btnCatalogus.icon(new ItemSprite(ItemSpriteSheet.S_AND_P , null));
			btnCatalogus.setRect(0, title.height(),
					btnCatalogus.reqWidth()+1,  btnCatalogus.reqHeight()+1);
			add(btnCatalogus);

			RedButton btnDocument = new RedButton("") {
				@Override
				protected void onClick() {
					hide();
					GameScene.show( new WndDocument() );
				}
			};
			btnDocument.icon(new ItemSprite(ItemSpriteSheet.NORMAL_DOCUMENT , null));
			btnDocument.setRect(btnCatalogus.reqWidth()+1, title.height() ,
					btnDocument.reqWidth() + 1, btnDocument.reqHeight() + 1);
			add(btnDocument);

			pos = btnDocument.bottom() + GAP;

			StateIconSlot btnSTR = new StateIconSlot(StateIndicator.STR_ICON,hero.STR()){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "str")));
				}
			};
			btnSTR.setRect(0, pos,14,14 );
			add(btnSTR);

			if(Dungeon.hero.buff(Hunger.class) != null) {
				//statSlot(Messages.get(this, "hunger"), Dungeon.hero.buff(Hunger.class).hungerLevel());
				StateIconSlot btnHUNGER = new StateIconSlot(StateIndicator.HUNGER_ICON,
						(Dungeon.hero.buff(Hunger.class).hungerLevel() + "/1000") ){
					@Override
					protected void onClick() {
						super.onClick();
						ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "hunger")));
					}
				};
				btnHUNGER.setRect(WIDTH * 0.5f, pos, 14, 14);
				add(btnHUNGER);
			}

			pos += GAP*3;

			StateIconSlot btnHP = new StateIconSlot(StateIndicator.HP_ICON,hero.HP){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "hp")));
				}
			};
			btnHP.setRect(0, pos,14,14 );
			add(btnHP);

				//statSlot(Messages.get(this, "hunger"), Dungeon.hero.buff(Hunger.class).hungerLevel());
			StateIconSlot btnHT = new StateIconSlot(StateIndicator.HT_ICON, hero.HT) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "ht")));
				}
			};
			btnHT.setRect(WIDTH * 0.5f, pos, 14, 14);
			add(btnHT);

			pos += GAP*3;

			StateIconSlot btnEXP = new StateIconSlot(StateIndicator.EXP_ICON,hero.exp + "/" + hero.maxExp()){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "exp")));
				}
			};
			btnEXP.setRect(0, pos,14,14 );
			add(btnEXP);

			//statSlot(Messages.get(this, "hunger"), Dungeon.hero.buff(Hunger.class).hungerLevel());
			StateIconSlot btnTIME = new StateIconSlot(StateIndicator.TIME_ICON,
					(Statistics.moves +"d " + (int)(Statistics.time/60) + ":" + (int)(Statistics.time%60))) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "time")));
				}
			};
			btnTIME.setRect(WIDTH * 0.5f, pos, 14, 14);
			add(btnTIME);

			pos += GAP*3;

			StateIconSlot btnACU = new StateIconSlot(StateIndicator.ACU_ICON,hero.hitSkill){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "atkskill")));
				}
			};
			btnACU.setRect(0, pos,14,14 );
			add(btnACU);

			StateIconSlot btnMELEE_CRI = new StateIconSlot(StateIndicator.MELEE_CRI_ICON, hero.meleecri() + "%/" + hero.meleecridamage()+ "%") {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "melee_cri")));
				}
			};
			btnMELEE_CRI.setRect(WIDTH * 0.5f, pos, 14, 14);
			add(btnMELEE_CRI);

			pos += GAP*3;

			StateIconSlot btnDEX = new StateIconSlot(StateIndicator.DEX_ICON,hero.evadeSkill){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "defskill")));
				}
			};
			btnDEX.setRect(0, pos,14,14 );
			add(btnDEX);

			StateIconSlot btnRANGE_CRI = new StateIconSlot(StateIndicator.RANGE_CRI_ICON, hero.rangecri() + "%/" + hero.rangecridamage()+ "%") {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "range_cri")));
				}
			};
			btnRANGE_CRI.setRect(WIDTH * 0.5f, pos, 14, 14);
			add(btnRANGE_CRI);

			pos += GAP*3;

			StateIconSlot btnMIG = new StateIconSlot(StateIndicator.MIG_ICON,hero.magicSkill()){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "magskill")));
				}
			};
			btnMIG.setRect(0, pos,14,14 );
			add(btnMIG);

			StateIconSlot btnMAGIC_CRI = new StateIconSlot(StateIndicator.MAGIC_CRI_ICON, hero.magiccri() + "%/" + hero.magiccridamage() + "%") {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "magic_cri")));
				}
			};
			btnMAGIC_CRI.setRect(WIDTH * 0.5f, pos, 14, 14);
			add(btnMAGIC_CRI);

			pos += GAP*3;

			StateIconSlot btnMOVE = new StateIconSlot(StateIndicator.MOVE_ICON,new DecimalFormat("#.##").format(hero.speed())){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "move")));
				}
			};
			btnMOVE.setRect(0, pos,14,14 );
			add(btnMOVE);

			StateIconSlot btnATK_DELAY = new StateIconSlot(StateIndicator.ATK_DELAY_ICON, new DecimalFormat("#.##").format(hero.attackDelay())) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "atk_delay")));
				}
			};
			btnATK_DELAY.setRect(WIDTH * 0.5f, pos, 14, 14);
			add(btnATK_DELAY);

			pos += GAP*3;

			StateIconSlot btnATTACK_RANGE = new StateIconSlot(StateIndicator.ATTACK_RANGE_ICON,hero.attackrange()){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "attack_range")));
				}
			};
			btnATTACK_RANGE.setRect(0, pos,14,14 );
			add(btnATTACK_RANGE);

			StateIconSlot btnHIDEN = new StateIconSlot(StateIndicator.HIDEN_ICON,new DecimalFormat("#.##").format(hero.stealth())) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "hiden")));
				}
			};
			btnHIDEN.setRect(WIDTH * 0.5f, pos, 14, 14);
			add(btnHIDEN);

			pos += GAP*3;

			StateIconSlot btnSPEED = new StateIconSlot(StateIndicator.SPEED_ICON,new DecimalFormat("#.##").format(hero.spendspeed())){
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "speed")));
				}
			};
			btnSPEED.setRect(0, pos,14,14 );
			add(btnSPEED);

			StateIconSlot btnLUCKY = new StateIconSlot(StateIndicator.LUCKY_ICON,hero.lucky()) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(new WndMessage(Messages.get(WndHero.StatsTab.class, "lucky")));
				}
			};
			btnLUCKY.setRect(WIDTH * 0.5f, pos, 14, 14);
			add(btnLUCKY);

			pos += GAP*3;

			//statSlot(Messages.get(this, "str"), hero.STR());
			//statSlot(Messages.get(this, "health"), hero.HP + "/" + hero.HT );
			//statSlot(Messages.get(this, "exp"), hero.exp + "/" + hero.maxExp());

			//statSlot(Messages.get(this, "time"),  (Statistics.moves +"d " + (int)(Statistics.time/60) + ":" + (int)(Statistics.time%60)));
			//pos += GAP;

			//statSlot(Messages.get(this, "gold"), Statistics.goldCollected);
			//statSlot(Messages.get(this, "atkskill"), hero.hitSkill);
			//statSlot(Messages.get(this, "defskill"), hero.evadeSkill);
			//statSlot(Messages.get(this, "magskill"), hero.magicSkill());

			//if(Dungeon.hero.buff(Hunger.class) != null){
			//	statSlot(Messages.get(this, "hunger") , Dungeon.hero.buff(Hunger.class).hungerLevel());
			//}
			
			
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

		private void statSlotWithIcon(Image image, String name, String value) {

			Image icon = image;
			add(icon);

			RenderedText txt = PixelScene.renderText(name, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.renderText(value, 8);
			//txt.measure();
			txt.x = PixelScene.align(WIDTH * 0.5f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}

		public float height() {
			return pos;
		}

		private class StateIconSlot extends Button {
			Image icon;
			RenderedText txt;


			public StateIconSlot(int num, String value) {
				icon = new Image( state_icons );
				icon.frame( state_film.get( num ) );
				icon.scale.set( 1.25f );
				add( icon );
				txt = PixelScene.renderText( value, 8);
				add(txt);
			}
			public  StateIconSlot(int num, int value ){
				icon = new Image( state_icons );
				icon.frame( state_film.get( num ) );
				icon.scale.set( 1.25f );
				add( icon );

				txt = PixelScene.renderText("" + value, 8);
				add(txt);

			}

			public  StateIconSlot(int num, float value ){
				icon = new Image( state_icons );
				icon.frame( state_film.get( num ) );
				icon.scale.set( 1.25f );
				add( icon );

				txt = PixelScene.renderText("" + value, 8);
				add(txt);

			}


			@Override
			protected void layout() {
				super.layout();
				icon.x = this.x;
				icon.y = this.y;

				txt.x  = x + width() - 5;
				txt.y = y ;
			}
			@Override
			protected void onClick() {
				//
			}
		}
	}
	
	private class BuffsTab extends Component {

		private static final int GAP = 2;

		private float pos;
		
		private ScrollPane buffList;
		private ArrayList<BuffSlot> slots = new ArrayList<>();

		public BuffsTab() {
			buffList = new ScrollPane( new Component() ){
				@Override
				public void onClick( float x, float y ) {
					int size = slots.size();
					for (int i=0; i < size; i++) {
						if (slots.get( i ).onClick( x, y )) {
							break;
						}
					}
				}
			};
			add(buffList);
		}
		
		@Override
		protected void layout() {
			super.layout();
			buffList.setRect(0, 0, width, height);
		}		
		
		private void setupList() {
			Component content = buffList.content();
			for (Buff buff : Dungeon.hero.buffs()) {
				if (buff.icon() != BuffIndicator.NONE) {
					BuffSlot slot = new BuffSlot(buff);
					slot.setRect(0, pos, WIDTH, slot.icon.height());
					content.add(slot);
					slots.add(slot);
					pos += GAP + slot.height();
				}
			}
			content.setSize(buffList.width(), pos);
			buffList.setSize(buffList.width(), buffList.height());
		}		
		
		
		private class BuffSlot extends Component {

			private Buff buff;

			Image icon;
			RenderedText txt;

			public BuffSlot( Buff buff ){
				super();
				this.buff = buff;
				int index = buff.icon();

				icon = new Image( icons );
				icon.frame( film.get( index ) );
				//buff.tintIcon(icon);
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
			
			protected boolean onClick ( float x, float y ) {
				if (inside( x, y )) {
					GameScene.show(new WndInfoBuff(buff));
					return true;
				} else {
					return false;
				}
			}
		}
	}
	
	private class PetTab extends Group {

        private static final int GAP = 4;

        private float pos;

        public PetTab() {

            Hero hero = Dungeon.hero;

            IconTitle title = new IconTitle();
            title.icon(HeroSprite.avatar(hero.heroClass, hero.useskin()));
            title.label(Messages.get(this, "title", hero.lvl, hero.className())
                    .toUpperCase(Locale.ENGLISH));
            title.color(SHPX_COLOR);
            title.setRect(0, 0, WIDTH, 0);
            add(title);

            RedButton btnSummon = new RedButton( "") {
                @Override
                protected void onClick() {
                    hide();
					PocketBallFull.teleportPet(Dungeon.hero);
                }
            };
			btnSummon.icon(new ItemSprite(ItemSpriteSheet.FLUTE , null));
            btnSummon.setRect(0, title.height(),
                    btnSummon.reqWidth() + 1, btnSummon.reqHeight() + 1);
            add(btnSummon);

            RedButton btnTarget = new RedButton("") {
            	@Override
            	protected void onClick() {
            				hide();
					PocketBallFull.target(Dungeon.hero);
            	}
            };
			btnTarget.icon(new ItemSprite(ItemSpriteSheet.PET_SIGN , null));
			btnTarget.setRect(btnSummon.reqWidth() + 1, title.height(),
					btnTarget.reqWidth() + 1, btnTarget.reqHeight() + 1);
            add(btnTarget);

			RedButton btnDisTarget = new RedButton("") {
				@Override
				protected void onClick() {
					hide();
					PocketBallFull.distarget(Dungeon.hero);
				}
			};
			btnDisTarget.icon(new ItemSprite(ItemSpriteSheet.ARTIFACT_CLOAK , null));
			btnDisTarget.setRect(2*btnTarget.reqWidth() + 2, title.height(),
					btnDisTarget.reqWidth() + 1, btnDisTarget.reqHeight() + 1);
			add(btnDisTarget);

			//RedButton btnChangeAction = new RedButton(Messages.get(this, "change")) {
			//	@Override
			//	protected void onClick() {
				//	GameScene.show(new WndChangeAction());
			//	}
			//};

            //btnChangeAction.setRect(btnDisTarget.right() + 1, title.height()+ btnSummon.top() + 1,
            //        btnSummon.reqWidth() + 2, btnSummon.reqHeight() + 2);
			//add(btnChangeAction);

			pos = btnDisTarget.bottom() + GAP;

			if (Dungeon.dungeondepth <26 && (Dungeon.dewDraw || Dungeon.dewWater)) {
				statSlot(Messages.get(this, "level_move"), (int)Dungeon.depth.currentmoves);
				statSlot(Messages.get(this, "level_max"), Dungeon.pars[Dungeon.dungeondepth]);
				//statSlot(Messages.get(this, "level_prefect"), Statistics.prevfloormoves);
			}

            statSlot(Messages.get(this, "pet_level"), Dungeon.hero.petLevel);
            statSlot(Messages.get(this, "pet_exp"), Dungeon.hero.petExperience);
            if (Dungeon.hero.haspet) {
                statSlot(Messages.get(this, "pet_type"), Messages.get(this,"id_"+ Dungeon.hero.petType));
                statSlot(Messages.get(this, "pet_hp"), Dungeon.hero.petHP);
                statSlot(Messages.get(this, "pet_skill"), Dungeon.hero.petCooldown);
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

	//private class WndChangeAction extends WndOptions {
		//public WndChangeAction() {
		//	super(Messages.get(WndHero.class, "pa_t"), Messages.get(WndHero.class, "pa_i"),Messages.get(WndHero.class, "pa_de"),
		//			Messages.get(WndHero.class, "pa_ch"),Messages.get(WndHero.class, "pa_fe"));
		//}

		//@Override
		//protected void onSelect( int index ) {
		//	Dungeon.hero.petAction = index;
		//}

	//}
}
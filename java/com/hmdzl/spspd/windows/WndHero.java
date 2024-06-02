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
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.Window;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.ui.Button;

import java.util.Locale;

public class WndHero extends WndTabbed {

	private static final int WIDTH = 100;
	private static final int TAB_WIDTH = 40;

	private StatsTab stats;
	private PetTab pet;
	private BuffsTab buffs;

	private SmartTexture icons;
	private TextureFilm film;
	
	
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

		icons = TextureCache.get(Assets.BUFFS_LARGE);
		film = new TextureFilm(icons, 16, 16);

		stats = new StatsTab();
		add(stats);

		pet = new PetTab();
		add(pet);

		buffs = new BuffsTab();
		add(buffs);
		
		
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
				}
            };
			btnCatalogus.setRect(0, title.height(),
					btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2);
			add(btnCatalogus);

			RedButton btnDocument = new RedButton(Messages.get(this, "document")) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show( new WndDocument() );
				}
			};
			btnDocument.setRect(0, title.height() + btnCatalogus.height(),
					btnDocument.reqWidth() + 2, btnDocument.reqHeight() + 2);
			add(btnDocument);

			pos = btnDocument.bottom() + GAP;

			statSlot(Messages.get(this, "str"), hero.STR());
			statSlot(Messages.get(this, "health"), hero.HP + "/" + hero.HT );
			statSlot(Messages.get(this, "exp"), hero.exp + "/" + hero.maxExp());

			statSlot(Messages.get(this, "time"),  (Statistics.moves +"d " + (int)(Statistics.time/60) + ":" + (int)(Statistics.time%60)));

			pos += GAP;

			statSlot(Messages.get(this, "gold"), Statistics.goldCollected);
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
            title.color(Window.SHPX_COLOR);
            title.setRect(0, 0, WIDTH, 0);
            add(title);

            RedButton btnSummon = new RedButton(Messages.get(this, "Summon")) {
                @Override
                protected void onClick() {
                    hide();
					PocketBallFull.teleportPet(Dungeon.hero);
                }
            };
            btnSummon.setRect(0, title.height(),
                    btnSummon.reqWidth() + 2, btnSummon.reqHeight() + 2);
            add(btnSummon);

            RedButton btnTarget = new RedButton(Messages.get(this, "target")) {
            	@Override
            	protected void onClick() {
            				hide();
					PocketBallFull.target(Dungeon.hero);
            	}
            };
			btnTarget.setRect(btnSummon.right() + 1, btnSummon.top(),
					btnTarget.reqWidth() + 2, btnTarget.reqHeight() + 2);
            add(btnTarget);

			RedButton btnDisTarget = new RedButton(Messages.get(this, "distarget")) {
				@Override
				protected void onClick() {
					hide();
					PocketBallFull.distarget(Dungeon.hero);
				}
			};
			btnDisTarget.setRect(0, title.height()+ btnSummon.top() + 1,
					btnDisTarget.reqWidth() + 2, btnDisTarget.reqHeight() + 2);
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
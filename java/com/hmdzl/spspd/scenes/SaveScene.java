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
package com.hmdzl.spspd.scenes;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Chrome;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.GamesInProgress;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.Archs;
import com.hmdzl.spspd.ui.Icons;
import com.hmdzl.spspd.ui.ScrollPane;
import com.hmdzl.spspd.ui.Window;
import com.hmdzl.spspd.windows.WndGameInProgress2;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class SaveScene extends PixelScene {

	private static final int SLOT_WIDTH = 100;
	private static final int SLOT_HEIGHT = 15;
	
	@Override
	public void create() {
		super.create();
		
		Badges.loadGlobal();
		//Journal.loadGlobal();
		
		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;
		
		Archs archs = new Archs();
		archs.setSize( w, h );
		add( archs );

		//GamesInProgress.Info

		//ExitButton btnExit = new ExitButton();
		//btnExit.setPos( w - btnExit.width(), 0 );
		//add( btnExit );
		
		RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9);
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2f;
		title.y = (16 - title.baseLine()) / 2f;
		align(title);
		add(title);
		
		ScrollPane list = new ScrollPane( new Component() );
		add( list );

		Component content = list.content();
		content.clear();
		
		ArrayList<GamesInProgress.Info> games = GamesInProgress.checkAll();

		int slotGap = 6;

		float yPos =  8;
		
		for (GamesInProgress.Info game : games) {
			SaveSlotButton2 existingGame = new SaveSlotButton2();
			existingGame.set(game.slot);
			existingGame.setRect((w - SLOT_WIDTH) / 2f, yPos, SLOT_WIDTH, SLOT_HEIGHT);
			yPos += SLOT_HEIGHT + slotGap;
			align(existingGame);
			content.add(existingGame);
			
		}
		
		if (games.size() < GamesInProgress.MAX_SLOTS){
			SaveSlotButton2 saveGame = new SaveSlotButton2();
			saveGame.set(GamesInProgress.firstEmpty());
			saveGame.setRect((w - SLOT_WIDTH) / 2f, yPos, SLOT_WIDTH, SLOT_HEIGHT);
			yPos += SLOT_HEIGHT + slotGap;
			align(saveGame);
			content.add(saveGame);
		}
		
		//GamesInProgress.curSlot = 0;
		
		//fadeIn();
		
		list.setRect( 0, 13, w, h-8 );
		list.scrollTo(0, 0);


		content.setSize( 0,yPos+30 );
		
		
	}
	
	@Override
	protected void onBackPressed() {
		InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
		Game.switchScene( InterlevelScene.class );
	}
	
	private static class SaveSlotButton2 extends Button {
		
		private NinePatch bg;
		
		private Image hero;
		private RenderedText name;
		
		private Image steps;
		private BitmapText depth;
		
		private Image classIcon;
		
		private BitmapText level;
		
		private BitmapText slots;
		
		private int slot;
		
		private boolean newGame;
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			bg = Chrome.get(Chrome.Type.GEM);
			add( bg);
			
			name = PixelScene.renderText(9);
			add(name);
		}
		
		public void set( int slot ){
			this.slot = slot;
			GamesInProgress.Info info = GamesInProgress.check(slot);
			newGame = info == null;
			if (newGame){
				name.text( Messages.get(SaveScene.class, "new"));
				
				if (hero != null){
					remove(hero);
					hero = null;
					remove(steps);
					steps = null;
					remove(depth);
					depth = null;
					remove(classIcon);
					classIcon = null;
					remove(level);
					level = null;
				}
			} else {
				if (info.subClass != HeroSubClass.NONE){
					name.text(Messages.titleCase(info.subClass.title()));
				} else {
					name.text(Messages.titleCase(info.heroClass.title()));
				}
				
				if (hero == null){
					hero = new Image(info.heroClass.spritesheet(), 0, 15*info.skins, 12, 15);
					add(hero);
					
					steps = new Image(Icons.get(Icons.DEPTH));
					add(steps);
					depth = new BitmapText(font1x);
					add(depth);

					slots = new BitmapText(font1x);
					add(slots);
					
					classIcon = new Image(Icons.get(info.heroClass));
					add(classIcon);
					level = new BitmapText(font1x);
					add(level);
				} else {
					//hero.copy(new Image(info.heroClass.spritesheet(), 0, 15*info.skins, 12, 15));
					
					//classIcon.copy(Icons.get(info.heroClass));
				}
				
				depth.text(Integer.toString(info.depth));
				depth.measure();

				slots.text( info.slot == 0  ? "AUTO" +"-" + info.oldslot : Integer.toString(info.slot));
				slots.measure();

				
				level.text(Integer.toString(info.level));
				level.measure();
				
				if (info.challenges > 0){
					name.hardlight(Window.TITLE_COLOR);
					depth.hardlight(Window.TITLE_COLOR);
					level.hardlight(Window.TITLE_COLOR);
				} else {
					name.resetColor();
					depth.resetColor();
					level.resetColor();
				}
				
			}
			
			layout();
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			bg.x = x;
			bg.y = y;
			bg.size( width, height );
			
			if (hero != null){
				hero.x = x+8;
				hero.y = y + (height - hero.height())/2f;
				align(hero);
				
				name.x = hero.x + hero.width() + 6;
				name.y = y + (height - name.baseLine())/2f;
				align(name);
				
				classIcon.x = x + width - classIcon.width() - 8;
				classIcon.y = y + (height - classIcon.height())/2f;
				
				level.x = classIcon.x + (classIcon.width() - level.width()) / 2f;
				level.y = classIcon.y + (classIcon.height() - level.height()) / 2f + 1;
				align(level);
				
				steps.x = classIcon.x - steps.width();
				steps.y = y + (height - steps.height())/2f;
				
				slots.x = hero.x + hero.width() + 6;
				slots.y = y - height+9;

				depth.x = steps.x + (steps.width() - depth.width()) / 2f;
				depth.y = steps.y + (steps.height() - depth.height()) / 2f + 1;
				align(depth);
				
			} else {
				name.x = x + (width - name.width())/2f;
				name.y = y + (height - name.baseLine())/2f;
				align(name);
			}
			
			
		}
		
		@Override
		protected void onClick() {
			if (newGame) {
				//GamesInProgress.selectedClass = null;
				Dungeon.saveNewSlot(slot);
				InterlevelScene.mode = InterlevelScene.Mode.SAVE;
				Game.switchScene(InterlevelScene.class);
			} else {
				ShatteredPixelDungeon.scene().add( new WndGameInProgress2(slot));
			}
		}
	}

}
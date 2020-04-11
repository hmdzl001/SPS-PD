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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.items.DolyaSlate;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.Window;

import com.watabou.noosa.Game;
import com.hmdzl.spspd.ui.RenderedTextMultiline;

public class WndOtiluke extends Window {

	

	private static final String TXT_FARAWELL = Messages.get(WndOtiluke.class,"title");
	public static final float TIME_TO_USE = 1;

    
	private static final int PAGES = 10;
	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndOtiluke(final boolean[] rooms, final DolyaSlate item) {

		super();
		
		String[] roomNames = new String[PAGES];
		roomNames[0] = Messages.get(this,"room1");
		roomNames[1] = Messages.get(this,"room2");
		roomNames[2] = Messages.get(this,"room3");
		roomNames[3] = Messages.get(this,"room4");
		roomNames[4] = Messages.get(this,"room5");
		roomNames[5] = Messages.get(this,"room6");
		roomNames[6] = Messages.get(this,"room7");
		roomNames[7] = Messages.get(this,"room8");
	
		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), null));
		titlebar.label(Messages.titleCase(item.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene.renderMultiline(TXT_FARAWELL, 6);
		message.maxWidth(WIDTH);
		message.setPos(0,titlebar.bottom() + GAP);
		add(message);
		
		//add each button
		  //after n*BTN_HEIGHT+GAP
		//add port function
		
		if (rooms[0]){
		RedButton btn1 = new RedButton(roomNames[0]) {
			@Override
			protected void onClick() {
				item.returnDepth = Dungeon.depth;
				item.returnPos = Dungeon.hero.pos;
				port(0, item.firsts[0]);
				item.firsts[0]=false;
				if (item.charge> 500) {item.charge-=500;}
			}
		};
		btn1.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add(btn1);
		resize(WIDTH, (int) btn1.bottom());
		}
		
		int buttons=1;
		
		for (int i=1; i<PAGES; i++) {	
			final int portnum=i;
			if (rooms[i]){
				buttons++;
				RedButton btn = new RedButton(roomNames[i]) {
					@Override
					protected void onClick() {
						item.returnDepth = Dungeon.depth;
						item.returnPos = Dungeon.hero.pos;
						port(portnum, item.firsts[portnum]);
						item.firsts[portnum]=false;
						item.charge-= Math.min(item.charge,500);
					}
				};
				
				btn.setRect(0, buttons*BTN_HEIGHT + (buttons+2)*GAP, WIDTH, BTN_HEIGHT);	
				
				add(btn);
				resize(WIDTH, (int) btn.bottom());
			}
		}		
	}

	
	public void port(int room, boolean first){
		
		 Hero hero = Dungeon.hero;
		 hero.spend(TIME_TO_USE);
		 
		Buff buff = Dungeon.hero
				.buff(TimekeepersHourglass.timeFreeze.class);
		if (buff != null)
			buff.detach(); 	
    
		InterlevelScene.mode = InterlevelScene.Mode.JOURNAL;
		
		InterlevelScene.returnDepth = Dungeon.depth;
		InterlevelScene.returnPos = Dungeon.hero.pos;
		InterlevelScene.journalpage = room;
		InterlevelScene.first = first;
		Game.switchScene(InterlevelScene.class);
		
		
	  
	}
}

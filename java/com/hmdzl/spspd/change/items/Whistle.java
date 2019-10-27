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
package com.hmdzl.spspd.change.items;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.pets.BlueDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.BugDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Bunny;
import com.hmdzl.spspd.change.actors.mobs.pets.CocoCat;
import com.hmdzl.spspd.change.actors.mobs.pets.Fly;
import com.hmdzl.spspd.change.actors.mobs.pets.GentleCrab;
import com.hmdzl.spspd.change.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.GreenDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.LeryFire;
import com.hmdzl.spspd.change.actors.mobs.pets.LightDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Monkey;
import com.hmdzl.spspd.change.actors.mobs.pets.PET;
import com.hmdzl.spspd.change.actors.mobs.pets.RedDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.RibbonRat;
import com.hmdzl.spspd.change.actors.mobs.pets.Scorpion;
import com.hmdzl.spspd.change.actors.mobs.pets.ShadowDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Snake;
import com.hmdzl.spspd.change.actors.mobs.pets.Spider;
import com.hmdzl.spspd.change.actors.mobs.pets.Stone;
import com.hmdzl.spspd.change.actors.mobs.pets.Velocirooster;
import com.hmdzl.spspd.change.actors.mobs.pets.VioletDragon;

import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.ui.Window;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.IconTitle;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Whistle extends Item {

	
	public static final float TIME_TO_USE = 1;

	public static final String AC_CALL = "CALL";
	
		{
		//name = "whistle";
		image = ItemSpriteSheet.POCKET_BALL;
		unique = true;
		stackable = false;
		}
	
				
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (Dungeon.depth < 26) actions.add(AC_CALL);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		
		if (action == AC_CALL) {	
			GameScene.show( new WndWhistle(this) );
		} else {

			super.execute(hero, action);

		}
			
	}	

	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	private static class WndWhistle extends Window {
		
		private static final int BTN_SIZE	= 32;
		private static final float GAP		= 2;
		private static final float BTN_GAP	= 12;
		private static final int WIDTH		= 116;
			
		WndWhistle(final Whistle whis){
			
			IconTitle titlebar = new IconTitle();
			titlebar.icon( new ItemSprite(whis) );
			titlebar.label( Messages.get(this, "title") );
			titlebar.setRect( 0, 0, WIDTH, 0 );
			add( titlebar );
			
			RenderedTextMultiline message =
					PixelScene.renderMultiline(Messages.get(this, "desc"),6);
			message.maxWidth( WIDTH );
			message.setPos(0, titlebar.bottom() + GAP);
			add( message );
			
			
			resize(WIDTH, (int)(message.bottom() + GAP));
		}
	
	}
}

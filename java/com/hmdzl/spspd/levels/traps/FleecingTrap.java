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
package com.hmdzl.spspd.levels.traps;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.npcs.Sheep;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokoban;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanBlack;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanCorner;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanSwitch;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.KindOfArmor;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.wands.WandOfFlock;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;

public class FleecingTrap {

	// 00x66CCEE

	public static void trigger(int pos, Char ch) {

		if (ch instanceof SheepSokoban || ch instanceof SheepSokobanCorner || ch instanceof SheepSokobanSwitch || ch instanceof Sheep || ch instanceof WandOfFlock.MagicSheep
		|| ch instanceof SheepSokobanBlack){
			Camera.main.shake(2, 0.3f);
			ch.destroy();
			ch.sprite.killAndErase();
			ch.sprite.emitter().burst(ShadowParticle.UP, 5);
		
		} else if (ch != null) {
			
			int dmg = ch.HP;	
			boolean port=true;
						
			if (ch == Dungeon.hero) {
				
				Hero hero = Dungeon.hero;
			
				KindOfArmor armor = hero.belongings.armor;
				if (armor!=null){
					hero.belongings.armor=null;
					GLog.n(Messages.get(FleecingTrap.class, "destroy"));
					((HeroSprite) hero.sprite).updateArmor();
					dmg=dmg-1;
					port=false;
				}			
						
		    }
			
			//Port back to 1,1 or something
			
			Camera.main.shake(2, 0.3f);
			ch.sprite.emitter().burst(ShadowParticle.UP, 5);
			
			if (ch == Dungeon.hero && port) {
				 IronKey key = ((Hero)ch).belongings.getKey(IronKey.class, Dungeon.depth);
				 if (key!=null){key.detachAll(Dungeon.hero.belongings.backpack);}				
			  InterlevelScene.mode = InterlevelScene.Mode.SOKOBANFAIL;
			  Game.switchScene(InterlevelScene.class);
			}
									
			if (ch == Dungeon.hero) {

				Camera.main.shake(2, 0.3f);

				if (!ch.isAlive()) {
					Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				} 
			}
		}
		
		Dungeon.hero.next();

	}
	
	public static final Fleece FLEECE = new Fleece();

	public static class Fleece {
	}

	
}

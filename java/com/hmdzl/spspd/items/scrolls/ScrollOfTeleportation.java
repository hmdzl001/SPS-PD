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
package com.hmdzl.spspd.items.scrolls;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokoban;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanCorner;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanSwitch;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.actors.mobs.npcs.Sheep;
import com.hmdzl.spspd.items.wands.WandOfFlock;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.hmdzl.spspd.effects.Speck;
import com.watabou.noosa.tweeners.AlphaTweener;

public class ScrollOfTeleportation extends Scroll {

	{
		//name = "Scroll of Teleportation";
		consumedValue = 10;
		initials = 12;
	}

	@Override
	public void doRead() {

		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		teleportHero(curUser);
		setKnown();

		readAnimation();
	}
	
	@Override
	public void empoweredRead() {
		
		if (Dungeon.bossLevel()){
			GLog.w( Messages.get(this, "no_tele") );
			return;
		}
		
		GameScene.selectCell(new CellSelector.Listener() {
			@Override
			public void onSelect(Integer target) {
				if (target != null) {
					//time isn't spent
					//((HeroSprite)curUser.sprite).read();
					teleportHeroLocation(curUser, target);
				}
			}
			
			@Override
			public String prompt() {
				return Messages.get(ScrollOfTeleportation.class, "prompt");
			}
		});
	}	

	public static void teleportHero(Hero hero) {

		int count = 10;
		int pos;
		do {
			pos = Dungeon.level.randomRespawnCell();
			if (count-- <= 0) {
				break;
			}
		} while (pos == -1);

		if (pos == -1 || Dungeon.bossLevel()) {

			GLog.w( Messages.get(ScrollOfTeleportation.class, "no_tele"));

		} else {

			ScrollOfTeleportation.appear(hero, pos);
			Dungeon.level.press(pos, hero);
			Dungeon.observe();

			GLog.i(Messages.get(ScrollOfTeleportation.class, "tele"));

		}
	}

	public static void teleportHeroLocation(Hero hero, int spot) {
		
		Char ch = Actor.findChar(spot);
		boolean sheepchk = false;
		
		if (ch!=null && (ch instanceof SheepSokoban || ch instanceof SheepSokobanSwitch || ch instanceof SheepSokobanCorner || ch instanceof Sheep || ch instanceof WandOfFlock.MagicSheep)){
			sheepchk = true;
		}

		if (Level.passable[spot] && (Actor.findChar(spot) == null || sheepchk)) {
			
			//GLog.i("clear");
			
			if (Actor.findChar(spot) != null && sheepchk){
				Camera.main.shake(2, 0.3f);
				ch.destroy();
				ch.sprite.killAndErase();
				ch.sprite.emitter().burst(ShadowParticle.UP, 5);
				Level.set(spot, Terrain.WOOL_RUG);
				GameScene.updateMap(spot);
			}
			
			ScrollOfTeleportation.appear(hero, spot);
			Dungeon.level.press(spot, hero);
			Dungeon.observe();

			GLog.i(Messages.get(ScrollOfTeleportation.class, "tele"));
		} 
		
		
		else {
		
		int count = 10;
		int pos;
		do {
			pos = Dungeon.level.randomRespawnCell();
			if (count-- <= 0) {
				break;
			}
		} while (pos == -1);

		if (pos == -1) {

			GLog.w(Messages.get(Scroll.class, "deact"));

		} else {

			ScrollOfTeleportation.appear(hero, pos);
			Dungeon.level.press(pos, hero);
			Dungeon.observe();

			GLog.i(Messages.get(ScrollOfTeleportation.class, "tele"));

		}
	  }
	}
	
	public static void appear( Char ch, int pos ) {

		ch.sprite.interruptMotion();

		ch.move( pos );
		ch.sprite.place( pos );

		if (ch.invisible == 0) {
			ch.sprite.alpha( 0 );
			ch.sprite.parent.add( new AlphaTweener( ch.sprite, 1, 0.4f ) );
		}

		ch.sprite.emitter().start( Speck.factory(Speck.LIGHT), 0.2f, 3 );
		Sample.INSTANCE.play( Assets.SND_TELEPORT );
	}	

	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}

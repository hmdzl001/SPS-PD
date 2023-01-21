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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.GameLog;
import com.hmdzl.spspd.windows.WndError;
import com.hmdzl.spspd.windows.WndStory;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.io.FileNotFoundException;
import java.io.IOException;

public class InterlevelScene extends PixelScene {

	private static final float TIME_TO_FADE = 0.3f;

	public enum Mode {
		DESCEND, ASCEND, CONTINUE, RESURRECT, RETURN, FALL, PORT4,
		PORTSHADOWEATER,PORTPOT, PORTCRAB, PORTTENGU, PORTCOIN, PORTBONE, RETURNSAVE,
		JOURNAL, SOKOBANFAIL, PALANTIR, BOSSRUSH, PORTMAP, SAVE, SLEEP, CHALLENGEBOOK, RESET,CHAOS
	}

    public static Mode mode;

	public static int returnDepth;
	public static int returnPos;
	
	public static int journalpage;
	public static boolean first;
	
	public static int challengelist;	

	public static boolean noStory = false;

	public static boolean fallIntoPit;

	private enum Phase {
		FADE_IN, STATIC, FADE_OUT
	}

    private Phase phase;
	private float timeLeft;

	private RenderedText message;

	private Thread thread;
	private Exception error = null;

	@Override
	public void create() {
		super.create();

		String text = Messages.get(Mode.class, mode.name());
		
		message = PixelScene.renderText(text, 9);
		message.x = (Camera.main.width - message.width()) / 2;
		message.y = (Camera.main.height - message.height()) / 2;
		align(message);
		add(message);

		phase = Phase.FADE_IN;
		timeLeft = TIME_TO_FADE;

		thread = new Thread() {
			@Override
			public void run() {

				try {

					Generator.reset();

					switch (mode) {
					case DESCEND:
						descend();
						break;
					case ASCEND:
						ascend();
						break;
					case CONTINUE:
						restore();
						break;
					case RESURRECT:
						resurrect();
						break;
					case RETURN:
						returnTo();
						break;
					case RETURNSAVE:
						returnToSave();
						break;
					case FALL:
						fall();
						break;
					//case PORT1:
						//portal(1);
						//break;
					//case PORT2:
						//portal(2);
						//break;
					//case PORT3:
						//portal(3);
						//break;
					case PORT4:
						portal(4);
						break;
					//case PORTSEWERS:
						//portal(5);
						//break;
					//case PORTPRISON:
						//portal(6);
						//break;
					//case PORTCAVES:
						//portal(7);
						//break;
					case PORTSHADOWEATER:
						portal(8);
					    break;
				    case PORTPOT:
						portal(9);
						break;
					case PORTCRAB:
						portal(10);
						break;
					case PORTTENGU:
						portal(11);
						break;
					case PORTCOIN:
						portal(12);
						break;
					case PORTBONE:
						portal(13);
						break;
					case JOURNAL:
						journalPortal(journalpage);
						break;	
					case SOKOBANFAIL:
						ascend();
						break;	
					case PALANTIR:
						portal(14);
						break;	
					case BOSSRUSH:
                        portal(15);
						break;
                    case PORTMAP:
                        portal(16);
                        break;
					case SAVE:
					    restore2();
						break;
					case SLEEP:
						restore3();
						break;
					case CHALLENGEBOOK:
						challengePortal(challengelist);
						break;		
					case RESET:
						reset();
						break;
					case CHAOS:
						portal(17);
						break;
					}

					if ((Dungeon.depth % 5) == 0) {
						Sample.INSTANCE.load(Assets.SND_BOSS);
					}

				} catch (Exception e) {

					error = e;

				}

				if (phase == Phase.STATIC && error == null) {
					phase = Phase.FADE_OUT;
					timeLeft = TIME_TO_FADE;
				}
			}
		};
		thread.start();
	}

	@Override
	public void update() {
		super.update();

		float p = timeLeft / TIME_TO_FADE;

		switch (phase) {

		case FADE_IN:
			message.alpha(1 - p);
			if ((timeLeft -= Game.elapsed) <= 0) {
				if (!thread.isAlive() && error == null) {
					phase = Phase.FADE_OUT;
					timeLeft = TIME_TO_FADE;
				} else {
					phase = Phase.STATIC;
				}
			}
			break;

		case FADE_OUT:
			message.alpha(p);

			if (mode == Mode.CONTINUE
					|| (mode == Mode.DESCEND && Dungeon.depth == 1)) {
				Music.INSTANCE.volume(p);
			}
			if ((timeLeft -= Game.elapsed) <= 0) {
				Game.switchScene(GameScene.class);
			}
			break;

		case STATIC:
			if (error != null) {
				String errorMsg;
				if (error instanceof FileNotFoundException) errorMsg = Messages.get(this, "file_not_found");
				else if (error instanceof IOException) errorMsg = Messages.get(this, "io_error");
				else throw new RuntimeException(
							"fatal error occured while moving between floors",
							error);

				add(new WndError(errorMsg) {
					@Override
					public void onBackPressed() {
						super.onBackPressed();
						Game.switchScene(StartScene.class);
					}
                });
				error = null;
			}
			break;
		}
	}

	private void descend() throws IOException {

		Actor.fixTime();
		if (Dungeon.hero == null) {
			//DriedRose.clearHeldGhostHero();
			Dungeon.init();
			if (noStory) {
				Dungeon.chapters.add(WndStory.ID_SEWERS);
				noStory = false;
			}
			GameLog.wipe();
		} else {
			//DriedRose.holdGhostHero( Dungeon.level );
			//DriedRose.clearHeldGhostHero();
			//Dungeon.saveLevel();
			Dungeon.saveAll();
		}

		Level level;
		if ((Dungeon.depth>55) && (Dungeon.depth >= Statistics.realdeepestFloor) && ((Random.Int(100)<101) || Dungeon.depth==56) ){
			level = Dungeon.newMineBossLevel();	
		//}else if (Dungeon.townCheck(Dungeon.depth) && (Dungeon.depth >= Statistics.realdeepestFloor || Random.Int(10)<2)){
			//	level = Dungeon.newLevel();	
	    }else if (Dungeon.depth >= Statistics.deepestFloor){
			level = Dungeon.newLevel();

		} else {
			Dungeon.depth++;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);
		}
		Dungeon.switchLevel(level, level.entrance);
	}

	private void fall() throws IOException {

		Actor.fixTime();
		//DriedRose.clearHeldGhostHero();
		//DriedRose.holdGhostHero( Dungeon.level );
		//Dungeon.saveLevel();
		Dungeon.saveAll();

		Level level;
		if (Dungeon.depth >= Statistics.deepestFloor) {
			level = Dungeon.newLevel();
		} else {
			Dungeon.depth++;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);
		}
		Dungeon.switchLevel(level,
				fallIntoPit ? level.pitCell() : level.randomRespawnCell());
	}

	private void ascend() throws IOException {
		Actor.fixTime();
		//DriedRose.holdGhostHero( Dungeon.level );
       // DriedRose.clearHeldGhostHero();
		Dungeon.saveAll();
		//Dungeon.saveLevel();
		if (Dungeon.hero.heroClass == HeroClass.PERFORMER && Dungeon.skins == 6) {
			if (Dungeon.hero.spp > Dungeon.hero.lvl){
				Dungeon.hero.spp --;
			} else Dungeon.hero.HT--;
		}
		if (Dungeon.depth == 41) {
			  Dungeon.depth=40;
			  Level level = Dungeon.loadLevel(Dungeon.hero.heroClass);
			  Dungeon.switchLevel(level, level.entrance);
		} else if (Dungeon.depth > 26) {
		  Dungeon.depth=1;
		  Level level = Dungeon.loadLevel(Dungeon.hero.heroClass);
		  Dungeon.switchLevel(level, level.entrance);
		} else {
		  Dungeon.depth--;
		  Level level = Dungeon.loadLevel(Dungeon.hero.heroClass);
		  Dungeon.switchLevel(level, level.exit);	
		}
	}

	private void returnTo() throws IOException {
		Actor.fixTime();
		//DriedRose.clearHeldGhostHero();
		//DriedRose.holdGhostHero( Dungeon.level );
       //Dungeon.hero.invisible=0;
        Dungeon.saveAll();
		Dungeon.depth = returnDepth;
		Level level = Dungeon.loadLevel(Dungeon.hero.heroClass);
		Dungeon.switchLevel(level,
				Level.resizingNeeded ? level.adjustPos(returnPos) : returnPos);
	}
	
	private void returnToSave() throws IOException {
		
		Actor.fixTime();
		//DriedRose.clearHeldGhostHero();
       // Dungeon.hero.invisible=0;
        Dungeon.saveAll();
		if (Dungeon.bossLevel(Statistics.deepestFloor)){
			Dungeon.depth = Statistics.deepestFloor-1;
		} else {
			Dungeon.depth = Statistics.deepestFloor;
		}
		Level level = Dungeon.loadLevel(Dungeon.hero.heroClass);
		Dungeon.switchLevel(level, level.entrance);
	}

	private void restore() throws IOException {

		Actor.fixTime();
		GameLog.wipe();
        //DriedRose.clearHeldGhostHero();
		Dungeon.loadGame(StartScene.curClass);
		if (Dungeon.depth == -1) {
			Dungeon.depth = Statistics.deepestFloor;
			Dungeon.switchLevel(Dungeon.loadLevel(StartScene.curClass), -1);
		} else {
			Level level = Dungeon.loadLevel(StartScene.curClass);
			Dungeon.switchLevel(level, Level.resizingNeeded ? level.adjustPos(Dungeon.hero.pos) : Dungeon.hero.pos);
		}
	}
	
	private void restore2() throws IOException {

		Actor.fixTime();
       // DriedRose.clearHeldGhostHero();
		Dungeon.loadGame(StartScene.curClass);
		if (Dungeon.depth == -1) {
			Dungeon.depth = Statistics.deepestFloor;
			Dungeon.switchLevel(Dungeon.loadLevel(StartScene.curClass), -1);
		} else {
			Level level = Dungeon.loadLevel(StartScene.curClass);
			Dungeon.switchLevel( level, Dungeon.hero.pos );
			//Dungeon.switchLevel(level,Level.resizingNeeded ? level.adjustPos(Dungeon.hero.pos): Dungeon.hero.pos);
		}
	}

	private void restore3() throws IOException {

		Actor.fixTime();
		//DriedRose.clearHeldGhostHero();
		Dungeon.loadGame(StartScene.curClass);
		if (Dungeon.depth == -1) {
			Dungeon.depth = Statistics.deepestFloor;
			Dungeon.switchLevel(Dungeon.loadLevel(StartScene.curClass), -1);
		} else {
			Level level = Dungeon.loadLevel(StartScene.curClass);
			Dungeon.switchLevel( level, Dungeon.hero.pos );
			//Dungeon.switchLevel(level,Level.resizingNeeded ? level.adjustPos(Dungeon.hero.pos): Dungeon.hero.pos);
		}
	}

	private void resurrect() {

		Actor.fixTime();
		//DriedRose.clearHeldGhostHero();
        //DriedRose.holdGhostHero( Dungeon.level );
		
		if (Dungeon.level.locked) {
			Dungeon.hero.resurrect(Dungeon.depth);
			Dungeon.depth--;
			Level level = Dungeon.newLevel();
			Dungeon.switchLevel(level, level.entrance);
		} else {
			Dungeon.hero.resurrect(-1);
			Dungeon.resetLevel();
		}
	}
	
	private void reset() {

		Actor.fixTime();
		Dungeon.depth--;
		if (Dungeon.depth > 50)
		{Level level = Dungeon.newChaosLevel();
		Dungeon.switchLevel( level, level.entrance );
		} else {Level level = Dungeon.newLevel();
			Dungeon.switchLevel( level, level.entrance );}

	}	
	
	private void portal(int branch) throws IOException {
		
		Actor.fixTime();
		//DriedRose.clearHeldGhostHero();
		Dungeon.saveAll();
				
		Level level;
		switch(branch){
		case 1:
			level=Dungeon.newCatacombLevel();
			break;
		case 2:
			level = Dungeon.newFortressLevel();
			break;
		case 3:
			level = Dungeon.newChasmLevel();
			break;
		case 4:
			level = Dungeon.newInfestLevel();
			break;
		case 5:
			level = Dungeon.newFieldLevel();
			break;
		case 6:
			level = Dungeon.newBattleLevel();
			break;
		case 7:
			level = Dungeon.newFishLevel();
			break;
		case 8:
			level = Dungeon.newShadowEaterLevel();
			break;
		case 9:
			level = Dungeon.newPotLevel();
			break;
		case 10:
			level = Dungeon.newCrabBossLevel();
			break;
		case 11:
			level = Dungeon.newTenguHideoutLevel();
			break;
		case 12:
			level = Dungeon.newThiefBossLevel();
			break;
		case 13:
			level = Dungeon.newSkeletonBossLevel();
			break;
		case 14:
			level = Dungeon.newZotBossLevel();
			break;
		case 15:
 		    level = Dungeon.newBossRushLevel();
			break;
		case 16:
		    level = Dungeon.newFieldBossLevel();
			break;
		case 17:
			level = Dungeon.newChaosLevel();
			break;
		default:
			level = Dungeon.newLevel();
		}
		Dungeon.switchLevel(level, level.entrance);
	}
	
	private void journalPortal(int branch) throws IOException {
		Actor.fixTime();
		//DriedRose.clearHeldGhostHero();
		//DriedRose.holdGhostHero( Dungeon.level );
		Dungeon.saveAll();
				
		Level level;
		
		/*if (branch==5 && !first){
		   Dungeon.depth=55;
		   level = Dungeon.loadLevel(Dungeon.hero.heroClass);	
		   
		} else*/ if (branch==0 && !first){
			   Dungeon.depth=50;
			   level = Dungeon.loadLevel(Dungeon.hero.heroClass);	
			   
		} else if (branch==7 && !first){
			   Dungeon.depth=67;
			   level = Dungeon.loadLevel(Dungeon.hero.heroClass);				   
		} else {
		   level=Dungeon.newJournalLevel(branch, first);			
		}
		
		Dungeon.switchLevel(level, level.entrance);
	}
	
	private void challengePortal(int branch) throws IOException {
		Actor.fixTime();
		//DriedRose.clearHeldGhostHero();
		//DriedRose.holdGhostHero( Dungeon.level );
		Dungeon.saveAll();
		Level level;
		if (branch==0 && !first){
			Dungeon.depth=26;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);

		} else if (branch==1 && !first){
			Dungeon.depth=27;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);

		} else	if (branch==2 && !first){
			Dungeon.depth=28;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);

		} else	if (branch==3 && !first){
			Dungeon.depth=29;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);

		} else	if (branch==4 && !first){
			Dungeon.depth=30;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);

		} else	if (branch==5 && !first){
			Dungeon.depth=31;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);

		} else	if (branch==6 && !first){
			Dungeon.depth=32;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);

		/*} else if (branch==7 && !first){
			Dungeon.depth=33;
			level = Dungeon.loadLevel(Dungeon.hero.heroClass);*/
		}else{
		level=Dungeon.newChallengeLevel(branch, first);
		}

		Dungeon.switchLevel(level, level.entrance);
	}
		
	@Override
	protected void onBackPressed() {
		// Do nothing
	}
}

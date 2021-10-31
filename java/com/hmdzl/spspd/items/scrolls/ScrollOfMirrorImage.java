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

import java.util.ArrayList;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.npcs.MirrorImage;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ScrollOfMirrorImage extends Scroll {

	private static final int NIMAGES = 3;

	{
		//name = "Scroll of Mirror Image";
		consumedValue = 5;
		initials = 5;
	}
	
	private static final String TXT_PREVENTING = "Something scrambles the illusion magic! ";

	@Override
	public void doRead() {
		
		if (Dungeon.depth>50){
			GLog.w( Messages.get(Scroll.class, "prevent"));
			Sample.INSTANCE.play(Assets.SND_READ);
			Invisibility.dispel();

			setKnown();

			curUser.spendAndNext(TIME_TO_READ);
			return;
		}

		ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			int p = curUser.pos + Level.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null
					&& (Level.passable[p] || Level.avoid[p])) {
				respawnPoints.add(p);
			}
		}

		int nImages = NIMAGES;
		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index(respawnPoints);

			MirrorImage mob = new MirrorImage();
			mob.duplicate(curUser);
			GameScene.add(mob);
			ScrollOfTeleportation.appear(mob, respawnPoints.get(index));

			respawnPoints.remove(index);
			nImages--;
		}

		if (nImages < NIMAGES) {
			setKnown();
		}

		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();
		readAnimation();
	}
	
	@Override
	public void empoweredRead() {
		//spawns 2 images right away, delays 4 of them, 6 total.
		new DelayedImageSpawner(6 - spawnImages(curUser, 2), 2, 3).attachTo(curUser);
		
		setKnown();
		
		Sample.INSTANCE.play( Assets.SND_READ );
		Invisibility.dispel();

		curUser.spendAndNext(TIME_TO_READ);
	}
	
	//returns the number of images spawned
	public static int spawnImages(Hero hero, int nImages ){
		
		ArrayList<Integer> respawnPoints = new ArrayList<Integer>();
		
		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = hero.pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
				respawnPoints.add( p );
			}
		}
		
		int spawned = 0;
		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index( respawnPoints );
			
			MirrorImage mob = new MirrorImage();
			mob.duplicate( hero );
			GameScene.add( mob );
			ScrollOfTeleportation.appear( mob, respawnPoints.get( index ) );
			
			respawnPoints.remove( index );
			nImages--;
			spawned++;
		}
		
		return spawned;
	}
	
	public static class DelayedImageSpawner extends Buff {
		
		public DelayedImageSpawner(){
			this(NIMAGES, NIMAGES, 1);
		}
		
		public DelayedImageSpawner( int total, int perRound, float delay){
			super();
			totImages = total;
			imPerRound = perRound;
			this.delay = delay;
		}
		
		private int totImages;
		private int imPerRound;
		private float delay;
		
		@Override
		public boolean attachTo(Char target) {
			if (super.attachTo(target)){
				spend(delay);
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public boolean act() {
			
			int spawned = spawnImages((Hero)target,  Math.min(totImages, imPerRound));
			
			totImages -= spawned;
			
			if (totImages <0){
				detach();
			} else {
				spend( delay );
			}
			
			return true;
		}
		
		private static final String TOTAL = "images";
		private static final String PER_ROUND = "per_round";
		private static final String DELAY = "delay";
		
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( TOTAL, totImages );
			bundle.put( PER_ROUND, imPerRound );
			bundle.put( DELAY, delay );
		}
		
		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			totImages = bundle.getInt( TOTAL );
			imPerRound = bundle.getInt( PER_ROUND );
			delay = bundle.getFloat( DELAY );
		}
	}

	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}	
}

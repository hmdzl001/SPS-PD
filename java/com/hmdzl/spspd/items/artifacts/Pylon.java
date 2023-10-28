/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.items.artifacts;
//If it weren't super obvious, this is going to become an artifact soon.

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class Pylon extends Artifact {

	public static final float TIME_TO_USE = 1;

	public static final String AC_ZAP       = "ZAP";
	public static final String AC_SET		= "SET";
	public static final String AC_RETURN	= "RETURN";
	public static final String AC_RANKUP	= "RANKUP";
	
	private int returnDepth	= -1;
	private int returnPos;
	
	{
		image = ItemSpriteSheet.ARTIFACT_BEACON;

		levelCap = 3;

		charge = 0;
		chargeCap = 3;

		defaultAction = AC_ZAP;
		usesTargeting = true;
	}
	
	private static final String DEPTH	= "depth";
	private static final String POS		= "pos";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( DEPTH, returnDepth );
		if (returnDepth != -1) {
			bundle.put( POS, returnPos );
		}
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		returnDepth	= bundle.getInt( DEPTH );
		returnPos	= bundle.getInt( POS );
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_ZAP );
		actions.add( AC_SET );
		if (returnDepth != -1) {
			actions.add( AC_RETURN );
		}
		if (level == 3) {
			actions.add( AC_RANKUP );
		}		
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {

		if (action.equals(AC_SET) || action.equals(AC_RETURN)){
			
			if (Dungeon.bossLevel() || Dungeon.depth > 25) {
				hero.spend( 1f );
				GLog.w( Messages.get(this, "preventing") );
				return;
			}
			
			for (int i=0; i < Level.NEIGHBOURS8.length; i++) {
				if (Actor.findChar( hero.pos + Level.NEIGHBOURS8[i] ) != null) {
					GLog.w( Messages.get(this, "creatures") );
					return;
				}
			}
		}

		if (action.equals(AC_ZAP) ){

			curUser = hero;
			int chargesToUse =  1;

			if      (!isEquipped( hero ))       GLog.i( Messages.get(Artifact.class, "need_to_equip") );
			else if (charge < chargesToUse)     GLog.i( Messages.get(this, "no_charge") );
			else {
				GameScene.selectCell(zapper);
			}

		} else if (action.equals(AC_SET)) {
			
			returnDepth = Dungeon.depth;
			returnPos = hero.pos;
			
			hero.spend( 1f );
			hero.busy();
			
			hero.sprite.operate( hero.pos );
			Sample.INSTANCE.play( Assets.SND_BEACON );
			
			GLog.i( Messages.get(this, "return") );
			
		} else if (action.equals(AC_RETURN)) {
			
			if (returnDepth == Dungeon.depth) {
				ScrollOfTeleportation.appear( hero, returnPos );
				Dungeon.level.press( returnPos, hero );
				Dungeon.observe();
			} else {

				Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null) buff.detach();

				//for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] ))
					//if (mob instanceof DriedRose.GhostHero) mob.destroy();

				InterlevelScene.mode = InterlevelScene.Mode.RETURN;
				InterlevelScene.returnDepth = returnDepth;
				InterlevelScene.returnPos = returnPos;
				Game.switchScene( InterlevelScene.class );
			}
			
			
		} else if (action.equals(AC_RANKUP)) {
			hero.TRUE_HT +=5;
			hero.hitSkill++;
			hero.evadeSkill++;
			hero.magicSkill++;
			level = 0;
			Dungeon.hero.updateHT(true);
			hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "rankup"));
		}else {
			
			super.execute( hero, action );
			
		}
	}

	protected CellSelector.Listener zapper = new  CellSelector.Listener() {

		@Override
		public void onSelect(Integer target) {

			if (target == null) return;

			Invisibility.dispel();
			charge -=  1;
			updateQuickslot();

			if (Actor.findChar(target) == curUser){
				ScrollOfTeleportation.teleportHero(curUser);
				curUser.spendAndNext(1f);
			} else {
				final Ballistica bolt = new Ballistica( curUser.pos, target, Ballistica.MAGIC_BOLT );
				final Char ch = Actor.findChar(bolt.collisionPos);

				if (ch == curUser){
					ScrollOfTeleportation.teleportHero(curUser);
					curUser.spendAndNext( 1f );
				} else {
					Sample.INSTANCE.play( Assets.SND_ZAP );
					curUser.sprite.zap(bolt.collisionPos);
					curUser.busy();

					MagicMissile.force(curUser.sprite.parent, bolt.sourcePos, bolt.collisionPos, new Callback() {
						@Override
						public void call() {
							if (ch != null) {

								int count = 10;
								int pos;
								do {
									pos = Dungeon.level.randomRespawnCell();
									if (count-- <= 0) {
										break;
									}
								} while (pos == -1);


								if (pos == -1 || Dungeon.bossLevel()) {

									GLog.w( Messages.get(ScrollOfTeleportation.class, "no_tele") );

								} else if (ch.properties().contains(Char.Property.IMMOVABLE)) {

									GLog.w( Messages.get(Pylon.class, "tele_fail") );

								} else  {

									ch.pos = pos;
									ch.sprite.place(ch.pos);
									ch.sprite.visible = Dungeon.visible[pos];

								}
							}
							curUser.spendAndNext(1f);
						}
					});

				}


			}

		}

		@Override
		public String prompt() {
			return Messages.get(Pylon.class, "prompt");
		}
	};

	@Override
	protected ArtifactBuff passiveBuff() {
		return new beaconRecharge();
	}

	@Override
	public Item upgrade() {
		if (level == levelCap) return this;
		if (charge < chargeCap) charge ++;
		GLog.p( Messages.get(this, "levelup") );
		return super.upgrade();
	}

	@Override
	public String desc() {
		String desc = super.desc();
		if (returnDepth != -1){
			desc += "\n\n" + Messages.get(this, "desc_set", returnDepth);
		}
		return desc;
	}

	public void reset() {
		returnDepth = -1;
	}
	
	private static final Glowing WHITE = new Glowing( 0xFFFFFF );
	
	@Override
	public Glowing glowing() {
		return returnDepth != -1 ? WHITE : null;
	}

	public class beaconRecharge extends ArtifactBuff{
		@Override
		public boolean act() {
			if (charge < chargeCap && !cursed ) {
				partialCharge += 1 / (100f - (chargeCap - charge)*10f);

				if (partialCharge >= 1) {
					partialCharge --;
					charge ++;

					if (charge == chargeCap){
						partialCharge = 0;
					}
				}
			}

			updateQuickslot();
			spend( TICK );
			return true;
		}
		public void gainExp( float levelPortion ) {
			if (cursed) return;
			exp += Math.round(levelPortion*100);
			if (exp > 300 && level() < levelCap){
				exp -= 300;
				upgrade();
			}

		}		
	}
}

/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.ActionIndicator;
import com.hmdzl.spspd.ui.AttackIndicator;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class NewCombo extends Buff implements ActionIndicator.Action {

		private int count = 0;
		private float comboTime = 0f;
		private int misses = 0;

	public String status() {
		return count > 0 ? Integer.toString( count ) : null;
	}


	@Override
		public int icon() {
			return BuffIndicator.COMBO;
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		public void hit() {

			count++;
			comboTime = 4f;
			misses = 0;

			//if (count >= 2) {

			//	ActionIndicator.setAction( this );
			//	GLog.p( Messages.get(this, "combo", count) );

			//}

		}

		public void miss(){
			misses++;
			comboTime = 4f;
			if (misses >= 2){
				detach();
			}
		}

		@Override
		public void detach() {
			super.detach();
			ActionIndicator.clearAction(this);
		}

		@Override
		public boolean act() {
			comboTime-=TICK;
			spend(TICK);
			if (comboTime <= 0) {
				detach();
			}
			return true;
		}

		@Override
		public String desc() {
			String desc = Messages.get(this, "desc");

			 if (count >= 8)desc += "\n\n" + Messages.get(this, "crush_desc");
			else if (count >= 6)desc += "\n\n" + Messages.get(this, "slam_desc");
			else if (count >= 4)desc += "\n\n" + Messages.get(this, "cleave_desc");
			else if (count >= 2)desc += "\n\n" + Messages.get(this, "clobber_desc");

			return desc;
		}

		private static final String COUNT = "count";
		private static final String TIME  = "combotime";
		private static final String MISSES= "misses";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(COUNT, count);
			bundle.put(TIME, comboTime);
			bundle.put(MISSES, misses);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			count = bundle.getInt( COUNT );
			if (count >= 2) ActionIndicator.setAction(this);
			comboTime = bundle.getFloat( TIME );
			misses = bundle.getInt( MISSES );
		}

		@Override
		public Image getIcon() {
			Image icon;

			icon = new ItemSprite(new Item(){ {image = ItemSpriteSheet.ERROR_WEAPON; }});

            if (count >= 8)icon.tint(0xFFFFCC00);
			else if (count >= 6)icon.tint(0xFFFFFF00);
			else if (count >= 4)icon.tint(0xFFCCFF00);
			else                icon.tint(0xFF00FF00);

			return icon;
		}

		@Override
		public void doAction() {
			GameScene.selectCell(finisher);
		}

		private enum finisherType{
			CLOBBER, CLEAVE, SLAM, CRUSH
        }

		private CellSelector.Listener finisher = new CellSelector.Listener() {

			private finisherType type;

			@Override
			public void onSelect(Integer cell) {
				if (cell == null) return;
				final Char enemy = Actor.findChar( cell );
				if (enemy == null || !((Hero)target).canAttack(enemy) || target.isCharmedBy( enemy )){
					GLog.w( Messages.get(NewCombo.class, "bad_target") );
				} else {
					target.sprite.attack(cell, new Callback() {
						@Override
						public void call() {
							if (count >= 8)type = finisherType.CRUSH;
							else if (count >= 6)type = finisherType.SLAM;
							else if (count >= 4)type = finisherType.CLEAVE;
							else                type = finisherType.CLOBBER;
							doAttack(enemy);
						}
					});
				}
			}

			private void doAttack(final Char enemy){

				AttackIndicator.target(enemy);

				int dmg = target.damageRoll();

				//variance in damage dealt
				switch(type){
					case CLOBBER:
						dmg = Math.round(dmg*1.6f);
						break;
					case CLEAVE:
						dmg = Math.round(dmg*2.5f);
						break;
					case SLAM:
						//rolls 2 times, takes the highest roll
						int dmgReroll = target.damageRoll();
						if (dmgReroll > dmg) dmg = dmgReroll;
						dmg = Math.round(dmg*2.6f);
						break;
					case CRUSH:
						//rolls 4 times, takes the highest roll
						for (int i = 1; i < 4; i++) {
							dmgReroll = target.damageRoll();
							if (dmgReroll > dmg) dmg = dmgReroll;
						}
						dmg = Math.round(dmg*3.5f);
						break;
				}

				dmg -= Random.IntRange( 0, enemy.drRoll() );
				dmg = target.attackProc(enemy, dmg);
				dmg = enemy.defenseProc(target, dmg);
				enemy.damage( dmg, this,1 );

				//special effects
				switch (type){
					case CLOBBER:
						if (enemy.isAlive()){
							if (!enemy.properties().contains(Char.Property.IMMOVABLE)){
								for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
									int ofs = Floor.NEIGHBOURS8[i];
									if (enemy.pos - target.pos == ofs) {
										int newPos = enemy.pos + ofs;
										if ((Floor.passable[newPos] || Floor.avoid[newPos]) && Actor.findChar( newPos ) == null) {

											Actor.addDelayed( new Pushing( enemy, enemy.pos, newPos ), -1 );

											enemy.pos = newPos;
											// FIXME
											if (enemy instanceof Mob) {
												Dungeon.depth.mobPress( (Mob)enemy );
											} else {
												Dungeon.depth.press( newPos, enemy );
											}

										}
										break;
									}
								}
							}
							Buff.prolong(enemy, Vertigo.class, Random.NormalIntRange(1, 4));
						}
						break;
					case SLAM:
						Buff.affect(target,ShieldArmor.class).level(dmg/5);
						break;
					default:
						//nothing
						break;
				}

				if (target.buff(FireImbue.class) != null)
					target.buff(FireImbue.class).proc(enemy);
				if (target.buff(EarthImbue.class) != null)
					target.buff(EarthImbue.class).proc(enemy);
                if (target.buff(FrostImbue.class) != null)
                    target.buff(FrostImbue.class).proc(enemy);
                if (target.buff(BloodImbue.class) != null)
                    target.buff(BloodImbue.class).proc(enemy);

                Sample.INSTANCE.play( Assets.SND_HIT, 1, 1, Random.Float( 0.8f, 1.25f ) );
				enemy.sprite.bloodBurstA( target.sprite.center(), dmg );
				enemy.sprite.flash();

				if (!enemy.isAlive()){
					GLog.i( Messages.capitalize(Messages.get(Char.class, "defeat", enemy.name)) );
				}

				Hero hero = (Hero)target;

				//Post-attack behaviour
				switch(type) {
                    case CLEAVE:
                        if (!enemy.isAlive()) {
                            //combo isn't reset, but rather increments with a cleave kill, and grants more time.
                            hit();
                            comboTime = 10f;
                        } else {
                            detach();
                            ActionIndicator.clearAction(NewCombo.this);
                        }
                        hero.spendAndNext(hero.attackDelay());
                        break;

					default:
						detach();
						ActionIndicator.clearAction(NewCombo.this);
						hero.spendAndNext(hero.attackDelay());
						break;
				}

			}

			@Override
			public String prompt() {
				 if (count >= 8)return Messages.get(NewCombo.class, "crush_prompt");
				else if (count >= 6)return Messages.get(NewCombo.class, "slam_prompt");
				else if (count >= 4)return Messages.get(NewCombo.class, "cleave_prompt");
				else                return Messages.get(NewCombo.class, "clobber_prompt");
			}
		};
	}


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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Chains;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class FlyChains extends Artifact {

	public static final String AC_CAST       = "CAST";
	public static final String AC_LOCKED = "LOCKED";

	{
		image = ItemSpriteSheet.ARTIFACT_CHAINS;

		levelCap = 5;
		exp = 0;

		charge = 5;

		defaultAction = AC_CAST;
		usesTargeting = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		if (isEquipped(hero) && charge > 0 && !cursed)
			actions.add(AC_CAST);
		if (isEquipped(hero) && level > 1 && !cursed)
			actions.add(AC_LOCKED);		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_CAST)){

			curUser = hero;

			if      (!isEquipped( hero ))       GLog.i( Messages.get(Artifact.class, "need_to_equip") );
			else if (charge < 1)                GLog.i( Messages.get(this, "no_charge") );
			else if (cursed)                    GLog.w( Messages.get(this, "cursed") );
			else {
				GameScene.selectCell(caster);
			}

		} else if (action.equals(AC_LOCKED)){

			curUser = hero;

			if      (!isEquipped( hero ))       GLog.i( Messages.get(Artifact.class, "need_to_equip") );
			else if (charge < 1)                GLog.i( Messages.get(this, "no_charge") );
			else if (cursed)                    GLog.w( Messages.get(this, "cursed") );
			else {
				GameScene.selectCell(locker);
			}

		} else
			super.execute(hero, action);
	}

	private CellSelector.Listener caster = new CellSelector.Listener(){

		@Override
		public void onSelect(Integer target) {
			if (target != null && (Dungeon.depth.visited[target] || Dungeon.depth.mapped[target])){

				//ballistica does not go through walls on pre-rework boss arenas
				int missileProperties = (Dungeon.dungeondepth == 10 || Dungeon.dungeondepth == 15 || Dungeon.dungeondepth == 20 || Dungeon.dungeondepth == 25) ?
						Ballistica.PROJECTILE : Ballistica.STOP_CHARS | Ballistica.STOP_TARGET;

				final Ballistica chain = new Ballistica(curUser.pos, target, missileProperties);

				//determine if we're grabbing an enemy, pulling to a location, or doing nothing.
				if (Actor.findChar( chain.collisionPos ) != null){
					int newPos = -1;
					for (int i : chain.subPath(1, chain.dist)){
						if (!Floor.solid[i] && Actor.findChar(i) == null){
							newPos = i;
							break;
						}
					}
					if (newPos == -1){
						GLog.w( Messages.get(EtherealChains.class, "does_nothing") );
					} else {
						final int newMobPos = newPos;
						final Char affected = Actor.findChar( chain.collisionPos );
						int chargeUse = Floor.distance(affected.pos, newMobPos);
						if (chargeUse > charge) {
							GLog.w( Messages.get(EtherealChains.class, "no_charge") );
							return;
						} else if (affected.properties().contains(Char.Property.IMMOVABLE)) {
							GLog.w( Messages.get(EtherealChains.class, "cant_pull") );
							return;
						} else {
							charge -= chargeUse;
							updateQuickslot();
						}
						curUser.busy();
						curUser.sprite.parent.add(new Chains(curUser.pos, affected.pos, new Callback() {
							public void call() {
								Actor.add(new Pushing(affected, affected.pos, newMobPos, new Callback() {
									public void call() {
										Dungeon.depth.press(newMobPos, affected);
									}
								}));
								affected.pos = newMobPos;
								Dungeon.observe();
								curUser.spendAndNext(1f);
							}
						}));
					}

				} else if (Floor.solid[chain.path.get(chain.dist)]
						|| (chain.dist > 0 && Floor.solid[chain.path.get(chain.dist-1)])
						|| (chain.path.size() > chain.dist+1 && Floor.solid[chain.path.get(chain.dist+1)])
						//if the player is trying to grapple the edge of the map, let them.
						|| (chain.path.size() == chain.dist+1)) {
					int newPos = -1;
					for (int i : chain.subPath(1, chain.dist)){
						if (!Floor.solid[i] && Actor.findChar(i) == null) newPos = i;
						}
					if (newPos == -1) {
						GLog.w( Messages.get(EtherealChains.class, "does_nothing") );
					} else {
						final int newHeroPos = newPos;
						int chargeUse = Floor.distance(curUser.pos, newHeroPos);
						if (chargeUse > charge){
							GLog.w( Messages.get(EtherealChains.class, "no_charge") );
							return;
						} else {
							charge -= chargeUse;
							updateQuickslot();
						}
						curUser.busy();
						curUser.sprite.parent.add(new Chains(curUser.pos, target, new Callback() {
							public void call() {
								Actor.add(new Pushing(curUser, curUser.pos, newHeroPos, new Callback() {
									public void call() {
										Dungeon.depth.press(newHeroPos, curUser);
									}
								}));
								curUser.spendAndNext(1f);
								curUser.pos = newHeroPos;
								Dungeon.observe();
							}
						}));
					}

				} else {
					GLog.i( Messages.get(EtherealChains.class, "nothing_to_grab") );
				}

			}

		}

		@Override
		public String prompt() {
			return Messages.get(EtherealChains.class, "prompt");
		}
	};

	private CellSelector.Listener locker = new CellSelector.Listener(){

		@Override
		public void onSelect(Integer target) {
			if (target != null && (Dungeon.depth.visited[target] || Dungeon.depth.mapped[target])){

				if (Actor.findChar( target ) != null){
					Char mob = Actor.findChar(target);
				Buff.affect(mob,Locked.class,level*4f);
				Buff.affect(mob,Silent.class,level*4f);
				Buff.affect(mob,AttackDown.class,level*4f).level(90);
				Buff.affect(mob,Slow.class,level*4f);
				level--;
				Sample.INSTANCE.play(Assets.SND_BURNING);
				curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
                curUser.spendAndNext(1f);
				updateQuickslot();	
				
				} else {
					GLog.i( Messages.get(EtherealChains.class, "nothing_to_grab") );
				}

			}

		}

		@Override
		public String prompt() {
			return Messages.get(EtherealChains.class, "prompt");
		}
	};	
	
	@Override
	protected ArtifactBuff passiveBuff() {
		return new chainsRecharge2();
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if (isEquipped( Dungeon.hero )){
			desc += "\n\n";
			if (cursed)
				desc += Messages.get(this, "desc_cursed");
			else
				desc += Messages.get(this, "desc_equipped");
		}
		return desc;
	}

	public class chainsRecharge2 extends ArtifactBuff{

		@Override
		public boolean act() {
			int chargeTarget = 5+(level()*2);
			if (charge < chargeTarget && !cursed ) {
				partialCharge += 1 / (40f - (chargeTarget - charge)*2f);
			} else if (cursed && Random.Int(100) == 0){
				Buff.prolong( target, Cripple.class, 10f);
			}

			if (partialCharge >= 1) {
				partialCharge --;
				charge ++;
			}

			updateQuickslot();

			spend( TICK );

			return true;
		}

		public void gainExp( float levelPortion ) {
			if (cursed) return;

			exp += Math.round(levelPortion*100);

			//past the soft charge cap, gaining  charge from leveling is slowed.
			if (charge > 5+(level()*2)){
				levelPortion *= (5+((float)level()*2))/charge;
			}
			partialCharge += levelPortion*10f;

			if (exp > 100+level()*50 && level() < levelCap){
				exp -= 100+level()*50;
				GLog.p( Messages.get(this, "levelup") );
				upgrade();
			}

		}
	}
}

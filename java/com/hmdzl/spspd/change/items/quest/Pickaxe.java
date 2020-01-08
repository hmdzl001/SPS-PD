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
package com.hmdzl.spspd.change.items.quest;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Bat;
import com.hmdzl.spspd.change.actors.mobs.ElderAvatar;
import com.hmdzl.spspd.change.actors.mobs.King;
import com.hmdzl.spspd.change.actors.mobs.LichDancer;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Pickaxe extends Weapon {

	public static final String AC_MINE = "MINE";

	public static final float TIME_TO_MINE = 2;

	private static final String TXT_NO_VEIN = "There is no dark gold vein near you to mine";

	private static final Glowing BLOODY = new Glowing(0x550000);

	{
		//name = "pickaxe";
		image = ItemSpriteSheet.PICKAXE;

		unique = true;

		defaultAction = AC_MINE;

		STR = 14;
		MIN = 10;
		MAX = 22;
	}

	public boolean bloodStained = false;
	
	
		
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_MINE);
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {

		if (action == AC_MINE) {

			if ((Dungeon.depth < 11 || Dungeon.depth > 15) && !(Dungeon.depth==32)) {
				GLog.w(Messages.get(this,"no_vein"));
				return;
			}

			for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {

				final int pos = hero.pos + Level.NEIGHBOURS8[i];
				if (Dungeon.level.map[pos] == Terrain.WALL_DECO) {

					hero.spend(TIME_TO_MINE);
					hero.busy();

					hero.sprite.attack(pos, new Callback() {

						@Override
						public void call() {

							CellEmitter.center(pos).burst(
									Speck.factory(Speck.STAR), 7);
							Sample.INSTANCE.play(Assets.SND_EVOKE);

							Level.set(pos, Terrain.WALL);
							GameScene.updateMap(pos);

							DarkGold gold = new DarkGold();
							if (gold.doPickUp(Dungeon.hero)) {
								GLog.i( Messages.get(Dungeon.hero, "you_now_have", gold.name()));
							} else {
								Dungeon.level.drop(gold, hero.pos).sprite
										.drop();
							}

							Hunger hunger = hero.buff(Hunger.class);
							if (hunger != null && !hunger.isStarving()) {
								hunger.satisfy(-10);
								BuffIndicator.refreshHero();
							}

							hero.onOperateComplete();
						}
					});

					return;
				}
			}

			GLog.w(Messages.get(this,"no_vein"));

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	@Override
	public Item upgrade(boolean enchant) {
		
		MIN += 3;
		MAX += 3;
		super.upgrade(enchant);
		updateQuickslot();
		return this;
	}
	
	@Override
	public Item degrade() {
		return super.degrade();
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		if (!bloodStained && defender instanceof Bat ) {
			bloodStained = true;
			updateQuickslot();
		}
		if (defender instanceof King.DwarfKingTomb || defender instanceof ElderAvatar.Obelisk || defender instanceof LichDancer.BatteryTomb){
			defender.damage(Random.Int(100,200), this);
		}
		
		switch (Random.Int (10)) {
        case 1 :
            Buff.affect(defender, Bleeding.class).set(10);
			break;
		case 2 :
            Buff.affect(defender, Ooze.class);
			break;
		case 3 :
            Buff.affect(defender, Poison.class).set(
					Random.Int(7, 10) * Poison.durationFactor(defender) );
			break;	
		default:
			break;
        }
		
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
	}

	private static final String BLOODSTAINED = "bloodStained";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);

		bundle.put(BLOODSTAINED, bloodStained);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		bloodStained = bundle.getBoolean(BLOODSTAINED);
	}

	@Override
	public Glowing glowing() {
		return bloodStained ? BLOODY : null;
	}

	/*@Override
	public String info() {
		return "This is a large and sturdy tool for breaking rocks. Probably it can be used as a weapon.";
	}*/
}

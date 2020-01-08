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
package com.hmdzl.spspd.change.items.misc;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.BalanceFaith;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.DemonFaith;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.HumanFaith;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.LifeFaith;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.MechFaith;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;

import com.hmdzl.spspd.change.items.weapon.missiles.RiceBall;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndItem;
import com.watabou.noosa.audio.Sample;

public class FaithSign extends Item {

	{
		//name = "faithsign";
		image = ItemSpriteSheet.FAITH_SIGN;
		unique = true;
		defaultAction = AC_CHOOSE;
	}

	public static final String AC_CHOOSE = "CHOOSE";

    public static final String AC_DEMON = "DEMON";
	public static final String AC_HUMAN = "HUMAN";
	public static final String AC_MECH = "MECH";
	public static final String AC_LIFE = "LIFE";
	public static final String AC_BALANCE = "BALANCE";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		actions.add(AC_DEMON);
		actions.add(AC_HUMAN);
		actions.add(AC_MECH);
		actions.add(AC_LIFE);
		actions.add(AC_BALANCE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals( AC_CHOOSE )){
			
			GameScene.show(new WndItem(null, this, true));
			
		} else if (action.equals(AC_DEMON)) {
			Buff.detach(hero,DemonFaith.class);
			Buff.detach(hero,HumanFaith.class);
			Buff.detach(hero,MechFaith.class);
			Buff.detach(hero,LifeFaith.class);
			Buff.detach(hero,BalanceFaith.class);
			Buff.affect(hero,DemonFaith.class);
			hero.spendAndNext(1f);

		} else if (action.equals(AC_HUMAN)) {
		    Buff.detach(hero,DemonFaith.class);
			Buff.detach(hero,HumanFaith.class);
			Buff.detach(hero,MechFaith.class);
			Buff.detach(hero,LifeFaith.class);
			Buff.detach(hero,BalanceFaith.class);
			Buff.affect(hero,HumanFaith.class);
			hero.spendAndNext(1f);

		} else if (action.equals(AC_MECH)) {
			Buff.detach(hero,DemonFaith.class);
			Buff.detach(hero,HumanFaith.class);
			Buff.detach(hero,MechFaith.class);
			Buff.detach(hero,LifeFaith.class);
			Buff.detach(hero,BalanceFaith.class);
			Buff.affect(hero,MechFaith.class);
			hero.spendAndNext(1f);
			
		} else if (action.equals(AC_LIFE)) {
			Buff.detach(hero,DemonFaith.class);
			Buff.detach(hero,HumanFaith.class);
			Buff.detach(hero,MechFaith.class);
			Buff.detach(hero,LifeFaith.class);
			Buff.detach(hero,BalanceFaith.class);
			Buff.affect(hero,LifeFaith.class);
			hero.spendAndNext(1f);
			
		} else if (action.equals(AC_BALANCE)) {
			Buff.detach(hero,DemonFaith.class);
			Buff.detach(hero,HumanFaith.class);
			Buff.detach(hero,MechFaith.class);
			Buff.detach(hero,LifeFaith.class);
			Buff.detach(hero,BalanceFaith.class);
			Buff.affect(hero,BalanceFaith.class);
			hero.spendAndNext(1f);
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

}

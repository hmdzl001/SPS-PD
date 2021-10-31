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
package com.hmdzl.spspd.actors.mobs.npcs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Journal;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.mobs.Golem;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.Monk;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.quest.DwarfToken;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.levels.CityLevel;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.AshWolfSprite;
import com.hmdzl.spspd.sprites.ImpSprite;
 
import com.hmdzl.spspd.windows.WndImp;
import com.hmdzl.spspd.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class AshWolf extends NPC {

	{
		//name = "ashwolf";
		spriteClass = AshWolfSprite.class;
		properties.add(Property.ORC);
		properties.add(Property.IMMOVABLE);
	}

	private boolean first=true;
	
	private static final String FIRST = "first";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(FIRST, first);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		first = bundle.getBoolean(FIRST);
	}

	@Override
	protected boolean act() {

        throwItem();
		return super.act();
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public boolean interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
			if (first){
				yell(Messages.get(this, "yell1"));
                //Plant.Seed seed = (Plant.Seed) Generator.random(Generator.Category.SEED4);
				Dungeon.level.plant((Plant.Seed)(Generator.random(Generator.Category.SEED4)),Dungeon.hero.pos);
				first=false;
			} else {
				yell(Messages.get(this, "yell2"));
			}	
		return false;
	}


}

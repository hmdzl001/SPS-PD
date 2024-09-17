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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.quest.GnollClothes;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.OldNewStwistSprite;
import com.hmdzl.spspd.windows.WndONS;

public class OldNewStwist extends NPC {

	{
		//name = "oldnewstwist";
		spriteClass = OldNewStwistSprite.class;

		state = SLEEPING;
		properties.add(Property.ORC);
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	protected Char chooseEnemy() {
		return null;
	}

	@Override
	public void damage(int dmg, Object src, int type) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.EASTERWEAPON);
	}
	
	@Override
	public boolean interact() {

		GnollClothes clothes = Dungeon.hero.belongings.getItem(GnollClothes.class);

		sprite.turnTo(pos, Dungeon.hero.pos);
		if (state == SLEEPING && Dungeon.gnollmission==false) {
			notice();
			yell(Messages.get(this, "yell1"));
			yell(Messages.get(this, "yell2"));
            state = PASSIVE;
		} else if (Dungeon.gnollmission==false && clothes == null){
			yell(Messages.get(this, "yell3"));
		} else if (Dungeon.gnollmission==false && clothes!=null ){
			yell(Messages.get(this, "yell4"));
				GameScene.show(new WndONS(this, clothes));
		} else if (Dungeon.gnollmission==true && state == PASSIVE) {
			yell(Messages.get(this, "yell5"));
		} else {
			notice();
			yell(Messages.get(this, "yell6"));
			state = PASSIVE;
		}
		return true;
	}

	@Override
	public String description() {
		return ((OldNewStwistSprite) sprite).gnollmission ? 
		Messages.get(this, "desc_gnollmission") : super.description();
	}
}

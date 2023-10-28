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
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ThankListSprite;
import com.watabou.utils.Random;

public class ThankList extends NPC {

	{
		//name = "ThankList";
		spriteClass = ThankListSprite.class;
		//state = WANDERING;
		properties.add(Property.UNKNOW);
	}

	/*@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}	*/
	
	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}


	@Override
	protected Char chooseEnemy() {
		return null;
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
		switch (Random.Int (47)) {
            case 0:
			yell("SuperSaiyan99");
			break;
			case 1:
			yell("‎Noodlemire");
			break;
			case 3:
			yell("猫佑薄荷");
			break;
			case 4:
			yell("RavenWolf");
			break;
			case 5:
			yell("Watabou");
			break;
			case 6:
			yell("‎Bilboldev");
			break;
			case 7:
			yell("‎ConsideredHamster");
			break;
			case 8:
			yell("‎Dachhack");
			break;
			case 9:
			yell("‎HeXA");
			break;
			case 10:
			yell("‎Juh9870");
			break;
			case 11:
			yell("Locastan");
			break;	
			case 12:
			yell("‎NYRDS");
			break;	
			case 13:
			yell("‎OldNewStwist");
			break;	
			case 14:
			yell("‎SadSaltan");
			break;	
			case 15:
			yell("Typedscroll");
			break;	
			case 16:
			yell("‎Leppan");
			break;	
			case 17:
			yell("Sharku2011");
			break;
			case 18:
			yell("‎老雷霆");
			break;
			case 19:
			yell("‎qi");
			break;
			case 20:
			yell("‎ian949");			
			break;
			case 22:
			yell("‎RaiseDead");
			break;
			case 23:
			yell("‎指虎教徒");	
			break;
			case 25:
			yell("白幽妹");
			break;
			case 26:
			yell("章华");
			break;
			case 27:
				yell("Hmorow12");
				break;
			case 28:
				yell("Eccentric_eye");
				break;
			case 29:
				yell("Ropuszka");
				break;
			case 30:
				yell("TrashboxBobylev");
				break;
			case 31:
				yell("Q1a2q1a2");
				break;
			case 32:
				yell("QueenOfTheMeowntain");
				break;
			case 33:
				yell("Buue2");
				break;
			case 34:
				yell("lighthouse64");
				break;
			case 35:
				yell("najecniv20");
				break;
			case 36:
				yell("Torian99");
				break;
			case 37:
				yell("deeperbroken");
				break;
			case 38:
				yell("DGN1");
				break;
			case 39:
				yell("Eldskutf");
				break;
			case 41:
				yell("SkyMuffin");
				break;
			case 42:
				yell("DragosCat1");
				break;
			case 43:
				yell("BlankDriver");
				break;
			case 44:
				yell("A神");
				break;
			case 45:
				yell("和缺月拉钩丶");
				break;
			case 46:
				yell(Messages.get(this, "yell1"));
				break;
		}
		return true;
	}

}

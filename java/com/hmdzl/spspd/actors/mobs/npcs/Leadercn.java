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
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.TestMob;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.PuddingCup;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.sellitem.DevUpPlan;
import com.hmdzl.spspd.items.wands.WandOfTest;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CoconutSprite;
import com.hmdzl.spspd.windows.WndNewNpcMessage;
import com.hmdzl.spspd.windows.WndQuest;

public class Leadercn extends NPC {

	{
		//name = Messages.get(this,"name");
		spriteClass = CoconutSprite.class;
		properties.add(Property.MECH);
		
	}

	@Override
	protected boolean act() {
		throwItem();
		pushother();
		return super.act();
	}	
	
	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public Item SupercreateLoot(){
		return new DevUpPlan();
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
		this.sprite.emitter().burst(Speck.factory(Speck.STEAM),6);
		switch (this.FRIEND) {
            case 0:
            teacher(this,Messages.get(this, "yell1"));
			//yell(Messages.get(this, "yell1"));
			Dungeon.depth.drop(new IronKey(Dungeon.dungeondepth), Dungeon.hero.pos).sprite.drop();
			break;
			case 10:
				teacher(this,Messages.get(this, "yell2"));
			break;
			case 20:
				teacher(this,Messages.get(this, "yell3"));

			break;	
            case 30:
				teacher(this,Messages.get(this, "yell4"));

			break;			
            case 40:
				teacher(this,Messages.get(this, "yell5"));

			Dungeon.depth.drop(new StoneOre(), Dungeon.hero.pos).sprite.drop();
			break;			
            case 50:
				teacher(this,Messages.get(this, "yell6"));

			Dungeon.depth.drop(new WandOfTest().identify(), Dungeon.hero.pos).sprite.drop();
			break;			
            case 60:
				teacher(this,Messages.get(this, "yell7"));

			Dungeon.depth.drop(new DungeonBomb(), Dungeon.hero.pos).sprite.drop();
			break;			
            case 70:
				teacher(this,Messages.get(this, "yell8"));

				Dungeon.depth.drop(new PuddingCup(), this.pos).sprite.drop();
			break;						
		}

		for (Mob mob : Dungeon.depth.mobs) {
			if (mob instanceof Leadercn && mob.isAlive())
				((NPC)mob).FRIEND += 10;
		}


		this.destroy();
		this.sprite.die();
		return true;
	}

	private void teacher(NPC npc,String text ) {
		GameScene.show(new WndNewNpcMessage(npc,text ));
	}
}

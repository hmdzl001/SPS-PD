package com.hmdzl.spspd.actors.mobs.npcs;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.sprites.SadSaltanSprite;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.watabou.utils.Random;

public class SadSaltan extends NPC {

	{
		//name = "SadSaltan";
		spriteClass = SadSaltanSprite.class;
		state = WANDERING;
		properties.add(Property.HUMAN);
	}

	/*@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}*/
	
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
		switch (Random.Int (3)) {
            case 0:
			yell(Messages.get(this, "yell1"));
			break;
			case 1:
			yell(Messages.get(this, "yell2"));
			break;
		    case 2:
			yell(Messages.get(this, "yell3"));
			break;			
		}
		return true;
	}
}

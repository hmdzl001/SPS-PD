package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.messages.Messages;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfForce extends Ring {

	{
		//name = "Ring of Force";
		initials = 3;
		sname = "DMG";
	}
		;
	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", Math.min(65, 5 + level*2), Dungeon.hero.STR/2 + level, Dungeon.hero.STR/2 * level + Dungeon.hero.STR/2);
		} else {
			return "???";
		}
	}	
		

	@Override
	protected RingBuff buff() {
		return new RingForce();
	}
	
	public class RingForce extends RingBuff {
	}
}

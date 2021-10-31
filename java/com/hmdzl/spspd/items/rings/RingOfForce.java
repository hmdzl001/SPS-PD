package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

import java.text.DecimalFormat;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfForce extends Ring {

	{
		//name = "Ring of Force";
		initials = 3;
	}
		;
	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", new DecimalFormat("#.##").format(100 * Math.min(3f,((level*1.00/10)*1f))), Dungeon.hero.STR/2 + level, Dungeon.hero.STR/2 * level + Dungeon.hero.STR/2);
		} else {
			return "???";
		}
	}	
		

	@Override
	protected RingBuff buff() {
		return new Force();
	}
	
	public class Force extends RingBuff {
	}
}

package com.hmdzl.spspd.change.items.rings;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.messages.Messages;

import java.text.DecimalFormat;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfForce extends Ring {

	{
		//name = "Ring of Force";
		initials = 3;
	}
	
	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", new DecimalFormat("#.##").format(10f * Math.min(3f,(1f + (level*1.00/15)*1f))));
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

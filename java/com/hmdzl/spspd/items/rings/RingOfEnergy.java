package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.messages.Messages;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfEnergy extends Ring {
	// TODO: monitor this one as it goes, super hard to balance so you'll need
	// some feedback.
	{
		//name = "Ring of Energy";
		initials = 11;
		sname = "ENG";
	}
	
	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats",  Math.min(30,level) , (int)Math.min(15f,level/2));
		} else {
			return "???";
		}
	}	
	
	@Override
	protected RingBuff buff() {
		return new RingEnergy();
	}

	public class RingEnergy extends RingBuff {
	}
}

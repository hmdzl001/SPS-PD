package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

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
			return Messages.get(this, "stats",  1.25*level);
		} else {
			return "???";
		}
	}	
	
	@Override
	protected RingBuff buff() {
		return new Energy();
	}

	public class Energy extends RingBuff {
	}
}

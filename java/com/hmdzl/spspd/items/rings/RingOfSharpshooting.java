package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.messages.Messages;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfSharpshooting extends Ring {

	{
		//name = "Ring of Sharpshooting";
		initials = 8;
		sname = "RNG";
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", level, Math.min(9, 1.5 + level*0.25));
		} else {
			return "???";
		}
	}	
		
	
	@Override
	protected RingBuff buff() {
		return new RingShoot();
	}


	public class RingShoot extends RingBuff {
	}
}

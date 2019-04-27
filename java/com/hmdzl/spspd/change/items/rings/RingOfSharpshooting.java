package com.hmdzl.spspd.change.items.rings;

import com.hmdzl.spspd.change.messages.Messages;

import java.text.DecimalFormat;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfSharpshooting extends Ring {

	{
		//name = "Ring of Sharpshooting";
		initials = 8;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", level, new DecimalFormat("#.##").format(100f * (0.1*level)));
		} else {
			return "???";
		}
	}	
		
	
	@Override
	protected RingBuff buff() {
		return new Aim();
	}


	public class Aim extends RingBuff {
	}
}

package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.messages.Messages;

import java.text.DecimalFormat;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfTenacity extends Ring {

	{
		//name = "Ring of Tenacity";
		sname = "TEN";
		initials = 9;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats",level ,new DecimalFormat("#.##").format(100f * Math.min(0.40, 1.00*level/75)));
		} else {
			return "???";
		}
	}	
	
	@Override
	protected RingBuff buff() {
		return new RingTenacity();
	}

	public class RingTenacity extends RingBuff {
	}
}

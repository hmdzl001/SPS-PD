package com.hmdzl.spspd.change.items.rings;

import com.hmdzl.spspd.change.messages.Messages;

import java.text.DecimalFormat;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfTenacity extends Ring {

	{
		//name = "Ring of Tenacity";
		initials = 9;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", new DecimalFormat("#.##").format(100f * Math.min(0.40, 1.00*level/75)));
		} else {
			return "???";
		}
	}	
	
	@Override
	protected RingBuff buff() {
		return new Tenacity();
	}

	public class Tenacity extends RingBuff {
	}
}

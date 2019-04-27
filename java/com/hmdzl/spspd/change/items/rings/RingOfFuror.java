package com.hmdzl.spspd.change.items.rings;

import com.hmdzl.spspd.change.messages.Messages;

import java.text.DecimalFormat;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfFuror extends Ring {

	{
		//name = "Ring of Furor";
		initials = 4;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats",new DecimalFormat("#.##").format(100f * (Math.pow(1.05f, level) - 1f)));
		} else {
			return "???";
		}
	}	
		
	
	@Override
	protected RingBuff buff() {
		return new Furor();
	}


	public class Furor extends RingBuff {
	}
}

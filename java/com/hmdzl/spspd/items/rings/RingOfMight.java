package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.messages.Messages;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfMight extends Ring {

	{
		//name = "Ring of Might";
		initials = 7;
		sname = "STR";
	}
	
	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats",(int)(level/5),Math.min(200,level*100/15));
		} else {
			return "???";
		}
	}	
	
	
	
	@Override
	protected RingBuff buff() {
		return new RingMight();
	}

	public static int strengthBonus( Char target ){
		return getBonus( target, RingMight.class );
	}


	public class RingMight extends RingBuff {
	}
}

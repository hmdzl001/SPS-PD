package com.hmdzl.spspd.change.items.rings;

import com.hmdzl.spspd.change.Dungeon;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfForce extends Ring {

	{
		//name = "Ring of Force";
		initials = 3;
	}

	@Override
	protected RingBuff buff() {
		return new Force();
	}
	
	public class Force extends RingBuff {
	}
}

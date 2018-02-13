package com.hmdzl.spspd.change.items.rings;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfSharpshooting extends Ring {

	{
		//name = "Ring of Sharpshooting";
		initials = 8;
	}

	@Override
	protected RingBuff buff() {
		return new Aim();
	}


	public class Aim extends RingBuff {
	}
}

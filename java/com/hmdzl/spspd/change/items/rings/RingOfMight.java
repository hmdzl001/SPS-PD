package com.hmdzl.spspd.change.items.rings;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfMight extends Ring {

	{
		//name = "Ring of Might";
		initials = 7;
	}

	@Override
	protected RingBuff buff() {
		return new Might();
	}


	public class Might extends RingBuff {
	}
}

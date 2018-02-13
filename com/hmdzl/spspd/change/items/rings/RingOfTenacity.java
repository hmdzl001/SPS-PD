package com.hmdzl.spspd.change.items.rings;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfTenacity extends Ring {

	{
		//name = "Ring of Tenacity";
		initials = 9;
	}

	@Override
	protected RingBuff buff() {
		return new Tenacity();
	}

	public class Tenacity extends RingBuff {
	}
}

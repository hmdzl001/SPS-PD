package com.hmdzl.spspd.change.items.rings;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfFuror extends Ring {

	{
		//name = "Ring of Furor";
		initials = 4;
	}

	@Override
	protected RingBuff buff() {
		return new Furor();
	}


	public class Furor extends RingBuff {
	}
}

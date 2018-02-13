package com.hmdzl.spspd.change.items.rings;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfWealth extends Ring {
	// TODO: monitor this one as it goes, super hard to balance so you'll need
	// some feedback.
	{
		//name = "Ring of Wealth";
		initials = 11;
	}

	@Override
	protected RingBuff buff() {
		return new Wealth();
	}

	public class Wealth extends RingBuff {
	}
}

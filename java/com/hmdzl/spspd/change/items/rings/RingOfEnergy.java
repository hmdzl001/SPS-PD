package com.hmdzl.spspd.change.items.rings;

/**
 * Created by debenhame on 10/09/2014.
 */
public class RingOfEnergy extends Ring {
	// TODO: monitor this one as it goes, super hard to balance so you'll need
	// some feedback.
	{
		//name = "Ring of Energy";
		initials = 11;
	}

	@Override
	protected RingBuff buff() {
		return new Energy();
	}

	public class Energy extends RingBuff {
	}
}

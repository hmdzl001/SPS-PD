/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;

	public class WatchOut extends FlavourBuff {

		public static final float DURATION = 30f;
	{
		type = buffType.NEGATIVE;
	}

	@Override
	public int icon() {
		return BuffIndicator.LIGHT;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc",dispTurns());
	}

}


		//public int charID;

		//private static final String CHAR_ID = "char_id";

		//@Override
		//public void detach() {
		//	super.detach();
		//	Dungeon.observe();
		//	GameScene.updateFog();
		//}

		//@Override
		//public void restoreFromBundle(Bundle bundle) {
		//	super.restoreFromBundle(bundle);
		//	charID = bundle.getInt(CHAR_ID);
		//}

		//@Override
		//public void storeInBundle(Bundle bundle) {
		//	super.storeInBundle(bundle);
		//	bundle.put(CHAR_ID, charID);
		//}

	//}
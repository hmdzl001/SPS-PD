package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class BeCorrupt extends Buff {

		private static final float STEP = 1f;

		private int lvl;
	    private int left;

	@Override
	public boolean attachTo(Char target) {
		left = target.HP;
		return super.attachTo(target);
	}

	@Override
		public boolean act() {
			if (target.isAlive()) {
                    if(left < target.HP){
                    	lvl -= (target.HP - left);
						target.HP = Math.max(1,left-1);
						left = target.HP;
                	} else if (left > target.HP){
                    	lvl-=1;
                    	target.HP = Math.max(1,target.HP-1);
                    	left = target.HP;
					}
			}
			spend(STEP);
			if (lvl <= 0 ) {
				detach();
			}
			return true;
		}

		//public int absorb(int damage) {
			//if (level <= damage) {
				//detach();
				//return damage - level;
			//} else {
				//level -= damage;
				//return 0;
			//}
		//}

		public void level(int value) {
			if (lvl < value) {
				lvl = value;
			}
		}

		@Override
		public int icon() {
			return BuffIndicator.BE_CORRUPT;
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}
		
		@Override
		public String desc() {
			return Messages.get(this, "desc", lvl);
		}

	    private static final String LEFT = "left";
		private static final String LEVEL = "level";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(LEVEL, lvl);
			bundle.put(LEFT, left);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			lvl = bundle.getInt(LEVEL);
			left = bundle.getInt(LEFT);
		}
	}
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.effects.FloatingText2;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class EnergyArmor extends Buff {

		private static final float STEP = 1f;

		private int level;

	public String status() {
		return level > 0 ? Integer.toString( level ) : null;
	}



	@Override
		public boolean act() {
			spend(STEP);
			return true;
		}

		public int absorb(int damage) {

        if (level <= damage) {
			target.sprite.showStatusWithIcon(CharSprite.NULL_DAMAGE, "-"+ Integer.toString(level), FloatingText2.SHIELDING);
            detach();
            return damage - level;
        } else {
            level -= damage;
			target.sprite.showStatusWithIcon(CharSprite.NULL_DAMAGE,"-"+ Integer.toString(damage) ,FloatingText2.SHIELDING);
            return 0;
        }
		}

		public void level(int value) {
			if (level < value) {
				level = value;
			}
		}

		@Override
		public int icon() {
			return BuffIndicator.ALL_SHIELD;
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}
		
		@Override
		public String desc() {
			return Messages.get(this, "desc", level);
		}

		private static final String LEVEL = "level";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(LEVEL, level);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			level = bundle.getInt(LEVEL);
		}
	}
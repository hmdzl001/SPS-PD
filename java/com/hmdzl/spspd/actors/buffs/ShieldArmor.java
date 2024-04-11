package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.effects.FloatingText2;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class ShieldArmor extends Buff {

		private static final float STEP = 1f;

		private int level;


		@Override
		public boolean act() {
			spend(STEP);
			return true;
		}

		public int absorb(int damage) {

        if (level <= damage) {
			target.sprite.showStatusWithIcon(CharSprite.MELEE_DAMAGE,"-"+ Integer.toString(level), FloatingText2.SHIELDING);
            detach();
            return damage - level;
        } else {
			target.sprite.showStatusWithIcon(CharSprite.MELEE_DAMAGE, "-"+ Integer.toString(damage),FloatingText2.SHIELDING);
            level -= damage;
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
			return BuffIndicator.ARMOR;
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
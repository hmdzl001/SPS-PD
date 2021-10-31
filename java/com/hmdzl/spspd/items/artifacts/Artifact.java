package com.hmdzl.spspd.items.artifacts;

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindofMisc;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
 
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

/**
 * Created by Evan on 24/08/2014.
 */
public class Artifact extends KindofMisc {

	private static final float TIME_TO_EQUIP = 1f;

	private static final String TXT_TO_STRING = "%s";
	private static final String TXT_TO_STRING_CHARGE = "%s (%d/%d)";
	private static final String TXT_TO_STRING_LVL = "%s%+d";
	private static final String TXT_TO_STRING_LVL_CHARGE = "%s%+d (%d/%d)";

	protected Buff passiveBuff;
	protected Buff activeBuff;

	// level is used internally to track upgrades to artifacts, size/logic
	// varies per artifact.
	// already inherited from item superclass
	// exp is used to count progress towards levels for some artifacts
	protected int exp = 0;
	// levelCap is the artifact's maximum level
	protected int levelCap = 0;

	// the current artifact charge
	protected int charge = 0;
	// the build towards next charge, usually rolls over at 1.
	// better to keep charge as an int and use a separate float than casting.
	protected float partialCharge = 0;
	// the maximum charge, varies per artifact, not all artifacts use this.
	protected int chargeCap = 0;

	// used by some artifacts to keep track of duration of effects or cooldowns
	// to use.
	protected int cooldown = 0;

	public Artifact() {
		super();
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(isEquipped(hero) ? AC_UNEQUIP : AC_EQUIP);
		return actions;
	}

	@Override
	public boolean doEquip(Hero hero) {

		if (hero.belongings.misc1 != null && hero.belongings.misc2 != null  && hero.belongings.misc3 != null) {

			GLog.w(Messages.get(Artifact.class, "onlythree"));
			return false;

		} else if ((hero.belongings.misc1 != null && hero.belongings.misc1.getClass() == this.getClass())
				|| (hero.belongings.misc2 != null && hero.belongings.misc2.getClass() == this.getClass())
			    || (hero.belongings.misc3 != null && hero.belongings.misc3.getClass() == this.getClass())) {

			GLog.w( Messages.get(Artifact.class, "cannot_wear_two"));
			return false;

		} else {

			if (hero.belongings.misc1 == null) {
				hero.belongings.misc1 = this;
			} else if (hero.belongings.misc2 == null){
				hero.belongings.misc2 = this;
			} else {
				hero.belongings.misc3 = this;
			}

			detach(hero.belongings.backpack);

			activate(hero);

			cursedKnown = true;
			identify();
			if (cursed) {
				equipCursed(hero);
				GLog.n(Messages.get(Artifact.class, "cursed_worn") );
			}

			hero.spendAndNext(TIME_TO_EQUIP);
			return true;

		}

	}

	@Override
	public void activate(Char ch) {
		passiveBuff = passiveBuff();
		passiveBuff.attachTo(ch);
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {

			if (hero.belongings.misc1 == this) {
				hero.belongings.misc1 = null;
			} else if (hero.belongings.misc2 == this) {
				hero.belongings.misc2 = null;
			} else {
				hero.belongings.misc3 = null;
			}

			passiveBuff.detach();
			passiveBuff = null;

			if (activeBuff != null) {
				activeBuff.detach();
				activeBuff = null;
			}

			return true;

		} else {

			return false;

		}
	}

	@Override
	public boolean isEquipped(Hero hero) {
		return hero.belongings.misc1 == this || hero.belongings.misc2 == this || hero.belongings.misc3 == this;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public int visiblyUpgraded() {
		return ((level * 10) / levelCap);
	}

	// transfers upgrades from another artifact, transfer level will equal the
	// displayed level
	public void transferUpgrade(int transferLvl) {
		upgrade(Math.round((float) (transferLvl * levelCap) / 10));
		if (level > levelCap) {
			level = levelCap;
		}
		updateArtifact();		
	}

	@Override
	public String info() {
		if (cursed && cursedKnown && !isEquipped(Dungeon.hero)) {

			return desc() + "\n\n" + Messages.get(Artifact.class, "curse_known");

		} else {

			return desc();

		}
	}

	@Override
	public String toString() {

		if (levelKnown && level / levelCap != 0) {
			if (chargeCap > 0) {
				return Messages.format(TXT_TO_STRING_LVL_CHARGE, name(),
						visiblyUpgraded(), charge, chargeCap);
			} else {
				return Messages.format(TXT_TO_STRING_LVL, name(),
						visiblyUpgraded());
			}
		} else {
			if (chargeCap > 0) {
				return Messages.format(TXT_TO_STRING_CHARGE, name(), charge,
						chargeCap);
			} else {
				return Messages.format(TXT_TO_STRING, name());
			}
		}
	}

	@Override
	public String status() {

		// display the current cooldown
		if (cooldown != 0)
			return Messages.format("%d", cooldown);

		// display as percent
		if (chargeCap == 100)
			return Messages.format("%d%%", charge);

		// display as #/#
		if (chargeCap > 0)
			return Messages.format("%d/%d", charge, chargeCap);

		// if there's no cap -
		// - but there is charge anyway, display that charge
		if (charge != 0)
			return Messages.format("%d", charge);

		// otherwise, if there's no charge, return null.
		return null;
	}

	// converts class names to be more concise and readable.
	protected String convertName(String className) {
		// removes known redundant parts of names.
		className = className.replaceFirst("ScrollOf|PotionOf", "");

		// inserts a space infront of every uppercase character
		className = className.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");

		return className;
	}

    @Override
	public Item random() {
		if (Random.Float() < 0.3f) {
			cursed = true;
		}
		return this;
	}

	@Override
	public int price() {
		int price = 100;
		if (level > 0)
			price += 50 * ((level * 10) / levelCap);
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

	protected ArtifactBuff passiveBuff() {
		return null;
	}

	protected ArtifactBuff activeBuff() {
		return null;
	}

	public class ArtifactBuff extends Buff {

		public int level() {
			return level;
		}

		public boolean isCursed() {
			return cursed;
		}

	}

	private static final String IMAGE = "image";
	private static final String EXP = "exp";
	private static final String CHARGE = "charge";
	private static final String PARTIALCHARGE = "partialcharge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(IMAGE, image);
		bundle.put(EXP, exp);
		bundle.put(CHARGE, charge);
		bundle.put(PARTIALCHARGE, partialCharge);
		
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		image = bundle.getInt(IMAGE);
		exp = bundle.getInt(EXP);
		charge = bundle.getInt(CHARGE);
		partialCharge = bundle.getFloat(PARTIALCHARGE);
		updateArtifact();
	}
	
	public void updateArtifact() {
		// in case any artifacts have special computations between levels
		// to be overriden and used when we restore from saves or transfer upgrades
	}	
}

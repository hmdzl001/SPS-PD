package com.hmdzl.spspd.change.items.artifacts;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

/**
 * Created by debenhame on 03/09/2014.
 */
public class CapeOfThorns extends Artifact {

	{
		//name = "Cape of Thorns";
		image = ItemSpriteSheet.ARTIFACT_CAPE;

		level = 0;
		levelCap = 10;

		charge = 0;
		chargeCap = 100;
		cooldown = 0;

		defaultAction = "NONE";
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new Thorns();
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc");
		if (isEquipped( Dungeon.hero )) {
			desc += "\n\n";
			if (cooldown == 0)
				desc += Messages.get(this, "desc_inactive");
			else
				desc += Messages.get(this, "desc_active");
		}

		return desc;
	}

	public class Thorns extends ArtifactBuff {

		@Override
		public boolean act() {
			if (cooldown > 0) {
				cooldown--;
				if (cooldown == 0) {
					BuffIndicator.refreshHero();
					GLog.w(Messages.get(this, "inert") );
				}
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}

		public int proc(int damage, Char attacker) {
			if (cooldown == 0) {
				charge += damage * (0.7 + level * 0.1);
				if (charge >= chargeCap) {
					charge = 0;
					cooldown = 10 + level;
					GLog.p(Messages.get(this, "radiating"));
					BuffIndicator.refreshHero();
				}
			}

			if (cooldown != 0) {
				int deflected = Random.NormalIntRange(0, damage);
				damage -= deflected;

				attacker.damage(deflected, this);

				exp += deflected;

				if (exp >= (level + 1) * 5 && level < levelCap) {
					exp -= (level + 1) * 5;
					upgrade();
					GLog.p(Messages.get(this, "levelup") );
				}

			}
			updateQuickslot();
			return damage;
		}

		@Override
		public String toString() {
				return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", dispTurns(cooldown));
		}

		@Override
		public int icon() {
			if (cooldown == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.THORNS;
		}

		@Override
		public void detach() {
			cooldown = 0;
			charge = 0;
			super.detach();
		}

	}

}

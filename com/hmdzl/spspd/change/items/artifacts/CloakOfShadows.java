package com.hmdzl.spspd.change.items.artifacts;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;

/**
 * Created by debenhame on 25/08/2014.
 */
public class CloakOfShadows extends Artifact {

	{
		//name = "Cloak of Shadows";
		image = ItemSpriteSheet.ARTIFACT_CLOAK;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = level + 5;
		partialCharge = 0;
		chargeCap = level + 5;

		cooldown = 0;

		defaultAction = AC_STEALTH;

		bones = false;
	}

	private boolean stealthed = false;

	public static final String AC_STEALTH = "STEALTH";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge > 1)
			actions.add(AC_STEALTH);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_STEALTH)) {

			if (!stealthed) {
				if (!isEquipped(hero))
					GLog.i(Messages.get(Artifact.class, "need_to_equip"));
				else if (cooldown > 0)
					GLog.i(Messages.get(this, "cooldown", cooldown) );
				else if (charge <= 1)
					GLog.i( Messages.get(this, "no_charge") );
				else {
					stealthed = true;
					hero.spend(1f);
					hero.busy();
					Sample.INSTANCE.play(Assets.SND_MELD);
					activeBuff = activeBuff();
					activeBuff.attachTo(hero);
					if (hero.sprite.parent != null) {
						hero.sprite.parent.add(new AlphaTweener(hero.sprite,
								0.4f, 0.4f));
					} else {
						hero.sprite.alpha(0.4f);
					}
					hero.sprite.operate(hero.pos);
				}
			} else {
				stealthed = false;
				activeBuff.detach();
				activeBuff = null;
				hero.sprite.operate(hero.pos);
			}

		} else
			super.execute(hero, action);
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		if (stealthed) {
			activeBuff = activeBuff();
			activeBuff.attachTo(ch);
		}
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {
			stealthed = false;
			return true;
		} else
			return false;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new cloakRecharge();
	}

	@Override
	protected ArtifactBuff activeBuff() {
		return new cloakStealth();
	}

	@Override
	public Item upgrade() {
		chargeCap++;
		return super.upgrade();
	}

	private static final String STEALTHED = "stealthed";
	private static final String COOLDOWN = "cooldown";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(STEALTHED, stealthed);
		bundle.put(COOLDOWN, cooldown);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		stealthed = bundle.getBoolean(STEALTHED);
		cooldown = bundle.getInt(COOLDOWN);
	}

	public class cloakRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap) {
				if (!stealthed)
					partialCharge += (1f / (50 - (chargeCap - charge)));

				if (partialCharge >= 1) {
					charge++;
					partialCharge -= 1;
					if (charge == chargeCap) {
						partialCharge = 0;
					}

				}
			} else
				partialCharge = 0;

			if (cooldown > 0)
				cooldown--;

			updateQuickslot();

			spend(TICK);

			return true;
		}

	}

	@Override
	public void updateArtifact() {
		chargeCap = level + 5;
	}

	public class cloakStealth extends ArtifactBuff {
		int turnsToCost = 0;
		@Override
		public int icon() {
			return BuffIndicator.INVISIBLE;
		}

		@Override
		public boolean attachTo(Char target) {
			if (super.attachTo(target)) {
				target.invisible++;
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean act() {
			if (turnsToCost == 0) charge--;
			if (charge <= 0) {
				detach();
				GLog.w(Messages.get(this, "no_charge"));
				((Hero) target).interrupt();
			}

			if (turnsToCost == 0) exp += 10 + ((Hero) target).lvl;

			if (exp >= (level + 1) * 50 && level < levelCap) {
				upgrade();
				exp -= level * 50;
				GLog.p(Messages.get(this, "levelup") );
			}
			
			if (turnsToCost == 0) turnsToCost = 2;
			else    turnsToCost--;

			updateQuickslot();

			spend(TICK);

			return true;
		}
		
		public void dispel(){
			charge --;

			exp += 10 + ((Hero)target).lvl;

			if (exp >= (level()+1)*50 && level() < levelCap) {
				upgrade();
				exp -= level()*50;
				GLog.p( Messages.get(this, "levelup") );
			}

			updateQuickslot();
			detach();
		}	

		/*@Override
		public void fx(boolean on) {
			if (on) target.sprite.add( CharSprite.State.INVISIBLE );
			else if (target.invisible == 0) target.sprite.remove( CharSprite.State.INVISIBLE );
		}*/

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc");
		}

		@Override
		public void detach() {
			if (target.invisible > 0)
				target.invisible--;
			stealthed = false;
			cooldown = 6 - (level / 4);

			updateQuickslot();
			super.detach();
		}
	}
}

package com.hmdzl.spspd.change.items.artifacts;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndOptions;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

/**
 * Created by debenhame on 01/12/2014.
 */
public class TimekeepersHourglass extends Artifact {

	private static final String TXT_HGLASS = "Timekeeper's Hourglass";
	private static final String TXT_STASIS = "Put myself in stasis";
	private static final String TXT_FREEZE = "Freeze time around me";
	private static final String TXT_DESC = "How would you like to use the hourglass's magic?\n\n"
			+ "While in stasis, time will move normally while you are frozen and completely invulnerable.\n\n"
			+ "When time is frozen, you can move as if your actions take no time. Note that attacking will break this.";

	{
		//name = "Timekeeper's Hourglass";
		image = ItemSpriteSheet.ARTIFACT_HOURGLASS;

		level = 0;
		levelCap = 5;

		charge = 10 + level * 2;
		partialCharge = 0;
		chargeCap = 10 + level * 2;

		defaultAction = AC_ACTIVATE;
	}

	public static final String AC_ACTIVATE = "ACTIVATE";

	// keeps track of generated sandbags.
	public int sandBags = 0;

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge > 0 && !cursed)
			actions.add(AC_ACTIVATE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_ACTIVATE)) {

			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip"));
			else if (activeBuff != null)
				GLog.i(Messages.get(this, "in_use"));
			else if (charge <= 1)
				GLog.i(Messages.get(this, "no_charge") );
			else if (cursed)
				GLog.i(Messages.get(this, "cursed"));
			else
				GameScene.show(new WndOptions(Messages.get(this, "name"),
								Messages.get(this, "prompt"),
								Messages.get(this, "stasis"),
								Messages.get(this, "freeze")) {
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							GLog.i(Messages.get(TimekeepersHourglass.class, "onstasis"));
							GameScene.flash(0xFFFFFF);
							Sample.INSTANCE.play(Assets.SND_TELEPORT);

							activeBuff = new timeStasis();
							activeBuff.attachTo(Dungeon.hero);
						} else if (index == 1) {
							GLog.i(Messages.get(TimekeepersHourglass.class, "onfreeze"));
							GameScene.flash(0xFFFFFF);
							Sample.INSTANCE.play(Assets.SND_TELEPORT);

							activeBuff = new timeFreeze();
							activeBuff.attachTo(Dungeon.hero);
						}
					};
				});
		} else
			super.execute(hero, action);
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		if (activeBuff != null)
			activeBuff.attachTo(ch);
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {
			if (activeBuff != null) {
				activeBuff.detach();
				activeBuff = null;
			}
			return true;
		} else
			return false;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new hourglassRecharge();
	}

	@Override
	public Item upgrade() {
		chargeCap += 2;

		// for artifact transmutation.
		while (level + 1 > sandBags)
			sandBags++;

		return super.upgrade();
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if (isEquipped( Dungeon.hero )){
			if (!cursed) {
				if (level < levelCap )
					desc += "\n\n" + Messages.get(this, "desc_hint");

			} else
				desc += "\n\n" + Messages.get(this, "desc_cursed");
		}
		return desc;
	}

	@Override
	public void updateArtifact() {
		chargeCap = 10 + level*2;
	}

	private static final String SANDBAGS = "sandbags";
	private static final String BUFF = "buff";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SANDBAGS, sandBags);

		if (activeBuff != null)
			bundle.put(BUFF, activeBuff);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		sandBags = bundle.getInt(SANDBAGS);

		// these buffs belong to hourglass, need to handle unbundling within the
		// hourglass class.
		if (bundle.contains(BUFF)) {
			Bundle buffBundle = bundle.getBundle(BUFF);

			if (buffBundle.contains(timeFreeze.PARTIALTIME))
				activeBuff = new timeFreeze();
			else
				activeBuff = new timeStasis();

			activeBuff.restoreFromBundle(buffBundle);
		}
	}

	public class hourglassRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap && !cursed) {
				partialCharge += 1 / (60f - (chargeCap - charge) * 2f);

				if (partialCharge >= 1) {
					partialCharge--;
					charge++;

					if (charge == chargeCap) {
						partialCharge = 0;
					}
				}
			} else if (cursed && Random.Int(10) == 0)
				((Hero) target).spend(TICK);

			updateQuickslot();

			spend(TICK);

			return true;
		}
	}

	public class timeStasis extends ArtifactBuff {

		@Override
		public boolean attachTo(Char target) {
			spend(charge);
			((Hero) target).spendAndNext(charge);

			// shouldn't punish the player for going into stasis frequently
			Hunger hunger = target.buff(Hunger.class);
			if (hunger != null && !hunger.isStarving())
				hunger.satisfy(charge);

			charge = 0;

			target.invisible++;

			updateQuickslot();

			Dungeon.observe();

			return super.attachTo(target);
		}

		@Override
		public boolean act() {
			detach();
			return true;
		}

		@Override
		public void detach() {
			if (target.invisible > 0)
				target.invisible--;
			super.detach();
			activeBuff = null;
			Dungeon.observe();
		}
	}

	public class timeFreeze extends ArtifactBuff {

		float partialTime = 0f;

		ArrayList<Integer> presses = new ArrayList<Integer>();

		public boolean processTime(float time) {
			partialTime += time;

			while (partialTime >= 1f) {
				partialTime--;
				charge--;
			}

			updateQuickslot();

			if (charge <= 0) {
				detach();
				return false;
			} else
				return true;

		}

		public void setDelayedPress(int cell) {
			if (!presses.contains(cell))
				presses.add(cell);
		}

		public void triggerPresses() {
			for (int cell : presses)
				Dungeon.level.press(cell, null);

			presses = new ArrayList<Integer>();
		}

		@Override
		public boolean attachTo(Char target) {
			if (Dungeon.level != null)
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					mob.sprite.add(CharSprite.State.PARALYSED);
			Group.freezeEmitters = true;
			return super.attachTo(target);
		}

		@Override
		public void detach() {
			triggerPresses();
			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
				mob.sprite.remove(CharSprite.State.PARALYSED);
			Group.freezeEmitters = false;

			charge = 0;
			updateQuickslot();
			super.detach();
			activeBuff = null;
		}

		private static final String PRESSES = "presses";
		private static final String PARTIALTIME = "partialtime";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);

			int[] values = new int[presses.size()];
			for (int i = 0; i < values.length; i++)
				values[i] = presses.get(i);
			bundle.put(PRESSES, values);

			bundle.put(PARTIALTIME, partialTime);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);

			int[] values = bundle.getIntArray(PRESSES);
			for (int value : values)
				presses.add(value);

			partialTime = bundle.getFloat(PARTIALTIME);
		}
	}

	public static class sandBag extends Item {

		{
			//name = "bag of magic sand";
			image = ItemSpriteSheet.SANDBAG;
		}

		@Override
		public boolean doPickUp(Hero hero) {
			TimekeepersHourglass hourglass = hero.belongings
					.getItem(TimekeepersHourglass.class);
			if (hourglass != null && !hourglass.cursed) {
				hourglass.upgrade();
				Sample.INSTANCE.play(Assets.SND_DEWDROP);
				if (hourglass.level == hourglass.levelCap)
					GLog.p(Messages.get(this, "maxlevel"));
				else
					GLog.i(Messages.get(this, "levelup"));
				hero.spendAndNext(TIME_TO_PICK_UP);
				return true;
			} else {
				GLog.w( Messages.get(this, "no_hourglass"));
				return false;
			}
		}

		@Override
		public int price() {
			return 10;
		}
	}

}

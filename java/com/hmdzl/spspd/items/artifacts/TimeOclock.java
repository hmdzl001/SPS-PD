package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.keys.Key;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by debenhame on 01/12/2014.
 */
public class TimeOclock extends Artifact {

	private static final String TXT_HGLASS = "Timekeeper's Hourglass";
	private static final String TXT_STASIS = "Put myself in stasis";
	private static final String TXT_FREEZE = "Freeze time around me";
	private static final String TXT_DESC = "How would you like to use the hourglass's magic?\n\n"
			+ "While in stasis, time will move normally while you are frozen and completely invulnerable.\n\n"
			+ "When time is frozen, you can move as if your actions take no time. Note that attacking will break this.";

	{
		//name = "Timekeeper's Hourglass";
		image = ItemSpriteSheet.ARTIFACT_TIME_OCLOCK;

		level = 0;
		levelCap = 5;

		charge = 5 + level;
		partialCharge = 0;
		chargeCap = 5 + level;

		defaultAction = AC_ACTIVATE;
	}

	public static final String AC_ACTIVATE = "ACTIVATE";
	public static final String AC_RESTART = "RESTART";

	// keeps track of generated sandbags.
	public int sandBags = 0;

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge > 0 && !cursed)
			actions.add(AC_ACTIVATE);
		if (!isEquipped(hero) && level > 4 && !cursed)
			actions.add(AC_RESTART);		
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

							activeBuff = new timeStasis2();
							activeBuff.attachTo(Dungeon.hero);
						} else if (index == 1) {
							GLog.i(Messages.get(TimekeepersHourglass.class, "onfreeze"));
							GameScene.flash(0xFFFFFF);
							Sample.INSTANCE.play(Assets.SND_TELEPORT);
                            charge --;
							Buff.affect(Dungeon.hero, HasteBuff.class,15f);
							Dungeon.hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.4f, 4);
							for (Mob mob : Dungeon.depth.mobs) {
								Buff.prolong(mob, Paralysis.class, 3f);
								Buff.prolong(mob, Slow.class, 15f);
								Buff.affect(mob, ArmorBreak.class,15f).level(30);
								mob.sprite.centerEmitter().start(Speck.factory(Speck.NOTE),	0.3f, 5);
							}
							Dungeon.hero.spendAndNext(1f);
						}
					}
                });
		} else  if (action.equals(AC_RESTART)){
			level=0;
			curUser = hero;
			InterlevelScene.returnDepth = Dungeon.dungeondepth;
				for (Item item : Dungeon.hero.belongings.backpack.items.toArray( new Item[0])){
					if (item instanceof Key && ((Key)item).depth == Dungeon.dungeondepth){
						item.detachAll(Dungeon.hero.belongings.backpack);
					}
				}
				InterlevelScene.mode = InterlevelScene.Mode.RESET;
				Game.switchScene(InterlevelScene.class);
			
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
		return new oclockRecharge();
	}

	@Override
	public Item upgrade() {
		chargeCap += 1;

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
		chargeCap = 5 + level;
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

			activeBuff = new timeStasis2();

			activeBuff.restoreFromBundle(buffBundle);
		}
	}

	public class oclockRecharge extends ArtifactBuff {
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

	public class timeStasis2 extends ArtifactBuff {

		@Override
		public boolean attachTo(Char target) {
			spend(4f);
			((Hero) target).spendAndNext(4f);

			// shouldn't punish the player for going into stasis frequently
			Hunger hunger = target.buff(Hunger.class);
			if (hunger != null && !hunger.isStarving())
				hunger.satisfy(4f);

			charge --;

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

	public static class clock extends Item {

		{
			//name = "bag of magic sand";
			image = ItemSpriteSheet.SANDBAG;
		}

		@Override
		public boolean doPickUp(Hero hero) {
			TimeOclock oclock = hero.belongings
					.getItem(TimeOclock.class);
			if (oclock != null && !oclock.cursed && oclock.level< oclock.levelCap) {
				oclock.upgrade();
				Sample.INSTANCE.play(Assets.SND_DEWDROP);
				if (oclock.level == oclock.levelCap)
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

package com.hmdzl.spspd.items.artifacts;

import java.util.ArrayList;
import java.util.Collections;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.Water;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.EarthParticle;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Earthroot;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

/**
 * Created by debenhame on 08/09/2014.
 */
public class SandalsOfNature extends Artifact {

	{
		//name = "Sandals of Nature";
		image = ItemSpriteSheet.ARTIFACT_SANDALS;

		level = 0;
		levelCap = 3;

		charge = 0;

		defaultAction = AC_ROOT;
	}

	public static final String AC_FEED = "FEED";
	public static final String AC_ROOT = "ROOT";
	public static final String AC_SPROUT = "SPROUT";

	//protected String inventoryTitle = "Select a seed";
	protected WndBag.Mode mode = WndBag.Mode.SEED;

	public ArrayList<String> seeds = new ArrayList<String>();

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && level < 3 && !cursed)
			actions.add(AC_FEED);
		if (isEquipped(hero) && charge > 0)
			actions.add(AC_ROOT);
		if (level > 0 && !isEquipped(hero) )
			actions.add(AC_SPROUT);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_FEED)) {
			GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));
		} else if (action.equals(AC_ROOT) && level > 0) {

			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip"));
			else if (charge == 0)
				GLog.i(Messages.get(this, "no_charge"));
			else {
				Buff.prolong(hero, Roots.class, 5);
				Buff.affect(hero, Earthroot.Armor.class).level(charge);
				CellEmitter.bottom(hero.pos).start(EarthParticle.FACTORY,
						0.05f, 8);
				Camera.main.shake(1, 0.4f);
				charge = 0;
				updateQuickslot();
			}
		} else if (action.equals(AC_SPROUT)) {
			curUser = hero;
			int length = Level.getLength();

			for (int i = 0; i < length; i++) {

				GameScene.add(Blob.seed(i, (2) * 20 * level, Water.class));

			}
			detach(curUser.belongings.backpack);
			Sample.INSTANCE.play(Assets.SND_BURNING);
			curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
            curUser.spendAndNext(2f);
		}
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new Naturalism();
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc_" + (level+1));
		
		if (isEquipped(Dungeon.hero)) {
			desc += "\n\n";
			if (!cursed)
				desc += Messages.get(this, "desc_hint");
			else
				desc += Messages.get(this, "desc_cursed");

			if (level > 0)
				desc += "\n\n" + Messages.get(this, "desc_ability");
		}

		if (!seeds.isEmpty()) {
           desc += "\n\n" + Messages.get(this, "desc_seeds", seeds.size());
		}

		return desc;
	}

	@Override
	public Item upgrade() {
		if (level < 0)
			image = ItemSpriteSheet.ARTIFACT_SANDALS;
		else if (level == 0)
			image = ItemSpriteSheet.ARTIFACT_SHOES;
		else if (level == 1)
			image = ItemSpriteSheet.ARTIFACT_BOOTS;
		else if (level >= 2)
			image = ItemSpriteSheet.ARTIFACT_GREAVES;
		name = Messages.get(this, "name_" + (level+1));
		return super.upgrade();
	}

	private static final String SEEDS = "seeds";
	//private static final String NAME = "name";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		//bundle.put(NAME, name);
		bundle.put(SEEDS, seeds.toArray(new String[seeds.size()]));
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (level > 0) name = Messages.get(this, "name_" + level);
		if (bundle.contains(SEEDS))
			Collections.addAll(seeds, bundle.getStringArray(SEEDS));
	}

	public class Naturalism extends ArtifactBuff {
		public void charge() {
			if (charge < target.HT) {
				// gain 1+(1*level)% of the difference between current charge
				// and max HP.
				charge += (Math.round((target.HT - charge)
						* (.01 + level * 0.01)));
				updateQuickslot();
			}
		}
	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Plant.Seed) {
				if (seeds.contains(item.name())) {
					GLog.w(Messages.get(SandalsOfNature.class, "already_fed"));
				} else {
					seeds.add(item.name());

					Hero hero = Dungeon.hero;
					hero.sprite.operate(hero.pos);
					Sample.INSTANCE.play(Assets.SND_PLANT);
					hero.busy();
					hero.spend(2f);
					if (seeds.size() >= 3 + (level * 3)) {
						seeds.clear();
						upgrade();
						if (level >= 1 && level <= 3) {
							GLog.p(Messages.get(SandalsOfNature.class, "levelup"));
						}

					} else {
						GLog.i(Messages.get(SandalsOfNature.class, "absorb_seed"));
					}
					item.detach(hero.belongings.backpack);
				}
			}
		}
	};

}

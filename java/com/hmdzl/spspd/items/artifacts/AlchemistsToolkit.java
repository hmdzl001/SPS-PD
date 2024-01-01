package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.potions.PotionOfExperience;
import com.hmdzl.spspd.items.potions.PotionOfMight;
import com.hmdzl.spspd.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.items.potions.PotionOfStrength;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndAlchemy;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by debenhame on 24/11/2014.
 */
public class AlchemistsToolkit extends Artifact {

	{
		//name = "Alchemists Toolkit";
		image = ItemSpriteSheet.ARTIFACT_TOOLKIT;

		level = 0;
		levelCap = 10;
		defaultAction = AC_BREW;
	}

	public static final String AC_BREW = "BREW";
	public static final String AC_CREATE = "CREATE";
	public static final String AC_COOKING = "COOKING";

	// arrays used in containing potion collections for mix logic.
	public final ArrayList<Class> combination = new ArrayList<>();
	public ArrayList<Class> curGuess = new ArrayList<>();
	public ArrayList<Class> bstGuess = new ArrayList<>();

	public int numWrongPlace = 0;
	public int numRight = 0;

	private int seedsToPotion = 0;

	//protected String inventoryTitle = "Select a potion";
	protected WndBag.Mode mode = WndBag.Mode.POTION;

	public AlchemistsToolkit() {
		super();

		Generator.Category cat = Generator.Category.POTION;
		for (int i = 1; i <= 3; i++) {
			Class potion;
			do {
				potion = cat.classes[Random.chances(cat.probs)];
				// forcing the player to use experience potions would be
				// completely unfair.
			} while (combination.contains(potion)
					|| potion == PotionOfExperience.class || potion == PotionOfOverHealing.class
                   || potion == PotionOfStrength.class || potion == PotionOfMight.class
					);
			combination.add(potion);
		}
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		   actions.add(AC_COOKING);
		if (isEquipped(hero) && level < levelCap && !cursed)
			actions.add(AC_BREW);
		//if (level > 0 && !isEquipped(hero) )
		actions.add(AC_CREATE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_COOKING)) {
			GameScene.show(new WndAlchemy());
		} else if (action.equals(AC_BREW)) {
			GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));
		} else if (action.equals(AC_CREATE)) {
			curUser = hero;
			Sample.INSTANCE.play(Assets.SND_BURNING);
			curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			curUser.spendAndNext(1f);
			for(int i=0; i<level; i++) {
            Dungeon.depth.drop(Generator.random(), hero.pos).sprite.drop();
			}
			detach(curUser.belongings.backpack);
			} else {
			super.execute(hero, action);
		}
	}

	public void guessBrew() {
		if (curGuess.size() != 3)
			return;

		int numWrongPlace = 0;
		int numRight = 0;

		for (Class potion : curGuess) {
			if (combination.contains(potion)) {
				if (curGuess.indexOf(potion) == combination.indexOf(potion)) {
					numRight++;
				} else {
					numWrongPlace++;
				}
			}
		}

		int score = (numRight * 3) + numWrongPlace;

		if (score == 9)
			score++;

		if (score == 0) {

			GLog.i(Messages.get(this,"waste"));

		} else if (score > level) {

			level = score;
			seedsToPotion = 0;
			bstGuess = curGuess;
			this.numRight = numRight;
			this.numWrongPlace = numWrongPlace;

			if (level == 10) {
				bstGuess = new ArrayList<>();
				GLog.p(Messages.get(this,"prefect"));
			} else {
				GLog.w(brewDesc(numWrongPlace, numRight)
						+ Messages.get(this, "bestbrew"));
			}

		} else {

			GLog.w(brewDesc(numWrongPlace, numRight)
					+ Messages.get(this, "waste"));
		}
		curGuess = new ArrayList<>();

	}

	private String brewDesc(int numWrongPlace, int numRight) {
		String result = "";
		if (numWrongPlace > 0) {
			result += numWrongPlace + Messages.get(this, "bdorder");
		} if (numRight > 0) {
			result += numRight + Messages.get(this, "right");
		}
		return result;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new alchemy();
	}

	@Override
	public String desc() {
		String result = Messages.get(this, "desc");
		if (isEquipped(Dungeon.hero))
			if (cursed)
				result += "\n\n" + Messages.get(this, "desc_cursed");
		if (level == 0) {
			result +=  "\n\n" + Messages.get(this, "level_zero");
		} else if (level == 10) {
			result +=  "\n\n" + Messages.get(this, "level_ten");
		} else if (!bstGuess.isEmpty()) {
			result += "\n\n" + Messages.get(this, "make_from")
					+ Messages.get(bstGuess.get(0), "name") + ", " + Messages.get(bstGuess.get(1), "name") + ", "
					+ Messages.get(bstGuess.get(2), "name") + ", " 
					+ brewDesc(numWrongPlace, numRight);

			// would only trigger if an upgraded toolkit was gained through
			// transmutation or bones.
		} else {
			result += Messages.get(this, "need_fix");
        }
		return result;
	}

	private static final String COMBINATION = "combination";
	private static final String CURGUESS = "curguess";
	private static final String BSTGUESS = "bstguess";

	private static final String NUMWRONGPLACE = "numwrongplace";
	private static final String NUMRIGHT = "numright";

	private static final String SEEDSTOPOTION = "seedstopotion";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(NUMWRONGPLACE, numWrongPlace);
		bundle.put(NUMRIGHT, numRight);

		bundle.put(SEEDSTOPOTION, seedsToPotion);

		bundle.put(COMBINATION,
				combination.toArray(new Class[combination.size()]));
		bundle.put(CURGUESS, curGuess.toArray(new Class[curGuess.size()]));
		bundle.put(BSTGUESS, bstGuess.toArray(new Class[bstGuess.size()]));
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		numWrongPlace = bundle.getInt(NUMWRONGPLACE);
		numRight = bundle.getInt(NUMRIGHT);

		seedsToPotion = bundle.getInt(SEEDSTOPOTION);

		combination.clear();
		Collections.addAll(combination, bundle.getClassArray(COMBINATION));
		Collections.addAll(curGuess, bundle.getClassArray(CURGUESS));
		Collections.addAll(bstGuess, bundle.getClassArray(BSTGUESS));
	}

	public class alchemy extends ArtifactBuff {

		public boolean tryCook(int count) {

			// this logic is handled inside the class with a variable so that it
			// may be stored.
			// to prevent manipulation where a player could keep throwing in 1-2
			// seeds until they get lucky.
			if (seedsToPotion == 0) {
				if (Random.Int(20) < 10 + level) {
					if (Random.Int(20) < level) {
						seedsToPotion = 1;
					} else
						seedsToPotion = 2;
				} else
					seedsToPotion = 3;
			}

			if (count >= seedsToPotion) {
				seedsToPotion = 0;
				return true;
			} else
				return false;

		}

	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Potion && item.isIdentified()) {
				if (!curGuess.contains(item.getClass())) {

					Hero hero = Dungeon.hero;
					hero.sprite.operate(hero.pos);
					hero.busy();
					hero.spend(1f);
					Sample.INSTANCE.play(Assets.SND_DRINK);

					//item.detach(hero.belongings.backpack);

					curGuess.add(item.getClass());
					if (curGuess.size() == 3) {
						guessBrew();
					} else {
						GLog.i(Messages.get(AlchemistsToolkit.class, "addpotion"));
					}
				} else {
					GLog.w(Messages.get(AlchemistsToolkit.class, "have_add"));
				}
			} else if (item != null) {
				GLog.w(Messages.get(AlchemistsToolkit.class, "know_first"));
			}
		}
	};

}


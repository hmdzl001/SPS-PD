package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Feed;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.SpellSprite;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

/**
 * Created by debenhame on 26/08/2014.
 */
public class HornOfPlenty extends Artifact {

	{
		//name = "Horn of Plenty";
		image = ItemSpriteSheet.ARTIFACT_HORN1;

		level = 0;
		levelCap = 30;

		charge = 0;
		partialCharge = 0;
		chargeCap = 10;

		defaultAction = AC_EAT;
	}

	private static final float TIME_TO_EAT = 3f;

	private float energy = 40f;

	public static final String AC_EAT = "EAT";
	public static final String AC_STORE = "STORE";
	public static final String AC_FEED = "FEED";

	protected String inventoryTitle = "Select a piece of food";
	protected WndBag.Mode mode = WndBag.Mode.FOOD;

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge > 0)
			actions.add(AC_EAT);
		if (isEquipped(hero) && level < 30 && !cursed)
			actions.add(AC_STORE);
	    if (isEquipped(hero) && level > 0 && !cursed)
			actions.add(AC_FEED);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

			if (!isEquipped(hero))
				GLog.i( Messages.get(Artifact.class, "need_to_equip"));
			else if (charge == 0)
				GLog.i( Messages.get(this, "no_food") );
			else {
				hero.buff(Hunger.class).satisfy(energy * charge);

				// if you get at least 100 food energy from the horn
				if (charge >= 3) {
					/*switch (hero.heroClass) {
					case WARRIOR:
						if (hero.HP < hero.TRUE_HT) {
							hero.HP = Math.min(hero.HP + 5, hero.TRUE_HT);
							hero.sprite.emitter().burst(
									Speck.factory(Speck.HEALING), 1);
						}
						break;
					case MAGE:
						Buff.affect( hero, Recharging.class, 4f );
						ScrollOfRecharging.charge(hero);
						break;
					case ROGUE:
					case HUNTRESS:
					case PERFORMER:
					case SOLDIER:
					case FOLLOWER:
						break;
					}*/

					Statistics.foodEaten++;
				}
				charge = 0;

				hero.sprite.operate(hero.pos);
				hero.busy();
				SpellSprite.show(hero, SpellSprite.FOOD);
				Sample.INSTANCE.play(Assets.SND_EAT);
				GLog.i( Messages.get(this, "eat"));

				hero.spend(TIME_TO_EAT);

				Badges.validateFoodEaten();

				image = ItemSpriteSheet.ARTIFACT_HORN1;

				updateQuickslot();
			}

		} else if (action.equals(AC_STORE)) {

			GameScene.selectItem(itemSelector, mode,Messages.get(this, "prompt"));
		} else if (action.equals(AC_FEED)) {
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else {	
				Buff.affect(hero, Feed.class,level*3f);
				hero.spend(1f);
				hero.busy();
				hero.sprite.operate(hero.pos);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				level = 0;
				updateQuickslot();	
			}
		}
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new hornRecharge();
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if ( isEquipped( Dungeon.hero ) ){
			if (!cursed) {
				if (level < levelCap)
					desc += "\n\n" +Messages.get(this, "desc_hint");
			} else {
				desc += "\n\n" +Messages.get(this, "desc_cursed");
			}
		}

		return desc;
	}

	public class hornRecharge extends ArtifactBuff {

		@Override
		public boolean act() {
			if (charge < chargeCap && !cursed) {

				// generates 0.25 food value every round, +0.015 value per level
				// to a max of 0.70 food value per round (0.25+0.5, at level 30)
				partialCharge += 0.25f + (0.015f * level);

				// charge is in increments of 36 food value.
				if (partialCharge >= 80) {
					charge++;
					partialCharge -= 80;

					if (charge == chargeCap)
						image = ItemSpriteSheet.ARTIFACT_HORN4;
					else if (charge >= 7)
						image = ItemSpriteSheet.ARTIFACT_HORN3;
					else if (charge >= 3)
						image = ItemSpriteSheet.ARTIFACT_HORN2;
					else
						image = ItemSpriteSheet.ARTIFACT_HORN1;

					if (charge == chargeCap) {
						GLog.p(Messages.get(HornOfPlenty.class, "full"));
						partialCharge = 0;
					}

					updateQuickslot();
				}
			} else
				partialCharge = 0;

			spend(TICK);

			return true;
		}

	}

	protected static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Food) {
			
					Hero hero = Dungeon.hero;
					hero.sprite.operate(hero.pos);
					hero.busy();
					hero.spend(TIME_TO_EAT);

					curItem.upgrade(((Food) item).hornValue);
					if (curItem.level >= 30) {
						curItem.level = 30;
						GLog.p( Messages.get(HornOfPlenty.class, "maxlevel") );
					} else
						GLog.p(Messages.get(HornOfPlenty.class, "levelup"));
					item.detach(hero.belongings.backpack);
				}

			
		}
	};

}

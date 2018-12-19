package com.hmdzl.spspd.change.items.food.fruit;


import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.buffs.EarthImbue;
import com.hmdzl.spspd.change.actors.buffs.FireImbue;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.buffs.ToxicImbue;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.SpellSprite;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.items.food.meatfood.FrozenCarpaccio;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.potions.PotionOfExperience;
import com.hmdzl.spspd.change.items.potions.PotionOfFrost;
import com.hmdzl.spspd.change.items.potions.PotionOfHealing;
import com.hmdzl.spspd.change.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.change.items.potions.PotionOfLevitation;
import com.hmdzl.spspd.change.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.change.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.change.items.potions.PotionOfParalyticGas;
import com.hmdzl.spspd.change.items.potions.PotionOfPurity;
import com.hmdzl.spspd.change.items.potions.PotionOfStrength;
import com.hmdzl.spspd.change.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.change.plants.Blindweed;
import com.hmdzl.spspd.change.plants.Dreamfoil;
import com.hmdzl.spspd.change.plants.Earthroot;
import com.hmdzl.spspd.change.plants.Fadeleaf;
import com.hmdzl.spspd.change.plants.Firebloom;
import com.hmdzl.spspd.change.plants.Flytrap;
import com.hmdzl.spspd.change.plants.Icecap;
import com.hmdzl.spspd.change.plants.Phaseshift;
import com.hmdzl.spspd.change.plants.Plant.Seed;
import com.hmdzl.spspd.change.plants.Sorrowmoss;
import com.hmdzl.spspd.change.plants.Stormvine;
import com.hmdzl.spspd.change.plants.Sungrass;
import com.hmdzl.spspd.change.plants.Rotberry;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

/**
 * Created by debenhame on 12/08/2014.
 */
public class Blandfruit extends Fruit {

	public String info = Messages.get(this,"desc");

	public Potion potionAttrib = null;
	public ItemSprite.Glowing potionGlow = null;

	{
		//name = "Blandfruit";
		stackable = true;
		image = ItemSpriteSheet.BLANDFRUIT;
		energy = 50;
		hornValue = 6; // only applies when blandfruit is cooked

		bones = true;
	}

	@Override
	public boolean isSimilar(Item item) {
		if (item instanceof Blandfruit) {
			if (potionAttrib == null) {
				if (((Blandfruit) item).potionAttrib == null)
					return true;
			} else if (((Blandfruit) item).potionAttrib != null) {
				if (((Blandfruit) item).potionAttrib.getClass() == potionAttrib
						.getClass())
					return true;
			}
		}
		return false;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_EAT)) {

			if (potionAttrib == null) {

                GLog.w( Messages.get(this, "raw"));
				
			} else {

				hero.buff(Hunger.class).satisfy(150);

				detach(hero.belongings.backpack);

				hero.spend(1f);
				hero.busy();

				if (potionAttrib instanceof PotionOfFrost) {
					GLog.i(Messages.get(this, "ice_msg"));
                    FrozenCarpaccio.effect(hero);
				} else if (potionAttrib instanceof PotionOfLiquidFlame) {
					GLog.i(Messages.get(this, "fire_msg"));
					Buff.affect(hero, FireImbue.class).set(FireImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfToxicGas) {
					GLog.i(Messages.get(this, "toxic_msg"));
					Buff.affect(hero, ToxicImbue.class)
							.set(ToxicImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfParalyticGas) {
					GLog.i(Messages.get(this, "para_msg"));
					Buff.affect(hero, EarthImbue.class, EarthImbue.DURATION);
				} else
					potionAttrib.apply(hero);

				Sample.INSTANCE.play(Assets.SND_EAT);
				SpellSprite.show(hero, SpellSprite.FOOD);
				hero.sprite.operate(hero.pos);

				switch (hero.heroClass) {
				case WARRIOR:
					if (hero.HP < hero.HT) {
						hero.HP = Math.min(hero.HP + 5, hero.HT);
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
					break;
				}
			}
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public String info() {
		return info;
	}

	public Item cook(Seed seed) {

		try {
			return imbuePotion((Potion) seed.alchemyClass.newInstance());
		} catch (Exception e) {
			return null;
		}

	}

	public Item imbuePotion(Potion potion) {

		potionAttrib = potion;
		potionAttrib.ownedByFruit = true;

		potionAttrib.image = ItemSpriteSheet.BLANDFRUIT;

		info = Messages.get(this,"desc_cooked");

		if (potionAttrib instanceof PotionOfHealing) {

			name =  "Sunfruit";
			potionGlow = new ItemSprite.Glowing(0x2EE62E);
			//info += "It looks delicious and hearty, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfStrength) {

			name = "Powerfruit";
			potionGlow = new ItemSprite.Glowing(0xCC0022);
			//info += "It looks delicious and powerful, ready to be eaten!";
			
		} else if (potionAttrib instanceof PotionOfMight) {

			name = "Mightyfruit";
			potionGlow = new ItemSprite.Glowing(0xFF3300);
			//info += "It looks delicious and super powerful, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfParalyticGas) {

			name = "Earthfruit";
			potionGlow = new ItemSprite.Glowing(0x67583D);
			//info += "It looks delicious and firm, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfInvisibility) {

			name = "Blindfruit";
			potionGlow = new ItemSprite.Glowing(0xE5D273);
			//info += "It looks delicious and shiny, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfLiquidFlame) {

			name = "Firefruit";
			potionGlow = new ItemSprite.Glowing(0xFF7F00);
			//info += "It looks delicious and spicy, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfFrost) {

			name = "Icefruit";
			potionGlow = new ItemSprite.Glowing(0x66B3FF);
			//info += "It looks delicious and refreshing, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfMindVision) {

			name = "Fadefruit";
			potionGlow = new ItemSprite.Glowing(0xB8E6CF);
			//info += "It looks delicious and shadowy, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfToxicGas) {

			name = "Sorrowfruit";
			potionGlow = new ItemSprite.Glowing(0xA15CE5);
			//info += "It looks delicious and crisp, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfLevitation) {

			name = "Stormfruit";
			potionGlow = new ItemSprite.Glowing(0x1C3A57);
			//info += "It looks delicious and lightweight, ready to be eaten!";

		} else if (potionAttrib instanceof PotionOfPurity) {

			name = "Dreamfruit";
			potionGlow = new ItemSprite.Glowing(0x8E2975);
			//info += "It looks delicious and clean, ready to be eaten!";
			
		} else if (potionAttrib instanceof PotionOfExperience) {

			name = "Starfruit";
			potionGlow = new ItemSprite.Glowing( 0xA79400 );
			//info += "It looks delicious and glorious, ready to be eaten!";
			
		} else if (potionAttrib instanceof PotionOfOverHealing) {

			name = "Heartfruit";
			potionGlow = new ItemSprite.Glowing( 0xB20000 );
			//info += "It is pulsating with energy, ready to be eaten!";

		}

		return this;
	}

	public static final String POTIONATTRIB = "potionattrib";

	@Override
	public void cast(final Hero user, int dst) {
		if (potionAttrib instanceof PotionOfLiquidFlame
				|| potionAttrib instanceof PotionOfToxicGas
				|| potionAttrib instanceof PotionOfParalyticGas
				|| potionAttrib instanceof PotionOfFrost
				|| potionAttrib instanceof PotionOfLevitation
				|| potionAttrib instanceof PotionOfPurity) {
			potionAttrib.cast(user, dst);
			detach(user.belongings.backpack);
		} else {
			super.cast(user, dst);
		}

	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(POTIONATTRIB, potionAttrib);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(POTIONATTRIB)) {
			imbuePotion((Potion) bundle.get(POTIONATTRIB));

			// TODO: legacy code for pre-v0.2.3, remove when saves from that
			// version are no longer supported.
		} else if (bundle.contains("name")) {
			name = bundle.getString("name");

			if (name.equals("Healthfruit"))
				cook(new Sungrass.Seed());
			else if (name.equals("Powerfruit"))
				cook(new Rotberry.Seed());
			else if (name.equals("Paralyzefruit"))
				cook(new Earthroot.Seed());
			else if (name.equals("Invisifruit"))
				cook(new Blindweed.Seed());
			else if (name.equals("Flamefruit"))
				cook(new Firebloom.Seed());
			else if (name.equals("Frostfruit"))
				cook(new Icecap.Seed());
			else if (name.equals("Visionfruit"))
				cook(new Fadeleaf.Seed());
			else if (name.equals("Toxicfruit"))
				cook(new Sorrowmoss.Seed());
			else if (name.equals("Floatfruit"))
				cook(new Stormvine.Seed());
			else if (name.equals("Purefruit"))
				cook(new Dreamfoil.Seed());
			else if (name.equals("Mightyfruit"))
				cook(new Phaseshift.Seed());
			else if (name.equals("Heartfruit"))
				cook(new Flytrap.Seed());
		}

	}

	@Override
	public ItemSprite.Glowing glowing() {
		return potionGlow;
	}

}

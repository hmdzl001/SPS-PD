package com.hmdzl.spspd.change.items.food.fruit;


import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.FrostImbue;
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

	public Potion potionAttrib = null;
	public ItemSprite.Glowing potionGlow = null;

	{
		//name = "Blandfruit";
		stackable = true;
		image = ItemSpriteSheet.BLANDFRUIT;
		energy = 50;
		hornValue = 6; // only applies when blandfruit is cooked

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

                if (potionAttrib instanceof PotionOfLiquidFlame) {
					GLog.i(Messages.get(this, "fire_msg"));
					Buff.affect(hero, FireImbue.class).set(FireImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfToxicGas) {
					GLog.i(Messages.get(this, "toxic_msg"));
					Buff.affect(hero, ToxicImbue.class)
							.set(ToxicImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfParalyticGas) {
					GLog.i(Messages.get(this, "para_msg"));
					Buff.affect(hero, EarthImbue.class, EarthImbue.DURATION);
				} else if (potionAttrib instanceof PotionOfFrost) {
					GLog.i(Messages.get(this, "frost_msg"));
					Buff.affect(hero, FrostImbue.class, FrostImbue.DURATION);
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
				case SOLDIER:
					break;
				}
			}
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public String desc() {
		if (potionAttrib== null) return super.desc();
		else return Messages.get(this, "desc_cooked");
	}

	@Override
	public int price() {
		return 20 * quantity;
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

		if (potionAttrib instanceof PotionOfHealing){
			name = Messages.get(this, "sunfruit");
			potionGlow = new ItemSprite.Glowing( 0x2EE62E );
		} else if (potionAttrib instanceof PotionOfStrength){
			name = Messages.get(this, "rotfruit");
			potionGlow = new ItemSprite.Glowing( 0xCC0022 );
		} else if (potionAttrib instanceof PotionOfParalyticGas){
			name = Messages.get(this, "earthfruit");
			potionGlow = new ItemSprite.Glowing( 0x67583D );
		} else if (potionAttrib instanceof PotionOfInvisibility){
			name = Messages.get(this, "blindfruit");
			potionGlow = new ItemSprite.Glowing( 0xE5D273 );
		} else if (potionAttrib instanceof PotionOfLiquidFlame){
			name = Messages.get(this, "firefruit");
			potionGlow = new ItemSprite.Glowing( 0xFF7F00 );
		} else if (potionAttrib instanceof PotionOfFrost){
			name = Messages.get(this, "icefruit");
			potionGlow = new ItemSprite.Glowing( 0x66B3FF );
		} else if (potionAttrib instanceof PotionOfMindVision){
			name = Messages.get(this, "fadefruit");
			potionGlow = new ItemSprite.Glowing( 0xB8E6CF );
		} else if (potionAttrib instanceof PotionOfToxicGas){
			name = Messages.get(this, "sorrowfruit");
			potionGlow = new ItemSprite.Glowing( 0xA15CE5 );
		} else if (potionAttrib instanceof PotionOfLevitation) {
			name = Messages.get(this, "stormfruit");
			potionGlow = new ItemSprite.Glowing( 0x1C3A57 );
		} else if (potionAttrib instanceof PotionOfPurity) {
			name = Messages.get(this, "dreamfruit");
			potionGlow = new ItemSprite.Glowing( 0x8E2975 );
		} else if (potionAttrib instanceof PotionOfExperience) {
			name = Messages.get(this, "starfruit");
			potionGlow = new ItemSprite.Glowing( 0xA79400 );
		}else if (potionAttrib instanceof PotionOfOverHealing) {
			name = Messages.get(this, "heartfruit");
			potionGlow = new ItemSprite.Glowing( 0xB20000 );

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

		}
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return potionGlow;
	}

}

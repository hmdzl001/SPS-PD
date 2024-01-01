package com.hmdzl.spspd.items.food.fruit;


import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.FrostImbue;
import com.hmdzl.spspd.actors.buffs.GasesImmunity;
import com.hmdzl.spspd.actors.buffs.HTimprove;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.HighLight;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.MindVision;
import com.hmdzl.spspd.actors.buffs.MoonFury;
import com.hmdzl.spspd.actors.buffs.Muscle;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.ToxicImbue;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.SpellSprite;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StrBottle;
import com.hmdzl.spspd.items.potions.PotionOfExperience;
import com.hmdzl.spspd.items.potions.PotionOfFrost;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.items.potions.PotionOfLevitation;
import com.hmdzl.spspd.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.items.potions.PotionOfMending;
import com.hmdzl.spspd.items.potions.PotionOfMight;
import com.hmdzl.spspd.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.items.potions.PotionOfMixing;
import com.hmdzl.spspd.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.items.potions.PotionOfParalyticGas;
import com.hmdzl.spspd.items.potions.PotionOfPurity;
import com.hmdzl.spspd.items.potions.PotionOfShield;
import com.hmdzl.spspd.items.potions.PotionOfStrength;
import com.hmdzl.spspd.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant.Seed;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

/**
 * Created by debenhame on 12/08/2014.
 */
public class Blandfruit extends Fruit {

	public Item potionAttrib = null;
	public ItemSprite.Glowing potionGlow = null;

	{
		//name = "Blandfruit";
		stackable = true;
		image = ItemSpriteSheet.BLANDFRUIT;
		energy = 100;
		hornValue = 2; // only applies when blandfruit is cooked

	}

	//@Override
	//public boolean isSimilar(Item item) {
	//	if (item instanceof Blandfruit) {
	//		if (potionAttrib == null) {
     //           return ((Blandfruit) item).potionAttrib == null;
	//		} else if (((Blandfruit) item).potionAttrib != null) {
    //            return ((Blandfruit) item).potionAttrib.getClass() == potionAttrib
     //                   .getClass();
	//		}
	//	}
	//	return false;
	//}

	//@Override
	//public void execute(Hero hero, String action) {
	//	if (action.equals(AC_EAT)) {


	//	} else {
	//		super.execute(hero, action);
	//	}
	//}

	@Override
	public int price() {
		return 20 * quantity;
	}

}

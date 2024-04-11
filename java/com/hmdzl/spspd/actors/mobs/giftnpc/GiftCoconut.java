/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.actors.mobs.giftnpc;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.TestCloak;
import com.hmdzl.spspd.items.armor.normalarmor.CeramicsArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MachineArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ProtectiveclothingArmor;
import com.hmdzl.spspd.items.armor.normalarmor.StoneArmor;
import com.hmdzl.spspd.items.armor.normalarmor.StyrofoamArmor;
import com.hmdzl.spspd.items.armor.specialarmor.RenBArmor;
import com.hmdzl.spspd.items.armor.specialarmor.TestArmor;
import com.hmdzl.spspd.items.artifacts.AlienBag;
import com.hmdzl.spspd.items.artifacts.GlassTotem;
import com.hmdzl.spspd.items.artifacts.RobotDMT;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.eggs.CocoCatEgg;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.completefood.NutCake;
import com.hmdzl.spspd.items.food.completefood.PerfectFood;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.completefood.YearFood;
import com.hmdzl.spspd.items.food.fruit.Cherry;
import com.hmdzl.spspd.items.food.fruit.Strawberry;
import com.hmdzl.spspd.items.food.meatfood.MeatFood;
import com.hmdzl.spspd.items.medicine.BlueMilk;
import com.hmdzl.spspd.items.medicine.DeathCap;
import com.hmdzl.spspd.items.medicine.Earthstar;
import com.hmdzl.spspd.items.medicine.GoldenJelly;
import com.hmdzl.spspd.items.medicine.GreenSpore;
import com.hmdzl.spspd.items.medicine.JackOLantern;
import com.hmdzl.spspd.items.medicine.PixieParasol;
import com.hmdzl.spspd.items.potions.PotionOfMixing;
import com.hmdzl.spspd.items.rings.RingOfKnowledge;
import com.hmdzl.spspd.items.scrolls.ScrollOfDummy;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.summon.Mobile;
import com.hmdzl.spspd.items.wands.WandOfFlock;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfTCloud;
import com.hmdzl.spspd.items.wands.WandOfTest;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.melee.Club;
import com.hmdzl.spspd.items.weapon.melee.CurseBox;
import com.hmdzl.spspd.items.weapon.melee.Flute;
import com.hmdzl.spspd.items.weapon.melee.HandLight;
import com.hmdzl.spspd.items.weapon.melee.Harp;
import com.hmdzl.spspd.items.weapon.melee.HolyWater;
import com.hmdzl.spspd.items.weapon.melee.Lance;
import com.hmdzl.spspd.items.weapon.melee.MirrorDoll;
import com.hmdzl.spspd.items.weapon.melee.PrayerWheel;
import com.hmdzl.spspd.items.weapon.melee.Rapier;
import com.hmdzl.spspd.items.weapon.melee.Triangolo;
import com.hmdzl.spspd.items.weapon.melee.TrickSand;
import com.hmdzl.spspd.items.weapon.melee.Trumpet;
import com.hmdzl.spspd.items.weapon.melee.Wardrum;
import com.hmdzl.spspd.items.weapon.melee.WindBottle;
import com.hmdzl.spspd.items.weapon.melee.relic.AresSword;
import com.hmdzl.spspd.items.weapon.melee.relic.CromCruachAxe;
import com.hmdzl.spspd.items.weapon.melee.relic.JupitersWraith;
import com.hmdzl.spspd.items.weapon.melee.relic.LokisFlail;
import com.hmdzl.spspd.items.weapon.melee.relic.NeptunusTrident;
import com.hmdzl.spspd.items.weapon.melee.special.RunicBlade;
import com.hmdzl.spspd.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.HugeShuriken;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.MiniMoai;
import com.hmdzl.spspd.items.weapon.spammo.GoldAmmo;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CoconutSprite;
import com.hmdzl.spspd.sprites.RENSprite;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndNewNpcMessage;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class GiftCoconut extends GiftNpc {

	{
		//name = Messages.get(this,"name");
		spriteClass = CoconutSprite.class;
		properties.add(Property.MECH);
		properties.add(Property.BEAST);
		
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public boolean loveitem(Item item) {
		return item instanceof NutCake || item instanceof AlienBag ||
				item instanceof CocoCatEgg || item instanceof GoldAmmo ||
				item instanceof PotionOfMixing ||
				item instanceof TestArmor || item instanceof TestWeapon || item instanceof WandOfTest || item instanceof TestCloak;
	}


	@Override
	public Item SupercreateLoot(){
		return new DungeonBomb();
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public void givereward(NPC npc) {
		this.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.2f, 5);
		switch (this.FRIEND) {
			case 30 :
				hero.hitSkill++;
				friend(this, Messages.get(this, "reward2"));
				break;

			case 50 :
				hero.evadeSkill++;
				friend(this, Messages.get(this, "reward3"));
				break;

			case 70 :
				hero.magicSkill++;
				friend(this, Messages.get(this, "reward4"));
				break;

			case 100 :
				hero.STR++;
				friend(this, Messages.get(this, "reward5"));
				break;

			default:
				if (this.FRIEND % 40 == 0){
					Dungeon.depth.drop(new DungeonBomb(), hero.pos).sprite.drop();
					friend(this, Messages.get(this, "reward1"));
				} else {
					friend(this, Messages.get(this, "thank1"));
				}
				break;
		}

	}

	private void friend(NPC npc,String text ) {
		GameScene.show(new WndNewNpcMessage(npc,text));
	}


}

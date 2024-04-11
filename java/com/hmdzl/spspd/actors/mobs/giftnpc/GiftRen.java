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
import com.hmdzl.spspd.items.armor.normalarmor.CeramicsArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MachineArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ProtectiveclothingArmor;
import com.hmdzl.spspd.items.armor.normalarmor.StoneArmor;
import com.hmdzl.spspd.items.armor.normalarmor.StyrofoamArmor;
import com.hmdzl.spspd.items.armor.specialarmor.RenBArmor;
import com.hmdzl.spspd.items.artifacts.AlienBag;
import com.hmdzl.spspd.items.artifacts.GlassTotem;
import com.hmdzl.spspd.items.artifacts.RobotDMT;
import com.hmdzl.spspd.items.food.Nut;
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
import com.hmdzl.spspd.items.rings.RingOfKnowledge;
import com.hmdzl.spspd.items.scrolls.ScrollOfDummy;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.summon.Mobile;
import com.hmdzl.spspd.items.wands.WandOfFlock;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfTCloud;
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
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.HugeShuriken;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.MiniMoai;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.RENSprite;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndNewNpcMessage;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.utils.Random;

public class GiftRen extends GiftNpc {

	{
		//name = Messages.get(this,"name");
		spriteClass = RENSprite.class;
		properties.add(Property.ELF);
		
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public boolean loveitem(Item item) {
		return item instanceof MiniMoai || item instanceof HugeShuriken ||
				item instanceof ActiveMrDestructo || item instanceof Mobile ||
				item instanceof ToyGun ||
				item instanceof TrickSand || item instanceof MirrorDoll || item instanceof WindBottle || item instanceof HandLight || item instanceof CurseBox ||
				item instanceof HolyWater || item instanceof PrayerWheel ||
				item instanceof Triangolo || item instanceof Flute || item instanceof Wardrum || item instanceof Trumpet || item instanceof Harp ||
				item instanceof Club || item instanceof RunicBlade || item instanceof Rapier || item instanceof Lance ||
				item instanceof AresSword || item instanceof CromCruachAxe || item instanceof JupitersWraith || item instanceof LokisFlail || item instanceof NeptunusTrident ||
				item instanceof WandOfFlock || item instanceof WandOfFlow || item instanceof WandOfTCloud ||
				item instanceof StoneArmor || item instanceof CeramicsArmor || item instanceof ProtectiveclothingArmor || item instanceof MachineArmor || item instanceof StyrofoamArmor ||
				item instanceof Strawberry || item instanceof Cherry || item instanceof Nut || item instanceof PerfectFood ||
				item instanceof BlueMilk || item instanceof DeathCap || item instanceof Earthstar ||
				item instanceof JackOLantern || item instanceof PixieParasol || item instanceof GoldenJelly || item instanceof GreenSpore ||
				item instanceof RingOfKnowledge ||
				item instanceof ScrollOfDummy || item instanceof ScrollOfRegrowth;
	}


	@Override
	public Item SupercreateLoot(){
		return new YearFood();
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public void givereward(NPC npc) {
		this.sprite.centerEmitter().start(Speck.factory(Speck.HEART), 0.2f, 5);
		switch (this.FRIEND) {
			case 100 :
				Dungeon.depth.drop(new RenBArmor(),Dungeon.hero.pos).sprite.drop();
				friend(this, Messages.get(this, "reward5"));
				break;

			default:
				if (this.FRIEND % 20 == 0){
					Dungeon.depth.drop(Generator.random(Generator.Category.NORNSTONE),Dungeon.hero.pos).sprite.drop();
					switch (Random.Int(4)) {
						case 0:
							friend(this, Messages.get(this, "reward1"));
							break;
						case 1:
							friend(this, Messages.get(this, "reward2"));
							break;
						case 2:
							friend(this, Messages.get(this, "reward3"));
							break;
						case 3:
							friend(this, Messages.get(this, "reward4"));
							break;
					}

				} else switch (Random.Int(3)) {
					case 0:
						friend(this, Messages.get(this, "thank1"));
						break;
					case 1:
						friend(this, Messages.get(this, "thank2"));
						break;
					case 2:
						friend(this, Messages.get(this, "thank3"));
						break;
				}

				break;
		}

	}

	private void friend(NPC npc,String text ) {
		GameScene.show(new WndNewNpcMessage(npc,text));
	}


}

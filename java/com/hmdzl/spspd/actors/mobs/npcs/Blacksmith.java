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
package com.hmdzl.spspd.actors.mobs.npcs;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Journal;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.ChaosPack;
import com.hmdzl.spspd.items.CurseBlood;
import com.hmdzl.spspd.items.EmptyBody;
import com.hmdzl.spspd.items.EquipableItem;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.TriForce;
import com.hmdzl.spspd.items.TriforceOfCourage;
import com.hmdzl.spspd.items.TriforceOfPower;
import com.hmdzl.spspd.items.TriforceOfWisdom;
import com.hmdzl.spspd.items.quest.DarkGold;
import com.hmdzl.spspd.items.quest.Pickaxe;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.weapon.melee.special.ShadowEater;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Room.Type;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BlacksmithSprite;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBlacksmith;
import com.hmdzl.spspd.windows.WndQuest;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.Collection;

public class Blacksmith extends NPC {

	private static final String TXT_GOLD_1 = "Hey human! Wanna be useful, eh? Take dis pickaxe and mine me some _dark gold ore_, _15 pieces_ should be enough. "
			+ "What do you mean, how am I gonna pay? You greedy...\n"
			+ "Ok, ok, I don't have money to pay, but I can do some smithin' for you. Consider yourself lucky, "
			+ "We're the only blacksmiths around.";
	private static final String TXT_BLOOD_1 = "Hey human! Wanna be useful, eh? Take dis pickaxe and _kill a bat_ wit' it, I need its blood on the head. "
			+ "What do you mean, how am I gonna pay? You greedy...\n"
			+ "Ok, ok, I don't have money to pay, but I can do some smithin' for you. Consider yourself lucky, "
			+ "We're the only blacksmiths around.";
	private static final String TXT2 = "Are you kiddin' me? Where is my pickaxe?!";
	private static final String TXT3 = "Dark gold ore. 15 pieces. Seriously, is it dat hard?";
	private static final String TXT4 = "I said I need bat blood on the pickaxe. Chop chop!";
	private static final String TXT_COMPLETED = "Oh, you have returned... Better late dan never.";
	private static final String TXT_GET_LOST = "I'm busy. Get lost!";

	private static final String TXT_LOOKS_BETTER = "your %s certainly looks better now";
	private static final String COLLECTED = "Finally, the TriForce. I will forge them for you...";

	{
		//name = "troll blacksmith named Bop";
		spriteClass = BlacksmithSprite.class;
		properties.add(Property.TROLL);
		properties.add(Property.IMMOVABLE);
	}
	

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}

	@Override
	public boolean interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
		
		if (checksan()){
            tell(Messages.get(this, "triforce"));	
            TriForce san = new TriForce();
            Dungeon.triforce = true;	
			Dungeon.level.drop(san, Dungeon.hero.pos).sprite.drop();
           }

		if (checkeater()){
			tell(Messages.get(this, "shadoweater"));
			ShadowEater san = new ShadowEater();
			Dungeon.level.drop(san, Dungeon.hero.pos).sprite.drop();
		}


		if (!Quest.given) {

			GameScene.show(new WndQuest(this,  Messages.get(this, "gold_1")) {

				@Override
				public void onBackPressed() {
					super.onBackPressed();

					Quest.given = true;
					Quest.completed = false;

					Pickaxe pick = new Pickaxe();

					Dungeon.level.drop(pick, Dungeon.hero.pos).sprite.drop();
	
				}
            });

			Journal.add(Journal.Feature.TROLL);

		} else if (!Quest.completed) {

				Pickaxe pick = Dungeon.hero.belongings.getItem(Pickaxe.class);
				DarkGold gold = Dungeon.hero.belongings.getItem(DarkGold.class);
				if (pick == null) {
					tell(Messages.get(this, "lost_pick"));
				} else if (gold == null || gold.quantity() < 15) {
					tell(Messages.get(this, "gold_2"));
				} else {
					//if (pick.isEquipped(Dungeon.hero)) {
					//	pick.doUnequip(Dungeon.hero, false);
					//}
					//pick.detach(Dungeon.hero.belongings.backpack);
					yell( Messages.get(this, "keeppickaxe"));
					tell( Messages.get(this, "completed"));

					Quest.completed = true;
					Quest.reforged = false;
				}

		} else if (!Quest.reforged) {

			GameScene.show(new WndBlacksmith(this, Dungeon.hero));

		} else {
            
		   tell(Messages.get(this, "get_lost"));
         

		}
		return false;
	}

	private void tell(String text) {
		GameScene.show(new WndQuest(this, text));
	}

	public static String verify(Item item1, Item item2) {

		if (item1 == item2) {
			return Messages.get(Blacksmith.class, "same_item");
		}

		//if (item1.getClass() != item2.getClass()) {
		//	return "Select 2 items of the same type!";
		//}

		if (!item1.isIdentified() || !item2.isIdentified()) {
			return Messages.get(Blacksmith.class, "un_ided");
		}

		if (item1.cursed || item2.cursed) {
			return Messages.get(Blacksmith.class, "cursed");
		}

		if (item1.level < 0 || item2.level < 1) {
			return Messages.get(Blacksmith.class, "degraded");
		}
		
		if ((item1.level + item2.level > 15) && !item1.isReinforced()) {
			return Messages.get(Blacksmith.class, "need_reinforced");
		}

		if (!item1.isUpgradable() || !item2.isUpgradable()) {
			return Messages.get(Blacksmith.class, "cant_reforge");
		}

		return null;
	}

	private static float upgradeChance = 0.5f;
	public static void upgrade(Item item1, Item item2) {

		Item first, second;
		
			first = item1;
			second = item2;
		

		Sample.INSTANCE.play(Assets.SND_EVOKE);
		ScrollOfUpgrade.upgrade(Dungeon.hero);
		Item.evoke(Dungeon.hero);

		if (first.isEquipped(Dungeon.hero)) {
			((EquipableItem) first).doUnequip(Dungeon.hero, true);
		}
		
		DarkGold gold = Dungeon.hero.belongings.getItem(DarkGold.class);
		if (gold!=null){
		upgradeChance = (upgradeChance + (gold.quantity()*0.05f));
		}
		if (first != null) {
                for(int i=0; i<second.level; i++){
				if (i<2){
				  Sample.INSTANCE.play(Assets.SND_EVOKE);
				  first.upgrade();
				} else if (Random.Float()<upgradeChance){
				  first.upgrade();
			      upgradeChance = Math.max(0.5f, upgradeChance-0.1f);
			    }
			}
		}
		
		GLog.p(Messages.get(ScrollOfUpgrade.class, "looks_better", first.name()));
		Dungeon.hero.spendAndNext(2f);
		Badges.validateItemLevelAquired(first);

		if (second.isEquipped(Dungeon.hero)) {
			((EquipableItem) second).doUnequip(Dungeon.hero, false);
		}
		second.detachAll(Dungeon.hero.belongings.backpack);
		if (gold!=null){gold.detachAll(Dungeon.hero.belongings.backpack);}
		Quest.reforged = true;

		//Journal.remove(Journal.Feature.TROLL);
	}
	
	public static boolean checksan() {
		TriforceOfCourage san1 = Dungeon.hero.belongings.getItem(TriforceOfCourage.class);
		TriforceOfPower san2 = Dungeon.hero.belongings.getItem(TriforceOfPower.class);
		TriforceOfWisdom san3 = Dungeon.hero.belongings.getItem(TriforceOfWisdom.class);
		
		if (san1!=null && san2!=null && san3!=null){
			san1.detach(Dungeon.hero.belongings.backpack);
			san2.detach(Dungeon.hero.belongings.backpack);
			san3.detach(Dungeon.hero.belongings.backpack);
			return true;			
		} else {
			return false;
		}
		
	}

	public static boolean checkeater() {
		CurseBlood san1 = Dungeon.hero.belongings.getItem(CurseBlood.class);
		EmptyBody san2 = Dungeon.hero.belongings.getItem(EmptyBody.class);
		ChaosPack san3 = Dungeon.hero.belongings.getItem(ChaosPack.class);

		if (san1!=null && san2!=null && san3!=null){
			san1.detach(Dungeon.hero.belongings.backpack);
			san2.detach(Dungeon.hero.belongings.backpack);
			san3.detach(Dungeon.hero.belongings.backpack);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

	public static class Quest {

		private static boolean spawned;

		//private static boolean alternative;
		private static boolean given;
		private static boolean completed;
		private static boolean reforged;

		public static void reset() {
			spawned = false;
			given = false;
			completed = false;
			reforged = false;
		}

		private static final String NODE = "blacksmith";

		private static final String SPAWNED = "spawned";
		//private static final String ALTERNATIVE = "alternative";
		private static final String GIVEN = "given";
		private static final String COMPLETED = "completed";
		private static final String REFORGED = "reforged";

		public static void storeInBundle(Bundle bundle) {

			Bundle node = new Bundle();

			node.put(SPAWNED, spawned);

			if (spawned) {
				//node.put(ALTERNATIVE, alternative);
				node.put(GIVEN, given);
				node.put(COMPLETED, completed);
				node.put(REFORGED, reforged);
			}

			bundle.put(NODE, node);
		}

		public static void restoreFromBundle(Bundle bundle) {

			Bundle node = bundle.getBundle(NODE);

			if (!node.isNull() && (spawned = node.getBoolean(SPAWNED))) {
				//alternative = node.getBoolean(ALTERNATIVE);
				given = node.getBoolean(GIVEN);
				completed = node.getBoolean(COMPLETED);
				reforged = node.getBoolean(REFORGED);
			} else {
				reset();
			}
		}

		public static boolean spawn(Collection<Room> rooms) {
			//if (!spawned && Dungeon.depth > 11 && Random.Int( 15 - Dungeon.depth ) == 0) {
			if (!spawned ) {
				    
				Room blacksmith = null;
				for (Room r : rooms) {
					if (r.type == Type.STANDARD && r.width() > 4
							&& r.height() > 4) {
						blacksmith = r;
						blacksmith.type = Type.BLACKSMITH;

						spawned = true;
						
						//alternative = Random.Int(2) == 0;
	
						given = false;

						break;
					}
				}
			}
			return spawned;
		}
	}
}

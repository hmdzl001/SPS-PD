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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Journal;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.quest.CorpseDust;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.wands.WandOfAcid;
import com.hmdzl.spspd.items.wands.WandOfBlood;
import com.hmdzl.spspd.items.wands.WandOfCharm;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.wands.WandOfFlock;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfFreeze;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.items.wands.WandOfLightning;
import com.hmdzl.spspd.items.wands.WandOfMagicMissile;
import com.hmdzl.spspd.items.wands.WandOfMeteorite;
import com.hmdzl.spspd.items.wands.WandOfSwamp;
import com.hmdzl.spspd.items.wands.WandOfTCloud;
import com.hmdzl.spspd.levels.PrisonLevel;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Rotberry;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.WandmakerSprite;
import com.hmdzl.spspd.windows.WndQuest;
import com.hmdzl.spspd.windows.WndWandmaker;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Wandmaker extends NPC {

	{
		//name = "old wandmaker";
		spriteClass = WandmakerSprite.class;
		properties.add(Property.HUMAN);
		properties.add(Property.IMMOVABLE);
	}

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
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

	@Override
	public boolean interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
		if (Quest.given) {

			Item item = Quest.alternative ? Dungeon.hero.belongings
					.getItem(CorpseDust.class) : Dungeon.hero.belongings
					.getItem(Rotberry.Seed.class);
			if (item != null) {
				GameScene.show(new WndWandmaker(this, item));
			} else {
				tell(Quest.alternative ?
				Messages.get(this, "dust_2", Dungeon.hero.givenName())
				: Messages.get(this, "berry_2" , Dungeon.hero.givenName()));
			}

		} else {

			Quest.placeItem();

			if (Quest.given)
				tell(Quest.alternative ? Messages.get(this, "dust_1") : Messages.get(this, "berry_1"));

			Journal.add(Journal.Feature.WANDMAKER);
		}
		return false;
	}

	private void tell(String text) {
		GameScene.show(new WndQuest(this,text));
	}

	public static class Quest {

		private static boolean spawned;

		private static boolean alternative;

		private static boolean given;

		public static Wand wand1;
		public static Wand wand2;

		public static void reset() {
			spawned = false;

			wand1 = null;
			wand2 = null;
		}

		private static final String NODE = "wandmaker";

		private static final String SPAWNED = "spawned";
		private static final String ALTERNATIVE = "alternative";
		private static final String GIVEN = "given";
		private static final String WAND1 = "wand1";
		private static final String WAND2 = "wand2";

		public static void storeInBundle(Bundle bundle) {

			Bundle node = new Bundle();

			node.put(SPAWNED, spawned);

			if (spawned) {

				node.put(ALTERNATIVE, alternative);

				node.put(GIVEN, given);

				node.put(WAND1, wand1);
				node.put(WAND2, wand2);
			}

			bundle.put(NODE, node);
		}

		public static void restoreFromBundle(Bundle bundle) {

			Bundle node = bundle.getBundle(NODE);

			if (!node.isNull() && (spawned = node.getBoolean(SPAWNED))) {

				alternative = node.getBoolean(ALTERNATIVE);

				given = node.getBoolean(GIVEN);

				wand1 = (Wand) node.get(WAND1);
				wand2 = (Wand) node.get(WAND2);
			} else {
				reset();
			}
		}

		public static void spawn(PrisonLevel level, Room room) {
			if (!spawned && Dungeon.dungeondepth ==7) {

				Wandmaker npc = new Wandmaker();
				do {
					npc.pos = room.random();
				} while (level.map[npc.pos] == Terrain.ENTRANCE
						|| level.map[npc.pos] == Terrain.SIGN
						|| level.map[npc.pos] == Terrain.DEW_BLESS);
				level.mobs.add(npc);
				Actor.occupyCell(npc);

				spawned = true;
				
				//if (Dungeon.isChallenged(Challenges.NO_HERBALISM)){
					//alternative=true;
				//} else {
				    alternative = Random.Int(2) == 0;
				//}

				given = false;

				switch (Random.Int(7)) {
				case 0:
					wand1 = new WandOfLight();
					break;
				case 1:
					wand1 = new WandOfDisintegration();
					break;
				case 2:
					wand1 = new WandOfFirebolt();
					break;
				case 3:
					wand1 = new WandOfLightning();
					break;
				case 4:
					wand1 = new WandOfAcid();
					break;
					case 5:
						wand1 = new WandOfBlood();
						break;
					case 6:
						wand1 = new WandOfFreeze();
						break;
				}
				wand1.random().upgrade();

				switch (Random.Int(7)) {
				case 0:
					wand2 = new WandOfCharm();
					break;
				case 1:
					wand2 = new WandOfFlock();
					break;
				case 2:
					wand2 = new WandOfSwamp();
					break;
				case 3:
					wand2 = new WandOfMeteorite();
					break;
				case 4:
					wand2 = new WandOfFlow();
					break;
					case 5:
						wand2 = new WandOfTCloud();
						break;
					case 6:
						wand2 = new WandOfFlow();
						break;
					case 7:
						wand2 = new WandOfMagicMissile();
						break;
				}
				wand2.random().upgrade();
			}
		}

		public static void placeItem() {
			if (alternative /*|| Dungeon.isChallenged(Challenges.NO_HERBALISM)*/) {

				ArrayList<Heap> candidates = new ArrayList<Heap>();
				for (Heap heap : Dungeon.depth.heaps.values()) {
					if (heap.type == Heap.Type.SKELETON
							&& !Dungeon.visible[heap.pos]) {
						candidates.add(heap);
					}
				}

				if (candidates.size() > 0) {
					Random.element(candidates).drop(new CorpseDust());
					given = true;
				} else {
					int pos = Dungeon.depth.randomRespawnCell();
					while (Dungeon.depth.heaps.get(pos) != null) {
						pos = Dungeon.depth.randomRespawnCell();
					}

					if (pos != -1) {
						Heap heap = Dungeon.depth.drop(new CorpseDust(), pos);
						heap.type = Heap.Type.SKELETON;
						heap.sprite.link();
						given = true;
					}
				}

			} else {

				int shrubPos = Dungeon.depth.randomRespawnCell();
				while (Dungeon.depth.heaps.get(shrubPos) != null) {
					shrubPos = Dungeon.depth.randomRespawnCell();
				}

				if (shrubPos != -1) {
					Dungeon.depth.plant(new Rotberry.Seed(), shrubPos);
					given = true;
				}

			}
		}

		public static void complete() {
			wand1 = null;
			wand2 = null;

			Journal.remove(Journal.Feature.WANDMAKER);
		}
	}
}

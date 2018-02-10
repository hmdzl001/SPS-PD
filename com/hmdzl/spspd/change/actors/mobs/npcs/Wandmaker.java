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
package com.hmdzl.spspd.change.actors.mobs.npcs;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Challenges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Journal;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.potions.PotionOfStrength;
import com.hmdzl.spspd.change.items.quest.CorpseDust;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.wands.WandOfCharm;
import com.hmdzl.spspd.change.items.wands.WandOfAvalanche;
import com.hmdzl.spspd.change.items.wands.WandOfBlood;
import com.hmdzl.spspd.change.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.change.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.change.items.wands.WandOfLightning;
import com.hmdzl.spspd.change.items.wands.WandOfPoison;
import com.hmdzl.spspd.change.items.wands.WandOfLight;
import com.hmdzl.spspd.change.items.wands.WandOfFreeze;
import com.hmdzl.spspd.change.items.wands.WandOfFlow;
import com.hmdzl.spspd.change.levels.PrisonLevel;
import com.hmdzl.spspd.change.levels.Room;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.plants.Rotberry;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.sprites.WandmakerSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.hmdzl.spspd.change.windows.WndQuest;
import com.hmdzl.spspd.change.windows.WndWandmaker;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.messages.Messages;

public class Wandmaker extends NPC {

	{
		//name = "old wandmaker";
		spriteClass = WandmakerSprite.class;
		properties.add(Property.HUMAN);
		properties.add(Property.IMMOVABLE);
	}

	private static final String TXT_BERRY1 = "Oh, what a pleasant surprise to meet a decent person in such place! I came here for a rare ingredient - "
			+ "a _Rotberry seed_. Being a magic user, I'm quite able to defend myself against local monsters, "
			+ "but I'm getting lost in no time, it's very embarrassing. Probably you could help me? I would be "
			+ "happy to pay for your service with one of my best wands.";

	private static final String TXT_DUST1 = "Oh, what a pleasant surprise to meet a decent person in such place! I came here for a rare ingredient - "
			+ "_corpse dust_. It can be gathered from skeletal remains and there is an ample number of them in the dungeon. "
			+ "Being a magic user, I'm quite able to defend myself against local monsters, but I'm getting lost in no time, "
			+ "it's very embarrassing. Probably you could help me? I would be happy to pay for your service with one of my best wands.";

	private static final String TXT_BERRY2 = "Any luck with a Rotberry seed, %s? No? Don't worry, I'm not in a hurry.";

	private static final String TXT_DUST2 = "Any luck with corpse dust, %s? Bone piles are the most obvious places to look.";

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}

	@Override
	public int defenseSkill(Char enemy) {
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
	public void interact() {

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
			if (!spawned && Dungeon.depth==7) {

				Wandmaker npc = new Wandmaker();
				do {
					npc.pos = room.random();
				} while (level.map[npc.pos] == Terrain.ENTRANCE
						|| level.map[npc.pos] == Terrain.SIGN);
				level.mobs.add(npc);
				Actor.occupyCell(npc);

				spawned = true;
				
				if (Dungeon.isChallenged(Challenges.NO_HERBALISM)){
					alternative=true;
				} else {
				    alternative = Random.Int(2) == 0;
				}

				given = false;

				switch (Random.Int(5)) {
				case 0:
					wand1 = new WandOfAvalanche();
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
					wand1 = new WandOfPoison();
					break;
				}
				wand1.random().upgrade();

				switch (Random.Int(5)) {
				case 0:
					wand2 = new WandOfCharm();
					break;
				case 1:
					wand2 = new WandOfBlood();
					break;
				case 2:
					wand2 = new WandOfLight();
					break;
				case 3:
					wand2 = new WandOfFreeze();
					break;
				case 4:
					wand2 = new WandOfFlow();
					break;
				}
				wand2.random().upgrade();
			}
		}

		public static void placeItem() {
			if (alternative || Dungeon.isChallenged(Challenges.NO_HERBALISM)) {

				ArrayList<Heap> candidates = new ArrayList<Heap>();
				for (Heap heap : Dungeon.level.heaps.values()) {
					if (heap.type == Heap.Type.SKELETON
							&& !Dungeon.visible[heap.pos]) {
						candidates.add(heap);
					}
				}

				if (candidates.size() > 0) {
					Random.element(candidates).drop(new CorpseDust());
					given = true;
				} else {
					int pos = Dungeon.level.randomRespawnCell();
					while (Dungeon.level.heaps.get(pos) != null) {
						pos = Dungeon.level.randomRespawnCell();
					}

					if (pos != -1) {
						Heap heap = Dungeon.level.drop(new CorpseDust(), pos);
						heap.type = Heap.Type.SKELETON;
						heap.sprite.link();
						given = true;
					}
				}

			} else {

				int shrubPos = Dungeon.level.randomRespawnCell();
				while (Dungeon.level.heaps.get(shrubPos) != null) {
					shrubPos = Dungeon.level.randomRespawnCell();
				}

				if (shrubPos != -1) {
					Dungeon.level.plant(new Rotberry.Seed(), shrubPos);
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

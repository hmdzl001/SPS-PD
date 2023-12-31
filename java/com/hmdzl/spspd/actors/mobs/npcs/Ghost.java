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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Journal;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.mobs.FetidRat;
import com.hmdzl.spspd.actors.mobs.GnollTrickster;
import com.hmdzl.spspd.actors.mobs.GreatCrab;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.levels.SewerLevel;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.GhostSprite;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndQuest;
import com.hmdzl.spspd.windows.WndSadGhost;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Ghost extends NPC {

	{
		//name = "sad ghost";
		spriteClass = GhostSprite.class;

		flying = true;

		state = WANDERING;
		properties.add(Property.UNDEAD);
	}

	private static final String TXT_RAT1 = "Hello %s... Once I was like you - strong and confident... "
			+ "But I was slain by a foul beast... I can't leave this place... Not until I have my revenge... "
			+ "Slay the _fetid rat_, that has taken my life...\n\n"
			+ "It stalks this floor... Spreading filth everywhere... "
			+ "_Beware its cloud of stink and corrosive bite, the acid dissolves in water..._ ";

	private static final String TXT_RAT2 = "Please... Help me... Slay the abomination...\n\n"
			+ "_Fight it near water... Avoid the stench..._";

	private static final String TXT_GNOLL1 = "Hello %s... Once I was like you - strong and confident... "
			+ "But I was slain by a devious foe... I can't leave this place... Not until I have my revenge... "
			+ "Slay the _gnoll trickster_, that has taken my life...\n\n"
			+ "It is not like the other gnolls... It hides and uses thrown weapons... "
			+ "_Beware its poisonous and incendiary darts, don't attack from a distance..._";

	private static final String TXT_GNOLL2 = "Please... Help me... Slay the trickster...\n\n"
			+ "_Don't let it hit you... Get near to it..._";

	private static final String TXT_CRAB1 = "Hello %s... Once I was like you - strong and confident... "
			+ "But I was slain by an ancient creature... I can't leave this place... Not until I have my revenge... "
			+ "Slay the _great crab_, that has taken my life...\n\n"
			+ "It is unnaturally old... With a massive single claw and a thick shell... "
			+ "_Beware its claw, you must surprise the crab or it will block with it..._";

	private static final String TXT_CRAB2 = "Please... Help me... Slay the Crustacean...\n\n"
			+ "_It will always block... When it sees you coming..._";

	public Ghost() {
		super();

		Sample.INSTANCE.load(Assets.SND_GHOST);
	}

	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}

	//@Override
	//public String defenseVerb() {
	//	return "evaded";
	//}

	@Override
	public float speed() {
		return  0.5f;
	}

	@Override
	protected Char chooseEnemy() {
		return null;
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

		Sample.INSTANCE.play(Assets.SND_GHOST);

		if (Quest.given) {
			if (Quest.artifact != null) {
				if (Quest.processed) {
					GameScene.show(new WndSadGhost(this, Quest.type));
				} else {
					switch (Quest.type) {
						case 1:
						default:
							GameScene.show(new WndQuest(this, Messages.get(this, "rat_2")));
							break;
						case 2:
							GameScene.show(new WndQuest(this, Messages.get(this, "gnoll_2")));
							break;
						case 3:
							GameScene.show(new WndQuest(this, Messages.get(this, "crab_2")));
							break;
					}

					int newPos = -1;
					for (int i = 0; i < 10; i++) {
						newPos = Dungeon.depth.randomRespawnCell();
						if (newPos != -1) {
							break;
						}
					}
					if (newPos != -1) {

						//Actor.freeCell(pos);

						CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT),
								0.2f, 3);
						pos = newPos;
						sprite.place(pos);
						sprite.visible = Dungeon.visible[pos];
					}
				}
			}
		} else {
			Mob questBoss;
			String txt_quest;

			switch (Quest.type){
				case 1: default:
					questBoss = new FetidRat();
					txt_quest = Messages.get(this, "rat_1", Dungeon.hero.givenName()); break;
				case 2:
					questBoss = new GnollTrickster();
					txt_quest = Messages.get(this, "gnoll_1", Dungeon.hero.givenName()); break;
				case 3:
					questBoss = new GreatCrab();
					txt_quest = Messages.get(this, "crab_1", Dungeon.hero.givenName()); break;
			}

			questBoss.pos = Dungeon.depth.randomRespawnCell();

			if (questBoss.pos != -1) {
				GameScene.add(questBoss);
				GameScene.show(new WndQuest(this, txt_quest));
				Quest.given = true;
				Journal.add(Journal.Feature.GHOST);
			}

		}
		return false;
	}

	{
		immunities.add(Paralysis.class);
		immunities.add(Roots.class);
	}


	public static class Quest {

		private static boolean spawned;

		private static int type;

		private static boolean given;
		public static boolean processed;

		private static int depth;

		public static Artifact artifact;
		public static Ring ring;
		public static Egg pet;

		public static void reset() {
			spawned = false;

			artifact = null;
			ring = null;
			pet = null;
		}

		private static final String NODE = "sadGhost";

		private static final String SPAWNED = "spawned";
		private static final String TYPE = "type";
		private static final String GIVEN = "given";
		private static final String PROCESSED = "processed";
		private static final String DEPTH = "depth";
		private static final String ARTIFACT = "artifact";
		private static final String RING = "ring";
		private static final String PET = "pet";

		public static void storeInBundle(Bundle bundle) {

			Bundle node = new Bundle();

			node.put(SPAWNED, spawned);

			if (spawned) {

				node.put(TYPE, type);

				node.put(GIVEN, given);
				node.put(DEPTH, depth);
				node.put(PROCESSED, processed);

				node.put(ARTIFACT, artifact);
				node.put(RING, ring);
				node.put(PET, pet);
			}

			bundle.put(NODE, node);
		}

		public static void restoreFromBundle(Bundle bundle) {

			Bundle node = bundle.getBundle(NODE);

			if (!node.isNull() && (spawned = node.getBoolean(SPAWNED))) {

				type = node.getInt(TYPE);
				given = node.getBoolean(GIVEN);
				processed = node.getBoolean(PROCESSED);

				depth = node.getInt(DEPTH);

				artifact = (Artifact) node.get(ARTIFACT);
				ring = (Ring) node.get(RING);
				pet = (Egg) node.get(PET);
			} else {
				reset();
			}
		}

		public static void spawn(SewerLevel level) {
			if (!spawned && Dungeon.dungeondepth > 1
					&& Random.Int(5 - Dungeon.dungeondepth) == 0) {

				Ghost ghost = new Ghost();
				do {
					ghost.pos = level.randomRespawnCell();
				} while (ghost.pos == -1);
				level.mobs.add(ghost);
				////Actor.occupyCell(ghost);

				spawned = true;
				// dungeon depth determines type of quest.
				// depth2=fetid rat, 3=gnoll trickster, 4=great crab
				type = Dungeon.dungeondepth - 1;

				given = false;
				processed = false;
				depth = Dungeon.dungeondepth;

				do {
					artifact = Generator.randomArtifact();
				} while (artifact.cursed);
				do {
					ring = Generator.randomRing();
				} while (ring.cursed);

				pet = (Egg) Generator.random(Generator.Category.BASEPET);
				artifact.identify();
				ring.identify();
			}
		}

		public static void process() {
			if (spawned && given && !processed && (depth == Dungeon.dungeondepth)) {
				GLog.n(Messages.get(Ghost.class, "find_me"));
				for (Mob m : Dungeon.depth.mobs){
					if (m instanceof Ghost)
						m.beckon(Dungeon.hero.pos);
				}
				Sample.INSTANCE.play(Assets.SND_GHOST);
				processed = true;
				Generator.Category.ARTIFACT.probs[10] = 1; // flags the dried
															// rose as
															// spawnable.
			}
		}

		public static void complete() {
			artifact = null;
			ring = null;
			pet = null;

			Journal.remove(Journal.Feature.GHOST);
		}
		
		public static boolean completed(){
			return spawned && processed;
		}		
	}
	
}

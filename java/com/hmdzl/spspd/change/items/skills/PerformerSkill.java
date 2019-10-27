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
package com.hmdzl.spspd.change.items.skills;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.HighVoice;
import com.hmdzl.spspd.change.actors.buffs.Rhythm;
import com.hmdzl.spspd.change.actors.buffs.Rhythm2;
import com.hmdzl.spspd.change.actors.buffs.WarGroove;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.artifacts.Artifact;
import com.hmdzl.spspd.change.items.rings.Ring;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.windows.WndBag;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.watabou.noosa.audio.Sample;

public class PerformerSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "performer cloak";
		image = ItemSpriteSheet.ARMOR_PERFORMER;
	}

	@Override
	public void doSpecial() {

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect(mob, Charm.class,Charm.durationFactor(mob)*5f).object = curUser.id();
				mob.sprite.centerEmitter().start(Speck.factory(Speck.HEART),0.2f, 5);
				Buff.affect(mob, Amok.class, 10f);
				Buff.prolong(mob, Haste.class, 5f);
				Buff.prolong(mob, ArmorBreak.class, 20f).level(50);
			}
		}
		charge += 20;
		Buff.affect(curUser, DefenceUp.class,10).level(25);
		Buff.affect(curUser, AttackUp.class,10).level(25);
        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}
	
	@Override
	public void doSpecial2() {

		int DMG = Dungeon.hero.lvl;

		if (curUser.buff(Rhythm.class) != null) {
			DMG = (int)(DMG*1.5);
			Buff.detach(curUser, Rhythm.class);
		}
		if (curUser.buff(Rhythm2.class) != null) {
			DMG = (int)(DMG*1.5);
			Buff.detach(curUser, Rhythm2.class);
		}
		if (curUser.buff(WarGroove.class) != null) {
			DMG = (int)(DMG*1.5);
			Buff.detach(curUser, WarGroove.class);
		}

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos]) {
				mob.sprite.centerEmitter().start(Speck.factory(Speck.HEART),0.2f, 5);
				mob.damage(Math.min(DMG,mob.HP - 10),this);
				Buff.prolong(mob, Blindness.class, 10f);
			}
		}
		charge += 20;

        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	@Override
	public void doSpecial3() {
		charge += 20;
		GameScene.selectItem(itemSelector, WndBag.Mode.TRANMSUTABLE, Messages.get(PerformerSkill.class, "prompt"));
	}

	@Override
	public void doSpecial4() {

		charge +=10;

		Buff.affect(curUser, HighVoice.class,100);
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			curUser = Dungeon.hero;
			Item result;
			if (item != null) {
				if (item instanceof MeleeWeapon) {
					result = changeWeapon((MeleeWeapon) item);
				} else if (item instanceof Armor) {
					result = changeArmor((Armor) item);
				} else if (item instanceof Ring) {
					result = changeRing((Ring) item);
				} else if (item instanceof Wand) {
					result = changeWand((Wand) item);
				} else if (item instanceof Artifact) {
					result = changeArtifact((Artifact) item);
				} else {
					result = null;
				}
				item.detach(Dungeon.hero.belongings.backpack);
				Dungeon.level.drop(result, Dungeon.hero.pos).sprite.drop();
			}
		}
	  };
		private MeleeWeapon changeWeapon(MeleeWeapon w) {

			MeleeWeapon n;
			do {
				n = (MeleeWeapon) Generator.random(Generator.Category.MELEEWEAPON);
			} while (n.getClass() == w.getClass());

			n.level = 0;

			int level = w.level;
			if (level > 0) {
				n.upgrade(level);
			} else if (level < 0) {
				n.degrade(-level);
			}

			n.enchantment = w.enchantment;
			n.reinforced = w.reinforced;
			n.levelKnown = w.levelKnown;
			n.cursedKnown = w.cursedKnown;
			n.cursed = w.cursed;

			return n;

		}

		private Armor changeArmor(Armor r) {
			Armor n;
			do {
				n = (Armor) Generator.random(Generator.Category.ARMOR);
			} while (n.getClass() == r.getClass());

			n.level = 0;

			int level = r.level;
			if (level > 0) {
				n.upgrade(level);
			} else if (level < 0) {
				n.degrade(-level);
			}
			n.glyph = r.glyph;
			n.reinforced = r.reinforced;
			n.levelKnown = r.levelKnown;
			n.cursedKnown = r.cursedKnown;
			n.cursed = r.cursed;

			return n;
		}


		private Ring changeRing(Ring r) {
			Ring n;
			do {
				n = (Ring) Generator.random(Generator.Category.RING);
			} while (n.getClass() == r.getClass());

			n.level = 0;

			int level = r.level;
			if (level > 0) {
				n.upgrade(level);
			} else if (level < 0) {
				n.degrade(-level);
			}
			n.reinforced = r.reinforced;
			n.levelKnown = r.levelKnown;
			n.cursedKnown = r.cursedKnown;
			n.cursed = r.cursed;

			return n;
		}

		private Artifact changeArtifact(Artifact a) {
			Artifact n;
			do {
				n = (Artifact) Generator.random(Generator.Category.ARTIFACT);
			} while (n.getClass() == a.getClass());

			if (n != null) {
				n.cursedKnown = a.cursedKnown;
				n.cursed = a.cursed;
				n.levelKnown = a.levelKnown;
				n.transferUpgrade(a.visiblyUpgraded());
			}

			return n;
		}

		private Wand changeWand(Wand w) {

			Wand n;
			do {
				n = (Wand) Generator.random(Generator.Category.WAND);
			} while (n.getClass() == w.getClass());

			n.level = 0;
			n.updateLevel();
			n.upgrade(w.level);

			n.reinforced = w.reinforced;
			n.levelKnown = w.levelKnown;
			n.cursedKnown = w.cursedKnown;
			n.cursed = w.cursed;

			return n;
		}

}


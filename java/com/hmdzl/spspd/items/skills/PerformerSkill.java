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
package com.hmdzl.spspd.items.skills;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.HighVoice;
import com.hmdzl.spspd.actors.buffs.LearnSkill;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;
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
				Buff.affect(mob, Charm.class,5f).object = curUser.id();
				mob.sprite.centerEmitter().start(Speck.factory(Speck.HEART),0.2f, 5);
				Buff.affect(mob, Amok.class, 10f);
				Buff.prolong(mob, HasteBuff.class, 5f);
				Buff.prolong(mob, ArmorBreak.class, 20f).level(50);
			}
		}
		PerformerSkill.charge += 20;
		Buff.affect(curUser, DefenceUp.class,10).level(25);
		Buff.affect(curUser, AttackUp.class,10).level(25);
		Buff.affect(curUser, HighVoice.class,100);
        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}
	
	@Override
	public void doSpecial2() {

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos]) {
				int dmg = (int) (Dungeon.hero.lvl * (1 + 0.1 * Dungeon.hero.magicSkill())) ;
				mob.sprite.centerEmitter().start(Speck.factory(Speck.HEART),0.2f, 5);
				Buff.prolong(mob, Blindness.class, 10f);
				Buff.prolong(mob, Slow.class, 10f);
				mob.damage(Math.min(mob.HP-10,mob.HT/10 + dmg), DamageType.ENERGY_DAMAGE);
			}
		}

		Buff.affect(curUser, HighVoice.class,100);
		PerformerSkill.charge += 10;

        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	@Override
	public void doSpecial3() {
		GameScene.selectItem(itemSelector, WndBag.Mode.TRANMSUTABLE, Messages.get(PerformerSkill.class, "prompt"));
	}

	@Override
	public void doSpecial4() {

		PerformerSkill.charge +=20;

		Buff.affect(curUser, HighVoice.class,100);
		Buff.affect(curUser,LearnSkill.class).set(50);
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
				Buff.affect(curUser, HighVoice.class,100);
				PerformerSkill.charge += 20;
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


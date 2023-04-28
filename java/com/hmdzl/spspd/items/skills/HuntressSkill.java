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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Needling;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.Mtree;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfArmor;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.reward.BoundReward;
import com.hmdzl.spspd.items.summon.FairyCard;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class HuntressSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "huntress cloak";
		image = ItemSpriteSheet.SKILL_HUNTER;
	}

	@Override
	public void doSpecial() {
		
		HuntressSkill.charge +=15;

        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.affect(curUser, TargetShoot.class,50f);
		Buff.affect(curUser, Needling.class,50f);
		switch(Random.Int(2)){
			case 0:
		    Buff.affect(curUser, FireImbue.class).set(50f);
			break;
			case 1:
			Buff.affect(curUser, EarthImbue.class,50);
			break;
		}
	}

	@Override
	public void doSpecial2() {
		KindOfWeapon weapon = hero.belongings.weapon;
		KindOfArmor armor = hero.belongings.armor;
		Item item1 = hero.belongings.misc1;
		Item item2 = hero.belongings.misc2;
		Item item3 = hero.belongings.misc3;
		if (weapon != null && !weapon.reinforced){
			weapon.reinforced = true;
		}else if (armor != null && !armor.reinforced) {
			armor.reinforced = true;
		} else if (item1 != null && !item1.reinforced) {
			item1.reinforced = true;
		} else if (item2 != null && !item2.reinforced) {
			item2.reinforced = true;
		} else if (item3 != null && !item3.reinforced) {
			item3.reinforced = true;
		}

		Dungeon.level.drop(new BoundReward(), hero.pos).sprite.drop(hero.pos);
		HuntressSkill.charge +=20;

        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);


		
	}

	@Override
	public void doSpecial3() {

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Dungeon.level.drop(Generator.random(Generator.Category.BERRY), hero.pos).sprite.drop(hero.pos);
		ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = curUser.pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null && (Level.passable[p] || Level.avoid[p])) {
				spawnPoints.add(p);
			}
		}

		if (spawnPoints.size() > 0) {
			FairyCard.Fairy fairy = new FairyCard.Fairy();
			fairy.HP = 10;
			fairy.pos = Random.element(spawnPoints);
			GameScene.add(fairy);
			HuntressSkill.charge +=15;
		} else {
			GLog.i( Messages.get(this, "no_space") );
		}
	}
	@Override
	public void doSpecial4() {

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos] && (Level.distance(curUser.pos, mob.pos) <= 10)) {
				Buff.prolong(mob, Roots.class, 8);
				Buff.affect(mob, GrowSeed.class).set(10f);
			}
		}

			Mtree tree = new Mtree();
			tree.state = tree.WANDERING;
			tree.pos = hero.pos;
			GameScene.add(tree);
			tree.beckon(hero.pos);

		HuntressSkill.charge += 20;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}

}
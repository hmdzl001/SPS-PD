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

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Needling;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.Mtree;
import com.hmdzl.spspd.items.summon.FairyCard;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.noosa.audio.Sample;

public class HuntressSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		//name = "huntress cloak";
		image = ItemSpriteSheet.ARMOR_HUNTRESS;
	}

	@Override
	public void doSpecial() {

		/*Item proto = new Shuriken();

		for (Mob mob : Dungeon.level.mobs) {
			if (Level.fieldOfView[mob.pos]) {

				Callback callback = new Callback() {
					@Override
					public void call() {
						curUser.attack(targets.get(this));
						targets.remove(this);
						if (targets.isEmpty()) {
							curUser.spendAndNext(curUser.attackDelay());
						}
					}
				};

				((MissileSprite) curUser.sprite.parent
						.recycle(MissileSprite.class)).reset(curUser.pos,
						mob.pos, proto, callback);

				targets.put(callback, mob);
			}
		}

		if (targets.size() == 0) {
			GLog.w(TXT_NO_ENEMIES);
			return;
		}*/

		//curUser.HP -= (curUser.HP / 2);
		
		HuntressSkill.charge +=10;

        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		
		switch(Random.Int(4)){
			case 0:
		    Buff.affect(curUser, FireImbue.class).set(30f);
			break;
			case 1:
			Buff.affect(curUser, EarthImbue.class,30);
			break;
			case 2:
		    Buff.affect(curUser, Awareness.class,10);
			break;
			case 3:
	        Buff.affect(curUser, BerryRegeneration.class).level(curUser.HP/2);
			break;
		}
	}

	@Override
	public void doSpecial2() {

		HuntressSkill.charge +=10;

        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

		Buff.affect(curUser, TargetShoot.class,20f);

		Buff.affect(curUser, Needling.class,20f);
		
	}

	@Override
	public void doSpecial3() {

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

		ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
		for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
			int p = curUser.pos + PathFinder.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null && (Level.passable[p] || Level.avoid[p])) {
				spawnPoints.add(p);
			}
		}

		if (spawnPoints.size() > 0) {
			FairyCard.Fairy fairy = new FairyCard.Fairy();
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
				Buff.affect(mob, GrowSeed.class);
			}
		}
		for (int i = 0; i < (Dungeon.hero.lvl)/15; i++){
			Mtree tree = new Mtree();
			tree.state = tree.WANDERING;
			tree.pos = Dungeon.level.randomRespawnCellFish();
			GameScene.add(tree);
			tree.beckon(Dungeon.hero.pos);
		}


		HuntressSkill.charge += 15;

		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

	}

}
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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.damageblobs.FireEffectDamage;
import com.hmdzl.spspd.actors.blobs.damageblobs.IceEffectDamage;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SuccubusSprite;
import com.watabou.utils.Random;

public class FireSuccubus extends Succubus {

	{
		spriteClass = SuccubusSprite.class;

		HP = HT = 150+(adj(0)*Random.NormalIntRange(5, 7));
		evadeSkill = 30+adj(1);
		viewDistance = Light.DISTANCE;

		EXP = 14;
		maxLvl = 35;

		properties.add(Property.DEMONIC);
	}

	@Override
	public boolean act() {
		for (int i = 0; i < Floor.NEIGHBOURS9.length; i++) {
			GameScene.add(Blob.seed(pos + Floor.NEIGHBOURS9[i], 2,
					FireEffectDamage.class));
		}
		return super.act();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 25+adj(0));
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		Buff.affect(this, DefenceUp.class,10f).level(30);
		GameScene.add(Blob.seed(enemy.pos, 3, IceEffectDamage.class));
		return super.attackProc(enemy, damage);
	}

	@Override
	public void damage(int dmg, Object src) {
		if (dmg> HT/6) {
			dmg =(int)Math.max(HT/6,1);
		}
		Buff.affect(this, AttackUp.class,10f).level(30);
		super.damage(dmg,src);

	}

	@Override
	public int hitSkill(Char target) {
		return 42+adj(1);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 10);
	}

    {
		immunities.add(Sleep.class);
		immunities.add(FireEffectDamage.class);
	}

}

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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.scrolls.ScrollOfDummy;
import com.hmdzl.spspd.items.scrolls.ScrollOfLullaby;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.items.wands.WandOfCharm;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.SuccubusSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Succubus extends Mob {

	private static final int BLINK_DELAY = 5;

	private int delay = 0;

	{
		spriteClass = SuccubusSprite.class;

		HP = HT = 110+(adj(0)*Random.NormalIntRange(5, 7));
		evadeSkill = 25+adj(1);
		viewDistance = Light.DISTANCE;

		EXP = 12;
		maxLvl = 35;

		loot = new ScrollOfLullaby();
		lootChance = 0.05f;
		
		lootOther = new MysteryMeat();
		lootChanceOther = 0.1f; // by default, see die()
		
		properties.add(Property.DEMONIC);
	}

	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new ScrollOfDummy(),new WandOfCharm());
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 25+adj(0));
	}

	@Override
	public int attackProc(Char enemy, int damage) {

		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Charm.class, Random.IntRange(3, 7)).object = id();
			enemy.sprite.centerEmitter().start(Speck.factory(Speck.HEART),
					0.2f, 5);
			Sample.INSTANCE.play(Assets.SND_CHARMS);
		}
		
		int reg = Math.min(damage, HT - HP);

		if (reg > 0) {
			HP += reg;
			sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
		}		

		return damage;
	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target] && Level.distance(pos, target) > 2
				&& delay <= 0 &&(buff(Silent.class) == null) ) {

			blink(target);
			spend(-1 / speed());
			return true;

		} else {

			delay--;
			return super.getCloser(target);

		}
	}

	private void blink(int target) {

		Ballistica route = new Ballistica( pos, target, Ballistica.PROJECTILE);
		int cell = route.collisionPos;
		
		//can't occupy the same cell as another char, so move back one.
		if (Actor.findChar( cell ) != null && cell != this.pos)
			cell = route.path.get(route.dist-1);

		if (Level.avoid[ cell ]){
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int n : Level.NEIGHBOURS8) {
				cell = route.collisionPos + n;
				if (Level.passable[cell] && Actor.findChar( cell ) == null) {
					candidates.add( cell );
				}
			}
			if (candidates.size() > 0)
				cell = Random.element(candidates);
			else {
				delay = BLINK_DELAY;
				return;
			}
		}
		
		ScrollOfTeleportation.appear( this, cell );
		
		delay = BLINK_DELAY;
	}

	@Override
	public int hitSkill(Char target) {
		return 40+adj(1);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 10);
	}

    {
		immunities.add(Sleep.class);
	}

}

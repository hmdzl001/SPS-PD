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
package com.hmdzl.spspd.change.actors.mobs;

import java.util.HashSet;

import com.hmdzl.spspd.change.items.eggs.Egg;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Badges.Badge;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.GooWarn;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.ActiveMrDestructo;
import com.hmdzl.spspd.change.items.eggs.EasterEgg;
import com.hmdzl.spspd.change.items.journalpages.Sokoban1;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.SewerBossLevel;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.GooSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Goo extends Mob {
	{
		HP = HT = 350; 
		EXP = 10;
		defenseSkill = 12;
		spriteClass = GooSprite.class;

		loot = new ActiveMrDestructo();
		lootChance = 0.2f;
		
		lootOther = Dungeon.getMonth() == 9 ? new EasterEgg() : new Egg();
		lootChanceOther = 1f;
		
		lootThird = Dungeon.getMonth() != 9 ? new Egg() : new EasterEgg();
		lootChanceThird = 0.05f;

		properties.add(Property.ELEMENT);
		properties.add(Property.BOSS);
	}

	private int pumpedUp = 0;
	private int goosAlive = 0;

	@Override
	public int damageRoll() {
		if (pumpedUp > 0) {
			pumpedUp = 0;
			for (int i = 0; i < Level.NEIGHBOURS9DIST2.length; i++) {
				int j = pos + Level.NEIGHBOURS9DIST2[i];
				if (j >= 0 && j <= 1023 && Level.passable[j])
					CellEmitter.get(j).burst(ElmoParticle.FACTORY, 10);
			}
			Sample.INSTANCE.play(Assets.SND_BURNING);
			return Random.NormalIntRange(5, 30);
		} else {
			return Random.NormalIntRange(2, 12);
		}
	}

	@Override
	public int attackSkill(Char target) {
		return (pumpedUp > 0) ? 30 : 15;
	}

	@Override
	public int dr() {
		return 2;
	}

	@Override
	public boolean act() {

		if (Level.water[pos] && HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP++;
		}

		return super.act();
	}

	
	@Override
	protected boolean canAttack(Char enemy) {
		return (pumpedUp > 0) ? distance(enemy) <= 2 : super.canAttack(enemy);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Ooze.class);
			enemy.sprite.burst(0x000000, 5);
		}

		if (pumpedUp > 0) {
			Camera.main.shake(3, 0.2f);
		}
				
		return damage;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		if (pumpedUp == 1) {
			((GooSprite) sprite).pumpUp();
			for (int i = 0; i < Level.NEIGHBOURS9DIST2.length; i++) {
				int j = pos + Level.NEIGHBOURS9DIST2[i];
				if (j >= 0 && j <= 1023 && Level.passable[j])
					GameScene.add(Blob.seed(j, 2, GooWarn.class));
			}
			pumpedUp++;

			spend(attackDelay());

			return true;
		} else if (pumpedUp >= 2 || Random.Int(3) > 0) {

			boolean visible = Dungeon.visible[pos];

			if (visible) {
				if (pumpedUp >= 2) {
					((GooSprite) sprite).pumpAttack();
				} else
					sprite.attack(enemy.pos);
			} else {
				attack(enemy);
			}

			spend(attackDelay());

			return !visible;

		} else {

			pumpedUp++;

			((GooSprite) sprite).pumpUp();

			for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
				int j = pos + Level.NEIGHBOURS9[i];
				GameScene.add(Blob.seed(j, 2, GooWarn.class));

			}

			if (Dungeon.visible[pos]) {
				sprite.showStatus(CharSprite.NEGATIVE, "!!!");
				GLog.n(Messages.get(this,"atk"));
			}

			spend(attackDelay());

			return true;
		}
	}

	@Override
	public boolean attack(Char enemy) {
		boolean result = super.attack(enemy);
		pumpedUp = 0;
		return result;
	}

	@Override
	protected boolean getCloser(int target) {
		pumpedUp = 0;
		return super.getCloser(target);
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

		for (Mob mob : Dungeon.level.mobs) {
			
			if (mob instanceof Goo || mob instanceof PoisonGoo){
				   goosAlive++;
				 }
			
			}
			
			 if(goosAlive==0){
			
			((SewerBossLevel) Dungeon.level).unseal();

			GameScene.bossSlain();
			Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();	
			Badges.validateBossSlain();
		}
			 
		 Badges.Badge badgeToCheck = null;
				switch (Dungeon.hero.heroClass) {
				case WARRIOR:
					badgeToCheck = Badge.MASTERY_WARRIOR;
					break;
				case MAGE:
					badgeToCheck = Badge.MASTERY_MAGE;
					break;
				case ROGUE:
					badgeToCheck = Badge.MASTERY_ROGUE;
					break;
				case HUNTRESS:
					badgeToCheck = Badge.MASTERY_HUNTRESS;
					break;
		}
				
	
		Dungeon.level.drop(new Sokoban1(), pos).sprite.drop();
		

		yell("glurp... glurp...");
	}
  
	protected boolean spawnedMini = false;
	
	@Override
	public void notice() {
		super.notice();
		yell("GLURP-GLURP!");
		if (!spawnedMini){
	    PoisonGoo.spawnAround(pos);
	    spawnedMini = true;
		}
	}

	private final String PUMPEDUP = "pumpedup";

	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		bundle.put(PUMPEDUP, pumpedUp);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {

		super.restoreFromBundle(bundle);

		pumpedUp = bundle.getInt(PUMPEDUP);
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

	static {
		IMMUNITIES.add(Roots.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	}

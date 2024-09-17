/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.HiddenShadow;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.SpeedUp;
import com.hmdzl.spspd.actors.buffs.WatchOut;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.GooSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class CopyBall extends Item {

	public static final String AC_USE = "USE";
	private static final float TIME_TO_DIG = 1f;

	{
		image = ItemSpriteSheet.SLIME_BALL;
		defaultAction = AC_USE;
		
		unique = true;
		 
	}
	
	public final int fullCharge = 50;
	public int charge = 0;
	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}	
		@Override
	public int price() {
		return 30 * quantity;
	}
	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (charge >= 10) {
			actions.add(AC_USE);
		}
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {		
      if( action.equals( AC_USE ) ) {
          curUser = hero;
		  if (charge < 10) {
			  GLog.i(Messages.get(CopyBall.class, "break"));
		  } else GameScene.selectCell(shooter);
		} else super.execute(hero, action);


	}

	@Override
	public String status() {
		return Messages.format("%d", charge /10);
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(CopyBall.class, "charge",charge,fullCharge);
		return info;	
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	
	
	private int targetPos;
	
	public CopyBallAmmo Ammo(){
		return new CopyBallAmmo();
	}
	
	public class CopyBallAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.SLIME_BALL;
			ACU = 1000;
		}

		public int damageRoll(Hero owner) {
			return 0;
		}

		@Override
		protected void onThrow( int cell ) {
			Char enemy = Actor.findChar( cell );
			if (enemy == null || enemy == curUser) {
				parent = null;
				Splash.at( cell, 0xCC99FFFF, 1 );
				new DungeonBomb().explode(cell);
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
				}
			}
		}

		@Override
		public void proc(Char attacker, Char defender, int damage) {
			defender.damage(attacker.damageRoll()+10, Hunger.class,3);
			CopyBall.shattercopy(null,defender.pos);

			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			CopyBall.this.targetPos = cell;
			charge-=10;
			super.cast(user, dst);
		}

	}
	private CellSelector.Listener shooter = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				Ammo().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(CopyBall.class, "prompt");
		}
	};

	public static void shattercopy(Char owner, int pos) {

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_SHATTER);
			Splash.at(pos, 0xffd500, 5);
		}

		int newPos = pos;
		if (Actor.findChar(pos) != null) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Floor.passable;

			for (int n : Floor.NEIGHBOURS4) {
				int c = pos + n;
				if (passable[c] && Actor.findChar(c) == null) {
					candidates.add(c);
				}
			}

			newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
		}

		if (newPos != -1 ) {
				CopyBall.SlimeS slime = new CopyBall.SlimeS();
			    slime.spawn();
			    slime.HP = (int)(Dungeon.hero.HT/3);
			    slime.pos = newPos;

				GameScene.add(slime);
				Actor.addDelayed(new Pushing(slime, pos, newPos), -1f);

			    slime.sprite.alpha(0);
			    slime.sprite.parent.add(new AlphaTweener(slime.sprite, 1, 0.15f));

				Sample.INSTANCE.play(Assets.SND_BEE);

		} else new DungeonBomb().explode(pos);
	}

	public static class SlimeS extends NPC {

		{
			//name = "Slime S";
			spriteClass = GooSprite.class;

			viewDistance = 6;
			ally=true;
			flying = true;
			state = WANDERING;
		}

		public void spawn() {
			HT = 100;
			//evadeSkill = Dungeon.hero.evadeSkill;
		}
		@Override
		public int hitSkill(Char target) {
			return Dungeon.hero.hitSkill;
		}

		public int defenseSkill(Char enemy) {
			return Dungeon.hero.evadeSkill;
		}

		@Override
		public int damageRoll() {
			return 0;
		}



		@Override
		public int attackProc(Char enemy, int damage) {
			enemy.damage((int)(Dungeon.hero.damageRoll()/3), Hunger.class,3);
			return damage;
		}

		@Override
		protected boolean canAttack(Char enemy) {
			return Floor.distance( pos, enemy.pos ) <= 2 ;
		}

		@Override
		public void add( Buff buff ) {
			if (buff instanceof AttackUp ||
					buff instanceof DefenceUp ||
					buff instanceof ShieldArmor ||
					buff instanceof MagicArmor ||
					buff instanceof HasteBuff ||
					buff instanceof SpeedUp ||
					buff instanceof HasteBuff||
					buff instanceof HiddenShadow ||
					buff instanceof WatchOut) {
				super.add(buff);
			} else {

			}
			//in other words, can't be directly affected by buffs/debuffs.
		}

		@Override
		protected boolean getCloser(int target) {
			if (state == WANDERING
					|| Floor.distance(target, Dungeon.hero.pos) > 6)
				this.target = target = Dungeon.hero.pos;
			return super.getCloser(target);
		}
		@Override
		protected Char chooseEnemy() {
			if (enemy == null || !enemy.isAlive() || state == WANDERING) {

				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.depth.mobs) {
					if (mob.hostile && Floor.fieldOfView[mob.pos]
							&& mob.state != mob.PASSIVE) {
						enemies.add(mob);
					}
				}
				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}
			return enemy;
		}

		@Override
		public boolean interact() {
			if (Floor.passable[pos] || Dungeon.hero.flying) {
				int curPos = pos;

				moveSprite(pos, Dungeon.hero.pos);
				move(Dungeon.hero.pos);

				Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
				Dungeon.hero.move(curPos);

				Dungeon.hero.spend(1 / Dungeon.hero.speed());
				Dungeon.hero.busy();
				return true;
			} else {
				return false;
			}
		}

	}

}

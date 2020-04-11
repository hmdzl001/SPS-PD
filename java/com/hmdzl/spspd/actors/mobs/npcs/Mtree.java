	package com.hmdzl.spspd.actors.mobs.npcs;


	import com.hmdzl.spspd.Dungeon;
	import com.hmdzl.spspd.actors.Char;
	import com.hmdzl.spspd.actors.buffs.Buff;
	import com.hmdzl.spspd.actors.buffs.GrowSeed;
	import com.hmdzl.spspd.actors.buffs.Poison;
	import com.hmdzl.spspd.actors.mobs.Mob;
	import com.hmdzl.spspd.levels.Level;
    import com.hmdzl.spspd.sprites.PlantKingSprite;
	import com.watabou.utils.Random;

	import java.util.HashSet;

	public class Mtree extends NPC {

		{
			//name = "Mtree";
			spriteClass = PlantKingSprite.class;

			HP = 10;
			HT = 10;
			
			viewDistance = 6;
			ally=true;
			flying = false;
			state = WANDERING;
		}


		@Override
		public int hitSkill(Char target) {
			return 40;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(10, 30);
		}

		@Override
		protected boolean canAttack(Char enemy) {
			return super.canAttack(enemy);
		}
		
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0) {
			Buff.affect(enemy, GrowSeed.class);
			destroy();
		}

		return damage;
	}			

		@Override
		protected boolean getCloser(int target) {
			if (state == WANDERING
					|| Level.distance(target, Dungeon.hero.pos) > 6)
				this.target = target = Dungeon.hero.pos;
			return super.getCloser(target);
		}
		@Override
		protected Char chooseEnemy() {
			if (enemy == null || !enemy.isAlive() || state == WANDERING) {

				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.level.mobs) {
					if (mob.hostile && Level.fieldOfView[mob.pos]
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
			if (Dungeon.level.passable[pos] || Dungeon.hero.flying) {
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

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Poison.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}

	}
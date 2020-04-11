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
package com.hmdzl.spspd.actors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.BloodAngry;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Cold;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.DamageUp;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Disarm;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.FrostImbue;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Haste;
import com.hmdzl.spspd.actors.buffs.HighAttack;
import com.hmdzl.spspd.actors.buffs.HighVoice;
import com.hmdzl.spspd.actors.buffs.Hot;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.Needling;
import com.hmdzl.spspd.actors.buffs.ParyAttack;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.buffs.Rhythm2;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.StoneIce;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.Speed;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.buffs.BloodImbue;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.effects.Lightning;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire2;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

public abstract class Char extends Actor {

	protected static final String TXT_KILL = "%s killed you...";

	public int pos = 0;

	public CharSprite sprite;

	public String name = "mob";

	public int HT;
	public int HP;

	protected float baseSpeed = 1;

	public int paralysed = 0;
	public boolean rooted = false;
	public boolean flying = false;
	public int invisible = 0;

	public int viewDistance = 8;

	private HashSet<Buff> buffs = new HashSet<Buff>();

	@Override
	protected boolean act() {
		Dungeon.level.updateFieldOfView(this);
		return false;
	}

	private static final String POS = "pos";
	private static final String TAG_HP = "HP";
	private static final String TAG_HT = "HT";
	private static final String BUFFS = "buffs";

	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		bundle.put(POS, pos);
		bundle.put(TAG_HP, HP);
		bundle.put(TAG_HT, HT);
		bundle.put(BUFFS, buffs);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {

		super.restoreFromBundle(bundle);

		pos = bundle.getInt(POS);
		HP = bundle.getInt(TAG_HP);
		HT = bundle.getInt(TAG_HT);

		for (Bundlable b : bundle.getCollection(BUFFS)) {
			if (b != null) {
				((Buff) b).attachTo(this);
			}
		}
	}

	public boolean attack(Char enemy) {

		boolean visibleFight = Dungeon.visible[pos]
				|| Dungeon.visible[enemy.pos];

		if (hit(this, enemy, false)) {

			/*if (visibleFight) {
				GLog.i(TXT_HIT, name, enemy.name);
			}*/

			// FIXME
			int dr = enemy.drRoll();

			if (this instanceof Hero){
				Hero h = (Hero)this;
				if ((h.belongings.weapon instanceof MissileWeapon
						&& h.subClass == HeroSubClass.SNIPER )||
						( h.heroClass == HeroClass.FOLLOWER && Dungeon.skins == 2) ){
					dr = 0;
				}
			}			
			
			int dmg = damageRoll();

			AttackUp atkup = buff(AttackUp.class);
			if (atkup != null) {
				dmg *=(1f+atkup.level()*0.01f);
			}
			
			AttackDown atkdown = buff(AttackDown.class);
			if (atkdown != null) {
				dmg *=(1f-atkdown.level()*0.01f);
			}			
			
			MechArmor marmor = buff(MechArmor.class);
			if (marmor != null) {
				dmg *=1.3f;
			}

			StoneIce si = buff(StoneIce.class);
		if ( si != null){
			dmg *= 1.4;
		}			
			
			DamageUp dmgup = buff(DamageUp.class);
			if (dmgup != null) {
				dmg +=dmgup.level();
				Buff.detach(this,DamageUp.class);
			}


			int effectiveDamage = Math.max(dmg - dr, 1);

			effectiveDamage = attackProc(enemy, effectiveDamage);
			effectiveDamage = enemy.defenseProc(this, effectiveDamage);

			if (visibleFight) {
				Sample.INSTANCE.play(Assets.SND_HIT, 1, 1,
						Random.Float(0.8f, 1.25f));
			}

			// If the enemy is already dead, interrupt the attack.
			// This matters as defence procs can sometimes inflict self-damage,
			// such as armor glyphs.
			if (!enemy.isAlive()) {
				return true;
			}

			// TODO: consider revisiting this and shaking in more cases.
			float shake = 0f;
			if (enemy == Dungeon.hero)
				shake = effectiveDamage / (enemy.HT / 4);

			if (shake > 1f)
				Camera.main.shake(GameMath.gate(1, shake, 5), 0.3f);

			enemy.damage(effectiveDamage, this);

			if (buff(FireImbue.class) != null)
				buff(FireImbue.class).proc(enemy);
			if (buff(EarthImbue.class) != null)
				buff(EarthImbue.class).proc(enemy);
			if (buff(BloodImbue.class) != null)
				buff(BloodImbue.class).proc(enemy);
			if (buff(FrostImbue.class) != null)
				buff(FrostImbue.class).proc(enemy);
			if (buff(Needling.class) != null)
				buff(Needling.class).proc(enemy);			

			enemy.sprite.bloodBurstA(sprite.center(), effectiveDamage);
			enemy.sprite.flash();

			if (!enemy.isAlive() && visibleFight) {
				if (enemy == Dungeon.hero) {
					if (Bestiary.isUnique(this)) {
						Dungeon.fail(Messages.format(ResultDescriptions.UNIQUE));
					} else {
						Dungeon.fail(Messages.format(ResultDescriptions.MOB));
					}

				} 
			}
			return true;

		} else {

			if (visibleFight) {
				String defense = enemy.defenseVerb();
				enemy.sprite.showStatus(CharSprite.NEUTRAL, defense);
				Sample.INSTANCE.play(Assets.SND_MISS);
			}

			return false;

		}
	}

	public static boolean hit(Char attacker, Char defender, boolean magic) {
		float acuRoll = Random.Float(attacker.hitSkill(defender));
		float defRoll = Random.Float(defender.evadeSkill(attacker));
		if (attacker.buff(Bless.class) != null) acuRoll *= 1.20f;
		if (defender.buff(Bless.class) != null) defRoll *= 1.20f;
		if (attacker.buff(Wet.class) != null) acuRoll *= 0.90f;
		if (defender.buff(Wet.class) != null) defRoll *= 0.90f;
		if (attacker.buff(Rhythm.class) != null) acuRoll *= 3.00f;
		if (defender.buff(Rhythm.class) != null) defRoll *= 1.50f;
		if (defender.buff(HighAttack.class) != null) defRoll *= 1.20f;
		return (magic ? acuRoll * 2 : acuRoll) >= defRoll;
	}

	public int hitSkill(Char target) {
		return 0;
	}

	public int evadeSkill(Char enemy) {
		return 0;
	}

	public String defenseVerb() {
		return Messages.get(this,"def_verb");
	}

	public int drRoll() {
		return 0;
	}

	public int damageRoll() {
		return 1;
	}

	public int attackProc(Char enemy, int damage) {
		if (buff(Shocked.class)!=null){
			Buff.detach(this,Shocked.class);
			Buff.affect(this, Disarm.class,5f);
            damage(this.HP/10,this);
			ArrayList<Lightning.Arc> arcs = new ArrayList<>();
			arcs.add(new Lightning.Arc(pos - Level.WIDTH, pos + Level.WIDTH));
			arcs.add(new Lightning.Arc(pos - 1, pos + 1));
			sprite.parent.add( new Lightning( arcs, null ) );
		}

		return damage;
	}

	public int defenseProc(Char enemy, int damage) {
		return damage;
	}

	public float speed() {
		if (buff(Cripple.class) != null){
			return baseSpeed * 0.5f;
		} else if (buff(Haste.class) != null){
			return baseSpeed * 2.5f;
		} else if (buff(Poison.class) != null) {
			return baseSpeed * 0.9f;
		} else if (buff(BloodAngry.class) != null) {
			return baseSpeed * 1.2f;
		} else if (buff(MechArmor.class) != null) {
			return baseSpeed * 1.5f;
		} else	if (buff(StoneIce.class) != null) {
			return baseSpeed * 0.8f;
		} else{
			return baseSpeed;
		}
		
		
	}

	public void damage(int dmg, Object src) {

		if (this.buff(Frost.class) != null) {
			Buff.detach(this, Frost.class);
			dmg = (int) Math.ceil(dmg *1.5);
		}
		if (this.buff(MagicalSleep.class) != null) {
			Buff.detach(this, MagicalSleep.class);
			dmg = (int) Math.ceil(dmg *1.5);
		}

		ArmorBreak ab = buff(ArmorBreak.class);
		if (buff(ArmorBreak.class) != null){
			dmg= (int) Math.ceil(dmg *(ab.level()*0.01+1));
		}
		
        ParyAttack paryatk = buff(ParyAttack.class);
		if (buff(ParyAttack.class) != null){
			dmg= (int) Math.ceil(dmg *Math.max((1-paryatk.level()*0.02),0.5));
		}		

		if (buff(Hot.class) != null){
			dmg = (int) Math.ceil(dmg * 1.2);
		}

		DefenceUp drup = buff(DefenceUp.class);
		if (buff(DefenceUp.class) != null) {
			dmg = (int) Math.ceil(dmg *(-drup.level()*0.01+1));
		}

		if (buff(GrowSeed.class) != null) {
			dmg = (int) Math.ceil(dmg *0.8);
		}

		ShieldArmor sarmor = buff(ShieldArmor.class);
		if (sarmor != null && !(src instanceof Hunger)) {
				dmg = sarmor.absorb(dmg);
		}

		MechArmor marmor = buff(MechArmor.class);
		if (marmor != null && !(src instanceof Hunger)) {
			dmg = marmor.absorb(dmg);
		}
		
		if (HP <= 0 || dmg < 0) {
			return;
		}

		Class<?> srcClass = src.getClass();
		if (immunities().contains(srcClass)) {
			dmg = 0;
		} else if (resistances().contains(srcClass)) {
			dmg = Random.IntRange(0, dmg);
		} else if (weakness().contains(srcClass)) {
			dmg = Random.IntRange(dmg+1, 2*dmg);
		}

		if (buff(Paralysis.class) != null) {
			if (Random.Int(dmg) >= Random.Int(HP)) {
				Buff.detach(this, Paralysis.class);
				if (Dungeon.visible[pos]) {
					GLog.i(Messages.get(this,"out_of_paralysis",name));
				}
			}
		}

		//GlassShield glass = buff(GlassShield.class);
		if (buff(GlassShield.class) != null) {
			if (dmg >= 10) {
				dmg = 10;
				Buff.detach(this, GlassShield.class);
			}
		}
       //if (dmg > HP){
		//Buff.detach(this,Corruption.class);}
		HP -= dmg;
		
		
		if (dmg > 0 || src instanceof Char) {
			sprite.showStatus(HP > HT / 2 ? CharSprite.WARNING
					: CharSprite.NEGATIVE, Integer.toString(dmg));
		}

		if (HP <= 0 || HT <= 0) {
			die(src);
		}
	}

	public void destroy() {
		HP = 0;
		Actor.remove(this);
		Actor.freeCell(pos);
	}

	public void die(Object src) {
		destroy();
		sprite.die();
	}

	public boolean isAlive() {
		return HP > 0 && HT > 0;
	}

	@Override
	protected void spend(float time) {

		float timeScale = 1f;
		if (buff(Slow.class) != null) {
			timeScale *= 0.67f;
		} else if (buff( Chill.class ) != null) {
			timeScale *= buff( Chill.class ).speedFactor();
		}
		if (buff(Speed.class) != null) {
			timeScale *= 1.5f;
		}
		/*if (buff(Haste.class) != null) {
			timeScale *= 1.5f;
		}*/
		if (buff(Cold.class) != null) {
			timeScale *= 0.9f;
		}
		
		if (buff(Tar.class) != null) {
			timeScale *= 0.8f;
		}
		if (buff(Burning.class) != null) {
			timeScale *= 1.25f;
		}

		if (buff(Rhythm2.class) != null) {
			timeScale *= 1.2f;
		}

		super.spend(time / timeScale);
	}

	public HashSet<Buff> buffs() {
		return buffs;
	}

	@SuppressWarnings("unchecked")
	public <T extends Buff> HashSet<T> buffs(Class<T> c) {
		HashSet<T> filtered = new HashSet<T>();
		for (Buff b : buffs) {
			if (c.isInstance(b)) {
				filtered.add((T) b);
			}
		}
		return filtered;
	}

	@SuppressWarnings("unchecked")
	public <T extends Buff> T buff(Class<T> c) {
		for (Buff b : buffs) {
			if (c.isInstance(b)) {
				return (T) b;
			}
		}
		return null;
	}

	public boolean isCharmedBy(Char ch) {
		int chID = ch.id();
		for (Buff b : buffs) {
			if (b instanceof Charm && ((Charm) b).object == chID) {
				return true;
			}
		}
		return false;
	}

	public void add(Buff buff) {

		buffs.add(buff);
		Actor.add(buff);

		if (sprite != null)
			switch(buff.type){
				case POSITIVE:
					sprite.showStatus(CharSprite.POSITIVE, buff.toString()); break;
				case NEGATIVE:
					sprite.showStatus(CharSprite.NEGATIVE, buff.toString());break;
				case NEUTRAL:
					sprite.showStatus(CharSprite.NEUTRAL, buff.toString()); break;
				case SILENT: default:
					break; //show nothing
			}

	}

	public void remove(Buff buff) {

		buffs.remove(buff);
		Actor.remove(buff);


	}

	public void remove(Class<? extends Buff> buffClass) {
		for (Buff buff : buffs(buffClass)) {
			remove(buff);
		}
	}

	@Override
	protected void onRemove() {
		for (Buff buff : buffs.toArray(new Buff[buffs.size()])) {
			buff.detach();
		}
	}
	
	public void updateSpriteState() {
		for (Buff buff : buffs) {
			/*if (buff instanceof Burning) {
				sprite.add(CharSprite.State.BURNING);
			} else if (buff instanceof Levitation) {
				sprite.add(CharSprite.State.LEVITATING);
			} else if (buff instanceof Invisibility
					|| buff instanceof CloakOfShadows.cloakStealth) {
				sprite.add(CharSprite.State.INVISIBLE);
			} else if (buff instanceof Stun || buff instanceof Shieldblock) {
				sprite.add(CharSprite.State.PARALYSED);
			} else if (buff instanceof Frost) {
				sprite.add(CharSprite.State.FROZEN);
			} else if (buff instanceof Light) {
				sprite.add(CharSprite.State.ILLUMINATED);
			}*/
			buff.fx( true );
		}
	}

	public int stealth() {
		return 0;
	}

	public int energybase() {
		return 0;
	}
	
	public void move(int step) {

		if (Level.adjacent(step, pos) && buff(Vertigo.class) != null) {
			step = pos + Level.NEIGHBOURS8[Random.Int(8)];
			if (!(Level.passable[step] || Level.avoid[step])
					|| Actor.findChar(step) != null)
				return;
		}

		if (Dungeon.level.map[pos] == Terrain.OPEN_DOOR) {
			Door.leave(pos);
		}

		pos = step;

		if (flying && Dungeon.level.map[pos] == Terrain.DOOR) {
			Door.enter(pos);
		}

		if (this != Dungeon.hero) {
			sprite.visible = Dungeon.visible[pos];
		}
	}

	public int distance(Char other) {
		return Level.distance(pos, other.pos);
	}

	public void onMotionComplete() {
		next();
	}

	public void onAttackComplete() {
		next();
	}

	public void onOperateComplete() {
		next();
	}

	private static final HashSet<Class<?>> EMPTY = new HashSet<Class<?>>();

	public HashSet<Class<?>> resistances() {
		return EMPTY;
	}

	public HashSet<Class<?>> immunities() {
		return EMPTY;
	}
	
	public HashSet<Class<?>> weakness() {
		return EMPTY;
	}

	 //protected final HashSet<Class> resistances = new HashSet<>();

	//returns percent effectiveness after resistances
	//TODO currently resistances reduce effectiveness by a static 50%, and do not stack.
	//public float resist( Class effect ){
		//HashSet<Class> resists = new HashSet<>(resistances);
	//	for (Property p : properties()){
	//		resists.addAll(p.resistances());
	//	}
	//	for (Buff b : buffs()){
	//		resists.addAll(b.resistances());
	//	}

	//	float result = 1f;
	//	for (Class c : resists){
	//		if (c.isAssignableFrom(effect)){
	//			result *= 0.5f;
	//		}
	//	}
	//	return result * RingOfElements.restore(this, effect);
	//}

	//protected final HashSet<Class> immunities = new HashSet<>();

	//public boolean isImmune(Class effect ){
		//HashSet<Class> immunes = new HashSet<>(immunities);
		//for (Property p : properties()){
			//immunes.addAll(p.immunities());
		//}
		//for (Buff b : buffs()){
			//immunes.addAll(b.immunities());
		//}

		//for (Class c : immunes){
			//if (c.isAssignableFrom(effect)){
			//	return true;
			//}
	//	}
		//return false;
	//}

	protected HashSet<Property> properties = new HashSet<>();

	public HashSet<Property> properties() { return properties; }

	public enum Property{
		BOSS,
		MINIBOSS,
		IMMOVABLE,

		HUMAN,
        ORC,
		ELF,
		//GNOLL,
		DWARF,
		TROLL,
		DEMONIC,
		GOBLIN,

        BEAST,
		DRAGON,
		PLANT( new HashSet<Class>(),
				new HashSet<Class>( Arrays.asList(Bleeding.class, ToxicGas.class, Poison.class)),
		        new HashSet<Class>( Arrays.asList(Burning.class, WandOfFirebolt.class,EnchantmentFire.class,EnchantmentFire2.class))
				),
		ELEMENT,

		MECH,
		UNDEAD,
		ALIEN,
		UNKNOW;

		private HashSet<Class> resistances;
		private HashSet<Class> immunities;
		private HashSet<Class> weakness;

		Property(){
			this(new HashSet<Class>(), new HashSet<Class>(),new HashSet<Class>());
		}

		Property( HashSet<Class> resistances, HashSet<Class> immunities,HashSet<Class> weakness){
			this.resistances = resistances;
			this.immunities = immunities;
			this.weakness = weakness;
		}

		public HashSet<Class> resistances(){
			return new HashSet<>(resistances);
		}

		public HashSet<Class> immunities(){
			return new HashSet<>(immunities);
		}
		public HashSet<Class> weakness(){
			return new HashSet<>(weakness);
		}
	}
}

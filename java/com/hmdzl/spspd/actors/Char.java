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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.HealLight;
import com.hmdzl.spspd.actors.blobs.SlowGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.blobs.effectblobs.ElectriShock;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.BeCorrupt;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.BeTired;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.BloodAngry;
import com.hmdzl.spspd.actors.buffs.BloodImbue;
import com.hmdzl.spspd.actors.buffs.BoxStar;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Cold;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.DamageUp;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Dry;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.ExProtect;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.actors.buffs.FrostImbue;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.HighAttack;
import com.hmdzl.spspd.actors.buffs.Hot;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.LightShootAttack;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.MagicWeak;
import com.hmdzl.spspd.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.Needling;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.ParyAttack;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.buffs.Rhythm2;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.ShadowCurse;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Shocked2;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.SoulMark;
import com.hmdzl.spspd.actors.buffs.SpeedImbue;
import com.hmdzl.spspd.actors.buffs.SpeedSlow;
import com.hmdzl.spspd.actors.buffs.SpeedUp;
import com.hmdzl.spspd.actors.buffs.StoneIce;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.actors.buffs.mindbuff.AmokMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.TerrorMind;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.items.armor.glyphs.Darkglyph;
import com.hmdzl.spspd.items.armor.glyphs.Earthglyph;
import com.hmdzl.spspd.items.armor.glyphs.Electricityglyph;
import com.hmdzl.spspd.items.armor.glyphs.Lightglyph;
import com.hmdzl.spspd.items.rings.RingOfElements;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.wands.WandOfAcid;
import com.hmdzl.spspd.items.wands.WandOfBlood;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfFreeze;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.items.wands.WandOfLightning;
import com.hmdzl.spspd.items.wands.WandOfMeteorite;
import com.hmdzl.spspd.items.wands.WandOfSwamp;
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

import java.util.Arrays;
import java.util.HashSet;

public abstract class Char extends Actor {

	protected static final String TXT_KILL = "%s killed you...";

	public int pos = 0;

	public CharSprite sprite;

	public String name = "mob";

	public int HT;
	public int TRUE_HT;
	//public int EX_HT;
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
	private static final String TRUEHT = "ture_HT";


	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		bundle.put(POS, pos);
		bundle.put(TAG_HP, HP);
		bundle.put(TAG_HT, HT);
		bundle.put(BUFFS, buffs);
		bundle.put(TRUEHT, TRUE_HT);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {

		super.restoreFromBundle(bundle);

		pos = bundle.getInt(POS);
		HP = bundle.getInt(TAG_HP);
		HT = bundle.getInt(TAG_HT);
		TRUE_HT = bundle.getInt(TRUEHT);

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
			if (isTerroredBy(enemy)){
				dmg *= 0.5f;
			}
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
			dmg *= 1.4f;
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
					//if (Bestiary.isUnique(this)) {
						//Dungeon.fail(Messages.format(ResultDescriptions.UNIQUE));
					//} else {
						Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
					//}

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
		if (attacker.buff(AmokMind.class) != null) acuRoll *= 0.70f;
		if (defender.buff(TerrorMind.class) != null) defRoll *= 0.70f;
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
		//if (buff(Shocked.class)!=null && Shocked.first == false){
		//	Buff.detach(this,Shocked.class);
		//	Buff.affect(this, Disarm.class,5f);
       //     damage(this.HP/10,SHOCK_DAMAGE);
	//	}

		return damage;
	}

	public int defenseProc(Char enemy, int damage) {
		return damage;
	}

	public float speed() {
		if (buff(Cripple.class) != null){
			return baseSpeed * 0.5f;
		} else if (buff(HasteBuff.class) != null){
			return baseSpeed * 2f;
		} else if (buff(Poison.class) != null) {
			return baseSpeed * 0.9f;
		} else if (buff(BloodAngry.class) != null) {
			return baseSpeed * 1.2f;
		} else if (buff(MechArmor.class) != null) {
			return baseSpeed * 1.5f;
		} else	if (buff(StoneIce.class) != null) {
			return baseSpeed * 0.8f;
		} else	if (buff(FrostIce.class) != null) {
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
		
		if (this.buff(ExProtect.class) != null) {
			Buff.detach(this, ExProtect.class);
			dmg = 0;
			Buff.affect(this,BoxStar.class,3f);
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

        if (src instanceof Wand && Dungeon.hero.heroClass == HeroClass.MAGE && Dungeon.skins == 4) {
			dmg = (int) Math.ceil(dmg * 1.5);
		}

		DefenceUp drup = buff(DefenceUp.class);
		if (buff(DefenceUp.class) != null) {
			dmg = (int) Math.ceil(dmg *(-drup.level()*0.01+1));
		}

		if (buff(GrowSeed.class) != null) {
			dmg = (int) Math.ceil(dmg *0.8);
		}

		if (buff(MagicWeak.class) != null && src instanceof Wand) {
			dmg = (int) Math.ceil(dmg *1.5);
		}
		
		
		if (buff(SoulMark.class) != null) {
			dmg = (int) Math.ceil(dmg *1.5);
		}

		if (buff(BeTired.class) != null) {
			BeTired.level++;
		}


		ShieldArmor sarmor = buff(ShieldArmor.class);
		MagicArmor magicarmor = buff(MagicArmor.class);
		MechArmor marmor = buff(MechArmor.class);
		EnergyArmor earmor = buff(EnergyArmor.class);
		if(!(src instanceof Hunger)) {
			//if (!(src instanceof DamageType) && !(src instanceof Buff) && !(src instanceof Blob)
					//&& !(src instanceof Wand) && !(src instanceof Weapon.Enchantment) && !(src instanceof MagicPlantArmor.Glyph)) {

			//} else {

			if (sarmor != null && src instanceof Char) {
				dmg = sarmor.absorb(dmg);
				//GLog.w(Messages.get(this, "htdown", src, dmg));
			}
		   if (magicarmor != null && (src instanceof DamageType || src instanceof Buff ||
				src instanceof Blob || src instanceof Wand )){ dmg = magicarmor.absorb(dmg);
			//GLog.w(Messages.get(this, "htdown",src,dmg));
			}
	//}
			if (marmor != null ) {
				dmg = marmor.absorb(dmg);
			}

		}

		if (earmor != null ) {
			dmg = earmor.absorb(dmg);
		}

		if(this.properties().contains(Property.UNKNOW)){
			if (((src instanceof Char)&& ((Char) src).properties().contains(Property.MAGICER)) ||
			src instanceof Buff || src instanceof DamageType ||
			src instanceof Blob ||
			src instanceof Wand) dmg = (int)(dmg*1.25);
                else dmg = (int)(dmg*0.75);
		}

		if(this.properties().contains(Property.ELEMENT)){
			if (((src instanceof Char)&& ((Char) src).properties().contains(Property.MAGICER)) ||

			src instanceof Buff || src instanceof DamageType ||
			src instanceof Blob ||
			src instanceof Wand
			) dmg = (int)(dmg*0.75);
			else dmg = (int)(dmg*1.25);
		}

		if(this.properties().contains(Property.TROLL)){
			 dmg = (int)(dmg*0.6);
		}

		if(this.properties().contains(Property.ALIEN)){
			dmg = (int)(dmg*1.4);
		}

		Class<?> srcClass = src.getClass();
		if (isImmune( srcClass )) {dmg = 0; sprite.showStatus(CharSprite.NULL_DAMAGE, "IMM" );}
		if (isResist( srcClass )) {dmg = Random.IntRange(dmg/4, dmg*3/4);sprite.showStatus(CharSprite.NULL_DAMAGE, "RES");}
		if (isWeak(srcClass)) {dmg = Random.IntRange(dmg + dmg / 2, 2 * dmg + dmg / 2);sprite.showStatus(CharSprite.NULL_DAMAGE, "WEK");}



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

		if (HP <= 0 || dmg < 0) {
			return;
		}

       //if (dmg > HP){
		//Buff.detach(this,Corruption.class);}
		HP -= dmg;
		
		
		if (dmg > 0) {
			if (src instanceof Hunger){
				sprite.showStatus(CharSprite.NULL_DAMAGE, Integer.toString(dmg));
			} else if (src instanceof Char){
				sprite.showStatus(CharSprite.MELEE_DAMAGE, Integer.toString(dmg));
			} else if (src instanceof DamageType || src instanceof Buff ||
					src instanceof Blob || src instanceof Wand){
				sprite.showStatus(CharSprite.MAGIC_DAMAGE, Integer.toString(dmg));
			} else {
				sprite.showStatus(CharSprite.NULL_DAMAGE, Integer.toString(dmg));
			}

		}
		//sprite.showStatus(HP > HT / 2 ? CharSprite.WARNING
		//		: CharSprite.NEGATIVE, Integer.toString(dmg)
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
		}
		if (buff( Chill.class ) != null) {
			timeScale *= buff( Chill.class ).speedFactor();
		}
		if (buff( SpeedSlow.class ) != null) {
			timeScale *= buff( SpeedSlow.class ).speedFactor();
		}
		if (buff(SpeedUp.class) != null) {
			timeScale *= 1.5f;
		}
		if (buff(SpeedImbue.class) != null) {
			timeScale *= 2f;
		}
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

	public boolean isTerroredBy(Char ch) {
		int chID = ch.id();
		for (Buff b : buffs) {
			if (b instanceof Terror && ((Terror) b).object == chID) {
				return true;
			}
		}
		return false;
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
	protected final HashSet<Class> resistances = new HashSet<>();

	//returns percent effectiveness after resisances
	//TODO currently resistances reduce effectiveness by a static 50%, and do not stack.

	public float RingFix( Class effect ){
		float result = 1f;
		return result * RingOfElements.fintime(this, effect);
	}

	public float resist( Class effect ){
		HashSet<Class> resists = new HashSet<>(resistances);
		for (Property p : properties()){
			resists.addAll(p.resistances());
		}
		for (Buff b : buffs()){
			resists.addAll(b.resistances());
		}

		float result = 1f;
		for (Class c : resists){
			if (c.isAssignableFrom(effect)){
				result *= 0.5f;
			}
		}
		return result;
	}

	protected final HashSet<Class> weakness = new HashSet<>();
	
	public float weak( Class effect ){
		HashSet<Class> weaks = new HashSet<>(weakness);
		for (Property p : properties()){
			weaks.addAll(p.weakness());
		}
		for (Buff b : buffs()){
			weaks.addAll(b.weakness());
		}

		float result = 1f;
		for (Class c : weaks){
			if (c.isAssignableFrom(effect)){
				result *= 1.5f;
			}
		}
		return result;
	}

	public boolean isResist( Class effect ){
		HashSet<Class> resists = new HashSet<>(resistances);
		for (Property p : properties()){
			resists.addAll(p.resistances());
		}
		for (Buff b : buffs()){
			resists.addAll(b.resistances());
		}
		for (Class c : resists){
			if (c.isAssignableFrom(effect)){
				return true;
			}
		}
		return false;
	}

	public boolean isWeak( Class effect ){
		HashSet<Class> weaks = new HashSet<>(weakness);
		for (Property p : properties()){
			weaks.addAll(p.weakness());
		}
		for (Buff b : buffs()){
			weaks.addAll(b.weakness());
		}
		for (Class c : weaks){
			if (c.isAssignableFrom(effect)){
				return true;
			}
		}
		return false;
	}

    protected final HashSet<Class> immunities = new HashSet<>();	

	public boolean isImmune(Class effect ){
		HashSet<Class> immunes = new HashSet<>(immunities);
		for (Property p : properties()){
			immunes.addAll(p.immunities());
		}
		for (Buff b : buffs()){
			immunes.addAll(b.immunities());
		}
		
		for (Class c : immunes){
			if (c.isAssignableFrom(effect)){
				return true;
			}
		}
		return false;
	}	
	
	protected HashSet<Property> properties = new HashSet<>();

	public HashSet<Property> properties() {
		HashSet<Property> props = new HashSet<>(properties);
	    return props;
	}

	public static boolean hasProp( Char ch, Property p){
		return (ch != null && ch.properties().contains(p));
	}

	public enum Property{
		BOSS,
		MINIBOSS,
		IMMOVABLE,
		MAGICER,

		HUMAN( new HashSet<Class>( ),
				new HashSet<Class>( ),
				new HashSet<Class>( )
		),
        ORC( new HashSet<Class>( Arrays.asList(Bleeding.class,Poison.class) ),
				new HashSet<Class>( Arrays.asList(Roots.class)  ),
				new HashSet<Class>( Arrays.asList(BeCorrupt.class,BeOld.class) )
		),
		FISHER( new HashSet<Class>( Arrays.asList(WandOfLightning.class,WandOfFreeze.class,WandOfFlow.class,ElectriShock.class)),
				new HashSet<Class>( ),
				new HashSet<Class>( Arrays.asList(Burning.class,WandOfFirebolt.class,WandOfMeteorite.class))
		),
		ELF( new HashSet<Class>( Arrays.asList(BeOld.class) ),
				new HashSet<Class>( ),
				new HashSet<Class>( Arrays.asList(Vertigo.class,Blindness.class))
		),
		DWARF( new HashSet<Class>(  Arrays.asList(Paralysis.class , Ooze.class,GrowSeed.class ) ),
				new HashSet<Class>( Arrays.asList(Cripple.class, Roots.class) ),
				new HashSet<Class>(  Arrays.asList(ShadowCurse.class,DamageType.DarkDamage.class,Darkglyph.class,WandOfBlood.class))
		),
		TROLL( new HashSet<Class>( ),
				new HashSet<Class>( ),
				new HashSet<Class>(  Arrays.asList(Buff.class) )
		),
		DEMONIC( new HashSet<Class>(Arrays.asList(ShadowCurse.class,DamageType.DarkDamage.class,Darkglyph.class,WandOfBlood.class) ),
				new HashSet<Class>( ),
				new HashSet<Class>( Arrays.asList(WandOfLightning.class,DamageType.LightDamage.class,Lightglyph.class,LightShootAttack.class) )
		),
		GOBLIN( new HashSet<Class>( Arrays.asList(WandOfLightning.class,ElectriShock.class,DamageType.ShockDamage.class,Shocked.class,Shocked2.class,Electricityglyph.class) ),
				new HashSet<Class>( ),
				new HashSet<Class>( Arrays.asList(DamageType.EarthDamage.class,Earthglyph.class,WandOfSwamp.class,WandOfAcid.class ) )
		),

        BEAST( new HashSet<Class>( Arrays.asList(Hot.class,Cold.class,Dry.class,Wet.class)),
				new HashSet<Class>(  ),
				new HashSet<Class>( Arrays.asList( BeOld.class, DamageType.DarkDamage.class, Blindness.class) )
		),
		DRAGON( new HashSet<Class>( Arrays.asList(Burning.class,WandOfFirebolt.class,WandOfMeteorite.class) ),
				new HashSet<Class>(  Arrays.asList(BeCorrupt.class,BeOld.class,AttackDown.class) ),
				new HashSet<Class>(  Arrays.asList(WandOfLightning.class,WandOfFreeze.class,WandOfFlow.class,ElectriShock.class) )
		),
		PLANT( new HashSet<Class>(Arrays.asList(Roots.class, Cripple.class, Ooze.class,DamageType.LightDamage.class)),
				new HashSet<Class>( Arrays.asList(Bleeding.class, ToxicGas.class, Poison.class)),
		        new HashSet<Class>( Arrays.asList(Burning.class, WandOfFirebolt.class,DamageType.FireDamage.class))
				),
		ELEMENT( new HashSet<Class>( ),
				new HashSet<Class>( ),
				new HashSet<Class>( )
		),

		MECH( new HashSet<Class>(  Arrays.asList(Fire.class, SlowGas.class, Frost.class,Burning.class)),
				new HashSet<Class>( Arrays.asList(Charm.class, Terror.class) ),
				new HashSet<Class>(  Arrays.asList(Ooze.class,WandOfAcid.class, WandOfSwamp.class,DamageType.EarthDamage.class))
		),
		UNDEAD( new HashSet<Class>( Arrays.asList(Cold.class, SlowGas.class, Frost.class,BeCorrupt.class)),
				new HashSet<Class>( Arrays.asList(Bleeding.class, HealLight.class, Poison.class,BeOld.class)),
				new HashSet<Class>(Arrays.<Class>asList(Blindness.class, WandOfLight.class, DamageType.LightDamage.class))
		),
		ALIEN( new HashSet<Class>( Arrays.asList(Buff.class) ),
				new HashSet<Class>( ),
				new HashSet<Class>( )
		),
		UNKNOW( new HashSet<Class>( ),
				new HashSet<Class>( ),
				new HashSet<Class>( )
		);

		private HashSet<Class> resistances;
		private HashSet<Class> immunities;
		private HashSet<Class> weakness;

		Property(){
			this(new HashSet<Class>(), new HashSet<Class>(),new HashSet<Class>());
		}

		Property( HashSet<Class> resistances, HashSet<Class> immunities,HashSet<Class> weakness){
			this.resistances = resistances;
			this.immunities = immunities ;
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

package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.effects.particles.EarthParticle;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.EARTH_DAMAGE;

public class MossAmmo extends SpAmmo {

	private static Glowing PURPLE = new ItemSprite.Glowing(0x8844CC);

	@Override
	public Glowing glowing() {
		return PURPLE;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.10*damage), EARTH_DAMAGE);
		if (Random.Int(4) == 3) {
			Buff.affect(defender, Ooze.class);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else {
			Buff.affect(defender, Poison.class).set(Random.Int(4, 5));
		}
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.10*damage), EARTH_DAMAGE);
		if (Random.Int(4) == 3) {
			Buff.affect(defender, Ooze.class);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else {
			Buff.affect(defender, Poison.class).set(Random.Int(4, 5));
		}
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.10*damage), EARTH_DAMAGE);
		if (Random.Int(4) == 3) {
			Buff.affect(defender, Ooze.class);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else {
			Buff.affect(defender, Poison.class).set(Random.Int(4, 5));
		}
	}
}
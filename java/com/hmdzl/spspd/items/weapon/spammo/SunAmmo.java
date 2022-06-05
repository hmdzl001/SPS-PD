package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.effects.particles.EarthParticle;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.LIGHT_DAMAGE;

public class SunAmmo extends SpAmmo {

    private static Glowing PINK = new ItemSprite.Glowing(0xCCAA88);

	@Override
	public Glowing glowing() {
		return PINK;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		if (Random.Int(7) == 3) {
			Buff.affect(defender, GrowSeed.class).set(5f);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else defender.damage((int)(0.20*damage), LIGHT_DAMAGE);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		if (Random.Int(7) == 3) {
			Buff.affect(defender, GrowSeed.class).set(5f);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else defender.damage((int)(0.20*damage), LIGHT_DAMAGE);
	}

	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		if (Random.Int(7) == 3) {
			Buff.affect(defender, GrowSeed.class).set(5f);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else defender.damage((int)(0.20*damage), LIGHT_DAMAGE);
	}

}
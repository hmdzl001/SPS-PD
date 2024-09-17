package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.DARK_DAMAGE;

public class StarAmmo extends SpAmmo {

	private static final Glowing BLACK = new Glowing(0x00000);

	@Override
	public Glowing glowing() {
		return BLACK;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		if (Random.Int(20) == 1) {
			if (defender.properties().contains(Char.Property.BOSS) || defender.properties().contains(Char.Property.MINIBOSS)) {
				defender.damage(Random.Int(defender.HT / 8, defender.HT / 4), DARK_DAMAGE,2);
			} else defender.damage(Random.Int(defender.HT, defender.HT * 2), DARK_DAMAGE,2);
			defender.sprite.emitter().burst(ShadowParticle.UP, 5);
			if (!defender.isAlive() && attacker instanceof Hero) {
				Badges.validateGrimWeapon();
			}
		} else
		defender.damage((int)(0.4*damage), DARK_DAMAGE,2);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		if (Random.Int(20) == 1) {
			if (defender.properties().contains(Char.Property.BOSS) || defender.properties().contains(Char.Property.MINIBOSS)) {
				defender.damage(Random.Int(defender.HT / 8, defender.HT / 4), DARK_DAMAGE,2);
			} else defender.damage(Random.Int(defender.HT, defender.HT * 2), DARK_DAMAGE,2);
			defender.sprite.emitter().burst(ShadowParticle.UP, 5);
			if (!defender.isAlive() && attacker instanceof Hero) {
				Badges.validateGrimWeapon();
			}
		} else
			defender.damage((int)(0.4*damage), DARK_DAMAGE,2);
	}
		@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		if (Random.Int(20) == 1) {
			if (defender.properties().contains(Char.Property.BOSS) || defender.properties().contains(Char.Property.MINIBOSS)) {
				defender.damage(Random.Int(defender.HT / 8, defender.HT / 4), DARK_DAMAGE,2);
			} else defender.damage(Random.Int(defender.HT, defender.HT * 2), DARK_DAMAGE,2);
			defender.sprite.emitter().burst(ShadowParticle.UP, 5);
			if (!defender.isAlive() && attacker instanceof Hero) {
				Badges.validateGrimWeapon();
			}
		} else
			defender.damage((int)(0.4*damage), DARK_DAMAGE,2);
	}
}
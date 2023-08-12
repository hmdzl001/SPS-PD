package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Boomerang;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;

public class BlindAmmo extends SpAmmo {

private static Glowing YELLOW = new ItemSprite.Glowing( 0xFFFF44 );

	@Override
	public Glowing glowing() {
		return YELLOW;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		if (Random.Int(5) == 3) {
			Buff.prolong(defender, Blindness.class,3f);
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6);
		} else if (Random.Int(5) == 3) {
			Buff.prolong(defender, Vertigo.class, 3f);
		} else defender.damage((int)(0.15*damage), ENERGY_DAMAGE);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		if (Random.Int(5) == 3) {
			Buff.prolong(defender, Blindness.class,3f);
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6);
		} else if (Random.Int(5) == 3) {
			Buff.prolong(defender, Vertigo.class, 3f);
		} else defender.damage((int)(0.15*damage), ENERGY_DAMAGE);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		if (Random.Int(5) == 3) {
			Buff.prolong(defender, Blindness.class,3f);
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6);
		} else if (Random.Int(5) == 3) {
			Buff.prolong(defender, Vertigo.class, 3f);
		} else defender.damage((int)(0.15*damage), ENERGY_DAMAGE);
	}
}
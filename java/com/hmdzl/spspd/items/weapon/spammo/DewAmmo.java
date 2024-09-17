package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.DARK_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.EARTH_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.FIRE_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.ICE_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.LIGHT_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.SHOCK_DAMAGE;

public class DewAmmo extends SpAmmo {

	//private static final Glowing BLACK = new Glowing(0x00000);

	//@Override
	//public Glowing glowing() {
		//return BLACK;
	//}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		defender.damage(Random.Int((int)(0.2*damage)), ENERGY_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), FIRE_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), LIGHT_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), DARK_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), SHOCK_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), ICE_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), EARTH_DAMAGE,2);

	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		defender.damage(Random.Int((int)(0.2*damage)), ENERGY_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), FIRE_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), LIGHT_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), DARK_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), SHOCK_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), ICE_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), EARTH_DAMAGE,2);

	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		defender.damage(Random.Int((int)(0.2*damage)), ENERGY_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), FIRE_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), LIGHT_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), DARK_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), SHOCK_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), ICE_DAMAGE,2);
		defender.damage(Random.Int((int)(0.2*damage)), EARTH_DAMAGE,2);
	}
}
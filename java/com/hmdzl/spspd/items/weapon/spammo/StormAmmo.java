package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.SHOCK_DAMAGE;

public class StormAmmo extends SpAmmo {

	private static Glowing WHITE = new ItemSprite.Glowing(0xFFFFFF);

	@Override
	public Glowing glowing() {
		return WHITE;
	}
	
	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		if (Random.Int(6) == 3) {
			Buff.affect(defender, Shocked.class).level(2);

		} else  defender.damage((int)(0.40*damage), SHOCK_DAMAGE,2);

	}
	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		if (Random.Int(6) == 3) {
			Buff.affect(defender, Shocked.class).level(2);

		} else  defender.damage((int)(0.40*damage), SHOCK_DAMAGE,2);

	}
		@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		if (Random.Int(6) == 3) {
			Buff.affect(defender, Shocked.class).level(2);

		} else  defender.damage((int)(0.40*damage), SHOCK_DAMAGE,2);

	}
}
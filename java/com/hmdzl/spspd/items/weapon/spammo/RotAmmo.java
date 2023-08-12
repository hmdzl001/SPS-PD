package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Boomerang;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class RotAmmo extends SpAmmo {

    private static Glowing RED = new ItemSprite.Glowing(0xCC0000);

	@Override
	public Glowing glowing() {
		return RED;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		if (Random.Int(7) == 3) {
			Buff.prolong(defender, Roots.class,3f);
		} else
			Buff.affect(defender, Ooze.class);
		    defender.damage((int)(0.5*damage), attacker);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		if (Random.Int(7) == 3) {
			Buff.prolong(defender, Roots.class,3f);
		} else
			Buff.affect(defender, Ooze.class);
		defender.damage((int)(0.5*damage), attacker);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		if (Random.Int(7) == 3) {
			Buff.prolong(defender, Roots.class,3f);
		} else
			Buff.affect(defender, Ooze.class);
		defender.damage((int)(0.5*damage), attacker);
	}

}
package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class ThornAmmo extends SpAmmo {

	private static Glowing BROWN = new ItemSprite.Glowing(0xCC6600);

	@Override
	public Glowing glowing() {
		return BROWN;
	}
	
	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		if (Random.Int(5) == 3) {
			Buff.affect(defender, Bleeding.class).set(Random.Int(5,damage));
		} else
			Buff.prolong(defender, Cripple.class, 3f);
			defender.damage((int)(0.20*damage), Bleeding.class,1);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		if (Random.Int(5) == 3) {
			Buff.affect(defender, Bleeding.class).set(Random.Int(5,damage));
		} else
			Buff.prolong(defender, Cripple.class, 3f);
		defender.damage((int)(0.20*damage), Bleeding.class,1);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		if (Random.Int(5) == 3) {
			Buff.affect(defender, Bleeding.class).set(Random.Int(5,damage));
		} else
			Buff.prolong(defender, Cripple.class, 3f);
		defender.damage((int)(0.20*damage), Bleeding.class,1);
	}
}
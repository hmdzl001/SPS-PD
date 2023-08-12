package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Boomerang;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class WoodenAmmo extends SpAmmo {

	private static final Glowing BLACK = new Glowing(0x00000);

	@Override
	public Glowing glowing() {
		return BLACK;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		Buff.prolong(defender,Vertigo.class,3f);
		if (Random.Int(10) == 1){
			Buff.prolong(defender,Paralysis.class,3f);
		}
	}
	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		Buff.prolong(defender,Vertigo.class,3f);
		if (Random.Int(8) == 1){
			Buff.prolong(defender,Paralysis.class,3f);
		}
	}
		@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		Buff.prolong(defender,Vertigo.class,3f);
		if (Random.Int(8) == 1){
			Buff.prolong(defender,Paralysis.class,3f);
		}
	}

}
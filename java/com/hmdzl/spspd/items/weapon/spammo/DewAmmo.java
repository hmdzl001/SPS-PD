package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.guns.GunA;
import com.hmdzl.spspd.items.weapon.guns.GunB;
import com.hmdzl.spspd.items.weapon.guns.GunC;
import com.hmdzl.spspd.items.weapon.guns.GunD;
import com.hmdzl.spspd.items.weapon.guns.GunE;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.items.weapon.guns.Sling;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class DewAmmo extends SpAmmo {

	//private static final Glowing BLACK = new Glowing(0x00000);

	//@Override
	//public Glowing glowing() {
		//return BLACK;
	//}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);

	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);

	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);
		defender.damage(Random.Int((int)(0.25*damage)), attacker);

	}
}
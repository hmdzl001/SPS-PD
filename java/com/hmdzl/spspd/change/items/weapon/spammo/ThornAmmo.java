package com.hmdzl.spspd.change.items.weapon.spammo;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.guns.GunA;
import com.hmdzl.spspd.change.items.weapon.guns.GunB;
import com.hmdzl.spspd.change.items.weapon.guns.GunC;
import com.hmdzl.spspd.change.items.weapon.guns.GunD;
import com.hmdzl.spspd.change.items.weapon.guns.GunE;
import com.hmdzl.spspd.change.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.items.weapon.guns.Sling;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
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
			defender.damage((int)(0.20*damage), attacker);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		if (Random.Int(5) == 3) {
			Buff.affect(defender, Bleeding.class).set(Random.Int(5,damage));
		} else
			Buff.prolong(defender, Cripple.class, 3f);
		defender.damage((int)(0.20*damage), attacker);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		if (Random.Int(5) == 3) {
			Buff.affect(defender, Bleeding.class).set(Random.Int(5,damage));
		} else
			Buff.prolong(defender, Cripple.class, 3f);
		defender.damage((int)(0.20*damage), attacker);
	}
}
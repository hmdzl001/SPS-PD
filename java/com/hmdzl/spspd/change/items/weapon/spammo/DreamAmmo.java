package com.hmdzl.spspd.change.items.weapon.spammo;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.Slow;
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

public class DreamAmmo extends SpAmmo {

	private static Glowing GREEN = new ItemSprite.Glowing(0x22CC44);

	@Override
	public Glowing glowing() {
		return GREEN;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.20*damage), attacker);
		Buff.prolong(defender, ArmorBreak.class,6f).level(25);
		Buff.prolong(defender, Slow.class,3f);
	}
	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.20*damage), attacker);
		Buff.prolong(defender, ArmorBreak.class,6f).level(25);
		Buff.prolong(defender, Slow.class,3f);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.20*damage), attacker);
		Buff.prolong(defender, ArmorBreak.class,6f).level(25);
		Buff.prolong(defender, Slow.class,3f);
	}
}
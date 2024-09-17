package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;


public class BattleAmmo extends SpAmmo {

	private static Glowing DEEPGREEN = new ItemSprite.Glowing(0x006633);

	@Override
	public Glowing glowing() {
		return DEEPGREEN;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.50*damage), attacker,1);
		Buff.prolong(attacker, AttackUp.class,5f).level(35);
		Buff.prolong(attacker, DefenceUp.class,5f).level(35);
	}
	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.50*damage), attacker,1);
		Buff.prolong(attacker, AttackUp.class,5f).level(35);
		Buff.prolong(attacker, DefenceUp.class,5f).level(35);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.50*damage), attacker,1);
		Buff.prolong(attacker, AttackUp.class,5f).level(35);
		Buff.prolong(attacker, DefenceUp.class,5f).level(35);
	}
}
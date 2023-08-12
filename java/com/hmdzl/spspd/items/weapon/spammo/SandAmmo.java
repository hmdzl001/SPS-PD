package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dry;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Boomerang;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class SandAmmo extends SpAmmo {

	private static Glowing GERY = new ItemSprite.Glowing(0xCCCCCC);

	@Override
	public Glowing glowing() {
		return GERY;
	}


	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		if (Random.Int(5) == 3) {
			Buff.affect(defender, AttackDown.class,3f).level(25);
			Buff.affect(defender, ArmorBreak.class,3f).level(25);
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6);
		} else if (Random.Int(4) == 1) {
			Buff.prolong(defender, Dry.class, 3f);
		} else defender.damage((int)(0.15*damage), attacker);
	}
	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		if (Random.Int(5) == 3) {
			Buff.affect(defender, AttackDown.class,3f).level(25);
			Buff.affect(defender, ArmorBreak.class,3f).level(25);
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6);
		} else if (Random.Int(4) == 1) {
			Buff.prolong(defender, Dry.class, 3f);
		} else defender.damage((int)(0.15*damage), attacker);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		if (Random.Int(5) == 3) {
			Buff.affect(defender, AttackDown.class,3f).level(25);
			Buff.affect(defender, ArmorBreak.class,3f).level(25);
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6);
		} else if (Random.Int(4) == 1) {
			Buff.prolong(defender, Dry.class, 3f);
		} else defender.damage((int)(0.15*damage), attacker);
	}
}
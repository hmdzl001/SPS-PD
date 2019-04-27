package com.hmdzl.spspd.change.items.weapon.spammo;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackDown;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Dry;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.guns.GunA;
import com.hmdzl.spspd.change.items.weapon.guns.GunB;
import com.hmdzl.spspd.change.items.weapon.guns.GunC;
import com.hmdzl.spspd.change.items.weapon.guns.GunD;
import com.hmdzl.spspd.change.items.weapon.guns.GunE;
import com.hmdzl.spspd.change.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.items.weapon.guns.Sling;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
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
}
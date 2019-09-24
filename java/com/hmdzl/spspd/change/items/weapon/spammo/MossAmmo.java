package com.hmdzl.spspd.change.items.weapon.spammo;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.effects.particles.EarthParticle;
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

public class MossAmmo extends SpAmmo {

	private static Glowing PURPLE = new ItemSprite.Glowing(0x8844CC);

	@Override
	public Glowing glowing() {
		return PURPLE;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.10*damage), attacker);
		if (Random.Int(4) == 3) {
			Buff.affect(defender, Ooze.class);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else {
			Buff.affect(defender, Poison.class).set(Random.Int(4, 5));
		}
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.10*damage), attacker);
		if (Random.Int(4) == 3) {
			Buff.affect(defender, Ooze.class);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else {
			Buff.affect(defender, Poison.class).set(Random.Int(4, 5));
		}
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		defender.damage((int)(0.10*damage), attacker);
		if (Random.Int(4) == 3) {
			Buff.affect(defender, Ooze.class);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else {
			Buff.affect(defender, Poison.class).set(Random.Int(4, 5));
		}
	}
}
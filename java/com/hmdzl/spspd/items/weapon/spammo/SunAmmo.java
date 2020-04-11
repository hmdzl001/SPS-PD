package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.effects.particles.EarthParticle;
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

public class SunAmmo extends SpAmmo {

    private static Glowing PINK = new ItemSprite.Glowing(0xCCAA88);

	@Override
	public Glowing glowing() {
		return PINK;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		if (Random.Int(7) == 3) {
			Buff.affect(defender, GrowSeed.class).reignite(defender);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else defender.damage((int)(0.20*damage), attacker);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		if (Random.Int(7) == 3) {
			Buff.affect(defender, GrowSeed.class).reignite(defender);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else defender.damage((int)(0.20*damage), attacker);
	}

	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		if (Random.Int(7) == 3) {
			Buff.affect(defender, GrowSeed.class).reignite(defender);
			defender.sprite.emitter().burst(EarthParticle.FACTORY, 5);
		} else defender.damage((int)(0.20*damage), attacker);
	}

}
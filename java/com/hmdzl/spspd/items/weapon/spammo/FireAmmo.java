package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.FIRE_DAMAGE;

public class FireAmmo extends SpAmmo {

	private static Glowing ORANGE = new ItemSprite.Glowing( 0xFF4400 );

	@Override
	public Glowing glowing() {
		return ORANGE;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		
		defender.sprite.emitter().burst(FlameParticle.FACTORY, 5);
		if (Random.Int(5) == 4 ) {
			Buff.affect(defender, Burning.class).set(5f);
		} else defender.damage((int)(0.25*damage), FIRE_DAMAGE);

	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		defender.sprite.emitter().burst(FlameParticle.FACTORY, 5);
		if (Random.Int(5) == 4 ) {
			Buff.affect(defender, Burning.class).set(5f);
		} else defender.damage((int)(0.25*damage), FIRE_DAMAGE);

	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		defender.sprite.emitter().burst(FlameParticle.FACTORY, 5);
		if (Random.Int(5) == 4 ) {
			Buff.affect(defender, Burning.class).set(5f);
		} else defender.damage((int)(0.25*damage), FIRE_DAMAGE);

	}	
	
}
package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cold;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.effects.particles.SnowParticle;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ICE_DAMAGE;

public class IceAmmo extends SpAmmo {

    private static Glowing BLUE = new ItemSprite.Glowing(0x0000FF);

	@Override
	public Glowing glowing() {
		return BLUE;
	}
	
	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.25*damage), ICE_DAMAGE,2);

		if (Random.Int(4) == 3) {
			Buff.affect(defender, Frost.class, Frost.duration(defender)*Random.Float(2f, 4f));
			defender.sprite.emitter().burst(SnowParticle.FACTORY, 5);
		} else {
			Buff.prolong(defender, Wet.class, 2f);
			Buff.prolong(defender, Cold.class,2f);
		}
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.25*damage), ICE_DAMAGE,2);

		if (Random.Int(4) == 3) {
			Buff.affect(defender, Frost.class, Frost.duration(defender)*Random.Float(2f, 4f));
			defender.sprite.emitter().burst(SnowParticle.FACTORY, 5);
		} else {
			Buff.prolong(defender, Wet.class, 2f);
			Buff.prolong(defender, Cold.class,2f);
		}
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.25*damage), ICE_DAMAGE,2);

		if (Random.Int(4) == 3) {
			Buff.affect(defender, Frost.class, Frost.duration(defender)*Random.Float(2f, 4f));
			defender.sprite.emitter().burst(SnowParticle.FACTORY, 5);
		} else {
			Buff.prolong(defender, Wet.class, 2f);
			Buff.prolong(defender, Cold.class,2f);
		}
	}	
}
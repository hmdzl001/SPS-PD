package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class GoldAmmo extends SpAmmo {

private static Glowing YELLOW = new ItemSprite.Glowing( 0xFFFF44 );

	@Override
	public Glowing glowing() {
		return YELLOW;
	}
	
	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {
		defender.damage((int)(Dungeon.gold*0.01*Random.Float(0.25f,2.0f)), attacker,1);

		Dungeon.gold -= (int)(Dungeon.gold*0.01);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {
		defender.damage((int)(Dungeon.gold*0.01*Random.Float(0.25f,2.0f)), attacker,1);

		Dungeon.gold -= (int)(Dungeon.gold*0.01);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {
		defender.damage((int)(Dungeon.gold*0.01*Random.Float(0.25f,2.0f)), attacker,1);

		Dungeon.gold -= (int)(Dungeon.gold*0.01);
	}
}
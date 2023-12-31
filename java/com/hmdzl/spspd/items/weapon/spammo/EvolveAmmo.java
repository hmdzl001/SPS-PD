package com.hmdzl.spspd.items.weapon.spammo;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.NormalCell;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Boomerang;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class EvolveAmmo extends SpAmmo {

	private static Glowing DEEPGREEN = new ItemSprite.Glowing(0x006633);

	@Override
	public Glowing glowing() {
		return DEEPGREEN;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		if (Random.Int(10) == 3) {
			if (defender != null && defender != attacker
					&& !defender.properties().contains(Char.Property.BOSS)
					&& !defender.properties().contains(Char.Property.MINIBOSS)) {
				NormalCell cell = new NormalCell();
				cell.HT = defender.HP;
				cell.HP = cell.HT;
				cell.pos = defender.pos;
				defender.destroy();
				defender.sprite.killAndErase();
				Dungeon.depth.mobs.remove(defender);
				GameScene.add(cell);
				CellEmitter.get(cell.pos).burst(Speck.factory(Speck.WOOL), 4);
			}
		} else defender.damage((int)(0.10*damage), attacker);
	}

	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		if (Random.Int(10) == 3) {
			if (defender != null && defender != attacker
					&& !defender.properties().contains(Char.Property.BOSS)
					&& !defender.properties().contains(Char.Property.MINIBOSS)) {
				NormalCell cell = new NormalCell();
				cell.HT = defender.HP;
				cell.HP = cell.HT;
				cell.pos = defender.pos;
				defender.destroy();
				defender.sprite.killAndErase();
				Dungeon.depth.mobs.remove(defender);
				GameScene.add(cell);
				CellEmitter.get(cell.pos).burst(Speck.factory(Speck.WOOL), 4);
			}
		} else defender.damage((int)(0.10*damage), attacker);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		if (Random.Int(10) == 3) {
			if (defender != null && defender != attacker
					&& !defender.properties().contains(Char.Property.BOSS)
					&& !defender.properties().contains(Char.Property.MINIBOSS)) {
				NormalCell cell = new NormalCell();
				cell.HT = defender.HP;
				cell.HP = cell.HT;
				cell.pos = defender.pos;
				defender.destroy();
				defender.sprite.killAndErase();
				Dungeon.depth.mobs.remove(defender);
				GameScene.add(cell);
				CellEmitter.get(cell.pos).burst(Speck.factory(Speck.WOOL), 4);
			}
		} else defender.damage((int)(0.10*damage), attacker);
	}	
}
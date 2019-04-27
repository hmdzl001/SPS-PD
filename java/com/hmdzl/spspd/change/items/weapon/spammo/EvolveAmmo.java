package com.hmdzl.spspd.change.items.weapon.spammo;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.mobs.NormalCell;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.guns.GunA;
import com.hmdzl.spspd.change.items.weapon.guns.GunB;
import com.hmdzl.spspd.change.items.weapon.guns.GunC;
import com.hmdzl.spspd.change.items.weapon.guns.GunD;
import com.hmdzl.spspd.change.items.weapon.guns.GunE;
import com.hmdzl.spspd.change.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.items.weapon.guns.Sling;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
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
				Dungeon.level.mobs.remove(defender);
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
				Dungeon.level.mobs.remove(defender);
				GameScene.add(cell);
				CellEmitter.get(cell.pos).burst(Speck.factory(Speck.WOOL), 4);
			}
		} else defender.damage((int)(0.10*damage), attacker);
	}
}
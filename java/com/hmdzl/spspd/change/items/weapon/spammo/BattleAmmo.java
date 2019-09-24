package com.hmdzl.spspd.change.items.weapon.spammo;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Slow;
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
import com.hmdzl.spspd.change.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.items.weapon.guns.Sling;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;


public class BattleAmmo extends SpAmmo {

	private static Glowing DEEPGREEN = new ItemSprite.Glowing(0x006633);

	@Override
	public Glowing glowing() {
		return DEEPGREEN;
	}

	@Override
	public void onHit(GunWeapon gunweapon, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.50*damage), attacker);
		Buff.prolong(attacker, AttackUp.class,5f).level(35);
		Buff.prolong(attacker, DefenceUp.class,5f).level(35);
	}
	@Override
	public void onHit(Boomerang boomerang, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.50*damage), attacker);
		Buff.prolong(attacker, AttackUp.class,5f).level(35);
		Buff.prolong(attacker, DefenceUp.class,5f).level(35);
	}
	@Override
	public void onHit(ManyKnive manyknive, Char attacker, Char defender, int damage) {

		defender.damage((int)(0.50*damage), attacker);
		Buff.prolong(attacker, AttackUp.class,5f).level(35);
		Buff.prolong(attacker, DefenceUp.class,5f).level(35);
	}
}
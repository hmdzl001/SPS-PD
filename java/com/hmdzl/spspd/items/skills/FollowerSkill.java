/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.items.skills;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Blasphemy;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.ParyAttack;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.scrolls.InventoryScroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.HashMap;

import static com.hmdzl.spspd.Dungeon.hero;


public class FollowerSkill extends ClassSkill {
	private static final String TXT_VALUE = "%+d";
 private static int SKILL_TIME = 1;
	{
		//name = "follower cape";
		image = ItemSpriteSheet.SKILL_FOLLOWER;
	}

	private HashMap<Callback, Mob> targets = new HashMap<Callback, Mob>();

	@Override
	public void doSpecial() {
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		FollowerSkill.charge += 15;
		Buff.affect(curUser, ParyAttack.class);
	}

	@Override
	public void doSpecial2() {
	
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		FollowerSkill.charge += 20;
		int people = 0;
		for (Mob mob : Dungeon.level.mobs) {
			mob.beckon(curUser.pos);
			if (mob instanceof Mob && !(mob instanceof NPC)){
				people++;
			}
		}
		int goldearn = people*(Dungeon.hero.lvl/10+100);
		Dungeon.gold+= goldearn;
		Dungeon.hero.sprite.showStatus(CharSprite.NEUTRAL, TXT_VALUE, goldearn);
		if (Random.Int(4) == 0)
			Dungeon.level.drop(Generator.random(),curUser.pos).sprite.drop(curUser.pos);
	}

	@Override
	public void doSpecial3() {

		if (Dungeon.hero.TRUE_HT > 40) {
			Dungeon.hero.TRUE_HT -= 40;

			Dungeon.hero.updateHT(true);
			Dungeon.hero.STR++;
			Dungeon.hero.hitSkill++;
			Dungeon.hero.evadeSkill++;
			Dungeon.hero.magicSkill++;
			Buff.affect(curUser, Blasphemy.class).level(1);
		} else {
			Buff.affect(Dungeon.hero, AttackUp.class,50).level(50);
			Buff.affect(Dungeon.hero, ArmorBreak.class,50).level(50);
		}
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		FollowerSkill.charge += 15;
	}

	@Override
	public void doSpecial4() {
		GameScene.selectItem(itemSelector, WndBag.Mode.UPGRADEABLE, Messages.get(InventoryScroll.class,"title"));
		
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				FollowerSkill.this.upgrade(item);
				FollowerSkill.charge += 40;
			}
		}
	};
	private void upgrade(Item item) {

		GLog.w(Messages.get(ScrollOfUpgrade.class,"looks_better", item.name()));

		item.upgrade(1);
        item.uncurse();
		
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
		Badges.validateItemLevelAquired(item);
		
		curUser.spend(1f);
		curUser.busy();
		
	}
}
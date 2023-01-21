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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Fury;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.SpellSprite;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndChooseWay;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class TomeOfMastery extends Item {

	public static final float TIME_TO_READ = 10;

	public static final String AC_READ = "READ";

	{
		stackable = false;
		//name = "Tome of Mastery";
		image = ItemSpriteSheet.MASTERY;

		unique = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_READ);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_READ)) {

			if (hero.buff(Blindness.class) != null) {
				GLog.w(Messages.get(this,"blind"));
				return;
			}

			curUser = hero;

			HeroSubClass way1 = null;
			HeroSubClass way2 = null;
			switch (hero.heroClass) {
			case WARRIOR:
				way1 = HeroSubClass.GLADIATOR;
				way2 = HeroSubClass.BERSERKER;
				break;
			case MAGE:
				way1 = HeroSubClass.BATTLEMAGE;
				way2 = HeroSubClass.WARLOCK;
				break;
			case ROGUE:
				way1 = HeroSubClass.FREERUNNER;
				way2 = HeroSubClass.ASSASSIN;
				break;
			case HUNTRESS:
				way1 = HeroSubClass.SNIPER;
				way2 = HeroSubClass.WARDEN;
				break;
			case PERFORMER:
				way1 = HeroSubClass.SUPERSTAR;
				way2 = HeroSubClass.JOKER;
				break;
			case SOLDIER:
				way1 = HeroSubClass.AGENT;
				way2 = HeroSubClass.LEADER;
				break;	
			case FOLLOWER:
				way1 = HeroSubClass.PASTOR;
				way2 = HeroSubClass.ARTISAN;
				break;	
			case ASCETIC:
				way1 = HeroSubClass.MONK;
				way2 = HeroSubClass.HACKER;
			break;								
			}
			GameScene.show(new WndChooseWay(this, way1, way2));

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public boolean doPickUp(Hero hero) {
		Badges.validateMastery();
		return super.doPickUp(hero);
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	public void choose(HeroSubClass way) {

		detach(curUser.belongings.backpack);

		curUser.spend(TomeOfMastery.TIME_TO_READ);
		curUser.busy();

		curUser.subClass = way;

		curUser.sprite.operate(curUser.pos);
		Sample.INSTANCE.play(Assets.SND_MASTERY);

		SpellSprite.show(curUser, SpellSprite.MASTERY);
		curUser.sprite.emitter().burst(Speck.factory(Speck.MASTERY), 12);
		GLog.w(Messages.get(this, "way", way.title()) );
		Dungeon.hero.updateHT(true);

		if (way == HeroSubClass.BERSERKER
				&& curUser.HP <= curUser.HT * Fury.LEVEL) {
			Buff.affect(curUser, Fury.class);
		}
	}
}

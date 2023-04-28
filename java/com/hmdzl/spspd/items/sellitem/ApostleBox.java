/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.items.sellitem;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.buffs.Arcane;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ApostleBox extends SellItem {

	private static final String AC_APPLY = "APPLY";

	{
		image = ItemSpriteSheet.A_BOX;
		stackable = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_APPLY);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_APPLY) {

			curUser = hero;
			//GameScene.selectItem(itemSelector, WndBag.Mode.ARMOR,
			//Messages.get(this,"prompt"));
			detach(curUser.belongings.backpack);
			curUser.sprite.centerEmitter().start(Speck.factory(Speck.KIT), 0.05f, 10);
			curUser.spend(1f);
			curUser.busy();
            switch (Random.Int(4)) {
                case 0:
                    Buff.affect(hero, AttackUp.class, 50).level(50);
                    GLog.n(Messages.get(this,"red"));
                    break;
                case 1:

                    Buff.affect(hero, BerryRegeneration.class).level(50);
                    GLog.p(Messages.get(this,"green"));
                    break;
                case 2:
                    Buff.affect(hero, DefenceUp.class, 50).level(50);
                    GLog.b(Messages.get(this,"blue"));
                    break;
                case 3:
                    Buff.affect(hero, Invisibility.class, 50);
                    Buff.affect(hero, Arcane.class, 50);
                    GLog.v(Messages.get(this,"violet"));
                    break;
            }

			curUser.sprite.operate(curUser.pos);
			Sample.INSTANCE.play(Assets.SND_EVOKE);

		} else {

			super.execute(hero, action);

		}
	}


	@Override
	public int price() {
		return 120 * quantity;
	}
	@Override
	public String info() {
		return super.desc();
	}
}

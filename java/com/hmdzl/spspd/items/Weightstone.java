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

import java.util.ArrayList;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Weightstone extends Item {

	private static final float TIME_TO_APPLY = 2;

	private static final String AC_APPLY = "APPLY";

	{
	
		image = ItemSpriteSheet.WEIGHT;

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
			GameScene.selectItem(itemSelector, WndBag.Mode.WEAPON,
					 Messages.get(this, "select"));

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	private void apply(Weapon weapon) {

	    if (!(Dungeon.hero.heroClass == HeroClass.FOLLOWER ) || (Dungeon.hero.heroClass == HeroClass.FOLLOWER && Random.Int(10)>=1 ))
		detach(curUser.belongings.backpack);

		GLog.w(Messages.get(this,"apply"));

		weapon.enchant();
		
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.centerEmitter().start(PurpleParticle.BURST, 0.05f, 10);
		Sample.INSTANCE.play(Assets.SND_MISS);

		curUser.spend(TIME_TO_APPLY);
		curUser.busy();
	}

	@Override
	public int price() {
		return 40 * quantity;
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				Weightstone.this.apply((Weapon) item);;
			}
		}
	};

	/*public class WndBalance extends Window {

		private static final int WIDTH = 120;
		private static final int MARGIN = 2;
		private static final int BUTTON_WIDTH = WIDTH - MARGIN * 2;
		private static final int BUTTON_HEIGHT = 20;

		public WndBalance(final Weapon weapon) {
			super();

			IconTitle titlebar = new IconTitle(weapon);
			titlebar.setRect(0, 0, WIDTH, 0);
			add(titlebar);

			RenderedTextMultiline tfMesage = PixelScene.renderMultiline( Messages.get(this, "choice"), 8 );
			tfMesage.maxWidth(WIDTH - MARGIN * 2);
			tfMesage.setPos(MARGIN, titlebar.bottom() + MARGIN);
			add( tfMesage );

			float pos = tfMesage.top() + tfMesage.height();

			if (weapon.imbue != Weapon.Imbue.LIGHT) {
				RedButton btnSpeed = new RedButton(Messages.get(this, "light")) {
					@Override
					protected void onClick() {
						hide();
						Weightstone.this.apply(weapon, true);
					}
				};
				btnSpeed.setRect(MARGIN, pos + MARGIN, BUTTON_WIDTH,
						BUTTON_HEIGHT);
				add(btnSpeed);

				pos = btnSpeed.bottom();
			}

			if (weapon.imbue != Weapon.Imbue.HEAVY) {
				RedButton btnAccuracy = new RedButton(Messages.get(this, "heavy")) {
					@Override
					protected void onClick() {
						hide();
						Weightstone.this.apply(weapon, false);
					}
				};
				btnAccuracy.setRect(MARGIN, pos + MARGIN, BUTTON_WIDTH,
						BUTTON_HEIGHT);
				add(btnAccuracy);

				pos = btnAccuracy.bottom();
			}

			RedButton btnCancel = new RedButton(Messages.get(this, "cancel")) {
				@Override
				protected void onClick() {
					hide();
				}
			};
			btnCancel
					.setRect(MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT);
			add(btnCancel);

			resize(WIDTH, (int) btnCancel.bottom() + MARGIN);
		}

		protected void onSelect(int index) {
		}
	}*/
}
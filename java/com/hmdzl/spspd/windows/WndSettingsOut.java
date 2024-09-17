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
package com.hmdzl.spspd.windows;

import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.CheckBox;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.Window;

public class WndSettingsOut extends Window	{
	private static final int WIDTH = 100;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;


	public WndSettingsOut() {
		super();

		CheckBox btnImmersive = null;

		//if (inGame) {
		//} else {

			CheckBox btnScaleUp = new CheckBox(Messages.get(WndSettingsOut.class, "scale_up")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scaleUp(checked());
				}
			};
			btnScaleUp.setRect(0, 0, WIDTH, BTN_HEIGHT);
			btnScaleUp.checked(ShatteredPixelDungeon.scaleUp());
			add(btnScaleUp);

			btnImmersive = new CheckBox(Messages.get(WndSettingsOut.class, "immersive")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.immerse(checked());
				}
			};
			btnImmersive.setRect(0, btnScaleUp.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			btnImmersive.checked(ShatteredPixelDungeon.immersed());
			btnImmersive.enable(android.os.Build.VERSION.SDK_INT >= 19);
			add(btnImmersive);

		//}


		//if (!inGame) {

			RedButton btnOrientation = new RedButton(orientationText()) {
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.landscape(!ShatteredPixelDungeon
							.landscape());
				}
			};
			btnOrientation.setRect(0, btnImmersive.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			add(btnOrientation);

			resize(WIDTH, (int) btnOrientation.bottom());

		//} else {

			//CheckBox btnQuickSlot = new CheckBox(Messages.get(WndSettings.class, "quickslot")) {
			//	@Override
			//	protected void onClick() {
			//		super.onClick();
			//		ShatteredPixelDungeon.quicktypes(checked() ? 2 : 1);
			//		Toolbar.quicktype = checked() ? 2 : 1;
			//		Toolbar.updateLayout();
			//	}
			//};
			//btnQuickSlot.setRect(0, btnBrightness.bottom() + GAP, WIDTH,BTN_HEIGHT);
			//btnQuickSlot.checked(ShatteredPixelDungeon.quicktypes() == 2);
			//add(btnQuickSlot);




			
			
			
			//resize(WIDTH, (int) btnCamera.bottom());
	}

	private String orientationText() {
		return ShatteredPixelDungeon.landscape() ? Messages.get(WndSettingsOut.class, "switch_port")
				: Messages.get(WndSettingsOut.class, "switch_land");
	}

}
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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.ui.CheckBox;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.Toolbar;
import com.hmdzl.spspd.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;

public class WndSettings extends Window {

	private static final String TXT_ZOOM_IN = "+";
	private static final String TXT_ZOOM_OUT = "-";
	private static final String TXT_ZOOM_DEFAULT = "Default Zoom";

	private static final String TXT_SCALE_UP = "Scale up UI";
	private static final String TXT_IMMERSIVE = "Immersive mode";

	private static final String TXT_MUSIC = "Music";

	private static final String TXT_SOUND = "Sound FX";

	private static final String TXT_BRIGHTNESS = "Brightness";

	private static final String TXT_QUICKSLOT = "More QuickSlot";

	private static final String TXT_SWITCH_PORT = "Switch to portrait";
	private static final String TXT_SWITCH_LAND = "Switch to landscape";
	
	private static final String TXT_SYSTEM_FONT = "System Font";

	private static final int WIDTH = 112;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;

	private RedButton btnZoomOut;
	private RedButton btnZoomIn;

	public WndSettings(boolean inGame) {
		super();

		CheckBox btnImmersive = null;

		if (inGame) {
			int w = BTN_HEIGHT;

			// Zoom out
			btnZoomOut = new RedButton(Messages.get(WndSettings.class, "zoom_out")) {
				@Override
				protected void onClick() {
					zoom(Camera.main.zoom - 1);
				}
			};
			add(btnZoomOut.setRect(0, 0, w, BTN_HEIGHT));

			// Zoom in
			btnZoomIn = new RedButton(Messages.get(WndSettings.class, "zoom_in")) {
				@Override
				protected void onClick() {
					zoom(Camera.main.zoom + 1);
				}
			};
			add(btnZoomIn.setRect(WIDTH - w, 0, w, BTN_HEIGHT));

			// Default zoom
			add(new RedButton(Messages.get(WndSettings.class, "zoom_default")) {
				@Override
				protected void onClick() {
					zoom(PixelScene.defaultZoom);
				}
			}.setRect(btnZoomOut.right(), 0, WIDTH - btnZoomIn.width()
					- btnZoomOut.width(), BTN_HEIGHT));

		} else {

			CheckBox btnScaleUp = new CheckBox(Messages.get(WndSettings.class, "scale_up")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scaleUp(checked());
				}
			};
			btnScaleUp.setRect(0, 0, WIDTH, BTN_HEIGHT);
			btnScaleUp.checked(ShatteredPixelDungeon.scaleUp());
			add(btnScaleUp);

			btnImmersive = new CheckBox(Messages.get(WndSettings.class, "immersive")) {
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

		}

		CheckBox btnMusic = new CheckBox(Messages.get(WndSettings.class, "music")) {
			@Override
			protected void onClick() {
				super.onClick();
				ShatteredPixelDungeon.music(checked());
			}
		};
		btnMusic.setRect(0, (btnImmersive != null ? btnImmersive.bottom()
				: BTN_HEIGHT) + GAP, WIDTH, BTN_HEIGHT);
		btnMusic.checked(ShatteredPixelDungeon.music());
		add(btnMusic);

		CheckBox btnSound = new CheckBox(Messages.get(WndSettings.class, "sound")) {
			@Override
			protected void onClick() {
				super.onClick();
				ShatteredPixelDungeon.soundFx(checked());
				Sample.INSTANCE.play(Assets.SND_CLICK);
			}
		};
		btnSound.setRect(0, btnMusic.bottom() + GAP, WIDTH, BTN_HEIGHT);
		btnSound.checked(ShatteredPixelDungeon.soundFx());
		add(btnSound);

		if (!inGame) {

			RedButton btnOrientation = new RedButton(orientationText()) {
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.landscape(!ShatteredPixelDungeon
							.landscape());
				}
			};
			btnOrientation.setRect(0, btnSound.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			add(btnOrientation);

			resize(WIDTH, (int) btnOrientation.bottom());

		} else {

			CheckBox btnBrightness = new CheckBox(Messages.get(WndSettings.class, "brightness")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.brightness(checked());
				}
			};
			btnBrightness
					.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
			btnBrightness.checked(ShatteredPixelDungeon.brightness());
			add(btnBrightness);

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

			RedButton btnQuickSlot = new RedButton(Messages.get(WndSettings.class, "quickslot_" + ShatteredPixelDungeon.quicktypes())) {
				@Override
				protected void onClick() {
					super.onClick();
					int val = ShatteredPixelDungeon.quicktypes();
					val = val < 4 ? val + 1 : 0;
					ShatteredPixelDungeon.quicktypes(val);
					text.text( Messages.get(WndSettings.class, "quickslot_" + ShatteredPixelDungeon.quicktypes()) );
					Toolbar.quicktype = val;
					Toolbar.updateLayout();
				}
			};
			btnQuickSlot.setRect(0, btnBrightness.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnQuickSlot);
			//resize(WIDTH, (int) btnQuickSlot.bottom());

			RedButton btnBaseTool = new RedButton(Messages.get(WndSettings.class, "basetool_" + ShatteredPixelDungeon.basetooltypes())) {
				@Override
				protected void onClick() {
					super.onClick();
					int val = ShatteredPixelDungeon.basetooltypes();
					val = val < 4 ? val + 1 : 0;
					ShatteredPixelDungeon.basetooltypes(val);
					text.text( Messages.get(WndSettings.class, "basetool_" + ShatteredPixelDungeon.basetooltypes()) );
					Toolbar.basetooltype = val;
					Toolbar.updateLayout();
				}
			};
			btnBaseTool.setRect(0, btnQuickSlot.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			add(btnBaseTool);

			RedButton btnCamera = new RedButton(Messages.get(WndSettings.class, "camera_" + ShatteredPixelDungeon.cameratypes())) {
				@Override
				protected void onClick() {
					super.onClick();
					int val = ShatteredPixelDungeon.cameratypes();
					val = val < 2 ? val + 1 : 0;
					ShatteredPixelDungeon.cameratypes(val);
					text.text( Messages.get(WndSettings.class, "camera_" + ShatteredPixelDungeon.cameratypes()) );

				}
			};
			btnCamera.setRect(0, btnBaseTool.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			add(btnCamera);
			
			
			
			resize(WIDTH, (int) btnCamera.bottom());
		
		}
	}



	private void zoom(float value) {

		Camera.main.zoom(value);
		ShatteredPixelDungeon.zoom((int) (value - PixelScene.defaultZoom));

		updateEnabled();
	}

	private void updateEnabled() {
		float zoom = Camera.main.zoom;
		btnZoomIn.enable(zoom < PixelScene.maxZoom);
		btnZoomOut.enable(zoom > PixelScene.minZoom);
	}

	private String orientationText() {
		return ShatteredPixelDungeon.landscape() ? Messages.get(WndSettings.class, "switch_port")
				: Messages.get(WndSettings.class, "switch_land");
	}
}

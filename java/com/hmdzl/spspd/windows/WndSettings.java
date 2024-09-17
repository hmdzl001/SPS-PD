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
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.ui.CheckBox;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.Toolbar;
import com.watabou.gltextures.SmartTexture;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Group;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;




public class WndSettings extends WndTabbed {

	private CameraTab cameratab;
	private MusicTab musictab;
	private GameRuleTab gameruletab;

	private static final int WIDTH = 112;
	private static final int HEIGHT = 160;
	private static final int TAB_WIDTH = 40;

	private SmartTexture icons;

	private SmartTexture state_icons;
	private TextureFilm film;

	private TextureFilm state_film;

	public WndSettings() {

		super();

		//icons = TextureCache.get(Assets.BUFFS_LARGE);
		//state_icons = TextureCache.get(Assets.STATE_ICON);
		//film = new TextureFilm(icons, 16, 16);
		//state_film = new TextureFilm(state_icons, 7, 7);

		cameratab = new CameraTab();
		add(cameratab);

		musictab = new MusicTab();
		add(musictab);

		gameruletab = new GameRuleTab();
		add(gameruletab);


		add(new LabeledTab(Messages.get(this, "cameratab")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				cameratab.visible = cameratab.active = selected;
			}
		});

		add(new LabeledTab(Messages.get(this, "musictab")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				musictab.visible = musictab.active = selected;
			}
		});


		add(new LabeledTab(Messages.get(this, "gameruletab")) {
			@Override
			protected void select(boolean value) {
				super.select(value);
				gameruletab.visible = gameruletab.active = selected;
			}
		});

		//resize(WIDTH, (int) Math.max(cameratab.height(), gameruletab.height()));

		resize(WIDTH, HEIGHT);

		layoutTabs();

		select(0);
	}

	private class CameraTab extends Group {

		private static final String TXT_TITLE = "";

		private static final int GAP = 4;

		private float pos;

		public CameraTab() {
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

			CheckBox btnBrightness = new CheckBox(Messages.get(WndSettings.class, "brightness")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.brightness(checked());
				}
			};
			btnBrightness.setRect(0, btnZoomIn.bottom() + GAP, WIDTH, BTN_HEIGHT);
			btnBrightness.checked(ShatteredPixelDungeon.brightness());
			add(btnBrightness);

			RedButton btnCamera = new RedButton(Messages.get(WndSettings.class, "camera_" + ShatteredPixelDungeon.cameratypes())) {
				@Override
				protected void onClick() {
					super.onClick();
					int val = ShatteredPixelDungeon.cameratypes();
					val = val < 2 ? val + 1 : 0;
					ShatteredPixelDungeon.cameratypes(val);
					text.text(Messages.get(WndSettings.class, "camera_" + ShatteredPixelDungeon.cameratypes()));

				}
			};
			btnCamera.setRect(0, btnBrightness.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			add(btnCamera);

			resize(WIDTH, (int) btnCamera.bottom());

		}

		public float height() {
			return pos;
		}

	}

	private class MusicTab extends Group {

		private static final String TXT_TITLE = "";

		private static final int GAP = 4;

		private float pos;

		public MusicTab() {

			int w = BTN_HEIGHT;

			CheckBox btnMusic = new CheckBox(Messages.get(WndSettings.class, "music")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.music(checked());
				}
			};
			btnMusic.setRect(0, (BTN_HEIGHT) + GAP, WIDTH, BTN_HEIGHT);
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

		}

		public float height() {
			return pos;
		}

	}

	private class GameRuleTab extends Group {

		private static final String TXT_TITLE = "";

		private static final int GAP = 4;

		private float pos;

		public GameRuleTab() {
			int w = BTN_HEIGHT;

			RedButton btnQuickSlot = new RedButton(Messages.get(WndSettings.class, "quickslot_" + ShatteredPixelDungeon.quicktypes())) {
				@Override
				protected void onClick() {
					super.onClick();
					int val = ShatteredPixelDungeon.quicktypes();
					val = val < 4 ? val + 1 : 0;
					ShatteredPixelDungeon.quicktypes(val);
					text.text(Messages.get(WndSettings.class, "quickslot_" + ShatteredPixelDungeon.quicktypes()));
					Toolbar.quicktype = val;
					Toolbar.updateLayout();
				}
			};
			btnQuickSlot.setRect(0, 0 + GAP, WIDTH, BTN_HEIGHT);
			add(btnQuickSlot);
			//resize(WIDTH, (int) btnQuickSlot.bottom());

			RedButton btnBaseTool = new RedButton(Messages.get(WndSettings.class, "basetool_" + ShatteredPixelDungeon.basetooltypes())) {
				@Override
				protected void onClick() {
					super.onClick();
					int val = ShatteredPixelDungeon.basetooltypes();
					val = val < 4 ? val + 1 : 0;
					ShatteredPixelDungeon.basetooltypes(val);
					text.text(Messages.get(WndSettings.class, "basetool_" + ShatteredPixelDungeon.basetooltypes()));
					Toolbar.basetooltype = val;
					Toolbar.updateLayout();
				}
			};
			btnBaseTool.setRect(0, btnQuickSlot.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			add(btnBaseTool);

			CheckBox btnPickup = new CheckBox(Messages.get(WndSettings.class, "picktype")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.picktype(checked());
					//Sample.INSTANCE.play(Assets.SND_CLICK);
				}
			};
			btnPickup.setRect(0, btnBaseTool.bottom() + GAP, WIDTH, BTN_HEIGHT);
			btnPickup.checked(ShatteredPixelDungeon.picktype());
			add(btnPickup);

			CheckBox btnShowUp = new CheckBox(Messages.get(WndSettings.class, "showup")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.showup(checked());
					//Sample.INSTANCE.play(Assets.SND_CLICK);
				}
			};
			btnShowUp.setRect(0, btnPickup.bottom() + GAP, WIDTH, BTN_HEIGHT);
			btnShowUp.checked(ShatteredPixelDungeon.showup());
			add(btnShowUp);

			CheckBox btnAllIn = new CheckBox(Messages.get(WndSettings.class, "allin")) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.allin(checked());
					//Sample.INSTANCE.play(Assets.SND_CLICK);
				}
			};
			btnAllIn.setRect(0, btnShowUp.bottom() + GAP, WIDTH, BTN_HEIGHT);
			btnAllIn.checked(ShatteredPixelDungeon.allin());
			add(btnAllIn);


		}

		public float height() {
			return pos;
		}

	}


//public class WndSettings extends Window {

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

	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;

	private RedButton btnZoomOut;
	private RedButton btnZoomIn;

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

}

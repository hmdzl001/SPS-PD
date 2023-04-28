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
package com.hmdzl.spspd.scenes;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.GamesInProgress;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.Skins;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.BannerSprites;
import com.hmdzl.spspd.effects.BannerSprites.Type;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.Archs;
import com.hmdzl.spspd.ui.ExitButton;
import com.hmdzl.spspd.ui.Icons;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.windows.WndChallenges;
import com.hmdzl.spspd.windows.WndClass;
import com.hmdzl.spspd.windows.WndImageOptions;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.BitmaskEmitter;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Button;
import com.watabou.utils.Callback;

import java.util.HashMap;

import static com.hmdzl.spspd.Dungeon.skins;

public class StartScene extends PixelScene {

	private static final float BUTTON_HEIGHT = 24;
	private static final float GAP = 2;

	private static final float WIDTH_P = 116;
	private static final float HEIGHT_P = 220;

	private static final float WIDTH_L = 224;
	private static final float HEIGHT_L = 124;

	private static HashMap<HeroClass, ClassShield> shields = new HashMap<HeroClass, ClassShield>();

	private float buttonX;
	private float buttonY;

	private GameButton btnLoad;
	private GameButton btnNewGame;

	public static HeroClass curClass;

	@Override
	public void create() {

		super.create();

		Badges.loadGlobal();

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		float width, height;
		if (ShatteredPixelDungeon.landscape()) {
			width = WIDTH_L;
			height = HEIGHT_L;
		} else {
			width = WIDTH_P;
			height = HEIGHT_P;
		}

		float left = (w - width) / 2;
		float top = (h - height) / 2;
		float bottom = h - top;

		Archs archs = new Archs();
		archs.setSize(w, h);
		add(archs);

		Image title = BannerSprites.get(Type.SELECT_YOUR_HERO);
		title.x = align((w - title.width()) / 2);
		title.y = align(top);
		align(title);
		add(title);

		buttonX = left;
		buttonY = bottom - BUTTON_HEIGHT;

		btnNewGame = new GameButton(Messages.get(this, "new")) {
			@Override
			protected void onClick() {
				if (GamesInProgress.check(curClass) != null) {
					StartScene.this.add(new WndOptions(
							Messages.get(StartScene.class, "really"),
							Messages.get(StartScene.class, "warning"),
							Messages.get(StartScene.class, "yes"),
							Messages.get(StartScene.class, "no")) {
						@Override
						protected void onSelect(int index) {
							if (index == 0) {
								//startNewGame();
								askSkin();
							}
						}
					});

				} else {
					//startNewGame();
					askSkin();
				}
			}
		};
		add(btnNewGame);

		btnLoad = new GameButton(Messages.get(this, "load")) {
			@Override
			protected void onClick() {
				InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
				Game.switchScene(InterlevelScene.class);
			}
		};
		add(btnLoad);

		float centralHeight = buttonY - title.y - title.height();

		HeroClass[] classes = {
				HeroClass.WARRIOR, HeroClass.MAGE, HeroClass.ROGUE, HeroClass.HUNTRESS, HeroClass.PERFORMER, HeroClass.SOLDIER, HeroClass.FOLLOWER, HeroClass.ASCETIC
		};
		for (HeroClass cl : classes) {
			ClassShield shield = new ClassShield(cl);
			shields.put(cl, shield);
			add(shield);
		}
		if (ShatteredPixelDungeon.landscape()) {
			float shieldW = width / 8;
			float shieldH = Math.min(centralHeight, shieldW);
			top = title.y + title.height + (centralHeight - shieldH) / 2;
			for (int i = 0; i < classes.length; i++) {
				ClassShield shield = shields.get(classes[i]);
				shield.setRect(left + i * shieldW, top, shieldW, shieldH);
			}

			ChallengeButton challenge = new ChallengeButton();
			challenge.setPos(
					title.x - challenge.width() / 2,
					title.y + shieldH / 2 - challenge.height() / 2);
			add(challenge);

		} else {
			float shieldW = width / 4;
			float shieldH = Math.min(centralHeight / 3, shieldW * 1.2f);
			top = title.y + title.height() + centralHeight / 2 - shieldH;
			for (int i = 0; i < classes.length; i++) {
				ClassShield shield = shields.get(classes[i]);
				if (i < 4) {
					shield.setRect(left + (i % 4) * shieldW, top - shieldH * 0.5f, shieldW, shieldH);
				}else{
					shield.setRect(left + (i % 4) * shieldW, top + /*(i / 2) **/ shieldH, shieldW, shieldH);
				}
				align(shield);
			}

			ChallengeButton challenge = new ChallengeButton();
			challenge.setPos(
					w / 2 - challenge.width() / 2,
					top + shieldH - challenge.height() / 2  + 70 );
			align(challenge);
			add(challenge);

		}
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos(Camera.main.width - btnExit.width(), 0);
		add(btnExit);

		curClass = null;
		updateClass(HeroClass.values()[Math.min(ShatteredPixelDungeon.lastClass(),7)]);

		fadeIn();

		Badges.loadingListener = new Callback() {
			@Override
			public void call() {
				if (Game.scene() == StartScene.this) {
					ShatteredPixelDungeon.switchNoFade(StartScene.class);
				}
			}
		};
	}

	@Override
	public void destroy() {

		Badges.saveGlobal();
		Badges.loadingListener = null;

		super.destroy();

	}

	private void updateClass(HeroClass cl) {

		if (curClass == cl) {
			add(new WndClass(cl));
			return;
		}

		if (curClass != null) {
			shields.get(curClass).highlight(false);
		}
		shields.get(curClass = cl).highlight(true);



			GamesInProgress.Info info = GamesInProgress.check( curClass );
			if (info != null) {

				btnLoad.visible = true;
				btnLoad.secondary( Messages.format( Messages.get(this, "depth_level"), info.depth, info.level ), info.challenges );
				btnNewGame.visible = true;
				btnNewGame.secondary( Messages.get(this, "erase"), false );

				float w = (Camera.main.width - GAP) / 2 - buttonX;

				btnLoad.setRect(
						buttonX, buttonY, w, BUTTON_HEIGHT );
				btnNewGame.setRect(
						btnLoad.right() + GAP, buttonY, w, BUTTON_HEIGHT );

			} else {
				btnLoad.visible = false;

				btnNewGame.visible = true;
				btnNewGame.secondary( null, false );
				btnNewGame.setRect( buttonX, buttonY, Camera.main.width - buttonX * 2, BUTTON_HEIGHT );
			}

	}
	
    private void askSkin() {

        StartScene.this.add( new WndSkin() );
    }	

	private void startNewGame() {

		Dungeon.hero = null;
		InterlevelScene.mode = InterlevelScene.Mode.DESCEND;

		if (ShatteredPixelDungeon.intro()) {
			ShatteredPixelDungeon.intro(false);
			Game.switchScene(IntroScene.class);
		} else {
			Game.switchScene(InterlevelScene.class);
		}
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade(TitleScene.class);
	}

	private static class GameButton extends RedButton {

		private static final int SECONDARY_COLOR_N = 0xCACFC2;
		private static final int SECONDARY_COLOR_H = 0xFFFF88;

		private RenderedText secondary;

		public GameButton(String primary) {
			super(primary);

			this.secondary.text(null);
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			secondary = renderText(6);
			add(secondary);
		}

		@Override
		protected void layout() {
			super.layout();

			if (secondary.text().length() > 0) {
				text.y = align(y
						+ (height - text.height() - secondary.baseLine()) / 2);

				secondary.x = align(x + (width - secondary.width()) / 2);
				secondary.y = align(text.y + text.height());
			} else {
				text.y = align(y + (height - text.baseLine()) / 2);
			}
			align(text);
			align(secondary);
		}

		public void secondary(String text, boolean highlighted) {
			secondary.text(text);
			secondary.hardlight(highlighted ? SECONDARY_COLOR_H : SECONDARY_COLOR_N);
		}
	}

	private class ClassShield extends Button {

		private static final float MIN_BRIGHTNESS = 0.6f;

		private static final int BASIC_NORMAL = 0x444444;
		private static final int BASIC_HIGHLIGHTED = 0xCACFC2;

		private static final int MASTERY_NORMAL = 0x666644;
		private static final int MASTERY_HIGHLIGHTED = 0xFFFF88;

		private static final int WIDTH = 28;
		private static final int HEIGHT = 36;
		private static final float SCALE = 1f;

		private HeroClass cl;

		private Image avatar;
		private RenderedText name;
		private Emitter emitter;

		private float brightness;

		private int normal;
		private int highlighted;

		public ClassShield(HeroClass cl) {
			super();

			this.cl = cl;

			avatar.frame(cl.ordinal() * WIDTH, 0, WIDTH, HEIGHT);
			avatar.scale.set(SCALE);

			if (Badges.isUnlocked(cl.masteryBadge())) {
				normal = MASTERY_NORMAL;
				highlighted = MASTERY_HIGHLIGHTED;
			} else {
				normal = BASIC_NORMAL;
				highlighted = BASIC_HIGHLIGHTED;
			}

			name.text( cl.title().toUpperCase() );
			name.hardlight( normal );

			brightness = MIN_BRIGHTNESS;
			updateBrightness();
		}

		@Override
		protected void createChildren() {

			super.createChildren();

			avatar = new Image(Assets.AVATARS);
			add(avatar);

			name = PixelScene.renderText(6);
			add(name);

			emitter = new BitmaskEmitter(avatar);
			add(emitter);
		}

		@Override
		protected void layout() {

			super.layout();

			avatar.x = x + (width - avatar.width()) / 2;
			avatar.y = y + (height - avatar.height() - name.height()) / 2;
			align(avatar);


			name.x = align(x + (width - name.width()) / 2);
			name.y = avatar.y + avatar.height() + SCALE;
			align(name);
			//emitter.pos(avatar.x, avatar.y, avatar.width(), avatar.height());
		}

		@Override
		protected void onTouchDown() {

			emitter.revive();
			emitter.start(Speck.factory(Speck.LIGHT), 0.05f, 7);

			Sample.INSTANCE.play(Assets.SND_CLICK, 1, 1, 1.2f);
			updateClass(cl);
		}

		@Override
		public void update() {
			super.update();

			if (brightness < 1.0f && brightness > MIN_BRIGHTNESS) {
				if ((brightness -= Game.elapsed) <= MIN_BRIGHTNESS) {
					brightness = MIN_BRIGHTNESS;
				}
				updateBrightness();
			}
		}

		public void highlight(boolean value) {
			if (value) {
				brightness = 1.0f;
				name.hardlight(highlighted);
			} else {
				brightness = 0.999f;
				name.hardlight(normal);
			}

			updateBrightness();
		}

		private void updateBrightness() {
			avatar.gm = avatar.bm = avatar.rm = avatar.am = brightness;
		}
	}

	private class ChallengeButton extends Button {

		private Image image;

		public ChallengeButton() {
			super();

			width = image.width;
			height = image.height;

			image.am = Badges.isUnlocked(Badges.Badge.MONSTERS_SLAIN_1) ? 1.0f : 0.5f;
		}

		@Override
		protected void createChildren() {

			super.createChildren();

			image = Icons.get(ShatteredPixelDungeon.challenges() > 0 ? Icons.CHALLENGE_ON : Icons.CHALLENGE_OFF);
			add(image);
		}

		@Override
		protected void layout() {

			super.layout();

			image.x = x;
			image.y = y;
		}

		@Override
		protected void onClick() {
				StartScene.this.add(new WndChallenges(ShatteredPixelDungeon.challenges(), true) {
					public void onBackPressed() {
						super.onBackPressed();
						image.copy(Icons.get(ShatteredPixelDungeon.challenges() > 0 ?
								Icons.CHALLENGE_ON : Icons.CHALLENGE_OFF));
					}
				});

		}

		@Override
		protected void onTouchDown() {
			Sample.INSTANCE.play(Assets.SND_CLICK);
		}
	}
	
    private class WndSkin extends WndImageOptions {

        public WndSkin() {
            super(Messages.get(Skins.class, "title"), Messages.get(Skins.class, "info"),Messages.get(Skins.class, "normal"),
                    Messages.get(Skins.class, "first"),Messages.get(Skins.class, "second"),Messages.get(Skins.class, "third"),
					Messages.get(Skins.class, "fourth"),Messages.get(Skins.class, "fifth"),Messages.get(Skins.class, "sixth"),
					Messages.get(Skins.class, "seventh"));
        }

     @Override
        protected void onSelect( int index ) {

            skins = index;
            final String skin = Skins.NAME_IDS[index];
                StartScene.this.add( 
				new WndOptions( curClass.skinsheet(index) ,Messages.get(Skins.class,skin),
						Messages.get(Skins.class, skin+"_desc"),
							Messages.get(Skins.class, "yes"),
							Messages.get(Skins.class, "no")
                ) {
                    @Override
                    protected void onSelect( int index ) {
                        if (index == 0) {

                            startNewGame();

                        } else {

                            StartScene.this.add( new WndSkin() );

                        }
                    }
                } );
            }
      
    }	
	
}
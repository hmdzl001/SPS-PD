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
package com.hmdzl.spspd;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.scenes.TitleScene;
import com.hmdzl.spspd.scenes.WelcomeScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;

import javax.microedition.khronos.opengles.GL10;

public class ShatteredPixelDungeon extends Game {

	//public static int specialcoin = 10;

	public ShatteredPixelDungeon() {
		super(WelcomeScene.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		updateImmersiveMode();

		DisplayMetrics metrics = new DisplayMetrics();
		instance.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		boolean landscape = metrics.widthPixels > metrics.heightPixels;

		if (Preferences.INSTANCE.getBoolean(Preferences.KEY_LANDSCAPE, false) != landscape) {
			landscape(!landscape);
		}

		Music.INSTANCE.enable(music());
		Sample.INSTANCE.enable(soundFx());

		Sample.INSTANCE.load(
				Assets.SND_CLICK, Assets.SND_BADGE,
				Assets.SND_GOLD,

				Assets.SND_STEP, Assets.SND_WATER, Assets.SND_OPEN,
				Assets.SND_UNLOCK, Assets.SND_ITEM, Assets.SND_DEWDROP,
				Assets.SND_HIT, Assets.SND_MISS,

				Assets.SND_DESCEND, Assets.SND_EAT, Assets.SND_READ,
				Assets.SND_LULLABY, Assets.SND_DRINK, Assets.SND_SHATTER,
				Assets.SND_ZAP, Assets.SND_LIGHTNING, Assets.SND_LEVELUP,
				Assets.SND_DEATH, Assets.SND_CHALLENGE, Assets.SND_CURSED,
				Assets.SND_EVOKE, Assets.SND_TRAP, Assets.SND_TOMB,
				Assets.SND_ALERT, Assets.SND_MELD, Assets.SND_BOSS,
				Assets.SND_BLAST, Assets.SND_PLANT, Assets.SND_RAY,
				Assets.SND_BEACON, Assets.SND_TELEPORT, Assets.SND_CHARMS,
				Assets.SND_MASTERY, Assets.SND_PUFF, Assets.SND_ROCKS,
				Assets.SND_BURNING, Assets.SND_FALLING, Assets.SND_GHOST,
				Assets.SND_SECRET, Assets.SND_BONES, Assets.SND_BEE,
				Assets.SND_DEGRADE, Assets.SND_MIMIC);
				
		if (!SPSSettings.systemFont()) {
			RenderedText.setFont("txttheme/pixelfont.ttf");
		} else {
			RenderedText.setFont( null );
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);

		if (hasFocus) {
			updateImmersiveMode();
		}
	}
	
	public static void switchNoFade(Class<? extends PixelScene> c){
		switchNoFade(c, null);
	}

	public static void switchNoFade(Class<? extends PixelScene> c, SceneChangeCallback callback) {
		PixelScene.noFade = true;
		switchScene( c, callback );
	}	

	/*
	 * ---> Prefernces
	 */

	public static void landscape(boolean value) {
		Game.instance.setRequestedOrientation(value ? 
				ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
				: ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Preferences.INSTANCE.put(Preferences.KEY_LANDSCAPE, value);
	}

	public static boolean landscape() {
		return width > height;
	}

	public static void scaleUp(boolean value) {
		Preferences.INSTANCE.put(Preferences.KEY_SCALE_UP, value);
		switchScene(TitleScene.class);
	}

	// *** IMMERSIVE MODE ****

	private static boolean immersiveModeChanged = false;

	@SuppressLint("NewApi")
	public static void immerse(boolean value) {
		Preferences.INSTANCE.put(Preferences.KEY_IMMERSIVE, value);

		instance.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				updateImmersiveMode();
				immersiveModeChanged = true;
			}
		});
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		super.onSurfaceChanged(gl, width, height);

		if (immersiveModeChanged) {
			requestedReset = true;
			immersiveModeChanged = false;
		}
	}

	private void updateDisplaySize(){
		DisplayMetrics m = new DisplayMetrics();
		if (immersed() && Build.VERSION.SDK_INT >= 19)
			getWindowManager().getDefaultDisplay().getRealMetrics( m );
		else
			getWindowManager().getDefaultDisplay().getMetrics( m );
		dispHeight = m.heightPixels;
		dispWidth = m.widthPixels;

		float dispRatio = dispWidth / (float)dispHeight;

		float renderWidth = dispRatio > 1 ? PixelScene.MIN_WIDTH_L : PixelScene.MIN_WIDTH_P;
		float renderHeight = dispRatio > 1 ? PixelScene.MIN_HEIGHT_L : PixelScene.MIN_HEIGHT_P;

		runOnUiThread(new Runnable() {
				@Override
				public void run() {
					view.getHolder().setSizeFromLayout();
				}
			});

	}
	
	@SuppressLint("NewApi")
	public static void updateImmersiveMode() {
		if (android.os.Build.VERSION.SDK_INT >= 19) {
			try {
				// Sometime NullPointerException happens here
				instance.getWindow()
						.getDecorView()
						.setSystemUiVisibility(
								immersed() ? View.SYSTEM_UI_FLAG_LAYOUT_STABLE
										| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
										| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
										| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
										| View.SYSTEM_UI_FLAG_FULLSCREEN
										| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
										: 0);
			} catch (Exception e) {
				reportException(e);
			}
		}
	}

	public static boolean immersed() {
		return Preferences.INSTANCE
				.getBoolean(Preferences.KEY_IMMERSIVE, false);
	}

	// *****************************

	public static boolean scaleUp() {
		return Preferences.INSTANCE.getBoolean(Preferences.KEY_SCALE_UP, true);
	}

	public static void zoom(int value) {
		Preferences.INSTANCE.put(Preferences.KEY_ZOOM, value);
	}

	public static int zoom() {
		return Preferences.INSTANCE.getInt(Preferences.KEY_ZOOM, 0);
	}

	public static void music(boolean value) {
		Music.INSTANCE.enable(value);
		Preferences.INSTANCE.put(Preferences.KEY_MUSIC, value);
	}

	public static boolean music() {
		return Preferences.INSTANCE.getBoolean(Preferences.KEY_MUSIC, true);
	}

	public static void soundFx(boolean value) {
		Sample.INSTANCE.enable(value);
		Preferences.INSTANCE.put(Preferences.KEY_SOUND_FX, value);
	}

	public static boolean soundFx() {
		return Preferences.INSTANCE.getBoolean(Preferences.KEY_SOUND_FX, true);
	}

	public static void picktype(boolean value) {
		SPSSettings.put(SPSSettings.KEY_PICKTYPE, value);
	}
	public static boolean picktype() {
		return SPSSettings.getBoolean(SPSSettings.KEY_PICKTYPE, true);
	}

	public static void showup(boolean value) {
		SPSSettings.put(SPSSettings.KEY_SHOWUP, value);
	}
	public static boolean showup() {
		return SPSSettings.getBoolean(SPSSettings.KEY_SHOWUP, true);
	}

	public static void allin(boolean value) {
		SPSSettings.put(SPSSettings.KEY_ALLIN, value);
	}

	public static boolean allin() {
		return SPSSettings.getBoolean(SPSSettings.KEY_ALLIN, true);
	}

	public static void brightness(boolean value) {
		Preferences.INSTANCE.put(Preferences.KEY_BRIGHTNESS, value);
		if (scene() instanceof GameScene) {
			((GameScene) scene()).brightness(value);
		}
	}

	public static boolean brightness() {
		return Preferences.INSTANCE.getBoolean(Preferences.KEY_BRIGHTNESS,
				false);
	}

	public static void unlocks( int value ) {
		GiftUnlocks.put( Preferences.KEY_UNLOCKS, value );
	}

	public static int unlocks() {
		return GiftUnlocks.getInt( Preferences.KEY_UNLOCKS, 0, 0, Integer.MAX_VALUE );
	}

	/*public static void language(Languages lang) {
		Preferences.INSTANCE.put( Preferences.KEY_LANG, lang.code());
		if (lang == Languages.RUSSIAN || lang == Languages.CHINESE || lang == Languages.KOREAN)
			RenderedText.setFont(null);
		else if (classicFont())
			RenderedText.setFont("pixelfont.ttf");
	}*/

	/*public static Languages language() {
		String code = Preferences.INSTANCE.getString(Preferences.KEY_LANG, null);
		if (code == null){
			Languages lang = Languages.matchLocale(Locale.getDefault());
			if (lang.status() == Languages.Status.REVIEWED)
				return lang;
			else
				return Languages.ENGLISH;
		}
		else return Languages.matchCode(code);
	}*/

	/*public static void classicFont(boolean classic){
		Preferences.INSTANCE.put(Preferences.KEY_CLASSICFONT, classic);
		if (classic) {
			RenderedText.setFont("pixelfont.ttf");
		} else {
			RenderedText.setFont( null );
		}
	}*/

	/*public static boolean classicFont(){
		Languages lang = ShatteredPixelDungeon.language();
		if (lang == Languages.RUSSIAN ||lang == Languages.CHINESE || lang == Languages.KOREAN )
			return false;
		else
			return Preferences.INSTANCE.getBoolean(Preferences.KEY_CLASSICFONT, true);
	}	*/

	public static void lastClass(int value) {
		Preferences.INSTANCE.put(Preferences.KEY_LAST_CLASS, value);
	}

	public static int lastClass() {
		return Preferences.INSTANCE.getInt(Preferences.KEY_LAST_CLASS, 0);
	}

	public static void challenges(int value) {
		Preferences.INSTANCE.put(Preferences.KEY_CHALLENGES, value);
	}

	public static int challenges() {
		return Preferences.INSTANCE.getInt(Preferences.KEY_CHALLENGES, 0);
	}

	public static void quicktypes(int value) {
		Preferences.INSTANCE.put(Preferences.KEY_QUICKSLOTS, value);
	}

	public static int quicktypes() {
		return Preferences.INSTANCE.getInt(Preferences.KEY_QUICKSLOTS, 1);
	}

	public static void basetooltypes(int value) {
		Preferences.INSTANCE.put(Preferences.KEY_BASETOOL, value);
	}

	public static int basetooltypes() {
		return Preferences.INSTANCE.getInt(Preferences.KEY_BASETOOL, 0);
	}


	public static void cameratypes(int value) {
		Preferences.INSTANCE.put(Preferences.KEY_CAMERA, value);
	}

	public static int cameratypes() {
		return Preferences.INSTANCE.getInt(Preferences.KEY_CAMERA, 1);
	}

	public static void intro(boolean value) {
		Preferences.INSTANCE.put(Preferences.KEY_INTRO, value);
	}

	public static boolean intro() {
		return Preferences.INSTANCE.getBoolean(Preferences.KEY_INTRO, true);
	}

	public static void version(int value) {
		Preferences.INSTANCE.put(Preferences.KEY_VERSION, value);
	}

	public static int version() {
		return Preferences.INSTANCE.getInt(Preferences.KEY_VERSION, 0);
	}

	/*
	 * <--- Preferences
	 */

	public static void reportException(Throwable tr) {
		Log.e("PD", Log.getStackTraceString(tr));
	}
}
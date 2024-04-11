/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
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

import com.hmdzl.spspd.messages.Languages;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.GameSettings;

import java.util.Locale;

public class SPSSettings extends GameSettings {
	
	//Version info
	
	public static final String KEY_VERSION      = "version";
	
	public static void version( int value)  {
		put( KEY_VERSION, value );
	}
	
	public static int version() {
		return getInt( KEY_VERSION, 0 );
	}
	
	//Graphics

	public static final String KEY_LANDSCAPE	= "landscape";
	public static final String KEY_SCALE		= "scale";
	public static final String KEY_ZOOM			= "zoom";
	
	public static boolean landscape() {
		return getBoolean(KEY_LANDSCAPE, Game.dispWidth > Game.dispHeight);
	}
	
	public static void scale( int value ) {
		put( KEY_SCALE, value );
	}
	
	public static int scale() {
		return getInt( KEY_SCALE, 0 );
	}
	
	public static void zoom( int value ) {
		put( KEY_ZOOM, value );
	}
	
	public static int zoom() {
		return getInt( KEY_ZOOM, 0 );
	}
	
	//Interface
	
	//Game State

	public static final String KEY_CHALLENGES	= "challenges";
	
	public static void challenges( int value ) {
		put( KEY_CHALLENGES, value );
	}
	
	//Audio
	
	public static final String KEY_MUSIC		= "music";
	
	public static void music( boolean value ) {
		Music.INSTANCE.enable( value );
		put( KEY_MUSIC, value );
	}
	
	public static boolean music() {
		return getBoolean( KEY_MUSIC, true );
	}

	//Languages and Font
	
	public static final String KEY_LANG         = "language";
	public static final String KEY_SYSTEMFONT	= "system_font";
	
	public static void language(Languages lang) {
		put( KEY_LANG, lang.code());
	}
	
	public static Languages language() {
		String code = getString(KEY_LANG, null);
		if (code == null){
			return Languages.matchLocale(Locale.getDefault());
		} else {
			return Languages.matchCode(code);
		}
	}
	
	public static void systemFont(boolean value){
		put(KEY_SYSTEMFONT, value);
		if (!value) {
			RenderedText.setFont("pixelfont.ttf");
		} else {
			RenderedText.setFont( null );
		}
	}
	
	public static boolean systemFont(){
		return getBoolean(KEY_SYSTEMFONT,
				(language() == Languages.TCHINESE || language() == Languages.CHINESE));
	}

	public static final String KEY_GIFT	= "start_gift";
	
}

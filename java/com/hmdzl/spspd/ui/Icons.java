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
package com.hmdzl.spspd.ui;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.watabou.noosa.Image;

public enum Icons {

	SKULL, BUSY, COMPASS, INFO, PREFS, WARNING, TARGET, MASTERY, WATA, SHPX, WARRIOR, MAGE, ROGUE, HUNTRESS, PERFORMER, SOLDIER, FOLLOWER, ASCETIC, CLOSE, DEPTH, SLEEP, ALERT, 
	BACKPACK, SEED_POUCH, SCROLL_HOLDER, POTION_BANDOLIER, WAND_HOLSTER, CHECKED, UNCHECKED, EXIT, CHALLENGE_OFF, CHALLENGE_ON, RESUME, SHOP_CART, KEYRING,
	LAN, NOTES, SAVELOAD, HOS, LOST;

	public Image get() {
		return get(this);
	}

	public static Image get(Icons type) {
		Image icon = new Image(Assets.ICONS);
		switch (type) {
		case SKULL:
			icon.frame(icon.texture.uvRect(0, 0, 8, 8));
			break;
		case BUSY:
			icon.frame(icon.texture.uvRect(8, 0, 16, 8));
			break;
		case COMPASS:
			icon.frame(icon.texture.uvRect(0, 8, 7, 13));
			break;
		case INFO:
			icon.frame(icon.texture.uvRect(16, 0, 30, 14));
			break;
		case PREFS:
			icon.frame(icon.texture.uvRect(30, 0, 46, 16));
			break;
		case WARNING:
			icon.frame(icon.texture.uvRect(46, 0, 58, 12));
			break;
		case TARGET:
			icon.frame(icon.texture.uvRect(0, 13, 16, 29));
			break;
		case MASTERY:
			icon.frame(icon.texture.uvRect(16, 14, 30, 28));
			break;
		case WATA:
			icon.frame(icon.texture.uvRect(30, 16, 45, 26));
			break;
		case SHPX:
			icon.frame(icon.texture.uvRect(64, 44, 80, 60));
			break;
		case WARRIOR:
			icon.frame(icon.texture.uvRect(0, 29, 16, 45));
			break;
		case MAGE:
			icon.frame(icon.texture.uvRect(16, 29, 32, 45));
			break;
		case ROGUE:
			icon.frame(icon.texture.uvRect(32, 29, 48, 45));
			break;
		case HUNTRESS:
			icon.frame(icon.texture.uvRect(48, 29, 64, 45));
			break;
		case PERFORMER:
			icon.frame(icon.texture.uvRect(48, 65, 60, 74)); 
			break;	
		case SOLDIER:
			icon.frame(icon.texture.uvRect(64, 64, 78, 78));
			break;	
		case FOLLOWER:
			icon.frame(icon.texture.uvRect(86, 62, 97, 73));
			break;	
		case ASCETIC:
			icon.frame(icon.texture.uvRect(102, 62, 115, 74));
			break;				
		case CLOSE:
			icon.frame(icon.texture.uvRect(0, 45, 13, 58));
			break;
		case DEPTH:
			icon.frame( icon.texture.uvRect( 38, 46, 54, 62 ) );
			break;
		case SLEEP:
			icon.frame(icon.texture.uvRect(13, 45, 22, 53));
			break;
		case ALERT:
			icon.frame(icon.texture.uvRect(22, 45, 30, 53));
			break;
		case LOST:
			icon.frame( icon.texture.uvRect( 30, 45, 38, 53 ) );
			break;			
		case BACKPACK:
			icon.frame(icon.texture.uvRect(58, 0, 68, 10));
			break;
		case SCROLL_HOLDER:
			icon.frame(icon.texture.uvRect(68, 0, 78, 10));
			break;
		case SEED_POUCH:
			icon.frame(icon.texture.uvRect(78, 0, 88, 10));
			break;
		case WAND_HOLSTER:
			icon.frame(icon.texture.uvRect(88, 0, 98, 10));
			break;
		case POTION_BANDOLIER:
			icon.frame(icon.texture.uvRect(98, 0, 108, 10));
			break;
		case CHECKED:
			icon.frame(icon.texture.uvRect(54, 12, 66, 24));
			break;
		case UNCHECKED:
			icon.frame(icon.texture.uvRect(66, 12, 78, 24));
			break;
		case EXIT:
			icon.frame(icon.texture.uvRect(108, 0, 124, 16));
			break;
		case CHALLENGE_OFF:
			icon.frame(icon.texture.uvRect(78, 16, 102, 40));
			break;
		case CHALLENGE_ON:
			icon.frame(icon.texture.uvRect(102, 16, 126, 40));
			break;
		case RESUME:
			icon.frame(icon.texture.uvRect(13, 53, 24, 64));
			break;
		case SHOP_CART:
			icon.frame(icon.texture.uvRect(82, 47, 92, 57));
			break;
		case KEYRING:
			icon.frame(icon.texture.uvRect(66, 29, 72, 39));
			break;
		case LAN:
			icon.frame(icon.texture.uvRect(2, 65, 25, 77));
			break;	
		case NOTES:
			icon.frame( icon.texture.uvRect( 110, 43, 125, 59 ));
			break;
		case SAVELOAD:
		    icon.frame( icon.texture.uvRect( 97, 46, 108, 56 ));
            break;
		case HOS:
		    icon.frame( icon.texture.uvRect( 30, 65, 41, 75 ));
            break;
		}		
		return icon;
	}

	public static Image get(HeroClass cl) {
		switch (cl) {
		case WARRIOR:
			return get(WARRIOR);
		case MAGE:
			return get(MAGE);
		case ROGUE:
			return get(ROGUE);
		case HUNTRESS:
			return get(HUNTRESS);
		case PERFORMER:
			return get(PERFORMER);	
		case SOLDIER:
			return get(SOLDIER);
		case FOLLOWER:
			return get(FOLLOWER);
		case ASCETIC:
			return get(ASCETIC);				
		default:
			return null;
		}
	}
}

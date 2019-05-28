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
package com.hmdzl.spspd.change.items.food.staplefood;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.buffs.Bless;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.buffs.Levitation;
import com.hmdzl.spspd.change.actors.buffs.Light;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.buffs.Regeneration;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.FlameParticle;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.particles.Emitter;

import java.util.Calendar;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class Pasty extends StapleFood {

	//TODO: implement fun stuff for other holidays
	//TODO: probably should externalize this if I want to add any more festive stuff.
	private enum Holiday{
		NONE,
		SPRING,
		STUDENT,
		EASTER, //TBD
		HWEEN,//2nd week of october though first day of november
		THANK,
		XMAS, //3rd week of december through first week of january
		CHILD,
		WORKER
	}

	private static Holiday holiday;

	static{

		holiday = Holiday.NONE;

		final Calendar calendar = Calendar.getInstance();
		switch(calendar.get(Calendar.MONTH)){
			case Calendar.JANUARY:
				if (calendar.get(Calendar.WEEK_OF_MONTH) == 1)
					holiday = Holiday.XMAS;
				if (calendar.get(Calendar.DAY_OF_MONTH) >=18)
					holiday = Holiday.SPRING;
				break;
			case Calendar.FEBRUARY:
				if(calendar.get(Calendar.DAY_OF_MONTH)<=28)
					holiday = Holiday.SPRING;
				break;
			case Calendar.APRIL:
					holiday = Holiday.EASTER;
				break;
			case Calendar.MAY:
				if(calendar.get(Calendar.DAY_OF_MONTH)<=7)
					holiday = Holiday.WORKER;
				break;
			case Calendar.JUNE:
				if(calendar.get(Calendar.DAY_OF_MONTH)<=3)
					holiday = Holiday.CHILD;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
				   holiday = Holiday.STUDENT;
				   break;
			case Calendar.OCTOBER:
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 2)
					holiday = Holiday.HWEEN;
				break;
			case Calendar.NOVEMBER:
				if (calendar.get(Calendar.DAY_OF_MONTH) == 1)
					holiday = Holiday.HWEEN;
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 2)
					holiday = Holiday.THANK;
				break;
			case Calendar.DECEMBER:
				if (calendar.get(Calendar.WEEK_OF_MONTH) <= 1)
					holiday = Holiday.THANK;
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 3)
					holiday = Holiday.XMAS;
				break;
		}
	}

	{
		switch(holiday){
			case NONE:
				name = Messages.get(this, "pasty");
				image = ItemSpriteSheet.PASTY;
				break;
			case SPRING:
				name = Messages.get(this,"assorted");
				image = ItemSpriteSheet.SPRING_ASSORTED;
				break;
			case EASTER:
				name = Messages.get(this,"egg");
				image = ItemSpriteSheet.EASTER_EGG;
				break;
			case STUDENT:
				name = Messages.get(this,"book");
				image = ItemSpriteSheet.KNOWNLADGE_FOOD;
				break;
			case HWEEN:
				name = Messages.get(this, "pie");
				image = ItemSpriteSheet.PUMPKIN_PIE;
				break;
			case THANK:
				name = Messages.get(this, "turkey");
				image = ItemSpriteSheet.TURKEY_MEAT;
				break;
			case XMAS:
				name = Messages.get(this, "cane");
				image = ItemSpriteSheet.CANDY_CANE;
				break;
			case WORKER:
				name = Messages.get(this, "bread");
				image = ItemSpriteSheet.BRICK;
				break;
			case CHILD:
				name = Messages.get(this, "jelly");
				image = ItemSpriteSheet.JELLY_SWORD;
				break;
		}

		energy = 400;

		bones = true;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)){
			switch(holiday){
				case NONE:
					break; //do nothing extra
				case SPRING:
					Buff.affect(hero, BerryRegeneration.class).level(5);
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
					break;
				case EASTER:
					Buff.affect(hero, Bless.class,5f);
					hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
					break;
				case STUDENT:
					Buff.affect(hero, Light.class,50f);
					hero.sprite.emitter().start(FlameParticle.FACTORY, 0.2f, 3);
					break;
				case HWEEN:
					//heals for 10% max hp
					hero.HP = Math.min(hero.HP + hero.HT/10, hero.HT);
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
					break;
				case THANK:
				    Buff.affect(hero, Haste.class,5f);
					Buff.affect(hero, Levitation.class,5f);
				    break;
				case XMAS:
					Buff.affect( hero, Recharging.class, 2f ); //half of a charge
					ScrollOfRecharging.charge( hero );
					break;
				case WORKER:
					Dungeon.gold +=500;
					GLog.p(Messages.get(Pasty.class,"worker"));
					break;
				case CHILD:
					hero.HP = hero.HT;
					hero.HT +=3;
					Buff.affect(hero,Blindness.class, 20f);
					Buff.affect(hero,Vertigo.class, 20f);
					break;
			}
		}
	}

	@Override
	public String info() {
		switch(holiday){
			case NONE: default:
				return Messages.get(this, "pasty_desc");
			case SPRING:
				return Messages.get(this, "assorted_desc");
			case EASTER:
				return Messages.get(this, "egg_desc");
			case STUDENT:
				return Messages.get(this, "book_desc");
			case HWEEN:
				return Messages.get(this, "pie_desc");
			case THANK:
			    return Messages.get(this, "turkey_desc");
			case XMAS:
				return Messages.get(this, "cane_desc");
			case WORKER:
				return Messages.get(this, "bread_desc");
			case CHILD:
				return Messages.get(this, "jelly_desc");
		}
	}
	
	@Override
	public int price() {
		return 100 * quantity;
	}
}

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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.ui.BuffIndicator;

import java.text.DecimalFormat;
import java.util.HashSet;

public class Buff extends Actor {

	public Char target;

	public String status() { return null; }

	public enum buffType {POSITIVE, NEGATIVE, NEUTRAL, SILENT}

    public buffType type = buffType.SILENT;

	//public HashSet<Class<?>> resistances = new HashSet<Class<?>>();

	protected HashSet<Class> resistances = new HashSet<>();
	public HashSet<Class> resistances() {
		return new HashSet<>(resistances);
	}

	//public HashSet<Class<?>> immunities = new HashSet<Class<?>>();

	protected HashSet<Class> immunities = new HashSet<>();
	
	public HashSet<Class> immunities() {
		return new HashSet<>(immunities);
	}

	//public HashSet<Class<?>> weakness = new HashSet<Class<?>>();
	
    protected HashSet<Class> weakness = new HashSet<>();
	public HashSet<Class> weakness() {
		return new HashSet<>(weakness);
	}	
	
	public boolean attachTo(Char target) {

		//if (target.immunities().contains(getClass())) {
		if (target.isImmune( getClass() )) {
			return false;
		}

		this.target = target;
		target.add(this);

		if (target.buffs().contains(this)){
			if (target.sprite != null) fx( true );
			return true;
		} else
			this.target = null;
			return false;	
	}

	public void detach() {
		if (target.sprite != null) fx( false );
		target.remove(this);
	}

	@Override
	public boolean act() {
		diactivate();
		return true;
	}

	public int icon() {
		return BuffIndicator.NONE;
	}

	public void fx(boolean on) {
		//do nothing by default
	}

    public String heroMessage(){
		return null;
	}	
	
	public String desc(){
		return "";
	}

	//to handle the common case of showing how many turns are remaining in a buff description.
	protected String dispTurns(float input){
		return new DecimalFormat("#.##").format(input);
	}

	public static <T extends Buff> T append(Char target, Class<T> buffClass) {
		try {
			T buff = buffClass.newInstance();
			buff.attachTo(target);
			return buff;
		} catch (Exception e) {
			//ShatteredPixelDungeon.reportException(e);
			return null;
		}
	}

	public static <T extends FlavourBuff> T append(Char target,
			Class<T> buffClass, float duration) {
		T buff = append(target, buffClass);
		buff.spend(duration * target.resist(buffClass)*target.weak(buffClass)*target.RingFix(buffClass));
		//buff.spend(duration);
		return buff;
	}

	public static <T extends Buff> T affect(Char target, Class<T> buffClass) {
		T buff = target.buff(buffClass);
		if (buff != null) {
			return buff;
		} else {
			return append(target, buffClass);
		}
	}

	public static <T extends FlavourBuff> T affect(Char target,
			Class<T> buffClass, float duration) {
		T buff = affect(target, buffClass);
		buff.spend(duration * target.resist(buffClass)*target.weak(buffClass)*target.RingFix(buffClass));
		//buff.spend(duration );
		return buff;
	}

	public static <T extends FlavourBuff> T prolong(Char target,
			Class<T> buffClass, float duration) {
		T buff = affect(target, buffClass);
		buff.postpone(duration * target.resist(buffClass)*target.weak(buffClass)*target.RingFix(buffClass));
		//buff.spend(duration);
		return buff;
	}

	public static void detach(Buff buff) {
		if (buff != null) {
			buff.detach();
		}
	}

	public static void detach(Char target, Class<? extends Buff> cl) {
		detach(target.buff(cl));
	}
}

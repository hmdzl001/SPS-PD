package com.hmdzl.spspd;

import android.content.Context;

import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public enum GiftUnlocks {

	HT1(1), HT2(2), HT3(2), HT4(2), HT5(3), 
	HIT1(1),HIT2(2),HIT3(2),
	MISS1(1),MISS2(2),
	MAGIC1(1),
	EXP1(1),EXP2(2),
	START_FOOD1(1),START_FOOD2(2),
	START_WEAPON1(1),START_WEAPON2(1),
	START_ARMOR1(1),
	START_WAND1(1),
	START_SEED1(1),START_SEED2(2),
	START_SCROLL1(1),
	START_POTION1(1),
	START_GOLD1(1),START_GOLD2(2);

	
	private int size;
	private int price;
	String codeName;

	private static int coinleft = 0;

	private static Bundle bundle;
	GiftUnlocks(int price){
		this(price,9,"");
	}

	GiftUnlocks(int price, int size){
		this(price, size,"");
	}

	GiftUnlocks(int price, int size, String basename){
		this.size=size;
		if (basename.equals("")){
			basename=toString().toLowerCase();
		}
		codeName=basename;
		this.price=price;
	}


	public int id(){
		return (int)Math.pow(2,ordinal());
	}

	public int textSize(){
		return size;
	}

	public String dispName(){
		return Messages.get(this, codeName +".name");
	}

	public static final int MAX_VALUE           = 33554431;

	public static int price(GiftUnlocks id){
		return id.price;
	}

	public static GiftUnlocks[] unlockables(){
		return new GiftUnlocks[]{
				HT1, HT2, HT3, HT4, HT5,
				HIT1 ,HIT2 ,HIT3 ,
				MISS1 ,MISS2 ,
				MAGIC1 ,
				EXP1 ,EXP2 ,
				START_FOOD1 ,START_FOOD2 ,
				START_WEAPON1 ,START_WEAPON2 ,
				START_ARMOR1 ,
				START_WAND1 ,
				START_SEED1 ,START_SEED2 ,
				START_SCROLL1 ,
				START_POTION1 ,
				START_GOLD1 ,START_GOLD2 
		};
	}
	public boolean isUnlocked(){
		return (ShatteredPixelDungeon.unlocks()&id())!=0;
	}

	public static boolean isUnlocked(GiftUnlocks id){
		return (ShatteredPixelDungeon.unlocks()&id.id())!=0;
	}
	public static void unlock(GiftUnlocks id){
		ShatteredPixelDungeon.unlocks(ShatteredPixelDungeon.unlocks()|id.id());
	}

	public static void lock(GiftUnlocks id){
		if (isUnlocked(id)){
			ShatteredPixelDungeon.unlocks(ShatteredPixelDungeon.unlocks()^id.id());
		}
	}

	public static Bundle get(){
		try {
			InputStream input = Game.instance.openFileInput("Unlocks.dat");
			bundle = Bundle.read(input);
			input.close();
			return bundle;
		} catch (IOException e){
			bundle=new Bundle();
			return bundle;
		}
	}
	public static void save(){
		try {
			OutputStream output = Game.instance.openFileOutput("Unlocks.dat", Context.MODE_PRIVATE);
			Bundle.write(bundle, output);
			output.close();
		} catch (IOException ignored){
		}
	}

	public static int getInt( String key, int defValue ) {
		return getInt(key, defValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public static int getInt( String key, int defValue, int min, int max ) {
		try {
			int i;
			if (get().contains(key))
				i = get().getInt(key);
			else
				i=defValue;
			if (i < min || i > max){
				int val = (int) GameMath.gate(min, i, max);
				put(key, val);
				return val;
			} else {
				return i;
			}
		} catch (ClassCastException | NumberFormatException e) {
			ShatteredPixelDungeon.reportException(e);
			put(key, defValue);
			return defValue;
		}
	}

	public static boolean getBoolean( String key, boolean defValue ) {
		try {
			if (get().contains(key))
				return get().getBoolean(key);
			else
				return defValue;
		} catch (ClassCastException | NumberFormatException e) {
			ShatteredPixelDungeon.reportException(e);
			put(key, defValue);
			return defValue;
		}
	}

	public static String getString( String key, String defValue ) {
		return getString(key, defValue, Integer.MAX_VALUE);
	}

	public static String getString( String key, String defValue, int maxLength ) {
		try {
			String s;
			if (bundle.contains(key))
				s = get().getString( key );
			else
				s=defValue;
			if (s != null && s.length() > maxLength) {
				put(key, defValue);
				return defValue;
			} else {
				return s;
			}
		} catch (ClassCastException | NumberFormatException e) {
			ShatteredPixelDungeon.reportException(e);
			put(key, defValue);
			return defValue;
		}
	}

	public static void put( String key, int value ) {
		get().put(key, value);
		save();
	}

	public static void put( String key, boolean value ) {
		get().put( key, value );
		save();
	}

	public static void put( String key, String value ) {
		get().put(key, value);
		save();
	}
}
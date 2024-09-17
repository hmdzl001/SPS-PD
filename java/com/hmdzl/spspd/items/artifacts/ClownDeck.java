package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.sort;

/**
 * Created by dachhack on 10/15/2015.
 */
public class ClownDeck extends Artifact {

	{
		//name = "ClownDeck";
		image = ItemSpriteSheet.ARTIFACT_CLOWN_DECK;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = 0;
		chargeCap = 54;
		

		defaultAction = AC_DRAW;
	}

    public static final String AC_DRAW = "DRAW";
	public static final String AC_SHAKE = "SHAKE";
	private static final String AC_CHOOSE = "CHOOSE";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= 54 && !cursed) {
            actions.add(AC_DRAW);

        }
        if (level > 9)
			actions.add(AC_SHAKE);

		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals( AC_CHOOSE )) {
			GameScene.show(new WndItem(null, this, true));
		}
		if (action.equals(AC_DRAW)) {
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else if (charge < 54)
				GLog.i(Messages.get(this, "no_charge"));
			else {	
			    charge -= 54;
				Dungeon.depth.drop(new ScrollOfUpgrade().identify(), hero.pos).sprite.drop();
				Dungeon.depth.drop(Generator.random(Generator.Category.SCROLL).identify(), hero.pos).sprite.drop();
				if (level < levelCap) {level++;}
				hero.spend(1f);
				updateQuickslot();	
			}
			
		} else if (action.equals(AC_SHAKE)) {
			//checkresult();
			level = 0;

			Dungeon.hero.TRUE_HT = Dungeon.hero.TRUE_HT + (Random.Int(2, 5));
			Dungeon.hero.updateHT(true);

			switch (Random.Int(3)){
				case 0:
					Dungeon.hero.hitSkill +=2;
					Dungeon.hero.evadeSkill +=2;
					Dungeon.hero.magicSkill -=2;
					break;
				case 1:
					Dungeon.hero.hitSkill -=2;
					Dungeon.hero.evadeSkill +=2;
					Dungeon.hero.magicSkill +=2;
					break;
				case 2:
					Dungeon.hero.hitSkill +=2;
					Dungeon.hero.evadeSkill -=2;
					Dungeon.hero.magicSkill +=2;
					break;
			}


			 hero.spend(1f);
			 updateQuickslot();
		} 
		super.execute(hero, action);
	}
	
	public int level(){
		return level;
	}
	

	@Override
	protected ArtifactBuff passiveBuff() {
		return new deckRecharge();
	}

	@Override
	public String desc() {
		String desc = super.desc();
		if (isEquipped(Dungeon.hero)) {
			if (charge == 54)
				desc += "\n\n" + Messages.get(this,"full_charge");
		}
		return desc;
	}

	public class deckRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (cursed && Random.Int(15) == 0){
				Buff.affect(target, AttackDown.class,10f).level(30);
			}
			spend(TICK);
			return true;
		}
		public void gainCharge( ) {
			if (cursed) return;
			if (charge < 54) {
				charge += checkresult();
			}
		}
	}
	
	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}

    public static int checkresult() {
		int prizenum = 0;
		Integer[] CardDeck = {
				101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,
				201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,
				301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313,
				401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413
		};
		List<Integer> list = Arrays.asList(CardDeck);
		Collections.shuffle(list); // 打乱数组顺序
		List<Integer> selected = list.subList(0, 5);

		for (int i = 0; i < selected.size(); i++) {
			int number = selected.get(i);
			String spnum;
			if(number % 100 == 1){
				spnum = "A";
			} else if (number % 100 == 11){
				spnum = "J";
			} else if (number % 100 == 12){
				spnum = "Q";
			} else if (number % 100 == 13){
				spnum = "K";
			} else {spnum = null;}


			if (number/100 == 1 ) {
				GLog.i( "♥" + (spnum != null ? spnum : number%100 ) );
			} else if (number/100 == 2 ) {
				GLog.i( "♠" + (spnum != null ? spnum : number%100 ) );
			} else if (number/100 == 3 ) {
				GLog.i( "♦" + (spnum != null ? spnum : number%100 ) );
			} else if (number/100 == 4 ) {
				GLog.i( "♣" + (spnum != null ? spnum : number%100 ) );
			} else {
				GLog.i( "error"); // 非法数字的替代字符
			}
		}

		//GLog.b( selected.toString());

		ArrayList<Integer> colours = new ArrayList<>(Arrays.asList());
		ArrayList<Integer> shuzis = new ArrayList<>(Arrays.asList());

		sort(new List[]{selected});
		for (int card : selected) {
            int colour = card/100;
			int shuzi = card%100;
			colours.add(colour);
			shuzis.add(shuzi);
		}
		int[] resultcolour = new int[colours.size()];
		for (int i = 0; i < colours.size(); i++) {
			resultcolour[i] = colours.get(i);
		}

		int[] resultshuzi = new int[shuzis.size()];
		for (int i = 0; i < shuzis.size(); i++) {
			resultshuzi[i] = shuzis.get(i);
		}

		Arrays.sort(resultshuzi);
		Arrays.sort(resultcolour);
		boolean shunzil = true;
		for (int i = 1; i < resultshuzi.length; i++) {
			if (resultshuzi[i] - resultshuzi[i - 1] != 1) {
					shunzil = false;
			}
		}

        int sameshuzi = 0;
		int notsameshuzi = 0;
		boolean not2sameshuzi = true;
		for (int i = 1; i < resultshuzi.length; i++) {
			if (resultshuzi[i] - resultshuzi[i - 1] == 0) {
				sameshuzi ++;
				notsameshuzi ++;
			} else  {
				notsameshuzi = 0;
			}
			if ( i > 2) {
				if (resultshuzi[i] - resultshuzi[i - 2] == 0) {
					not2sameshuzi = false;
				}
			}
		}

		int samecolour = 0;
		for (int i = 1; i < resultcolour.length; i++) {
			if (resultcolour[i] - resultcolour[i - 1] == 0) {
				samecolour ++;
			}
		}

		if( shunzil == true && samecolour == 4){
			GLog.b(Messages.get(ClownDeck.class,"straightflush") );
			prizenum = 52;
		} else if ( shunzil == true ){
			GLog.b(Messages.get(ClownDeck.class,"straight"));
			prizenum = 13;
        } else if (samecolour == 4) {
			GLog.b(Messages.get(ClownDeck.class,"flush") );
			prizenum = 14;
		} else if ((notsameshuzi == 1 || notsameshuzi == 2 )&& sameshuzi == 3){
			GLog.b(Messages.get(ClownDeck.class,"fullhouse") );
			prizenum = 15;
		} else if (sameshuzi == 3 ){
			GLog.b(Messages.get(ClownDeck.class,"fourofakind") );
			prizenum = 26;
		} else if (sameshuzi == 2 && not2sameshuzi == true ){
			GLog.b(Messages.get(ClownDeck.class,"twopair") );
			prizenum = 3;
		} else if (sameshuzi == 2){
			GLog.b(  Messages.get(ClownDeck.class,"threeofakind") );
			prizenum = 3;
		} else if (sameshuzi == 1){
			GLog.b(Messages.get(ClownDeck.class,"pair") );
			prizenum = 2;
		} else if (sameshuzi == 0){
			GLog.b(Messages.get(ClownDeck.class,"highcard") );
			prizenum = 1;
		} else GLog.b(" ??? " );

		return prizenum;
	}

}

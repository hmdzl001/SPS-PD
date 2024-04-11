package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndIronMaker;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

/**
 * Created by dachhack on 10/15/2015.
 */
public class AlienBag extends Artifact {

	{
		//name = "AlienBag";
		image = ItemSpriteSheet.ARTIFACT_CAT_BAG;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = 0;
		partialCharge = 0;
		chargeCap = 100;
		

		defaultAction = AC_CHOOSE;
	}

    public static final String AC_SHIELD = "SHIELD";
	public static final String AC_BOMB = "BOMB";
	public static final String AC_BUILD = "BUILD";
	//public static final String AC_ETC = "ETC";
	private static final String AC_CHOOSE = "CHOOSE";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == 100 && !cursed) {
            actions.add(AC_SHIELD);

        }
        if (level > 2) actions.add(AC_BOMB);
		actions.add(AC_BUILD);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals( AC_CHOOSE )) {
			GameScene.show(new WndItem(null, this, true));
		}
		if (action.equals(AC_SHIELD)) {
   
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else if (charge != chargeCap)
				GLog.i(Messages.get(this, "no_charge"));
			else {	
			    charge = 0;
                Buff.affect(hero,EnergyArmor.class).level(level * 10);
				Buff.affect(hero,DefenceUp.class,20).level(level * 3);
				Buff.affect(hero, Invisibility.class, level * 3f);
				Buff.affect(hero, Levitation.class, level * 3f);
				Buff.affect(hero, HasteBuff.class, level * 3f);
				hero.spend(1f);
				updateQuickslot();	
			}
			
		} else if (action.equals(AC_BOMB)) {

			 for(int i=0; i<level/4; i++) {
				Dungeon.depth.drop(Generator.random(Generator.Category.BOMBS), hero.pos).sprite.drop();
				Dungeon.depth.drop(Generator.random(Generator.Category.HIGHFOOD), hero.pos).sprite.drop();
			 }
			 level-=2;
				hero.spend(1f);
				updateQuickslot();	
			
		} else if (action.equals(AC_BUILD)) {
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else {
				GameScene.show(new WndIronMaker());
			}
		}
		super.execute(hero, action);
	}
	
	public int level(){
		return level;
	}
	

	@Override
	protected ArtifactBuff passiveBuff() {
		return new bagRecharge();
	}

	@Override
	public String desc() {
		String desc = super.desc();
		if (isEquipped(Dungeon.hero)) {
			if (charge == 100)
				desc += "\n\n" + Messages.get(this,"full_charge");
		}
		return desc;
	}

	public class bagRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap) {
					partialCharge += 1f + level * 1f;
				if (partialCharge >= 10) {
					charge++;
					partialCharge = 0;
					if (charge == chargeCap) {
						partialCharge = 0;
					}

				}
			} else
				partialCharge = 0;

			
			updateQuickslot();

			spend(TICK);

			return true;
		}
		public void gainExp( ) {
			if (cursed) return;
			exp += 1;
			if (exp > 10+level()*5 && level() < levelCap){
				exp -= 10+level()*5;
				GLog.p( Messages.get(this, "levelup") );
				upgrade();
			}

		}
	}
	
	private static final String PARTIALCHARGE = "partialCharge";
	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(PARTIALCHARGE, partialCharge);
		bundle.put(CHARGE, charge);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		partialCharge = bundle.getInt(PARTIALCHARGE);
		charge = bundle.getInt(CHARGE);
	}
}

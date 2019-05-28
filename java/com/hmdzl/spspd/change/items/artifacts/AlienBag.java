package com.hmdzl.spspd.change.items.artifacts;

import java.io.IOException;
import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.buffs.Bless;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Dewcharge;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.Levitation;
import com.hmdzl.spspd.change.actors.buffs.MindVision;
import com.hmdzl.spspd.change.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.armor.normalarmor.ErrorArmor;
import com.hmdzl.spspd.change.items.wands.WandOfError;
import com.hmdzl.spspd.change.items.weapon.melee.special.ErrorW;
import com.hmdzl.spspd.change.items.weapon.missiles.ErrorAmmo;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.LoadSaveScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

/**
 * Created by dachhack on 10/15/2015.
 */
public class AlienBag extends Artifact {

	{
		//name = "AlienBag";
		image = ItemSpriteSheet.ALIEN_BAG;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = 0;
		partialCharge = 0;
		chargeCap = 100;
		

		defaultAction = AC_SHIELD;
	}

    public static final String AC_SHIELD = "SHIELD";
	public static final String AC_BOMB = "BOMB";
	public static final String AC_FLY = "FLY";
	public static final String AC_ETC = "ETC";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == 100 && !cursed) {
            actions.add(AC_SHIELD);
            actions.add(AC_FLY);
        }
        if (level > 1) actions.add(AC_BOMB);
		//if (level > 2 && !isEquipped(hero) )
			//actions.add(AC_ETC);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_SHIELD)) {
   
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else if (charge != chargeCap)
				GLog.i(Messages.get(this, "no_charge"));
			else {	
			    charge = 0;
                Buff.affect(hero,ShieldArmor.class).level(level * 20);
				hero.spend(1f);
				updateQuickslot();	
			}
			
		} else if (action.equals(AC_BOMB)) {
			    level--;
			for(int i=0; i<2; i++) {
            Dungeon.level.drop(Generator.random(Generator.Category.BOMBS), hero.pos).sprite.drop();
			}
				hero.spend(1f);
				updateQuickslot();	
			
		} else if (action.equals(AC_FLY)) {
			    charge = 0;
               	Buff.affect(hero, Invisibility.class, level * 5f);
				Buff.affect(hero, Levitation.class, level * 5f);
			    Buff.affect(hero, DefenceUp.class, level * 5f).level(level * 5);
				hero.spend(1f);
				updateQuickslot();	
		} else if (action.equals(AC_ETC)) {
			hero.spend(1f);
		}
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
		public void gainExp( float levelPortion ) {
			if (cursed) return;
			exp += Math.round(levelPortion*100);
			if (exp > 100+level()*50 && level() < levelCap){
				exp -= 100+level()*50;
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

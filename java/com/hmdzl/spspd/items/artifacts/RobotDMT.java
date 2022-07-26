package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.MindVision;
import com.hmdzl.spspd.actors.buffs.Strength;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.armor.normalarmor.ErrorArmor;
import com.hmdzl.spspd.items.wands.WandOfError;
import com.hmdzl.spspd.items.weapon.melee.special.ErrorW;
import com.hmdzl.spspd.items.weapon.missiles.ErrorAmmo;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.LoadSaveScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dachhack on 10/15/2015.
 */
public class RobotDMT extends Artifact {

	{
		//name = "Robot Determination";
		image = ItemSpriteSheet.RING_DISINTEGRATION;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = 0;
		partialCharge = 0;
		chargeCap = 100;
		

		defaultAction = AC_HEART;
	}

    public static final String AC_HEART = "HEART";
	public static final String AC_MEMORY = "MEMORY";
	public static final String AC_ERROR = "ERROR";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == 100 && !cursed)
			actions.add(AC_HEART);
		if (level > 9 && !isEquipped(hero) )
			actions.add(AC_MEMORY);
		if (Dungeon.error == true && !isEquipped(hero))
			actions.add(AC_ERROR);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_HEART)) {
   
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else if (charge != chargeCap)
				GLog.i(Messages.get(this, "no_charge"));
			else {	
			    charge = 0;
                if (level < 10 )level++;
				switch (Random.Int(level)){
					case 0:
						Buff.affect(hero, Invisibility.class,50);
						GLog.w(Messages.get(this,"patience"));
						break;
					case 1:
						Buff.affect(hero, AttackUp.class,100).level(20);
						Buff.affect(hero, DefenceUp.class,100).level(20);
						GLog.w(Messages.get(this,"bravery"));
						break;
					case 2:
						Buff.affect(hero, MindVision.class,80);
						GLog.w(Messages.get(this,"integrity"));
						break;
					case 3:
						Buff.affect(hero, Bless.class,50);
						GLog.w(Messages.get(this,"preseverance"));
						break;
					case 4:
						Buff.affect(hero, BerryRegeneration.class).level(20);
						GLog.w(Messages.get(this,"kindness"));
						break;
					case 5:
						Buff.affect(hero,Strength.class);
						GLog.w(Messages.get(this,"justice"));
						break;
					case 6:
						Buff.affect(hero,Dewcharge.class,100f);
						GLog.w(Messages.get(this,"soul"));
						break;
					case 7:
						//Buff.affect(hero,Dewcharge.class, 100f);
						GLog.w(Messages.get(this,"friendship"));
						break;
					case 8:
						//
						GLog.w(Messages.get(this,"chaos"));
                       Dungeon.error = true;
						break;
					default:
						//
						GLog.w(Messages.get(this,"determination"));
						break;
				}
				hero.spend(1f);
				updateQuickslot();	
			}
			
		} else if (action.equals(AC_MEMORY)) {
			//showPudding_cupScene(false);
			curUser = hero;
			detach(curUser.belongings.backpack);
			try {
				Dungeon.saveAll();
			} catch (IOException e) {
				//
			}
			Dungeon.canSave=true;
			Game.switchScene(LoadSaveScene.class);
		} 	else if (action.equals(AC_ERROR)) {
			curUser = hero;
            curUser.spendAndNext(1f);
			detach(curUser.belongings.backpack);
			Dungeon.error = false;
			Sample.INSTANCE.play(Assets.SND_BURNING);
			hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			switch (Random.Int(4)){
				case 0:
					Dungeon.level.drop(new ErrorW() ,Dungeon.hero.pos).sprite.drop();
					break;
				case 1:
					Dungeon.level.drop(new WandOfError(),Dungeon.hero.pos).sprite.drop();
					break;
				case 2:
					Dungeon.level.drop(new ErrorArmor(),Dungeon.hero.pos).sprite.drop();
					break;
				case 3:
					Dungeon.level.drop(new ErrorAmmo(3),Dungeon.hero.pos).sprite.drop();
					break;

			}
		}
	}
	
	public int level(){
		return level;
	}
	

	@Override
	protected ArtifactBuff passiveBuff() {
		return new dmtRecharge();
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

	public class dmtRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap) {
					partialCharge += 1f;
				if (partialCharge >= 5) {
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

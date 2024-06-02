package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GoldTouch;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by debenhame on 03/09/2014.
 */
public class MasterThievesArmband extends Artifact {

	{
		//name = "Master Thieves' Armband";
		image = ItemSpriteSheet.ARTIFACT_ARMBAND;

		//level = 0;
		levelCap = 5;

		charge = 0;
		partialCharge = 0;
		chargeCap = 1 + level;

		defaultAction = AC_STEAL;
	}

	public static final String AC_STEAL = "STEAL";
	public static final String AC_GOLDTOUCH = "GOLDTOUCH";

	@Override
	public String status() {
		if (levelKnown) {
			return charge + "/" + chargeCap;
		} else {
			return null;
		}
	}

	@Override
	public Item upgrade() {
		chargeCap = Math.min( 6, chargeCap + 1);
		return super.upgrade();
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
			if (isEquipped(hero) && charge > 0 && !cursed) 
				actions.add(AC_STEAL);
		if (!isEquipped(hero) && level > 1 && !cursed ) {
			actions.add(AC_GOLDTOUCH);
		}
		return actions;
	}	
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
	
		if (action.equals(AC_STEAL)){

			curUser = hero;

			if (!isEquipped( hero )) {
				GLog.i( Messages.get(Artifact.class, "need_to_equip") );
				usesTargeting = false;
				return;
			} else if (charge < 1) {
				GLog.i( Messages.get(this, "no_charge") );
				usesTargeting = false;
				return;
			} else if (cursed) {
				GLog.w( Messages.get(this, "cursed") );
				usesTargeting = false;
				return;
			} else {
				usesTargeting = true;
				GameScene.selectCell(targeter);
			}

		}	
		
        if (action.equals(AC_GOLDTOUCH)) {

				Buff.affect(hero, GoldTouch.class,level*5f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				hero.spend(1f);
				hero.busy();
				hero.sprite.operate(hero.pos);
				level-=1;
				
				//detach(curUser.belongings.backpack);
				//Dungeon.level.drop(Generator.random(Generator.Category.ARTIFACT),hero.pos);

		}
	}	
	

	private CellSelector.Listener targeter = new CellSelector.Listener(){

		@Override
		public void onSelect(Integer target) {

			if (target == null) {
				GLog.w(Messages.get(MasterThievesArmband.class, "no_target"));
			} else if (!Dungeon.depth.adjacent(curUser.pos, target) || Actor.findChar(target) == null){
				GLog.w( Messages.get(MasterThievesArmband.class, "no_target") );
			} else {
				final Char ch = Actor.findChar(target);
				//if (ch instanceof Shopkeeper){
				//	GLog.w( Messages.get(MasterThievesArmband.class, "steal_shopkeeper") );
			//	} else if (ch.alignment != Char.Alignment.ENEMY){
			//		GLog.w( Messages.get(MasterThievesArmband.class, "no_target") );
				//} else
					if (ch instanceof Mob) {
					curUser.busy();
					curUser.sprite.operate(target);
					Sample.INSTANCE.play(Assets.SND_HIT);
					if (((Mob) ch).firstitem == true) {
						Item loot = ((Mob) ch).SupercreateLoot();
						Dungeon.depth.drop(loot, curUser.pos).sprite.drop();
						((Mob) ch).firstitem = false;
					} else {Dungeon.depth.drop(new StoneOre(), curUser.pos).sprite.drop();}
									charge--;
									exp++;
									while (exp >= level && level < levelCap) {
										exp = 0;
										GLog.p(Messages.get(MasterThievesArmband.class, "level_up"));
										upgrade();
									}
									updateQuickslot();
									//curUser.next();
					    }
					}

			}
		@Override
		public String prompt() {
			return Messages.get(MasterThievesArmband.class, "prompt");
		}
	};	
	
	@Override
	protected ArtifactBuff passiveBuff() {
		return new Thievery();
	}

	
	
	@Override
	public String desc() {
		String desc = super.desc();

		if ( isEquipped (Dungeon.hero) )
			desc += "\n\n" + Messages.get(this, "desc_worn");

		return desc;

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
	
	public class Thievery extends ArtifactBuff {
		@Override
		public boolean act() {
			if (cursed && Dungeon.gold > 0 && Random.Int(5) == 0){
				Dungeon.gold--;
			}

			if (charge < chargeCap) {
				partialCharge += 1f;
				if (partialCharge >= 400f) {
					charge++;
					partialCharge = 0;
					if (charge == chargeCap) {
						partialCharge = 0;
					}

				}
			} else partialCharge = 0;


			updateQuickslot();

			spend(TICK);
			return true;
		}

		public void gainCharge() {
			if (cursed) return;

			if (charge < chargeCap){
				//float chargeGain = 3f * levelPortion;
				//chargeGain *= RingOfEnergy.artifactChargeMultiplier(target);

				partialCharge +=level ;
				while (partialCharge > 400f){
					partialCharge=0;
					charge++;
					updateQuickslot();

					if (charge == chargeCap){
						partialCharge = 0;
					}
				}

			} else {
				partialCharge = 0f;
			}
		}
		
		//public boolean steal(Item item){
			//	charge --;
			//	exp += 1;
			//	GLog.i(Messages.get(MasterThievesArmband.class, "stole_item", item.name()));
			//	while (exp >= level && level() < levelCap) {
			//		exp = 0;
			//		GLog.p(Messages.get(MasterThievesArmband.class, "level_up"));
			///		upgrade();
			//	}
		//		return true;
		//	}
		}




}




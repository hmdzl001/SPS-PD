package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.effects.particles.SnowParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.nornstone.NornStone;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by dachhack on 10/15/2015.
 */
public class EyeOfSkadi extends Artifact {

	{
		//name = "Eye of Skadi";
		image = ItemSpriteSheet.ICE_EYE;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = 0;
		partialCharge = 0;
		chargeCap = 100;
		

		defaultAction = AC_CURSE;
	}

	protected WndBag.Mode mode = WndBag.Mode.STONE;
	
	public static int consumedpts = 0;
	
	public static final String AC_BLAST = "BLAST";
	public static final String AC_ADD = "ADD";
    public static final String AC_CURSE = "CURSE";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == 100 && !cursed)
			actions.add(AC_CURSE);
		if (isEquipped(hero) && level < levelCap && !cursed)
			actions.add(AC_ADD);
		if (isEquipped(hero) && level > 1 && !cursed)
			actions.add(AC_BLAST);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_BLAST)) {
            curUser = hero;
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else {
				
				blast(hero.pos);
				level--;
				exp -=level;
				Sample.INSTANCE.play(Assets.SND_BURNING);
				updateQuickslot();
				//GLog.p("Blast!");
				CellEmitter.get(hero.pos).start(SnowParticle.FACTORY, 0.2f, 6);
				
			}
			
		} else if (action.equals(AC_ADD)) {
			GameScene.selectItem(itemSelector, mode,Messages.get(this, "prompt"));
		} else if (action.equals(AC_CURSE)) {
			curUser = hero;
			if (charge != chargeCap) {GLog.i(Messages.get(this, "no_charge"));}
			else GameScene.selectCell(curser);
		}
	}
	
	private CellSelector.Listener curser = new CellSelector.Listener(){

		@Override
		public void onSelect(Integer target) {
			if (target != null && (Dungeon.level.visited[target] || Dungeon.level.mapped[target])){

				if (Actor.findChar( target ) != null){
					Char mob = Actor.findChar(target);
				Buff.affect(mob,Poison.class).set(level*2f);
				Buff.affect(mob,FrostIce.class).level(level*4);
				Buff.affect(mob,ArmorBreak.class,level*4f).level(80);
				Buff.affect(mob,Chill.class,level*4f);
				charge = 0;
				curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				curUser.spendAndNext(1f);
				updateQuickslot();	
				
				} else {
					GLog.i( Messages.get(EtherealChains.class, "nothing_to_grab") );
				}

			}

		}

		@Override
		public String prompt() {
			return Messages.get(EtherealChains.class, "prompt");
		}
	};	

	public int level(){
		return level;
	}
	
	public void blast(int cell) {
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			
            CellEmitter.get(mob.pos).start(SnowParticle.FACTORY, 0.2f, 6);
			mob.damage(Random.Int(mob.HP/4,mob.HP/2 ), DamageType.ICE_DAMAGE);
			
			if (mob.isAlive()) {
			Buff.prolong(mob, Frost.class, Frost.duration(mob)* Random.Float(1f*level(), 1.5f*level()));
			}
			

		}	
		eyeUsed();
	}
	
	
	
	protected void eyeUsed() {
		
		updateQuickslot();

	}
	
	
	
	
	@Override
	protected ArtifactBuff passiveBuff() {
		return new eyeRecharge();
	}

	@Override
	public String desc() {
		String desc = super.desc();
		if (isEquipped(Dungeon.hero)) {
			if (charge < 100)
				desc += "\n\n" + Messages.get(this,"need_charge");
			else
				desc += "\n\n" + Messages.get(this,"full_charge");
		}

		return desc;
	}

	public class eyeRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap && !cursed ) {
				
					partialCharge += 1+level;

				if (partialCharge >= 10) {
					charge++;
					partialCharge = 0;
					if (charge == chargeCap) {
						partialCharge = 0;
					}

				}
			} else if (cursed && Random.Int(100) == 0){
				Buff.prolong( target, Frost.class, 5f);
			} else
				partialCharge = 0;

			
			updateQuickslot();

			spend(TICK);

			return true;
		}

	}
	
	private static final String PARTIALCHARGE = "partialCharge";
	private static final String CHARGE = "charge";
	private static final String CONSUMED = "consumedpts";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(PARTIALCHARGE, partialCharge);
		bundle.put(CHARGE, charge);
		bundle.put(CONSUMED, consumedpts);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		partialCharge = bundle.getInt(PARTIALCHARGE);
		charge = bundle.getInt(CHARGE);
		consumedpts = bundle.getInt(CONSUMED);
	}
	
	protected static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof StoneOre) {
				Hero hero = Dungeon.hero;
				consumedpts += 1;
			
				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(2f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				item.detach(hero.belongings.backpack);
				GLog.h(Messages.get(EyeOfSkadi.class, "exp",consumedpts));
				
				int levelChk = curItem.level + 1 ;
								
				if (consumedpts > levelChk && curItem.level<10) {
					curItem.upgrade();
					GLog.p(Messages.get(EyeOfSkadi.class, "infuse_ore"));
				}

		} else if (item != null && item instanceof NornStone) {
				Hero hero = Dungeon.hero;
				consumedpts += 5;

				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(2f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				item.detach(hero.belongings.backpack);
				GLog.h(Messages.get(EyeOfSkadi.class, "exp", consumedpts));

				int levelChk = curItem.level + 1;

				if (consumedpts > levelChk && curItem.level < 10) {
					curItem.upgrade();
					GLog.p(Messages.get(EyeOfSkadi.class, "infuse_ore"));
				}
			}
	 }
	};
	
}

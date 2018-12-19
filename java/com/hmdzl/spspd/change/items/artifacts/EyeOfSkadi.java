package com.hmdzl.spspd.change.items.artifacts;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.DeathRay;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.effects.particles.PurpleParticle;
import com.hmdzl.spspd.change.effects.particles.SnowParticle;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

/**
 * Created by dachhack on 10/15/2015.
 */
public class EyeOfSkadi extends Artifact {

	{
		//name = "Eye of Skadi";
		image = ItemSpriteSheet.RING_FROST;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = 0;
		partialCharge = 0;
		chargeCap = 100;
		

		defaultAction = AC_BLAST;
	}

	protected WndBag.Mode mode = WndBag.Mode.ALL;
	
	public static int consumedpts = 0;
	
	public static final String AC_BLAST = "BLAST";
	public static final String AC_ADD = "ADD";


	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == 100 && !cursed)
			actions.add(AC_BLAST);
		if (isEquipped(hero) && level < levelCap && !cursed)
			actions.add(AC_ADD);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_BLAST)) {
   
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else if (charge != chargeCap)
				GLog.i(Messages.get(this, "no_charge"));
			else {
				
				blast(hero.pos);
				charge = 0;
				updateQuickslot();
				//GLog.p("Blast!");
				CellEmitter.get(hero.pos).start(SnowParticle.FACTORY, 0.2f, 6);
				
			}
			
		} else if (action.equals(AC_ADD)) {
			GameScene.selectItem(itemSelector, mode,Messages.get(this, "prompt"));
		}
	}
	
	private int distance() {
		return (level() * 2)+1;
	}
	
	public int level(){
		return level;
	}
	
	public void blast(int cell) {
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			int dist = Level.distance(cell, mob.pos);
			 if (dist<=distance()){
			    mob.damage(Random.Int(level(),level()*level()+1), this);
			    Buff.prolong(mob, Frost.class, Frost.duration(mob)* Random.Float(1f*level(), 1.5f*level()));
				CellEmitter.get(mob.pos).start(SnowParticle.FACTORY, 0.2f, 6);
			 } 
		}	
		ringUsed();
	}
	
	
	
	protected void ringUsed() {
		
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
				
				int levelChk = curItem.level*2 + 1 ;
								
				if (consumedpts > levelChk && curItem.level<10) {
					curItem.upgrade();
					GLog.p(Messages.get(EyeOfSkadi.class, "infuse_ore"));
				}

		}
	 }
	};
	
}

package com.hmdzl.spspd.change.items.artifacts;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.GlassShield;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

/**
 * Created by dachhack on 10/15/2015.
 */
public class GlassTotem extends Artifact {

	{
		//name = "Glass Totem";
		image = ItemSpriteSheet.GLASSTOTEM;

		level = 0;
		levelCap = 10;

		defaultAction = AC_ATK;
	}

    public static final String AC_ATK = "ATK";
	public static final String AC_DEF = "DEF";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero)&& !cursed){
		actions.add(AC_ATK);}
		if (isEquipped(hero) && level > 1 && !cursed)
		actions.add(AC_DEF);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_ATK)) {
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else if (cursed)        
				GLog.i( Messages.get(Artifact.class, "cursed") );
			else {
                if (level<10)level++;
				Buff.affect(hero, AttackUp.class,200).level(75);
				Buff.affect(hero, ArmorBreak.class,200).level(75);
				hero.spend(1f);
				hero.busy();
				hero.sprite.operate(hero.pos);				
				updateQuickslot();	
			}
			
		} else if (action.equals(AC_DEF)) {
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else {	
                if (level > 1 )level--;
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				Buff.detach(hero, AttackUp.class);
				Buff.affect(hero, GlassShield.class).turns(2);
				hero.spend(3f);
				hero.busy();
				hero.sprite.operate(hero.pos);
				updateQuickslot();	
			}
		}
	}
	
	//public int level(){
		//return level;
	//}
	

	@Override
	protected ArtifactBuff passiveBuff() {
		return new glassRecharge();
	}

	@Override
	public String desc() {
		String desc = super.desc();
		return desc;
	}

	public class glassRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (cursed && Random.Int(100) == 0){
				Buff.affect( target, ArmorBreak.class, 10f).level(100);
			} else if (Random.Int(1000/(level+1)) == 0){
				Buff.affect( target, AttackUp.class, 5f).level(20);
				Buff.affect( target, DefenceUp.class, 5f).level(20);
			}
			spend( TICK );
			return true;
		}
	}
	
	/*private static final String PARTIALCHARGE = "partialCharge";
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
	}*/
}

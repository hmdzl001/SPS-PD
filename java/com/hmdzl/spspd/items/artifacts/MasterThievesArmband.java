package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GoldTouch;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by debenhame on 03/09/2014.
 */
public class MasterThievesArmband extends Artifact {

	{
		//name = "Master Thieves' Armband";
		image = ItemSpriteSheet.ARTIFACT_ARMBAND;

		level = 0;
		levelCap = 10;

		charge = 0;
	}

	private int exp = 0;
	public static final String AC_GOLDTOUCH = "GOLDTOUCH";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (!isEquipped(hero) && level > 1 && !cursed )
		actions.add(AC_GOLDTOUCH);		
		return actions;
	}	
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
        if (action.equals(AC_GOLDTOUCH)) {

				Buff.affect(hero, GoldTouch.class,level*10f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				hero.spend(1f);
				hero.busy();
				hero.sprite.operate(hero.pos);
				detach(curUser.belongings.backpack);
				Dungeon.level.drop(Generator.random(Generator.Category.ARTIFACT),hero.pos);

		}
	}	
	
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

	public class Thievery extends ArtifactBuff {
		public void collect(int gold) {
			if (!cursed) {
				charge += gold/2;
			}
		}

		@Override
		public void detach() {
			charge *= 0.95;
			super.detach();
		}

		@Override
		public boolean act() {
			if (cursed) {

				if (Dungeon.gold > 0 && Random.Int(6) == 0){
					Dungeon.gold--;
				}

				spend(TICK);
				return true;
			} else {
				return super.act();
			}
		}

		public boolean steal(int value) {
			if (value <= charge) {
				charge -= value;
				exp += value;
			} else {
				float chance = stealChance(value);
				//if (Random.Float() > chance)
					//return false;
				//else {
					if (chance <= 1)
						charge = 0;
					else
						// removes the charge it took you to reach 100%
						charge -= charge / chance;
					exp += value;
				//}
			}
			while (exp >= 1000 && level < levelCap) {
				exp -= 1000;
				upgrade();
			}
			return true;
		}

		public float stealChance(int value) {
			// get lvl*100 gold or lvl*5% item value of free charge, whichever
			// is less.
			//int chargeBonus = Math.min(level * 100, (value * level) / 20);
			int chargeBonus = Math.min(level * 100, (value * level) / 20);
			return (((float) charge + chargeBonus) / value);
		}
	}
}

package com.hmdzl.spspd.items.artifacts;

import java.util.ArrayList;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.ForeverShadow;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;

/**
 * Created by debenhame on 25/08/2014.
 */
public class CloakOfShadows extends Artifact {

	{
		//name = "Cloak of Shadows";
		image = ItemSpriteSheet.ARTIFACT_CLOAK;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = Math.min(level+3, 10);
		partialCharge = 0;
		chargeCap = Math.min(level+3, 10);

		defaultAction = AC_STEALTH;

		 
	}

	private boolean stealthed = false;

	public static final String AC_STEALTH = "STEALTH";
	public static final String AC_SHADOW = "SHADOW";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge > 1)
			actions.add(AC_STEALTH);
		if (isEquipped(hero) && level > 3 && !cursed)
		actions.add(AC_SHADOW);		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_STEALTH)) {

			if (!stealthed) {
				if (!isEquipped(hero))
					GLog.i(Messages.get(Artifact.class, "need_to_equip"));
				else if (cooldown > 0)
					GLog.i(Messages.get(this, "cooldown", cooldown) );
				else if (charge <= 1)
					GLog.i( Messages.get(this, "no_charge") );
				else {
					stealthed = true;
					hero.spend(1f);
					hero.busy();
					Sample.INSTANCE.play(Assets.SND_MELD);
					activeBuff = activeBuff();
					activeBuff.attachTo(hero);
					if (hero.sprite.parent != null) {
						hero.sprite.parent.add(new AlphaTweener(hero.sprite,
								0.4f, 0.4f));
					} else {
						hero.sprite.alpha(0.4f);
					}
					hero.sprite.operate(hero.pos);
				}
			} else {
				stealthed = false;
				activeBuff.detach();
				activeBuff = null;
				hero.spend( 1f );
				hero.sprite.operate(hero.pos);
			}
		} else if (action.equals(AC_SHADOW)) {
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else {	
                if (level > 3 )level-=3;
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				Buff.affect(hero, ForeverShadow.class,(level+3)*10f);
				hero.spend(1f);
				hero.busy();
				hero.sprite.operate(hero.pos);
				updateQuickslot();	
			}
		} else
			super.execute(hero, action);
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		if (stealthed) {
			activeBuff = activeBuff();
			activeBuff.attachTo(ch);
		}
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {
			stealthed = false;
			return true;
		} else
			return false;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new cloakRecharge();
	}

	@Override
	protected ArtifactBuff activeBuff() {
		return new cloakStealth();
	}

	@Override
	public Item upgrade() {
		chargeCap = Math.min(chargeCap + 1, 10);
		return super.upgrade();
	}

	private static final String STEALTHED = "stealthed";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(STEALTHED, stealthed);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		stealthed = bundle.getBoolean(STEALTHED);
		if (bundle.contains("cooldown")){
			exp = 0;
			level=(int)Math.ceil(level*0.7f);
			charge = chargeCap = Math.min(3 + level, 10);
		}
	}

	public class cloakRecharge extends ArtifactBuff {
		@Override
		public boolean act() {
			if (charge < chargeCap) {
				if (!stealthed ) {
					float turnsToCharge = (50 - (chargeCap - charge));
					if (level() > 7) turnsToCharge -= 10*(level() - 7)/3f;
					partialCharge += (1f / turnsToCharge);
				}

				if (partialCharge >= 1) {
					charge++;
					partialCharge -= 1;
					if (charge == chargeCap){
						partialCharge = 0;
					}

				}
			} else
				partialCharge = 0;

			if (cooldown > 0)
				cooldown --;

			updateQuickslot();

			spend( TICK );

			return true;
		}


	}


	public class cloakStealth extends ArtifactBuff {
		int turnsToCost = 0;
		@Override
		public int icon() {
			return BuffIndicator.INVISIBLE;
		}

		@Override
		public boolean attachTo( Char target ) {
			if (super.attachTo( target )) {
				target.invisible++;
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean act(){
			turnsToCost--;
			
			if (turnsToCost <= 0){
				charge--;
				if (charge < 0) {
					charge = 0;
					detach();
					GLog.w(Messages.get(this, "no_charge"));
					((Hero) target).interrupt();
				} else {
					//target hero level is 1 + 2*cloak level
					int lvlDiffFromTarget = ((Hero) target).lvl - (1+level()*2);
					//plus an extra one for each level after 6
					if (level() >= 7){
						lvlDiffFromTarget -= level()-6;
					}
					if (lvlDiffFromTarget >= 0){
						exp += Math.round(10f * Math.pow(1.1f, lvlDiffFromTarget));
					} else {
						exp += Math.round(10f * Math.pow(0.75f, -lvlDiffFromTarget));
					}
					
					if (exp >= (level() + 1) * 50 && level() < levelCap) {
						upgrade();
						exp -= level() * 50;
						GLog.p(Messages.get(this, "levelup"));
						
					}
					turnsToCost = 5;
				}
				updateQuickslot();
			}

			spend( TICK );

			return true;
		}
		
		public void dispel(){
			updateQuickslot();
			detach();
		}

		@Override
		public void fx(boolean on) {
			if (on) target.sprite.add( CharSprite.State.INVISIBLE );
			else if (target.invisible == 0) target.sprite.remove( CharSprite.State.INVISIBLE );
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc");
		}

		@Override
		public void detach() {
			if (target.invisible > 0)
				target.invisible--;
			stealthed = false;
			updateQuickslot();
			super.detach();
		}
	}
}

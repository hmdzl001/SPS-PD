
package com.hmdzl.spspd.items.weapon.rockcode;


import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.MagicMissile;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.melee.start.XSaber;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.QuickSlotButton;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public abstract class RockCode extends Item {

	public static final String AC_ZAP	= "ZAP";

	public String sname;

	private static final float TIME_TO_ZAP	= 1f;

    public int maxEnergy = initialEnergy();
    public int curEnergy = maxEnergy;

	protected boolean hitChars = true;

	protected int collisionProperties = Ballistica.MAGIC_BOLT;

	{
		//stackable=false;
		image = ItemSpriteSheet.POCKETBALL_EMPTY;
		defaultAction = AC_ZAP;
		usesTargeting = true;

	}

	public abstract void onHit(XSaber megap, Char attacker, Char defender, int damage);
		
	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (curEnergy > 0) {
			actions.add( AC_ZAP );
		}

		return actions;
	}


	@Override
	public void execute( Hero hero, String action ) {
		if (action.equals( AC_ZAP )) {
			curUser = hero;
			curItem = this;
			GameScene.selectCell( shooter );
		} else {
			
			super.execute( hero, action );
			
		}
	}		
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder( super.toString() );
		
		String status = status();
		if (status != null) {
			sb.append( " (" + status +  ")" );
		}
		
		return sb.toString();
	}

    @Override
    public boolean collect( Bag container ) {
        if (super.collect( container )) {
            return true;
        } else {
            return false;
        }
    }
	@Override
	public String status() {
		return curEnergy + "/" + maxEnergy;
	}

	@Override
	public String info() {
		String desc = desc();

		desc += "\n\n" + statsDesc();

		return desc;
	}

	public String statsDesc(){
		return Messages.get(this, "stats_desc");
	}

	protected void fx( Ballistica bolt, Callback callback ) {
		MagicMissile.whiteLight( curUser.sprite.parent, bolt.sourcePos, bolt.collisionPos, callback );
		Sample.INSTANCE.play( Assets.SND_ZAP );
	}

    protected int initialEnergy() {
        return 4;
    }

    protected int EnergyPerCast() {
        return 1;
    }


    protected void codeUsed() {
        curEnergy -= EnergyPerCast();
        updateQuickslot();
        curUser.spendAndNext(TIME_TO_ZAP);
	}
	
    protected abstract void onZap( Ballistica attack );

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int price() {
		return 100 * quantity;
	}

    private static final String CUR_ENERGY		= "energy";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(CUR_ENERGY, curEnergy);
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		curEnergy = bundle.getInt(CUR_ENERGY);
	}
	
	protected static CellSelector.Listener shooter = new  CellSelector.Listener() {

		@Override
		public void onSelect( Integer target ) {

			if (target != null) {

				final RockCode rockcode = (RockCode)RockCode.curItem;

				final Ballistica shot = new Ballistica( curUser.pos, target, rockcode.collisionProperties);
				int cell = shot.collisionPos;

				if (target == curUser.pos || cell == curUser.pos) {
					GLog.i( Messages.get(Wand.class, "self_target") );
					return;
				}

				curUser.sprite.zap(cell);

				//attempts to target the cell aimed at if something is there, otherwise targets the collision pos.
				if (Actor.findChar(target) != null)
					QuickSlotButton.target(Actor.findChar(target));
				else
					QuickSlotButton.target(Actor.findChar(cell));

				if (rockcode.curEnergy >= 1) {

					curUser.busy();

					rockcode.fx(shot, new Callback() {
						@Override
						public void call() {
							rockcode.onZap(shot);
							rockcode.codeUsed();
						}
					});

					Invisibility.dispel();

				} else {

					GLog.w( Messages.get(Wand.class, "fizzles") );

				}

			}
		}

		@Override
		public String prompt() {
			return Messages.get(Wand.class, "prompt");
		}
	};	
	
}	
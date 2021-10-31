/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.items.nornstone.NornStone;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PowerHandScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;

import java.io.IOException;
import java.util.ArrayList;

public class PowerHand extends Item {

	public static final String AC_ADD = "ADD";

    public static final String AC_USE = "USE";
    protected HandCharger handcharger;

	{
		//name = "PowerHand";
		image = ItemSpriteSheet.POWER_HAND;

	}
	
    protected WndBag.Mode mode = WndBag.Mode.STONE;
	
	public ArrayList<String> stones = new ArrayList<String>();

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_ADD);
		actions.add(AC_USE);
		return actions;
	}

    @Override
    public boolean collect( Bag container ) {
        if (super.collect( container )) {
            if (container.owner != null) {
                charge( container.owner );
            }
            return true;
        } else {
            return false;
        }
    }

    public void charge( Char owner ) {
        if (handcharger == null) handcharger = new HandCharger();
        handcharger.attachTo( owner );
    }

    @Override
    public void onDetach( ) {
        stopCharging();
    }

    public void stopCharging() {
        if (handcharger != null) {
            handcharger.detach();
            handcharger = null;
        }
    }

    @Override
	public void execute(Hero hero, String action) {
		if (action.equals( AC_USE )) {
		    if (stones.size() >= 5){
                 showPowerHandScene();
			} else {
		    	GLog.i(Messages.get(PowerHand.class, "nothing"));
			}
		} else if (action.equals( AC_ADD )) {
			GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));
		} else {
			super.execute(hero, action);

		}
	}
	
	@Override
	public String desc() {
		String desc = super.desc();


		if (stones.size() > 0) {

			desc += "\n\n" + Messages.get(this, "desc_stones", stones.size());
		}

		return desc;
	}

	private void showPowerHandScene() {
		try {
			stones.clear();
			Dungeon.saveAll();
			Game.switchScene(PowerHandScene.class);
		} catch (IOException e) {
		}
	}

	private static final String STONES =   "stones";

	//@Override
	//public void storeInBundle( Bundle bundle ) {
	//	super.storeInBundle(bundle);
	//	bundle.put( STONES, stones.toArray(new Class[stones.size()]) );
	//}

	//@Override
	//public void restoreFromBundle( Bundle bundle ) {
	//	super.restoreFromBundle(bundle);
	//	if (bundle.contains(STONES))
	//		Collections.addAll(stones, bundle.getStringArray(STONES));
	//}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	
	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof NornStone) {
				if (stones.contains(item.name()) || item instanceof StoneOre) {
					GLog.w(Messages.get(PowerHand.class, "already_fed"));
				} else {
					stones.add(item.name());
					
					Hero hero = Dungeon.hero;
					hero.sprite.operate(hero.pos);
					Sample.INSTANCE.play(Assets.SND_PLANT);
					hero.busy();
					hero.spend(2f);
					GLog.i(Messages.get(PowerHand.class, "absorb_stone"));
					item.detach(hero.belongings.backpack);
					}

				}
			}
		};

    protected class HandCharger extends Buff {

        @Override
        public boolean attachTo( Char target ) {
            super.attachTo( target );

            return true;
        }

        @Override
        public boolean act() {
            spend( TICK );
            return true;
        }
    }
}

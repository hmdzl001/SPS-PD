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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.HighLight;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.summon.FairyCard;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class UndeadBook extends Item {

    public static final String AC_READ = "READ";

	public static final String AC_READ2 = "READ2";
    
	public static final String AC_BLESS = "BLESS";

	{
		//name = "UndeadBook";
		image = ItemSpriteSheet.UNDEAD_BOOK;

		unique = true;
        charge = 0;
		//defaultAction = AC_READ;
	}

	public static int charge;
	private static final String CHARGE = "charge";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (charge > 10) actions.add(AC_READ);
		if (hero.TRUE_HT > 10) actions.add(AC_READ2);
		if (Dungeon.hero.spp < hero.lvl) actions.add(AC_BLESS);
		return actions;
	}
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}		
	

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_READ)) {
			charge-=10;
			switch (Random.Int(4)) {
				case 0:
					Buff.affect(hero, Levitation.class, 10f);
					Buff.affect(hero, HighLight.class, 10f);
					break;
				case 1:
					Buff.affect(hero, GlassShield.class).turns(2);
					Buff.affect(hero, EnergyArmor.class).level(hero.lvl*2);
					break;
				case 2:
					for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
						if (Level.fieldOfView[mob.pos]) {
							mob.damage(mob.HT/2, this );
						}
					}
					break;
				case 3:
					ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
					for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
						int p = curUser.pos + PathFinder.NEIGHBOURS8[i];
						if (Actor.findChar(p) == null && (Level.passable[p] || Level.avoid[p])) {
							spawnPoints.add(p);
						}
					}

					if (spawnPoints.size() > 0) {
						FairyCard.Fairy fairy = new FairyCard.Fairy();
						fairy.HP = Dungeon.hero.lvl*3;
						fairy.pos = Random.element(spawnPoints);
						GameScene.add(fairy);
					}
					break;
				default:
					break;
			}
			hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			Sample.INSTANCE.play(Assets.SND_BURNING);
			hero.spendAndNext(1f);
			updateQuickslot();

		}

		if (action.equals(AC_READ2)) {
			if (charge>50) charge-=50;
			else hero.TRUE_HT -= 5;

			Dungeon.hero.updateHT(false);
			Buff.affect(hero, Dewcharge.class).level(100);
			GLog.p(Messages.get(this, "bless"));

		}

		if (action.equals(AC_BLESS)) {
			    Dungeon.hero.spp ++;
                Dungeon.level.drop(new Ankh(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
				GLog.p(Messages.get(this, "1up"));
		} else {
			super.execute(hero, action);

		}
	}

	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(this, "charge",charge);
		return info;
	}


	@Override
	public String status() {
		return Messages.format("%d", charge);
	}


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
		return 50 * quantity;
	}

}

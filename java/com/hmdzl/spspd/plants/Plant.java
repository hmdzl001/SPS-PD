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
package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Barkskin;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.LeafParticle;
import com.hmdzl.spspd.items.Dewdrop;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.SandalsOfNature;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.PlantSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Plant implements Bundlable {

	public String plantName = Messages.get(this, "name");

	public int image;
	public int pos;

	public PlantSprite sprite;


	public void activate(Char ch) {

		if (ch instanceof Hero && ((Hero) ch).subClass == HeroSubClass.WARDEN) {
			Buff.affect(ch, Barkskin.class).level(((Hero) ch).lvl);
		}
		wither();
	}
	
	public void wither() {
		Dungeon.depth.uproot(pos);

		sprite.kill();
		if (Dungeon.visible[pos]) {
			CellEmitter.get(pos).burst(LeafParticle.GENERAL, 6);
		}

		if (Dungeon.hero.subClass == HeroSubClass.WARDEN) {

			int naturalismLevel = 0;
			SandalsOfNature.Naturalism naturalism = Dungeon.hero
					.buff(SandalsOfNature.Naturalism.class);
			if (naturalism != null) {
				naturalismLevel = naturalism.level() + 1;
			}

			if (Random.Int(5 - (naturalismLevel / 2)) == 0) {
				Item seed = Generator.random(Generator.Category.SEED);
					Dungeon.depth.drop(seed, pos).sprite.drop();
			}
			if (Random.Int(5 - naturalismLevel) == 0) {
				Dungeon.depth.drop(new Dewdrop(), pos).sprite.drop();
			}
		}
	}

	private static final String POS = "pos";

	@Override
	public void restoreFromBundle(Bundle bundle) {
		pos = bundle.getInt(POS);
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(POS, pos);
	}

	public String desc() {
		return Messages.get(this, "desc");
	}

	public static class Seed extends Item {

		public static final String AC_PLANT = "PLANT";

		private static final float TIME_TO_PLANT = 1f;

		{
			stackable = true;
			defaultAction = AC_THROW;
		}

		protected Class<? extends Plant> plantClass;
		protected Class<? extends Plant> explantClass;

		public Class<? extends Item> alchemyClass;

		@Override
		public ArrayList<String> actions(Hero hero) {
			ArrayList<String> actions = super.actions(hero);
			actions.add(AC_PLANT);
			return actions;
		}

		@Override
		protected void onThrow(int cell) {
			if (Dungeon.depth.map[cell] == Terrain.ALCHEMY
					|| Dungeon.depth.map[cell] == Terrain.IRON_MAKER
			        || Dungeon.depth.map[cell] == Terrain.TENT
					|| Floor.pit[cell]) {
				super.onThrow(cell);
			} else if (Dungeon.depth.map[cell] == Terrain.FLOWER_POT) {
				Dungeon.depth.explant(this, cell);
			} else {
				Dungeon.depth.plant(this, cell);
			}
		}

		@Override
		public void execute(Hero hero, String action) {
			if (action.equals(AC_PLANT)) {

				hero.spend(TIME_TO_PLANT);
				hero.busy();
				((Seed) detach(hero.belongings.backpack)).onThrow(hero.pos);

				hero.sprite.operate(hero.pos);
	
			} else {
				super.execute(hero, action);
			}
		}

		public Plant couch(int pos) {
			try {
				if (Dungeon.visible[pos]) {
					Sample.INSTANCE.play(Assets.SND_PLANT);
				}
				Plant plant = plantClass.newInstance();
				plant.pos = pos;
				return plant;
			} catch (Exception e) {
				return null;
			}
		}

		public Plant excouch(int pos) {
			try {
				if (Dungeon.visible[pos]) {
					Sample.INSTANCE.play(Assets.SND_PLANT);
				}
				Plant plant = explantClass.newInstance();
				plant.pos = pos;
				return plant;
			} catch (Exception e) {
				return null;
			}
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
			return 10 * quantity;
		}

		@Override
		public String desc() {
			return Messages.get(plantClass, "desc");
		}

		@Override
		public String info() {
			return Messages.get( Seed.class, "info", desc() );
		}
	}
}

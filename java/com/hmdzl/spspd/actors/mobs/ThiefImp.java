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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.ChaliceOfBlood;
import com.hmdzl.spspd.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.items.scrolls.ScrollOfRage;
import com.hmdzl.spspd.items.summon.Honeypot;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ThiefImpSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ThiefImp extends Mob {

	protected static final String TXT_STOLE = "%s stole %s from you!";
	protected static final String TXT_CARRIES = "\n\n%s is carrying a _%s_. Stolen obviously.";
	protected static final String TXT_RATCHECK1 = "Spork is avail";
	protected static final String TXT_RATCHECK2 = "Spork is not avail";

	public Item item;

	{
		spriteClass = ThiefImpSprite.class;

		HP = HT = 200+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 20+adj(1);

		EXP = 13;
		maxLvl = 35;

		flying = true;
		
		loot = Generator.Category.BERRY;
		lootChance = 0.05f;

		FLEEING = new Fleeing();
		
		properties.add(Property.DEMONIC);
	}

	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new PotionOfInvisibility(),new ScrollOfRage(),new ChaliceOfBlood());
	}

	private static final String ITEM = "item";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ITEM, item);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		item = (Item) bundle.get(ITEM);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 25+adj(0));
	}


	@Override
	public void die(Object cause) {
		
		super.die(cause);

		if (item != null) {
			Dungeon.level.drop(item, pos).sprite.drop();
		}
	}

	@Override
	protected Item createLoot() {
			return new Gold(Random.NormalIntRange(100, 250));
	}

	@Override
	public int hitSkill(Char target) {
		return 30+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(10, 20);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (item == null && enemy instanceof Hero && steal((Hero) enemy)) {
			state = FLEEING;
		}

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if (state == FLEEING) {
			Dungeon.level.drop(new Gold(), pos).sprite.drop();
		}

		return damage;
	}

	protected boolean steal(Hero hero) {

		Item item = hero.belongings.randomUnequipped();
		
		if (item != null && !item.unique && item.level < 1 ) {
			
			Dungeon.quickslot.clearItem( item );
			item.updateQuickslot();
			GLog.w(Messages.get(ThiefImp.class, "stole", item.name()));
			
			if (item instanceof Honeypot) {
				this.item = ((Honeypot) item).shatter(this, this.pos);
				item.detach(hero.belongings.backpack);
			} else {
				this.item = item.detach( hero.belongings.backpack );
				if (item instanceof Honeypot.ShatteredPot)
					((Honeypot.ShatteredPot) item).setHolder(this);
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public String description() {
		String desc = super.description();

		if (item != null) {
			desc += Messages.get(this, "carries", item.name() );
		}

		return desc;
	}

	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Mob.class, "rage"));
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
}

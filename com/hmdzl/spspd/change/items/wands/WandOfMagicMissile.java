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
package com.hmdzl.spspd.change.items.wands;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Beam;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.quest.DarkGold;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.hmdzl.spspd.change.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class WandOfMagicMissile extends Wand {

	public static final String AC_DISENCHANT = "DISENCHANT";

	private static final String TXT_SELECT_WAND = "Select a wand to upgrade";

	private static final String TXT_DISENCHANTED = "you disenchanted the Wand of Magic Missile and used its essence to upgrade your %s";

	private static final float TIME_TO_DISENCHANT = 2f;

	private boolean disenchantEquipped;
	
	private float upgradeChance = 0.5f;

	{
		image = ItemSpriteSheet.WAND_MAGIC_MISSILE;
		collisionProperties = Ballistica.MAGIC_BOLT;
	}

	//@Override
	//public ArrayList<String> actions(Hero hero) {
	//	ArrayList<String> actions = super.actions(hero);
	//	if (level > 0) {
	//		actions.add(AC_DISENCHANT);
	//	}
	//	return actions;
	//}

	@Override
	protected void onZap( Ballistica bolt ) {
				
		Char ch = Actor.findChar( bolt.collisionPos );
		if (ch != null) {

		    processSoulMark(ch, chargesPerCast());
			
            int damage= Random.Int(level()+2, 6 + level() * 5);
            if (Dungeon.hero.buff(Strength.class) != null){ damage *= (int) 4f; Buff.detach(Dungeon.hero, Strength.class);}
			ch.damage(damage, this);

			ch.sprite.burst(0xFF99CCFF, level / 2 + 2);

		}
	}

	//@Override
	//public void execute(Hero hero, String action) {
	//	if (action.equals(AC_DISENCHANT)) {

	//		if (hero.belongings.weapon == this) {
	//			disenchantEquipped = true;
	//			hero.belongings.weapon = null;
	//			updateQuickslot();
	//		} else {
	//			detach(hero.belongings.backpack);
	//		}

	//		curUser = hero;
	//		GameScene.selectItem(itemSelector, WndBag.Mode.WAND,
	//				TXT_SELECT_WAND);

	//	} else {

	//		super.execute(hero, action);

	//	}
	//}

	@Override
	protected int initialCharges() {
		return 3;
	}
	
	@Override
	public String statsDesc() {
		/*if (levelKnown)
			return Messages.get(this, "stats_desc", min(), max());
		else
			return Messages.get(this, "stats_desc", min(0), max(0));*/
		return Messages.get(this,"stats_desc");
	}
	//private final WndBag.Listener itemSelector = new WndBag.Listener() {
	//	@Override
	//	public void onSelect(Item item) {
	//		if (item != null) {

	//			Sample.INSTANCE.play(Assets.SND_EVOKE);
	//			ScrollOfUpgrade.upgrade(curUser);
	//			evoke(curUser);

	//			GLog.w(TXT_DISENCHANTED, item.name());

	//			Dungeon.quickslot.clearItem(WandOfMagicMissile.this);
	//			WandOfMagicMissile.this.updateQuickslot();
				
	//			DarkGold gold = Dungeon.hero.belongings.getItem(DarkGold.class);
	//			if (gold!=null){
	//			upgradeChance = (upgradeChance + (gold.quantity()*0.01f));
	//			}

	//			 int i=0;
	//				while(i<level) {
	//					if (i<2){
	//					  Sample.INSTANCE.play(Assets.SND_EVOKE);
	//					  ScrollOfUpgrade.upgrade(curUser);
	//					  evoke(curUser);
	//					  item.upgrade();
	//					} else if (Random.Float()<upgradeChance){
	//						if (item.level<15 || item.reinforced){
	//				            Sample.INSTANCE.play(Assets.SND_EVOKE);
	//				            ScrollOfUpgrade.upgrade(curUser);
	//				            evoke(curUser);
	//				            item.upgrade();
	//				            upgradeChance = Math.max(0.5f, upgradeChance-0.1f);
	//						 } else {
	//							 GLog.w("%s is not strong enough to recieve anymore upgrades!", item.name());
	//							 i=level;
	//						 }
	//				  }
	//				i++;
	//				}
	//			
	//			item.upgrade();
	//			curUser.spendAndNext(TIME_TO_DISENCHANT);

	//			Badges.validateItemLevelAquired(item);

	//		} else {
	//			if (disenchantEquipped) {
	//				curUser.belongings.weapon = WandOfMagicMissile.this;
	//				WandOfMagicMissile.this.updateQuickslot();
	//			} else {
	//				collect(curUser.belongings.backpack);
	//			}
	//		}
	//	}
	//};
}

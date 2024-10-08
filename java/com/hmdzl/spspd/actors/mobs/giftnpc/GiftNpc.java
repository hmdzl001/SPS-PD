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
package com.hmdzl.spspd.actors.mobs.giftnpc;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.NmGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.blobs.VenomGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndNewNpcMessage;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public abstract class GiftNpc extends NPC {

	{
		HP = HT = 1;
		EXP = 0;

		//WANDERING = new Wandering();
		//HUNTING = new Hunting();


		flying = true;
		hostile = false;
		//ally = true;

		state = PASSIVE;

		properties.add(Property.MINIBOSS);
		properties.add(Property.IMMOVABLE);
	}

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}

	protected void throwItem() {
		Heap heap = Dungeon.depth.heaps.get(pos);
		if (heap != null) {
			int n;
			do {
				n = pos + Floor.NEIGHBOURS8[Random.Int(8)];
			} while (!Floor.passable[n] && !Floor.avoid[n]);
			Dungeon.depth.drop(heap.pickUp(), n).type = Heap.Type.FOR_SALE;
		}
	}

	public boolean loveitem(Item item) {
		return false;
	}

	@Override
	public void damage(int dmg, Object src, int type) {

	}

	@Override
	public void add(Buff buff) {
	}


	@Override
	public boolean interact() {
		sprite.turnTo(pos, hero.pos);
		GameScene.show(new WndOptions(
				this.sprite(),
				Messages.get(this, "name"),
				Messages.get(this, "normal"),
				Messages.get(this, "talk"),
				Messages.get(this, "gift")) {
			@Override
			protected void onSelect(int index) {
				if (index == 0) {
					normaltalk();
				} else if (index == 1) {
					gift();
				}
			}
		});
		return true;
	}



	private void normaltalk() {
			switch (Random.Int(4)) {
				case 1:
					friend(this, Messages.get(this, "yell1"));
					break;
				case 2:
					friend(this, Messages.get(this, "yell2"));
					break;
				case 3:
					friend(this, Messages.get(this, "yell3"));
					break;
				case 0: default:
					friend(this, Messages.get(this, "yell4"));
					break;
		}
	}

	private void friend(NPC npc,String text ) {
		GameScene.show(new WndNewNpcMessage(npc,text));
	}

	{
		immunities.add( ToxicGas.class );
		immunities.add( VenomGas.class );
		immunities.add( Burning.class );
		immunities.add( ScrollOfPsionicBlast.class );
		immunities.add( CorruptGas.class );
		immunities.add( NmGas.class );
	}

	public WndBag gift() {
		return GameScene.selectItem(itemSelector, WndBag.Mode.NOTEQUIP,
				Messages.get(GiftNpc.class, "gift"));
	}

	private WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				takengift(item);
			}
		}
	};

	private void takengift(Item item) {
		boolean loveitem = this.loveitem(item);
		if (loveitem) {
			this.FRIEND += 10;
			item.detach(hero.belongings.backpack);
			hero.spend(1f);
			hero.busy();
			hero.sprite.operate(hero.pos);
			GLog.p(Messages.get(GiftNpc.class, "npc_item", item.name()));
			givereward(this);
		} else {
			GLog.n(Messages.get(GiftNpc.class, "npc_not_item"));
			saydonot(this);

		}
	}

	public void givereward(NPC npc) {

	}

	public void saydonot(NPC npc) {
		friend(this, Messages.get(this, "want"));
	}

}
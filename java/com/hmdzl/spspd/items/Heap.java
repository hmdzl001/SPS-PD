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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mimic;
import com.hmdzl.spspd.actors.mobs.MonsterBox;
import com.hmdzl.spspd.actors.mobs.RedWraith;
import com.hmdzl.spspd.actors.mobs.Spinner;
import com.hmdzl.spspd.actors.mobs.Wraith;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.bombs.Bomb;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.food.meatfood.DarkMeat;
import com.hmdzl.spspd.items.food.meatfood.EarthMeat;
import com.hmdzl.spspd.items.food.meatfood.FireMeat;
import com.hmdzl.spspd.items.food.meatfood.IceMeat;
import com.hmdzl.spspd.items.food.meatfood.LightMeat;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.food.meatfood.ShockMeat;
import com.hmdzl.spspd.items.medicine.Pill;
import com.hmdzl.spspd.items.nornstone.NornStone;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.AresSword;
import com.hmdzl.spspd.items.weapon.melee.relic.CromCruachAxe;
import com.hmdzl.spspd.items.weapon.melee.relic.JupitersWraith;
import com.hmdzl.spspd.items.weapon.melee.relic.LokisFlail;
import com.hmdzl.spspd.items.weapon.melee.relic.NeptunusTrident;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.Rotberry;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static com.hmdzl.spspd.Dungeon.shopOnLevel;

public class Heap implements Bundlable {

	private static final int SEEDS_TO_POTION = 3;

	public enum Type {
		HEAP, FOR_SALE,FOR_LIFE, CHEST, LOCKED_CHEST, CRYSTAL_CHEST, TOMB, SKELETON, REMAINS, MIMIC, E_DUST ,G_MIMIC, M_WEB
	}

	public Type type = Type.HEAP;

	public int pos = 0;

	public ItemSprite sprite;
	public boolean seen = false;

	public LinkedList<Item> items = new LinkedList<Item>();

	public int image() {
		switch (type) {
		case HEAP:
		case FOR_SALE:
			return size() > 0 ? items.peek().image() : 0;
		case FOR_LIFE:
			return size() > 0 ? items.peek().image() : 0;
		case CHEST:
		case MIMIC:
			return ItemSpriteSheet.CHEST;
		case LOCKED_CHEST:
		case G_MIMIC:
			return ItemSpriteSheet.LOCKED_CHEST;
		case CRYSTAL_CHEST:
			return ItemSpriteSheet.CRYSTAL_CHEST;
		case TOMB:
			return ItemSpriteSheet.TOMB;
		case SKELETON:
			return ItemSpriteSheet.BONES;
		case REMAINS:
			return ItemSpriteSheet.REMAINS;
		case E_DUST:
			return ItemSpriteSheet.E_DUST;
		case M_WEB:
			return ItemSpriteSheet.M_WEB;
		default:
			return 0;
		}
	}
	

	public boolean chestCheck() {
		switch (type) {
		case HEAP:
		case FOR_SALE:
		case FOR_LIFE:
		case TOMB:
		case SKELETON:
		case REMAINS:
		case E_DUST:
			case M_WEB:
			case CRYSTAL_CHEST:
			case LOCKED_CHEST:
			case MIMIC:
				case G_MIMIC:
	            return false;

		case CHEST:

		       return true;		
		default:
			return false;
		}
	}


	public ItemSprite.Glowing glowing() {
		return (type == Type.HEAP || type == Type.FOR_SALE || type == Type.FOR_LIFE) && items.size() > 0 ? items
				.peek().glowing() : null;
	}

	public void open(Hero hero) {
		switch (type) {
			case G_MIMIC:
			MonsterBox.spawnAt(pos);
			GLog.n(Messages.get(this,"mimic"));
		    break;		
		case MIMIC:
			if (Mimic.spawnAt(pos, items) != null) {
				GLog.n(Messages.get(this,"mimic"));
				destroy();
			} else {
				type = Type.CHEST;
			}
			break;
		case TOMB:
			  Wraith.spawnAround(hero.pos);
			  break;
		case SKELETON:
		case REMAINS:
			CellEmitter.center(pos).start(Speck.factory(Speck.RATTLE), 0.1f, 3);
			for (Item item : items) {
				
				if (RedWraith.spawnAt(pos) == null) {
						hero.sprite.emitter().burst(ShadowParticle.CURSE, 6);
						hero.damage(hero.HP / 2, this);
					}
					Sample.INSTANCE.play(Assets.SND_CURSED);
					break;
				}
			
			break;
		case M_WEB:
			CellEmitter.center(pos).start(Speck.factory(Speck.COBWEB), 0.1f, 3);
				if (Random.Int(10) == 0 && !shopOnLevel()){
					if (Spinner.spawnAt(pos) == null) {
						Spinner.spawnAround(hero.pos);
					}
				}
            Buff.affect(hero,Roots.class,5f);
			Sample.INSTANCE.play(Assets.SND_SHATTER);
			break;			
		default:
		}

		//if (type != Type.MIMIC && type != Type.MONSTERBOX) {
		if (type != Type.MIMIC) {
			type = Type.HEAP;
			sprite.link();
			sprite.drop();
		}
	}

	public int size() {
		return items.size();
	}

	public Item pickUp() {

		Item item = items.removeFirst();
		if (items.isEmpty()) {
			destroy();
		} else if (sprite != null) {
			sprite.view(image(), glowing());
		}

		return item;
	}

	public Item peek() {
		return items.peek();
	}

	public void drop(Item item) {

		if (item.stackable && type != Type.FOR_SALE && type != Type.FOR_LIFE) {

			for (Item i : items) {
				if (i.isSimilar(item)) {
					i.quantity += item.quantity;
					item = i;
					break;
				}
			}
			items.remove(item);

		}

		if ((item instanceof Dewdrop || item instanceof YellowDewdrop || item instanceof RedDewdrop || item instanceof VioletDewdrop) && type != Type.FOR_SALE && type != Type.FOR_LIFE ) {
			items.add(item);
		} else {
			items.addFirst(item);
		}

		if (sprite != null) {
			sprite.view(image(), glowing());
		}
	}

	//public void spdrop(Item item) {

		//items.add(item);

		//if (sprite != null) {
		//	sprite.view(image(), glowing());
		//}
	//}

	
	
	public void replace(Item a, Item b) {
		int index = items.indexOf(a);
		if (index != -1) {
			items.remove(index);
			items.add(index, b);
		}
	}
	
	public void firehit() {

		if (type == Type.MIMIC) {
			Mimic m = Mimic.spawnAt(pos, items);
			if (m != null) {
				Buff.affect(m, Burning.class).set(3);
				m.sprite.emitter().burst(FlameParticle.FACTORY, 5);
				destroy();
			}
		}
		
		if (type == Type.M_WEB) {
			GameScene.add(Blob.seed(pos, 2, Fire.class));
			type = Type.HEAP;
			sprite.link();
			sprite.drop();
			return;
		}		
				
		if (type != Type.HEAP) {
			return;
		}
		
		boolean burnt = false;

		for (Item item : items.toArray(new Item[0])) {
			if (item instanceof Scroll
			    && !(item instanceof ScrollOfUpgrade || item instanceof ScrollOfMagicalInfusion)) {
				items.remove(item);
				burnt = true;
			} else if (item instanceof Egg) {
				((Egg) item).burns++;
				burnt = true;
			} else if (item instanceof MysteryMeat) {
				replace(item, FireMeat.cook((MysteryMeat) item));
				burnt = true;
			} else if (item instanceof Meat) {
				replace(item, FireMeat.cook((Meat) item));
				burnt = true;
			//} else if (item instanceof Nut) {
				//replace(item, ToastedNut.cook((Nut) item));
				//burnt = true;
			//} else if (item instanceof Bomb) {
				//items.remove(item);
				//((Bomb) item).explode(pos);
				// stop processing the burning, it will be replaced by the
				// explosion.
				//return;
			}
		}

		if (burnt) {
			if (Dungeon.visible[pos]) {
				if (burnt) {
					burnFX(pos);
				} else {
					evaporateFX(pos);
				}
			}
			if (isEmpty()) {
				destroy();
			} else if (sprite != null) {
				sprite.view(image(), glowing());
			}

		}
	}

	// Note: should not be called to initiate an explosion, but rather by an
	// explosion that is happening.
	public void explode() {

		// breaks open most standard containers, mimics die.
		if (type == Type.MIMIC ||  type == Type.CHEST || type == Type.SKELETON) {
			type = Type.HEAP;
			sprite.link();
			sprite.drop();
			return;
		}

		if (type != Type.HEAP) {

			return;

		} else {

			for (Item item : items.toArray(new Item[0])) {

				 if (item instanceof Bomb) {

					 items.remove(item);
					((Bomb) item).explode(pos);

					if (((Bomb) item).explodesDestructively()) {
						//stop processing current explosion, it will be replaced by the new one.
						return;
					}
					// unique and upgraded items can endure the blast
				//} else if (!(item.level > 0 || item.unique))

			} else if (item instanceof Gold)
					items.remove(item);
			}

			if (items.isEmpty())
				destroy();
			}

	}
	
	/*
	public void dewcollect() {

		
			for (Item item : items.toArray(new Item[0])) {

				if (item instanceof Dewdrop ||
					item instanceof YellowDewdrop ||
					item instanceof RedDewdrop) {
					
					item.doPickUp(Dungeon.hero);
				}
		}
	}
	*/
	public int dewdrops(){
		
		if (type != Type.HEAP) {
			return 0;
		}
		
		int drops=0;
		
		for (Item item : items.toArray(new Item[0])) {
			 if (item instanceof Dewdrop) {
				drops++;
			} else if (item instanceof VioletDewdrop) {
				drops++;
			} else if (item instanceof RedDewdrop) {
				drops++;
			} else if (item instanceof YellowDewdrop) {
				drops++;
			} 
		}
		
		return drops;		
	}
	
	
	public void shockhit() {
		boolean shocker = false;
		if (type != Type.HEAP) {
			return;
		}		
		for (Item item : items.toArray(new Item[0])) {
			if (item instanceof MysteryMeat) {
				replace(item, ShockMeat.cook((MysteryMeat) item));
				shocker = true;
			} else if (item instanceof Pill) {
				items.remove(item);
				shocker = true;
			} else if (item instanceof Meat) {
				replace(item, ShockMeat.cook((Meat) item));
				shocker = true;
			} else if (item instanceof Egg) {	
				((Egg) item).lits++;
			}			
		}
		if (shocker) {
			if (isEmpty()) {
				destroy();
			} else if (sprite != null) {
				sprite.view(image(), glowing());
			}
		}
	}
	

	public void darkhit() {
		boolean darker = false;
		if (type != Type.HEAP) {
			return;
		}		
		for (Item item : items.toArray(new Item[0])) {
			if (item instanceof MysteryMeat) {
				replace(item, DarkMeat.cook((MysteryMeat) item));
				darker = true;
			} else if (item instanceof Bomb) {
				items.remove(item);
				darker = true;
			} else if (item instanceof Meat) {
				replace(item, DarkMeat.cook((Meat) item));
				darker = true;
			} else if (item instanceof Egg) {	
				((Egg) item).darks++;
			}			
		}
		if (darker) {
			if (isEmpty()) {
				destroy();
			} else if (sprite != null) {
				sprite.view(image(), glowing());
			}
		}
	}
	
	public void earthhit() {
		boolean earther = false;
		if (type != Type.HEAP) {
			return;
		}		
		for (Item item : items.toArray(new Item[0])) {
			if (item instanceof MysteryMeat) {
				replace(item, EarthMeat.cook((MysteryMeat) item));
				earther = true;
			} else if (item instanceof StoneOre) {
				items.remove(item);
				earther = true;
			} else if (item instanceof Meat) {
				replace(item, EarthMeat.cook((Meat) item));
				earther = true;
			} else if (item instanceof Egg) {	
				((Egg) item).poisons++;
			}			
		}
		if (earther) {
			if (isEmpty()) {
				destroy();
			} else if (sprite != null) {
				sprite.view(image(), glowing());
			}
		}
	}

	public void lighthit() {
		boolean lighter = false;
		if (type != Type.HEAP) {
			return;
		}		
		for (Item item : items.toArray(new Item[0])) {
			if (item instanceof MysteryMeat) {
				replace(item, LightMeat.cook((MysteryMeat) item));
				lighter = true;
			} else if (item instanceof Plant.Seed
			    && !(item instanceof Rotberry.Seed)) {
				items.remove(item);
				lighter = true;
			} else if (item instanceof Meat) {
				replace(item, LightMeat.cook((Meat) item));
				lighter = true;
			} else if (item instanceof Egg) {	
				((Egg) item).lights++;
			}			
		}
		if (lighter) {
			if (isEmpty()) {
				destroy();
			} else if (sprite != null) {
				sprite.view(image(), glowing());
			}
		}

	}	
	
	public void icehit() {
		if (type == Type.MIMIC) {
			Mimic m = Mimic.spawnAt(pos, items);
			if (m != null) {
				Buff.prolong(m, Frost.class,
						Frost.duration(m) * Random.Float(1.0f, 1.5f));
				destroy();
			}
		}
		if (type != Type.HEAP) {
			return;
		}

		boolean frozen = false;
		for (Item item : items.toArray(new Item[0])) {
			if (item instanceof MysteryMeat) {
				replace(item, IceMeat.cook((MysteryMeat) item));
				frozen = true;
			} else if (item instanceof Potion) {
				items.remove(item);
				((Potion) item).shatter(pos);
				frozen = true;
			}else if (item instanceof Egg) {	
				((Egg) item).freezes++;
				frozen = true;
			} else if (item instanceof Bomb) {
				((Bomb) item).fuse = null;
				frozen = true;
			} else if (item instanceof Meat) {
				replace(item, IceMeat.cook((Meat) item));
				frozen = true;
			}
		}
		if (frozen) {
			if (isEmpty()) {
				destroy();
			} else if (sprite != null) {
				sprite.view(image(), glowing());
			}
		}
	}

	public Item transmute() {

		CellEmitter.get(pos).burst(Speck.factory(Speck.BUBBLE), 3);
		Splash.at(pos, 0xFFFFFF, 3);

		float chances[] = new float[items.size()];
		int count = 0;


		return null;
		
	}
	
	public Weapon consecrate() {

		CellEmitter.get(pos).burst(Speck.factory(Speck.FORGE), 3);
		Splash.at(pos, 0xFFFFFF, 3);	
		
		int count=0;
		int type=0;
		
		for (Item item : items) {
			if (item instanceof NornStone) {
				count += item.quantity;
				if(type==0){
					type=((NornStone) item).type;
				} else if (Random.Int(3)<item.quantity){
				 	type=((NornStone) item).type;	
				}
			} else {
				count = 0;
				break;
			}
		}
		
		Weapon weapon;
				
		if (count >= 2) {

			CellEmitter.get(pos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
			
			destroy(); 
			
				switch (type) {
	            case 1:  weapon = new LokisFlail().enchantLoki();
	                     break;
	            case 2:  weapon = new NeptunusTrident(); weapon.enchantNeptune();
                         break;
	            case 3:  weapon = new CromCruachAxe(); weapon.enchantLuck();
                         break;
	            case 4:  weapon = new AresSword(); weapon.enchantAres();
                         break;
	            case 5:  weapon = new JupitersWraith(); weapon.enchantJupiter();
                         break;
	            default: weapon = new AresSword(); weapon.enchantAres();
                         break;
				}
              weapon.identify().uncurse().upgrade(6);
			return weapon;

		} else {
			return null;
		}
	}


	public static void burnFX(int pos) {
		CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);
		Sample.INSTANCE.play(Assets.SND_BURNING);
	}

	public static void evaporateFX(int pos) {
		CellEmitter.get(pos).burst(Speck.factory(Speck.STEAM), 5);
	}

	public boolean isEmpty() {
		return items == null || items.size() == 0;
	}

	public void destroy() {
		Dungeon.depth.heaps.remove(this.pos);
		if (sprite != null) {
			sprite.kill();
		}
		items.clear();
		items = null;
	}

	private static final String POS = "pos";
	private static final String SEEN	= "seen";
	private static final String TYPE = "type";
	private static final String ITEMS = "items";

	@SuppressWarnings("unchecked")
	@Override
	public void restoreFromBundle(Bundle bundle) {
		pos = bundle.getInt(POS);
		seen = bundle.getBoolean( SEEN );
		type = Type.valueOf(bundle.getString(TYPE));
		items = new LinkedList<Item>(
				(Collection<Item>) ((Collection<?>) bundle.getCollection(ITEMS)));
		items.removeAll(Collections.singleton(null));
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(POS, pos);
		bundle.put( SEEN, seen );
		bundle.put(TYPE, type.toString());
		bundle.put(ITEMS, items);
	}

}

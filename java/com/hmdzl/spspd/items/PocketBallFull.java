/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HiddenShadow;
import com.hmdzl.spspd.actors.buffs.WatchOut;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.pets.BlueDragon;
import com.hmdzl.spspd.actors.mobs.pets.BugDragon;
import com.hmdzl.spspd.actors.mobs.pets.Bunny;
import com.hmdzl.spspd.actors.mobs.pets.ButterflyPet;
import com.hmdzl.spspd.actors.mobs.pets.Chocobo;
import com.hmdzl.spspd.actors.mobs.pets.CocoCat;
import com.hmdzl.spspd.actors.mobs.pets.Datura;
import com.hmdzl.spspd.actors.mobs.pets.DogPet;
import com.hmdzl.spspd.actors.mobs.pets.DwarfBoy;
import com.hmdzl.spspd.actors.mobs.pets.Fly;
import com.hmdzl.spspd.actors.mobs.pets.FoxHelper;
import com.hmdzl.spspd.actors.mobs.pets.FrogPet;
import com.hmdzl.spspd.actors.mobs.pets.GentleCrab;
import com.hmdzl.spspd.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.actors.mobs.pets.GreenDragon;
import com.hmdzl.spspd.actors.mobs.pets.Haro;
import com.hmdzl.spspd.actors.mobs.pets.Kodora;
import com.hmdzl.spspd.actors.mobs.pets.LeryFire;
import com.hmdzl.spspd.actors.mobs.pets.LightDragon;
import com.hmdzl.spspd.actors.mobs.pets.LitDemon;
import com.hmdzl.spspd.actors.mobs.pets.Monkey;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.actors.mobs.pets.PigPet;
import com.hmdzl.spspd.actors.mobs.pets.RedDragon;
import com.hmdzl.spspd.actors.mobs.pets.RibbonRat;
import com.hmdzl.spspd.actors.mobs.pets.Scorpion;
import com.hmdzl.spspd.actors.mobs.pets.ShadowDragon;
import com.hmdzl.spspd.actors.mobs.pets.Snake;
import com.hmdzl.spspd.actors.mobs.pets.Spider;
import com.hmdzl.spspd.actors.mobs.pets.StarKid;
import com.hmdzl.spspd.actors.mobs.pets.Stone;
import com.hmdzl.spspd.actors.mobs.pets.Velocirooster;
import com.hmdzl.spspd.actors.mobs.pets.VioletDragon;
import com.hmdzl.spspd.actors.mobs.pets.YearPet;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PocketBallFull extends Item {

    {
        //name = "pocket ball";
        image = ItemSpriteSheet.PALANTIR;

        stackable = false;
    }

    public static final String AC_USE = "USE";

    public int pet_type = 0;
	public int pet_hp = 0;

	
	private static final String PET_TYPE = "pet_type";
    private static final String PET_HP = "pet_hp";

    public PocketBallFull() {
        this( 1, 5 );
    }

    public PocketBallFull(int type,int hp ) {
        super();
        this.pet_type = type;
        this.pet_hp = hp;
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( PET_TYPE,pet_type );
        bundle.put( PET_HP ,pet_hp );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        //super.restoreFromBundle( bundle );
		//pet_type = bundle.getInt(PET_TYPE);
        pet_hp = bundle.getInt(PET_HP);
        try {
            pet_type = bundle.getInt(PET_TYPE);
        }
               catch (Exception ex)
        {
            pet_type = 1;
        }
    }	

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        if ((Dungeon.hero.haspet == false & Dungeon.depth < 26 )|| Dungeon.depth == 50){
        actions.add(AC_USE);}
        return actions;
    }
	
	
    @Override
    public void execute(Hero hero, String action) {

        if (action.equals(AC_USE)) {

            Dungeon.hero.petType = pet_type;
               summonPet(hero);
                hero.spend(1f);
                hero.busy();
                hero.sprite.operate(hero.pos);
                hero.next();
                if (Dungeon.depth!=50) {
                    Dungeon.hero.haspet = true;
                }
                detach(hero.belongings.backpack);

            } else {

                super.execute(hero, action);

            }

    }

    public static void summonPet(Hero hero) {
        PET pet = null;
        switch  (Dungeon.hero.petType) {
            case 1:
                pet = null;
                break;
            case 101:
                pet = new Kodora();
                break;
            case 102:
                pet = new GentleCrab();
                break;
            case 103:
                pet = new RibbonRat();
                break;
            case 104:
                pet = new Snake();
                break;
            case 105:
			   pet = new LitDemon();
                break;
			case 106:
			    pet = new StarKid();
                break;
            case 201:
                pet = new DogPet();
                break;
            case 202:
                pet = new Chocobo();
                break;
            case 203:
                pet = new Fly();
                break;
            case 204:
                pet = new Spider();
                break;
            case 205:
                pet = new Stone();
                break;
		    case 206:
                  pet = new DwarfBoy();
                  break;
            case 301:
                pet = new Datura();
                break;
            case 302:
                pet = new Monkey();
                break;
            case 303:
                pet = new PigPet();
                break;
            case 304:
                pet = new ButterflyPet();
                break;
            case 305:
			     pet = new FoxHelper();
                  break;
			  case 306:
                  pet = new FrogPet();
                  break;
            case 401:
                pet = new Bunny();
                break;
            case 402:
                pet = new CocoCat();
                break;
            case 403:
                pet = new Haro();
                break;
            case 404:
                pet = new Velocirooster();
                break;
            case 405:
                break;
            case 501:
                pet = new BlueDragon();
                break;
            case 502:
                pet = new GreenDragon();
                break;
            case 503:
                pet = new LightDragon();
                break;
            case 504:
                pet = new RedDragon();
                break;
            case 505:
                pet = new ShadowDragon();
                break;
            case 506:
                pet = new VioletDragon();
                break;
            case 507:
                pet = new Scorpion();
                break;
            case 508:
                pet = new LeryFire();
                break;
            case 509:
                pet = new GoldDragon();
                break;
            case 510:
                pet = new BugDragon();
                break;
            case 666:
                pet = new YearPet();
                break;
            default:
                pet = null;
                break;
        }
        ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            int p = hero.pos + PathFinder.NEIGHBOURS8[i];
            if (Actor.findChar(p) == null && (Level.passable[p] || Level.avoid[p])) {
                spawnPoints.add(p);
            }
        }
        if (spawnPoints.size() > 0 && pet != null) {
            pet.pos = Random.element(spawnPoints);GameScene.add(pet);
            pet.updateStats();
            pet.HP = Dungeon.hero.petHP;
            pet.cooldown = Dungeon.hero.petCooldown;
        } else GLog.n(Messages.get(PocketBallFull.class, "no_pet"));
    }

	public static void teleportPet(Hero hero) {
        if (Dungeon.depth == 50) {
            GLog.n(Messages.get(PocketBallFull.class, "no_place"));
            return;
        }
        PET petCheck = checkpet();
        if(petCheck!=null){
            petCheck.destroy();
            petCheck.sprite.killAndErase();
        }
		if(Dungeon.hero.haspet == false) return;
        PET pet = null;
          switch  (Dungeon.hero.petType) {
              case 1:
                  return;
              case 101:
                  pet = new Kodora();
                  break;
              case 102:
                  pet = new GentleCrab();
                  break;
              case 103:
                  pet = new RibbonRat();
                  break;
              case 104:
                  pet = new Snake();
                  break;
              case 105:
			       pet = new LitDemon();
                  break;
	          case 106:
			       pet = new StarKid();
                  break;			  
              case 201:
                  pet = new DogPet();
                  break;
              case 202:
                  pet = new Chocobo();
                  break;
              case 203:
                  pet = new Fly();
                  break;
              case 204:
                  pet = new Spider();
                  break;
              case 205:
                  pet = new Stone();
                  break;
			  case 206:
                  pet = new DwarfBoy();
                  break;
              case 301:
                  pet = new Datura();
                  break;
              case 302:
                  pet = new Monkey();
                  break;
              case 303:
                  pet = new PigPet();
                  break;
              case 304:
                  pet = new ButterflyPet();
                  break;
              case 305:
			     pet = new FoxHelper();
                  break;
			  case 306:
                  pet = new FrogPet();
                  break;
              case 401:
                  pet = new Bunny();
                  break;
              case 402:
                  pet = new CocoCat();
                  break;
              case 403:
                  pet = new Haro();
                  break;
              case 404:
                  pet = new Velocirooster();
                  break;
              case 405:
                  break;
              case 501:
                  pet = new BlueDragon();
                  break;
              case 502:
                  pet = new GreenDragon();
                  break;
              case 503:
                  pet = new LightDragon();
                  break;
              case 504:
                  pet = new RedDragon();
                  break;
              case 505:
                  pet = new ShadowDragon();
                  break;
              case 506:
                  pet = new VioletDragon();
                  break;
              case 507:
                  pet = new Scorpion();
                  break;
              case 508:
                  pet = new LeryFire();
                  break;
              case 509:
                  pet = new GoldDragon();
                  break;
              case 510:
                  pet = new BugDragon();
                  break;
              case 666:
                  pet = new YearPet();
                  break;
              default:
                  pet = null;
                  break;
          }
        ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
        for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
            int p = hero.pos + PathFinder.NEIGHBOURS8[i];
            if (Actor.findChar(p) == null && (Level.passable[p] || Level.avoid[p])) {
                spawnPoints.add(p);
            }
        }
        if (spawnPoints.size() > 0 && pet != null ) {
            pet.pos = Random.element(spawnPoints);
            GameScene.add(pet);
            pet.updateStats();
            pet.HP = Dungeon.hero.petHP;
        } else GLog.n(Messages.get(PocketBallFull.class, "no_pet"));
	}

    public static void removePet(Hero hero) {
        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
            if(mob instanceof PET) {
                mob.destroy();
                mob.sprite.killAndErase();
            }
        }
    }


    public static void target(Hero hero) {
        PET petCheck = checkpet();
        if(petCheck!=null){
            Buff.detach(petCheck,HiddenShadow.class);
            Buff.affect(petCheck, WatchOut.class);
        }
    }

    public static void distarget(Hero hero) {
        PET petCheck = checkpet();
        if(petCheck!=null){
            Buff.detach(petCheck, WatchOut.class);
            Buff.affect(petCheck,HiddenShadow.class,999f);

        }
    }


    private static PET checkpet(){
        for (Mob mob : Dungeon.level.mobs) {
            if(mob instanceof PET) {
                return (PET) mob;
            }
        }
        return null;
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
        return 1000 * quantity;
    }

}

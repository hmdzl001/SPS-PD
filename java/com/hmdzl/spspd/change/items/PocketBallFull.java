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
package com.hmdzl.spspd.change.items;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.pets.BlueDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.BugDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Bunny;
import com.hmdzl.spspd.change.actors.mobs.pets.CocoCat;

import com.hmdzl.spspd.change.actors.mobs.pets.Fly;
import com.hmdzl.spspd.change.actors.mobs.pets.GentleCrab;
import com.hmdzl.spspd.change.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.GreenDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.LeryFire;
import com.hmdzl.spspd.change.actors.mobs.pets.LightDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Monkey;
import com.hmdzl.spspd.change.actors.mobs.pets.PET;
import com.hmdzl.spspd.change.actors.mobs.pets.RedDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.RibbonRat;
import com.hmdzl.spspd.change.actors.mobs.pets.Scorpion;
import com.hmdzl.spspd.change.actors.mobs.pets.ShadowDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Snake;
import com.hmdzl.spspd.change.actors.mobs.pets.Spider;
import com.hmdzl.spspd.change.actors.mobs.pets.Stone;

import com.hmdzl.spspd.change.actors.mobs.pets.Velocirooster;
import com.hmdzl.spspd.change.actors.mobs.pets.VioletDragon;

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class PocketBallFull extends Item {

    {
        //name = "pocket ball";
        image = ItemSpriteSheet.PALANTIR;
		unique = true;
        stackable = false;
    }

    public static final String AC_USE = "USE";

    public int pet_type = 0;
	public int pet_ht = 0;
    public int pet_level = 0;
    public int pet_exp = 0;
	
	private static final String PET_TYPE = "pet_type";
    private static final String PET_HT = "pet_ht";
    private static final String PET_LEVEL = "pet_level";
    private static final String PET_EXP = "pet_exp";

    public PocketBallFull() {
        this( 1, 5, 1, 0 );
    }

    public PocketBallFull(int type,int ht ,int level, int exp) {
        super();
        this.pet_type = type;
        this.pet_ht = ht;
        this.pet_level = level;
        this.pet_exp = exp;
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( PET_TYPE,pet_type );
        bundle.put( PET_HT ,pet_ht );
        bundle.put( PET_LEVEL, pet_level );
        bundle.put( PET_EXP,pet_exp );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        //super.restoreFromBundle( bundle );
		//pet_type = bundle.getInt(PET_TYPE);
        pet_ht = bundle.getInt(PET_HT);
        pet_level = bundle.getInt(PET_LEVEL);
        pet_exp = bundle.getInt(PET_EXP);
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
        if (Dungeon.hero.haspet == false & Dungeon.depth < 26){
        actions.add(AC_USE);}
        return actions;
    }
	
	
    @Override
    public void execute(Hero hero, String action) {

        if (action.equals(AC_USE)) {
            ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
            for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
                int p = hero.pos + PathFinder.NEIGHBOURS8[i];
                if (Actor.findChar(p) == null && (Level.passable[p] || Level.avoid[p])) {
                    spawnPoints.add(p);
                }
            }
            if (spawnPoints.size() > 0) {
                Dungeon.hero.petType = pet_type;
                if (Dungeon.hero.petType==1){
                    Spider pet = new Spider();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==2){
                    CocoCat pet = new CocoCat();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==4){
                    RedDragon pet = new RedDragon();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==3){
                    Velocirooster pet = new Velocirooster();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==5){
                    GreenDragon pet = new GreenDragon();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==6){
                    VioletDragon pet = new VioletDragon();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==7){
                    BlueDragon pet = new BlueDragon();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==8){
                    Scorpion pet = new Scorpion();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==9){
                    Bunny pet = new Bunny();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==10){
                    LightDragon pet = new LightDragon();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==11){
                    BugDragon pet = new BugDragon();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==12){
                    ShadowDragon pet = new ShadowDragon();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==13){
                    CocoCat pet = new CocoCat();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==14){
                    LeryFire pet = new LeryFire();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==15){
                    GoldDragon pet = new GoldDragon();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==16){
                    Snake pet = new Snake();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==17){
                    Fly pet = new Fly();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==18){
                    Stone pet = new Stone();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==19){
                    Monkey pet = new Monkey();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==20){
                    GentleCrab pet = new GentleCrab();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                if (Dungeon.hero.petType==21){
                    RibbonRat pet = new RibbonRat();
                    pet.pos = Random.element(spawnPoints);GameScene.add(pet);
                    pet.HP = pet.HT = pet_ht;
                    pet.level = pet_level;
                    pet.experience = pet_exp;
                }
                //pet.pos = Random.element(spawnPoints);
               // GameScene.add(pet);

                hero.spend(1f);
                hero.busy();
                hero.sprite.operate(hero.pos);
                hero.next();

                Dungeon.hero.haspet=true;
                detach(hero.belongings.backpack);

            } else {

                super.execute(hero, action);

            }
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
        return 1000 * quantity;
    }

}

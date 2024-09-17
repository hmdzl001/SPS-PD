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


import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.eggs.AflyEgg;
import com.hmdzl.spspd.items.eggs.BatPetEgg;
import com.hmdzl.spspd.items.eggs.BlueDragonEgg;
import com.hmdzl.spspd.items.eggs.BlueGirlEgg;
import com.hmdzl.spspd.items.eggs.BugDragonEGG;
import com.hmdzl.spspd.items.eggs.ButterflypetEgg;
import com.hmdzl.spspd.items.eggs.ChocoboEgg;
import com.hmdzl.spspd.items.eggs.CocoCatEgg;
import com.hmdzl.spspd.items.eggs.DaturaEgg;
import com.hmdzl.spspd.items.eggs.DogpetEgg;
import com.hmdzl.spspd.items.eggs.DwarfBoyEgg;
import com.hmdzl.spspd.items.eggs.EasterEgg;
import com.hmdzl.spspd.items.eggs.FlyEgg;
import com.hmdzl.spspd.items.eggs.FoxHelperEgg;
import com.hmdzl.spspd.items.eggs.FrogpetEgg;
import com.hmdzl.spspd.items.eggs.GentleCrabEgg;
import com.hmdzl.spspd.items.eggs.GoldDragonEgg;
import com.hmdzl.spspd.items.eggs.GreenDragonEgg;
import com.hmdzl.spspd.items.eggs.HaroEgg;
import com.hmdzl.spspd.items.eggs.KodoraEgg;
import com.hmdzl.spspd.items.eggs.LeryFireEgg;
import com.hmdzl.spspd.items.eggs.LightDragonEgg;
import com.hmdzl.spspd.items.eggs.LitDemonEgg;
import com.hmdzl.spspd.items.eggs.MonkeyEgg;
import com.hmdzl.spspd.items.eggs.PigpetEgg;
import com.hmdzl.spspd.items.eggs.RedDragonEgg;
import com.hmdzl.spspd.items.eggs.RibbonRatEgg;
import com.hmdzl.spspd.items.eggs.ScorpionEgg;
import com.hmdzl.spspd.items.eggs.ShadowDragonEgg;
import com.hmdzl.spspd.items.eggs.SnakeEgg;
import com.hmdzl.spspd.items.eggs.SpiderpetEgg;
import com.hmdzl.spspd.items.eggs.StarKidEgg;
import com.hmdzl.spspd.items.eggs.StoneEgg;
import com.hmdzl.spspd.items.eggs.VelociroosterEgg;
import com.hmdzl.spspd.items.eggs.VioletDragonEgg;
import com.hmdzl.spspd.items.eggs.YearPetEgg;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;


public class PocketBall extends Item {

	{
		//name = "pocket ball";
		image = ItemSpriteSheet.POCKETBALL_EMPTY;

		stackable = true;
	}

	public PocketBall() {
		this( 1 );
	}

	public PocketBall(int number) {
		super();
		quantity = number;
	}


    @Override
    protected void onThrow( int cell ) {
        if(Actor.findChar(cell) != null && Actor.findChar(cell) instanceof PET) {
            Actor.findChar(cell).sprite.emitter().burst(ShadowParticle.CURSE, 6);
            Sample.INSTANCE.play( Assets.SND_CURSED );
            PET pet = (PET) Actor.findChar(cell);
            int petid = pet.type;
			Item petegg = null;
			switch (petid){
            case 101:
                petegg = new KodoraEgg();
                break;
            case 102:
                petegg = new GentleCrabEgg();
                break;
            case 103:
                petegg = new RibbonRatEgg();
                break;
            case 104:
                petegg = new SnakeEgg();
                break;
            case 105:
			   petegg = new LitDemonEgg();
                break;
			case 106:
			    petegg = new StarKidEgg();
                break;
            case 201:
                petegg = new DogpetEgg();
                break;
            case 202:
                petegg = new ChocoboEgg();
                break;
            case 203:
                petegg = new FlyEgg();
                break;
            case 204:
                petegg = new SpiderpetEgg();
                break;
            case 205:
                petegg = new StoneEgg();
                break;
		    case 206:
                petegg = new DwarfBoyEgg();
                break;
            case 301:
                petegg = new DaturaEgg();
                break;
            case 302:
                petegg = new MonkeyEgg();
                break;
            case 303:
                petegg = new PigpetEgg();
                break;
            case 304:
                petegg = new ButterflypetEgg();
                break;
            case 305:
			     petegg = new FoxHelperEgg();
                  break;
			  case 306:
                  petegg = new FrogpetEgg();
                  break;
            case 401:
                petegg = new EasterEgg();
                break;
            case 402:
                petegg = new CocoCatEgg();
                break;
            case 403:
                petegg = new HaroEgg();
                break;
            case 404:
                petegg = new VelociroosterEgg();
                break;
            case 405:
			   petegg = new AflyEgg();
                break;
            case 501:
                petegg = new BlueDragonEgg();
                break;
            case 502:
                petegg = new GreenDragonEgg();
                break;
            case 503:
                petegg = new LightDragonEgg();
                break;
            case 504:
                petegg = new RedDragonEgg();
                break;
            case 505:
                petegg = new ShadowDragonEgg();
                break;
            case 506:
                petegg = new VioletDragonEgg();
                break;
            case 507:
                petegg = new ScorpionEgg();
                break;
            case 508:
                petegg = new LeryFireEgg();
                break;
            case 509:
                petegg = new GoldDragonEgg();
                break;
            case 510:
                petegg = new BugDragonEGG();
                break;
			case 601:
                petegg = new BlueGirlEgg();
                break;
            case 666:
                petegg = new YearPetEgg();
                break;
            case 807:
                petegg = new BatPetEgg();
                break;
            default:
                petegg = new RandomEgg();
                break;
			}
            Dungeon.depth.drop( petegg, cell ).sprite.drop();
			((PET) Actor.findChar(cell)).sprite.killAndErase();
			Actor.findChar(cell).die(null);
			//Actor.findChar(cell).destroy();
            if (Dungeon.dungeondepth != 50) {
				Dungeon.hero.haspet = false;
				Dungeon.hero.petType = 1;
			}
			GLog.n(Messages.get(this, "get_pet"));
        } else super.onThrow(cell);
    }

	@Override
	public int price() {
		return 100 * quantity;
	}

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
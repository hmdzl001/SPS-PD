package com.hmdzl.spspd.items.weapon.ranges;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class RangeWeapon extends Weapon {

	{
		image2 = ItemSpriteSheet.MIND_ARROW;
        defaultAction = AC_SHOOT;
        usesTargeting = true;
    }


	public static final String AC_SHOOT		= "SHOOT";

	private int tier;

	public RangeWeapon(int tier, float dly) {
		super();

		this.tier = tier;

		STR = typicalSTR();

        DLY = dly;

		MIN = min();
		MAX = max();		
	}
	
	
	private int min() {
		return (int)((tier + 3) * DLY) ;
	}

	private int max() {
		return (int)((tier * tier - tier + 8) * DLY);
	}

	public int typicalSTR() {
		return 8 + tier * 2;
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}

	public Item upgrade(boolean enchant) {
		MIN += 1;
		MAX += 1 + tier/2 ;
		updateQuickslot();
		return super.upgrade(enchant);
	}
	
	@Override
	public int price() {
		int price = 100 ;
		if (enchantment != null) {
			price *= 1.5;
		}
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level > 0) {
				price *= (level + 1);
			} else if (level < 0) {
				price /= (1 - level);
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}	
	

	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		//actions.remove(AC_EQUIP);	
	if (isEquipped(hero)  || Dungeon.hero.subClass == HeroSubClass.AGENT){
		actions.add(AC_SHOOT);
	}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_SHOOT)) {
			if (!isEquipped( hero ) && Dungeon.hero.subClass != HeroSubClass.AGENT)
				GLog.i( Messages.get(ToyGun.class, "need_to_equip") );
			else GameScene.selectCell( shooter );
		}
	}

	@Override
	public int damageRoll(Hero owner) {
	    int damage = Random.Int(MIN, MAX);
		damage = damage/2;
		return Math.round(damage);
	}

	public int damageRoll2(Hero owner) {
		int damage = Random.Int(MIN, MAX);

		if (hero.buff(TargetShoot.class)!= null)
	        damage = (int)(damage*1.5f);
		if (hero.buff(MechArmor.class)!= null)
			damage = (int)(damage*1.5f);
		
		float bonus = 0;
		for (Buff buff : owner.buffs(RingOfSharpshooting.Aim.class)) {
			bonus += Math.min(((RingOfSharpshooting.Aim) buff).level,30);
		}	
		
		if (Random.Int(10) < 3 && bonus > 0) {
			damage = (int)(damage * ( 1.5 + 0.25 * bonus));
			hero.sprite.emitter().burst(Speck.factory(Speck.STAR),8);
		}
		return Math.round(damage);
	}	
	
   @Override
    public void proc(Char attacker, Char defender, int damage) {

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
    }	
	
	@Override
	public String info() {
		String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(RangeWeapon.class, "stats_known", tier, MIN, MAX, STR, DLY);
			if (Dungeon.hero.STR() > typicalSTR()){
				info += " " + Messages.get(MeleeWeapon.class, "excess_str", Dungeon.hero.STR() - typicalSTR());
			}
		} else {
			info += "\n" + Messages.get(RangeWeapon.class, "stats_unknown", tier, min(), max(), typicalSTR());
		}

        //String stats_desc = Messages.get(this, "stats_desc");
       // if (!stats_desc.equals("")) info+= "  " + stats_desc;
        //Messages.get(MeleeWeapon.class, "stats_known", tier, MIN, MAX,STR,ACU,DLY,RCH )

        if (enchantment != null) {
            info += "\n" + Messages.get(MeleeWeapon.class, "enchanted", enchantment.desc());
        }

        if (reinforced) {
            info += "\n" + Messages.get(Item.class, "reinforced");
        }

		if (levelKnown && STR() > Dungeon.hero.STR()) {
			info += "\n" + Messages.get(MeleeWeapon.class, "too_heavy");
		}		

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "cursed");
		}		
		
		return info;
	}

	private int targetPos;
	
	@Override
	public boolean isUpgradable() {
		return true;
	}
	
	public NormalArrow Arrow(){
		return new NormalArrow();
	}
	
	public class NormalArrow extends MissileWeapon {
		
		{
			image = RangeWeapon.this.image2;
			STR = Math.max(typicalSTR(),hero.STR);
			//ACU = 1.3f;
			DLY = RangeWeapon.this.DLY;
			enchantment = RangeWeapon.this.enchantment;
		}

		public int damageRoll(Hero owner) {
			return RangeWeapon.this.damageRoll2(owner);
		}

		@Override
		protected void onThrow( int cell ) {
			Char enemy = Actor.findChar( cell );
			if (enemy == null || enemy == curUser) {
				parent = null;
				Splash.at( cell, 0xCC99FFFF, 1 );
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
				}
			}
		}

		@Override
		public void proc(Char attacker, Char defender, int damage) {

			if (enchantment != null) {
			      enchantment.proc(this, attacker, defender, damage);
		    }
			super.proc(attacker, defender, damage);
		}
		
		int flurryCount = -1;
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			RangeWeapon.this.targetPos = cell;
				super.cast(user, dst);
		}

	}
	
	private CellSelector.Listener shooter = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				Arrow().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(GunWeapon.class, "prompt");
		}
	};
}
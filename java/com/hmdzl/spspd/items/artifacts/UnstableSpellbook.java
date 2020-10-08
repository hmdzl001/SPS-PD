/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Arcane;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.misc.SkillOfAtk;
import com.hmdzl.spspd.items.misc.SkillOfDef;
import com.hmdzl.spspd.items.misc.SkillOfMig;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfIdentify;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.scrolls.ScrollOfRemoveCurse;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by debenhame on 26/11/2014.
 */
public class UnstableSpellbook extends Artifact {

	{
		image = ItemSpriteSheet.ARTIFACT_SPELLBOOK;

		levelCap = 10;

		charge = ((level/2)+2);
		partialCharge = 0;
		chargeCap = ((level/2)+2);

		defaultAction = AC_READ;
	}

	public static final String AC_READ = "READ";
	public static final String AC_ADD = "ADD";
	public static final String AC_SONG = "SONG";
	public static int consumedpts = 0;

	private final ArrayList<Class> scrolls = new ArrayList<>();

	protected WndBag.Mode mode = WndBag.Mode.SCROLL;

	/*public UnstableSpellbook() {
		super();

		Class<?>[] scrollClasses = Generator.Category.SCROLL.classes;
		float[] probs = Generator.Category.SCROLL.probs.clone(); //array of primitives, clone gives deep copy.
		int i = Random.chances(probs);

		while (i != -1){
			scrolls.add(scrollClasses[i]);
			probs[i] = 0;

			i = Random.chances(probs);
		};
	}*/

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (isEquipped( hero ) && charge > 0 && !cursed)
			actions.add(AC_READ);
		if (isEquipped( hero ) && level< levelCap && !cursed)
			actions.add(AC_ADD);
		if (isEquipped(hero) && level > 3 && !cursed)
			actions.add(AC_SONG);			
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {
		if (action.equals( AC_READ )) {

			if (hero.buff( Blindness.class ) != null) GLog.w( Messages.get(this, "blinded") );
			else if (!isEquipped( hero ))             GLog.i( Messages.get(Artifact.class, "need_to_equip") );
			else if (charge == 0)                     GLog.i( Messages.get(this, "no_charge") );
			else if (cursed)                          GLog.i( Messages.get(this, "cursed") );
			else {
				charge--;

				Scroll scroll;
				do {
					scroll = (Scroll) Generator.random(Generator.Category.SCROLL);
				} while (scroll == null ||
						//gotta reduce the rate on these scrolls or that'll be all the item does.
						((scroll instanceof ScrollOfIdentify ||
								scroll instanceof ScrollOfRemoveCurse ||
								scroll instanceof ScrollOfMagicMapping) && Random.Int(2) == 0)
						|| (scroll instanceof ScrollOfTeleportation && Dungeon.bossLevel()));

				scroll.ownedByBook = true;
				curItem = scroll;
				curUser = hero;
				if (Random.Int(20)> level) {
					scroll.doRead();
				} else {
					scroll.empoweredRead();
				}
				updateQuickslot();
			}

		} else if (action.equals( AC_ADD )) {
			GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));
		} else if (action.equals( AC_SONG )) {
			curUser = hero;
			switch (Random.Int(3)){
				case 0:
					Dungeon.hero.hitSkill++;
					Dungeon.hero.evadeSkill++;
					GLog.w(Messages.get(SkillOfAtk.class, "skillup"));
					GLog.w(Messages.get(SkillOfDef.class, "skillup"));
					break;
				case 1:
					Buff.affect(hero, AttackUp.class,level*10f).level(25);
					Buff.affect(hero, DefenceUp.class,level*10f).level(25);
					Buff.affect(hero, Arcane.class,level*10f);
					Buff.affect(hero, TargetShoot.class,level*10f);
					break;
				case 2:
					Dungeon.hero.magicSkill++;
					Buff.affect(hero, Invisibility.class, level*10f );
					Buff.affect(hero, HasteBuff.class, level*3f );
					GLog.w(Messages.get(SkillOfMig.class, "skillup"));
					break;
			}
			level-=4;
			exp-=100;
			curUser.spendAndNext(1f);
			updateQuickslot();
			Sample.INSTANCE.play(Assets.SND_BURNING);
			curUser.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
		} else
			super.execute( hero, action );
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new bookRecharge();
	}

	@Override
	public Item upgrade() {
		chargeCap = (((level+1)/2)+3);
		return super.upgrade();
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if (cursed && isEquipped (Dungeon.hero)){
			desc += "\n\n" + Messages.get(this, "desc_cursed");
		}

		if (level < levelCap) {
				desc += "\n\n" + Messages.get(this, "desc_index",consumedpts);
			}

		return desc;
	}

	private static final String SCROLLS =   "scrolls";
	private static final String PARTIALCHARGE = "partialCharge";
	private static final String CHARGE = "charge";
	private static final String CONSUMED = "consumedpts";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( SCROLLS, scrolls.toArray(new Class[scrolls.size()]) );
		bundle.put(PARTIALCHARGE, partialCharge);
		bundle.put(CHARGE, charge);
		bundle.put(CONSUMED, consumedpts);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		partialCharge = bundle.getInt(PARTIALCHARGE);
		charge = bundle.getInt(CHARGE);
		consumedpts = bundle.getInt(CONSUMED);

	}

	public class bookRecharge extends ArtifactBuff{
		@Override
		public boolean act() {
			if (charge < chargeCap && !cursed) {
				partialCharge += 1 / (150f - (chargeCap - charge)*15f);

				if (partialCharge >= 1) {
					partialCharge --;
					charge ++;

					if (charge == chargeCap){
						partialCharge = 0;
					}
				}
			}

			updateQuickslot();

			spend( TICK );

			return true;
		}
	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Scroll && item.isIdentified()) {
				Hero hero = Dungeon.hero;
				int scrollWorth = item.consumedValue;
				consumedpts += scrollWorth;
			
				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(2f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);

				item.detach(hero.belongings.backpack);
				GLog.h(Messages.get(UnstableSpellbook.class, "exp",consumedpts));
				
				int levelChk = ((level*2)+1)*10;
								
				if (consumedpts > levelChk && level<10) {
					upgrade();
					GLog.p(Messages.get(UnstableSpellbook.class, "infuse_scroll"));
					}
				
			
			} else if (item instanceof Scroll && !item.isIdentified()){
				GLog.w(Messages.get(UnstableSpellbook.class, "unknown_scroll"));
		   } else if (item != null){
			GLog.w(Messages.get(UnstableSpellbook.class, "unable_scroll"));
		}
	 }
	};
 }

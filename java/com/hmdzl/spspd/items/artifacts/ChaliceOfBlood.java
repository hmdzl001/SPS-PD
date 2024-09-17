package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.BloodAngry;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Earthroot;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by debenhame on 27/08/2014.
 */
public class ChaliceOfBlood extends Artifact {

	private static final String TXT_CHALICE = "Chalice of Blood";
	private static final String TXT_YES = "Yes, I know what I'm doing";
	private static final String TXT_NO = "No, I changed my mind";
	private static final String TXT_PRICK = "Each time you use the chalice it will drain more life energy, "
			+ "if you are not careful this draining effect can easily kill you.\n\n"
			+ "Are you sure you want to offer it more life energy?";

	{
		//name = "Chalice of Blood";
		image = ItemSpriteSheet.ARTIFACT_CHALICE1;

		level = 0;
		levelCap = 10;
		
		defaultAction = AC_BLOODANGRY;
	}

	public static final String AC_PRICK = "PRICK";
	public static final String AC_BLOODANGRY = "BLOODANGRY";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && level < levelCap && !cursed)
			actions.add(AC_PRICK);
		if (isEquipped(hero) && level > 3 && !cursed)
		actions.add(AC_BLOODANGRY);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_PRICK)) {

			int damage = 3 * (level * level);

			if (damage > hero.HP * 0.75) {

				GameScene.show(new WndOptions(Messages.get(this, "name"),
							Messages.get(this, "prick_warn"),
							Messages.get(this, "yes"),
							Messages.get(this, "no")) {
					@Override
					protected void onSelect(int index) {
						if (index == 0)
							prick(Dungeon.hero);
					}
                });

			} else {
				prick(hero);
			}
		} else if (action.equals(AC_BLOODANGRY)) {
			if (!isEquipped(hero) || level < 4)
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else {	
                if (level > 3 )level-=3;
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				Buff.affect(hero, BloodAngry.class).set(100);
				hero.spend(1f);
				hero.busy();
				hero.sprite.operate(hero.pos);
				updateQuickslot();	
			}
		}
	}

	private void prick(Hero hero) {
		int damage = 2 * (level * level);

		Earthroot.MagicPlantArmor armor = hero.buff(Earthroot.MagicPlantArmor.class);
		if (armor != null) {
			damage = armor.absorb(damage);
		}

		damage -= Random.IntRange(0, hero.drRoll());

		hero.sprite.operate(hero.pos);
		hero.busy();
		hero.spend(3f);
		GLog.w( Messages.get(this, "onprick") );
		if (damage <= 0){
			damage = 1;
		} else {
			Sample.INSTANCE.play(Assets.SND_CURSED);
			hero.sprite.emitter().burst( ShadowParticle.CURSE, 4+(damage/10) );
		}
		
		hero.damage(damage, this,1);
		Buff.affect(hero,Bleeding.class).set(level * level);

		if (!hero.isAlive()) {
		//	Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
			//GLog.n("The Chalice sucks your life essence dry...");
		} else {
			upgrade();
		}
	}

	@Override
	public Item upgrade() {
		if (level >= 6)
			image = ItemSpriteSheet.ARTIFACT_CHALICE3;
		else if (level >= 2)
			image = ItemSpriteSheet.ARTIFACT_CHALICE2;
		return super.upgrade();
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new chaliceRegen();
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if (isEquipped (Dungeon.hero)){
			desc += "\n\n";
			if (cursed)
				desc += Messages.get(this, "desc_cursed");
			else if (level == 0)
				desc += Messages.get(this, "desc_1");
			else if (level < levelCap)
				desc += Messages.get(this, "desc_2");
			else
				desc += Messages.get(this, "desc_3");
		}

		return desc;
	}

	public class chaliceRegen extends ArtifactBuff {

	}

}

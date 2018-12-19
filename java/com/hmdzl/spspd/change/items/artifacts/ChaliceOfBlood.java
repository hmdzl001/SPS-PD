package com.hmdzl.spspd.change.items.artifacts;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.plants.Earthroot;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.hmdzl.spspd.change.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

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
	}

	public static final String AC_PRICK = "PRICK";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && level < levelCap && !cursed)
			actions.add(AC_PRICK);
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
					};
				});

			} else {
				prick(hero);
			}
		}
	}

	private void prick(Hero hero) {
		int damage = 3 * (level * level);

		Earthroot.Armor armor = hero.buff(Earthroot.Armor.class);
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
		
		hero.damage(damage, this);

		if (!hero.isAlive()) {
			Dungeon.fail(Messages.format(ResultDescriptions.ITEM));
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

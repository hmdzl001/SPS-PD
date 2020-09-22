package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Needling;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by debenhame on 03/09/2014.
 */
public class CapeOfThorns extends Artifact {

	{
		//name = "Cape of Thorns";
		image = ItemSpriteSheet.ARTIFACT_CAPE;

		level = 0;
		levelCap = 10;

		charge = 0;
		chargeCap = 100;
		cooldown = 0;

		defaultAction = AC_NEEDLING;
	}

	public static final String AC_NEEDLING = "NEEDLING";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && level > 1 && !cursed)
		actions.add(AC_NEEDLING);
		return actions;
	}	
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_NEEDLING)) {
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip"));
			else if (cursed)
				GLog.i(Messages.get(Artifact.class, "cursed"));
			else {
				if (level > 1) {level--;
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				Buff.affect(hero, Needling.class, (level + 1) * 10f);
				hero.spend(1f);
				hero.busy();
				hero.sprite.operate(hero.pos);
				updateQuickslot();}
				else GLog.i(Messages.get(Artifact.class, "cursed"));
			}
		}
	}
	@Override
	protected ArtifactBuff passiveBuff() {
		return new Thorns();
	}

	@Override
	public String desc() {
		String desc = Messages.get(this, "desc");
		if (isEquipped( Dungeon.hero )) {
			desc += "\n\n";
			if (cooldown == 0)
				desc += Messages.get(this, "desc_inactive");
			else
				desc += Messages.get(this, "desc_active");
		}

		return desc;
	}

	public class Thorns extends ArtifactBuff {

		@Override
		public boolean act() {
			if (cooldown > 0) {
				cooldown--;
				if (cooldown == 0) {
					BuffIndicator.refreshHero();
					GLog.w(Messages.get(this, "inert") );
				}
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}

		public int proc(int damage, Char attacker) {
			if (cooldown == 0) {
				charge += damage * (0.7 + level * 0.1);
				if (charge >= chargeCap) {
					charge = 0;
					cooldown = 10 + level;
					GLog.p(Messages.get(this, "radiating"));
					BuffIndicator.refreshHero();
				}
			}

			if (cooldown != 0) {
				int deflected = Random.NormalIntRange(0, damage);
				damage -= deflected;

				attacker.damage(deflected, this);

				exp += deflected;

				if (exp >= (level + 1) * 5 && level < levelCap) {
					exp -= (level + 1) * 5;
					upgrade();
					GLog.p(Messages.get(this, "levelup") );
				}

			}
			updateQuickslot();
			return damage;
		}

		@Override
		public String toString() {
				return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc", dispTurns(cooldown));
		}

		@Override
		public int icon() {
			if (cooldown == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.THORNS;
		}

		@Override
		public void detach() {
			cooldown = 0;
			charge = 0;
			super.detach();
		}

	}

}

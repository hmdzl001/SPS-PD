package com.hmdzl.spspd.items.artifacts;

import android.annotation.SuppressLint;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Notice;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

/**
 * Created by debenhame on 08/09/2014.
 */
public class TalismanOfForesight extends Artifact {

	{
		//name = "Talisman of Foresight";
		image = ItemSpriteSheet.ARTIFACT_TALISMAN;

		level = 0;
		exp = 0;
		levelCap = 10;

		charge = 0;
		partialCharge = 0;
		chargeCap = 100;

		defaultAction = AC_SCRY;
	}

	public static final String AC_SCRY = "SCRY";
	public static final String AC_NOTICE = "NOTICE";

	@SuppressLint("SuspiciousIndentation")
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge == 100 && !cursed && !Dungeon.sokobanLevel(Dungeon.dungeondepth))
			actions.add(AC_SCRY);
		if (isEquipped(hero) && level > 2 && !cursed)
		actions.add(AC_NOTICE);		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_SCRY)) {

			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else if (charge != chargeCap)
				GLog.i(Messages.get(this, "no_charge"));
			else {
				hero.sprite.operate(hero.pos);
				hero.busy();
				Sample.INSTANCE.play(Assets.SND_BEACON);
				charge = 0;
				for (int i = 0; i < Floor.getLength(); i++) {

					int terr = Dungeon.depth.map[i];
					if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

						GameScene.updateMap(i);

						if (Dungeon.visible[i]) {
							GameScene.discoverTile(i, terr);
						}
					}
				}

				GLog.p(Messages.get(this, "scry"));

				Buff.affect(hero, Awareness.class, Awareness.DURATION);
				Dungeon.observe();
			}
		} else if (action.equals(AC_NOTICE)) {
			if (!isEquipped(hero))
				GLog.i(Messages.get(Artifact.class, "need_to_equip") );
			else {	
                if (level > 2 )level-=2;
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				Buff.affect(hero, Notice.class,(level+2)*10f);
				hero.spend(1f);
				hero.busy();
				hero.sprite.operate(hero.pos);
				updateQuickslot();	
			}
		}
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new Foresight();
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if ( isEquipped( Dungeon.hero ) ){
			if (!cursed) {
				desc += "\n\n" + Messages.get(this, "desc_worn");

			} else {
				desc += "\n\n" + Messages.get(this, "desc_cursed");
			}
		}

		return desc;
	}

	public class Foresight extends ArtifactBuff {
		private int warn = 0;

		@Override
		public boolean act() {
			spend(TICK);
			if (Dungeon.canseehp == false && !cursed) {
				Dungeon.canseehp = true;
			}
			boolean smthFound = false;

			int distance = 3;

			int cx = target.pos % Floor.getWidth();
			int cy = target.pos / Floor.getWidth();
			int ax = cx - distance;
			if (ax < 0) {
				ax = 0;
			}
			int bx = cx + distance;
			if (bx >= Floor.getWidth()) {
				bx = Floor.getWidth() - 1;
			}
			int ay = cy - distance;
			if (ay < 0) {
				ay = 0;
			}
			int by = cy + distance;
			if (by >= Floor.HEIGHT) {
				by = Floor.HEIGHT - 1;
			}

			for (int y = ay; y <= by; y++) {
				for (int x = ax, p = ax + y * Floor.getWidth(); x <= bx; x++, p++) {

					if (Dungeon.visible[p] && Floor.secret[p]
							&& Dungeon.depth.map[p] != Terrain.SECRET_DOOR)
						smthFound = true;
				}
			}

			if (smthFound == true && !cursed) {
				if (warn == 0) {
					GLog.w(Messages.get(this, "uneasy"));
					if (target instanceof Hero) {
						((Hero) target).interrupt();
					}
				}
				warn = 3;
			} else {
				if (warn > 0) {
					warn--;
				}
			}
			BuffIndicator.refreshHero();

			// fully energy in 2500 turns at lvl=0, scaling to 1000 turns at
			// lvl = 10.
			if (charge < 100 && !cursed) {
				partialCharge += 0.04 + (level * 0.006);

				if (partialCharge > 1 && charge < 100) {
					partialCharge--;
					charge++;
				} else if (charge >= 100) {
					partialCharge = 0;
					GLog.p(Messages.get(this, "full_charge"));
				}
			}

			return true;
		}

		public void charge() {
			charge = Math.min(charge + (2 + (level / 3)), chargeCap);
			exp++;
			if (exp >= 4 && level < levelCap) {
				upgrade();
				GLog.p(Messages.get(this, "levelup") );
				exp -= 4;
			}
		}

		@Override
		public String toString() {
			return  Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc");
		}

		@Override
		public int icon() {
			if (warn == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.FORESIGHT;
		}
	}
}

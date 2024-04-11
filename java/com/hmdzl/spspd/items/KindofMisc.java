package com.hmdzl.spspd.items;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndOptions;

/**
 * Created by Evan on 24/08/2014.
 */
public abstract class KindofMisc extends EquipableItem {

	public abstract void activate(Char ch);
	private static final float TIME_TO_EQUIP = 1f;
	@Override
	public boolean doEquip(final Hero hero) {

		if (hero.belongings.misc1 != null && hero.belongings.misc2 != null && hero.belongings.misc3 != null) {

			final KindofMisc m1 = hero.belongings.misc1;
			final KindofMisc m2 = hero.belongings.misc2;
			final KindofMisc m3 = hero.belongings.misc3;

			GameScene.show(
					new WndOptions(Messages.get(KindofMisc.class, "unequip_title"),
							Messages.get(KindofMisc.class, "unequip_message"),
							Messages.titleCase(m1.toString()),
							Messages.titleCase(m2.toString()),
							Messages.titleCase(m3.toString())) {

						@Override
						protected void onSelect(int index) {

							KindofMisc equipped;
							//temporarily give 1 extra backpack spot to support swapping with a full inventory
							hero.belongings.backpack.size++;
							if (index == 0) {
								equipped = m1;
							} else if (index == 1) {
								equipped = m2;
							} else {
								equipped = m3;
							}
							if (equipped.doUnequip(hero, true, false)) {
								execute(hero, AC_EQUIP);
							}
							hero.belongings.backpack.size--;
						}
					});

			return false;

		} else {

			if (hero.belongings.misc1 == null) {
				hero.belongings.misc1 = this;
			} else if (hero.belongings.misc2 == null) {
				hero.belongings.misc2 = this;
			} else if (hero.belongings.misc3 == null){
				hero.belongings.misc3 = this;
			}

			detach( hero.belongings.backpack );

			activate( hero );

			cursedKnown = true;
			if (cursed) {
				equipCursed( hero );
				GLog.n( Messages.get(this, "equip_cursed", this) );
			}

			hero.spendAndNext( TIME_TO_EQUIP );
			return true;

		}

	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)){

			if (hero.belongings.misc1 == this) {
				hero.belongings.misc1 = null;
			} else if (hero.belongings.misc2 == this) {
				hero.belongings.misc2 = null;
			} else if (hero.belongings.misc3 == this){
				hero.belongings.misc3 = null;
			}

			return true;

		} else {

			return false;

		}
	}

	@Override
	public boolean isEquipped( Hero hero ) {
		return hero.belongings.misc1 == this || hero.belongings.misc2 == this || hero.belongings.misc3 == this;
	}

}

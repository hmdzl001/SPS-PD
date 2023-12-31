/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
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
package com.hmdzl.spspd.windows;

import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Group;

public class WndClass extends WndTabbed {

	private static final int WIDTH	= 110;

	private static final int TAB_WIDTH	= 50;

	private HeroClass cl;

	private PerksTab tabPerks;
	private MasteryTab tabMastery;

	private SkinTab tabSkin;

	public WndClass( HeroClass cl ) {

		super();

		this.cl = cl;

		tabPerks = new PerksTab();
		add( tabPerks );

		Tab tab = new RankingTab( cl.title().toUpperCase(), tabPerks );
		tab.setSize( TAB_WIDTH, tabHeight() );
		add( tab );

		tabMastery = new MasteryTab();
		add( tabMastery );

		tab = new RankingTab( Messages.get(this, "mastery"), tabMastery );
		add( tab );

		tabSkin = new SkinTab();
		add( tabSkin );

		tab = new RankingTab( Messages.get(this, "skin"), tabSkin );
		add( tab );

		resize(
					(int)Math.max( tabPerks.width, Math.max(tabMastery.width, tabSkin.width)  ),
					(int)Math.max( tabPerks.height, Math.max(tabMastery.height,tabSkin.height)) );


		layoutTabs();

		select( 0 );
	}

	private class RankingTab extends LabeledTab {

		private Group page;

		public RankingTab( String label, Group page ) {
			super( label );
			this.page = page;
		}

		@Override
		protected void select( boolean value ) {
			super.select( value );
			if (page != null) {
				page.visible = page.active = selected;
			}
		}
	}

	private class PerksTab extends Group {

		private static final int MARGIN	= 4;
		private static final int GAP	= 4;

		public float height;
		public float width;

		public PerksTab() {
			super();

			float dotWidth = 0;

			String[] items = cl.perks();
			float pos = MARGIN;

			for (int i=0; i < items.length; i++) {

				if (i > 0) {
					pos += GAP;
				}

				BitmapText dot = PixelScene.createText( "-", 6 );
				dot.x = MARGIN;
				dot.y = pos;
				if (dotWidth == 0) {
					dot.measure();
					dotWidth = dot.width();
				}
				add( dot );

				RenderedTextMultiline item = PixelScene.renderMultiline( items[i], 6 );
				item.maxWidth((int)(WIDTH - MARGIN * 2 - dotWidth));
				item.setPos(dot.x + dotWidth, pos);
				add( item );

				pos += item.height();
				float w = item.width();
				if (w > width) {
					width = w;
				}
			}

			width += MARGIN + dotWidth;
			height = pos + MARGIN;
		}
	}

	private class MasteryTab extends Group {

		private static final int MARGIN	= 4;

		public float height;
		public float width;

		public MasteryTab() {
			super();

			String message = null;
			switch (cl) {
				case WARRIOR:
					message = HeroSubClass.GLADIATOR.desc() + "\n\n" + HeroSubClass.BERSERKER.desc();
					break;
				case MAGE:
					message = HeroSubClass.BATTLEMAGE.desc() + "\n\n" + HeroSubClass.WARLOCK.desc();
					break;
				case ROGUE:
					message = HeroSubClass.FREERUNNER.desc() + "\n\n" + HeroSubClass.ASSASSIN.desc();
					break;
				case HUNTRESS:
					message = HeroSubClass.SNIPER.desc() + "\n\n" + HeroSubClass.WARDEN.desc();
					break;
				case PERFORMER:
					message = HeroSubClass.SUPERSTAR.desc() + "\n\n" + HeroSubClass.JOKER.desc();
					break;
				case SOLDIER:
					message = HeroSubClass.AGENT.desc() + "\n\n" + HeroSubClass.LEADER.desc();
					break;
				case FOLLOWER:
					message = HeroSubClass.PASTOR.desc() + "\n\n" + HeroSubClass.ARTISAN.desc();
					break;
				case ASCETIC:
					message = HeroSubClass.MONK.desc() + "\n\n" + HeroSubClass.HACKER.desc();
					break;

			}

			RenderedTextMultiline text = PixelScene.renderMultiline( 6 );
			text.text( message, WIDTH - MARGIN * 2 );
			text.setPos( MARGIN, MARGIN );
			add( text );

			height = text.bottom() + MARGIN;
			width = text.right() + MARGIN;
		}
	}

	private class SkinTab extends Group {

		private static final int MARGIN	= 4;

		public float height;
		public float width;

		public SkinTab() {
			super();
			String message = null;
			switch (cl) {
				case WARRIOR:
					message = Messages.get(WndClass.class, "warrior_skin1") + "\n\n" +
							Messages.get(WndClass.class, "warrior_skin2") + "\n\n" +
							Messages.get(WndClass.class, "warrior_skin3") + "\n\n" +
							Messages.get(WndClass.class, "warrior_skin4") + "\n\n" +

							Messages.get(WndClass.class, "warrior_skin6") + "\n\n" +
							Messages.get(WndClass.class, "warrior_skin7");
					break;
				case MAGE:
					message = Messages.get(WndClass.class, "mage_skin1") + "\n\n" +
							Messages.get(WndClass.class, "mage_skin2") + "\n\n" +
							Messages.get(WndClass.class, "mage_skin3") + "\n\n" +
							Messages.get(WndClass.class, "mage_skin4") + "\n\n" +

							Messages.get(WndClass.class, "mage_skin6") + "\n\n" +
							Messages.get(WndClass.class, "mage_skin7");
					break;
				case ROGUE:
					message = Messages.get(WndClass.class, "rogue_skin1") + "\n\n" +
							Messages.get(WndClass.class, "rogue_skin2") + "\n\n" +
							Messages.get(WndClass.class, "rogue_skin3") + "\n\n" +
							Messages.get(WndClass.class, "rogue_skin4") + "\n\n" +

							Messages.get(WndClass.class, "rogue_skin6") + "\n\n" +
							Messages.get(WndClass.class, "rogue_skin7");
					break;
				case HUNTRESS:
					message = Messages.get(WndClass.class, "huntress_skin1") + "\n\n" +
							Messages.get(WndClass.class, "huntress_skin2") + "\n\n" +
							Messages.get(WndClass.class, "huntress_skin3") + "\n\n" +
							Messages.get(WndClass.class, "huntress_skin4") + "\n\n" +

							Messages.get(WndClass.class, "huntress_skin6") + "\n\n" +
							Messages.get(WndClass.class, "huntress_skin7");
					break;
				case PERFORMER:
					message = Messages.get(WndClass.class, "performer_skin1") + "\n\n" +
							Messages.get(WndClass.class, "performer_skin2") + "\n\n" +
							Messages.get(WndClass.class, "performer_skin3") + "\n\n" +
							Messages.get(WndClass.class, "performer_skin4") + "\n\n" +

							Messages.get(WndClass.class, "performer_skin6") + "\n\n" +
							Messages.get(WndClass.class, "performer_skin7");
					break;
				case SOLDIER:
					message = Messages.get(WndClass.class, "soldier_skin1") + "\n\n" +
							Messages.get(WndClass.class, "soldier_skin2") + "\n\n" +
							Messages.get(WndClass.class, "soldier_skin3") + "\n\n" +
							Messages.get(WndClass.class, "soldier_skin4") + "\n\n" +

							Messages.get(WndClass.class, "soldier_skin6") + "\n\n" +
							Messages.get(WndClass.class, "soldier_skin7");
					break;
				case FOLLOWER:
					message = Messages.get(WndClass.class, "follower_skin1") + "\n\n" +
							Messages.get(WndClass.class, "follower_skin2") + "\n\n" +
							Messages.get(WndClass.class, "follower_skin3") + "\n\n" +
							Messages.get(WndClass.class, "follower_skin4") + "\n\n" +

							Messages.get(WndClass.class, "follower_skin6") + "\n\n" +
							Messages.get(WndClass.class, "follower_skin7");
					break;
				case ASCETIC:
					message = Messages.get(WndClass.class, "ascetic_skin1") + "\n\n" +
							Messages.get(WndClass.class, "ascetic_skin2") + "\n\n" +
							Messages.get(WndClass.class, "ascetic_skin3") + "\n\n" +
							Messages.get(WndClass.class, "ascetic_skin4") + "\n\n" +

							Messages.get(WndClass.class, "ascetic_skin6") + "\n\n" +
							Messages.get(WndClass.class, "ascetic_skin7");
					break;
			}

			RenderedTextMultiline text = PixelScene.renderMultiline( 6 );
			text.text( message, WIDTH - MARGIN * 2 );
			text.setPos( MARGIN, MARGIN );
			add( text );

			height = text.bottom() + MARGIN;
			width = text.right() + MARGIN;
		}
	}
}
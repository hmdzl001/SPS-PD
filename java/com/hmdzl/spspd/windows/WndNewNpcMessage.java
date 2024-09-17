/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
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

import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.HealthBar;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;

public class WndNewNpcMessage extends WndUnderTitledMessage {

	protected static final int WIDTH_P    = 120;
	protected static final int WIDTH_L    = 144;
	protected static final int GAP	= 2;

	public WndNewNpcMessage( NPC npc , String desc ) {
		super( new NPCTitle(npc), desc(desc));
	}

	private static String desc(String desc) {

		StringBuilder builder = new StringBuilder(desc);

		return builder.toString();
	}


	private static class NPCTitle extends Component {

		private static final int GAP = 2;

		private CharSprite image;
		private RenderedText name;
		private HealthBar friend;


		public NPCTitle(NPC npc) {

			name = PixelScene.renderText(Messages.titleCase(npc.name), 9);
			name.hardlight(TITLE_COLOR);
			//name.measure();
			add(name);

			image = npc.sprite();
			add(image);

			friend = new HealthBar();
			friend.level((float) npc.FRIEND / 100);
			add(friend);

		}

		@Override
		protected void layout() {

			image.x = 0;
			image.y = Math.max(0, name.height() + GAP + friend.height() - image.height);

			name.x = image.width + GAP;
			name.y = image.height - friend.height() - GAP - name.baseLine();

			float w = width - image.width - GAP;

			friend.setRect(image.width + GAP, image.height - friend.height(),
					w, friend.height());

			height = friend.bottom();

		}
	}
}

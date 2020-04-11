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
package com.hmdzl.spspd.actors.blobs;

import com.hmdzl.spspd.effects.particles.MemoryParticle;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.LoadSaveScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Journal;
import com.hmdzl.spspd.Journal.Feature;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.watabou.utils.Bundle;

import java.io.IOException;

public class MemoryFire extends Blob {
	
	protected int pos;
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
		for (int i=0; i < LENGTH; i++) {
			if (cur[i] > 0) {
				pos = i;
				break;
			}
		}
	}
	
	@Override
	protected void evolve() {
		volume = off[pos] = cur[pos];
		Char ch = Actor.findChar( pos );
		MemoryFire fire = (MemoryFire)Dungeon.level.blobs.get( MemoryFire.class );
		if (ch != null && ch == Dungeon.hero) {
			if (Dungeon.visible[pos]) {
				Sample.INSTANCE.play( Assets.SND_BURNING );
				fire.seed( fire.pos, 0 );
				Journal.remove( Feature.MEMORY_FIRE );
				try {
					Dungeon.saveAll();
				} catch (IOException e) {
					//
				}
				Dungeon.canSave=true;
				Game.switchScene(LoadSaveScene.class);
				//GameScene.show(new WndMemory());
			}			
		}
		if (Dungeon.visible[pos]) {
			Journal.add( Feature.MEMORY_FIRE );
		}
	}

	@Override
	public void seed( int cell, int amount ) {
		cur[pos] = 0;
		pos = cell;
		volume = cur[pos] = amount;
	}

	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		
		emitter.pour( MemoryParticle.FACTORY, 0.04f );
	}	
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}


	/*public class WndMemory extends Window {
		
		private static final int WIDTH = 120;
		private static final int MARGIN = 2;
		private static final int BUTTON_WIDTH = WIDTH - MARGIN * 2;
		private static final int BUTTON_HEIGHT = 20;
	
	    public WndMemory() {
			super();
			
			IconTitle titlebar = new IconTitle();
			titlebar.setRect(0, 0, WIDTH, 0);
			add(titlebar);
			
			RenderedTextMultiline tfMesage = PixelScene.renderMultiline( Messages.get(this, "SorN"), 8 );
			tfMesage.maxWidth(WIDTH - MARGIN * 2);
			tfMesage.setPos(MARGIN, titlebar.bottom() + MARGIN);
			add( tfMesage );
			
		RedButton btnSave = new RedButton(Messages.get(MemoryFire.class,"save")) {
			@Override
			protected void onClick() {
				Game.switchScene(LoadSaveScene.class);
			}
		};
		btnSave.setRect(MARGIN, pos + MARGIN, BUTTON_WIDTH,
						BUTTON_HEIGHT);
		add( btnSave );

		RedButton btnNosave = new RedButton(Messages.get(MemoryFire.class,"no_save")) {
			@Override
			protected void onClick() {
				hide();
			}
		};
		btnNosave.setRect(MARGIN, pos + MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT);
		add(btnNosave);		

        resize(WIDTH, (int) btnNosave.bottom());	
		}
	}*/
	
}	
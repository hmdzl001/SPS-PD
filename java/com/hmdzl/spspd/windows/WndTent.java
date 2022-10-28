/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.TentSleep;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.Icons;
import com.hmdzl.spspd.ui.ItemSlot;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class WndTent extends Window {

	private WndBlacksmith.ItemButton[] inputs = new WndBlacksmith.ItemButton[1];
	private ItemSlot output;

	private Emitter smokeEmitter;
	private Emitter bubbleEmitter;

	private RedButton btnCombine;

	private static final int WIDTH_P = 116;
	private static final int WIDTH_L = 160;

	private static final int BTN_SIZE	= 28;

	public WndTent(){

		int w = WIDTH_P;

		int h = 0;

		IconTitle titlebar = new IconTitle();
		titlebar.icon(HeroSprite.avatar(hero.heroClass, hero.useskin()));
		titlebar.label( Messages.get(this, "title") );
		titlebar.setRect( 0, 0, w, 0 );
		add( titlebar );

		h += titlebar.height() + 2;

		RenderedTextMultiline desc = PixelScene.renderMultiline(6);
		desc.text( Messages.get(this, "text") );
		desc.setPos(0, h);
		desc.maxWidth(w);
		add(desc);

		h += desc.height() + 6;

		for (int i = 0; i < inputs.length  ; i++) {
			inputs[i] = new WndBlacksmith.ItemButton(){
				@Override
				protected void onClick() {
					super.onClick();
					if (item != null){
						if (!item.collect()){
							Dungeon.level.drop(item, hero.pos);
						}
						item = null;
						slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
					}
					GameScene.selectItem( itemSelector, WndBag.Mode.FOOD, Messages.get(WndAlchemy.class, "select") );
				}
			};
			inputs[i].setRect(15, h, BTN_SIZE, BTN_SIZE);
			add(inputs[i]);
			h += BTN_SIZE + 2;
		}

		Image arrow = Icons.get(Icons.RESUME);
		arrow.hardlight(0, 0, 0);
		arrow.x = (w - arrow.width)/2f;
		arrow.y = inputs[0].top() + (inputs[0].height() - arrow.height)/2f;
		PixelScene.align(arrow);
		add(arrow);

		output = new ItemSlot(){
			@Override
			protected void onClick() {
				super.onClick();
				/*if (visible){
					GameScene.show(new WndInfoItem);
				}*/
			}
		};
		output.setRect(w - BTN_SIZE - 15, inputs[0].top(), BTN_SIZE, BTN_SIZE);

		ColorBlock outputBG = new ColorBlock(output.width(), output.height(), 0x9991938C);
		outputBG.x = output.left();
		outputBG.y = output.top();
		add(outputBG);

		add(output);
		output.visible = false;

		bubbleEmitter = new Emitter();
		smokeEmitter = new Emitter();
		bubbleEmitter.pos(outputBG.x + (BTN_SIZE-16)/2f, outputBG.y + (BTN_SIZE-16)/2f, 16, 16);
		smokeEmitter.pos(bubbleEmitter.x, bubbleEmitter.y, bubbleEmitter.width, bubbleEmitter.height);
		bubbleEmitter.autoKill = false;
		smokeEmitter.autoKill = false;
		add(bubbleEmitter);
		add(smokeEmitter);

		h += 4;

		float btnWidth = (w-14)/2f;

		btnCombine = new RedButton(Messages.get(this, "combine")){
			@Override
			protected void onClick() {
				super.onClick();
				combine();
			}
		};
		btnCombine.setRect(5, h, btnWidth, 18);
		PixelScene.align(btnCombine);
		btnCombine.enable(false);
		add(btnCombine);

		RedButton btnCancel = new RedButton(Messages.get(this, "cancel")){
			@Override
			protected void onClick() {
				super.onClick();
				onBackPressed();
			}
		};
		btnCancel.setRect(w - 5 - btnWidth, h, btnWidth, 18);
		PixelScene.align(btnCancel);
		add(btnCancel);

		h += btnCancel.height();

		resize(w, h);
	}

	protected WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				for (int i = 0; i < inputs.length ; i++) {
					if (inputs[i].item == null){
						inputs[i].item(item.detach(hero.belongings.backpack));
						break;
					}
				}
			}
			updateState();
		}
	};

	private<T extends Item> ArrayList<T> filterInput(Class<? extends T> itemClass){
		ArrayList<T> filtered = new ArrayList<>();
		for (int i = 0; i < (inputs.length ); i++){
			Item item = inputs[i].item;
			if (item != null && itemClass.isInstance(item)){
				filtered.add((T)item);
			}
		}
		return filtered;
	}

	private void updateState(){
		if(filterInput(Item.class).size() > 0){
			output.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
			output.visible = true;
			btnCombine.enable(true);
		} else {
			btnCombine.enable(false);
			output.visible = false;
		}
	}

	private void combine(){
		Item item = inputs[0].item;
		float time = ((Food)item).energy;

		Dungeon.hero.buff(Hunger.class).satisfy(time);
		Buff.affect(hero,TentSleep.class,time);
		Dungeon.hero.spendAndNext(time);

        bubbleEmitter.start(Speck.factory( Speck.BUBBLE ), 0.2f, 10 );
        smokeEmitter.burst(Speck.factory( Speck.WOOL ), 10 );
        Sample.INSTANCE.play( Assets.SND_PUFF );

        for (int i = 0; i < (inputs.length ); i++){
            inputs[i].slot.item(new WndBag.Placeholder(ItemSpriteSheet.SOMETHING));
            inputs[i].item = null;
        }

        btnCombine.enable(false);


	}

	@Override
	public void onBackPressed() {
		for (int i = 0; i < inputs.length ; i++) {
			if (inputs[i].item != null){
				if (!inputs[i].item.collect()){
					Dungeon.level.drop(inputs[i].item, hero.pos);
				}
			}
		}
		super.onBackPressed();
	}
}
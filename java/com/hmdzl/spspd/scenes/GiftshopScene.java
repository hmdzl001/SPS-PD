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
package com.hmdzl.spspd.scenes;

import com.hmdzl.spspd.Chrome;
import com.hmdzl.spspd.ShatteredPixelDungeon;

import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.ui.*;

import com.hmdzl.spspd.windows.WndOptions;

import com.hmdzl.spspd.windows.WndTitledMessage;

import com.hmdzl.spspd.GiftUnlocks;
import com.watabou.noosa.*;
import com.watabou.noosa.ui.Component;

//TODO: update this class with relevant info as new versions come out.
public class GiftshopScene extends PixelScene {

	public static Class<? extends PixelScene> lastScene;
	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedText title = renderText( Messages.get(this, "title"), 12 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2 ;
		title.y = 4;
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = 120 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 16;

		panel.size( pw, ph );
		panel.x = (w - pw) / 2f;

		panel.y = title.y + title.height();
		align( panel );
		add( panel );

		//System.out.println(stones.width()+"|"+stones.height());

		ScrollPane list = new ScrollPane( new Component() );
		add( list );

		Component content = list.content();
		content.clear();

		int pos = 0;
		for(int i = 0; i<GiftUnlocks.unlockables().length;i++){
			ShopButton sb = new ShopButton(GiftUnlocks.unlockables()[i]);
			content.add(sb);

			sb.setRect(0,pos,panel.innerWidth()-10,15);
			pos=(int)sb.bottom()+2;
		}

		content.setSize( panel.innerWidth(), (int)Math.ceil(pos) );

		if(ShatteredPixelDungeon.landscape()) {
			list.setRect(
					panel.x + panel.marginLeft(),
					25, w, h-20 );
		} else {
			list.setRect(
					panel.x + panel.marginLeft(),
					25, w, h-20);
		}
		list.scrollTo(0, 0);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade( TitleScene.class );
	}

	public class ShopButton extends RedButton{
		public final int price;
		private boolean sold;
		private Image moon;
		private NinePatch whiteBG;
		private RenderedTextMultiline priceTXT;
		private GiftUnlocks id;

		public ShopButton(GiftUnlocks id){
			super(id.dispName(),id.textSize());
			price=GiftUnlocks.price(id);
			sold=id.isUnlocked();
			this.id=id;
			priceTXT = new RenderedTextMultiline("price",7);
			add(priceTXT);
			if (sold){
				erase(moon);
				erase(whiteBG);
			}
		}

		@Override
		protected void createChildren() {
			super.createChildren();
			whiteBG = Chrome.get(Chrome.Type.TOAST);
			moon = Icons.get(Icons.INFO);

			if(!sold) {
				add(whiteBG);
				add(moon);
			}
		}

		@Override
		protected void layout() {
			super.layout();
			text.x=x+2;
			if(!sold) {
				priceTXT.text(price + "");

				whiteBG.size(priceTXT.width() + 2, priceTXT.height() + 2);
				whiteBG.x = x + width - whiteBG.width - 3;
				whiteBG.y = centerY() - whiteBG.height / 2;

				priceTXT.setPos(whiteBG.x + 1,centerY() - priceTXT.height() / 2);

				moon.x = whiteBG.x - moon.width - 2;
				moon.y = centerY() - moon.height / 2;
			} else {
				priceTXT.text(Messages.get(this, "sold"));
//                priceTXT.measure();

				priceTXT.setPos(x+width -priceTXT.width()-2,centerY() - priceTXT.height() / 2);
			}
		}

		public void sold(){
			sold=true;
			erase(whiteBG);
			erase(moon);
			layout();
			//ShatteredPixelDungeon.specialcoin(ShatteredPixelDungeon.specialcoin()-price);
			ShatteredPixelDungeon.switchNoFade(GiftshopScene.class);
		}

		@Override
		protected void onClick() {
			if (!sold) {
				boolean canBuy = 10 >= price;
				WndOptions wo = new WndOptions(
						id.dispName(),
						Messages.get(this, "buy",id.dispName(),price),
						canBuy?Messages.get(this, "yes"):Messages.get(this, "yes_1",price),
						Messages.get(this, "no")) {
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							sold();
						}
					}
				};
				GiftshopScene.this.add(wo);
			}
		}
	}
}



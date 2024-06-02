package com.hmdzl.spspd.windows;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.ui.HealthBar;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
import com.hmdzl.spspd.utils.GLog;

import java.util.Locale;


public class WndPetInfo extends Window {

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	   private CharSprite image;

		private HealthBar health;
		private BuffIndicator buffs;
		private PET checkpet;


	public WndPetInfo(final PET heropet) {
	
		super();
	
			image = heropet.sprite();
			add(image);

			health = new HealthBar();
			health.level((float) heropet.HP / heropet.HT);
			add(health);

			buffs = new BuffIndicator(heropet);
			add(buffs);
		
			checkpet = heropet;
			
			IconTitle title = new IconTitle();
			title.icon(image);
			title.label( Messages.get(this, "title",heropet.name).toUpperCase(Locale.ENGLISH), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

		RenderedTextMultiline message = PixelScene.renderMultiline(Messages.get(this, "info1",heropet.HP,heropet.HT), 6);
		message.maxWidth(WIDTH);
		message.setPos(0,title.bottom() + GAP);
		add(message);

		RenderedTextMultiline message2 = PixelScene.renderMultiline(Messages.get(this, "info2",heropet.cooldown), 6);
		message2.maxWidth(WIDTH);
		message2.setPos(0,message.top() + message.height() + GAP);
		add(message2);


		RedButton btnInfo = new RedButton(Messages.get(this, "moreinfo")) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show(new WndInfoMob(heropet));
			}
		};
		btnInfo.setRect(0, message2.top() + message2.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnInfo);

		RedButton btnStay = new RedButton(Messages.get(this, "stay")) {
			@Override
			protected void onClick() {
				hide();
					if (heropet.stay){
					   heropet.stay = false;
					} else {
						heropet.stay = true;
					}
			}
		};
		btnStay.setRect(0, btnInfo.top() + btnInfo.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnStay);	
		
		RedButton btnFollow = new RedButton(Messages.get(this, "follow")) {
			@Override
			protected void onClick() {
				hide();
				heropet.callback = true;
			    heropet.stay = false;
			}
		};
		btnFollow.setRect(0, btnStay.top() + btnStay.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnFollow);	
		
	    RedButton btnFeed = new RedButton(Messages.get(this, "feed")) {
			@Override
			protected void onClick() {
				hide();
				GameScene.selectItem(itemSelector, WndBag.Mode.ALL, Messages.get(WndHero.class, "choose_food"));
			}
		};
		btnFeed.setRect(0, btnFollow.top() + btnFollow.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnFeed);

		RedButton btnRecover = new RedButton(Messages.get(this, "recover")) {
			@Override
			protected void onClick() {
				hide();
				heropet.dropreward();
			}
		};
		btnRecover.setRect(0, btnFeed.top() + btnFeed.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnRecover);

		resize(WIDTH, (int) btnRecover.bottom());
	}
	
	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				feed(item);
			}
		}
	};
	

	
		private void feed(Item item) {
		PET heropet = checkpet;

		boolean lovefood = checkpet.lovefood(item) ;

          if (lovefood) {
			  int effect = heropet.HT - heropet.HP;
			  if (effect > 0) {
				  heropet.HP += (int) (effect * 0.8);
				  heropet.sprite.emitter().burst(Speck.factory(Speck.HEALING), 2);
				  heropet.sprite.showStatus(CharSprite.POSITIVE, Messages.get(WndHero.class, "heals", effect));
			  }
			  heropet.cooldown = (int)(heropet.cooldown/2);
			  item.detach(Dungeon.hero.belongings.backpack);
			  Buff.affect(heropet, HasteBuff.class, 10f);
			  Dungeon.hero.spend(1f);
			  Dungeon.hero.busy();
			  Dungeon.hero.sprite.operate(Dungeon.hero.pos);
			  GLog.n(Messages.get(WndHero.class, "pet_eat", item.name()));
		  }  else {
				GLog.n(Messages.get(WndHero.class, "pet_not_eat"));
          }

		}
	
}

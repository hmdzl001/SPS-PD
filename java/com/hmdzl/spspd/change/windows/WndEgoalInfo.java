package com.hmdzl.spspd.change.windows;

import com.hmdzl.spspd.change.items.DewVial;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.melee.special.ErrorW;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.ui.Window;
import com.hmdzl.spspd.change.utils.GLog;


public class WndEgoalInfo extends Window {

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndEgoalInfo(final Item item) {

		super();
		
		Item dewvial = new ErrorW();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(dewvial.image(), null));
		titlebar.label(Messages.titleCase(Messages.get(this, "title")));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene.renderMultiline(Messages.get(this, "info1"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0,titlebar.bottom() + GAP);
		add(message);
	
		RedButton btnYes = new RedButton(Messages.get(this, "yes")) {
			@Override
			protected void onClick() {
				hide();
				GLog.n(Messages.get(WndEgoalInfo.class, "tell1"));
			}
		};
		btnYes.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnYes);

		RedButton btnNo = new RedButton(Messages.get(this, "no")) {
			@Override
			protected void onClick() {
				hide();
				GLog.n(Messages.get(WndEgoalInfo.class, "tell2"));
			}
		};
		btnNo.setRect(0, btnYes.top() + btnYes.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnNo);	

		resize(WIDTH, (int) btnNo.bottom());
	}

	
}

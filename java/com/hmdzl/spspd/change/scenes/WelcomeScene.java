package com.hmdzl.spspd.change.scenes;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Chrome;
import com.hmdzl.spspd.change.Rankings;
import com.hmdzl.spspd.change.ShatteredPixelDungeon;
import com.hmdzl.spspd.change.ui.Archs;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.ScrollPane;
import com.hmdzl.spspd.change.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Component;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.RenderedText;
import com.hmdzl.spspd.change.ui.ExitButton;

//TODO: update this class with relevant info as new versions come out.
public class WelcomeScene extends PixelScene {
	
	
	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2 ;
		title.y = 4;
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		RenderedTextMultiline text = renderMultiline(Messages.get(WelcomeScene.class,"new_things"), 6 );


		int pw = w - 6;
		int ph = h - 40;


		NinePatch panel = Chrome.get(Chrome.Type.WINDOW);
		panel.size( pw, ph );
		panel.x = (w - pw) / 2;
		//panel.y = title.y + title.height() + 2;
		panel.y = (h - ph) / 2;
		add( panel );

		ScrollPane list = new ScrollPane( new Component() );
		add( list );

		Component content = list.content();
		content.clear();

		text.maxWidth((int) panel.innerWidth());

		content.add(text);

		content.setSize( panel.innerWidth(), text.height() );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop(),
				panel.innerWidth(),
				panel.innerHeight());
		list.scrollTo(0, 0);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
		
		RedButton okay = new RedButton(Messages.get(this, "continue")){
			@Override
			protected void onClick() {
				super.onClick();
				//updateVersion(previousVersion);
				ShatteredPixelDungeon.switchScene(TitleScene.class);
			}
		};	
		
		okay.setRect(title.x, h-20, title.width(), 16);
		okay.textColor(0xBBBB33);
		add(okay);		
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade(TitleScene.class);
	}
}

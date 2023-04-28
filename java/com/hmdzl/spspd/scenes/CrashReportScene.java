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
package com.hmdzl.spspd.scenes;

import com.hmdzl.spspd.Chrome;
import com.hmdzl.spspd.CrashHandler;
import com.hmdzl.spspd.ExceptionStrings;
import com.hmdzl.spspd.SPSSettings;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.Archs;
import com.hmdzl.spspd.ui.ExitButton;
import com.hmdzl.spspd.ui.Icons;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.ScrollPane;
import com.hmdzl.spspd.ui.Window;
import com.hmdzl.spspd.windows.IconTitle;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;

public class CrashReportScene extends PixelScene {

    private final ArrayList<CrashInfo> infos = new ArrayList<>();

    private static final int WIDTH		= 115;
    private static final int HEIGHT		= 120;

    @Override
    public void create() {
        super.create();

        int w = Camera.main.width;
        int h = Camera.main.height;

        RenderedText title = PixelScene.renderText(Messages.get(this, "title"), 9);
        title.hardlight(Window.TITLE_COLOR);
        title.x = (w - title.width()) / 2f;
        title.y = (16 - title.baseLine()) / 2f;
        align(title);
        add(title);

        ExitButton btnExit = new ExitButton();
        btnExit.setPos(Camera.main.width - btnExit.width(), 0);
        add(btnExit);


        RedButton btnDelete = new RedButton( Messages.get(this, "clear") ) {
            @Override
            protected void onClick() {
                FileUtils.deleteFile( CrashHandler.CRASH_FILE );
                onBackPressed();
            }
        };
        btnDelete.setSize( 40, 16 );
        btnDelete.setPos( 0, btnExit.top() );
        add( btnDelete );

        /*
        RedButton btnDelete1 = new RedButton( Messages.get(this, "clear") ) {
            @Override
            protected void onClick() {
                //cause to crash;
                int i = 1/0;
            }
        };
        btnDelete1.setSize( 40, 16 );
        btnDelete1.setPos( 0, Camera.main.height - btnDelete1.height() - 2 );
        add( btnDelete1 );
        */

        NinePatch panel = Chrome.get(Chrome.Type.TOAST);

        int pw = WIDTH + panel.marginLeft() + panel.marginRight() - 2;
        int ph = h - 16;

        panel.size(pw, ph);
        panel.x = (w - pw) / 2f;
        panel.y = title.y + title.height();
        align(panel);
        add(panel);

        ScrollPane list = new ScrollPane(new Component()) {

            @Override
            public void onClick(float x, float y) {
                for (CrashInfo info : infos){
                    if (info.onClick( x, y )){
                        return;
                    }
                }
            }

        };
        add(list);

        ArrayList<ExceptionStrings> exceptions = new ArrayList<>();
        Bundle bundle = new Bundle();
        try {
            Bundle exceptionBundle = FileUtils.bundleFromFile(CrashHandler.CRASH_FILE);
            for (Bundlable expt : exceptionBundle.getCollection(CrashHandler.EXCEPTIONS)) {
                if (expt != null) {
                    exceptions.add((ExceptionStrings) expt);
                }
            }
        }catch (IOException e){
            ShatteredPixelDungeon.reportException(e);
            return;
        }

        CrashInfo info = new CrashInfo();
        for(ExceptionStrings expt: exceptions){
            CrashReportButton crb = new CrashReportButton(expt);
            info.addButton(crb);
        }
        infos.add(info);

        Component content = list.content();
        content.clear();

        float posY = 0;
        for (CrashInfo info1 : infos){
            info1.setRect(0, posY, panel.innerWidth(), 0);
            content.add(info1);
            posY += info1.height() + 2;
        }


        content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

        list.setRect(
                panel.x + panel.marginLeft(),
                panel.y + panel.marginTop() - 1,
                panel.innerWidth(),
                panel.innerHeight() + 2);
        list.scrollTo(0, 0);

        Archs archs = new Archs();
        archs.setSize( Camera.main.width, Camera.main.height );
        addToBack( archs );

        fadeIn();

        /*
        bundle.put(CrashHandler.EXCEPTIONS, exceptions);
        try {
            FileUtils.bundleToFile( CrashHandler.CRASH_FILE, bundle );
        } catch (IOException e) {
            Fushiginopixeldungeon.reportException(e);
        }
        */
    }

    @Override
    protected void onBackPressed() {
        ShatteredPixelDungeon.switchNoFade(TitleScene.class);
    }


    private static class CrashInfo extends Component {

        private ArrayList<CrashReportButton> buttons = new ArrayList<>();

        public void addButton( CrashReportButton button ){
            buttons.add(button);
            add(button);

            button.setSize(16, 16);
            layout();
        }

        public boolean onClick( float x, float y ){
            for( CrashReportButton button : buttons){
                if (button.inside(x, y)){
                    button.onClick();
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void layout() {
            float posY = this.y + 2;

            float posX = x;
            float tallest = 0;
            for (CrashReportButton change : buttons){

                //move to left
                posX = x;
                posY += tallest;

                change.setPos(posX, posY);
                tallest = change.height();
            }
            posY += tallest + 2;

            height = posY - this.y;
        }
    }

    //not actually a button, but functions as one.
    private static class CrashReportButton extends Component {

        protected Image icon;
        protected ExceptionStrings es;
        RenderedText txt;

        public CrashReportButton(ExceptionStrings es){
            super();

            this.icon = Icons.get(Icons.WARNING);
            add(this.icon);

            this.es = es;

            txt = PixelScene.renderText( es.fileName, 8 );
            txt.x = icon.width + 2;
            txt.y = this.y - (int)(icon.height -  txt.baseLine()) / 2;
            add( txt );
            layout();
        }

        protected void onClick() {
            ShatteredPixelDungeon.scene().add(new CrashReportWindow(new Image(icon), "", es.exception + "\n" + es.result ));
        }

        @Override
        protected void layout() {
            super.layout();

            icon.x = x;
            icon.y = y;
            PixelScene.align(icon);
            txt.x = x + icon.width + 2;
            txt.y = y + (int)(icon.height - txt.baseLine()) / 2;
        }
    }

    private static class CrashReportWindow extends Window {

        protected static final int WIDTH_P    = 120;
        protected static final int WIDTH_L    = 160;
        private static final int HEIGHT		= 120;
        protected static final int GAP	= 2;

        public CrashReportWindow( Image icon, String title, String message ) {

            this( new IconTitle( icon, title ), message );

        }

        public CrashReportWindow( Component titlebar, String message ) {

            super();

            int width = SPSSettings.landscape() ? WIDTH_L : WIDTH_P;
            resize( width, HEIGHT );

            titlebar.setRect( 0, 0, width, 0 );
            add(titlebar);

            RenderedTextMultiline text = PixelScene.renderMultiline( message, 4 );
            text.maxWidth(width);
            text.setPos( titlebar.left(), titlebar.bottom() + GAP );
            //add( text );

            ScrollPane list = new ScrollPane(new Component());
            add(list);
            Component content = list.content();
            content.clear();

            content.add(text);
            content.setSize( width, text.height() );

            list.setRect(
                    0,
                    titlebar.bottom(),
                    width,
                    height - titlebar.bottom());
            list.scrollTo(0, 0);


            /*
            add( new TouchArea( chrome ) {
                @Override
                protected void onClick( Touchscreen.Touch touch ) {
                    hide();
                }
            } );
            */
        }
        /*
        public CrashReportWindow( Image icon, String title, String message ) {
            super( icon, title, message);

            ScrollPane list = new ScrollPane(new Component());
            add(list);
            Component content = list.content();
            content.clear();
            list.setRect(
                    0,
                    0,
                    width,
                    height);
            list.scrollTo(0, 0);


            add( new TouchArea( chrome ) {
                @Override
                protected void onClick( Touchscreen.Touch touch ) {
                    hide();
                }
            } );

        }
        */

    }
}



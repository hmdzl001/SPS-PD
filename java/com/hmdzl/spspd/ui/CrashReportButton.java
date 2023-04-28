package com.hmdzl.spspd.ui;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.scenes.CrashReportScene;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;

public class CrashReportButton extends Button {

    protected Image image;

    public CrashReportButton() {
        super();

        width = image.width;
        height = image.height;
    }

    @Override
    protected void createChildren() {
        super.createChildren();

        image = Icons.CRASH_R.get();
        add( image );
    }

    @Override
    protected void layout() {
        super.layout();

        image.x = x;
        image.y = y;
    }

    @Override
    protected void onTouchDown() {
        image.brightness( 1.5f );
        Sample.INSTANCE.play( Assets.SND_CLICK );
    }

    @Override
    protected void onTouchUp() {
        image.resetColor();
    }

    @Override
    protected void onClick() {
        ShatteredPixelDungeon.switchNoFade(CrashReportScene.class);
    }
}

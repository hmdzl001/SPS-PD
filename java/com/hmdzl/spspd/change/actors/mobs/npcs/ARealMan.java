package com.hmdzl.spspd.change.actors.mobs.npcs;


import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ARealManSprite;
import com.hmdzl.spspd.change.sprites.G2159687Sprite;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.windows.WndHotel;
import com.hmdzl.spspd.change.windows.WndMix;
import com.hmdzl.spspd.change.windows.WndONS;
import com.watabou.utils.Random;

public class ARealMan extends NPC {

	{
	   //name = Messages.get(this,"name");
	   spriteClass = ARealManSprite.class;
	   properties.add(Property.BEAST);
	}

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}	
	
	@Override
	public int evadeSkill(Char enemy) {
		return 1000;
	}


	@Override
	protected Char chooseEnemy() {
		return null;
	}

	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

   
	
	@Override
	public boolean interact() {
		
		sprite.turnTo(pos, Dungeon.hero.pos);
        GameScene.show(new WndMix());
		return true;
	}
	
}
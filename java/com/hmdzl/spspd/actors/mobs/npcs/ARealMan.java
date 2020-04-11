package com.hmdzl.spspd.actors.mobs.npcs;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ARealManSprite;
import com.hmdzl.spspd.windows.WndMix;

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
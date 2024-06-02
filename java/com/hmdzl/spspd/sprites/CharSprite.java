/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.sprites;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.DarkBlock;
import com.hmdzl.spspd.effects.EmoIcon;
import com.hmdzl.spspd.effects.FloatingText;
import com.hmdzl.spspd.effects.FloatingText2;
import com.hmdzl.spspd.effects.IceBlock;
import com.hmdzl.spspd.effects.ShieldHalo;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.effects.TorchHalo;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.effects.particles.LeafParticle;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.effects.particles.SnowParticle;
import com.hmdzl.spspd.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.ui.CharHealthIndicator;
import com.watabou.noosa.Game;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.Visual;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.tweeners.PosTweener;
import com.watabou.noosa.tweeners.Tweener;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class CharSprite extends MovieClip implements Tweener.Listener, MovieClip.Listener {
	
	// Color constants for floating text
	public static final int DEFAULT		= 0xFFFFFF;
	//white
	public static final int POSITIVE	= 0x00FF00;
	//green
	public static final int NEGATIVE	= 0xFF0000;
	//red
	public static final int WARNING		= 0xFF8800;
	//orange
	public static final int NEUTRAL		= 0xFFFF00;
    //yellow
	public static final int VIOLET		= 0x9400D3;
	//violet
	public static final int BLUE		= 0x00CCFF;
	//blue
	public static final int MELEE_DAMAGE		= 0xFF8800;
	//orange
	public static final int MAGIC_DAMAGE		= 0x00CCFF;
	//blue
	public static final int NULL_DAMAGE		= 0xFFFFFF;
    //white
	public static final int EAT_SOME		= 0xCC9966;
	//violet

	private static final float MOVE_INTERVAL	= 0.1f;
	private static final float FLASH_INTERVAL	= 0.05f;
	
	public enum State {
		BURNING, LEVITATING, INVISIBLE, PARALYSED, FROZEN, ILLUMINATED, CHILLED, DARKENED, MARKED, REGROW, HEALING,SKILLREADY, SHIELDED,
	}
	
	protected Animation idle;
	protected Animation run;
	protected Animation attack;
	protected Animation operate;
	protected Animation zap;
	protected Animation die;
	protected Animation antidie;
	
	protected Callback animCallback;
	
	protected Tweener motion;
	
	protected Emitter burning;
	protected Emitter chilled;
	protected Emitter marked;
	protected Emitter levitation;
	protected Emitter regrow;
	protected Emitter healing;

	protected Emitter skillready;
	protected PosTweener motion2;
	protected IceBlock iceBlock;
	protected DarkBlock darkBlock;
	protected TorchHalo halo;
	protected ShieldHalo shield;
	protected AlphaTweener invisible;
	
	protected EmoIcon emo;
	protected CharHealthIndicator health;

	private Tweener jumpTweener;

	private Tweener rollTweener;

	private Callback jumpCallback;

	private Callback rollCallback;

	//private float flashTime = 0;
	protected float flashTime = 0;

	protected boolean sleeping = false;

	public Char ch;

	public volatile boolean isMoving = false;
	
	public CharSprite() {
		super();
		listener = this;
	}

	public void link( Char ch ) {
		this.ch = ch;
		ch.sprite = this;
		
		place( ch.pos );
		turnTo( ch.pos, Random.Int( Floor.LENGTH ) );

		if (ch != Dungeon.hero) {
			if (health == null) {
				health = new CharHealthIndicator(ch);
			} else {
				health.target(ch);
			}
		}		
		
		ch.updateSpriteState();
	}
	
	public PointF worldToCamera( int cell ) {
		
		final int csize = DungeonTilemap.SIZE;
		
		return new PointF(
			((cell % Floor.WIDTH) + 0.5f) * csize - width * 0.5f,
			((cell / Floor.WIDTH) + 1.0f) * csize - height
		);
	}
	
	public void place( int cell ) {
		point( worldToCamera( cell ) );
	}
	
	//public void showStatus2( int color, String text, Object... args ) {
	//	if (visible) {
	//		if (args.length > 0) {
	//			text = Messages.format( text, args );
	//		}
	//		if (ch != null) {
	//			PointF tile = DungeonTilemap.tileCenterToWorld(ch.pos);
	//			FloatingText.show( tile.x, tile.y-(width*0.5f), ch.pos, text, color );
	//		} else {
	//			FloatingText.show( x + width * 0.5f, y, text, color );
	//		}
	//	}
	//}

	public void showStatus( int color, String text, Object... args ) {
		showStatusWithIcon(color, text, FloatingText2.NO_ICON, args);
	}

	public void showStatusWithIcon( int color, String text, int icon, Object... args ) {
		if (visible) {
			if (args.length > 0) {
				text = Messages.format( text, args );
			}
			//float x = destinationCenter().x;
			//float y = destinationCenter().y - height()/2f;
			if (ch != null) {
				PointF tile = DungeonTilemap.tileCenterToWorld(ch.pos);
				//FloatingText2.show( x, y, ch.pos, text, color, icon, true );
				FloatingText2.show( tile.x, tile.y-(width*0.5f), ch.pos, text, color, icon, true );
			} else {
				//FloatingText2.show( x, y, -1, text, color, icon, true );
				FloatingText2.show( x + width * 0.5f, y, -1, text, color, icon, true );
			}
		}
	}

	public PointF destinationCenter(){
		PosTweener motion2 = this.motion2;
		if (motion2 != null && motion2.elapsed >= 0){
			return new PointF(motion2.end.x + width()/2f, motion2.end.y + height()/2f);
		} else {
			return center();
		}
	}
	
	public void idle() {
		play( idle );
	}
	
	public void move( int from, int to ) {
		turnTo( from , to );

		play( run );
		
		motion = new PosTweener( this, worldToCamera( to ), MOVE_INTERVAL );
		motion.listener = this;
		parent.add( motion );

		isMoving = true;
		
		if (visible && Floor.water[from] && !ch.flying) {
			GameScene.ripple( from );
		}

	}
	
	public void interruptMotion() {
		if (motion != null) {
			onComplete( motion );
		}
	}
	
	public void attack( int cell ) {
		turnTo( ch.pos, cell );
		play( attack );
	}
	
	public void attack( int cell, Callback callback ) {
		animCallback = callback;
		turnTo( ch.pos, cell );
		play( attack );
	}
	
	public void operate( int cell ) {
		turnTo( ch.pos, cell );
		play( operate );
	}
	
	public void zap( int cell ) {
		turnTo( ch.pos, cell );
		play( zap );
	}
	
	public void zap( int cell, Callback callback ) {
		animCallback = callback;
		zap( cell );
	}	
	
	public void turnTo( int from, int to ) {
		int fx = from % Floor.WIDTH;
		int tx = to % Floor.WIDTH;
		if (tx > fx) {
			flipHorizontal = false;
		} else if (tx < fx) {
			flipHorizontal = true;
		}
	}

	public void jump( int from, int to, Callback callback ) {
		jumpCallback = callback;

		int distance = Floor.distance( from, to );
		jumpTweener = new JumpTweener( this, worldToCamera( to ), distance * 4, distance * 0.1f );
		jumpTweener.listener = this;
		parent.add( jumpTweener );

		turnTo( from, to );
	}

	public void roll( int from, int to, Callback callback ) {
		rollCallback = callback;
		int distance = Floor.distance( from, to );
		rollTweener = new RollTweener( this, worldToCamera( to ), distance * 4, distance * 0.1f );
		rollTweener.listener = this;
		parent.add( rollTweener );

		turnTo( from, to );
	}

	public void die() {
		sleeping = false;
		play( die );
		
		if (emo != null) {
			emo.killAndErase();
		}
		if (health != null){
			health.killAndErase();
		}
	}

	public void antidie(int cell) {
		turnTo( ch.pos, cell );
		play( antidie );
	}
	
	public Emitter emitter() {
		Emitter emitter = GameScene.emitter();
		emitter.pos( this );
		return emitter;
	}
	
	public Emitter centerEmitter() {
		Emitter emitter = GameScene.emitter();
		emitter.pos( center() );
		return emitter;
	}
	
	public Emitter bottomEmitter() {
		Emitter emitter = GameScene.emitter();
		emitter.pos( x, y + height, width, 0 );
		return emitter;
	}
	
	public void burst( final int color, int n ) {
		if (visible) {
			Splash.at( center(), color, n );
		}
	}
	
	public void bloodBurstA( PointF from, int damage ) {
		if (visible) {
			PointF c = center();
			int n = (int)Math.min( 9 * Math.sqrt( (double)damage / ch.HT ), 9 );
			Splash.at( c, PointF.angle( from, c ), 3.1415926f / 2, blood(), n );
		}
	}

	public int blood() {
		return 0xFFBB0000;
	}
	
	public void flash() {
		ra = ba = ga = 1f;
		flashTime = FLASH_INTERVAL;
	}
	
	public void add( State state ) {
		switch (state) {
			case BURNING:
				burning = emitter();
				burning.pour( FlameParticle.FACTORY, 0.1f );
				if (visible) {
					Sample.INSTANCE.play( Assets.SND_BURNING );
				}
				break;
			case LEVITATING:
				levitation = emitter();
				levitation.pour( Speck.factory( Speck.JET ), 0.2f );
				break;
			case INVISIBLE:
				PotionOfInvisibility.melt( ch );
				break;
			case PARALYSED:
				paused = true;
				break;
			case FROZEN:
				iceBlock = IceBlock.freeze( this );
				paused = true;
				break;
			case ILLUMINATED:
				GameScene.effect( halo = new TorchHalo( this ) );
				break;
			case CHILLED:
				chilled = emitter();
				chilled.pour(SnowParticle.FACTORY, 0.1f);
				break;
			case DARKENED:
				darkBlock = DarkBlock.darken( this );
				break;				
			case MARKED:
				marked = emitter();
				marked.pour(ShadowParticle.UP, 0.1f);
				break;
				
			case REGROW:
				regrow = emitter();
				regrow.pour( LeafParticle.LEVEL_SPECIFIC, 0.1f );
				break;				
			case HEALING:
				healing = emitter();
				healing.pour(Speck.factory(Speck.HEALING), 0.5f);
				break;
			case SKILLREADY:
				skillready = emitter();
				skillready.pour(Speck.factory(Speck.BUBBLE), 0.5f);
				break;
			case SHIELDED:
				GameScene.effect( shield = new ShieldHalo( this ));
				break;			
		}
	}
	
	public void remove( State state ) {
		switch (state) {
			case BURNING:
				if (burning != null) {
					burning.on = false;
					burning = null;
				}
				break;
			case LEVITATING:
				if (levitation != null) {
					levitation.on = false;
					levitation = null;
				}
				break;
			case INVISIBLE:
				alpha( 1f );
				break;
			case PARALYSED:
				paused = false;
				break;
			case FROZEN:
				if (iceBlock != null) {
					iceBlock.melt();
					iceBlock = null;
				}
				paused = false;
				break;
			case ILLUMINATED:
				if (halo != null) {
					halo.putOut();
				}
				break;
			case CHILLED:
				if (chilled != null){
					chilled.on = false;
					chilled = null;
				}
				break;
			case DARKENED:
				if (darkBlock != null) {
					darkBlock.lighten();
					darkBlock = null;
				}
				break;				
			case MARKED:
				if (marked != null){
					marked.on = false;
					marked = null;
				}
				break;
			case REGROW:
				if (regrow != null) {
					regrow.on = false;
					regrow = null;
				}
				break;
			case HEALING:
				if (healing != null){
					healing.on = false;
					healing = null;
				}
				break;
			case SKILLREADY:
				if (skillready != null){
					skillready.on = false;
					skillready = null;
				}
				break;
			case SHIELDED:
				if (shield != null){
					shield.putOut();
				}
				break;				
		}
	}
	
	@Override
	public void update() {
		
		super.update();
		
		if (paused && listener != null) {
			listener.onComplete( curAnim );
		}
		
		if (flashTime > 0 && (flashTime -= Game.elapsed) <= 0) {
			resetColor();
		}

		if (burning != null) {
			burning.visible = visible;
		}
		if (levitation != null) {
			levitation.visible = visible;
		}
		if (iceBlock != null) {
			iceBlock.visible = visible;
		}
		if (chilled != null) {
			chilled.visible = visible;
		}
		if (marked != null) {
			marked.visible = visible;
		}		
		if (sleeping) {
			showSleep();
		} else {
			hideSleep();
		}
		if (emo != null) {
			emo.visible = visible;
		}
		if (regrow != null) {
			regrow.visible = visible;
		}

		if (skillready != null) {
			skillready.visible = visible;
		}

	}
	
	@Override
	public void resetColor() {
		super.resetColor();
		if (invisible != null){
			alpha(0.4f);
		}
	}	
	
	public synchronized void showSleep() {
		if (emo instanceof EmoIcon.Sleep) {
			
		} else {
			if (emo != null) {
				emo.killAndErase();
			}
			emo = new EmoIcon.Sleep( this );
		}
		idle();
	}
	
	public synchronized void hideSleep() {
		if (emo instanceof EmoIcon.Sleep) {
			emo.killAndErase();
			emo = null;
		}
	}
	
	public synchronized void showAlert() {
		if (emo instanceof EmoIcon.Alert) {
			
		} else {
			if (emo != null) {
				emo.killAndErase();
			}
			emo = new EmoIcon.Alert( this );
			emo.visible = visible;
		}
	}
	
	public synchronized void hideAlert() {
		if (emo instanceof EmoIcon.Alert) {
			emo.killAndErase();
			emo = null;
		}
	}
	
	public synchronized void showLost() {
		if (emo instanceof EmoIcon.Lost) {
		
		} else {
			if (emo != null) {
				emo.killAndErase();
			}
			emo = new EmoIcon.Lost( this );
			emo.visible = visible;
		}
	}
	
	public synchronized void hideLost() {
		if (emo instanceof EmoIcon.Lost) {
			emo.killAndErase();
			emo = null;
		}
	}	
	
	@Override
	public void kill() {
		super.kill();
		
		if (emo != null) {
			emo.killAndErase();
			emo = null;
		}
		
		if (health != null){
			health.killAndErase();
		}		
		
	}
	
	@Override
	public void onComplete( Tweener tweener ) {
		if (tweener == jumpTweener) {

			if (visible && Floor.water[ch.pos] && !ch.flying) {
				GameScene.ripple( ch.pos );
			}
			if (jumpCallback != null) {
				jumpCallback.call();
			}

		} else if (tweener == motion) {

			isMoving = false;

			motion.killAndErase();
			motion = null;
			ch.onMotionComplete();
		}
	}

	@Override
	public void onComplete( Animation anim ) {
		
		if (animCallback != null) {
			animCallback.call();
			animCallback = null;
		} else {
			
			if (anim == attack) {
				
				idle();
				ch.onAttackComplete();
				
			} else if (anim == operate) {
				
				idle();
				ch.onOperateComplete();
				
			}
			
		}
	}

	private static class JumpTweener extends Tweener {

		public Visual visual;

		public PointF start;
		public PointF end;

		public float height;

		public JumpTweener( Visual visual, PointF pos, float height, float time ) {
			super( visual, time );

			this.visual = visual;
			start = visual.point();
			end = pos;

			this.height = height;
		}

		@Override
		protected void updateValues( float progress ) {
			visual.point( PointF.inter( start, end, progress ).offset( 0, -height * 4 * progress * (1 - progress) ) );
		}
	}


	private static class RollTweener extends Tweener {

		public Visual visual;

		public PointF start;
		public PointF end;

		public float height;

		public RollTweener( Visual visual, PointF pos, float height, float time ) {
			super( visual, time );

			this.visual = visual;
			start = visual.point();
			end = pos;

			this.height = height;
		}

		@Override
		protected void updateValues( float progress ) {
			visual.point( PointF.inter( start, end, progress ).offset( -height * 4 * progress * (1 - progress), 0  ) );
		}
	}
}

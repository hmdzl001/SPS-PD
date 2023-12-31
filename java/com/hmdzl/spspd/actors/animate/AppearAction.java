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
package com.hmdzl.spspd.actors.animate;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Wound;
import com.hmdzl.spspd.effects.particles.BlastParticle;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.traps.PitfallTrap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.sprites.MobSprite;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.noosa.tweeners.ScaleTweener;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class AppearAction {

	public static void heroLand() {

		Sample.INSTANCE.play(Assets.SND_ROCKS);

		Hero hero = Dungeon.hero;

		hero.sprite.jump(hero.pos-5,hero.pos,new Callback() {
			@Override
			public void call() {
				hero.move(hero.pos);
				Camera.main.shake(4, 0.5f);
			}
		});
		hero.sprite.burst(hero.sprite.blood(), 10);


	}

	public static void heroBlink( ) {

		Sample.INSTANCE.play( Assets.SND_BLAST );
		Hero hero = Dungeon.hero;
		CellEmitter.center(hero.pos).burst(BlastParticle.FACTORY, 30);
		hero.sprite.alpha( 0 );
		hero.sprite.parent.add( new AlphaTweener( hero.sprite, 1, 0.4f ));
		hero.sprite.emitter().start( Speck.factory(Speck.LIGHT), 0.2f, 3 );

		Sample.INSTANCE.play( Assets.SND_TELEPORT );

	}

	public static void heroRoll() {
		Sample.INSTANCE.play(Assets.SND_LULLABY);
		Sample.INSTANCE.play(Assets.SND_LULLABY);
		Sample.INSTANCE.play(Assets.SND_LULLABY);

		Hero hero = Dungeon.hero;

		hero.sprite.origin.set(hero.sprite.width / 2, hero.sprite.height - DungeonTilemap.SIZE / 2);
		hero.sprite.angularSpeed = 720;
		hero.sprite.parent.add(new ScaleTweener(hero.sprite, new PointF(1, 1), 1.5f) {
			@Override
			protected void onComplete() {

				hero.sprite.angularSpeed = 0;
				parent.erase(this);
			}

			@Override
			protected void updateValues(float progress) {
				super.updateValues(progress);
				//am = 1 - progress;
			}
		});
		hero.sprite.roll(hero.pos - 4,hero.pos,new Callback() {
			@Override
			public void call() {
				hero.move(hero.pos);
			}
		});
	}

	public static void heroGold() {

		Sample.INSTANCE.play(Assets.SND_GOLD);

		Hero hero = Dungeon.hero;

		CellEmitter.center(hero.pos).burst(Speck.factory(Speck.COIN), 3);
		Sample.INSTANCE.play(Assets.SND_GOLD);
		CellEmitter.center(hero.pos).burst(Speck.factory(Speck.COIN), 3);
		Sample.INSTANCE.play(Assets.SND_GOLD);
		CellEmitter.center(hero.pos).burst(Speck.factory(Speck.COIN), 3);
		Sample.INSTANCE.play(Assets.SND_GOLD);
		CellEmitter.center(hero.pos).burst(Speck.factory(Speck.COIN), 3);
		Sample.INSTANCE.play(Assets.SND_GOLD);

		hero.sprite.alpha( 0 );
		hero.sprite.parent.add( new AlphaTweener( hero.sprite, 1, 0.4f ) );
		hero.sprite.emitter().start( Speck.factory(Speck.COIN), 0.2f, 3 );

	}

	public static void heroLife() {
		Sample.INSTANCE.play(Assets.SND_BEACON);
		Sample.INSTANCE.play(Assets.SND_BEACON);

		Hero hero = Dungeon.hero;

		hero.sprite.jump(hero.pos+5,hero.pos,new Callback() {
			@Override
			public void call() {
				hero.sprite.emitter().start( Speck.factory(Speck.STAR), 0.2f, 3 );
				hero.sprite.antidie(hero.pos);
				hero.move(hero.pos);

			}
		});

	}

}

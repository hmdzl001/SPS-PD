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
package com.hmdzl.spspd.ui;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Chrome;
import com.hmdzl.spspd.scenes.PixelScene;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;

/*public class RedButton extends Button {
	
	protected NinePatch bg;
	protected RenderedText text;
	protected Image icon;
			
	public RedButton(String label ) {
		this(label, 9);
	}

	public RedButton(String label, int size ){
		super();

		text = PixelScene.renderText( size );
		text.text( label );
		add( text );
	}
	
	@Override
	protected void createChildren() {
		super.createChildren();
		
		bg = Chrome.get( Chrome.Type.BUTTON );
		add( bg );
	}
	
	@Override
	protected void layout() {
		
		super.layout();
		
		bg.x = x;
		bg.y = y;
		bg.size( width, height );
		
		text.x = x + (width - text.width()) / 2;
		text.y = y + (height - text.baseLine()) / 2;
		PixelScene.align(text);
		
		if (icon != null) {
			icon.x = x + text.x - icon.width() - 2;
			icon.y = y + (height - icon.height()) / 2;
			PixelScene.align(icon);
		}
	}

	@Override
	protected void onTouchDown() {
		bg.brightness( 1.2f );
		Sample.INSTANCE.play( Assets.SND_CLICK );
	}
	
	@Override
	protected void onTouchUp() {
		bg.resetColor();
	}
	
	public void enable( boolean value ) {
		active = value;
		text.alpha( value ? 1.0f : 0.3f );
	}
	
	public void text( String value ) {
		text.text( value );
		layout();
	}

	public void textColor( int value ) {
		text.hardlight( value );
	}

	public void icon( Image icon ) {
		if (this.icon != null) {
			remove( this.icon );
		}
		this.icon = icon;
		if (this.icon != null) {
			add( this.icon );
			layout();
		}
	}
	
	public float reqWidth() {
		return text.width() + 2f;
	}
	
	public float reqHeight() {
		return text.baseLine() + 4;
	}
}*/

/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
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

public class RedButton extends Button {
	
	protected NinePatch bg;
	protected RenderedText text;
	protected Image icon;
			
	public RedButton( String label ) {
		this(label, 9);
	}

	public RedButton( Image image, String label ) {
		super();
		icon = image;
		add(icon);

		text = PixelScene.renderText( 9 );
		text.text( label );
		add( text );
	}

	public RedButton( String label, int size ){
		super();

		text = PixelScene.renderText( size );
		text.text( label );
		add( text );
	}

	public RedButton(Image image, String label, int size ){
		super();

		icon = image;
		add(icon);

		text = PixelScene.renderText( size );
		text.text( label );
		add( text );
	}
	
	@Override
	protected void createChildren() {
		super.createChildren();
		
		bg = Chrome.get( Chrome.Type.BUTTON );
		add( bg );
	}
	
	@Override
	protected void layout() {
		
		super.layout();
		
		bg.x = x;
		bg.y = y;
		bg.size( width, height );
		
		float componentWidth = 0;
		
		if (icon != null) componentWidth += icon.width() + 2;
		
		if (text != null && !text.text().equals("")){
			componentWidth += text.width() + 2;
			
			text.x = x + (width() - componentWidth)/2f + 1;
			text.y = y + (height() - text.baseLine()) / 2f;
			PixelScene.align(text);
			
		}
		
		if (icon != null) {
			
			icon.x = x + (width() + componentWidth)/2f - icon.width() - 1;
			icon.y = y + (height() - icon.height()) / 2f;
			PixelScene.align(icon);
		}
		
	}

	@Override
	protected void onTouchDown() {
		bg.brightness( 1.2f );
		Sample.INSTANCE.play( Assets.SND_CLICK );
	}
	
	@Override
	protected void onTouchUp() {
		bg.resetColor();
	}
	
	public void enable( boolean value ) {
		active = value;
		text.alpha( value ? 1.0f : 0.3f );
	}
	
	public void text( String value ) {
		text.text( value );
		layout();
	}

	public void textColor( int value ) {
		text.hardlight( value );
	}

	public void icon( Image icon ) {
		if (this.icon != null) {
			remove( this.icon );
		}
		this.icon = icon;
		if (this.icon != null) {
			add( this.icon );
			layout();
		}
	}
	
	public Image icon(){
		return icon;
	}
	
	public float reqWidth() {
		float reqWidth = 0;
		if (icon != null){
			reqWidth += icon.width() + 2;
		}
		if (text != null && !text.text().equals("")){
			reqWidth += text.width() + 2;
		}
		return reqWidth;
	}
	
	public float reqHeight() {
		float reqHeight = 0;
		if (icon != null){
			reqHeight = Math.max(icon.height() + 4, reqHeight);
		}
		if (text != null && !text.text().equals("")){
			reqHeight = Math.max(text.baseLine() + 4, reqHeight);
		}
		return reqHeight;
	}
}


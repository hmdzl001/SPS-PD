

package com.watabou.noosa;

import com.watabou.glscripts.Script;

//This class should be used on heavy pixel-fill based loads when lighting is not needed.
// It skips the lighting component of the fragment shader, giving a significant performance boost

//Remember that switching programs is expensive
// if this script is to be used many times try to block them together
public class NoosaScriptNoLighting extends NoosaScript {

	@Override
	public void lighting(float rm, float gm, float bm, float am, float ra, float ga, float ba, float aa) {
		//Does nothing
	}

	public static NoosaScriptNoLighting get(){
		return Script.use( NoosaScriptNoLighting.class );
	}

	@Override
	protected String shader() {
		return SHADER;
	}

	private static final String SHADER =

			"uniform mat4 uCamera;" +
			"uniform mat4 uModel;" +
			"attribute vec4 aXYZW;" +
			"attribute vec2 aUV;" +
			"varying vec2 vUV;" +
			"void main() {" +
			"  gl_Position = uCamera * uModel * aXYZW;" +
			"  vUV = aUV;" +
			"}" +

			"//\n" +

			"varying mediump vec2 vUV;" +
			"uniform lowp sampler2D uTex;" +
			"void main() {" +
			"  gl_FragColor = texture2D( uTex, vUV );" +
			"}";
}

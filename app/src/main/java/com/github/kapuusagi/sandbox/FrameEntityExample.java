package com.github.kapuusagi.sandbox;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.github.kapuusagi.sandbox.menu.texture.FrameTexture;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;

public class FrameEntityExample extends SimpleBaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 480;
    private static final int CAMERA_HEIGHT = 320;

    // ===========================================================
    // Fields
    // ===========================================================
    // 描画対象
    private BitmapTextureAtlas mBitmapTextureAtlas;
    // テクスチャ
    private ITextureRegion mDecoratedBalloonTextureRegion;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    public void onCreateResources() {
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),
                190, 190, TextureOptions.BILINEAR);

        // Canvasでレンダリングしたデータを貼るテクスチャを作成する。
        final IBitmapTextureAtlasSource baseTextureSource
                = new EmptyBitmapTextureAtlasSource(190, 190);
        // Canvasでレンダリングするためのラッパーを用意する。

        int fg = Color.WHITE;
        int bg = 0x80000000;

        final IBitmapTextureAtlasSource decoratedTextureAtlasSource
                = new FrameTexture(baseTextureSource, fg, bg);
        this.mDecoratedBalloonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(
                this.mBitmapTextureAtlas, decoratedTextureAtlasSource, 0, 0);
        // テクスチャを読み込む。このタイミングで onDecorateBitmap が実行される（多分）
        this.mBitmapTextureAtlas.load();
    }

    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();
        scene.setBackground(new Background(0.5f, 0.5f, 0.5f));

        /* Calculate the coordinates for the face, so its centered on the camera. */
        final float centerX = (CAMERA_WIDTH - this.mDecoratedBalloonTextureRegion.getWidth()) / 2;
        final float centerY = (CAMERA_HEIGHT - this.mDecoratedBalloonTextureRegion.getHeight()) / 2;

        /* Create the balloon and add it to the scene. */
        final Sprite balloon = new Sprite(centerX, centerY, this.mDecoratedBalloonTextureRegion, this.getVertexBufferObjectManager());
        balloon.registerEntityModifier(new LoopEntityModifier(new RotationModifier(60, 0, 360)));
        scene.attachChild(balloon);

        return scene;
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}

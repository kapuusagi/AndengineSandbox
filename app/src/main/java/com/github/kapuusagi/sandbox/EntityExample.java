package com.github.kapuusagi.sandbox;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.clip.ClipEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;

import java.io.IOException;
import java.io.InputStream;

public class EntityExample extends SimpleBaseGameActivity {

    private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;

    // フォントオブジェクト(Andengineのものなので注意)
    private Font mFont;
    private BitmapTextureAtlas mBitmapTextureAtlas;
    private ITextureRegion mDecoratedBalloonTextureRegion;

    /**
     * エンジンオプション生成時に呼ばれる。
     * onCreateScene()より前に呼び出される。
     * @return エンジンオプションを取得する。
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }
    /**
     * リソースを作成する。
     */
    @Override
    public void onCreateResources() {
        // フォント作成は onCreateResource()のタイミングで呼ぶ事。
        // onCreateEngineのタイミングで呼ぶと fontManager がnullで例外が発生する。
        Typeface typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
        this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(),
                256, 256, typeface, 32);
        this.mFont.load();
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 190, 190, TextureOptions.BILINEAR);

        final IBitmapTextureAtlasSource baseTextureSource = new EmptyBitmapTextureAtlasSource(190, 190);
        final IBitmapTextureAtlasSource decoratedTextureAtlasSource = new BaseBitmapTextureAtlasSourceDecorator(baseTextureSource) {
            @Override
            protected void onDecorateBitmap(Canvas pCanvas) throws Exception {
                pCanvas.drawRect(pCanvas.getClipBounds(), mPaint);
            }

            @Override
            public BaseBitmapTextureAtlasSourceDecorator deepCopy() {
                throw new IModifier.DeepCopyNotSupportedException();
            }
        };

        this.mDecoratedBalloonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(this.mBitmapTextureAtlas, decoratedTextureAtlasSource, 0, 0);
        this.mBitmapTextureAtlas.load();
    }

    /**
     * メインシーンを作成する。
     * 一般的には各シーンの派生クラスで描画オブジェクトを設定するような形になる。
     * @return シーンオブジェクト。
     */
    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();
        scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append("MenuItem").append(i + 1).append('\n');
        }
        final Text text = new Text(0, 0, this.mFont, sb.toString(),
                new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
        final ClipEntity clipEntity = new ClipEntity(100f, 100f, 100f,100f);
        clipEntity.attachChild(text);
        scene.attachChild(clipEntity);

        return scene;
    }
}

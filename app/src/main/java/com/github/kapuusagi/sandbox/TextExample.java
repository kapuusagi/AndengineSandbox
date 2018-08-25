package com.github.kapuusagi.sandbox;

import android.graphics.Typeface;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleLayoutGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * フォントから Text オブジェクトを生成して画面に描画するサンプル。
 *
 *  Font font = FontFactory.create(fontManager, textureManager, textureWidth, textureHeight, typeface, fontSize);
 *  このフォントを元にTextオブジェクトを構築してaddChildで追加する。
 */
public class TextExample extends SimpleLayoutGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;

    // ===========================================================
    // Fields
    // ===========================================================
    // フォントオブジェクト(Andengineのものなので注意)
    private Font mFont;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

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
        final Text centerText = new Text(100, 40, this.mFont,
                "Hello AndEngine!\nYou can even have multilined text!",
                new TextOptions(HorizontalAlign.CENTER), vertexBufferObjectManager);
        final Text leftText = new Text(100, 170, this.mFont,
                "Also left aligned!\nLorem ipsum dolor sit amat...",
                new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
        final Text rightText = new Text(100, 300, this.mFont,
                "And right aligned!\nLorem ipsum dolor sit amat...",
                new TextOptions(HorizontalAlign.RIGHT), vertexBufferObjectManager);

        scene.attachChild(centerText);
        scene.attachChild(leftText);
        scene.attachChild(rightText);

        return scene;
    }

    /**
     * レイアウトIDを取得する。
     * @return レイアウトID。
     */
    @Override
    protected int getLayoutID() {
        return R.layout.text_example;
    }

    /**
     * ビューIDを取得する。
     * @return ビューID。
     */
    @Override
    protected int getRenderSurfaceViewID() {
        return R.id.renderSurfaceView;
    }
    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}

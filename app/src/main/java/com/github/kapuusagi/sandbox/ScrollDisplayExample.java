package com.github.kapuusagi.sandbox;

import android.graphics.Color;
import android.graphics.Typeface;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.clip.ClipEntity;
import org.andengine.entity.parts.renderer.SimpleFrameRenderer;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.CanvasSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.IFont;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.ColorF;
import org.andengine.util.debug.Debug;

/**
 * メニューを実現するためのスクロール実装お試し。
 * スクロールさせたときの挙動がうまくないが、
 * 何よりテクスチャリソースを多く消費してるのが問題。
 */
public class ScrollDisplayExample extends SimpleBaseGameActivity {
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    // テキスト
    private Text mText;
    // フォント
    private IFont mFont;

    // 前のx座標
    private float mPrevX = -1f;
    // 前のy座標
    private float mPrevY = -1f;

    private float mXMax; // 設定可能な最大 x 位置
    private float mXMin; // 設定可能な最小 x 位置
    private float mYMax; // 設定可能な最大 y 位置
    private float mYMin; // 設定可能な最小 y 位置

    @Override
    protected void onCreateResources() {
        mFont = FontFactory.create(getFontManager(), getTextureManager(),
                256, 256, Typeface.DEFAULT, 32, true, Color.WHITE);
        mFont.load();
    }

    /**
     * エンジンオプションを取得する。
     * @return エンジンオプション
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(0f,0.0f,0f));

        final float centerX = CAMERA_WIDTH / 2;
        final float centerY = CAMERA_HEIGHT / 2;

        CanvasSprite canvasSprite = new CanvasSprite(getTextureManager(), centerX, centerY, CAMERA_WIDTH, CAMERA_HEIGHT,
                new SimpleFrameRenderer(), ColorF.WHITE, new ColorF(0f, 0f, 0.125f, 0.5f),
                getVertexBufferObjectManager());
        scene.attachChild(canvasSprite);

        Rectangle rectangle = new Rectangle(centerX, centerY,
                CAMERA_WIDTH - 40, CAMERA_HEIGHT - 40,
                getVertexBufferObjectManager()) {
            @Override
            public boolean onAreaTouched(final TouchEvent touchEvent, final float localX, final float localY) {
                procTouchEvent(touchEvent);
                return true;
            }
        };
        rectangle.setColor(new ColorF(0.1f, 0.1f,0.1f,1.0f));
        scene.attachChild(rectangle);
        scene.registerTouchArea(rectangle);

        // ClipEntityではTouchEventを受け取れなかった。そういう仕様のようだ。
        final float clipEntityX = rectangle.getWidth() / 2;
        final float clipEntityY = rectangle.getHeight() / 2;
        ClipEntity clipEntity = new ClipEntity(clipEntityX, clipEntityY, rectangle.getWidth(), rectangle.getHeight());
        clipEntity.setClippingEnabled(true);
        scene.registerTouchArea(clipEntity);
        rectangle.attachChild(clipEntity);

        // 描画対象のデータ
        // 選択状態は入っていない。
        // この実装ではメニューの配列データをまるっとテクスチャにするので、
        // 表示されない部分のテクスチャリソースがもったいない。
        StringBuilder msg = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            msg.append("メニュー項目").append(i + 1).append('\n');
        }
        mText = new Text(0, 0, mFont, msg.toString(),
                new TextOptions(HorizontalAlign.LEFT), getVertexBufferObjectManager());

        // 設定可能な範囲を計算する。
        // 移動処理のところで毎回計算してもいいけれど、キャッシュしておく。
        mXMax = mText.getWidth() / 2;
        mYMin = clipEntity.getHeight() - mText.getHeight() / 2;
        if (mText.getWidth() > clipEntity.getWidth()) {
            mXMin = clipEntity.getWidth() - mText.getWidth() / 2;
        } else {
            mXMin = mXMax;
        }
        if (mText.getHeight() > clipEntity.getHeight()) {
            mYMax = mText.getHeight() / 2;
        } else {
            mYMax = mYMin;
        }

        // ちょっとわかりにくいが、左下が原点になるので
        // mXMax と mYMinを指定すると、初期位置で対象の左上が表示領域の左上と一致するようになる。
        final float textX = mXMax;
        final float textY = mYMin;
        mText.setPosition(textX, textY);
        mText.setColor(ColorF.WHITE);
        mText.setZIndex(255);
        clipEntity.attachChild(mText);

        //resetEntityPosition(clipEntity);

        //scene.attachChild(mText);

        return scene;
    }

    /**
     * タッチイベントを処理する。
     * @param touchEvent タッチイベント
     */
    private void procTouchEvent(TouchEvent touchEvent) {
        if ((touchEvent.getAction() == TouchEvent.ACTION_DOWN)
                || ((touchEvent.getAction() == TouchEvent.ACTION_MOVE)
                && ((mPrevX < 0) || (mPrevY < 0)))) {
            mPrevX = touchEvent.getX();
            mPrevY = touchEvent.getY();
            Debug.d("Init x,y.");
        } else if (touchEvent.getAction() == TouchEvent.ACTION_MOVE) {
            float moveX = touchEvent.getX() - mPrevX;
            float moveY = touchEvent.getY() - mPrevY;

            float newX = mText.getX() + moveX;
            float newY = mText.getY() + moveY;

            // この辺の処理はもう少し改善の余地がある。
            // 現状では動かしたピクセル数分しかスクロールしない。
            // 操作性を考えると、例えば下の端までドラッグしたら、
            // 対象の一番下まで表示させたいと思うだろう。

            // 移動可能な範囲を調べ、範囲外なら修正する。
            if (newX < mXMin) {
                newX = mXMin;
            } else if (newX > mXMax) {
                newX = mXMax;
            }
            if (newY < mYMin) {
                newY = mYMin;
            } else if (newY > mYMax) {
                newY = mYMax;
            }
            mText.setPosition(newX, newY);

            mPrevX = touchEvent.getX();
            mPrevY = touchEvent.getY();
            Debug.d("Drag distance x=" + moveX + " y=" + moveY);
        } else {
            mPrevX = -1f;
            mPrevY = -1f;
            Debug.d("Reset x,y.");
        }
    }
}

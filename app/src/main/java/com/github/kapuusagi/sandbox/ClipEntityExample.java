package com.github.kapuusagi.sandbox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

import com.github.kapuusagi.sandbox.menu.texture.ButtonTexture;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.clip.ClipEntity;
import org.andengine.entity.parts.renderer.TriangleButtonRenderer;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.CanvasSprite;
import org.andengine.entity.sprite.ICanvasPaint;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.ColorF;

/**
 * ClipEntityの動作を見るためのクラス。
 * 左側の
 */
public class ClipEntityExample extends SimpleBaseGameActivity {
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    // 押されているボタン種別
    private String mPressedButtonType = null;

    // 動かす対象
    private CanvasSprite mCanvasSprite;

    // 座標を表示するためのフォント
    private Font mFont;
    // 座標を表示するためのテキスト
    private Text mTextCoordinate;
    // クリップエンティティ
    private ClipEntity mClipEntity;
    // クリップエリアを表すスプライト
    private CanvasSprite mClipAreaSprite;

    private RectF mCursorRange;

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
    protected void onCreateResources() {
        this.mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(),
                256, 256, Typeface.DEFAULT, 32, Color.WHITE);
        this.mFont.load();
    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(0.0f, 0.0f, 0.0f));

        {
            final float x = CAMERA_WIDTH * 3 / 4;
            final float y = CAMERA_HEIGHT / 2;
            final float width = CAMERA_WIDTH / 2 - 80;
            final float height = CAMERA_HEIGHT - 80;
            final float clipWidth = width - 80;
            final float clipHeight = height - 80;

            // クリップされる領域がわかりやすいように、Rectangleの上に同サイズのClipEntityを構築する。
            // Rectangleで背景色が変わるので、わかりやすく処理できる（はず）
            Rectangle clipArea = new Rectangle(x, y, width, height, getVertexBufferObjectManager());
            clipArea.setColor(0x40808080);
            scene.attachChild(clipArea);
            mClipAreaSprite = new CanvasSprite(getTextureManager(), x, y, clipWidth, clipHeight,
                    new ICanvasPaint() {
                        @Override
                        public void draw(Canvas canvas, Paint paint, int foregroundColor, int backgroundColor) {
                            paint.setColor(foregroundColor);
                            paint.setStrokeWidth(1);
                            paint.setAntiAlias(false);
                            paint.setStyle(Paint.Style.STROKE);
                            RectF rect = new RectF(0f, 0f, canvas.getWidth() - 1, canvas.getHeight() - 1);
                            canvas.drawRect(rect, paint);
                        }
                    }, ColorF.WHITE, ColorF.TRANSPARENT,
                    getVertexBufferObjectManager());
            mClipAreaSprite.setZIndex(300);
            scene.attachChild(mClipAreaSprite);

            // clipAreaの上にClipEntityを乗せるので、ClipEntityの位置はclipAreaからの
            // 相対座標になる。
            mClipEntity = new ClipEntity(width / 2, height / 2, clipWidth, clipHeight);
            mClipEntity.setClippingEnabled(true);
            clipArea.attachChild(mClipEntity);

            mCanvasSprite = new CanvasSprite(getTextureManager(), 0.0f, 0.0f, 40f, 40f,
                    new PositionRenderer(), ColorF.BLUE, new ColorF(0.0f, 0.0f, 0.0f, 0.125f),
                    getVertexBufferObjectManager());
            mCanvasSprite.setZIndex(255);

            mClipEntity.attachChild(mCanvasSprite);

            mCursorRange = new RectF(-100.0f, clipHeight + 100, clipWidth + 100, -100.0f);
        }

        {
            // 切り替え
            Rectangle onoffButton = new Rectangle(CAMERA_WIDTH / 4, 240, 320, 40, getVertexBufferObjectManager()) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    boolean clipEnabled = mClipEntity.isClippingEnabled();
                    mClipEntity.setClippingEnabled(!clipEnabled);
                    mClipAreaSprite.setVisible(!clipEnabled);
                    return true;
                }
            };
            onoffButton.setColor(0xffff8000);
            Text onoffMsgText = new Text(onoffButton.getWidth() / 2, onoffButton.getHeight() / 2, mFont,
                    "ClipEntity On/Off",
                    new TextOptions(HorizontalAlign.CENTER), getVertexBufferObjectManager());
            onoffButton.attachChild(onoffMsgText);
            scene.attachChild(onoffButton);
            scene.registerTouchArea(onoffButton);

            // 上ボタン
            CanvasSprite upButton = new CanvasSprite(getTextureManager(), 160f, 160f, 40f, 40f,
                    new TriangleButtonRenderer(TriangleButtonRenderer.DirectionType.UP), ColorF.WHITE, ColorF.RED,
                    getVertexBufferObjectManager()) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    onKeyTouchEvent(pSceneTouchEvent, "UP");
                    return true;
                }
            };
            scene.attachChild(upButton);
            scene.registerTouchArea(upButton);

            // 左ボタン
            CanvasSprite leftButton = new CanvasSprite(getTextureManager(), 100f, 100f, 40f, 40f,
                    new TriangleButtonRenderer(TriangleButtonRenderer.DirectionType.LEFT), ColorF.WHITE, ColorF.RED,
                    getVertexBufferObjectManager()) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    onKeyTouchEvent(pSceneTouchEvent, "LEFT");
                    return true;
                }
            };
            scene.attachChild(leftButton);
            scene.registerTouchArea(leftButton);

            // 右ボタン
            CanvasSprite rightButton = new CanvasSprite(getTextureManager(), 220f, 100f, 40f, 40f,
                    new TriangleButtonRenderer(TriangleButtonRenderer.DirectionType.RIGHT), ColorF.WHITE, ColorF.RED,
                    getVertexBufferObjectManager()) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    onKeyTouchEvent(pSceneTouchEvent, "RIGHT");
                    return true;
                }
            };
            scene.attachChild(rightButton);
            scene.registerTouchArea(rightButton);

            // 下ボタン
            CanvasSprite downButton = new CanvasSprite(getTextureManager(), 160f, 40f, 40f, 40f,
                    new TriangleButtonRenderer(TriangleButtonRenderer.DirectionType.DOWN), ColorF.WHITE, ColorF.RED,
                    getVertexBufferObjectManager()) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    onKeyTouchEvent(pSceneTouchEvent, "DOWN");
                    return true;
                }
            };
            scene.attachChild(downButton);
            scene.registerTouchArea(downButton);
        }

        {
            // 座標
            mTextCoordinate = new Text(CAMERA_WIDTH / 4, CAMERA_HEIGHT - 120, mFont,
                    "(xxxx.xx, xxxx.xx)", // 表示しそうな最大桁数で書く。さもないとクラッシュする。
                    new TextOptions(HorizontalAlign.CENTER), getVertexBufferObjectManager());
            scene.attachChild(mTextCoordinate);
        }
        scene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                float x = mCanvasSprite.getX();
                float y = mCanvasSprite.getY();
                if (mPressedButtonType != null) {
                    switch (mPressedButtonType) {
                        case "UP":
                            y = Math.min(y + 1, mCursorRange.top);
                            break;
                        case "LEFT":
                            x = Math.max(x - 1, mCursorRange.left);
                            break;
                        case "RIGHT":
                            x = Math.min(x + 1, mCursorRange.right);
                            break;
                        case "DOWN":
                            y = Math.max(y - 1, mCursorRange.bottom);
                            break;
                    }
                }

                mCanvasSprite.setPosition(x, y);

                // Update coordinate
                mTextCoordinate.setText("(" + Integer.toString((int)(x)) + " ," + Integer.toString((int)(y)) + ")");
            }
            @Override
            public void reset() {
            }
        });

        scene.sortChildren();
        return scene;
    }

    private void onKeyTouchEvent(TouchEvent event, String type) {
        switch (event.getAction()) {
            case TouchEvent.ACTION_DOWN: // タッチされた
                mPressedButtonType = type;
                break;
            case TouchEvent.ACTION_MOVE: // タッチ中
                mPressedButtonType = type;
                break;
            case TouchEvent.ACTION_UP:
            case TouchEvent.ACTION_OUTSIDE:
            case TouchEvent.ACTION_CANCEL:
                mPressedButtonType = null;
                break;
        }
    }

    /**
     * 田の字を描画する。
     */
    private class PositionRenderer implements ICanvasPaint {
        /**
         * 描画を行う。
         *
         * @param canvas          キャンバスオブジェクト
         * @param paint           描画オブジェクト
         * @param foregroundColor 前景色
         * @param backgroundColor 背景色
         */
        @Override
        public void draw(Canvas canvas, Paint paint, int foregroundColor, int backgroundColor) {
            Rect rect = canvas.getClipBounds();

            paint.setAntiAlias(true);

            paint.setColor(backgroundColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(rect, paint);

            paint.setColor(foregroundColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3.0f);
            canvas.drawRect(rect, paint);
            // 中央縦線
            canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight(), paint);
            // 中央横線
            canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, paint);
        }
    }
}

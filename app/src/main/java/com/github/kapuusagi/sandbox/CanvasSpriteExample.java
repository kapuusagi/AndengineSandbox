package com.github.kapuusagi.sandbox;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.Toast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.parts.renderer.CloseButtonRenderer;
import org.andengine.entity.parts.renderer.SimpleFrameRenderer;
import org.andengine.entity.parts.renderer.TriangleButtonRenderer;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ICanvasPaint;
import org.andengine.entity.sprite.CanvasSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.ColorF;

public class CanvasSpriteExample extends SimpleBaseGameActivity {
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    @Override
    protected void onCreateResources() {

    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(0f,0.125f,0f));

        final float centerX = CAMERA_WIDTH / 2;
        final float centerY = CAMERA_HEIGHT / 2;
        {
            CanvasSprite frameSprite = new CanvasSprite(getTextureManager(), centerX, centerY,
                    CAMERA_WIDTH, CAMERA_HEIGHT,
                    new SimpleFrameRenderer(), ColorF.WHITE, ColorF.BLACK,
                    getVertexBufferObjectManager());
            scene.attachChild(frameSprite);
        }
        {
            CanvasSprite closeButton = new CanvasSprite(getTextureManager(),
                    CAMERA_WIDTH -  50, CAMERA_HEIGHT - 30, 40, 40,
                    new CloseButtonRenderer(CloseButtonRenderer.BorderType.RECTANGLE), ColorF.WHITE, ColorF.RED,
                    getVertexBufferObjectManager()) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    CanvasSpriteExample.this.finish();
                    return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
                }
            };
            scene.attachChild(closeButton);
            scene.registerTouchArea(closeButton);
        }

        {
            CanvasSprite upButton = new CanvasSprite(getTextureManager(),
                    20, 20, 80, 20,
                    new TriangleButtonRenderer(TriangleButtonRenderer.DirectionType.UP), ColorF.WHITE, ColorF.RED,
                    getVertexBufferObjectManager()) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    return true;
                }
            };
            scene.attachChild(upButton);
            scene.registerTouchArea(upButton);
        }

        {
            ICanvasPaint vectorPaint = new ICanvasPaint() {
                @Override
                public void draw(Canvas canvas, Paint paint, int foregroundColor, int backgroundColor) {
                    RectF rect = new RectF(0.0f, 0.0f, canvas.getWidth() -  1.0f, canvas.getHeight() - 1.0f);
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(backgroundColor);
                    canvas.drawRoundRect(rect, 5, 5, paint);

                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(foregroundColor);
                    canvas.drawRoundRect(rect, 5, 5, paint);
                }
            };

            CanvasSprite canvasSprite = new CanvasSprite(getTextureManager(), centerX, centerY,
                    100f, 100f,
                    vectorPaint, ColorF.WHITE, new ColorF(0.0f, 0.0f, 0.0f, 0.5f),
                    getVertexBufferObjectManager());
            SequenceEntityModifier sequenceEntityModifier = new SequenceEntityModifier(
                    new ColorModifier(5f, ColorF.WHITE, ColorF.GREEN),
                    new ColorModifier(5f, ColorF.GREEN, ColorF.BLUE),
                    new ColorModifier(5f, ColorF.BLUE, ColorF.RED),
                    new ColorModifier( 5f, ColorF.RED, ColorF.WHITE));
            canvasSprite.registerEntityModifier(new LoopEntityModifier(sequenceEntityModifier));
            canvasSprite.setBackgroundColor(0x80000000); // 半透明黒
            scene.attachChild(canvasSprite);
        }
        return scene;
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
}

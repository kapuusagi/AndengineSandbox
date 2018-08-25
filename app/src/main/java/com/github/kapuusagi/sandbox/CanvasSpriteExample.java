package com.github.kapuusagi.sandbox;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ICanvasPaint;
import org.andengine.entity.sprite.CanvasSprite;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class CanvasSpriteExample extends SimpleBaseGameActivity {
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    @Override
    protected void onCreateResources() {

    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(0f,1.0f,0f));

        ICanvasPaint vectorPaint = new ICanvasPaint() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(0xff0000ff);
                RectF rect = new RectF(0.0f, 0.0f, canvas.getWidth() -  1.0f, canvas.getHeight() - 1.0f);
                canvas.drawRoundRect(rect, 5, 5, paint);

            }
        };

        final float x = CAMERA_WIDTH / 2;
        final float y = CAMERA_HEIGHT / 2;

        CanvasSprite canvasSprite = new CanvasSprite(getTextureManager(), x, y, 100f, 100f,
                vectorPaint, getVertexBufferObjectManager());
        scene.attachChild(canvasSprite);
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

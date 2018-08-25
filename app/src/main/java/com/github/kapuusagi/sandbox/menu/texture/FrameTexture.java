package com.github.kapuusagi.sandbox.menu.texture;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;

public class FrameTexture extends BaseBitmapTextureAtlasSourceDecorator {

    // フレームカラー
    private int mFrameColor;
    // バックグラウンドカラー
    private int mBackgroundColor;

    /**
     * フレームテクスチャ
     *
     * @param bitmapTextureAtlasSource テクスチャを描画する対象。
     * @param frameColor フレーム色
     * @param backgroundColor バックグラウンド色
     */
    public FrameTexture(IBitmapTextureAtlasSource bitmapTextureAtlasSource, int frameColor, int backgroundColor) {
        super(bitmapTextureAtlasSource);
        mFrameColor = frameColor;
        mBackgroundColor = backgroundColor;
    }

    /**
     * 描画を行う。
     * @param canvas Canvasオブジェクト
     */
    @Override
    protected void onDecorateBitmap(Canvas canvas) {
        // 内部
        RectF rect = new RectF(3f, 3f, getTextureWidth() - 3.0f, getTextureHeight() - 3.0f);
        mPaint.setColor(mBackgroundColor);
        mPaint.setStrokeWidth(1.0f);
        mPaint.setAntiAlias(false);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rect, 5, 5, mPaint);

        // フレーム描画
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3.0f);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mFrameColor);
        canvas.drawRoundRect(rect, 5.0f, 5.0f, mPaint);
    }

    /**
     * 新しいリソースを確保して複製を作る。
     *
     * @return FrameTextureオブジェクトが返る。
     */
    @Override
    public FrameTexture deepCopy() {
        return new FrameTexture(super.mBitmapTextureAtlasSource.deepCopy(), mFrameColor, mBackgroundColor);
    }
}

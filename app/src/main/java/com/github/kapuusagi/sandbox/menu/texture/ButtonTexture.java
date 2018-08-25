package com.github.kapuusagi.sandbox.menu.texture;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;

public class ButtonTexture extends BaseBitmapTextureAtlasSourceDecorator {

    public enum ButtonType {
        /**
         * 上向き三角形
         */
        UP_TRIANGLE,
        /**
         * 下向き三角形
         */
        DOWN_TRIANGLE,
        /**
         * ×ボタン
         */
        CLOSE_BUTTON,
    }

    private int mFgColor;
    private int mBgColor;
    private ButtonType mButtonType;

    /**
     * コンストラクタ
     * @param bitmapTextureAtlasSource
     * @param fgColor
     * @param bgColor
     */
    public ButtonTexture(IBitmapTextureAtlasSource bitmapTextureAtlasSource, int fgColor, int bgColor, ButtonType type) {
        super(bitmapTextureAtlasSource);
        mFgColor = fgColor;
        mBgColor = bgColor;
        mButtonType = type;
    }

    /**
     * 描画を行う。
     * @param canvas Canvasオブジェクト
     */
    @Override
    protected void onDecorateBitmap(Canvas canvas) {
        switch (mButtonType) {
            case UP_TRIANGLE:
                drawUpTriangle(canvas);
                break;
            case DOWN_TRIANGLE:
                drawDownTriangle(canvas);
                break;
            case CLOSE_BUTTON:
                drawCloseButton(canvas);
        }
    }

    private void drawUpTriangle(Canvas c) {
        final int hHalf = getTextureWidth() / 2;
        final int vHalf = getTextureHeight() / 2;
        final int size = (hHalf > vHalf) ? vHalf : hHalf;
        final int inset = (size > 20) ? 5 : (int)(size * 0.2);

        final float centerX = hHalf;
        final float centerY = vHalf;

        Path path = new Path();
        path.moveTo(centerX, centerY - (size - inset)); // 頂点
        path.lineTo(centerX - (size - inset), centerY + (size - inset)); // 底辺
        path.lineTo(centerX + (size - inset), centerY + (size - inset)); // 底辺
        path.close();

        // background
        mPaint.setColor(mBgColor);
        mPaint.setStrokeWidth(1.0f);
        mPaint.setAntiAlias(false);
        mPaint.setStyle(Paint.Style.FILL);
        c.drawPath(path, mPaint);

        // foreground
        mPaint.setColor(mFgColor);
        mPaint.setStrokeWidth(3.0f);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        c.drawPath(path, mPaint);
    }

    private void drawDownTriangle(Canvas c) {
        final int hHalf = getTextureWidth() / 2;
        final int vHalf = getTextureHeight() / 2;
        final int size = (hHalf > vHalf) ? vHalf : hHalf;
        final int inset = (size > 20) ? 5 : (int)(size * 0.2);

        final float centerX = hHalf;
        final float centerY = vHalf;

        Path path = new Path();
        path.moveTo(centerX, centerY + (size - inset)); // 頂点
        path.lineTo(centerX - (size - inset), centerY - (size - inset)); // 底辺
        path.lineTo(centerX + (size - inset), centerY - (size - inset)); // 底辺
        path.close();

        // background
        mPaint.setColor(mBgColor);
        mPaint.setStrokeWidth(1.0f);
        mPaint.setAntiAlias(false);
        mPaint.setStyle(Paint.Style.FILL);
        c.drawPath(path, mPaint);

        // foreground
        mPaint.setColor(mFgColor);
        mPaint.setStrokeWidth(3.0f);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        c.drawPath(path, mPaint);
    }

    private void drawCloseButton(Canvas c) {
        final int hHalf = getTextureWidth() / 2;
        final int vHalf = getTextureHeight() / 2;
        final int size = (hHalf > vHalf) ? vHalf : hHalf;
        final int inset = (size > 20) ? 5 : (int)(size * 0.2);

        final int centerX = hHalf;
        final int centerY = vHalf;

        // 背景
        mPaint.setColor(mBgColor);
        mPaint.setStrokeWidth(1.0f);
        mPaint.setAntiAlias(false);
        mPaint.setStyle(Paint.Style.FILL);
        c.drawCircle(centerX, centerY, size - inset, mPaint);

        // 外枠
        mPaint.setColor(mFgColor);
        mPaint.setStrokeWidth(3.0f);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        c.drawCircle(centerX, centerY, size - inset, mPaint);

        // 中文字
        final int interSize = size / 3;
        c.drawLine(centerX - interSize, centerY - interSize,
                centerX + interSize, centerY + interSize, mPaint);
        c.drawLine(centerX + interSize, centerY - interSize,
                centerX - interSize, centerY + interSize, mPaint);
    }

    /**
     * 新しいリソースを確保して複製を作る。
     *
     * @return FrameTextureオブジェクトが返る。
     */
    @Override
    public ButtonTexture deepCopy() {
        return new ButtonTexture(super.mBitmapTextureAtlasSource.deepCopy(), mFgColor, mBgColor, mButtonType);
    }
}

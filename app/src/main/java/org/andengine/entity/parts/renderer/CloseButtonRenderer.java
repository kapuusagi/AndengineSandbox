package org.andengine.entity.parts.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import org.andengine.entity.sprite.ICanvasPaint;

/**
 * バッテンが付いたボタンを描画する。
 */
public class CloseButtonRenderer implements ICanvasPaint {

    public enum BorderType {
        /**
         * 円
          */
        CIRCLE,
        /**
         * 四角形
         */
        RECTANGLE,
    }

    // ボーダー種別
    private BorderType mBorderType;

    public CloseButtonRenderer(BorderType borderType) {
        this.mBorderType = borderType;
    }

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
        final int centerX = canvas.getWidth() / 2;
        final int centerY = canvas.getHeight() / 2;
        final int size = Math.min(centerX, centerY);
        final int inset = (size > 20) ? 5 : (int)(size * 0.2);

        paint.setAntiAlias(true);
        switch (mBorderType) {
            case CIRCLE:
            {
                // 背景
                paint.setColor(backgroundColor);
                paint.setStrokeWidth(1.0f);
                paint.setAntiAlias(false);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(centerX, centerY, size - inset, paint);
                // 外枠
                paint.setColor(foregroundColor);
                paint.setStrokeWidth(3.0f);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(centerX, centerY, size - inset, paint);
                break;
            }

            case RECTANGLE:
            {
                RectF rectF = new RectF(centerX - (size - inset), centerY - (size - inset),
                        centerX + (size - inset), centerY + (size - inset));
                // 背景
                paint.setColor(backgroundColor);
                paint.setStrokeWidth(1.0f);
                paint.setAntiAlias(false);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(rectF, 2.0f, 2.0f, paint);
                // 外枠
                paint.setColor(foregroundColor);
                paint.setStrokeWidth(3.0f);
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRoundRect(rectF, 2.0f, 2.0f, paint);
                break;
            }
        }

        // 中文字
        final int interSize = size / 3;
        canvas.drawLine(centerX - interSize, centerY - interSize,
                centerX + interSize, centerY + interSize, paint);
        canvas.drawLine(centerX + interSize, centerY - interSize,
                centerX - interSize, centerY + interSize, paint);
    }
}

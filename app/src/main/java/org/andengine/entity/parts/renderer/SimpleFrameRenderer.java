package org.andengine.entity.parts.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import org.andengine.entity.sprite.ICanvasPaint;

/**
 * フレーム(枠)を描画するためのクラス。
 *
 * よくRPGで見られる、角がちょっとだけ曲がったシンプルな枠。
 */
public class SimpleFrameRenderer implements ICanvasPaint {

    /**
     * SimpleFrameRenderオブジェクトを構築する。
     */
    public SimpleFrameRenderer() {
    }


    /**
     * 描画を行う。
     *
     * @param canvas キャンバスオブジェクト
     * @param paint  描画オブジェクト
     * @param foregroundColor 枠の色
     * @param backgroundColor 背景色
     */
    @Override
    public void draw(Canvas canvas, Paint paint, int foregroundColor, int backgroundColor) {
        // 内部
        RectF rect = new RectF(3f, 3f, canvas.getWidth() - 3.0f, canvas.getHeight() - 3.0f);
        paint.setColor(backgroundColor);
        paint.setStrokeWidth(1.0f);
        paint.setAntiAlias(false);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rect, 5, 5, paint);

        // フレーム描画
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3.0f);
        paint.setAntiAlias(true);
        paint.setColor(foregroundColor);
        canvas.drawRoundRect(rect, 5.0f, 5.0f, paint);
    }
}

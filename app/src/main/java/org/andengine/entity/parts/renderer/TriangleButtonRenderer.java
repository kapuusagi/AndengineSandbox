package org.andengine.entity.parts.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import org.andengine.entity.sprite.ICanvasPaint;

/**
 * 三角ボタンをレンダリングする。
 */
public class TriangleButtonRenderer implements ICanvasPaint {
    public enum DirectionType {
        /**
         * 上方向を向いた三角形
         */
        UP,
        /**
         * 下方向を向いた三角形
         */
        DOWN,
        /**
         * 左方向を向いた三角形
         */
        LEFT,
        /**
         * 右方向を向いた三角形
         */
        RIGHT,
    }

    // 方向
    private DirectionType mDirectionType;


    /**
     * 指定した方向を向く三角形描画オブジェクトを構築する。
     *
     * @param directionType 方向
     */
    public TriangleButtonRenderer(DirectionType directionType) {
        mDirectionType = directionType;
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

        Path path = new Path();
        switch (mDirectionType) {
            case UP:
                path.moveTo(centerX, centerY - (size - inset)); // 頂点
                path.lineTo(centerX - (size - inset), centerY + (size - inset)); // 左下
                path.lineTo(centerX + (size - inset), centerY + (size - inset)); // 右下
                path.close();
                break;
            case DOWN:
                path.moveTo(centerX, centerY + (size - inset)); // 頂点
                path.lineTo(centerX - (size - inset), centerY - (size - inset)); // 左上
                path.lineTo(centerX + (size - inset), centerY - (size - inset)); // 右上
                path.close();
                break;
            case LEFT:
                path.moveTo(centerX - (size - inset), centerY); // 頂点
                path.lineTo(centerX + (size - inset), centerY - (size - inset)); // 右上
                path.lineTo(centerX + (size - inset), centerY + (size - inset)); // 右下
                path.close();
                break;
            case RIGHT:
                path.moveTo(centerX + (size - inset), centerY); // 頂点
                path.lineTo(centerX - (size - inset), centerY - (size - inset)); // 左上
                path.lineTo(centerX - (size - inset), centerY + (size - inset)); // 左下
                path.close();
                break;
        }
        paint.setAntiAlias(true);

        // Draw background.
        paint.setColor(backgroundColor);
        paint.setStrokeWidth(1.0f);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

        // Draw foreground.
        paint.setColor(foregroundColor);
        paint.setStrokeWidth(3.0f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
    }
}

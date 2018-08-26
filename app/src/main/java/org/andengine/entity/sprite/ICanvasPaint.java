package org.andengine.entity.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * ベクタ画像を描画するインタフェース。
 */
public interface ICanvasPaint {
    /**
     * 描画を行う。
     * @param canvas キャンバスオブジェクト
     * @param paint 描画オブジェクト
     * @param foregroundColor 前景色
     * @param backgroundColor 背景色
     */
    void draw(Canvas canvas, Paint paint, int foregroundColor, int backgroundColor);
}

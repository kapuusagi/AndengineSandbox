package org.andengine.entity.sprite.vbo;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.IVertexBufferObject;

/**
 * (c) 2012 Zynga Inc.
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 18:40:47 - 28.03.2012
 */
public interface ISpriteVertexBufferObject extends IVertexBufferObject {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * 色が変更されたときの通知を受け取る
	 * @param pSprite スプライトオブジェクト
	 */
	void onUpdateColor(final ISprite pSprite);

	/**
	 * バーテックスが変更されたときの通知を受け取る。
	 * @param pSprite スプライトオブジェクト
	 */
	void onUpdateVertices(final ISprite pSprite);

	/**
	 * テクスチャ座標が変更されたときの通知を受け取る。
	 * @param pSprite スプライトオブジェクト
	 */
	void onUpdateTextureCoordinates(final ISprite pSprite);
}
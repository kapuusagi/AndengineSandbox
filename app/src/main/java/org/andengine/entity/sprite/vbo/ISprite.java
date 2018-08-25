package org.andengine.entity.sprite.vbo;

import org.andengine.entity.shape.IShape;
import org.andengine.opengl.texture.region.ITextureRegion;

public interface ISprite extends IShape {
    /**
     * 頂点数
     */
    int VERTEX_SIZE = 2 + 1 + 2;

    /**
     *
     */
    int VERTEX_INDEX_X = 0;
    /**
     *
     */
    int VERTEX_INDEX_Y = ISprite.VERTEX_INDEX_X + 1;
    /**
     *
     */
    int COLOR_INDEX = ISprite.VERTEX_INDEX_Y + 1;
    /**
     *
     */
    int TEXTURE_COORDINATES_INDEX_U = ISprite.COLOR_INDEX + 1;
    /**
     *
     */
    int TEXTURE_COORDINATES_INDEX_V = ISprite.TEXTURE_COORDINATES_INDEX_U + 1;

    /**
     *
     */
    int VERTICES_PER_SPRITE = 4;
    /**
     *
     */
    int SPRITE_SIZE = ISprite.VERTEX_SIZE * ISprite.VERTICES_PER_SPRITE;

    /**
     * スプライトに貼り付けるテクスチャを取得する。
     * @return テクスチャ
     */
    ITextureRegion getTextureRegion();

    /**
     * テクスチャを垂直反転させるかどうかを取得する。
     * @return 垂直反転させる場合にはtrue。
     */
    boolean isFlippedVertical();

    /**
     * テクスチャを水平反転させるかどうかを取得する。
     * @return 水平反転させる場合にはtrue。
     */
    boolean isFlippedHorizontal();
}

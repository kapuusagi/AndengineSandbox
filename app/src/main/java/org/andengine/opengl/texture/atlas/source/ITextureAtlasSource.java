package org.andengine.opengl.texture.atlas.source;


/**
 * ITextureAtlasSourceはテクスチャの基本情報を定義するためのインタフェース。
 * テクスチャの起点(x,y)とサイズ(width, height)を設定・取得するためのインタフェースを持つ。
 *
 * OpenGLとのやりとりをするためのテクスチャインタフェースは
 * ITextureで提供されており、うまく分離して考えられている。
 *
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 11:46:56 - 12.07.2011
 */
public interface ITextureAtlasSource {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

    /**
     * テクスチャの起点 x座標を取得する。
     * @return X座標
     */
	int getTextureX();

    /**
     * テクスチャの起点 y座標を取得する。
     * @return Y座標
     */
	int getTextureY();

    /**
     * テクスチャのx座標を設定する。
     * @param pTextureX x座標
     */
	void setTextureX(final int pTextureX);

    /**
     * テクスチャのy座標を設定する。
     * @param pTextureY y座標
     */
	void setTextureY(final int pTextureY);

    /**
     * テクスチャの幅を取得する。
     * @return テクスチャの幅
     */
	int getTextureWidth();

    /**
     * テクスチャの高さを取得する。
     * @return テクスチャの高さ
     */
	int getTextureHeight();

    /**
     * テクスチャの幅を設定する。
     * @param pTextureWidth 幅
     */
	void setTextureWidth(final int pTextureWidth);

    /**
     * テクスチャの高さを設定する。
     * @param pTextureHeight 高さ
     */
	void setTextureHeight(final int pTextureHeight);

    /**
     * このオブジェクトの複製を作る。
     * 複製したオブジェクトを変更しても、オリジナルには全く影響がない。
     * @return ITextureAtlasSourceオブジェクト
     */
	ITextureAtlasSource deepCopy();
}
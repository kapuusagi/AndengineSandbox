package org.andengine.opengl.texture.atlas.bitmap.source;


import org.andengine.opengl.texture.atlas.source.BaseTextureAtlasSource;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

/**
 * ビットマップテクスチャの基本的な実装を行ったクラス。
 * onLoadBitmap()では何も描画されていない、空のBitmapオブジェクトを構築して返す。
 *
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 20:20:36 - 08.08.2010
 */
public class EmptyBitmapTextureAtlasSource extends BaseTextureAtlasSource implements IBitmapTextureAtlasSource {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

    /**
     * 幅と高さを指定してオブジェクトを構築する。
     * xとyは0で初期化される。
     * @param pTextureWidth 幅
     * @param pTextureHeight 高さ
     */
	public EmptyBitmapTextureAtlasSource(final int pTextureWidth, final int pTextureHeight) {
		this(0, 0, pTextureWidth, pTextureHeight);
	}

    /**
     * 位置および幅と高さを指定してオブジェクトを構築する。
     * @param pTextureX x位置
     * @param pTextureY y位置
     * @param pTextureWidth 幅
     * @param pTextureHeight 高さ
     */
	public EmptyBitmapTextureAtlasSource(final int pTextureX, final int pTextureY, final int pTextureWidth, final int pTextureHeight) {
		super(pTextureX, pTextureY, pTextureWidth, pTextureHeight);
	}

	@Override
	public EmptyBitmapTextureAtlasSource deepCopy() {
		return new EmptyBitmapTextureAtlasSource(this.mTextureX, this.mTextureY, this.mTextureWidth, this.mTextureHeight);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Bitmap onLoadBitmap(final Config pBitmapConfig) {
		return this.onLoadBitmap(pBitmapConfig, false);
	}

	@Override
	public Bitmap onLoadBitmap(final Config pBitmapConfig, final boolean pMutable) {
		return Bitmap.createBitmap(this.mTextureWidth, this.mTextureHeight, pBitmapConfig);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + this.mTextureWidth + " x " + this.mTextureHeight + ")";
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
package org.andengine.opengl.texture.atlas.bitmap.source;

import org.andengine.opengl.texture.atlas.source.ITextureAtlasSource;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

/**
 * ITextureAtlasSourceに加えて、ビットマップテクスチャとして必要なインタフェースを追加で定義したもの。
 * 具体的にはビットマップデータを取得する為のonLoadBitmapが追加されている。
 *
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 12:08:52 - 09.03.2010
 */
public interface IBitmapTextureAtlasSource extends ITextureAtlasSource {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * このオブジェクトの複製を作る。
	 * 複製したオブジェクトを変更しても、オリジナルには全く影響がない。
	 * @return ITextureAtlasSourceオブジェクト
	 */
	@Override
	IBitmapTextureAtlasSource deepCopy();

	/**
	 * テクスチャのイメージデータを変更不可なものとして取得する。
	 * このメソッドが返すBitmapデータに修正を加えたいならば、onLoadBitmap(Config, true)を使うこと。
	 *
	 * @param pBitmapConfig ビットマップ設定
	 * @return Bitmapオブジェクトが返る。このオブジェクトは変更できない。
	 */
	Bitmap onLoadBitmap(final Config pBitmapConfig);

	/**
	 * テクスチャのイメージデータを取得する。
	 *
	 * @param pBitmapConfig ビットマップ設定
	 * @param pMutable 変更可能なものとして取得するかどうか。変更可能なものとして取得する場合にはtrue
	 * @return Bitmapオブジェクトが返る。
	 */
	Bitmap onLoadBitmap(final Config pBitmapConfig, final boolean pMutable);
}
package org.andengine.opengl.texture.atlas.bitmap.source.decorator;

import org.andengine.opengl.texture.atlas.bitmap.source.BaseBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.util.debug.Debug;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * ビットマップテクスチャに独自の描画を行ってテクスチャとして扱うためのクラス。
 * IBitmapTextureAtlasSourceを実装したクラス
 * 使う際には本クラスを派生させ、onDecorateBitmap(Canvas)を実装する。
 *
 * org.andengine.opengl.texture.atlas.bitmap.sourceの下にある各クラスが使える。
 * 空のキャンバスに描画したい場合にはEmptyBitmapTextureAtlasSourceを使う。
 * 既存のリソースを加工したい場合にはAssetBitmapTextureAtlasSource等を使う。
 *
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 16:43:29 - 06.08.2010
 */
public abstract class BaseBitmapTextureAtlasSourceDecorator extends BaseBitmapTextureAtlasSource {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
    /**
     * 描画オプション
     */
	protected TextureAtlasSourceDecoratorOptions mTextureAtlasSourceDecoratorOptions;
	/**
     * 描画に使用するPaintオブジェクト。
     */
	protected Paint mPaint = new Paint();

	// ===========================================================
	// Constructors
	// ===========================================================

	public BaseBitmapTextureAtlasSourceDecorator(final IBitmapTextureAtlasSource pBitmapTextureAtlasSource) {
		this(pBitmapTextureAtlasSource, new TextureAtlasSourceDecoratorOptions());
	}

	public BaseBitmapTextureAtlasSourceDecorator(final IBitmapTextureAtlasSource pBitmapTextureAtlasSource,
                                                 final TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
		super(pBitmapTextureAtlasSource);

		this.mTextureAtlasSourceDecoratorOptions = (pTextureAtlasSourceDecoratorOptions == null) ? new TextureAtlasSourceDecoratorOptions() : pTextureAtlasSourceDecoratorOptions;
		this.mPaint.setAntiAlias(this.mTextureAtlasSourceDecoratorOptions.getAntiAliasing());
	}

	@Override
	public abstract BaseBitmapTextureAtlasSourceDecorator deepCopy();

	// ===========================================================
	// Getter & Setter
	// ===========================================================

    /**
     * 描画に使用するPaintオブジェクトを取得する。
     * @return Paintオブジェクト
     */
	public Paint getPaint() {
		return this.mPaint;
	}

    /**
     * 描画に使用するPaintオブジェクトを設定する。
     * @param pPaint Paintオブジェクト
     */
	public void setPaint(final Paint pPaint) {
		this.mPaint = pPaint;
	}

    /**
     * 描画オプションを取得する。
     * @return 描画オプション
     */
	public TextureAtlasSourceDecoratorOptions getTextureAtlasSourceDecoratorOptions() {
		return this.mTextureAtlasSourceDecoratorOptions;
	}

    /**
     * 描画オプションを設定する。
     * @param pTextureAtlasSourceDecoratorOptions 描画オプション
     */
	public void setTextureAtlasSourceDecoratorOptions(final TextureAtlasSourceDecoratorOptions pTextureAtlasSourceDecoratorOptions) {
		this.mTextureAtlasSourceDecoratorOptions = pTextureAtlasSourceDecoratorOptions;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

    /**
     * 対象のビットマップに描画を行う。
     *
     * @param pCanvas キャンバス
     * @throws Exception エラーが発生した場合
     */
	protected abstract void onDecorateBitmap(final Canvas pCanvas) throws Exception;

	@Override
	public Bitmap onLoadBitmap(final Config pBitmapConfig) {
	    return this.onLoadBitmap(pBitmapConfig, false);
	}

	@Override
    public Bitmap onLoadBitmap(final Config pBitmapConfig, boolean pMutable) {
	    // super.onLoadBitmap(Config, boolean)はBaseBitmapTextureAtlasSoruceが保持している
        // IBitmapTextureAtlasSourceからBitmapデータを作成する。
	    final Bitmap bitmap = super.onLoadBitmap(pBitmapConfig, true);

	    final Canvas canvas = new Canvas(bitmap);
	    try {
            this.onDecorateBitmap(canvas);
        }
        catch (final Exception e) {
	        Debug.e(e);
        }
        return bitmap;
    }


	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static class TextureAtlasSourceDecoratorOptions {
		// ===========================================================
		// Constants
		// ===========================================================

		public static final TextureAtlasSourceDecoratorOptions DEFAULT = new TextureAtlasSourceDecoratorOptions();

		// ===========================================================
		// Fields
		// ===========================================================

		private float mInsetLeft = 0.25f;
		private float mInsetRight = 0.25f;
		private float mInsetTop = 0.25f;
		private float mInsetBottom = 0.25f;

		private boolean mAntiAliasing;

		// ===========================================================
		// Constructors
		// ===========================================================

        /**
         * 複製を作成する。
         * @return 複製
         */
		protected TextureAtlasSourceDecoratorOptions deepCopy() {
			final TextureAtlasSourceDecoratorOptions textureSourceDecoratorOptions = new TextureAtlasSourceDecoratorOptions();
			textureSourceDecoratorOptions.setInsets(this.mInsetLeft, this.mInsetTop, this.mInsetRight, this.mInsetBottom);
			textureSourceDecoratorOptions.setAntiAliasing(this.mAntiAliasing);
			return textureSourceDecoratorOptions;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

        /**
         * アンチエイリアスを有効にするかどうかを取得する。
         * @return アンチエイリアスを有効にする場合にはtrue
         */
		public boolean getAntiAliasing() {
			return this.mAntiAliasing;
		}

        /**
         * 左側のインセットを取得する。
         * @return インセット
         */
		public float getInsetLeft() {
			return this.mInsetLeft;
		}

        /**
         * 右側のインセットを取得する。
         * @return インセット。
         */
		public float getInsetRight() {
			return this.mInsetRight;
		}

        /**
         * 上側のインセットを取得する。
         * @return インセット
         */
		public float getInsetTop() {
			return this.mInsetTop;
		}

        /**
         * 下側のインセットを取得する。
         * @return インセット
         */
		public float getInsetBottom() {
			return this.mInsetBottom;
		}

        /**
         * アンチエイリアスを設定する。
         * @param pAntiAliasing アンチエイリアスを有効にする場合にはtrue
         * @return trueが返る。
         */
		public TextureAtlasSourceDecoratorOptions setAntiAliasing(final boolean pAntiAliasing) {
			this.mAntiAliasing = pAntiAliasing;
			return this;
		}

        /**
         * 左側のインセットを設定する。
         * @param pInsetLeft インセット
         * @return trueが返る。
         */
		public TextureAtlasSourceDecoratorOptions setInsetLeft(final float pInsetLeft) {
			this.mInsetLeft = pInsetLeft;
			return this;
		}

        /**
         * 右側のインセットを設定する。
         * @param pInsetRight インセット
         * @return trueが返る。
         */
		public TextureAtlasSourceDecoratorOptions setInsetRight(final float pInsetRight) {
			this.mInsetRight = pInsetRight;
			return this;
		}

        /**
         * 上側のインセットを設定する。
         * @param pInsetTop インセット
         * @return trueが返る。
         */
		public TextureAtlasSourceDecoratorOptions setInsetTop(final float pInsetTop) {
			this.mInsetTop = pInsetTop;
			return this;
		}

        /**
         * 下側のインセットを設定する。
         * @param pInsetBottom インセット
         * @return trueが返る。
         */
		public TextureAtlasSourceDecoratorOptions setInsetBottom(final float pInsetBottom) {
			this.mInsetBottom = pInsetBottom;
			return this;
		}

        /**
         * 上下左右に同じインセットを設定する。
         * @param pInsets インセット
         * @return 描画オプションが返る。
         */
		public TextureAtlasSourceDecoratorOptions setInsets(final float pInsets) {
			return this.setInsets(pInsets, pInsets, pInsets, pInsets);
		}

        /**
         * インセットを設定する。
         * @param pInsetLeft 左側のインセット
         * @param pInsetTop 上側のインセット
         * @param pInsetRight 右側のインセット
         * @param pInsetBottom 下側のインセット
         * @return 描画オプションが返る。
         */
		public TextureAtlasSourceDecoratorOptions setInsets(final float pInsetLeft, final float pInsetTop, final float pInsetRight, final float pInsetBottom) {
			this.mInsetLeft = pInsetLeft;
			this.mInsetTop = pInsetTop;
			this.mInsetRight = pInsetRight;
			this.mInsetBottom = pInsetBottom;
			return this;
		}

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}

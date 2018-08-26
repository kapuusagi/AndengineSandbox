package org.andengine.util.adt.color;


/**
 * Andengine用のカラークラス。
 * 色データは0.0～1.0の範囲に正規化されたものを扱う。
 * 古いAPIではAndroid標準のColorクラスが微妙であったため、専用のクラスを定義したようだ。
 *
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 02:23:08 - 12.08.2011
 */
public class ColorF {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int ABGR_PACKED_RED_SHIFT = 0;
	public static final int ABGR_PACKED_GREEN_SHIFT = 8;
	public static final int ABGR_PACKED_BLUE_SHIFT = 16;
	public static final int ABGR_PACKED_ALPHA_SHIFT = 24;

	public static final int ABGR_PACKED_RED_CLEAR = 0XFFFFFF00;
	public static final int ABGR_PACKED_GREEN_CLEAR = 0XFFFF00FF;
	public static final int ABGR_PACKED_BLUE_CLEAR = 0XFF00FFFF;
	public static final int ABGR_PACKED_ALPHA_CLEAR = 0X00FFFFFF;

	public static final int ARGB_PACKED_BLUE_SHIFT = 0;
	public static final int ARGB_PACKED_GREEN_SHIFT = 8;
	public static final int ARGB_PACKED_RED_SHIFT = 16;
	public static final int ARGB_PACKED_ALPHA_SHIFT = 24;

	public static final int ARGB_PACKED_BLUE_CLEAR = 0XFFFFFF00;
	public static final int ARGB_PACKED_GREEN_CLEAR = 0XFFFF00FF;
	public static final int ARGB_PACKED_RED_CLEAR = 0XFF00FFFF;
	public static final int ARGB_PACKED_ALPHA_CLEAR = 0X00FFFFFF;

	public static final ColorF WHITE = new ColorF(1, 1, 1, 1);
	public static final ColorF BLACK = new ColorF(0, 0, 0, 1);
	public static final ColorF RED = new ColorF(1, 0, 0, 1);
	public static final ColorF YELLOW = new ColorF(1, 1, 0, 1);
	public static final ColorF GREEN = new ColorF(0, 1, 0, 1);
	public static final ColorF CYAN = new ColorF(0, 1, 1, 1);
	public static final ColorF BLUE = new ColorF(0, 0, 1, 1);
	public static final ColorF PINK = new ColorF(1, 0, 1, 1);
	public static final ColorF TRANSPARENT = new ColorF(0, 0, 0, 0);

	public static final int WHITE_ABGR_PACKED_INT = ColorF.WHITE.getABGRPackedInt();
	public static final int BLACK_ABGR_PACKED_INT = ColorF.BLACK.getABGRPackedInt();
	public static final int RED_ABGR_PACKED_INT = ColorF.RED.getABGRPackedInt();
	public static final int YELLOW_ABGR_PACKED_INT = ColorF.YELLOW.getABGRPackedInt();
	public static final int GREEN_ABGR_PACKED_INT = ColorF.GREEN.getABGRPackedInt();
	public static final int CYAN_ABGR_PACKED_INT = ColorF.CYAN.getABGRPackedInt();
	public static final int BLUE_ABGR_PACKED_INT = ColorF.BLUE.getABGRPackedInt();
	public static final int PINK_ABGR_PACKED_INT = ColorF.PINK.getABGRPackedInt();
	public static final int TRANSPARENT_ABGR_PACKED_INT = ColorF.TRANSPARENT.getABGRPackedInt();

	public static final float WHITE_ABGR_PACKED_FLOAT = ColorF.WHITE.getABGRPackedFloat();
	public static final float BLACK_ABGR_PACKED_FLOAT = ColorF.BLACK.getABGRPackedFloat();
	public static final float RED_ABGR_PACKED_FLOAT = ColorF.RED.getABGRPackedFloat();
	public static final float YELLOW_ABGR_PACKED_FLOAT = ColorF.YELLOW.getABGRPackedFloat();
	public static final float GREEN_ABGR_PACKED_FLOAT = ColorF.GREEN.getABGRPackedFloat();
	public static final float CYAN_ABGR_PACKED_FLOAT = ColorF.CYAN.getABGRPackedFloat();
	public static final float BLUE_ABGR_PACKED_FLOAT = ColorF.BLUE.getABGRPackedFloat();
	public static final float PINK_ABGR_PACKED_FLOAT = ColorF.PINK.getABGRPackedFloat();
	public static final float TRANSPARENT_ABGR_PACKED_FLOAT = ColorF.TRANSPARENT.getABGRPackedFloat();

	public static final int WHITE_ARGB_PACKED_INT = ColorF.WHITE.getARGBPackedInt();
	public static final int BLACK_ARGB_PACKED_INT = ColorF.BLACK.getARGBPackedInt();
	public static final int RED_ARGB_PACKED_INT = ColorF.RED.getARGBPackedInt();
	public static final int YELLOW_ARGB_PACKED_INT = ColorF.YELLOW.getARGBPackedInt();
	public static final int GREEN_ARGB_PACKED_INT = ColorF.GREEN.getARGBPackedInt();
	public static final int CYAN_ARGB_PACKED_INT = ColorF.CYAN.getARGBPackedInt();
	public static final int BLUE_ARGB_PACKED_INT = ColorF.BLUE.getARGBPackedInt();
	public static final int PINK_ARGB_PACKED_INT = ColorF.PINK.getARGBPackedInt();
	public static final int TRANSPARENT_ARGB_PACKED_INT = ColorF.TRANSPARENT.getARGBPackedInt();

	// ===========================================================
	// Fields
	// ===========================================================

	private float mRed;
	private float mGreen;
	private float mBlue;
	private float mAlpha;

	private int mABGRPackedInt;
	private float mABGRPackedFloat;

	// ===========================================================
	// Constructors
	// ===========================================================

	/* package */ ColorF() {

	}

	/**
	 * 指定した色でColorFオブジェクトを構築する。
	 * @param pColorF
	 */
	public ColorF(final ColorF pColorF) {
		this.set(pColorF);
	}

	/**
	 *
	 * @param pRed
	 * @param pGreen
	 * @param pBlue
	 */
	public ColorF(final float pRed, final float pGreen, final float pBlue) {
		this(pRed, pGreen, pBlue, 1);
	}

	public ColorF(final float pRed, final float pGreen, final float pBlue, final float pAlpha) {
		this.set(pRed, pGreen, pBlue, pAlpha);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public final float getRed() {
		return this.mRed;
	}

	public final float getGreen() {
		return this.mGreen;
	}

	public final float getBlue() {
		return this.mBlue;
	}

	public final float getAlpha() {
		return this.mAlpha;
	}

	public final void setRed(final float pRed) {
		this.mRed = pRed;

		this.packABGRRed();
	}

	public final boolean setRedChecking(final float pRed) {
		if (this.mRed != pRed) {
			this.mRed = pRed;

			this.packABGRRed();
			return true;
		}
		return false;
	}

	public final void setGreen(final float pGreen) {
		this.mGreen = pGreen;

		this.packABGRGreen();
	}

	public final boolean setGreenChecking(final float pGreen) {
		if (this.mGreen != pGreen) {
			this.mGreen = pGreen;

			this.packABGRGreen();
			return true;
		}
		return false;
	}

	public final void setBlue(final float pBlue) {
		this.mBlue = pBlue;

		this.packABGRBlue();
	}

	public final boolean setBlueChecking(final float pBlue) {
		if (this.mBlue != pBlue) {
			this.mBlue = pBlue;

			this.packABGRBlue();
			return true;
		}
		return false;
	}

	public final void setAlpha(final float pAlpha) {
		this.mAlpha = pAlpha;

		this.packABGRAlpha();
	}

	public final boolean setAlphaChecking(final float pAlpha) {
		if (this.mAlpha != pAlpha) {
			this.mAlpha = pAlpha;

			this.packABGRAlpha();
			return true;
		}
		return false;
	}

	public final void set(final float pRed, final float pGreen, final float pBlue) {
		this.mRed = pRed;
		this.mGreen = pGreen;
		this.mBlue = pBlue;

		this.packABGR();
	}

	public final boolean setChecking(final float pRed, final float pGreen, final float pBlue) {
		if ((this.mRed != pRed) || (this.mGreen != pGreen) || (this.mBlue != pBlue)) {
			this.mRed = pRed;
			this.mGreen = pGreen;
			this.mBlue = pBlue;

			this.packABGR();
			return true;
		}
		return false;
	}

	public final void set(final float pRed, final float pGreen, final float pBlue, final float pAlpha) {
		this.mRed = pRed;
		this.mGreen = pGreen;
		this.mBlue = pBlue;
		this.mAlpha = pAlpha;

		this.packABGR();
	}

	public final boolean setChecking(final float pRed, final float pGreen, final float pBlue, final float pAlpha) {
		if ((this.mAlpha != pAlpha) || (this.mRed != pRed) || (this.mGreen != pGreen) || (this.mBlue != pBlue)) {
			this.mRed = pRed;
			this.mGreen = pGreen;
			this.mBlue = pBlue;
			this.mAlpha = pAlpha;

			this.packABGR();
			return true;
		}
		return false;
	}

	public final void set(final ColorF pColorF) {
		this.mRed = pColorF.mRed;
		this.mGreen = pColorF.mGreen;
		this.mBlue = pColorF.mBlue;
		this.mAlpha = pColorF.mAlpha;

		this.mABGRPackedInt = pColorF.mABGRPackedInt;
		this.mABGRPackedFloat = pColorF.mABGRPackedFloat;
	}

	public final boolean setChecking(final ColorF pColorF) {
		if (this.mABGRPackedInt != pColorF.mABGRPackedInt) {
			this.mRed = pColorF.mRed;
			this.mGreen = pColorF.mGreen;
			this.mBlue = pColorF.mBlue;
			this.mAlpha = pColorF.mAlpha;

			this.mABGRPackedInt = pColorF.mABGRPackedInt;
			this.mABGRPackedFloat = pColorF.mABGRPackedFloat;
			return true;
		}
		return false;
	}

	public final int getABGRPackedInt() {
		return this.mABGRPackedInt;
	}

	public final float getABGRPackedFloat() {
		return this.mABGRPackedFloat;
	}

	/**
	 * @return the same format as {@link android.graphics.Color}.
	 */
	public final int getARGBPackedInt() {
		return ColorUtils.convertRGBAToARGBPackedInt(this.mRed, this.mGreen, this.mBlue, this.mAlpha);
	}

	public final void reset() {
		this.set(ColorF.WHITE);
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public int hashCode() {
		return this.mABGRPackedInt;
	}

	@Override
	public boolean equals(final Object pObject) {
		if (this == pObject) {
			return true;
		} else if (pObject == null) {
			return false;
		} else if (this.getClass() != pObject.getClass()) {
			return false;
		}

		return this.equals((ColorF) pObject);
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append("[Red: ")
			.append(this.mRed)
			.append(", Green: ")
			.append(this.mGreen)
			.append(", Blue: ")
			.append(this.mBlue)
			.append(", Alpha: ")
			.append(this.mAlpha)
			.append(']')
			.toString();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public boolean equals(final ColorF pColorF) {
		return this.mABGRPackedInt == pColorF.mABGRPackedInt;
	}

	private final void packABGRRed() {
		this.mABGRPackedInt = (this.mABGRPackedInt & ColorF.ABGR_PACKED_RED_CLEAR) | ((int)(255 * this.mRed) << ColorF.ABGR_PACKED_RED_CLEAR);
		this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
	}

	private final void packABGRGreen() {
		this.mABGRPackedInt = (this.mABGRPackedInt & ColorF.ABGR_PACKED_GREEN_CLEAR) | ((int)(255 * this.mGreen) << ColorF.ABGR_PACKED_GREEN_CLEAR);
		this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
	}

	private final void packABGRBlue() {
		this.mABGRPackedInt = (this.mABGRPackedInt & ColorF.ABGR_PACKED_BLUE_CLEAR) | ((int)(255 * this.mBlue) << ColorF.ABGR_PACKED_BLUE_CLEAR);
		this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
	}

	private final void packABGRAlpha() {
		this.mABGRPackedInt = (this.mABGRPackedInt & ColorF.ABGR_PACKED_ALPHA_CLEAR) | ((int)(255 * this.mAlpha) << ColorF.ABGR_PACKED_ALPHA_SHIFT);
		this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
	}

	private final void packABGR() {
		this.mABGRPackedInt = ColorUtils.convertRGBAToABGRPackedInt(this.mRed, this.mGreen, this.mBlue, this.mAlpha);
		this.mABGRPackedFloat = ColorUtils.convertPackedIntToPackedFloat(this.mABGRPackedInt);
	}

	public final void mix(final ColorF pColorFA, final float pPercentageA, final ColorF pColorFB, final float pPercentageB) {
		final float red = (pColorFA.mRed * pPercentageA) + (pColorFB.mRed * pPercentageB);
		final float green = (pColorFA.mGreen * pPercentageA) + (pColorFB.mGreen * pPercentageB);
		final float blue = (pColorFA.mBlue * pPercentageA) + (pColorFB.mBlue * pPercentageB);
		final float alpha = (pColorFA.mAlpha * pPercentageA) + (pColorFB.mAlpha * pPercentageB);

		this.set(red, green, blue, alpha);
	}

	/**
	 * 32bitパックされたABGR値からColorオブジェクトを構築する。
	 * @param argb ARGB 32ビット値
	 * @return Colorオブジェクトが返る。
	 */
	public static ColorF fromARGBPackedInt(int argb) {
		float a = ((argb >> ARGB_PACKED_ALPHA_SHIFT) & 0xff) / 255.0f;
		float r = ((argb >> ARGB_PACKED_RED_SHIFT) & 0xff) / 255.0f;
		float g = ((argb >> ARGB_PACKED_GREEN_SHIFT) & 0xff) / 255.0f;
		float b = ((argb >> ARGB_PACKED_BLUE_SHIFT) & 0xff) / 255.0f;
		return new ColorF(r, g, b, a);
	}

	/**
	 * 32bitパックされたABGR値からColorオブジェクトを構築する。
	 * @param abgr ABGR 32ビット値
	 * @return Colorオブジェクトが返る。
	 */
	public static ColorF fromABGRPackedInt(int abgr) {
		float a = ((abgr >> ABGR_PACKED_ALPHA_SHIFT) & 0xff) / 255.0f;
		float b = ((abgr >> ABGR_PACKED_BLUE_SHIFT) & 0xff) / 255.0f;
		float g = ((abgr >> ABGR_PACKED_GREEN_SHIFT) & 0xff) / 255.0f;
		float r = ((abgr >> ABGR_PACKED_RED_SHIFT) & 0xff) / 255.0f;
		return new ColorF(r, g, b, a);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

package org.andengine.entity.scene.background.modifier;

import org.andengine.entity.scene.background.IBackground;
import org.andengine.util.adt.color.ColorF;
import org.andengine.util.modifier.BaseTripleValueSpanModifier;
import org.andengine.util.modifier.ease.EaseLinear;
import org.andengine.util.modifier.ease.IEaseFunction;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 14:51:03 - 03.09.2010
 */
public class ColorBackgroundModifier extends BaseTripleValueSpanModifier<IBackground> implements IBackgroundModifier {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	public ColorBackgroundModifier(final float pDuration, final ColorF pFromColorF, final ColorF pToColorF) {
		this(pDuration, pFromColorF.getRed(), pToColorF.getRed(), pFromColorF.getGreen(), pToColorF.getGreen(), pFromColorF.getBlue(), pToColorF.getBlue(), null, EaseLinear.getInstance());
	}

	public ColorBackgroundModifier(final float pDuration, final float pFromRed, final float pToRed, final float pFromGreen, final float pToGreen, final float pFromBlue, final float pToBlue) {
		this(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, null, EaseLinear.getInstance());
	}

	public ColorBackgroundModifier(final float pDuration, final ColorF pFromColorF, final ColorF pToColorF, final IEaseFunction pEaseFunction) {
		this(pDuration, pFromColorF.getRed(), pToColorF.getRed(), pFromColorF.getGreen(), pToColorF.getGreen(), pFromColorF.getBlue(), pToColorF.getBlue(), null, pEaseFunction);
	}

	public ColorBackgroundModifier(final float pDuration, final float pFromRed, final float pToRed, final float pFromGreen, final float pToGreen, final float pFromBlue, final float pToBlue, final IEaseFunction pEaseFunction) {
		this(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, null, pEaseFunction);
	}

	public ColorBackgroundModifier(final float pDuration, final ColorF pFromColorF, final ColorF pToColorF, final IBackgroundModifierListener pBackgroundModifierListener) {
		super(pDuration, pFromColorF.getRed(), pToColorF.getRed(), pFromColorF.getGreen(), pToColorF.getGreen(), pFromColorF.getBlue(), pToColorF.getBlue(), pBackgroundModifierListener, EaseLinear.getInstance());
	}

	public ColorBackgroundModifier(final float pDuration, final float pFromRed, final float pToRed, final float pFromGreen, final float pToGreen, final float pFromBlue, final float pToBlue, final IBackgroundModifierListener pBackgroundModifierListener) {
		super(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pBackgroundModifierListener, EaseLinear.getInstance());
	}

	public ColorBackgroundModifier(final float pDuration, final ColorF pFromColorF, final ColorF pToColorF, final IBackgroundModifierListener pBackgroundModifierListener, final IEaseFunction pEaseFunction) {
		super(pDuration, pFromColorF.getRed(), pToColorF.getRed(), pFromColorF.getGreen(), pToColorF.getGreen(), pFromColorF.getBlue(), pToColorF.getBlue(), pBackgroundModifierListener, pEaseFunction);
	}

	public ColorBackgroundModifier(final float pDuration, final float pFromRed, final float pToRed, final float pFromGreen, final float pToGreen, final float pFromBlue, final float pToBlue, final IBackgroundModifierListener pBackgroundModifierListener, final IEaseFunction pEaseFunction) {
		super(pDuration, pFromRed, pToRed, pFromGreen, pToGreen, pFromBlue, pToBlue, pBackgroundModifierListener, pEaseFunction);
	}

	protected ColorBackgroundModifier(final ColorBackgroundModifier pColorBackgroundModifier) {
		super(pColorBackgroundModifier);
	}

	@Override
	public ColorBackgroundModifier deepCopy() {
		return new ColorBackgroundModifier(this);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onSetInitialValues(final IBackground pBackground, final float pRed, final float pGreen, final float pBlue) {
		pBackground.setColor(pRed, pGreen, pBlue);
	}

	@Override
	protected void onSetValues(final IBackground pBackground, final float pPerctentageDone, final float pRed, final float pGreen, final float pBlue) {
		pBackground.setColor(pRed, pGreen, pBlue);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

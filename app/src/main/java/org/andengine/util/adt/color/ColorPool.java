package org.andengine.util.adt.color;

import org.andengine.util.adt.pool.GenericPool;

/**
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 2:25:20 - 12.08.2011
 */
public class ColorPool extends GenericPool<ColorF> {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected ColorF onAllocatePoolItem() {
		return new ColorF(ColorF.WHITE);
	}

	@Override
	protected void onHandleRecycleItem(final ColorF pColorF) {
		pColorF.setChecking(ColorF.WHITE);

		super.onHandleRecycleItem(pColorF);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

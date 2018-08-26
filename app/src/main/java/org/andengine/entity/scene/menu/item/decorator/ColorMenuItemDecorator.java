package org.andengine.entity.scene.menu.item.decorator;

import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.util.adt.color.ColorF;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 14:25:35 - 07.07.2010
 */
public class ColorMenuItemDecorator extends BaseMenuItemDecorator {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final ColorF mSelectedColorF;
	private final ColorF mUnselectedColorF;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ColorMenuItemDecorator(final IMenuItem pMenuItem, final ColorF pSelectedColorF, final ColorF pUnselectedColorF) {
		super(pMenuItem);

		this.mSelectedColorF = pSelectedColorF;
		this.mUnselectedColorF = pUnselectedColorF;

		pMenuItem.setColor(pUnselectedColorF);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onMenuItemSelected(final IMenuItem pMenuItem) {
		pMenuItem.setColor(this.mSelectedColorF);
	}

	@Override
	public void onMenuItemUnselected(final IMenuItem pMenuItem) {
		pMenuItem.setColor(this.mUnselectedColorF);
	}

	@Override
	public void onMenuItemReset(final IMenuItem pMenuItem) {
		pMenuItem.setColor(this.mUnselectedColorF);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

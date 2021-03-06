package org.andengine.entity.clip;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.opengl.util.GLState;
import org.andengine.util.Constants;
import org.andengine.util.math.MathUtils;

/**
 * 子エンティティの描画範囲を、このEntityの範囲内の矩形に限ったものとするエンティティ。
 * シーングラフのどこにでも貼り付けることができる。
 *
 * 子エンティティの位置情報は、ClipEntityの親からの相対位置になることに注意する。
 *
 *
 *
 * (c) 2012 Zynga Inc.
 *
 * @author Nicolas Gramlich <ngramlich@zynga.com>
 * @since 13:53:29 - 10.05.2012
 */
public class ClipEntity extends Entity {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private boolean mClippingEnabled = true;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * コンストラクタ
	 */
	public ClipEntity() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param pX X座標
	 * @param pY Y座標
	 */
	public ClipEntity(final float pX, final float pY) {
		super(pX, pY);
	}

	/**
	 * コンストラクタ
	 * @param pX X座標
	 * @param pY Y座標
	 * @param pWidth 幅
	 * @param pHeight 高さ
	 */
	public ClipEntity(final float pX, final float pY, final float pWidth, final float pHeight) {
		super(pX, pY, pWidth, pHeight);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * クリッピングが有効かどうかを判定する。
	 * @return クリッピングが有効な場合にはtrue, それ以外はfalse
	 */
	public boolean isClippingEnabled() {
		return this.mClippingEnabled;
	}

	/**
	 * クリッピング領域の有効・無効を設定する。
	 * @param pClippingEnabled クリッピング領域の有効判定
	 */
	public void setClippingEnabled(final boolean pClippingEnabled) {
		this.mClippingEnabled = pClippingEnabled;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/**
	 * 描画を行う。
	 * @param pGLState OpenGLステータス
	 * @param pCamera カメラ
	 */
	@Override
	protected void onManagedDraw(final GLState pGLState, final Camera pCamera) {
		if (this.mClippingEnabled) {
			/* Enable scissor test, while remembering previous state. */
			final boolean wasScissorTestEnabled = pGLState.enableScissorTest();

			final int surfaceHeight = pCamera.getSurfaceHeight();

			/* In order to apply clipping, we need to determine the the axis aligned bounds in OpenGL coordinates. */

			/* Determine clipping coordinates of each corner in surface coordinates. */
			final float[] lowerLeftSurfaceCoordinates = pCamera.getSurfaceCoordinatesFromSceneCoordinates(this.convertLocalCoordinatesToSceneCoordinates(0, 0));
			final int lowerLeftX = (int) Math.round(lowerLeftSurfaceCoordinates[Constants.VERTEX_INDEX_X]);
			final int lowerLeftY = surfaceHeight - (int) Math.round(lowerLeftSurfaceCoordinates[Constants.VERTEX_INDEX_Y]);

			final float[] upperLeftSurfaceCoordinates = pCamera.getSurfaceCoordinatesFromSceneCoordinates(this.convertLocalCoordinatesToSceneCoordinates(0, this.mHeight));
			final int upperLeftX = (int) Math.round(upperLeftSurfaceCoordinates[Constants.VERTEX_INDEX_X]);
			final int upperLeftY = surfaceHeight - (int) Math.round(upperLeftSurfaceCoordinates[Constants.VERTEX_INDEX_Y]);

			final float[] upperRightSurfaceCoordinates = pCamera.getSurfaceCoordinatesFromSceneCoordinates(this.convertLocalCoordinatesToSceneCoordinates(this.mWidth, this.mHeight));
			final int upperRightX = (int) Math.round(upperRightSurfaceCoordinates[Constants.VERTEX_INDEX_X]);
			final int upperRightY = surfaceHeight - (int) Math.round(upperRightSurfaceCoordinates[Constants.VERTEX_INDEX_Y]);

			final float[] lowerRightSurfaceCoordinates = pCamera.getSurfaceCoordinatesFromSceneCoordinates(this.convertLocalCoordinatesToSceneCoordinates(this.mWidth, 0));
			final int lowerRightX = (int) Math.round(lowerRightSurfaceCoordinates[Constants.VERTEX_INDEX_X]);
			final int lowerRightY = surfaceHeight - (int) Math.round(lowerRightSurfaceCoordinates[Constants.VERTEX_INDEX_Y]);

			/* Determine minimum and maximum x clipping coordinates. */
			final int minClippingX = MathUtils.min(lowerLeftX, upperLeftX, upperRightX, lowerRightX);
			final int maxClippingX = MathUtils.max(lowerLeftX, upperLeftX, upperRightX, lowerRightX);

			/* Determine minimum and maximum y clipping coordinates. */
			final int minClippingY = MathUtils.min(lowerLeftY, upperLeftY, upperRightY, lowerRightY);
			final int maxClippingY = MathUtils.max(lowerLeftY, upperLeftY, upperRightY, lowerRightY);

			/* Determine clipping width and height. */
			final int clippingWidth = maxClippingX - minClippingX;
			final int clippingHeight = maxClippingY - minClippingY;

			/* Finally apply the clipping. */
			pGLState.glPushScissor(minClippingX, minClippingY, clippingWidth, clippingHeight);

			/* Draw children, etc... */
			super.onManagedDraw(pGLState, pCamera);

			/* Revert scissor test to previous state. */
			pGLState.glPopScissor();
			pGLState.setScissorTestEnabled(wasScissorTestEnabled);
		} else {
			super.onManagedDraw(pGLState, pCamera);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

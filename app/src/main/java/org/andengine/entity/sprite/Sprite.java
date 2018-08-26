package org.andengine.entity.sprite;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.vbo.HighPerformanceSpriteVertexBufferObject;
import org.andengine.entity.sprite.vbo.ISprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.shader.PositionColorTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;

import android.opengl.GLES20;

/**
 * スプライトクラス。
 * スプライトクラスは1枚のテクスチャを持つ矩形のエンティティを表す。
 *
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 *
 * @author Nicolas Gramlich
 * @since 19:22:38 - 09.03.2010
 */
public class Sprite extends Shape implements ISprite {
	// ===========================================================
	// Constants
	// ===========================================================

	/**
	 * バーテックスバッファ属性
	 */
	public static final VertexBufferObjectAttributes VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT = new VertexBufferObjectAttributesBuilder(3)
		.add(ShaderProgramConstants.ATTRIBUTE_POSITION_LOCATION, ShaderProgramConstants.ATTRIBUTE_POSITION, 2, GLES20.GL_FLOAT, false)
		.add(ShaderProgramConstants.ATTRIBUTE_COLOR_LOCATION, ShaderProgramConstants.ATTRIBUTE_COLOR, 4, GLES20.GL_UNSIGNED_BYTE, true)
		.add(ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES_LOCATION, ShaderProgramConstants.ATTRIBUTE_TEXTURECOORDINATES, 2, GLES20.GL_FLOAT, false)
		.build();

	// ===========================================================
	// Fields
	// ===========================================================

	/**
	 *
	 */
	protected final ITextureRegion mTextureRegion;
	/**
	 *
	 */
	protected final ISpriteVertexBufferObject mSpriteVertexBufferObject;

	/**
	 *
	 */
	protected boolean mFlippedVertical;
	/**
	 *
	 */
	protected boolean mFlippedHorizontal;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * テクスチャと同じサイズのSpriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからSTATICタイプのものが構築される。
	 * シェーダーはデフォルトのものが使用される。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObjectManager バーテックスバッファマネージャ
	 */
	public Sprite(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
	}

	/**
	 * シェーダーを指定してテクスチャと同じサイズのSpriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからSTATICタイプのものが構築される。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObjectManager バーテックスバッファマネージャ
	 * @param pShaderProgram シェーダー
	 */
	public Sprite(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, final ShaderProgram pShaderProgram) {
		this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
	}

	/**
	 * テクスチャと同じサイズのSpriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからpDrawTypeで指定された描画タイプで構築する。
	 * シェーダーはデフォルトのものが使用される。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObjectManager バーテックスバッファマネージャ
	 * @param pDrawType 描画タイプ
	 */
	public Sprite(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, final DrawType pDrawType) {
		this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, pDrawType);
	}

	/**
	 * シェーダーを指定してテクスチャと同じサイズのSpriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからpDrawTypeで指定された描画タイプで構築する。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObjectManager バーテックスバッファマネージャ
	 * @param pDrawType 描画タイプ
	 * @param pShaderProgram シェーダー
	 */
	public Sprite(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager, final DrawType pDrawType, final ShaderProgram pShaderProgram) {
		this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObjectManager, pDrawType, pShaderProgram);
	}

	/**
	 * バーテックスバッファを指定してテクスチャと同じサイズのSpriteオブジェクトを構築する。
	 * シェーダーはデフォルトのものが使用される。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObject バーテックスバッファ
	 */
	public Sprite(final float pX, final float pY, final ITextureRegion pTextureRegion,
				  final ISpriteVertexBufferObject pVertexBufferObject) {
		this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObject);
	}

	/**
	 * テクスチャと同じサイズのSpriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからSTATICタイプで構築する。
	 * シェーダーはデフォルトのものが使用される。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObject バーテックスバッファ
	 * @param pShaderProgram シェーダープログラム
	 */
	public Sprite(final float pX, final float pY, final ITextureRegion pTextureRegion, final ISpriteVertexBufferObject pVertexBufferObject, final ShaderProgram pShaderProgram) {
		this(pX, pY, pTextureRegion.getWidth(), pTextureRegion.getHeight(), pTextureRegion, pVertexBufferObject, pShaderProgram);
	}

	/**
	 * Spriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからSTATICタイプで構築する。
	 * シェーダーはデフォルトのものが使用される。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pWidth 幅
	 * @param pHeight 高さ
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObjectManager バーテックスバッファマネージャ
	 */
	public Sprite(final float pX, final float pY, final float pWidth, final float pHeight, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC);
	}

	/**
	 * シェーダーを指定してSpriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからSTATICタイプで構築する。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pWidth 幅
	 * @param pHeight 高さ
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObjectManager バーテックスバッファマネージャ
	 * @param pShaderProgram シェーダー
	 */
	public Sprite(final float pX, final float pY, final float pWidth, final float pHeight,
				  final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,
				  final ShaderProgram pShaderProgram) {
		this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, DrawType.STATIC, pShaderProgram);
	}

	/**
	 * Spriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからpDrawTypeで指定されたタイプで構築する。
	 * シェーダーはデフォルトのものが使用される。
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pWidth 幅
	 * @param pHeight 高さ
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObjectManager バーテックスバッファマネージャ
	 * @param pDrawType 描画タイプ
	 */
	public Sprite(final float pX, final float pY, final float pWidth, final float pHeight,
				  final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,
				  final DrawType pDrawType) {
		this(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager, pDrawType,
				PositionColorTextureCoordinatesShaderProgram.getInstance());
	}

	/**
	 * 描画タイプとシェーダーを指定してSpriteオブジェクトを構築する。
	 * バーテックスバッファはpVertexBufferObjectManagerからpDrawTypeで指定されたタイプで構築する。
	 *
	 *
	 * @param pX x位置
	 * @param pY y位置
	 * @param pWidth 幅
	 * @param pHeight 高さ
	 * @param pTextureRegion テクスチャ
	 * @param pVertexBufferObjectManager バーテックスバッファマネージャ
	 * @param pDrawType 描画タイプ
	 * @param pShaderProgram シェーダー
	 */
	public Sprite(final float pX, final float pY, final float pWidth, final float pHeight,
				  final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager,
				  final DrawType pDrawType, final ShaderProgram pShaderProgram) {

		this(pX, pY, pWidth, pHeight, pTextureRegion,
				new HighPerformanceSpriteVertexBufferObject(pVertexBufferObjectManager, Sprite.SPRITE_SIZE,
						pDrawType, true, Sprite.VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT), pShaderProgram);
	}

	/**
	 * バーテックスバッファを指定してSpriteオブジェクトを構築する。
	 * シェーダーはデフォルトのものが使用される。
	 *
	 * @param pX X位置
	 * @param pY Y位置
	 * @param pWidth 幅
	 * @param pHeight 高さ
	 * @param pTextureRegion テクスチャ
	 * @param pSpriteVertexBufferObject バーテックスバッファ。
	 */
	public Sprite(final float pX, final float pY, final float pWidth, final float pHeight,
				  final ITextureRegion pTextureRegion, final ISpriteVertexBufferObject pSpriteVertexBufferObject) {
		this(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject, PositionColorTextureCoordinatesShaderProgram.getInstance());
	}

	/**
	 * 使用するバーテックスバッファとシェーダープログラムを指定してSpriteオブジェクトを構築する。
	 *
	 * @param pX X位置
	 * @param pY Y位置
	 * @param pWidth 幅
	 * @param pHeight 高さ
	 * @param pTextureRegion テクスチャ
	 * @param pSpriteVertexBufferObject 頂点バッファ
	 * @param pShaderProgram レンダリングを行うシェーダープログラム
	 */
	public Sprite(final float pX, final float pY, final float pWidth, final float pHeight,
				  final ITextureRegion pTextureRegion, final ISpriteVertexBufferObject pSpriteVertexBufferObject,
				  final ShaderProgram pShaderProgram) {
		super(pX, pY, pShaderProgram);

		this.mTextureRegion = pTextureRegion;
		this.mSpriteVertexBufferObject = pSpriteVertexBufferObject;

		this.setBlendingEnabled(true);
		this.initBlendFunction(pTextureRegion);

		this.setSize(pWidth, pHeight);

		this.onUpdateColor();
		this.onUpdateTextureCoordinates();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 *
	 */
	public ITextureRegion getTextureRegion() {
		return this.mTextureRegion;
	}

	/**
	 *
	 */
	public boolean isFlippedHorizontal() {
		return this.mFlippedHorizontal;
	}

	/**
	 *
	 */
	public void setFlippedHorizontal(final boolean pFlippedHorizontal) {
		if (this.mFlippedHorizontal != pFlippedHorizontal) {
			this.mFlippedHorizontal = pFlippedHorizontal;

			this.onUpdateTextureCoordinates();
		}
	}

	/**
	 *
	 */
	public boolean isFlippedVertical() {
		return this.mFlippedVertical;
	}

	/**
	 *
	 */
	public void setFlippedVertical(final boolean pFlippedVertical) {
		if (this.mFlippedVertical != pFlippedVertical) {
			this.mFlippedVertical = pFlippedVertical;

			this.onUpdateTextureCoordinates();
		}
	}

	/**
	 *
	 */
	public void setFlipped(final boolean pFlippedHorizontal, final boolean pFlippedVertical) {
		if ((this.mFlippedHorizontal != pFlippedHorizontal) || (this.mFlippedVertical != pFlippedVertical)) {
			this.mFlippedHorizontal = pFlippedHorizontal;
			this.mFlippedVertical = pFlippedVertical;

			this.onUpdateTextureCoordinates();
		}
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/**
	 *
	 */
	@Override
	public ISpriteVertexBufferObject getVertexBufferObject() {
		return this.mSpriteVertexBufferObject;
	}

	/**
	 *
	 */
	@Override
	public void reset() {
		super.reset();

		this.initBlendFunction(this.getTextureRegion().getTexture());
	}

	/**
	 *
	 */
	@Override
	protected void preDraw(final GLState pGLState, final Camera pCamera) {
		super.preDraw(pGLState, pCamera);

		this.getTextureRegion().getTexture().bind(pGLState);

		this.mSpriteVertexBufferObject.bind(pGLState, this.mShaderProgram);
	}

	/**
	 *
	 */
	@Override
	protected void draw(final GLState pGLState, final Camera pCamera) {
		this.mSpriteVertexBufferObject.draw(GLES20.GL_TRIANGLE_STRIP, Sprite.VERTICES_PER_SPRITE);
	}

	/**
	 *
	 */
	@Override
	protected void postDraw(final GLState pGLState, final Camera pCamera) {
		this.mSpriteVertexBufferObject.unbind(pGLState, this.mShaderProgram);

		super.postDraw(pGLState, pCamera);
	}

	/**
	 *
	 */
	@Override
	protected void onUpdateVertices() {
		this.mSpriteVertexBufferObject.onUpdateVertices(this);
	}

	/**
	 *
	 */
	@Override
	protected void onUpdateColor() {
		this.mSpriteVertexBufferObject.onUpdateColor(this);
	}

	/**
	 *
	 */
	protected void onUpdateTextureCoordinates() {
		this.mSpriteVertexBufferObject.onUpdateTextureCoordinates(this);
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

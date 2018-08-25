package org.andengine.entity.sprite;

import android.graphics.Canvas;
import android.opengl.GLES20;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.vbo.HighPerformanceSpriteVertexBufferObject;
import org.andengine.entity.sprite.vbo.ISprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.shader.PositionColorTextureCoordinatesShaderProgram;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.shader.constants.ShaderProgramConstants;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.decorator.BaseBitmapTextureAtlasSourceDecorator;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributes;
import org.andengine.opengl.vbo.attribute.VertexBufferObjectAttributesBuilder;

/**
 * IVectorPaintを使って描画するSprite。
 */
public class CanvasSprite extends Shape implements ISprite {
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
     * 垂直方向
     */
    protected boolean mFlippedVertical;
    /**
     * 水平方向
     */
    protected boolean mFlippedHorizontal;
    // ベクタ画像描画オブジェクト
    private ICanvasPaint mVectorPaint;
    // テクスチャ
    private ITextureRegion mTextureRegion;
    // バーテックスバッファ
    private ISpriteVertexBufferObject mSpriteVertexBufferObject;
    // テクスチャ
    private BitmapTextureAtlas mBitmapTextureAtlas;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * コンストラクタ。
     * vertexBufferObjectManagerからSTATICなバーテックスバッファを作成し、
     * シェーダーはデフォルトのものを使う。
     *
     * @param textureManager テクスチャマネージャ
     * @param x x位置
     * @param y y位置
     * @param width 幅
     * @param height 高さ
     * @param vectorPaint 描画オブジェクト
     * @param vertexBufferObjectManager バーテックスバッファマネージャ
     */
    public CanvasSprite(final TextureManager textureManager, final float x, final float y, final float width, final float height,
                        final ICanvasPaint vectorPaint, final VertexBufferObjectManager vertexBufferObjectManager) {
        this(textureManager, x, y, width, height, vectorPaint,
                new HighPerformanceSpriteVertexBufferObject(vertexBufferObjectManager, ISprite.SPRITE_SIZE, DrawType.STATIC, true, CanvasSprite.VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }

    /**
     * コンストラクタ
     * vertexBufferObjectManager及びdrawTypeからバーテックスバッファを作成し、
     * シェーダーはデフォルトのものを使う。
     *
     * @param textureManager テクスチャマネージャ
     * @param x x位置
     * @param y y位置
     * @param width 幅
     * @param height 高さ
     * @param vectorPaint 描画オブジェクト
     * @param vertexBufferObjectManager  バーテックスバッファマネージャ
     * @param drawType 描画タイプ
     */
    public CanvasSprite(final TextureManager textureManager, final float x, final float y, final float width, final float height,
                        final ICanvasPaint vectorPaint, final VertexBufferObjectManager vertexBufferObjectManager, final DrawType drawType) {
        this(textureManager, x, y, width, height, vectorPaint,
                new HighPerformanceSpriteVertexBufferObject(vertexBufferObjectManager, ISprite.SPRITE_SIZE, drawType, true, CanvasSprite.VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }


    /**
     * コンストラクタ。
     * デフォルトのシェーダーを使う。
     *
     * @param textureManager テクスチャマネージャ
     * @param x x位置
     * @param y y位置
     * @param width 幅
     * @param height 高さ
     * @param vectorPaint 描画オブジェクト
     * @param vertexBuffer バーテックスバッファ
     */
    public CanvasSprite(final TextureManager textureManager, final float x, final float y, final float width, final float height,
                        final ICanvasPaint vectorPaint, final ISpriteVertexBufferObject vertexBuffer) {
        this(textureManager, x, y, width, height, vectorPaint, vertexBuffer, PositionColorTextureCoordinatesShaderProgram.getInstance());
    }

    /**
     * コンストラクタ
     *
     * @param textureManager テクスチャマネージャ
     * @param x x位置
     * @param y y位置
     * @param width 幅
     * @param height 高さ
     * @param vectorPaint 描画オブジェクト
     * @param vertexBuffer バーテックスバッファ
     * @param shaderProgram シェーダープログラム
     */
    public CanvasSprite(final TextureManager textureManager, final float x, final float y, final float width, final float height,
                        final ICanvasPaint vectorPaint,
                        final ISpriteVertexBufferObject vertexBuffer,
                        final ShaderProgram shaderProgram) {
        super(x, y, shaderProgram);

        this.mVectorPaint = vectorPaint;
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(textureManager,
                (int)(width), (int)(height), TextureOptions.BILINEAR);

        final IBitmapTextureAtlasSource baseTextureAtlasSource
                = new EmptyBitmapTextureAtlasSource((int)(width), (int)(height));
        final IBitmapTextureAtlasSource decolatedTextureAtlasSource
                = new DecolatedTextureAtlasSource(baseTextureAtlasSource, mVectorPaint);
        this.mBitmapTextureAtlas.load();
        this.mTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(
                this.mBitmapTextureAtlas, decolatedTextureAtlasSource, 0, 0);
        this.mSpriteVertexBufferObject = vertexBuffer;

        this.setBlendingEnabled(true);
        this.initBlendFunction(mTextureRegion);
        this.setSize(width, height);
        this.onUpdateColor();
        this.onUpdateTextureCoordinates();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    /**
     * テクスチャを取得する。
     * @return テクスチャ
     */
    public ITextureRegion getTextureRegion() {
        return this.mTextureRegion;
    }

    /**
     * 水平方向を反転させるかを取得する。
     *
     * @return 水平方向に反転させる場合にはtrue。
     */
    public boolean isFlippedHorizontal() {
        return this.mFlippedHorizontal;
    }

    /**
     * 水平方向を反転させるかどうかを設定する。
     *
     * @param pFlippedHorizontal 水平方向を反転させる場合にはtrue
     */
    public void setFlippedHorizontal(final boolean pFlippedHorizontal) {
        if (this.mFlippedHorizontal != pFlippedHorizontal) {
            this.mFlippedHorizontal = pFlippedHorizontal;

            this.onUpdateTextureCoordinates();
        }
    }

    /**
     * 垂直方向を反転させるかどうかを設定する。
     *
     * @return 垂直方向を反転させる場合にはtrue
     */
    public boolean isFlippedVertical() {
        return this.mFlippedVertical;
    }

    /**
     * 垂直方向を反転させるかどうかを設定する。
     *
     * @param pFlippedVertical 垂直方向に反転させる場合にはtrue
     */
    public void setFlippedVertical(final boolean pFlippedVertical) {
        if (this.mFlippedVertical != pFlippedVertical) {
            this.mFlippedVertical = pFlippedVertical;

            this.onUpdateTextureCoordinates();
        }
    }

    /**
     * 反転状態を設定する。
     *
     * @param pFlippedHorizontal 水平方向反転状態
     * @param pFlippedVertical 垂直方向反転状態
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
     * バーテックスバッファを取得する。
     *
     * @return バーテックスバッファオブジェクト。
     */
    @Override
    public ISpriteVertexBufferObject getVertexBufferObject() {
        return this.mSpriteVertexBufferObject;
    }

    /**
     * リセットする。
     */
    @Override
    public void reset() {
        super.reset();

        this.initBlendFunction(this.getTextureRegion().getTexture());
    }

    /**
     * 描画を行う。
     * @param pGLState GLES20オブジェクト
     * @param pCamera カメラ
     */
    @Override
    protected void preDraw(final GLState pGLState, final Camera pCamera) {
        super.preDraw(pGLState, pCamera);

        this.getTextureRegion().getTexture().bind(pGLState);

        this.mSpriteVertexBufferObject.bind(pGLState, this.mShaderProgram);
    }

    /**
     * 描画を行う。
     * @param pGLState the currently active {@link GLState} i.e. to apply transformations to.
     * @param pCamera the currently active {@link Camera} i.e. to be used for culling.
     */
    @Override
    protected void draw(final GLState pGLState, final Camera pCamera) {
        this.mSpriteVertexBufferObject.draw(GLES20.GL_TRIANGLE_STRIP, Sprite.VERTICES_PER_SPRITE);
    }

    /**
     * 描画を行う。
     * @param pGLState
     * @param pCamera
     */
    @Override
    protected void postDraw(final GLState pGLState, final Camera pCamera) {
        this.mSpriteVertexBufferObject.unbind(pGLState, this.mShaderProgram);

        super.postDraw(pGLState, pCamera);
    }

    /**
     * 頂点を更新する。
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

    /**
     * 描画対象のテクスチャを取得するためのラッパークラス。
     */
    private class DecolatedTextureAtlasSource extends BaseBitmapTextureAtlasSourceDecorator {
        // 描画用オブジェクト
        private ICanvasPaint mVectorPaint;

        /**
         * コンストラクタ
         * @param baseTextureAtlasSource 描画対象のテクスチャ
         * @param vectorPaint 描画オブジェクト
         */
        DecolatedTextureAtlasSource(IBitmapTextureAtlasSource baseTextureAtlasSource, ICanvasPaint vectorPaint) {
            super(baseTextureAtlasSource);
            mVectorPaint = vectorPaint;
        }

        /**
         * コピーを作成する
         * @return DecolatedTextureAtlasSourceオブジェクト
         */
        @Override
        public DecolatedTextureAtlasSource deepCopy() {
            return new DecolatedTextureAtlasSource(mBitmapTextureAtlasSource.deepCopy(), mVectorPaint);
        }

        /**
         * テクスチャのイメージデータを取得する。
         * @param pCanvas 描画対象のキャンバス
         * @throws Exception エラーが発生した場合
         */
        @Override
        protected void onDecorateBitmap(Canvas pCanvas) throws Exception {
            mVectorPaint.draw(pCanvas, mPaint);
        }
    }

}

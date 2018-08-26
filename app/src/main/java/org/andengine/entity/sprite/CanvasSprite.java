package org.andengine.entity.sprite;

import android.graphics.Canvas;
import android.opengl.GLES20;
import android.support.annotation.NonNull;

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
import org.andengine.util.adt.color.ColorF;

/**
 * ICanvasPaintを使って描画するスプライト(Sprite)。
 *
 * Canvasでベクタ画像を書いてテクスチャにしたい場合などに使うユーティリティクラス。
 * Andengineの元デザインでは、 IBtmapTextureAtlasSource インタフェースを介して
 * 貼り付けるテクスチャイメージを生成する構造を推奨している。
 * その場合、シーン側でテクスチャの管理をしなければいけなくなるため、
 * 簡素化したものを用意した。
 *
 * このクラスはコンストラクタでテクスチャのロードを行うので、
 * バックグラウンドで予めテクスチャを読み込むようなアーキテクチャには対応できない。
 * バックグラウンドでの読み出しをやりたい場合にはIBitmapTextureAtlasSourceを使うようにすること。
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
    // 背景色
    protected ColorF mBackgroundColorF;

    // ===========================================================
    // Constructors
    // ===========================================================

    /**
     * CanvasSpriteオブジェクトを構築する。
     * vertexBufferObjectManagerからSTATICなバーテックスバッファを作成する。
     * シェーダーはデフォルトのものを使う。
     *
     * @param textureManager テクスチャマネージャ
     * @param x x位置
     * @param y y位置
     * @param width 幅
     * @param height 高さ
     * @param canvasPaint 描画オブジェクト
     * @param foregroundColor 前景色
     * @param backgroundColor 背景色
     * @param vertexBufferObjectManager バーテックスバッファマネージャ
     */
    public CanvasSprite(final TextureManager textureManager, final float x, final float y, final float width, final float height,
                        final ICanvasPaint canvasPaint, ColorF foregroundColor, ColorF backgroundColor,
                        final VertexBufferObjectManager vertexBufferObjectManager) {
        this(textureManager, x, y, width, height, canvasPaint, foregroundColor, backgroundColor,
                new HighPerformanceSpriteVertexBufferObject(vertexBufferObjectManager, ISprite.SPRITE_SIZE, DrawType.STATIC, true, CanvasSprite.VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }

    /**
     * CanvasSpriteオブジェクトを構築する。
     * vertexBufferObjectManagerからdrawTypeで指定するバーテックスバッファを作成する。
     * シェーダーはデフォルトのものを使う。
     *
     * @param textureManager テクスチャマネージャ
     * @param x x位置
     * @param y y位置
     * @param width 幅
     * @param height 高さ
     * @param canvasPaint 描画オブジェクト
     * @param foregroundColor 前景色
     * @param backgroundColor 背景色
     * @param vertexBufferObjectManager  バーテックスバッファマネージャ
     * @param drawType 描画タイプ
     */
    public CanvasSprite(final TextureManager textureManager, final float x, final float y, final float width, final float height,
                        final ICanvasPaint canvasPaint, ColorF foregroundColor, ColorF backgroundColor,
                        final VertexBufferObjectManager vertexBufferObjectManager, final DrawType drawType) {
        this(textureManager, x, y, width, height, canvasPaint, foregroundColor, backgroundColor,
                new HighPerformanceSpriteVertexBufferObject(vertexBufferObjectManager, ISprite.SPRITE_SIZE, drawType, true, CanvasSprite.VERTEXBUFFEROBJECTATTRIBUTES_DEFAULT));
    }


    /**
     * バーテックスバッファを指定してCanvasSpriteオブジェクトを構築する。
     * シェーダーはデフォルトのものが使用される。
     *
     * @param textureManager テクスチャマネージャ
     * @param x x位置
     * @param y y位置
     * @param width 幅
     * @param height 高さ
     * @param canvasPaint 描画オブジェクト
     * @param foregroundColor 前景色
     * @param backgroundColor 背景色
     * @param vertexBuffer バーテックスバッファ
     */
    public CanvasSprite(final TextureManager textureManager, final float x, final float y, final float width, final float height,
                        final ICanvasPaint canvasPaint, final ColorF foregroundColor, final ColorF backgroundColor,
                        final ISpriteVertexBufferObject vertexBuffer) {
        this(textureManager, x, y, width, height, canvasPaint, foregroundColor, backgroundColor,
                vertexBuffer, PositionColorTextureCoordinatesShaderProgram.getInstance());
    }

    /**
     * バーテックスバッファとシェーダーを指定してCanvasSpriteを構築する。
     *
     * @param textureManager テクスチャマネージャ
     * @param x x位置
     * @param y y位置
     * @param width 幅
     * @param height 高さ
     * @param canvasPaint 描画オブジェクト
     * @param foregroundColor 前景色
     * @param backgroundColor 背景色
     * @param vertexBuffer バーテックスバッファ
     * @param shaderProgram シェーダープログラム
     */
    public CanvasSprite(final TextureManager textureManager, final float x, final float y, final float width, final float height,
                        final ICanvasPaint canvasPaint, ColorF foregroundColor, ColorF backgroundColor,
                        final ISpriteVertexBufferObject vertexBuffer,
                        final ShaderProgram shaderProgram) {
        super(x, y, shaderProgram);
        this.mColorF.set(foregroundColor);
        this.mBackgroundColorF = backgroundColor;

        this.mVectorPaint = canvasPaint;
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(textureManager,
                (int)(width), (int)(height), TextureOptions.BILINEAR);

        final IBitmapTextureAtlasSource baseTextureAtlasSource
                = new EmptyBitmapTextureAtlasSource((int)(width), (int)(height));
        final IBitmapTextureAtlasSource decolatedTextureAtlasSource
                = new DecolatedTextureAtlasSource(baseTextureAtlasSource, mVectorPaint);
        this.mBitmapTextureAtlas.load(); // テクスチャの読み込み。重い。
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

    /**
     * 背景色を設定する。
     * @param color ARGB 32bitパッキングされた色データ
     */
    public void setBackgroundColor(int color) {
        setBackgroundColor(ColorF.fromARGBPackedInt(color));
    }

    /**
     * 背景色を設定する。
     * @param colorF Colorオブジェクト
     */
    public void setBackgroundColor(ColorF colorF) {
        if (colorF == null) {
            throw new NullPointerException("setBackgroundColor accept null reference.");
        }
        if (mBackgroundColorF.equals(colorF)) {
            return ;
        }
        mBackgroundColorF = colorF;
        onUpdateColor();
    }

    public ColorF getBackgroundColor() {
        return mBackgroundColorF;
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
     * @param pGLState OpenGLオブジェクト
     * @param pCamera カメラ
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
     * 色が変更されたときに呼び出される。
     */
    @Override
    protected void onUpdateColor() {
        // テクスチャの再読込をしたいなあ。
        mBitmapTextureAtlas.load();
        this.mSpriteVertexBufferObject.onUpdateColor(this);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    /**
     * テクスチャ座標を更新する。
     */
    protected void onUpdateTextureCoordinates() {
        this.mSpriteVertexBufferObject.onUpdateTextureCoordinates(this);
    }

    /**
     * テクスチャを読み込む。
     */
    public void load() {
        mBitmapTextureAtlas.load();
        this.mSpriteVertexBufferObject.onUpdateColor(this);
    }

    /**
     * テクスチャを解放する。
     */
    public void unload() {
        mBitmapTextureAtlas.unload();
    }

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
            mVectorPaint.draw(pCanvas, mPaint, mColorF.getARGBPackedInt(), mBackgroundColorF.getARGBPackedInt());
        }
    }
}

package com.github.kapuusagi.sandbox.menu;

import com.github.kapuusagi.sandbox.menu.texture.FrameTexture;

import org.andengine.entity.Entity;
import org.andengine.entity.clip.ClipEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.EmptyBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * メニューエンティティ
 */
public class Menu extends Entity {
    //----------------------------------------------------------------------------------------------
    // クラスグローバル
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // クラス内部
    //----------------------------------------------------------------------------------------------
    // メニュー項目
    private java.util.List<Object> mMenuItems;
    // クリップエンティティ
    private ClipEntity mMenuClipEntity;
    // メニュー項目を描画するためのテキスト
    private Text mMenuText;


    // フレーム
    private BitmapTextureAtlas mFrameBitmapTextureAtlas;
    // テクスチャ領域
    private ITextureRegion mFrameTextureRegion;
    // 枠のスプライト
    private Sprite mFrameSprite;




    /**
     * コンストラクタ
     * @param x X位置
     * @param y Y位置
     * @param width 幅
     * @param height 高さ
     * @param menuItems メニュー項目
     */
    public Menu(TextureManager textureManager, VertexBufferObjectManager vertexBufferObjectManager,
                float x, float y, float width, float height, java.util.List<Object> menuItems,
                int fgColor, int bgColor) {
        super(x, y, width, height);
        this.mMenuItems = menuItems;

        // フレームテクスチャを構築する
        initFrameSprite(textureManager, vertexBufferObjectManager, fgColor, bgColor);
        attachChild(mFrameSprite);

        // テキスト領域
        initTextEntity();
        attachChild(mMenuClipEntity);

        updateMenuPosition();
    }

    /**
     * フレームのスプライトを構築する
     * @param textureManager テクスチャマネージャ
     * @param vertexBufferObjectManager バーテックスバッファオブジェクトマネージャ
     * @param fgColor 前景色
     * @param bgColor 背景色
     */
    private void initFrameSprite(TextureManager textureManager, VertexBufferObjectManager vertexBufferObjectManager,
                                 int fgColor, int bgColor) {
        mFrameBitmapTextureAtlas = new BitmapTextureAtlas(textureManager, (int)(getWidth()), (int)(getHeight()),
                TextureOptions.BILINEAR);
        final IBitmapTextureAtlasSource baseTextureAtlasSource
                = new EmptyBitmapTextureAtlasSource((int)(getWidth()), (int)(getHeight()));
        final IBitmapTextureAtlasSource textureAtlasSource
                = new FrameTexture(baseTextureAtlasSource, fgColor, bgColor);
        mFrameTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromSource(
                mFrameBitmapTextureAtlas, textureAtlasSource, 0, 0);
        mFrameBitmapTextureAtlas.load();
        mFrameSprite = new Sprite(0, 0, mFrameTextureRegion, vertexBufferObjectManager);
    }

    private void initTextEntity() {
        StringBuilder sb = new StringBuilder();
        for (Object item : mMenuItems) {

        }

        mMenuClipEntity = new ClipEntity();

    }

    /**
     * サイズを設定する
     * @param pWidth 幅
     * @param pHeight 高さ
     */
    @Override
    public void setSize(float pWidth, float pHeight) {
        super.setSize(pWidth, pHeight);
        updateMenuPosition();
    }

    /**
     * 幅を設定する。
     * @param width 幅
     */
    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        updateMenuPosition();
    }

    /**
     * 高さを設定する。
     * @param height 高さ
     */
    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        updateMenuPosition();
    }

    /**
     * メニューの位置を調整する。
     *
     * ボタンやメニューリストのレイアウトを再設定する。
     */
    protected void updateMenuPosition() {
        mFrameSprite.setPosition(0,0);
        mFrameSprite.setSize(getWidth(), getHeight());



    }
}

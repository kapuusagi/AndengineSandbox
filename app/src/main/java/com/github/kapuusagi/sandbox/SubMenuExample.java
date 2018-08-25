package com.github.kapuusagi.sandbox;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.animator.SlideMenuSceneAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * よくわからん例。
 *
 * おそらくキーイベントを受け取ってごにょごにょするはずだが、
 * タッチ操作では全く反応しなかった。
 */
public class SubMenuExample extends MenuExample {
    // ===========================================================
    // Constants
    // ===========================================================

    private static final int MENU_QUIT_OK = MenuExample.MENU_QUIT + 1;
    private static final int MENU_QUIT_BACK = MENU_QUIT_OK + 1;

    // ===========================================================
    // Fields
    // ===========================================================

    private MenuScene mSubMenuScene;

    private BitmapTextureAtlas mSubMenuTexture;
    private ITextureRegion mMenuOkTextureRegion;
    private ITextureRegion mMenuBackTextureRegion;

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
    public void onCreateResources() {
        super.onCreateResources();
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        this.mSubMenuTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
        this.mMenuOkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSubMenuTexture, this, "menu_ok.png", 0, 0);
        this.mMenuBackTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mSubMenuTexture, this, "menu_back.png", 0, 50);
        this.mSubMenuTexture.load();
    }

    @Override
    protected void createMenuScene() {
        super.createMenuScene();

        this.mSubMenuScene = new MenuScene(this.mCamera);
        this.mSubMenuScene.addMenuItem(new SpriteMenuItem(MENU_QUIT_OK, this.mMenuOkTextureRegion, this.getVertexBufferObjectManager()));
        this.mSubMenuScene.addMenuItem(new SpriteMenuItem(MENU_QUIT_BACK, this.mMenuBackTextureRegion, this.getVertexBufferObjectManager()));
        this.mSubMenuScene.setMenuSceneAnimator(new SlideMenuSceneAnimator());
        this.mSubMenuScene.buildAnimations();

        this.mSubMenuScene.setBackgroundEnabled(false);

        this.mSubMenuScene.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
        switch(pMenuItem.getID()) {
            case MENU_RESET:
                this.mMainScene.reset();

                this.mMenuScene.back();
                return true;
            case MENU_QUIT:
                pMenuScene.setChildSceneModal(this.mSubMenuScene);
                return true;
            case MENU_QUIT_BACK:
                this.mSubMenuScene.back();
                return true;
            case MENU_QUIT_OK:
                this.finish();
                return true;
            default:
                return false;
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}

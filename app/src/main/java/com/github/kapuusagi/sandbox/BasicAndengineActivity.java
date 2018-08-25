package com.github.kapuusagi.sandbox;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.ui.activity.SimpleBaseGameActivity;

/**
 * SimpleLayoutGameActivityの最低限の実装
 *
 * 全体の流れ
 *     onCreate
 *       ↓
 *     onResume (変更不要)
 *       ↓
 *     onSurfaceCreated (変更不要)
 *       ↓
 *     onCreateGame (変更不要)
 *       ↓
 *     onCreateResources
 *       ↓
 *     onCreateScene
 *       ↓
 *     onPopulateScene
 *       ↓
 *     onGameCreated
 *       ↓
 *     onSurfaceChanged
 *       ↓
 *     onResumeGame
 *       ↓
 *     アクティビティ実行
 *       ↓
 *     onPause
 *       ↓
 *     onPauseGame
 *       ↓
 *     onDestroy
 *       ↓
 *     onDestroyResources
 *       ↓
 *     onGameDestroyed
 *
 */
public class BasicAndengineActivity extends SimpleBaseGameActivity {
    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    @Override
    protected void onCreateResources() {

    }

    @Override
    protected Scene onCreateScene() {
        Scene scene = new Scene();
        scene.setBackground(new Background(0f,1.0f,0f));
        return scene;
    }

    /**
     * エンジンオプションを取得する。
     * @return エンジンオプション
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }
}

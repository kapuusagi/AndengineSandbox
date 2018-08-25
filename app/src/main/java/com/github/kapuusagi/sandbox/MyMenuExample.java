package com.github.kapuusagi.sandbox;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class MyMenuExample extends SimpleBaseGameActivity {
    private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;

    /**
     * エンジンオプション生成時に呼ばれる。
     * onCreateScene()より前に呼び出される。
     * @return エンジンオプションを取得する。
     */
    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }

    /**
     * リソースを作成する。
     */
    @Override
    public void onCreateResources() {
    }

    /**
     * メインシーンを作成する。
     * 一般的には各シーンの派生クラスで描画オブジェクトを設定するような形になる。
     * @return シーンオブジェクト。
     */
    @Override
    public Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());

        final Scene scene = new Scene();








        return scene;
    }
}

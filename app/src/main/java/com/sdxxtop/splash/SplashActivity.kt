package com.sdxxtop.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sdxxtop.splash.viewmodel.AutoLoginViewModel
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.ui.home.MainTabActivity
import com.sdxxtop.zjlguardian.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    val mViewModel: AutoLoginViewModel by lazy {
        ViewModelProviders.of(this@SplashActivity)[AutoLoginViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val lp = window.attributes
            // 仅当缺口区域完全包含在状态栏之中时，才允许窗口延伸到刘海区域显示
            // lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            // 永远不允许窗口延伸到刘海区域
            // lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
            // 始终允许窗口延伸到屏幕短边上的刘海区域
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
        }

        mViewModel.mAutoLoginSucc.observe(this, Observer {
            if (it) {
                startActivity(Intent(this@SplashActivity, MainTabActivity::class.java))
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
        })

        mViewModel.postAutoLogin()

    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}

package com.sdxxtop.mapsdk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdxxtop.common.showToasty
import kotlinx.android.synthetic.main.activity_map_test.*

class MapTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_test)
        mv_test.onCreate(savedInstanceState)
        var map = mv_test.map

        btnPosition.setOnClickListener {
            var instance = FindLocation.getInstance()
            instance.setLocationListener {
                showToasty("${it.address} <---> ${it.latitude}, ${it.longitude}")
            }
            instance.location(this@MapTestActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        mv_test?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mv_test?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mv_test?.onDestroy()
    }
}

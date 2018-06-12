package io.kaeawc.blurapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        val background = BlurKit.loadBitmapFromFile("/data/user/0/io.kaeawc.blurapp/cache/img_cache/asdf") ?: return
        blur_layout.setImageBitmap(background)
        //blur_layout.animate().alpha(1f).duration = 100
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}

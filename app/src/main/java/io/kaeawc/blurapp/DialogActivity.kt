package io.kaeawc.blurapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dialog.*

/**
 * [DialogActivity] is the testing ground for how we might display
 * the blurred screenshot from another activity.
 */
class DialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        val background = BlurKit.loadBlur() ?: return
        blur_layout.setImageBitmap(background)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}

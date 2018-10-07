package io.kaeawc.blurapp

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

/**
 * [MainActivity] is the launcher activity used as a basis for what
 * we might screenshot and blur.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val bitmap = BlurKit.drawViewToBitmap(root_view, Color.parseColor("#fff5f5f5"))
            val blurredBitmap = BlurKit.fastBlur(baseContext, bitmap, 25f)
            bitmap.recycle()
            BlurKit.saveBitmapToFile(this, blurredBitmap)
            blurredBitmap.recycle()
            startActivity(Intent(baseContext, DialogActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}

package io.kaeawc.blurapp


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BlurKit {

    fun loadBitmapFromFile(fileName: String): Bitmap? {
        val localOptions = BitmapFactory.Options()
        localOptions.inPurgeable = true
        localOptions.inInputShareable = true
        return BitmapFactory.decodeFile(fileName, localOptions)
    }

    fun saveBitmapToFile(context: Activity, bitmap: Bitmap): String? {
        return try {
            val file = generateFile(context)
            file.createNewFile()
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            file.absolutePath
        } catch (exception: IOException) {
            null
        }
    }

    private fun md5(value: String): String {
        return "asdf"
    }

    private fun generateFile(context: Activity): File {
        val timestamp = System.currentTimeMillis().toString()
        val md5 = md5(timestamp)
        val localFile = getCacheDir(context, "img_cache")
        if (!localFile.exists()) {
            localFile.mkdirs()
        }

        return File(localFile, md5)
    }

    private fun getCacheDir(context: Context, dirName: String): File {
        return File(context.cacheDir, dirName)
    }

    //http://stackoverflow.com/a/9596132/1121509
    fun drawViewToBitmap(view: View, color: Int): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        when {
            bgDrawable != null -> bgDrawable.draw(canvas)
            else -> canvas.drawColor(color)
        }
        view.draw(canvas)
        return returnedBitmap
    }

    fun deleteFile(filename: String): Boolean {
        return File(filename).delete()
    }

    @SuppressLint("NewApi")
    fun fastblur(context: Context, sentBitmap: Bitmap, radius: Float): Bitmap {

        val bitmap = sentBitmap.copy(sentBitmap.config, true)
        val rs = RenderScript.create(context)
        val input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT)
        val output = Allocation.createTyped(rs, input.type)
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        when (radius) {
            in 0f..25f -> script.setRadius(radius)
            else -> script.setRadius(25f)
        }
        script.setInput(input)
        script.forEach(output)
        output.copyTo(bitmap)
        return bitmap
    }

}
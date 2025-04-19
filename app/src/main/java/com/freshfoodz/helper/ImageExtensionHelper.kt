package com.freshfoodz.helper

import android.graphics.*
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.freshfoodz.R
import kotlin.math.min


// circular image with stroke (optional)
fun <T> ImageView.loadCircularStroke(
    model: T,
    borderSize: Float = 0F,
    borderColor: Int = Color.WHITE,
    placeHolderRes: Int = R.drawable.temp_image
) {
    val placeholder = BitmapFactory.decodeResource(context.resources, placeHolderRes)
    val circularBitmapDrawable: RoundedBitmapDrawable =
        RoundedBitmapDrawableFactory.create(context.resources, placeholder)
    circularBitmapDrawable.isCircular = true

    Glide.with(context)
        .asBitmap()
        .load(model)
        .apply(RequestOptions().placeholder(circularBitmapDrawable).error(circularBitmapDrawable))
        .apply(RequestOptions.circleCropTransform())
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(object : BitmapImageViewTarget(this) {
            override fun setResource(resource: Bitmap?) {
                setImageDrawable(
                    resource?.run {
                        RoundedBitmapDrawableFactory.create(
                            resources,
                            if (borderSize > 0) {
                                createBitmapWithBorder(borderSize, borderColor)
                            } else {
                                this
                            }
                        ).apply {
                            isCircular = true
                        }
                    }
                )
            }
        })
}


// stroke
fun Bitmap.createBitmapWithBorder(borderSize: Float, borderColor: Int = Color.WHITE): Bitmap {
    val borderOffset = (borderSize * 2).toInt()
    val halfWidth = width / 2
    val halfHeight = height / 2
    val circleRadius = min(halfWidth, halfHeight).toFloat()
    val newBitmap = Bitmap.createBitmap(
        width + borderOffset,
        height + borderOffset,
        Bitmap.Config.ARGB_8888
    )

    // Center coordinates of the image
    val centerX = halfWidth + borderSize
    val centerY = halfHeight + borderSize

    val paint = Paint()
    val canvas = Canvas(newBitmap).apply {
        // Set transparent initial area
        drawARGB(0, 0, 0, 0)
    }

    // Draw the transparent initial area
    paint.isAntiAlias = true
    paint.style = Paint.Style.FILL
    canvas.drawCircle(centerX, centerY, circleRadius, paint)

    // Draw the image
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, borderSize, borderSize, paint)

    // Draw the createBitmapWithBorder
    paint.xfermode = null
    paint.style = Paint.Style.STROKE
    paint.color = borderColor
    paint.strokeWidth = borderSize
    canvas.drawCircle(centerX, centerY, circleRadius, paint)
    return newBitmap
}


// circular image loader
fun <T> ImageView.loadCircular(
    model: T,
    placeHolderRes: Int = R.drawable.temp_image,
    _width: Int = 300,
    _height: Int = 300
) {
    val placeholder = BitmapFactory.decodeResource(context.resources, placeHolderRes)
    val circularBitmapDrawable: RoundedBitmapDrawable =
        RoundedBitmapDrawableFactory.create(context.resources, placeholder)
    circularBitmapDrawable.isCircular = true

    Glide.with(context)
        .load(model)
        .apply(RequestOptions().placeholder(circularBitmapDrawable).error(circularBitmapDrawable))
        .apply(RequestOptions.circleCropTransform())
        .override(_width, _height)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}


// normal image loader
fun <T> ImageView.load(model: T, placeHolderRes: Int = R.drawable.temp_image) {
    Glide.with(context)
        .load(model)
        .placeholder(placeHolderRes)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}
package rs.sloman.sunshine.adapter

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import rs.sloman.sunshine.Constants
import rs.sloman.sunshine.R
import kotlin.math.roundToInt


@BindingAdapter("bindTextView")
fun bindTextView(textView: TextView, string: String?) {
    textView.text = string
}

@BindingAdapter("bindDoubleTemp")
fun bindDoubleTemp(textView: TextView, double:Double?) {
    textView.text = double?.roundToInt().toString()
}

@BindingAdapter("bindIcon")
fun bindIcon(imageView: ImageView, icon: String?) {
    if (icon == null) return

    if (icon.isNotEmpty()) {
        imageView.visibility = View.VISIBLE
        Glide.with(imageView)
            .load("${Constants.IMAGES_URL}${icon}.png")
            .circleCrop()
            .placeholder(R.drawable.ic_loading_img)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView.apply {
                colorFilter = ColorMatrixColorFilter(ColorMatrix().apply {
                    setSaturation(0F)
                })
            })

    } else {
        imageView.visibility = View.GONE
    }
}
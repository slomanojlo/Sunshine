package rs.sloman.sunshine.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import rs.sloman.sunshine.R
import rs.sloman.sunshine.model.Favorite
import rs.sloman.sunshine.util.Constants
import rs.sloman.sunshine.util.MetricUtil.Companion.symbol
import java.util.*
import kotlin.math.roundToInt


@BindingAdapter("bindTextView")
fun bindTextView(textView: TextView, string: String?) {
    textView.text = string
}

@BindingAdapter("bindIntTextView")
fun bindIntTextView(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("bindFavCity")
fun bindFavCity(textView: TextView, favorite: Favorite?) {

    if (favorite != null) {
        textView.text = "Favorite city : ${favorite.city}"
        textView.visibility = View.VISIBLE
    } else {
        textView.visibility = View.GONE
    }
}

@BindingAdapter("bindTemp")
fun bindTemp(textView: TextView, double: Double?) {
    textView.text = "Temperature: ${double?.roundToInt().toString()}${Locale.getDefault().symbol()}"
}

@BindingAdapter("bindTempMax")
fun bindTempMax(textView: TextView, double: Double?) {
    textView.text = "Temperature max: ${double?.roundToInt().toString()}${Locale.getDefault().symbol()}"
}

@BindingAdapter("bindTempMin")
fun bindTempMin(textView: TextView, double: Double?) {
    textView.text = "Temperature min: ${double?.roundToInt().toString()}${Locale.getDefault().symbol()}"
}

@BindingAdapter("bindFeelsLike")
fun bindFeelsLike(textView: TextView, double: Double?) {
    textView.text = "Feels like temperature: ${double?.roundToInt().toString()}${Locale.getDefault().symbol()}"
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
            .into(imageView)

    } else {
        imageView.visibility = View.GONE
    }
}

@BindingAdapter("bindFav")
fun bindFav(imageView: ImageView, isFavorite: Boolean) {

    Glide.with(imageView)
        .load(if (isFavorite) R.drawable.ic_fav_full else R.drawable.ic_fav_empty)
        .into(imageView)

}

@BindingAdapter("bindListData")
fun bindListData(recyclerView: RecyclerView, favList: List<Favorite>?) {

    recyclerView.visibility =
        if (favList != null && favList.isNotEmpty()) View.VISIBLE else View.INVISIBLE
    val adapter = recyclerView.adapter as FavAdapter
    adapter.submitList(favList)
}
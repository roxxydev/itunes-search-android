package com.itunessearch.android.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.itunessearch.android.R
import com.itunessearch.android.domain.model.Content
import java.text.SimpleDateFormat
import java.util.*

class UiUtil {

    companion object {

        /**
         * Helper method for navigating between fragments in NavigationHost.
         */
        fun navigateTo(navControllerView: View, action: Int, bundle: Bundle?) {
            Navigation
                .findNavController(navControllerView)
                .navigate(action, bundle)
        }

        /**
         * Assign display values to TextViews base from Content model object.
         */
        fun setContentTextValues(
            content: Content,
            tvName: TextView,
            tvArtist: TextView,
            tvGenre: TextView,
            tvReleaseDate: TextView,
            tvPrice: TextView,
            tvDescription: TextView?) {

            tvName.visibility = if (content.trackName.isNullOrEmpty()) View.GONE else View.VISIBLE

            tvName.text = content.trackName
            tvArtist.text = content.artistName

            val kind = content.kind?.let {
                var displayTxt = it.value.replace("_", " ")
                displayTxt = displayTxt.replace("-", " ")
                displayTxt.toUpperCase()
            }
            val genre = content.primaryGenreName
            tvGenre.text = "$kind - $genre"

            content.releaseDate?.let { releasedDate ->
                if (content.releaseDate == null) {
                    tvReleaseDate.visibility = View.GONE
                }
                else {
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    val cal = Calendar.getInstance()
                    cal.timeInMillis = simpleDateFormat.parse(releasedDate).time
                    tvReleaseDate.text = cal.get(Calendar.YEAR).toString()
                    tvReleaseDate.visibility = View.VISIBLE
                }
            }

            val currency = content.currency ?: "AUD"
            val price = content.trackPrice?.let { "%.2f".format(it) } ?: "0"
            tvPrice.text = "$currency $price"

            tvDescription?.let {
                var description = content.longDescription ?: ""
                if (description.isEmpty()) {
                    description = content.shortDescription ?: "..."
                }
                it.text = description
            }
        }

        /**
         * Helper method for displaying and loading image to ImageView using Glide.
         * SwipeRefreshLayout CircularProgressDrawable will be used as placeholder while
         * Material Design progress indicator is not yet supported.
         */
        fun displayImage(context: Context, imgUrl: String?, imageView: ImageView) {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.setColorSchemeColors(
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.colorPrimaryDark))
            circularProgressDrawable.strokeWidth = 10f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .placeholder(circularProgressDrawable)
                .error(ContextCompat.getDrawable(context, R.drawable.ic_img_broken))
                .fallback(ContextCompat.getDrawable(context, R.drawable.ic_img_fallback))
                .into(imageView)
        }
    }
}
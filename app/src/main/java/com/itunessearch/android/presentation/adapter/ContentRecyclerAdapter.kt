package com.itunessearch.android.presentation.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.itunessearch.android.R
import com.itunessearch.android.domain.model.Content
import kotlinx.android.synthetic.main.content_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ContentRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Content> = ArrayList()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.content_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is ContentViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(contentList: List<Content>) {
        items = contentList
        notifyDataSetChanged()
    }

    class ContentViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {

        val ivImage: ImageView = itemView.item_image
        val tvName: TextView = itemView.item_name
        val tvArtist: TextView = itemView.item_artist
        val tvGenre: TextView = itemView.item_genre
        val tvReleaseDate: TextView = itemView.item_release_date
        val tvPrice: TextView = itemView.item_price

        fun bind(content: Content) {
            tvName.text = content.trackName
            tvArtist.text = content.artistName

            val kind = content.kind?.let {
                var displayTxt = it.value.replace("_", " ")
                displayTxt = displayTxt.replace("-", " ")
                displayTxt.toUpperCase()
            }
            val genre = content.primaryGenreName
            tvGenre.text = "$kind - $genre"

            val releasedDate = content.releaseDate
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

            val currency = content.currency ?: "AUD"
            val price = content.trackPrice?.let { "%.2f".format(it) } ?: "0"
            tvPrice.text = "$currency $price"

            Glide.with(itemView.context)
                .load(content.artworkUrl100)
                .transition(DrawableTransitionOptions.withCrossFade(100))
                .into(ivImage)
        }
    }

    class TopSpacingDecoration(private val padding: Int): RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = padding
            }
        }
    }
}
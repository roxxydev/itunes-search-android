package com.itunessearch.android.presentation.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.itunessearch.android.R
import com.itunessearch.android.domain.model.Content
import com.itunessearch.android.presentation.UiUtil
import com.itunessearch.android.presentation.detail.DetailFragment
import kotlinx.android.synthetic.main.content_list_item.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
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
                holder.bind(items[position])
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

        private val card: MaterialCardView = itemView.card;
        private val ivImage: ImageView = itemView.item_image
        private val tvName: TextView = itemView.item_name
        private val tvArtist: TextView = itemView.item_artist
        private val tvGenre: TextView = itemView.item_genre
        private val tvReleaseDate: TextView = itemView.item_release_date
        private val tvPrice: TextView = itemView.item_price

        fun bind(content: Content) {
            UiUtil.setContentTextValues(
                content,
                tvName,
                tvArtist,
                tvGenre,
                tvReleaseDate,
                tvPrice,
                null
            )

            UiUtil.displayImage(itemView.context, content.artworkUrl100, ivImage)

            card.setOnClickListener {
                UiUtil.navigateTo(
                    itemView,
                    R.id.action_mainFragment_to_detailFragment,
                    bundleOf(
                        DetailFragment.ARGS_CONTENT_ID to content.id,
                        DetailFragment.ARGS_CONTENT_TRACKID to content.trackId)
                )
            }
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
package com.example.player.presentation.fragments.player.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.player.R
import com.example.player.databinding.ItemQualityBinding
import com.example.player.domain.data.Quality

open class QualityItemVH(
    private val binding: ItemQualityBinding,
    private val context: Context,
    private val listener: QualityItemsListener,
    private val allCount: Int
): RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun setData(item: Quality) {
        binding.apply {
            if (item.isAuto) {
                tvTitle.text = context.getString(R.string.quality_auto)
            } else {
                tvTitle.text = "${item.height}p"
            }

            root.background = null
            rlItem.setBackgroundResource(R.drawable.ripple_rectangle)
            tvTitle.setTextColor(ContextCompat.getColor(context, R.color.quality_default))

            if (item.selected) {
                tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
                if (allCount > 1) {
                    if (item.index == 0) {
                        root.setBackgroundResource(R.drawable.bg_popup_selected_top)
                        rlItem.setBackgroundResource(R.drawable.ripple_rectangle_top_12)
                    } else if (item.index == allCount - 1) {
                        root.setBackgroundResource(R.drawable.bg_popup_selected_bottom)
                        rlItem.setBackgroundResource(R.drawable.ripple_rectangle_bottom_12)
                    } else {
                        root.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                    }
                } else {
                    root.setBackgroundResource(R.drawable.bg_popup_selected)
                    rlItem.setBackgroundResource(R.drawable.ripple_rectangle_12)
                }
            }

            rlItem.setOnClickListener { listener.changeQuality(item) }
        }
    }
}
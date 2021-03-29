package com.cis4030.pokedex.ui.pokedex_list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * This item decorator adds margins between items in the recyclerview
 * Class taken from Cesar Morigaki at https://cesarmorigaki.medium.com/a-better-way-to-set-recyclerview-items-margin-708ea9d3ac25
 */
class MarginItemDecoration(
    private val spaceSize: Int,
    private val spanCount: Int = 2,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (orientation == GridLayoutManager.VERTICAL) {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    top = spaceSize
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    left = spaceSize
                }
            } else {
                if (parent.getChildAdapterPosition(view) < spanCount) {
                    left = spaceSize
                }
                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
                    top = spaceSize
                }
            }

            right = spaceSize
            bottom = spaceSize
        }
    }
}
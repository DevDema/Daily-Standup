package com.andreadematteis.dailystandup.presentation.meeting.adapters

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MeetingPeopleItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if(position == 0) {
            outRect.left = 60
        }

        if(position == parent.childCount -1) {
            outRect.right = 60
        }

        outRect.right = 20
        outRect.bottom = 20
    }
}
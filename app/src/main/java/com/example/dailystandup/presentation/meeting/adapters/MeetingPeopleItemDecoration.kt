package com.example.dailystandup.presentation.meeting.adapters

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

        outRect.left = 10
        outRect.right = 10

        outRect.top = if (parent.getChildLayoutPosition(view) >= 0) 20 else 0
    }
}
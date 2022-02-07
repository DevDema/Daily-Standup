package com.andreadematteis.dailystandup.presentation.meeting.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.andreadematteis.dailystandup.R
import com.andreadematteis.dailystandup.databinding.LayoutPersonBinding

open class MeetingPersonView(context: Context, attrs: AttributeSet?, defStyle: Int): CardView(context, attrs, defStyle) {
    private lateinit var binding: LayoutPersonBinding

    open var title: CharSequence
        get() = binding.title.text
        set(value) { binding.title.text = value }

    open var subtitle: CharSequence
        get() = binding.subtitle.text
        set(value) { binding.subtitle.text = value }

    constructor(context: Context): this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater)?.let {
            binding = LayoutPersonBinding.inflate(it, this, true)
        }

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MeetingPersonView,
            0, 0).apply {

            try {

                getString(R.styleable.MeetingPersonView_title)?.let { title = it }
                getString(R.styleable.MeetingPersonView_subtitle)?.let { subtitle = it }
                binding.profilePicture.setImageResource(getResourceId(R.styleable.MeetingPersonView_profile_picture, R.mipmap.download))
            } finally {
                recycle()
            }
        }

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    fun setProfilePicture(bitmap: Bitmap?) = bitmap?.let { binding.profilePicture.setImageBitmap(it) }

    fun setProfilePicture(drawable: Drawable?) = drawable?.let { binding.profilePicture.setImageDrawable(it) }
}



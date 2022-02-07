package com.andreadematteis.dailystandup.presentation.meeting.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.andreadematteis.dailystandup.R
import com.andreadematteis.dailystandup.databinding.LayoutPersonSkippedBinding

open class MeetingPersonSkippedView(context: Context, attrs: AttributeSet?, defStyle: Int): CardView(context, attrs, defStyle) {
    private lateinit var binding: LayoutPersonSkippedBinding

    open var title: CharSequence
        get() = binding.title.text
        set(value) { binding.title.text = value }

    open var subtitle: CharSequence
        get() = binding.subtitle.text
        set(value) { binding.subtitle.text = value }

    open var buttonText: CharSequence
        get() = binding.bringBackButton.text
        set(value) { binding.bringBackButton.text = value }

    constructor(context: Context): this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater)?.let {
            binding = LayoutPersonSkippedBinding.inflate(it, this, true)
        }

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MeetingPersonSkippedView,
            0, 0).apply {

            try {
                getString(R.styleable.MeetingPersonSkippedView_title)
                    ?.let { title = it }
                getString(R.styleable.MeetingPersonSkippedView_subtitle)
                    ?.let { subtitle = it }
                getString(R.styleable.MeetingPersonSkippedView_bring_back_button_text)
                    ?.let { buttonText = it }
                binding.profilePicture.setImageResource(getResourceId(R.styleable.MeetingPersonSkippedView_profile_picture, R.mipmap.download))
            } finally {
                recycle()
            }
        }

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    fun setProfilePicture(bitmap: Bitmap?) = bitmap?.let { binding.profilePicture.setImageBitmap(it) }

    fun setProfilePicture(drawable: Drawable?) = drawable?.let { binding.profilePicture.setImageDrawable(it) }

    fun setOnClickBringBackListener(onClickListener: OnClickListener) =
        binding.bringBackButton.setOnClickListener(onClickListener)

    fun setButtonEnabled(enabled: Boolean) {
        binding.bringBackButton.isEnabled = enabled
    }
}



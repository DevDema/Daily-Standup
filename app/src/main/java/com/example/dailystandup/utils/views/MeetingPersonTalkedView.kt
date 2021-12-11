package com.example.dailystandup.utils.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.dailystandup.R
import com.example.dailystandup.databinding.LayoutPersonTalkedBinding
import com.example.dailystandup.utils.toHHmmssFormat

open class MeetingPersonTalkedView(context: Context, attrs: AttributeSet?, defStyle: Int): CardView(context, attrs, defStyle) {
    private lateinit var binding: LayoutPersonTalkedBinding

    open var title: CharSequence
        get() = binding.title.text
        set(value) { binding.title.text = value }

    open var subtitle: CharSequence
        get() = binding.subtitle.text
        set(value) { binding.subtitle.text = value }

    open var elapsedTimeText: CharSequence
        get() = binding.timer.text
        set(value) { binding.timer.text = value }

    var elapsedSeconds: Int = 0
        private set

    constructor(context: Context): this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater)?.let {
            binding = LayoutPersonTalkedBinding.inflate(it, this, true)
        }

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MeetingPersonTalkedView,
            0, 0).apply {

            try {
                getString(R.styleable.MeetingPersonTalkedView_title)
                    ?.let { title = it }
                getString(R.styleable.MeetingPersonTalkedView_subtitle)
                    ?.let { subtitle = it }
                binding.profilePicture.setImageResource(
                    getResourceId(
                        R.styleable.MeetingPersonTalkedView_profile_picture,
                        R.mipmap.download)
                )
            } finally {
                recycle()
            }
        }

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    fun setElapsedTime(elapsedSeconds: Int) {
        this.elapsedSeconds = elapsedSeconds

        binding.timer.text = elapsedSeconds.toHHmmssFormat()
    }

    fun setProfilePicture(bitmap: Bitmap?) = bitmap?.let { binding.profilePicture.setImageBitmap(it) }

    fun setProfilePicture(drawable: Drawable?) = drawable?.let { binding.profilePicture.setImageDrawable(it) }


}



package com.example.dailystandup.utils.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.dailystandup.R
import com.example.dailystandup.databinding.LayoutPersonActiveBinding

class MeetingPersonActiveView(context: Context, attrs: AttributeSet?, defStyle: Int): CardView(context, attrs, defStyle) {

    private lateinit var binding: LayoutPersonActiveBinding

    var title: CharSequence
        get() = binding.title.text
        set(value) { binding.title.text = value }

    var subtitle: CharSequence
        get() = binding.subtitle.text
        set(value) { binding.subtitle.text = value }

    var elapsedTimeText: CharSequence
        get() = binding.timer.text
        set(value) { binding.timer.text = value }

    var nextEndButtonText: CharSequence
        get() = binding.nextEndButton.text
        set(value) { binding.nextEndButton.text = value }

    var skipButtonText: CharSequence
        get() = binding.skipButton.text
        set(value) { binding.skipButton.text = value }

    var tasksButtonText: CharSequence
        get() = binding.tasksButton.text
        set(value) { binding.tasksButton.text = value }

    var elapsedSeconds: Int = 0

    constructor(context: Context): this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater)?.let {
            binding = LayoutPersonActiveBinding.inflate(it, this, true)
        }

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MeetingPersonActiveView,
            0, 0
        ).apply {
            getString(R.styleable.MeetingPersonActiveView_next_button_text)?.let { nextEndButtonText = it }
            getString(R.styleable.MeetingPersonActiveView_skip_button_text)?.let { skipButtonText = it }
            getString(R.styleable.MeetingPersonActiveView_tasks_button_text)?.let { tasksButtonText = it }
            getString(R.styleable.MeetingPersonActiveView_title)?.let { title = it }
            getString(R.styleable.MeetingPersonActiveView_subtitle)?.let { subtitle = it }
            binding.profilePicture.setImageResource(getResourceId(R.styleable.MeetingPersonActiveView_profile_picture, R.mipmap.download))
        }

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }

    fun setProfilePicture(bitmap: Bitmap?) = bitmap?.let { binding.profilePicture.setImageBitmap(it) }

    fun setProfilePicture(drawable: Drawable?) = drawable?.let { binding.profilePicture.setImageDrawable(it) }
    fun setSkippedButtonEnabled(boolean: Boolean) {
        binding.skipButton.isEnabled = boolean
    }

    fun setOnClickNextListener(onClickListener: OnClickListener) =
        binding.nextEndButton.setOnClickListener(onClickListener)

    fun setOnClickSkipListener(onClickListener: OnClickListener) =
        binding.skipButton.setOnClickListener(onClickListener)

    fun setOnClickTasksListener(onClickListener: OnClickListener) =
        binding.tasksButton.setOnClickListener(onClickListener)
}
package com.example.dailystandup

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailystandup.databinding.ActivityMeetingBinding
import com.example.dailystandup.data.local.model.TeamMember
import com.example.dailystandup.utils.adapters.*
import com.example.dailystandup.utils.toHHmmssFormat

class DailyStandupActivity : AppCompatActivity(),
    ActivePeopleAdapterCallback,
    SkippedPeopleAdapterCallback, SecondIntervalListener {

    private var totalElapsedSeconds = 0

    private val meetingTimer = MeetingTimer().apply {
        registerListener(this@DailyStandupActivity)
    }

    private lateinit var binding: ActivityMeetingBinding
    private val defaultScrollListener =
        View.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            val dy = scrollY - oldScrollY

            if (dy > 0) {
                collapseOverlay()
            } else {
                expandOverlay()
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeetingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setMockAdapters()

        binding.scrollView.setOnScrollChangeListener(defaultScrollListener)
        binding.collapseOverlayButton.setImageDrawable(
            ContextCompat.getDrawable(this,
                R.drawable.ic_baseline_keyboard_arrow_down_24)
        )
        binding.collapseOverlayButton.setOnClickListener { collapseOverlay() }

        meetingTimer.start()

        setNoSkippedUsers()
    }

    private fun setMockAdapters() {
        val personList = mutableListOf(
            TeamMember("Deborah", "Santucci", "Frontend"),
            TeamMember("Andrea", "De Matteis", "Mobile"),
            TeamMember("Coglione", "De Muraris", "Frontend"),
            TeamMember("Eva", "Baldabocchini", "Frontend"),
            TeamMember("Matteo", "Maisia", "Backend"),
            TeamMember("Matteo", "Mistura", "Backend"),
            TeamMember("Paolo", "Andrea", "Backend"),
            TeamMember("Silvio", "Villani", "Backend")
        )

        binding.recyclerViewActive.layoutManager = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (position == 0 ||
                    binding.recyclerViewActive.adapter!!.getItemViewType(position) == MeetingPeopleTalkingAdapter.MeetingPersonViewType.MORE.ordinal
                ) 2 else 1
            }
        }

        binding.recyclerViewActive.addItemDecoration(MeetingPeopleItemDecoration())
        binding.recyclerViewActive.adapter = MeetingPeopleTalkingAdapter(this, this, personList)

        binding.recyclerViewTalked.addItemDecoration(MeetingPeopleTalkedItemDecoration())
        binding.recyclerViewTalked.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTalked.adapter = MeetingPeopleTalkedAdapter(this, mutableListOf())

        binding.recyclerViewSkipped.addItemDecoration(MeetingPeopleTalkedItemDecoration())
        binding.recyclerViewSkipped.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSkipped.adapter = MeetingPeopleSkippedAdapter(this, this, mutableListOf())
    }

    override fun onTalked(teamMember: TeamMember, elapsedTalking: Int) {
        binding.talkedLabel.visibility = View.VISIBLE
        binding.talkedDivider.visibility = View.VISIBLE

        (binding.recyclerViewTalked.adapter as? MeetingPeopleTalkedAdapter)?.add(teamMember, elapsedTalking)
    }

    override fun onSkipped(teamMember: TeamMember) {
        setSkippedUsers()
        (binding.recyclerViewSkipped.adapter as? MeetingPeopleSkippedAdapter)?.add(teamMember)
    }

    override fun onCollapsed() {
        binding.scrollView.setOnScrollChangeListener(defaultScrollListener)
    }

    override fun registerSecondIntervalListener(listener: SecondIntervalListener) {
        meetingTimer.registerListener(listener)
    }

    override fun unregisterSecondIntervalListener(listener: SecondIntervalListener) {
        meetingTimer.unregisterListener(listener)
    }

    override fun onExpanded() {
        binding.scrollView.setOnScrollChangeListener { v, x, y, oldX, oldY ->
            defaultScrollListener.onScrollChange(v, x, y, oldX, oldY)

            if (!binding.scrollView.canScrollVertically(-1)) {
                (binding.recyclerViewActive.adapter as? MeetingPeopleTalkingAdapter)?.collapse()
            }
        }
    }

    override fun onEnd() {
        meetingTimer.stop()

        binding.collapseOverlayButton.visibility = View.INVISIBLE
        binding.collapseOverlayButton.isClickable = false
        binding.leaveMeetingButton.visibility = View.INVISIBLE
        binding.finishedCardView.visibility = View.VISIBLE

        binding.finishedCardView.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if(binding.finishedCardView.height > 0 || binding.finishedCardView.width > 0) {
                    val slideDown = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down)
                    binding.finishedCardView.startAnimation(slideDown)
                }
            }
        })

        collapseOverlay()
        binding.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->  }
    }

    override fun onBroughtBack(teamMember: TeamMember) {
        (binding.recyclerViewActive.adapter as? MeetingPeopleTalkingAdapter)?.add(teamMember)

        if((binding.recyclerViewSkipped.adapter as? MeetingPeopleSkippedAdapter)?.dataSet?.isEmpty() == true) {
            setNoSkippedUsers()
        }
    }

    private fun collapseOverlay() {
        binding.overlay.animate()
            .setStartDelay(0)
            .setDuration(200L)
            .translationY((binding.overlay.height - binding.timer.height - binding.timer.marginTop - binding.timer.marginBottom).toFloat())
            .withEndAction {
                binding.collapseOverlayButton.setImageDrawable(
                    ContextCompat.getDrawable(this,
                        R.drawable.ic_baseline_keyboard_arrow_up_24))
                binding.collapseOverlayButton.setOnClickListener { expandOverlay() }
            }
    }

    private fun expandOverlay() {
        binding.overlay.animate()
            .setStartDelay(0)
            .setDuration(200L)
            .translationY(0F)
            .withEndAction {
                binding.collapseOverlayButton.setImageDrawable(
                    ContextCompat.getDrawable(this,
                        R.drawable.ic_baseline_keyboard_arrow_down_24)
                )

                binding.collapseOverlayButton.setOnClickListener { collapseOverlay() }
            }
    }

    private fun setNoSkippedUsers() {
        binding.noSkippedUsers.visibility = View.VISIBLE
        binding.recyclerViewSkipped.visibility = View.INVISIBLE
    }

    private fun setSkippedUsers() {
        binding.noSkippedUsers.visibility = View.INVISIBLE
        binding.recyclerViewSkipped.visibility = View.VISIBLE
    }

    override fun onSecondPassed() {
        totalElapsedSeconds++
        binding.timer.text = totalElapsedSeconds.toHHmmssFormat()
    }

    override fun onFinish() {
        //Do nothing
    }
}
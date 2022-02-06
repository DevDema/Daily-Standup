package com.example.dailystandup.presentation.meeting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dailystandup.ActivePeopleAdapterCallback
import com.example.dailystandup.R
import com.example.dailystandup.SkippedPeopleAdapterCallback
import com.example.dailystandup.data.local.model.TeamMember
import com.example.dailystandup.databinding.FragmentMeetingBinding
import com.example.dailystandup.presentation.meeting.adapters.*
import com.example.dailystandup.utils.toHHmmssFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeetingFragment : Fragment(), ActivePeopleAdapterCallback, SkippedPeopleAdapterCallback {
    private lateinit var binding: FragmentMeetingBinding
    private val viewModel: MeetingViewModel by viewModels()

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

        viewModel.loadMeeting()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMeetingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrollView.setOnScrollChangeListener(defaultScrollListener)
        binding.collapseOverlayButton.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_keyboard_arrow_down_24
            )
        )
        binding.collapseOverlayButton.setOnClickListener { collapseOverlay() }

        binding.recyclerViewActive.layoutManager = GridLayoutManager(requireContext(), 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = if (position == 0 ||
                    binding.recyclerViewActive.adapter!!.getItemViewType(position) == MeetingPeopleTalkingAdapter.MeetingPersonViewType.MORE.ordinal
                ) 2 else 1
            }
        }
        binding.recyclerViewActive.addItemDecoration(MeetingPeopleItemDecoration())

        binding.recyclerViewTalked.addItemDecoration(MeetingPeopleTalkedItemDecoration())
        binding.recyclerViewTalked.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewTalked.adapter =
            MeetingPeopleTalkedAdapter(requireContext(), mutableListOf())

        binding.recyclerViewSkipped.addItemDecoration(MeetingPeopleTalkedItemDecoration())
        binding.recyclerViewSkipped.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewSkipped.adapter =
            MeetingPeopleSkippedAdapter(requireContext(), this, mutableListOf())

        binding.noSkippedUsers.visibility = View.VISIBLE
        binding.recyclerViewSkipped.visibility = View.INVISIBLE

        viewModel.secondPassed.observe(viewLifecycleOwner) {
            binding.timer.text = it.toHHmmssFormat()
        }

        viewModel.teamMembers.observe(viewLifecycleOwner) { listWrapper ->
            val grouped = listWrapper.groupBy { it.status }

            val talkingList = grouped[TeamMemberStatus.TALKING] ?: emptyList()
            val comingList = grouped[TeamMemberStatus.COMING] ?: emptyList()

            (talkingList + comingList).let {
                binding.recyclerViewActive.adapter =
                    MeetingPeopleTalkingAdapter(
                        requireContext(),
                        this,
                        it.toMutableList()
                )
            }

            val talkedList = grouped[TeamMemberStatus.TALKED] ?: emptyList()

            binding.talkedLabel.visibility =
                if (talkedList.isEmpty()) View.GONE else View.VISIBLE
            binding.talkedDivider.visibility =
                if (talkedList.isEmpty()) View.GONE else View.VISIBLE

            (binding.recyclerViewTalked.adapter as MeetingPeopleTalkedAdapter).handle(
                talkedList
            )

            val skippedList = grouped[TeamMemberStatus.SKIPPED] ?: emptyList()

            binding.noSkippedUsers.visibility =
                if (skippedList.isEmpty()) View.VISIBLE else View.INVISIBLE
            binding.recyclerViewSkipped.visibility =
                if (skippedList.isEmpty()) View.INVISIBLE else View.VISIBLE

            (binding.recyclerViewSkipped.adapter as MeetingPeopleSkippedAdapter).handle(skippedList)
        }
    }

    override fun onTalked(teamMember: TeamMember, elapsedTalking: Long) {
        viewModel.setTalked(teamMember, elapsedTalking)
    }

    override fun onSkipped(teamMember: TeamMember) {
        viewModel.setSkipped(teamMember)
    }

    override fun onBroughtBack(teamMember: TeamMember) {
        viewModel.setTalking(teamMember)
    }

    override fun onCollapsed() {
        binding.scrollView.setOnScrollChangeListener(defaultScrollListener)
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
        binding.collapseOverlayButton.visibility = View.INVISIBLE
        binding.collapseOverlayButton.isClickable = false
        binding.leaveMeetingButton.visibility = View.INVISIBLE
        binding.finishedCardView.visibility = View.VISIBLE

        binding.finishedCardView.viewTreeObserver.addOnGlobalLayoutListener {
            if (binding.finishedCardView.height > 0 || binding.finishedCardView.width > 0) {
                val slideDown = AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.slide_down
                )
                binding.finishedCardView.startAnimation(slideDown)
            }
        }

        collapseOverlay()
        binding.scrollView.setOnScrollChangeListener { _, _, _, _, _ -> }
    }

    private fun collapseOverlay() {
        binding.overlay.animate()
            .setStartDelay(0)
            .setDuration(200L)
            .translationY((binding.overlay.height - binding.timer.height - binding.timer.marginTop - binding.timer.marginBottom).toFloat())
            .withEndAction {
                binding.collapseOverlayButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_keyboard_arrow_up_24
                    )
                )
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
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_keyboard_arrow_down_24
                    )
                )

                binding.collapseOverlayButton.setOnClickListener { collapseOverlay() }
            }
    }
}
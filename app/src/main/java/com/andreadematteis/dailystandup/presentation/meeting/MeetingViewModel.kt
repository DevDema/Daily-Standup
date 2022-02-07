package com.andreadematteis.dailystandup.presentation.meeting

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreadematteis.dailystandup.data.local.model.Meeting
import com.andreadematteis.dailystandup.data.local.model.TeamMember
import com.andreadematteis.dailystandup.domain.usecase.CloseMeetingUseCase
import com.andreadematteis.dailystandup.domain.usecase.LoadMeetingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingViewModel @Inject constructor(
    private val loadMeetingUseCase: LoadMeetingUseCase,
    private val closeMeetingUseCase: CloseMeetingUseCase
) : ViewModel() {

    private lateinit var teamMemberList: MutableList<TeamMemberWrapper>

    private val _meeting: MutableLiveData<Meeting> by lazy {
        MutableLiveData<Meeting>()
    }
    private val _teamMembers: MutableLiveData<List<TeamMemberWrapper>> by lazy {
        MutableLiveData<List<TeamMemberWrapper>>()
    }
    private val _secondPassed: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val teamMembers: LiveData<List<TeamMemberWrapper>>
        get() = _teamMembers
    val secondPassed: LiveData<Long>
        get() = _secondPassed
    val meeting: LiveData<Meeting>
        get() = _meeting

    private val countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            if (teamMemberList.none { it.status == TeamMemberStatus.TALKING }) {
                cancel()
                return
            }

            val secondElapsed = Long.MAX_VALUE - millisUntilFinished
            _secondPassed.postValue(secondElapsed)

            val newList = teamMemberList.map { it.copy() }

            newList.firstOrNull { it.status == TeamMemberStatus.TALKING }
                ?.run {
                    timeMillis = secondElapsed -
                            newList.filter { it.status == TeamMemberStatus.TALKED
                                    || it.status == TeamMemberStatus.SKIPPED }
                                .sumOf { it.timeMillis }
                }

            _teamMembers.postValue(newList)
        }

        override fun onFinish() {
            // Do nothing
        }
    }

    fun loadMeeting() {
        if (::teamMemberList.isInitialized) {
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            loadMeetingUseCase.execute(1).let { meetingTeamMemberWrapper ->
                val list = meetingTeamMemberWrapper.memberWrapperList.toMutableList()
                teamMemberList = list

                _teamMembers.postValue(list)

                _meeting.postValue(
                    meetingTeamMemberWrapper.meeting
                )
            }
            countDownTimer.start()
        }
    }

    fun closeMeeting() = viewModelScope.launch(Dispatchers.IO) {
        closeMeetingUseCase.execute()
        countDownTimer.cancel()
    }

    fun setTalked(teamMember: TeamMember, elapsedTalking: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            teamMemberList
                .first { teamMember.id == it.member.id }
                .run {
                    timeMillis = elapsedTalking
                    status = TeamMemberStatus.TALKED
                }

            teamMemberList.firstOrNull {
                it.status == TeamMemberStatus.COMING
            }?.status = TeamMemberStatus.TALKING

            _teamMembers.postValue(teamMemberList)
        }

    fun setSkipped(teamMember: TeamMember, elapsedTalking: Long) = viewModelScope.launch(Dispatchers.IO) {
        teamMemberList
            .first { teamMember.id == it.member.id }
            .run {
                status = TeamMemberStatus.SKIPPED
                timeMillis = elapsedTalking

            }

        teamMemberList.firstOrNull {
            it.status == TeamMemberStatus.COMING
        }?.status = TeamMemberStatus.TALKING


        _teamMembers.postValue(teamMemberList)
    }

    fun setTalking(teamMember: TeamMember) = viewModelScope.launch(Dispatchers.IO) {
        val indexBroughtBack = teamMemberList
            .indexOfFirst { teamMember.id == it.member.id }
        val wrapper = teamMemberList[indexBroughtBack]

        teamMemberList.removeAt(indexBroughtBack)
        teamMemberList.add(wrapper.apply {
            status = TeamMemberStatus.COMING
        })

        _teamMembers.postValue(teamMemberList)
    }
}
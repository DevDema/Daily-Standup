package com.example.dailystandup.presentation.meeting

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailystandup.data.local.model.TeamMember
import com.example.dailystandup.domain.usecase.CloseMeetingUseCase
import com.example.dailystandup.domain.usecase.LoadMeetingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MeetingViewModel(
    private val loadMeetingUseCase: LoadMeetingUseCase,
    private val closeMeetingUseCase: CloseMeetingUseCase
) : ViewModel() {

    private val _teamMembers: MutableLiveData<List<TeamMemberWrapper>> by lazy {
        MutableLiveData<List<TeamMemberWrapper>>()
    }

    private val _secondPassed: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    private val countDownTimer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _secondPassed.postValue(Long.MAX_VALUE - millisUntilFinished)
        }

        override fun onFinish() {
            // Do nothing
        }
    }

    val teamMembers: LiveData<List<TeamMemberWrapper>>
        get() = _teamMembers
    val secondPassed: LiveData<Long>
        get() = _secondPassed

    fun loadMeeting() = viewModelScope.launch(Dispatchers.IO) {
        loadMeetingUseCase.execute().let { list ->
            _teamMembers.postValue(
                list.mapIndexed { index, member ->
                    TeamMemberWrapper(
                        member,
                        if (index == 0) TeamMemberStatus.TALKING else TeamMemberStatus.COMING
                    )
                }
            )
        }
        countDownTimer.start()
    }

    fun closeMeeting() = viewModelScope.launch(Dispatchers.IO) {
        closeMeetingUseCase.execute()
        countDownTimer.cancel()
    }

    fun setTalked(teamMember: TeamMember, elapsedTalking: Int) {
        TODO("Not yet implemented")
    }

    fun setSkipped(teamMember: TeamMember) {
        TODO("Not yet implemented")
    }

    fun setTalking(teamMember: TeamMember) {
        TODO("Not yet implemented")
    }
}
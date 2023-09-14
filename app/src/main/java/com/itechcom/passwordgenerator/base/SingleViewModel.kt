package com.itechcom.passwordgenerator.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itechcom.passwordgenerator.storage.room.RoomDBManager
import com.itechcom.passwordgenerator.storage.room.UserEntity
import com.itechcom.passwordgenerator.storage.sharedPref.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleViewModel @Inject constructor(
    private val sharedPrefManager: SharedPrefManager,
    private val roomDBManager: RoomDBManager
    ) : ViewModel() {

    private val _allData = MutableStateFlow<List<UserEntity>?>(null)
    val allData : SharedFlow<List<UserEntity>?> = _allData

    fun getSharedPrefManager() = sharedPrefManager
    fun getRoomManager() = roomDBManager

    fun getAllData() = viewModelScope.launch {
       _allData.value = roomDBManager.getAllData()
    }

}
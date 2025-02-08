package com.jp.chatapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.chatapp.domain.models.user2.User
import com.jp.chatapp.domain.repo.APIDataRepo
import com.jp.chatapp.domain.state.ResultState
import com.jp.chatapp.data.dataStore.DataStorePref
import com.jp.chatapp.domain.repo.DataStore
import com.jp.chatapp.utils.ACCESS_TOKEN
import com.jp.chatapp.utils.REFRESH_TOKEN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewmodel(
    private val apiDataRepo: APIDataRepo,
    private  val dataStore: DataStore
) : ViewModel() {

    private val _user = MutableStateFlow<UserState>(UserState())
    val user = _user.asStateFlow()
    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private val _bio = MutableStateFlow("")
    val bio = _bio.asStateFlow()
    fun updatePhone(value: String) {
        _phone.value = value
    }

    fun updateBio(value: String) {
        _bio.value = value
    }


    fun register(file: ByteArray) {
        viewModelScope.launch {
            apiDataRepo.register(phone.value, bio = bio.value, file = file)
                .collectLatest { it: ResultState<User> ->

                    println("Log : $it")
                    when (it) {
                        is ResultState.Error -> {
                            _user.value = UserState(error = it.msg, data = null)
                        }

                        ResultState.Loading -> {
                            _user.value = UserState(isLoading = true)
                        }

                        is ResultState.Success -> {
                            _user.value = UserState(data = it.data)

                            println("Log : Data ${_user.value}")
                            saveToken(ACCESS_TOKEN, it.data.data.accessToken)
                            saveToken(REFRESH_TOKEN, it.data.data.refreshtoken)
                        }
                    }
                }
        }
    }

    fun login() {
        viewModelScope.launch {
            apiDataRepo.login(phone.value).collectLatest {
                when (it) {
                    is ResultState.Error -> {
                        _user.value = UserState(error = it.msg)
                    }

                    ResultState.Loading -> {
                        _user.value = UserState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        println("RES LOgin ${it.data}")

                            _user.value = UserState(data = it.data)

                            saveToken(ACCESS_TOKEN, it.data.data.accessToken)
                            saveToken(REFRESH_TOKEN, it.data.data.refreshtoken)


                    }
                }
            }
        }
    }

     private fun saveToken(key: String, value: String) {
        viewModelScope.launch {
            dataStore.setToken(key, value)
        }
    }




}

data class UserState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: User? = null
)
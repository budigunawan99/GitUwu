package com.bnawan.gituwu.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bnawan.gituwu.data.ApiConfig
import com.bnawan.gituwu.model.ResponseHandler
import com.bnawan.gituwu.model.User
import com.bnawan.gituwu.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    username: String
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _responseHandler = MutableLiveData<Event<ResponseHandler>>()
    val responseHandler: LiveData<Event<ResponseHandler>> = _responseHandler

    init {
        findUser(username)
    }

    private fun findUser(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUser(username)

        client.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _user.value = response.body()
                    _responseHandler.value = Event(ResponseHandler(true, response.message()))
                } else {
                    _responseHandler.value = Event(ResponseHandler(false, response.message()))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                _isLoading.value = false
                _responseHandler.value = Event(ResponseHandler(false, t.message.toString()))
            }

        })
    }
}
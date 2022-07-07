package com.bnawan.gituwu.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bnawan.gituwu.data.ApiConfig
import com.bnawan.gituwu.model.ResponseHandler
import com.bnawan.gituwu.model.SearchResponse
import com.bnawan.gituwu.model.User
import com.bnawan.gituwu.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listUser = MutableLiveData<List<User>>()
    val listUser: LiveData<List<User>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _responseHandler = MutableLiveData<Event<ResponseHandler>>()
    val responseHandler: LiveData<Event<ResponseHandler>> = _responseHandler

    fun searchUser(query: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listUser.value = response.body()?.items
                    if (_listUser.value.isNullOrEmpty()) {
                        _responseHandler.value = Event(ResponseHandler(false, MESSAGE_NOT_FOUND))
                    } else {
                        _responseHandler.value = Event(ResponseHandler(true, response.message()))
                    }
                } else {
                    _responseHandler.value = Event(ResponseHandler(false, response.message()))
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _isLoading.value = false
                _responseHandler.value = Event(ResponseHandler(false, t.message.toString()))
            }

        })
    }

    companion object {
        const val MESSAGE_NOT_FOUND = "User Not Found!"
    }
}
package com.example.github.UI

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.data.response.DetailUsersResponse
import com.example.github.data.response.UsersFollowingResponse
import com.example.github.data.response.UsersFollowingResponseItem
import com.example.github.data.response.UsersResponse
import com.example.github.data.response.UsersResponseItem
import com.example.github.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _usersItem = MutableLiveData<List<UsersResponseItem>>()
    val userItem: LiveData<List<UsersResponseItem>> = _usersItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<DetailUsersResponse>()
    val userDetail: LiveData<DetailUsersResponse> = _userDetail

    private val _followerItem = MutableLiveData<List<UsersFollowingResponseItem>>()
    val followerItem: LiveData<List<UsersFollowingResponseItem>> = _followerItem

    companion object {
        private const val TAG = "MainViewModel"
        }

    init {
        getUser()
    }

    fun getUserFollowing(userName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(userName)
        client.enqueue(object: Callback<List<UsersFollowingResponseItem>>{
            override fun onResponse(
                call: Call<List<UsersFollowingResponseItem>>,
                response: Response<List<UsersFollowingResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followerItem.value = response.body()
                } else {
                    Log.e(TAG, "onFailureUserFollowing: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersFollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailureUserFollowing: ${t.message.toString()}")
            }
        })
    }

    fun fetchUserDetail(userName: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().findUser(userName)
        client.enqueue(object: Callback<DetailUsersResponse>{
            override fun onResponse(
                call: Call<DetailUsersResponse>,
                response: Response<DetailUsersResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userDetailResponse = response.body()
                    if (userDetailResponse != null) {
                        _userDetail.value = userDetailResponse
                    } else {
                        Log.e(TAG, "fetchUserDetail: Response body is null")
                    }
                } else {
                    Log.e(TAG, "fetchUserDetail: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUsersResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "fetchUserDetail: ${t.message.toString()}")
            }

        })
    }

    fun getUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser()
        client.enqueue(object : Callback<List<UsersResponseItem>> {
            override fun onResponse(
                call: Call<List<UsersResponseItem>>,
                response: Response<List<UsersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val usersList = response.body()
                    if (usersList != null) {
                        _usersItem.value = usersList
                    } else {
                        Log.e(TAG, "onResponse: Response body is null")
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UsersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}", t)
            }
        })
    }
}
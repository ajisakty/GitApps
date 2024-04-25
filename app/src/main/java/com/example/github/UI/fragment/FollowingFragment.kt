package com.example.github.UI.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.UI.FollowingAdapter
import com.example.github.UI.MainViewModel
import com.example.github.data.response.UsersFollowingResponseItem
import com.example.github.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    companion object {
        val TAG = "FollowingFragment"
    }

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var followingAdapter: FollowingAdapter
    private var loginUser: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        followingAdapter = FollowingAdapter()
        binding.rvFollowing.adapter = followingAdapter
        binding.rvFollowing.layoutManager = LinearLayoutManager(requireContext())

        loginUser = arguments?.getString("login")
        if (!loginUser.isNullOrEmpty()) {
            viewModel.getUserFollowing(loginUser!!)
        } else {
            Log.e(TAG, "Error Get LoginUser From Main Activity: Login User = $loginUser")
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.followerItem.observe(viewLifecycleOwner, { userFollowers ->
            userFollowers?.let {
                try {
                    followingAdapter.submitList(it)
                } catch (e: Exception) {
                Log.e(TAG, "Error submitting list to adapter: ${e.message}")
            }
            }
        })
    }
}

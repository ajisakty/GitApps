package com.example.github.UI

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.github.R
import com.example.github.data.response.DetailUsersResponse
import com.example.github.data.response.UserDetailResponse
import com.example.github.data.response.UsersResponseItem
import com.example.github.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.follower_fragment,
            R.string.following_fragment
        )
    }

    private lateinit var detailUserActivityBinding : ActivityDetailUserBinding
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        detailUserActivityBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailUserActivityBinding.root)

        mainViewModel.userDetail.observe(this, {userDetail ->
            userDetail?.let{
                displayUserDetail(it)
            }
        })
        val loginUser = intent.getStringExtra("login")
        if (!loginUser.isNullOrEmpty()) {
            mainViewModel.fetchUserDetail(loginUser)
        }

        //TAB LAYOUT
        val sectionPageAdapter = SectionPageAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = findViewById(R.id.tabLayout)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun displayUserDetail(userDetail: DetailUsersResponse) {
        detailUserActivityBinding.apply {
            Glide.with(detailUserActivityBinding.ivUserDetail)
                .load(userDetail.avatarUrl)
                .into(detailUserActivityBinding.ivUserDetail)
            tvUserNameDetail.text = userDetail.name
            tvCountFollowers.text = userDetail.followers.toString()
            tvCountFollowing.text = userDetail.following.toString()
        }
    }

}
package com.example.github.UI

import UsersAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.UI.fragment.FollowingFragment
import com.example.github.data.response.UsersResponseItem
import com.example.github.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        enableEdgeToEdge()

        val recylerView: RecyclerView = findViewById(R.id.rv_githubUsers)
        val layoutManager = LinearLayoutManager(this)
        val getUserLogin = intent.getStringExtra("login")

        recylerView.layoutManager = layoutManager


        ViewCompat.setOnApplyWindowInsetsListener(activityMainBinding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(activityMainBinding){
            searchView.setupWithSearchBar(searchBar)
        }


        val adapter = UsersAdapter{user ->
            navigateToUserDetail(user)
        }

        if (!getUserLogin.isNullOrEmpty()) {
            mainViewModel.fetchUserDetail(getUserLogin)
        }


        activityMainBinding.rvGithubUsers.adapter = adapter
        recylerView.adapter = adapter


        mainViewModel.userItem.observe(this, Observer { userItem ->
            userItem?.let {
                setUsersData(it)
            }
        })


        mainViewModel.isLoading.observe(this, Observer { isLoading ->
            showLoading(isLoading)
        })


        mainViewModel.getUser()
    }

    private fun setUsersData(getUsers: List<UsersResponseItem>) {
        val adapter = activityMainBinding.rvGithubUsers.adapter as UsersAdapter
        adapter.submitList(getUsers)
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.progresBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun enableEdgeToEdge() {
    }

    private fun navigateToUserDetail(user: UsersResponseItem){
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra("login", user.login)
        val bundle = Bundle()
        val fragment = FollowingFragment()
        bundle.putString("login", user.login)
        fragment.arguments = bundle
        startActivity(intent)
    }
}

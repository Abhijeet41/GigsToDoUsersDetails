package com.abhi41.bookdetails.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.abhi41.bookdetails.R
import com.abhi41.bookdetails.adapters.UsersAdapter
import com.abhi41.bookdetails.databinding.ActivityUsersListBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UsersList : AppCompatActivity() {
    lateinit var binding: ActivityUsersListBinding
    val mainViewModel by viewModels<MainViewModel>()
    private val mUserAdapter by lazy { UsersAdapter(mainViewModel,application) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users_list)

        setupRecyclerView()
        observers()
    }

    private fun observers() {
        mainViewModel.readUsers.observe(this) { users ->
            users?.let { mUserAdapter.setData(users) }
        }
    }

    private fun setupRecyclerView() {
        binding.rvUser.adapter = mUserAdapter
        binding.rvUser.layoutManager = LinearLayoutManager(applicationContext)
        val itemDecoration: ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rvUser.addItemDecoration(itemDecoration)
    }
}
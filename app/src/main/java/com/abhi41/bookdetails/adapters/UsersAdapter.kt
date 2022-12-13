package com.abhi41.bookdetails.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abhi41.bookdetails.databinding.SingleUserItemBinding
import com.abhi41.bookdetails.local.UserEntity
import com.abhi41.bookdetails.presentation.MainViewModel
import com.abhi41.bookdetails.presentation.UpdateUsers
import com.abhi41.bookdetails.util.Constants
import com.abhi41.bookdetails.util.UsersDiffUtils

class UsersAdapter(
    val mainViewModel: MainViewModel,
    val context: Context
) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    private val TAG = "UsersAdapter"
    private var users = emptyList<UserEntity>()

    class MyViewHolder(val binding: SingleUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity, mainViewModel: MainViewModel) {
            binding.user = user
            binding.executePendingBindings()//this will update our layout if there is any change in data
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleUserItemBinding.inflate(layoutInflater, parent, false)

                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = users[position]
        holder.bind(currentUser, mainViewModel)
        holder.binding.imgDeleteUser.setOnClickListener {
            mainViewModel.deleteUser(userEntity = currentUser)
            notifyItemRemoved(position)
        }
        holder.binding.imgEditUser.setOnClickListener {
            val intent = Intent(context, UpdateUsers::class.java)
            intent.putExtra(Constants.SEND_USER, currentUser)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setData(newData: List<UserEntity>) {
        val usersDiffUtil = UsersDiffUtils(users, newData)
        val diffUtilResult = DiffUtil.calculateDiff(usersDiffUtil)
        users = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
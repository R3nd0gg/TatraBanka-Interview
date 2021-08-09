package sk.tatrabanka.masarykapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sk.tatrabanka.masarykapp.adapter.diffutil.UserDiffUtil
import sk.tatrabanka.masarykapp.databinding.UserListItemLayoutBinding
import sk.tatrabanka.masarykapp.model.User


class UserListAdapter(private val onClickListener: View.OnClickListener) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private val userList: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.insertUser(userList[position])
    }

    override fun getItemCount() = userList.size

    fun setNewData(newUsers: List<User>) {
        val diffCallback = UserDiffUtil(userList, newUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        userList.clear()
        userList.addAll(newUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        private val binding: UserListItemLayoutBinding,
        private val onLineClickListener: View.OnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun insertUser(user: User) {
            binding.linesContainer.id = user.id
            binding.linesContainer.setOnClickListener(onLineClickListener)
            binding.userName.text = "${user.firstName} ${user.lastName}"
            binding.userEmail.text = user.email
            Glide.with(binding.avatar)
                .load(user.avatar.toUri())
                .into(binding.avatar)
        }
    }
}
package sk.tatrabanka.masarykapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sk.tatrabanka.masarykapp.adapter.diffutil.UserDiffUtil
import sk.tatrabanka.masarykapp.databinding.UserListItemLayoutBinding
import sk.tatrabanka.masarykapp.model.User


class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    var userList: List<User> = listOf()
        set(value) {
            val diffCallback = UserDiffUtil(userList, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.insertUser(userList[position])
    }

    override fun getItemCount() = userList.size

    class ViewHolder(private val binding: UserListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun insertUser(user: User) {
            binding.linesContainer.id = user.id
            binding.userName.text = "${user.firstName} ${user.lastName}"
            binding.userEmail.text = user.email
            Glide.with(binding.avatar)
                .load(user.avatar.toUri())
                .into(binding.avatar)
        }
    }
}
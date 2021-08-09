package sk.tatrabanka.masarykapp.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import sk.tatrabanka.masarykapp.model.User

class UserDiffUtil(
    private val oldUserList: List<User>,
    private val newUserList: List<User>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldUserList.size

    override fun getNewListSize() = newUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldUserList[oldItemPosition].id == newUserList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldUserList[oldItemPosition]
        val newItem = newUserList[newItemPosition]
        return oldItem.email == newItem.email &&
                oldItem.avatar == newItem.avatar &&
                oldItem.firstName == newItem.firstName &&
                oldItem.lastName == newItem.lastName
    }
}
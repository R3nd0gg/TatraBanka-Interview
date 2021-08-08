package sk.tatrabanka.masarykapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import sk.tatrabanka.masarykapp.databinding.UserListFragmentBinding
import sk.tatrabanka.masarykapp.viewmodel.UserListViewModel

class UserListFragment : BaseFragment<UserListViewModel, UserListFragmentBinding>() {
    override fun viewModelClass() = UserListViewModel::class.java

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        UserListFragmentBinding.inflate(inflater, container, false)

    override fun initView() {
        TODO("Not yet implemented")
    }
}
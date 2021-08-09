package sk.tatrabanka.masarykapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import sk.tatrabanka.masarykapp.databinding.UserDetailFragmentBinding
import sk.tatrabanka.masarykapp.viewmodel.UserDetailsViewModel

class UserDetailsFragment : BaseFragment<UserDetailsViewModel, UserDetailFragmentBinding>() {
    private var isLoading = true
        set(value) {
            viewBinding.mainLayout.isRefreshing = value
            field = value
        }

    override fun viewModelClass() = UserDetailsViewModel::class.java

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        UserDetailFragmentBinding.inflate(inflater, container, false)

    override fun initView(savedInstanceState: Bundle?) {
        // swipe to refresh
        viewBinding.mainLayout.setOnRefreshListener {
            isLoading = true
            viewModel.reFetchUserDetails()
        }
        viewModel.fetchUserDetails().observe(viewLifecycleOwner) {
            viewBinding.userName.text = "${it.firstName} ${it.lastName}"
            viewBinding.userEmail.text = it.email
            Glide.with(viewBinding.avatar)
                .load(it.avatar.toUri())
                .into(viewBinding.avatar)
            isLoading = false
        }
    }

    override fun loadArgs(bundle: Bundle) {
        viewModel.userId = UserDetailsFragmentArgs.fromBundle(bundle).id
    }
}
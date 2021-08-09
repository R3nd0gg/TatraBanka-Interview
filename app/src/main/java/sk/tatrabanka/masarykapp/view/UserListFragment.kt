package sk.tatrabanka.masarykapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sk.tatrabanka.masarykapp.adapter.UserListAdapter
import sk.tatrabanka.masarykapp.databinding.UserListFragmentBinding
import sk.tatrabanka.masarykapp.viewmodel.UserListViewModel
import kotlin.math.ceil

class UserListFragment : BaseFragment<UserListViewModel, UserListFragmentBinding>() {
    companion object {
        const val FETCH_LIMIT = 10
    }

    private val recyclerViewAdapter = UserListAdapter().apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
    private var isLoading = false
        set(value) {
            viewBinding.mainLayout.isRefreshing = value
            field = value
        }
    private var lastLoadedPage = 1
    private var isExhausted = false

    override fun viewModelClass() = UserListViewModel::class.java

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        UserListFragmentBinding.inflate(inflater, container, false)

    override fun initView(savedInstanceState: Bundle?) {
        // swipe to refresh
        viewBinding.mainLayout.setOnRefreshListener {
            lastLoadedPage = 1
            viewModel.fetchUsersAndClearCache(lastLoadedPage, FETCH_LIMIT)
        }
        // recycler view
        val layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerView.adapter = recyclerViewAdapter
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0 || isExhausted) {
                    // not scrolling down or don't have any new data
                    return
                }
                val visibleItemsCount = layoutManager.childCount
                val totalItems = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                // if we are not already loading and we can display more items than we have in total
                // new items have to be fetched
                if (!isLoading && (visibleItemsCount + firstVisibleItem) >= totalItems) {
                    isLoading = true
                    viewModel.fetchUsers(lastLoadedPage + 1, FETCH_LIMIT)
                }
            }
        })
        // init observers
        viewModel.usersObservable.observe(viewLifecycleOwner) {
            lastLoadedPage = ceil(it.size / FETCH_LIMIT.toDouble()).toInt()
            recyclerViewAdapter.userList = it
            isLoading = false
        }
        viewModel.usersExhaustedObservable.observe(viewLifecycleOwner) {
            isExhausted = it
        }
        // load first data
        if (savedInstanceState == null) {
            viewModel.fetchUsers(lastLoadedPage, FETCH_LIMIT)
        }
    }
}
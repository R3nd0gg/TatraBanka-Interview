package sk.tatrabanka.masarykapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sk.tatrabanka.masarykapp.adapter.UserListAdapter
import sk.tatrabanka.masarykapp.databinding.UserListFragmentBinding
import sk.tatrabanka.masarykapp.viewmodel.UserListViewModel

class UserListFragment : BaseFragment<UserListViewModel, UserListFragmentBinding>() {
    private val recyclerViewAdapter = UserListAdapter {
        goToFragment(UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(it.id))
    }.apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
    private var isLoading = true
        set(value) {
            viewBinding.mainLayout.isRefreshing = value
            field = value
        }
    private var isExhausted = false

    override fun viewModelClass() = UserListViewModel::class.java

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        UserListFragmentBinding.inflate(inflater, container, false)

    override fun initView(savedInstanceState: Bundle?) {
        // swipe to refresh
        viewBinding.mainLayout.setOnRefreshListener {
            viewModel.clearCacheAndFetch()
        }
        viewBinding.mainLayout.isRefreshing = true
        // recycler view
        val layoutManager = LinearLayoutManager(requireContext())
        viewBinding.recyclerView.adapter = recyclerViewAdapter
        viewBinding.recyclerView.layoutManager = layoutManager
        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // fetching more items when scrolling down
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
                    viewModel.fetchNextPage()
                }
            }
        })
        // init observers
        viewModel.usersObservable.observe(viewLifecycleOwner) {
            recyclerViewAdapter.setNewData(it)
            isLoading = false
        }
        viewModel.usersExhaustedObservable.observe(viewLifecycleOwner) {
            isExhausted = it
        }
    }

    override fun loadArgs(bundle: Bundle) {
    }
}
package sk.tatrabanka.masarykapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<TViewModel : ViewModel, TBinding : ViewBinding> : Fragment() {
    protected val viewBinding get() = binding ?: throw IllegalAccessException()
    protected val viewModel: TViewModel by lazy { ViewModelProvider(this).get(viewModelClass()) }
    private var binding: TBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        state: Bundle?
    ): View? {
        binding = initBinding(inflater, container)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected abstract fun viewModelClass(): Class<TViewModel>

    protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): TBinding

    protected abstract fun initView()
}
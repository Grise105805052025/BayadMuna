package klo0812.mlaserna.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import klo0812.mlaserna.base.BR
import klo0812.mlaserna.base.ui.models.BaseViewModel

/**
 * Creates a sample of a re-usable fragment that showcases a simple data binding concept.
 *
 * @param A type parameter to represent the ViewModel class used in this fragment.
 * @param B type parameter to represent the ViewModelFactory class used to create the ViewModel in this fragment.
 * @param C type parameter to represent the DataBinding class used in this fragment.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-22
 */
abstract class BaseBindingFragment<A : BaseViewModel<*, *>, B : ViewModelProvider.Factory, C : ViewDataBinding> : Fragment() {

    protected lateinit var viewModel: A
    protected lateinit var viewDataBinding: C

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
    }

    /**
     * Initializes the view model for this fragment.
     */
    open fun initViewModels() {
        viewModel = ViewModelProvider(
            initViewModelStore(),
            initViewModelFactory()
        )[viewModelClass()]
    }

    open fun initViewModelStore(): ViewModelStore {
        return requireActivity().viewModelStore
    }

    abstract fun initViewModelFactory(): B

    abstract fun viewModelClass(): Class<A>

    /**
     * Initializes databinding for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, getFragmentLayout(), container, false)
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    abstract fun getFragmentLayout(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateBindings()
        initiateObservers()
        initiateViews()
    }

    /**
     * Binds the view model to the layout.
     */
    open fun initiateBindings() {
        viewDataBinding.setVariable(BR.fragmentViewModel, viewModel)
    }

    /**
     * Initializes the observers (if any) for this fragment.
     */
    open fun initiateObservers() {

    }

    /**
     * Initializes the views for this fragment.
     */
    open fun initiateViews() {

    }

}
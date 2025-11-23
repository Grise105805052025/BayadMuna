package klo0812.mlaserna.base.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import klo0812.mlaserna.base.BR
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel

/**
 * Creates a sample of a re-usable activity that showcases a simple data binding concept.
 *
 * @param A type parameter to represent the ViewModel class used in this activity.
 * @param B type parameter to represent the ViewModelFactory class used to create the ViewModel in this activity.
 * @param C type parameter to represent the DataBinding class used in this activity.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-24
 */
abstract class BaseBindingActivity<
        A : BaseActivityViewModel,
        B : ViewModelProvider.Factory,
        C : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: A
    protected lateinit var viewDataBinding: C

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        bindContentView()
    }

    /**
     * Initializes the view model for this activity.
     */
    open fun initViewModels() {
        viewModel = ViewModelProvider(
            initViewModelStore(),
            initViewModelFactory()
        )[viewModelClass()]
    }

    open fun initViewModelStore(): ViewModelStore {
        return viewModelStore
    }

    abstract fun initViewModelFactory(): B

    abstract fun viewModelClass(): Class<A>

    /**
     * Initializes databinding for this activity.
     */
    private fun bindContentView() {
        viewDataBinding = DataBindingUtil.inflate(layoutInflater, getFragmentLayout(), null, false)
        viewDataBinding.lifecycleOwner = this
        setContentView(viewDataBinding.root)
        onViewCreated()
    }

    abstract fun getFragmentLayout(): Int

    private fun onViewCreated() {
        initiateBindings()
        initiateObservers()
        initiateViews()
    }

    /**
     * Binds the view model to the layout.
     */
    open fun initiateBindings() {
        viewDataBinding.setVariable(BR.activityViewModel, viewModel)
    }

    /**
     * Initializes the observers (if any) for this activity.
     */
    open fun initiateObservers() {

    }

    /**
     * Initializes the views for this activity.
     */
    open fun initiateViews() {

    }

}
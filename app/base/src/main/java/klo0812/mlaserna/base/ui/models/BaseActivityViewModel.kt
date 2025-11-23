package klo0812.mlaserna.base.ui.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Provides a ready to use ViewModel for {@link BaseBindingActivity}.
 *
 * @param progress returns true if a service call requires the app to block further actions.
 * @param navigating returns true if navigation in the app requires to block further actions.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-24
 */
open class BaseActivityViewModel(
    progress: Boolean,
    navigating: Boolean,
) : ViewModel() {

    // Let's use MutableLiveData for direct databinding
    val progress: MutableLiveData<Boolean> = MutableLiveData(progress)
    val navigating: MutableLiveData<Boolean> = MutableLiveData(navigating)

}
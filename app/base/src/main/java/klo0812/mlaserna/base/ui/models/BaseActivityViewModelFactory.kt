package klo0812.mlaserna.base.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Provides a ready to use factory to create BaseActivityViewModel.
 *
 * @param progress returns true if a service call requires the app to block further actions.
 * @param navigating returns true if navigation in the app requires to block further actions.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-24
 */
class BaseActivityViewModelFactory(
    val progress: Boolean,
    val navigating: Boolean,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BaseActivityViewModel::class.java)) {
            return BaseActivityViewModel(
                progress = progress,
                navigating = navigating
            ) as T
        }
        throw IllegalArgumentException("Unable to convert to BaseActivityViewModel!")
    }

}
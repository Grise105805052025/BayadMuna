package klo0812.mlaserna.bayadmuna.pages.main.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(
    val progress: Boolean,
    val navigating: Boolean,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                progress = progress,
                navigating = navigating
            ) as T
        }
        throw IllegalArgumentException("Unable to convert to MainViewModel!")
    }

}
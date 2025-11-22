package klo0812.mlaserna.base.ui.models

import androidx.lifecycle.ViewModel
import klo0812.mlaserna.base.repository.BaseRepository

open class BaseViewModel(val repository: BaseRepository) : ViewModel()
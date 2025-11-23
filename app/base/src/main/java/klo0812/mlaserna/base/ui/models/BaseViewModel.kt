package klo0812.mlaserna.base.ui.models

import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
import klo0812.mlaserna.base.database.BaseRepository
import klo0812.mlaserna.base.services.BaseService

/**
 * @param service is an optional parameter in case the ViewModel needs direct access to a service.
 * @param repository is an optional parameter in case the ViewModel needs direct access to a repository.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-23
 */
open class BaseViewModel<A : BaseService, B : RoomDatabase>(
    open val service: A?,
    open val repository: BaseRepository<B>?
) : ViewModel()
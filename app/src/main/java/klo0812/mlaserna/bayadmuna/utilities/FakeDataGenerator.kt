package klo0812.mlaserna.bayadmuna.utilities

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.database.entities.WalletEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

/**
 * This is used to generated initial user mocked data.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-24
 */
class FakeDataGenerator @Inject constructor(
    val appDataBase: AppDataBase
) {

    fun generateUserData(lifecycleOwner: LifecycleOwner, userId: String) {
        lifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val userData = appDataBase.userDao().get(userId)
                var wallet = appDataBase.walletDao().get(userId)
                if (wallet == null) {
                    wallet = WalletEntity(
                        id = userId,
                        userEntity = userData,
                        balance = generateRandomBalance(),
                    )
                    appDataBase.walletDao().insertAll(wallet)
                }
            }
        }
    }

}

fun generateRandomBalance(): Double {
    return (10000..100000).random().toDouble()
}

fun generateRandomId(length: Int = 10): String {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}
package klo0812.mlaserna.bayadmuna.pages.main.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import klo0812.mlaserna.base.ui.models.BaseFragmentViewModel
import klo0812.mlaserna.bayadmuna.R
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.database.entities.TransactionEntity
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.models.JSONPlaceHolderResponseModel.Companion.CODE_GENERIC_ERROR
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import klo0812.mlaserna.bayadmuna.utilities.formatMoney
import klo0812.mlaserna.bayadmuna.utilities.generateRandomId

open class SendMoneyViewModel(
    target: String,
    amount: String,
    val username: String,
    balance: Double,
    service: MainService,
    repository: MainRepository
) : BaseFragmentViewModel<MainService, AppDataBase>(
    service,
    repository
) {

    // Let's use MutableLiveData for direct databinding
    val target: MutableLiveData<String> = MutableLiveData(target)
    val amount: MutableLiveData<String> = MutableLiveData(amount)
    val balance: MutableLiveData<Double> = MutableLiveData(balance)
    val balanceString: MutableLiveData<String> = MutableLiveData(formatMoney(balance))

    fun allowSendMoney(): Boolean {
        return target.value?.isEmpty() != true && amount.value?.isEmpty() != true
    }

    fun validTarget(): Boolean {
        return !target.value.equals(username)
    }

    suspend fun sendMoney(context: Context?, userId: String): JSONPlaceHolderResponseModel {
        if (allowSendMoney()) {
            if (!validTarget()) {
                return JSONPlaceHolderResponseModel(
                    code = CODE_GENERIC_ERROR,
                    message = context?.getString(R.string.error_send_money_own_account)
                )
            }
            val targetAmount = amount.value?.toDouble() ?: 0.0
            val currentBalance = (repository as MainRepository).getBalance(userId)
            if (currentBalance == null) {
                return JSONPlaceHolderResponseModel(
                    code = CODE_GENERIC_ERROR,
                    message = context?.getString(R.string.error_send_money_balance_not_available)
                )
            } else if (targetAmount < 0.0) {
                return JSONPlaceHolderResponseModel(
                    code = CODE_GENERIC_ERROR,
                    message = context?.getString(R.string.error_send_money_amount_not_zero)
                )
            } else if (currentBalance.minus(targetAmount) < 0.0) {
                return JSONPlaceHolderResponseModel(
                    code = CODE_GENERIC_ERROR,
                    message = context?.getString(R.string.error_send_money_insufficient_balance)
                )
            } else {
                val result = (service as MainService).sendMoney(
                    userId = userId,
                    target = target.value ?: "",
                    amount = targetAmount
                )
                if (result.code == 200 || result.code == 201) {
                    (repository as MainRepository).deductBalance(userId, targetAmount)
                    val userEntity = (repository as MainRepository).getUser(userId)
                    val transaction = TransactionEntity(
                        id = generateRandomId(),
                        userEntity = userEntity,
                        target = target.value ?: "",
                        amount = targetAmount,
                        date = System.currentTimeMillis()
                    )
                    (repository as MainRepository).addNewTransaction(transaction)
                }
                return result
            }
        } else {
            return JSONPlaceHolderResponseModel(
                code = CODE_GENERIC_ERROR,
                message = context?.getString(R.string.error_generic)
            )
        }
    }

}
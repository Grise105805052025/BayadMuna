package klo0812.mlaserna.bayadmuna.pages.main.models

import androidx.lifecycle.MutableLiveData
import klo0812.mlaserna.base.ui.models.BaseFragmentViewModel
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import klo0812.mlaserna.bayadmuna.pages.main.database.MainRepository
import klo0812.mlaserna.bayadmuna.pages.main.services.MainService
import klo0812.mlaserna.bayadmuna.utilities.formatMoney
import klo0812.mlaserna.bayadmuna.utilities.hideMoney

class WalletViewModel(
    username: String,
    balance: Double,
    showBalance: Boolean,
    service: MainService,
    repository: MainRepository
) : BaseFragmentViewModel<MainService, AppDataBase>(
    service,
    repository
) {

    // Let's use MutableLiveData for direct databinding
    val username: MutableLiveData<String> = MutableLiveData(username)
    val balance: MutableLiveData<Double> = MutableLiveData(balance)
    val balanceString: MutableLiveData<String> = MutableLiveData(formatMoney(balance))
    val showBalance: MutableLiveData<Boolean> = MutableLiveData(showBalance)

    fun toggleBalanceVisibility() {
        if (showBalance.value == true) {
            balanceString.value = hideMoney(balance.value ?: 0.0)
            showBalance.postValue(false)
        } else {
            balanceString.value = formatMoney(balance.value ?: 0.0)
            showBalance.postValue(true)
        }
    }

}
package klo0812.mlaserna.bayadmuna.pages.main.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import klo0812.mlaserna.bayadmuna.utilities.formatDate
import klo0812.mlaserna.bayadmuna.utilities.formatMoney

open class TransactionItemViewModel(
    target: String,
    amount: Double,
    date: Long
) : ViewModel() {

    val target: MutableLiveData<String> = MutableLiveData(target)
    val amountString: MutableLiveData<String> = MutableLiveData(formatMoney(amount))
    val dateString: MutableLiveData<String> = MutableLiveData(formatDate(date))

}
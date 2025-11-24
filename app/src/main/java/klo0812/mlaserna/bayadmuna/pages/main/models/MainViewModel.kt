package klo0812.mlaserna.bayadmuna.pages.main.models

import android.util.TypedValue
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import klo0812.mlaserna.base.ui.models.BaseActivityViewModel
import klo0812.mlaserna.bayadmuna.R


class MainViewModel(
    progress: Boolean,
    navigating: Boolean,
    selectedMenu: Int = 0,
    lastFragment: Int = 0,
) : BaseActivityViewModel(progress, navigating) {

    companion object {

        @JvmStatic
        @BindingAdapter("app:selectedMenu")
        fun setSelectedMenu(imageView: ImageView, selected: Boolean) {
            if (selected) {
                val typedValue = TypedValue()
                val theme = imageView.context.theme
                theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
                imageView.setColorFilter(typedValue.data)
                // -------------------------
            } else {
                imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.black))
            }
        }

    }

    val selectedMenu: MutableLiveData<Int> = MutableLiveData(selectedMenu)

    val lastFragment: MutableLiveData<Int> = MutableLiveData(lastFragment)

    fun selectMenu01() {
        selectedMenu.postValue(0)
        lastFragment.postValue(0)
    }

    fun selectMenu02() {
        selectedMenu.postValue(1)
        lastFragment.postValue(1)
    }

    fun selectMenu03() {
        selectedMenu.postValue(2)
        lastFragment.postValue(2)
    }

    fun selectMenu04() {
        selectedMenu.postValue(3)
    }

}
package klo0812.mlaserna.bayadmuna.utilities

import android.content.Context
import klo0812.mlaserna.bayadmuna.R

/**
 * Applies a preset custom theme to target context.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-23
 */
class ThemeChanger {

    enum class Theme {
        BLUE,
        RED,
        YELLOW
    }

    companion object {

        @JvmStatic
        fun changeTheme(context: Context, theme: Theme) {
            val themeRecourseID: Int = when (theme) {
                Theme.BLUE -> {
                    R.style.Theme_BayadMuna_Blue
                }
                Theme.RED -> {
                    R.style.Theme_BayadMuna_Red
                }
                Theme.YELLOW -> {
                    R.style.Theme_BayadMuna_Yellow
                }
            }
            context.setTheme(themeRecourseID)
        }

        @JvmStatic
        fun randomizeTheme(context: Context) {
            val theme: Theme = Theme.entries.toTypedArray().random()
            changeTheme(context, theme)
        }

    }

}
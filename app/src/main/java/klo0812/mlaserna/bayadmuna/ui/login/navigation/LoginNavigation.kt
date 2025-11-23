package klo0812.mlaserna.bayadmuna.ui.login.navigation

import klo0812.mlaserna.bayadmuna.ui.login.ui.LoginActivity

interface LoginNavigation {
    fun navigate(fragment: LoginActivity.Fragments)
}
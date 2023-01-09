package app.vahid.base_ui.common.navigation

import androidx.annotation.StringRes

interface WithLabel {
    @StringRes
    fun getLabelResourceId(): Int = -1
}
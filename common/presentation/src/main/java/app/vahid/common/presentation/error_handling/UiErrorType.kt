package app.vahid.common.presentation.error_handling

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import app.vahid.common.presentation.R

sealed class UiErrorType {
    @StringRes
    open val title: Int = R.string.remote_error_default_title

    @DrawableRes
    open val icon: Int = R.drawable.ic_baseline_warning_24

}

data class IntResError(
    @StringRes val message: Int,
    @StringRes override val title: Int = R.string.remote_error_default_title,
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_warning_24,
) : UiErrorType()

data class StringResError(
    val message: String,
    @StringRes override val title: Int = R.string.remote_error_default_title,
    @DrawableRes override val icon: Int = R.drawable.ic_baseline_warning_24,
) : UiErrorType()

data class FormatResError(
    @StringRes val message: Int,
    @StringRes val arguments: List<Any?>,
) : UiErrorType()

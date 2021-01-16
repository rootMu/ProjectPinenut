package com.projects.rootmu.projectpinenut.ui.models

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.projects.rootmu.projectpinenut.R

sealed class DialogData {

    data class Input(
        val data: DialogInnerData,
        val type: PopupType,
        val icon: CustomIcon?,
        val textFieldTitle: CharSequence,
        val inputType: TransformationMethod,
        val defaultValue: CharSequence?
    ) : DialogData() {

        data class CustomIcon(
            val drawable: Drawable,
            val contentDescription: CharSequence
        ) {
            companion object {
                fun fromIds(
                    resources: Resources,
                    @DrawableRes drawable: Int,
                    @StringRes contentDescription: Int
                ): CustomIcon = CustomIcon(
                    ResourcesCompat.getDrawable(resources, drawable, null)!!,
                    resources.getString(contentDescription)
                )
            }
        }

        companion object {
            fun fromIds(
                resources: Resources,
                type: PopupType,
                @StringRes title: Int,
                @StringRes text: Int,
                @StringRes primaryCta: Int,
                @StringRes secondaryCta: Int?,
                customIcon: CustomIcon?,
                @StringRes textFieldTitle: Int,
                inputType: TransformationMethod,
                @StringRes defaultValue: Int?
            ): Input {
                return Input(
                    DialogInnerData(
                        resources.getString(title),
                        resources.getString(text),
                        resources.getString(primaryCta),
                        secondaryCta?.let { resources.getString(it) },
                        null
                    ),
                    type,
                    customIcon,
                    resources.getString(textFieldTitle),
                    inputType,
                    defaultValue?.let { resources.getString(it) }
                )
            }

            fun fromStrings(
                type: PopupType,
                customIcon: CustomIcon?,
                title: CharSequence,
                text: CharSequence,
                textFieldTitle: CharSequence,
                inputType: TransformationMethod,
                defaultValue: CharSequence?,
                primaryCta: CharSequence,
                secondaryCta: CharSequence?
            ): Input {
                return Input(
                    DialogInnerData(
                        title,
                        text,
                        primaryCta,
                        secondaryCta
                    ),
                    type,
                    customIcon,
                    textFieldTitle,
                    inputType,
                    defaultValue
                )
            }
        }
    }

    data class Basic(val data: DialogInnerData, val type: PopupType) : DialogData() {
        companion object {
            fun fromIds(
                resources: Resources,
                type: PopupType,
                @StringRes title: Int,
                @StringRes text: Int,
                @StringRes primaryCta: Int,
                @StringRes secondaryCta: Int?
            ): Basic {
                return Basic(
                    DialogInnerData(
                        resources.getString(title),
                        resources.getString(text),
                        resources.getString(primaryCta),
                        secondaryCta?.let { resources.getString(it) },
                        null
                    ),
                    type
                )
            }

            fun fromStrings(
                type: PopupType,
                title: CharSequence,
                text: CharSequence,
                primaryCta: CharSequence,
                secondaryCta: CharSequence?
            ): Basic {
                return Basic(
                    DialogInnerData(
                        title,
                        text,
                        primaryCta,
                        secondaryCta,
                        null
                    ),
                    type
                )
            }

            fun error(
                resources: Resources,
                @StringRes titleId: Int = R.string.error_title,
                @StringRes descriptionId: Int,
                showCancel: Boolean = true
            ): Basic = fromIds(
                resources,
                PopupType.ERROR,
                titleId,
                descriptionId,
                R.string.retry,
                if (showCancel) R.string.cancel else null
            )
        }
    }

    data class Banner(val type: PopupType, val text: String) : DialogData() {
        companion object {
            fun fromIds(
                resources: Resources,
                type: PopupType,
                @StringRes text: Int
            ): Banner {
                return Banner(
                    type,
                    resources.getString(text)
                )
            }
        }
    }
}

enum class PopupType {
    INFO, WARNING, ERROR
}

data class DialogInnerData(
    val title: CharSequence,
    val text: CharSequence,
    val primaryCta: CharSequence,
    val secondaryCta: CharSequence?,
    val debugMessage: CharSequence? = null
)

sealed class DialogResult {
    class Primary(val input: String? = null) : DialogResult()
    object Secondary : DialogResult()
}
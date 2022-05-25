package com.example.photovoltaics

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout

fun <T> redirect(context: Context, destination: Class<T>, clear: Boolean = false) {
    val intent = Intent(context, destination)
    if (clear) {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

fun dropdown(
    dropdownBtn: TextView,
    constrainLayout: ConstraintLayout? = null,
    textView: TextView? = null,
    context: Context? = null
) {
    val dropdownContent = constrainLayout ?: textView

    if (dropdownContent != null) {

        dropdownBtn.setOnClickListener {
            if (dropdownContent.visibility == View.VISIBLE) {
                dropdownContent.visibility = View.GONE
                dropdownBtn.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_tv_arrow_down,
                    0
                )
                if (dropdownContent == textView && context != null)
                    dropdownBtn.background =
                        AppCompatResources.getDrawable(context, R.drawable.tv_rounded_background)
            } else {
                dropdownContent.visibility = View.VISIBLE
                dropdownBtn.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_tv_arrow_up,
                    0
                )

                if (dropdownContent == textView && context != null)
                    dropdownBtn.background =
                        AppCompatResources.getDrawable(
                            context,
                            R.drawable.tv_top_rounded_background
                        )
            }
        }
    }

}
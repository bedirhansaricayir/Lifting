package com.lifting.app.core.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import com.lifting.app.core.ui.extensions.lighterColor
import com.lifting.app.core.ui.extensions.toLegacyInt

/**
 * Created by bedirhansaricayir on 22.02.2025
 */

@SuppressLint("ClickableViewAccessibility")
@Composable
fun RowScope.LiftingSetInputField(
    value: String,
    contentColor: Color,
    containerColor: Color,
    layoutWeight: Float = 1.25f,
    onChangeInputConnection: ((ic: InputConnection) -> Unit)? = {},
    onValueChange: ((String) -> Unit)? = {}
) {
    val keyboard = LocalSoftwareKeyboardController.current

    BoxWithConstraints(
        modifier = Modifier
            .height(32.dp)
            .padding(start = 8.dp, end = 8.dp)
            .weight(layoutWeight)
            .clip(RoundedCornerShape(12.dp))
            .background(containerColor.lighterColor(0.10f)),
    ) {
        val width = with(LocalDensity.current) { constraints.minWidth.toDp() }
        val height = with(LocalDensity.current) { constraints.minHeight.toDp() }

        AndroidView(modifier = Modifier
            .width(width)
            .height(height),
            factory = {
                TextView(it).apply {
                    text = value
                    textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    gravity = Gravity.CENTER
                    setTextColor(contentColor.toLegacyInt())
                    showSoftInputOnFocus = false

                    addTextChangedListener { e ->
                        val newValue = e.toString()
                        if (newValue != value) {
                            onValueChange?.invoke(newValue)
                        }
                    }
                    setRawInputType(inputType or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                    setTextIsSelectable(true)
                    val ic = onCreateInputConnection(EditorInfo())

                    onChangeInputConnection?.invoke(ic)

                    setOnClickListener {
                        keyboard?.show()
                    }

                    setOnTouchListener { view, motionEvent ->
                        val imm: InputMethodManager =
                            context.getSystemService(
                                Context.INPUT_METHOD_SERVICE
                            ) as InputMethodManager
                        imm.hideSoftInputFromWindow(
                            view.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )

                        view.onTouchEvent(motionEvent) // Call native handler

                        true
                    }

                    onFocusChangeListener = View.OnFocusChangeListener { _, p1 ->
                        if (p1) {
                            keyboard?.show()
                        } else {
                            keyboard?.hide()
                        }
                    }
                }
            },
            update = {
                it.setTextColor(contentColor.toLegacyInt())
            }
        )
    }
}

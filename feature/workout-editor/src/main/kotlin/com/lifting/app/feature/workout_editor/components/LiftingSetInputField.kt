package com.lifting.app.feature.workout_editor.components

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.widget.addTextChangedListener
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.keyboard.LocalLiftingKeyboard
import com.lifting.app.core.keyboard.event.ImeActionEvent
import com.lifting.app.core.keyboard.event.SetInputConnectionEvent
import com.lifting.app.core.keyboard.event.SetKeyboardTypeEvent
import com.lifting.app.core.keyboard.event.SetKeyboardVisibilityEvent
import com.lifting.app.core.keyboard.model.LiftingKeyboardType
import com.lifting.app.core.ui.extensions.toLegacyInt
import kotlinx.coroutines.awaitCancellation

/**
 * Created by bedirhansaricayir on 10.06.2025
 */

@Composable
fun RowScope.LiftingSetInputField(
    value: String,
    layoutWeight: Float = 1.25f,
    keyboardType: LiftingKeyboardType,
    onValueChange: (String) -> Unit,
) {
    val keyboard = LocalLiftingKeyboard.current
    val density = LocalDensity.current
    val textColor = LiftingTheme.colors.onBackground
    val focusManager = LocalFocusManager.current

    keyboard.subscribeOnce(ImeActionEvent.EVENT_NAME) { _, _ ->
        focusManager.moveFocus(FocusDirection.Next)
    }

    InterceptPlatformTextInput(
        interceptor = { request, nextHandler ->
            awaitCancellation()
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .height(32.dp)
                .padding(horizontal = 8.dp)
                .weight(layoutWeight)
                .clip(RoundedCornerShape(12.dp))
                .background(LiftingTheme.colors.surface),
            contentAlignment = Alignment.Center
        ) {
            val width = with(density) { this@BoxWithConstraints.constraints.minWidth.toDp() }
            val height = with(density) { this@BoxWithConstraints.constraints.minHeight.toDp() }

            AndroidView(
                modifier = Modifier
                    .size(width, height),
                factory = { context ->
                    EditText(context).apply {
                        setText(value)
                        background = null
                        setPadding(0,0,0,0)
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                        isLongClickable = false
                        isFocusable = true
                        setTextIsSelectable(false)
                        textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        gravity = Gravity.CENTER
                        isSingleLine = true
                        setTextColor(textColor.toLegacyInt())
                        showSoftInputOnFocus = false


                        if (keyboardType == LiftingKeyboardType.Time) {
                            addTextChangedListener(
                                TimeTextWatcher(
                                    editText = this,
                                    onValueChanged = { onValueChange.invoke(it) },
                                )
                            )
                        } else {
                            addTextChangedListener { e ->
                                val newValue = e.toString()
                                if (newValue != value) {
                                    onValueChange.invoke(newValue)
                                }
                            }
                        }




                        setRawInputType(inputType or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                        setTextIsSelectable(true)
                        val ic = onCreateInputConnection(EditorInfo())

                        setOnClickListener {
                            keyboard.dispatch(SetKeyboardTypeEvent(hashMapOf("keyboardType" to keyboardType)))
                            keyboard.dispatch(SetKeyboardVisibilityEvent(hashMapOf("isVisible" to true)))
                        }

                        @SuppressLint("ClickableViewAccessibility")
                        setOnTouchListener { view, motionEvent ->
                            val inputMethodManager =
                                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                            inputMethodManager.hideSoftInputFromWindow(
                                view.windowToken,
                                InputMethodManager.HIDE_NOT_ALWAYS
                            )
                            view.onTouchEvent(motionEvent)
                            true
                        }

                        onFocusChangeListener = View.OnFocusChangeListener { _, isFocused ->
                            if (isFocused) {
                                keyboard.dispatch(SetKeyboardTypeEvent(hashMapOf("keyboardType" to keyboardType)))
                                keyboard.dispatch(SetKeyboardVisibilityEvent(hashMapOf("isVisible" to true)))
                                keyboard.dispatch(SetInputConnectionEvent(hashMapOf("inputConnection" to ic)))
                            } else {
                                keyboard.dispatch(SetKeyboardVisibilityEvent(hashMapOf("isVisible" to false)))
                            }
                        }

                    }
                },
                update = {
                    it.setTextColor(textColor.toLegacyInt())
                }
            )
        }
    }
}
class TimeTextWatcher(
    private val editText: EditText,
    private val onValueChanged: (String) -> Unit,
) : TextWatcher {
    private var isEditing = false
    private val maxDigits = 4

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (isEditing) return

        val digits = s.toString().filter(Char::isDigit).takeLast(maxDigits)
        val padded = digits.padStart(maxDigits, '0')
        var minutes = padded.dropLast(2)
        var seconds = padded.takeLast(2)
        val validSeconds = (seconds.toIntOrNull() ?: 0) <= 59
        val minutesIsZeroOrLeadingZero = minutes == "00" || (minutes.startsWith("0") && minutes != "00")

        val minInt = minutes.toIntOrNull() ?: 0
        val secInt = seconds.toIntOrNull() ?: 0

        minutes = if (minInt > 59) {
            "59"
        } else {
            minutes.padStart(2, '0')
        }

        seconds = if (!minutesIsZeroOrLeadingZero && (secInt > 59 && !minutes.startsWith("0"))) {
            "59"
        } else {
            seconds.padStart(2, '0')
        }

        val formatted = "$minutes:$seconds"

        isEditing = true
        editText.setText(formatted)
        editText.setSelection(formatted.length)
        onValueChanged(formatted)
        isEditing = false

        editText.onFocusChangeListener = View.OnFocusChangeListener { _, isFocused ->
            if (!isFocused && !validSeconds) {
                normalizeToRealTime()
            }
        }
    }

    private fun normalizeToRealTime() {
        val current = editText.text.toString()
        val parts = current.split(":")
        val minutes = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val seconds = parts.getOrNull(1)?.toIntOrNull() ?: 0

        val totalSeconds = minutes * 60 + seconds
        val realMinutes = totalSeconds / 60
        val realSeconds = totalSeconds % 60

        val normalized = "%02d:%02d".format(realMinutes, realSeconds)

        isEditing = true
        editText.setText(normalized)
        editText.setSelection(normalized.length)
        onValueChanged(normalized)
        isEditing = false
    }
}


/*
@Composable
fun RowScope.LiftingSetInputField2(
    value: String,
    layoutWeight: Float = 1.25f,
    liftingKeyboardType: LiftingKeyboardType = LiftingKeyboardType.Weight(),
    onValueChange: (String) -> Unit,
) {
    val keyboard = LocalLiftingKeyboard.current
    val density = LocalDensity.current
    val focusRequester = remember { FocusRequester() }
    val textFieldValue = remember { mutableStateOf(TextFieldValue(text = value)) }
    var inputConnection by remember { mutableStateOf<InputConnection?>(null) }

    InterceptPlatformTextInput(
        interceptor = { request, nextHandler ->
            inputConnection = request.createInputConnection(EditorInfo())
            awaitCancellation()
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .height(32.dp)
                .padding(horizontal = 8.dp)
                .weight(layoutWeight)
                .clip(RoundedCornerShape(12.dp))
                .background(LiftingTheme.colors.surface)
                .clickable {
                    focusRequester.requestFocus()
                    keyboard.show()
                    inputConnection?.let {
                        keyboard.setInputConnection(it)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            val width = with(density) { this@BoxWithConstraints.constraints.minWidth.toDp() }
            val height = with(density) { this@BoxWithConstraints.constraints.minHeight.toDp() }

            BasicTextField(
                value = textFieldValue.value,
                onValueChange = {
                    textFieldValue.value = it
                    onValueChange(it.text)
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .size(width, height)
                    .onFocusChanged { state ->
                        if (state.isFocused) {
                            keyboard.dispatch(SetKeyboardTypeEvent(hashMapOf("keyboardType" to liftingKeyboardType)))
                            keyboard.show()
                            inputConnection?.let {
                                keyboard.setInputConnection(it)
                            }
                        } else {
                            keyboard.hide()
                        }
                    },
                textStyle = LiftingTheme.typography.caption.copy(
                    color = LiftingTheme.colors.onBackground,
                    textAlign = TextAlign.Center,
                    textMotion = TextMotion.Animated
                ),
                cursorBrush = SolidColor(LiftingTheme.colors.primary),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        innerTextField()
                    }
                }
            )
        }
    }
}
*/
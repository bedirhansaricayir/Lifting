package com.lifting.app.feature_auth.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lifting.app.R

@Composable
fun Requirement(
    modifier: Modifier = Modifier,
    message: String,
    satisfied: Boolean
) {
    val requirementStatus = if (satisfied) {
        stringResource(
            id =
            R.string.password_requirement_satisfied, message
        )
    } else {
        stringResource(
            id =
            R.string.password_requirement_needed, message
        )
    }
    val tint = animateColorAsState(
        targetValue =
        if (satisfied) MaterialTheme.colorScheme.primary
        else Color.White.copy(0.6f), label = ""
    )
    Row(
        modifier = Modifier
            .padding(6.dp)
            .semantics(mergeDescendants = true) {
                text = AnnotatedString(requirementStatus)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = tint.value
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.clearAndSetSemantics { },
            text = message,
            fontSize = 12.sp,
            color = tint.value
        )
    }
}
package com.lifting.app.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lifting.app.core.common.extensions.EMPTY
import com.lifting.app.core.designsystem.LiftingTheme
import com.lifting.app.core.model.MaxDistancePR
import com.lifting.app.core.model.MaxDurationPR
import com.lifting.app.core.model.MaxOneRmPR
import com.lifting.app.core.model.MaxRepsPR
import com.lifting.app.core.model.MaxVolumeAddedPR
import com.lifting.app.core.model.MaxVolumePR
import com.lifting.app.core.model.MaxWeightAddedPR
import com.lifting.app.core.model.MaxWeightPR
import com.lifting.app.core.model.PersonalRecord
import com.lifting.app.core.ui.R

/**
 * Created by bedirhansaricayir on 06.04.2025
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PersonalRecordsRowComponent(
    modifier: Modifier,
    prs: List<PersonalRecord>
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small),
        verticalArrangement = Arrangement.spacedBy(LiftingTheme.dimensions.small),
        content = {
            prs.forEach { pr ->
                PRItem(pr = pr)
            }
        }
    )
}

@Composable
private fun PRItem(
    pr: PersonalRecord
) {
    val readableText = when (pr) {
        is MaxDistancePR -> stringResource(R.string.max_distance)
        is MaxDurationPR -> stringResource(R.string.max_duration)
        is MaxOneRmPR -> stringResource(R.string.max_one_rm)
        is MaxRepsPR -> stringResource(R.string.max_reps)
        is MaxVolumeAddedPR -> stringResource(R.string.max_volume_added)
        is MaxVolumePR -> stringResource(R.string.max_volume)
        is MaxWeightAddedPR -> stringResource(R.string.max_weight_added)
        is MaxWeightPR -> stringResource(R.string.max_weight)
        else -> pr.value
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(LiftingTheme.colors.primary.copy(0.1f))
    ) {
        Row(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(LiftingTheme.dimensions.large),
                imageVector = LiftingTheme.icons.calendar,
                tint = LiftingTheme.colors.primary,
                contentDescription = String.EMPTY
            )
            Spacer(Modifier.width(LiftingTheme.dimensions.extraSmall))
            Text(
                text = readableText,
                color = LiftingTheme.colors.primary,
                style = LiftingTheme.typography.subtitle2
            )
        }
    }
}
package com.lifting.app.feature_home.presentation.calculator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.lifting.app.R
import com.lifting.app.theme.Black40
import com.lifting.app.theme.grey10
import com.lifting.app.theme.grey50
import kotlinx.coroutines.launch

enum class CalculatorCategory {
    BMR, RM, BMI, ZURT
}

data class GridItemData(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val infoTitle: Int,
    @StringRes val infoDescription: Int,
    val category: CalculatorCategory
)

val gridList = listOf(
    GridItemData(
        image = R.drawable.bmr_image,
        title = R.string.bmr_calculator_title_label,
        infoTitle = R.string.bmr_calculator_info_title,
        infoDescription = R.string.bmr_calculator_info_description,
        category = CalculatorCategory.BMR
    ),
    GridItemData(
        image = R.drawable.bmr_image,
        title = R.string.rm_calculator_title_label,
        infoTitle = R.string.rm_calculator_info_title,
        infoDescription = R.string.rm_calculator_info_description,
        category = CalculatorCategory.RM
    ),
    GridItemData(
        image = R.drawable.bmr_image,
        title = R.string.bmr_calculator_title_label,
        infoTitle = R.string.bmr_calculator_info_title,
        infoDescription = R.string.bmr_calculator_info_description,
        category = CalculatorCategory.ZURT
    ),
    GridItemData(
        image = R.drawable.bmr_image,
        title = R.string.bmi_calculator_title_label,
        infoTitle = R.string.bmi_calculator_info_title,
        infoDescription = R.string.bmi_calculator_info_description,
        category = CalculatorCategory.BMI
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridItem(data: GridItemData, onClick: (category: CalculatorCategory) -> Unit) {
    val tooltipState = remember { RichTooltipState() }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(4.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black)
            ) {
                onClick(data.category)
            },
        elevation = CardDefaults.cardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Black40),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Card(modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.pulse),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )
                }

                RichTooltipBox(
                    tooltipState = tooltipState,
                    title = { Text(modifier = Modifier.padding(start = 7.dp),text = stringResource(data.infoTitle), style = MaterialTheme.typography.labelSmall) },
                    text = { Text(modifier = Modifier.padding(start = 7.dp),text = stringResource(data.infoDescription), style = MaterialTheme.typography.labelSmall) },
                    action = { TextButton(onClick = { scope.launch { tooltipState.dismiss() } }) { Text(text = stringResource(id = R.string.close_label)) } },
                    colors = TooltipDefaults.richTooltipColors(containerColor = Black40, titleContentColor = grey10, contentColor = grey10)
                )
                {
                    IconButton(
                        modifier = Modifier.tooltipAnchor(),
                        onClick = { scope.launch { tooltipState.show() } }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = data.title))
            }
        }

    }
}

@Composable
fun CalculatorScreen(onClick: (category: CalculatorCategory) -> Unit) {

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(color = grey50), columns = GridCells.Fixed(2)
    ) {
        items(gridList.size) { index ->
            GridItem(gridList[index], onClick = onClick)
        }
    }
}
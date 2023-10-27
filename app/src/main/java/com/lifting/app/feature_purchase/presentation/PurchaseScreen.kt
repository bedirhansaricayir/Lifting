package com.lifting.app.feature_purchase.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_purchase.domain.model.ProductCardData
import com.lifting.app.feature_purchase.domain.model.ProvidedFeaturesData
import com.lifting.app.feature_purchase.presentation.components.PurchaseButton
import com.lifting.app.feature_purchase.presentation.components.SelectableProductCard
import com.lifting.app.theme.grey10

@Composable
fun PurchaseScreen(
    state: PurchaseScreenState,
    onNavigationClick: () -> Unit
) {

    val context = LocalContext.current
    PurchaseScreenContent(
        state = state,
        onNavigationClick = onNavigationClick,
        onButtonClick = {
            Toast.makeText(context,context.getString(R.string.coming_soon_label),Toast.LENGTH_SHORT).show()
        }
    )

}

@Composable
fun PurchaseScreenContent(
    state: PurchaseScreenState,
    onNavigationClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    var selectedProductCardData by remember {
        mutableStateOf<ProductCardData?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(8.dp)
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopNavigationSection(onClick = onNavigationClick)
        Spacer(modifier = Modifier.height(24.dp))
        HeaderSection()
        Spacer(modifier = Modifier.height(32.dp))
        ProvidedFeaturesSection(featureTitle = state.providedFeaturesData)
        Spacer(modifier = Modifier.height(64.dp))
        SelectableProductCardGroup(
            productCardData = state.productCardData,
            selectedProductCardData = selectedProductCardData,
            onProductCardSelected = { selectedProductCard ->
                selectedProductCardData = selectedProductCard
            }
        )
        Spacer(modifier = Modifier.weight(1f))

        PurchaseButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            enabled = selectedProductCardData != null,
            onClick = onButtonClick
        )
        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun TopNavigationSection(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "Close Icon",
            modifier = Modifier
                .padding(8.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .size(32.dp)
                .clickable { onClick() }
                .padding(8.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun HeaderSection() {
    Icon(
        painter = painterResource(id = R.drawable.baseline_workspace_premium_24),
        contentDescription = "Premium Icon",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .size(96.dp)
            .padding(8.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(id = R.string.label_upgrade_to_premium),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
    Text(
        text = stringResource(id = R.string.label_upgrade_to_premium_description),
        style = MaterialTheme.typography.labelMedium,
        color = grey10,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun ProvidedFeaturesSection(
    modifier: Modifier = Modifier,
    leadingIcon: Int = R.drawable.icon_check_circle,
    leadingIconColor: Color = MaterialTheme.colorScheme.primary,
    leadingIconSize: Dp = 18.dp,
    featureTitle: List<ProvidedFeaturesData>,
    featureTitleStyle: TextStyle = MaterialTheme.typography.labelSmall,
    featureTitleColor: Color = grey10
) {

    featureTitle.forEach {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = "Provided Features Icon",
                tint = leadingIconColor,
                modifier = Modifier.size(leadingIconSize)
            )
            Text(
                text = stringResource(id = it.title),
                style = featureTitleStyle,
                color = featureTitleColor
            )
        }
    }
}

@Composable
fun SelectableProductCardGroup(
    modifier: Modifier = Modifier,
    productCardData: List<ProductCardData>,
    selectedProductCardData: ProductCardData?,
    onProductCardSelected: (ProductCardData) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        productCardData.forEach {
            val selected = it == selectedProductCardData
            SelectableProductCard(selected = selected, title = it.title, subtitle = it.subTitle) {
                if (!selected) {
                    onProductCardSelected(it)
                }
            }
        }
    }
}



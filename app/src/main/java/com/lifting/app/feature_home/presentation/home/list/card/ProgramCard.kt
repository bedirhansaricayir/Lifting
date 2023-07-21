package com.lifting.app.feature_home.presentation.home.list.card

import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lifting.app.R
import com.lifting.app.feature_home.data.remote.model.DusukZorluk
import com.lifting.app.feature_home.data.remote.model.OrtaZorluk
import com.lifting.app.feature_home.data.remote.model.YuksekZorluk

@Composable
fun BeginnerProgramCard(
    modifier: Modifier = Modifier,
    model: DusukZorluk,
    onPlayButtonClick: (DusukZorluk) -> Unit
) {
    Card(
        modifier = modifier
            .height(250.dp)
            .width(350.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
            Box(modifier = modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.beginner_image),
                    contentDescription = "Program Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize()
                )

                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(
                        modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = model.programAdi.toString(),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = modifier.padding(4.dp))
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            /*Text(
                                text = model.programAdi.toString(),
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )*/
                            CustomRatingBar(rating = model.gunSayisi!!.toFloat())
                            OutlinedIconButton(border = BorderStroke(1.dp, Color(0xFFCAF76F)),
                                onClick = { onPlayButtonClick(model) }) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play Button",
                                    tint = Color(0xFFCAF76F)
                                )
                            }
                        }
                    }
                }
            }
    }
    Spacer(modifier = modifier.width(8.dp))
}

@Composable
fun IntermediateProgramCard(
    modifier: Modifier = Modifier,
    model: OrtaZorluk,
    onPlayButtonClick: (OrtaZorluk) -> Unit
) {
    Card(
        modifier = modifier
            .height(250.dp)
            .width(350.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.intermediate_image),
                contentDescription = "Program Image",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = model.programAdi.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = modifier.padding(4.dp))
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /*Text(
                            text = model.programAdi.toString(),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )*/
                        CustomRatingBar(rating = model.gunSayisi!!.toFloat())
                        OutlinedIconButton(border = BorderStroke(1.dp, Color(0xFFCAF76F)),
                            onClick = { onPlayButtonClick(model) }) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Button",
                                tint = Color(0xFFCAF76F)
                            )
                        }

                    }
                }
            }
        }
    }
    Spacer(modifier = modifier.width(8.dp))
}

@Composable
fun AdvancedProgramCard(
    modifier: Modifier = Modifier,
    model: YuksekZorluk,
    onPlayButtonClick: (YuksekZorluk) -> Unit
) {
    Card(
        modifier = modifier
            .height(250.dp)
            .width(350.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.advanced_image),
                contentDescription = "Program Image",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = model.programAdi.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = modifier.padding(4.dp))
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        /*Text(
                            text = model.programAdi.toString(),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )*/
                        CustomRatingBar(rating = model.gunSayisi!!.toFloat())
                        OutlinedIconButton(border = BorderStroke(1.dp, Color(0xFFCAF76F)),
                            onClick = { onPlayButtonClick(model) }) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Button",
                                tint = Color(0xFFCAF76F)
                            )
                        }

                    }
                }
            }
        }
    }
    Spacer(modifier = modifier.width(8.dp))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PersonalizedProgramCard(
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit
) {
    var isCardScaled by remember { mutableStateOf(false) }
    val cardScale by animateFloatAsState(
        targetValue = if (isCardScaled) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    Card(
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth()
            .pointerInteropFilter { motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isCardScaled = true
                        true
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        isCardScaled = false
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        isCardScaled = false
                        onButtonClicked.invoke()
                        true
                    }

                    else -> false
                }
            }
            .clickable { isCardScaled = !isCardScaled }
            .scale(cardScale),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.advanced_image),
                contentDescription = "Program Image",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )

            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.label_personalized_plan),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun CustomRatingBar(rating: Float) {
    val imageBackground = ImageBitmap.imageResource(id = R.drawable.star_background)
    val imageForeground = ImageBitmap.imageResource(id = R.drawable.star_foreground)
    RatingBar(
        rating = rating,
        space = 2.dp,
        imageEmpty = imageBackground,
        imageFilled = imageForeground,
        tintEmpty = MaterialTheme.colorScheme.primary,
        tintFilled = MaterialTheme.colorScheme.primary,
        animationEnabled = true,
        gestureEnabled = false,
        shimmer = Shimmer(
            colors = listOf(
                MaterialTheme.colorScheme.primary.copy(.9f),
                MaterialTheme.colorScheme.primary.copy(.3f),
                MaterialTheme.colorScheme.primary.copy(.9f))
            ),
        itemSize = 20.dp
    ) {
    }
}
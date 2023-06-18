package com.fitness.app.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitness.app.R
import com.fitness.app.data.remote.DusukZorluk
import com.fitness.app.data.remote.OrtaZorluk
import com.fitness.app.data.remote.YuksekZorluk

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
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                    /*Text(
                        text = model.programAdi.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = modifier.padding(4.dp))*/
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = model.programAdi.toString(),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
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
                    /*Text(
                        text = model.programAdi.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = modifier.padding(4.dp))*/
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = model.programAdi.toString(),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
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
                    /*Text(
                        text = model.programAdi.toString(),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = modifier.padding(4.dp))*/
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = model.programAdi.toString(),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
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
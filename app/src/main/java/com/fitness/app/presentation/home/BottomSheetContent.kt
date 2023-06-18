package com.fitness.app.presentation.home


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fitness.app.data.remote.Uygulanis
import com.fitness.app.ui.theme.White40
import com.fitness.app.ui.theme.grey30

@Composable
fun BottomSheetContent(
    model: ArrayList<Uygulanis>,
    onPlayButtonClicked: (String) -> Unit
) {
    val expandedStateMap = remember { mutableStateMapOf<Int, Boolean>() }
    var isVideoAvailable = remember { mutableStateOf(false) }

    LazyColumn() {
        itemsIndexed(model) { index, it ->
            fun isExpanded(index: Int): Boolean {
                return expandedStateMap[index] ?: false
            }

            fun expandedState(index: Int) {
                expandedStateMap[index] = !(expandedStateMap[index] ?: false)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = it.gun!!,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                )
                IconButton(onClick = { expandedState(index) }) {
                    Icon(
                        imageVector = if (isExpanded(index)) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expanded Icon"
                    )
                }
            }

            if (isExpanded(index)) {
                it.hareketler.forEach {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Hareket Adı: ${it.hareketAdi}",
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                color = White40
                            )

                            isVideoAvailable.value = !it.hareketForm.isNullOrEmpty()
                            OutlinedIconButton(modifier = Modifier.align(Alignment.CenterVertically),
                                border = BorderStroke(
                                    1.dp,
                                    if (isVideoAvailable.value) MaterialTheme.colorScheme.primary else grey30
                                ),
                                onClick = {
                                    if (!it.hareketForm.isNullOrEmpty()) {
                                        onPlayButtonClicked(it.hareketForm!!)
                                    }
                                }) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play Button",
                                    tint = if (isVideoAvailable.value) MaterialTheme.colorScheme.primary else grey30
                                )
                            }

                        }
                    }

                    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Set Sayısı: ${it.setSayisi}",
                                style = MaterialTheme.typography.labelSmall,
                                color = White40
                            )
                            Text(
                                text = "Tekrar Sayısı: ${it.tekrarSayisi}",
                                style = MaterialTheme.typography.labelSmall,
                                color = White40
                            )
                        }

                    }
                    Divider(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), thickness = 1.dp,color = grey30)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
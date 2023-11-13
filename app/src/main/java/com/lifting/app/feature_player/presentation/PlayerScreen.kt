package com.lifting.app.feature_player.presentation

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.lifting.app.common.util.noRippleClickable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.lifting.app.R
import com.lifting.app.common.util.toTimeFormat
import com.lifting.app.feature_player.presentation.components.PlayerBackButton
import com.lifting.app.feature_player.presentation.components.PlayerDuration
import com.lifting.app.feature_player.presentation.components.PlayerPlaybackSpeed
import com.lifting.app.feature_player.presentation.components.PlayerSliderDuration
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@UnstableApi
@Composable
fun PlayerScreen(
    videoUrl: String,
    state: PlayerScreenState,
    onBackNavigationIconClicked: () -> Unit,
) {
    var currentPosition by remember { mutableLongStateOf(0) }
    var sliderDuration by remember { mutableFloatStateOf(0f) }
    var playbackSpeed by remember { mutableFloatStateOf(1f) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp))
            .background(color = Color.Black)
    ) {
        LazyColumn {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .fillParentMaxHeight(0.6f)
                        .statusBarsPadding()
                ) {
                    VideoPlayer(
                        videoUrl = videoUrl,
                        playbackSpeed = playbackSpeed,
                        onChangedCurrentPosition = { position,duration ->
                            currentPosition = position
                            sliderDuration = duration
                        },
                    )
                    PlayerBackButton(
                        modifier = Modifier.align(Alignment.TopStart),
                        onBackNavigationIconClicked = onBackNavigationIconClicked
                    )

                    PlayerDuration(
                        modifier = Modifier.align(Alignment.BottomStart),
                        currentPosition = currentPosition.toTimeFormat()
                    )
                    PlayerPlaybackSpeed(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        onPlaybackSpeedChanged = { speed ->
                            playbackSpeed = speed
                        }
                    )
                    PlayerSliderDuration(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        progress = sliderDuration
                    )
                }
            }
        }

    }
}

@UnstableApi
@Composable
fun VideoPlayer(
    videoUrl: String,
    playbackSpeed: Float = 1f,
    onChangedCurrentPosition: (Long,Float) -> Unit,
) {
    val context = LocalContext.current
    val playbackParameters = PlaybackParameters(playbackSpeed)
    var isOverlayVisible by remember { mutableStateOf(false) }
    var isVideoLoading by remember { mutableStateOf(true) }
    var playPauseIcon by remember { mutableIntStateOf(R.drawable.icon_pause) }
    var remainingTime by remember { mutableLongStateOf(0L) }
    var isPlaying by remember { mutableStateOf(false) }
    var offsetState by remember { mutableFloatStateOf(0f) }


    var lifecycle by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(videoUrl))

                this.setMediaSource(source)
                prepare()
            }
    }
    val gestureModifier = Modifier.pointerInput(Unit) {
        detectDragGestures { _, dragAmount ->
            offsetState += dragAmount.x
            if (offsetState != 0f) {
                playPauseIcon = R.drawable.icon_pause
                isOverlayVisible = false
            }
        }
    }
    LaunchedEffect(offsetState) {
        while (true) {
            if (offsetState > 100) {
                exoPlayer.seekTo(exoPlayer.currentPosition + 1000)
                offsetState = 0f
            } else if (offsetState < -100) {
                exoPlayer.seekTo(exoPlayer.currentPosition - 1000)
                offsetState = 0f
            }
            delay(100)
        }
    }


    LaunchedEffect(key1 = playbackSpeed) {
        if (exoPlayer.playWhenReady) {
            playPauseIcon = R.drawable.icon_pause
            exoPlayer.playWhenReady = true
            isOverlayVisible = false
        }
    }

    LaunchedEffect(exoPlayer) {
        while (true) {
            val playWhenReady = exoPlayer.playWhenReady
            if (playWhenReady) {
                if (!isPlaying) {
                    isPlaying = true
                    launch {
                        while (isPlaying) {
                            val currentPosition = exoPlayer.currentPosition
                            val duration = exoPlayer.duration
                            remainingTime = duration - currentPosition
                            val currentDuration = currentPosition / duration.toFloat()
                            onChangedCurrentPosition(remainingTime,currentDuration)
                            delay(1000)
                        }
                    }
                }
            } else {
                isPlaying = false
            }
            delay(100)
        }
    }


    val playerListener = object : Player.Listener {

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                Player.STATE_BUFFERING -> {
                    isVideoLoading = true
                }

                Player.STATE_READY -> {
                    isVideoLoading = false
                }

                Player.STATE_IDLE -> {
                    isVideoLoading = false
                }

                Player.STATE_ENDED -> {
                }
            }
        }
    }

    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    exoPlayer.volume = 0f
    exoPlayer.addListener(playerListener)
    exoPlayer.playbackParameters = playbackParameters

    Player(
        modifier = gestureModifier,
        lifecycle = lifecycle,
        exoPlayer = exoPlayer,
        isVideoLoading = isVideoLoading,
        onPlayerClick = {
            if (!exoPlayer.playWhenReady) {
                playPauseIcon = R.drawable.icon_pause
                exoPlayer.playWhenReady = true
                isOverlayVisible = false
            } else isOverlayVisible = !isOverlayVisible
        },
    )


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(
            visible = isVideoLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CircularProgressIndicator(color = Color.Green)
        }
        AnimatedVisibility(
            visible = isOverlayVisible,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .background(
                        color = Color.Black.copy(0.3f),
                        shape = CircleShape
                    )
                    .padding(8.dp)
                    .noRippleClickable {
                        if (exoPlayer.playWhenReady) {
                            exoPlayer.playWhenReady = false
                            playPauseIcon = R.drawable.icon_play
                        } else {
                            exoPlayer.playWhenReady = true
                            playPauseIcon = R.drawable.icon_pause
                            isOverlayVisible = false
                        }
                    }
                    .align(Alignment.Center),
            ) {
                Icon(
                    painter = painterResource(id = playPauseIcon),
                    contentDescription = "Play-Pause Button",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
            }
        }
    }
}

@Composable
@androidx.annotation.OptIn(UnstableApi::class)
fun Player(
    modifier: Modifier = Modifier,
    lifecycle: Lifecycle.Event,
    exoPlayer: ExoPlayer,
    isVideoLoading: Boolean,
    onPlayerClick: () -> Unit,
) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier
            .noRippleClickable(
                enabled = !isVideoLoading
            ) {
                onPlayerClick()
            },
        factory = {
            PlayerView(context).apply {
                hideController()
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    MATCH_PARENT,
                    MATCH_PARENT
                )
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }

                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }

                else -> Unit
            }
        }
    )
}

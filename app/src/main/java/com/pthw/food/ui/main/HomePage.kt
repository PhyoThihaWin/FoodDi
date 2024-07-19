package com.pthw.food.ui.main

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.pthw.food.R
import com.pthw.food.composable.CoilAsyncImage
import com.pthw.food.composable.CustomTextField
import com.pthw.food.composable.TitleTextView
import com.pthw.food.theme.Dimens
import com.pthw.food.theme.FoodDiAppTheme
import com.pthw.food.theme.LocalCustomColors
import com.pthw.food.theme.Shapes
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by P.T.H.W on 16/07/2024.
 */
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val isScrolledOnTop by remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset == 0 }
    }
    val progress by animateFloatAsState(
        targetValue = if (isScrolledOnTop) 0f else 1f,
        tween(500, easing = LinearOutSlowInEasing)
    )
    HomePageContent(scrollState = scrollState, progress = progress) {
        coroutineScope.launch {
            scrollState.scrollToItem(0)
        }
    }
}


private val startConstraintSet = ConstraintSet {
    val background = createRefFor("background")
    constrain(background) {
        top.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    val setting = createRefFor("setting")
    constrain(setting) {
        top.linkTo(parent.top, Dimens.MARGIN_MEDIUM_2)
        start.linkTo(parent.start, Dimens.MARGIN_20)
    }
    val filter = createRefFor("filter")
    constrain(filter) {
        top.linkTo(parent.top, Dimens.MARGIN_MEDIUM_2)
        end.linkTo(parent.end, Dimens.MARGIN_20)
    }
    val title = createRefFor("title")
    constrain(title) {
        top.linkTo(background.top)
        start.linkTo(background.start)
        end.linkTo(background.end)
        bottom.linkTo(background.bottom)
    }
    val search = createRefFor("search")
    constrain(search) {
        width = Dimension.fillToConstraints
        start.linkTo(parent.start, Dimens.MARGIN_20)
        end.linkTo(parent.end, Dimens.MARGIN_20)
        top.linkTo(background.bottom)
        bottom.linkTo(background.bottom)
    }
    val list = createRefFor("list")
    constrain(list) {
        height = Dimension.fillToConstraints
        top.linkTo(search.bottom)
        bottom.linkTo(parent.bottom)
    }
}
private val endConstraintSet = ConstraintSet {
    val background = createRefFor("background")
    constrain(background) {
        bottom.linkTo(parent.top)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
    val setting = createRefFor("setting")
    constrain(setting) {
        bottom.linkTo(parent.top)
        end.linkTo(parent.start)
    }
    val filter = createRefFor("filter")
    constrain(filter) {
        bottom.linkTo(parent.top)
        start.linkTo(parent.end)
    }
    val title = createRefFor("title")
    constrain(title) {
        top.linkTo(background.top)
        start.linkTo(background.start)
        end.linkTo(background.end)
        bottom.linkTo(background.bottom)
    }
    val search = createRefFor("search")
    constrain(search) {
        start.linkTo(parent.end, -56.dp)
        top.linkTo(parent.top, Dimens.MARGIN_MEDIUM_2)
    }
    val list = createRefFor("list")
    constrain(list) {
        height = Dimension.fillToConstraints
        top.linkTo(parent.top)
        bottom.linkTo(parent.bottom)
    }
}

@Composable
fun HomePageContent(
    scrollState: LazyListState,
    progress: Float,
    iconClick: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val openDialog = remember { mutableStateOf(false) }

    MotionLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        start = startConstraintSet,
        end = endConstraintSet,
        progress = progress
    ) {

        Spacer(
            modifier = Modifier
                .layoutId("background")
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .background(color = colorResource(id = R.color.colorPrimary))
        )

        Icon(
            modifier = Modifier.layoutId("setting"),
            painter = painterResource(id = R.drawable.ic_more_menu),
            tint = Color.White,
            contentDescription = ""
        )

        Icon(
            modifier = Modifier
                .layoutId("filter")
                .clickable {
                    openDialog.value = true
                },
            painter = painterResource(id = R.drawable.ic_filter_list),
            tint = Color.White,
            contentDescription = ""
        )

        TitleTextView(
            modifier = Modifier.layoutId("title"),
            text = stringResource(id = R.string.app_name),
            color = Color.White,
            fontSize = Dimens.TEXT_HEADING_2
        )


        // test list
        LazyColumn(
            modifier = Modifier
                .layoutId("list")
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = Dimens.MARGIN_LARGE,
                bottom = (configuration.screenHeightDp / 4).dp
            ),
            state = scrollState,
        ) {
            item {
                Column {
                    repeat((1..10).count()) {
                        Card(
                            modifier = Modifier.padding(
                                start = Dimens.MARGIN_20, end = Dimens.MARGIN_20,
                                bottom = Dimens.MARGIN_MEDIUM_2
                            ),
                            shape = Shapes.medium,
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = Dimens.MARGIN_SMALL
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Dimens.MARGIN_LARGE),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CoilAsyncImage(
                                        imageUrl = R.drawable.logoblack,
                                        modifier = Modifier
                                            .size(Dimens.IMAGE_CARD_SIZE)
                                            .clip(Shapes.medium),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM_2))
                                    Text(text = "Honey", fontSize = Dimens.TEXT_REGULAR)
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(modifier = Modifier.height(Dimens.MARGIN_SMALL))
                                    Text(text = "Vs", fontSize = Dimens.TEXT_XLARGE)
                                    Spacer(modifier = Modifier.height(Dimens.MARGIN_10))
                                    TitleTextView(
                                        text = "Honey",
                                        color = colorResource(id = R.color.colorPrimary),
                                        fontSize = Dimens.TEXT_REGULAR_2
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CoilAsyncImage(
                                        imageUrl = R.drawable.logoblack,
                                        modifier = Modifier
                                            .size(Dimens.IMAGE_CARD_SIZE)
                                            .clip(Shapes.medium),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM_2))
                                    Text(text = "Honey", fontSize = Dimens.TEXT_REGULAR)
                                }
                            }
                        }
                    }
                }
            }
        }


        // searchBox
        HomeSearchBarView(modifier = Modifier.layoutId("search"), iconClick)

        // filter dialog
        if (openDialog.value) {
            FilterDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                onConfirmation = { },
                imageDescription = ""
            )
        }

    }
}

@Composable
private fun HomeSearchBarView(
    modifier: Modifier,
    iconClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.MARGIN_SMALL
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .clip(Shapes.medium)
                .padding(horizontal = Dimens.MARGIN_MEDIUM_2)

        ) {
            Icon(
                modifier = Modifier.clickable {
                    iconClick()
                },
                painter = painterResource(id = R.drawable.ic_search_normal),
                contentDescription = ""
            )
            CustomTextField(
                modifier = Modifier
                    .height(52.dp)
                    .fillMaxWidth()
                    .padding(start = Dimens.MARGIN_MEDIUM),
                placeholderText = "Search"
            )
        }
    }
}

@Composable
fun FilterDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    imageDescription: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.filter_loading))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            restartOnPlay = true,
            iterations = LottieConstants.IterateForever,
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = Shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimation(
                    modifier = Modifier.height(200.dp),
                    composition = composition,
                    progress = { progress },
                )
                Text(
                    text = "This is a dialog with buttons and an image.",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun HomePagePreview() {
    FoodDiAppTheme {
        Surface {
            HomePageContent(
                scrollState = rememberLazyListState(),
                progress = 0f,
                iconClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun FilterDialogPreview() {
    FoodDiAppTheme {
        Surface {
            FilterDialog(
                onDismissRequest = { /*TODO*/ },
                onConfirmation = { /*TODO*/ },
                imageDescription = "null"
            )
        }
    }
}
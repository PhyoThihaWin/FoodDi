package com.pthw.food.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.MotionLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.facebook.ads.AdSize
import com.pthw.food.R
import com.pthw.food.common.composable.CoilAsyncImage
import com.pthw.food.common.composable.LocalizationUpdater
import com.pthw.food.common.composable.RadioSelectionDialog
import com.pthw.food.common.composable.TitleTextView
import com.pthw.food.data.model.AppThemeMode
import com.pthw.food.data.model.FilterType
import com.pthw.food.data.model.Food
import com.pthw.food.data.model.Localization
import com.pthw.food.ui.home.adview.MetaBanner
import com.pthw.food.ui.home.adview.MetaInterstitial
import com.pthw.food.ui.theme.ColorPrimary
import com.pthw.food.ui.theme.Dimens
import com.pthw.food.ui.theme.FoodDiAppTheme
import com.pthw.food.ui.theme.Shapes
import com.pthw.food.utils.ConstantValue
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by P.T.H.W on 16/07/2024.
 */
@Composable
fun HomePage(
    viewModel: HomePageViewModel = hiltViewModel()
) {
    Timber.i("Reached: HomePage")

    HomePageContent(
        UiState(
            themeCode = viewModel.appThemeMode.value,
            localeCode = viewModel.currentLanguage.value,
            pageTitle = viewModel.pageTitle.intValue,
            foods = viewModel.foods.value
        )
    ) {
        when (it) {
            is UiEvent.SearchFoods -> {
                viewModel.updateClickCountAd()
                viewModel.updateSearchQuery(it.search)
            }

            is UiEvent.FilterFoods -> {
                viewModel.updateClickCountAd()
                if (it.filterType.type == null) {
                    viewModel.getAllFoods()
                } else {
                    viewModel.getFoodsByType(it.filterType)
                }
            }

            is UiEvent.ChangeLanguage -> {
                viewModel.updateClickCountAd()
                viewModel.updateLanguageCache(it.localeCode)
            }

            is UiEvent.ChangeThemeMode -> {
                viewModel.updateClickCountAd()
                viewModel.updateCachedThemeMode(it.theme)
            }
        }
    }

    // interstitial ad
    if (viewModel.clickCountForAd.intValue > ConstantValue.INTERSTITIAL_COUNT) {
        LocalFocusManager.current.clearFocus()
        MetaInterstitial {
            viewModel.updateClickCountAd(true)
        }
    }

}

private data class UiState(
    val themeCode: String,
    val localeCode: String,
    val pageTitle: Int = R.string.app_name,
    val foods: List<Food> = emptyList()
)

private sealed class UiEvent {
    class SearchFoods(val search: String) : UiEvent()
    class FilterFoods(val filterType: FilterType) : UiEvent()
    class ChangeLanguage(val localeCode: String) : UiEvent()
    class ChangeThemeMode(val theme: String) : UiEvent()
}

@Composable
private fun HomePageContent(
    uiState: UiState,
    onAction: (UiEvent) -> Unit = {}
) {
    val isDarkMode = AppThemeMode.isDarkMode(uiState.themeCode)
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val isScrolledOnTop by remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset == 0 }
    }
    val progress by animateFloatAsState(
        targetValue = if (isScrolledOnTop) 0f else 1f,
        tween(500, easing = LinearOutSlowInEasing), label = ""
    )

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val configuration = LocalConfiguration.current
    var showFilterDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showAboutAppDialog by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }

    // Set locale
    LocalizationUpdater(uiState.localeCode)

    Box {
        // Main layout
        MotionLayout(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
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
                    .fillMaxHeight(if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 0.18f else 0.28f)
                    .background(color = if (isDarkMode) MaterialTheme.colorScheme.background else ColorPrimary)
            )

            Icon(
                modifier = Modifier
                    .layoutId("setting")
                    .clip(CircleShape)
                    .clickable {
                        showSheet = true
                        focusManager.clearFocus()
                    }
                    .padding(Dimens.MARGIN_10),
                painter = painterResource(id = R.drawable.ic_round_menu),
                tint = if (isDarkMode) ColorPrimary else Color.White,
                contentDescription = ""
            )

            Icon(
                modifier = Modifier
                    .layoutId("filter")
                    .clip(CircleShape)
                    .clickable {
                        showFilterDialog = true
                        focusManager.clearFocus()
                    }
                    .padding(Dimens.MARGIN_10),
                painter = painterResource(id = R.drawable.ic_filter_list),
                tint = if (isDarkMode) ColorPrimary else Color.White,
                contentDescription = ""
            )

            TitleTextView(
                modifier = Modifier.layoutId("title"),
                text = stringResource(id = uiState.pageTitle),
                color = if (isDarkMode) ColorPrimary else Color.White,
                fontSize = Dimens.TEXT_HEADING_2
            )


            // Item list
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
                items(uiState.foods) { item ->
                    FoodListItemView(localeCode = uiState.localeCode, food = item)
                }
            }


            // searchBox
            HomeSearchBarView(
                modifier = Modifier.layoutId("search"),
                hint = stringResource(id = R.string.search),
                iconClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                },
                onValueChange = {
                    onAction(UiEvent.SearchFoods(it))
                }
            )

            // Filter dialog
            FilterDialog(
                isShow = showFilterDialog,
                onDismissRequest = {
                    it?.let {
                        onAction(UiEvent.FilterFoods(it))
                    }
                    showFilterDialog = false
                }
            )

            // About Dialog
            AboutAppDialog(showAboutAppDialog) {
                showAboutAppDialog = false
            }

            // Language dialog
            LanguageChooseDialog(
                isShow = showLanguageDialog,
                localeCode = uiState.localeCode
            ) {
                it?.let {
                    onAction(UiEvent.ChangeLanguage(it.localeCode))
                }
                showLanguageDialog = false
            }

            // Theme choose dialog
            ThemeModeDialog(
                isShow = showThemeDialog,
                themeCode = uiState.themeCode
            ) {
                it?.let {
                    onAction(UiEvent.ChangeThemeMode(it.themeCode))
                }
                showThemeDialog = false
            }

            // Setting bottom sheet
            SettingModalSheet(showSheet) {
                showSheet = false
                when (it) {
                    0 -> showLanguageDialog = true
                    1 -> showThemeDialog = true
                    2 -> showAboutAppDialog = true
                    3 -> {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/dev?id=5729357381500909341")
                            )
                        )
                    }
                }
            }

        }

        // banner ad
        MetaBanner(
            modifier = Modifier.align(Alignment.BottomCenter),
            adSize = AdSize.BANNER_HEIGHT_50
        )
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
        start.linkTo(parent.start, Dimens.MARGIN_10)
    }
    val filter = createRefFor("filter")
    constrain(filter) {
        top.linkTo(parent.top, Dimens.MARGIN_MEDIUM_2)
        end.linkTo(parent.end, Dimens.MARGIN_10)
    }
    val title = createRefFor("title")
    constrain(title) {
        alpha = 1f
        scaleY = 1f
        scaleX = 1f
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
        alpha = 0f
        scaleY = 0.6f
        scaleX = 0.6f
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
private fun FoodListItemView(
    localeCode: String,
    food: Food
) {
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
                .padding(vertical = Dimens.MARGIN_LARGE, horizontal = Dimens.MARGIN_MEDIUM),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CoilAsyncImage(
                    imageUrl = food.imgOne,
                    modifier = Modifier
                        .size(Dimens.IMAGE_CARD_SIZE)
                        .clip(Shapes.medium)
                        .background(color = Color.White),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM_2))
                Text(
                    text = food.getFoodOne(localeCode),
                    fontSize = Dimens.TEXT_REGULAR,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(Dimens.MARGIN_SMALL))
                Text(text = "Vs", fontSize = Dimens.TEXT_XLARGE)
                Spacer(modifier = Modifier.height(Dimens.MARGIN_10))
                TitleTextView(
                    text = food.getFoodDie(localeCode),
                    color = colorResource(id = R.color.colorPrimary),
                    fontSize = Dimens.TEXT_REGULAR_2
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoilAsyncImage(
                    imageUrl = food.imgTwo,
                    modifier = Modifier
                        .size(Dimens.IMAGE_CARD_SIZE)
                        .clip(Shapes.medium)
                        .background(color = Color.White),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM_2))
                Text(
                    text = food.getFoodTwo(localeCode),
                    fontSize = Dimens.TEXT_REGULAR,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun HomeSearchBarView(
    modifier: Modifier,
    hint: String,
    iconClick: () -> Unit,
    onValueChange: (String) -> Unit
) {

    var textSearchBox by rememberSaveable { mutableStateOf("") }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.MARGIN_SMALL
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(Shapes.medium)
                .padding(horizontal = Dimens.MARGIN_MEDIUM)

        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        iconClick()
                    }
                    .padding(Dimens.MARGIN_MEDIUM),
                painter = painterResource(id = R.drawable.ic_search_normal),
                contentDescription = ""
            )

            TextField(
                value = textSearchBox,
                onValueChange = {
                    textSearchBox = it
                    onValueChange(it)
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = Dimens.TEXT_REGULAR
                ),
                placeholder = {
                    Text(
                        text = hint,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                        fontSize = Dimens.TEXT_REGULAR
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(textSearchBox.length > 1) {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    textSearchBox = ""
                                    onValueChange("")
                                }
                                .padding(Dimens.MARGIN_MEDIUM),
                            painter = painterResource(id = R.drawable.ic_delete_search),
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingModalSheet(
    isShow: Boolean,
    onDismiss: (index: Int) -> Unit
) {
    if (!isShow) return
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss(999) },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        TitleTextView(
            modifier = Modifier.padding(horizontal = Dimens.MARGIN_20),
            text = stringResource(id = R.string.setting),
            color = colorResource(id = R.color.colorPrimary)
        )

        Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM_2))

        ConstantValue.settingList.forEachIndexed { index, pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDismiss(index) }
                    .padding(horizontal = Dimens.MARGIN_20),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = pair.first),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM))
                Text(
                    text = stringResource(id = pair.second),
                    modifier = Modifier.padding(16.dp),
                )
            }

            if (index == ConstantValue.settingList.lastIndex) {
                Spacer(modifier = Modifier.height(Dimens.MARGIN_XXXLARGE))
            } else {
                HorizontalDivider()
            }
        }
    }
}

@Composable
private fun FilterDialog(
    isShow: Boolean,
    onDismissRequest: (item: FilterType?) -> Unit,
) {
    if (!isShow) return
    Dialog(onDismissRequest = { onDismissRequest(null) }) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.filter_loading))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            restartOnPlay = true,
            iterations = LottieConstants.IterateForever,
        )

        Card(shape = Shapes.medium) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimation(
                    modifier = Modifier.height(150.dp),
                    composition = composition,
                    progress = { progress },
                )

                ConstantValue.filterList.forEachIndexed { index, item ->
                    Column(
                        modifier = Modifier
                            .clickable {
                                onDismissRequest(item)
                            }
                            .padding(horizontal = Dimens.MARGIN_LARGE)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.width(Dimens.MARGIN_MEDIUM_2))
                            Text(
                                text = stringResource(id = item.title),
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                        if (index == ConstantValue.filterList.lastIndex) {
                            Spacer(modifier = Modifier.height(Dimens.MARGIN_MEDIUM))
                        } else {
                            HorizontalDivider()
                        }
                    }
                }

            }
        }
    }
}


@Composable
private fun LanguageChooseDialog(
    isShow: Boolean,
    localeCode: String,
    onDismissRequest: (locale: Localization?) -> Unit,
) {
    if (!isShow) return
    RadioSelectionDialog(
        items = ConstantValue.languageList,
        onDismissRequest = onDismissRequest
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .selectable(
                    selected = (it.localeCode == localeCode),
                    enabled = (it.localeCode != localeCode),
                    onClick = {
                        onDismissRequest(it)
                    },
                    role = Role.RadioButton
                )
                .padding(horizontal = Dimens.MARGIN_20),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (it.localeCode == localeCode),
                onClick = null
            )
            Text(
                text = stringResource(id = it.title),
                modifier = Modifier.padding(start = Dimens.MARGIN_MEDIUM_2)
            )
        }
    }
}

@Composable
private fun ThemeModeDialog(
    isShow: Boolean,
    themeCode: String,
    onDismissRequest: (themeMode: AppThemeMode?) -> Unit,
) {
    if (!isShow) return
    RadioSelectionDialog(
        items = ConstantValue.appThemeModes,
        onDismissRequest = onDismissRequest
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .selectable(
                    selected = (it.themeCode == themeCode),
                    enabled = (it.themeCode != themeCode),
                    onClick = {
                        onDismissRequest(it)
                    },
                    role = Role.RadioButton
                )
                .padding(horizontal = Dimens.MARGIN_20),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (it.themeCode == themeCode),
                onClick = null
            )
            Text(
                text = stringResource(id = it.title),
                modifier = Modifier.padding(start = Dimens.MARGIN_MEDIUM_2)
            )
        }
    }
}

@Composable
fun AboutAppDialog(
    isShow: Boolean,
    onDismissRequest: () -> Unit,
) {
    if (!isShow) return
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.wrapContentHeight(),
            shape = Shapes.medium
        ) {
            CoilAsyncImage(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = R.drawable.img_fooddi_landscape,
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun HomePagePreview() {
    FoodDiAppTheme {
        Surface {
            HomePageContent(
                uiState = UiState(
                    themeCode = AppThemeMode.LIGHT_MODE,
                    localeCode = Localization.ENGLISH,
                    foods = listOf(
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                    )
                )
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun HomePagePreviewNight() {
    FoodDiAppTheme {
        Surface {
            HomePageContent(
                uiState = UiState(
                    themeCode = AppThemeMode.DARK_MODE,
                    localeCode = Localization.ENGLISH,
                    foods = listOf(
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                        Food.fake(),
                    )
                )
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SettingModalSheetPreview() {
    FoodDiAppTheme {
        Surface {
            SettingModalSheet(true) {

            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FilterDialogPreview() {
    FoodDiAppTheme {
        Surface {
            FilterDialog(
                isShow = true,
                onDismissRequest = {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun LanguageChooseDialogPreview() {
    FoodDiAppTheme {
        Surface {
            LanguageChooseDialog(
                isShow = true,
                localeCode = Localization.ENGLISH
            ) {}
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun ThemeModeDialogPreview() {
    FoodDiAppTheme {
        Surface {
            ThemeModeDialog(
                isShow = true,
                themeCode = AppThemeMode.LIGHT_MODE,
            ) {}
        }
    }
}

@Preview
@Composable
private fun AboutAppDialogPreview() {
    FoodDiAppTheme {
        Surface {
            AboutAppDialog(true) {
            }
        }
    }
}
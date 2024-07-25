package com.pthw.food.ui.home.adview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdListener
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import com.pthw.food.R
import com.pthw.food.composable.LoadingDialog
import timber.log.Timber

/**
 * Created by P.T.H.W on 25/07/2024.
 */

@Composable
fun MetaBanner(
    modifier: Modifier = Modifier,
    adSize: AdSize = AdSize.BANNER_HEIGHT_50
) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = {
            AdView(it, it.getString(R.string.meta_test_unit), adSize).apply {
                loadAd(buildLoadAdConfig().withAdListener(object : AdListener {
                    override fun onError(p0: Ad?, p1: AdError?) {
                        Timber.e("MetaAd: ${p1?.errorMessage}")
                    }

                    override fun onAdLoaded(p0: Ad?) {
                        Timber.i("MetaAd: onAdLoaded")
                    }

                    override fun onAdClicked(p0: Ad?) {
                        Timber.i("MetaAd: onAdClicked")
                    }

                    override fun onLoggingImpression(p0: Ad?) {
                        Timber.i("MetaAd: onLoggingImpression")
                    }
                }).build())
            }
        },
    )
}

@Composable
fun MetaInterstitial(
    onFinished: () -> Unit
) {
    Timber.i("Reached: MetaInterstitial")

    val context = LocalContext.current
    var adLoadSuccess by remember { mutableStateOf(false) }
    val interstitialAd: InterstitialAd = remember {
        InterstitialAd(
            context,
            context.getString(R.string.meta_test_unit)
        )
    }

    interstitialAd.apply {
        loadAd(
            buildLoadAdConfig().withAdListener(object : InterstitialAdListener {
                override fun onError(p0: Ad?, p1: AdError?) {
                    Timber.e("Ad was onError.")
                }

                override fun onAdLoaded(p0: Ad?) {
                    Timber.i("Ad was loaded.")
                    adLoadSuccess = true
                }

                override fun onAdClicked(p0: Ad?) {
                    Timber.i("Ad was onAdClicked.")
                }

                override fun onLoggingImpression(p0: Ad?) {
                    Timber.i("Ad was onLoggingImpression.")
                }

                override fun onInterstitialDisplayed(p0: Ad?) {
                    Timber.i("Ad was onInterstitialDisplayed.")
                }

                override fun onInterstitialDismissed(p0: Ad?) {
                    Timber.i("Ad was onInterstitialDismissed.")
                }
            }).build()
        )
    }

    if (adLoadSuccess) {
        interstitialAd.show()
        onFinished()
    } else {
        LoadingDialog()
    }

}
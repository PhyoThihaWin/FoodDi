package com.pthw.food.ui.home.ironsource

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.ironsource.mediationsdk.ISBannerSize
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.sdk.LevelPlayBannerListener
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener
import com.pthw.food.utils.findActivity
import timber.log.Timber

@Composable
fun IronSourceBanner(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = {
            IronSource.createBanner(it.findActivity(), ISBannerSize.LARGE).apply {
                IronSource.loadBanner(this)
                levelPlayBannerListener = object : LevelPlayBannerListener {
                    override fun onAdLoaded(p0: AdInfo?) {
                        Timber.i("IronSource onAdLoaded!")
                    }

                    override fun onAdLoadFailed(p0: IronSourceError?) {
                        Timber.i("IronSource onAdLoadFailed!")
                    }

                    override fun onAdClicked(p0: AdInfo?) {
                        Timber.i("IronSource onAdClicked!")
                    }

                    override fun onAdLeftApplication(p0: AdInfo?) {
                        Timber.i("IronSource onAdLeftApplication!")
                    }

                    override fun onAdScreenPresented(p0: AdInfo?) {
                        Timber.i("IronSource onAdScreenPresented!")
                    }

                    override fun onAdScreenDismissed(p0: AdInfo?) {
                        Timber.i("IronSource onAdScreenDismissed!")
                    }
                }
            }
        },
    )
}

fun ironSourceInterstitial() {
    IronSource.loadInterstitial()
    IronSource.setLevelPlayInterstitialListener(object : LevelPlayInterstitialListener {
        override fun onAdReady(p0: AdInfo?) {
            IronSource.showInterstitial()
            Timber.i("Interstitial onAdReady!")
        }

        override fun onAdLoadFailed(p0: IronSourceError?) {
            Timber.i("Interstitial onAdLoadFailed!")
        }

        override fun onAdOpened(p0: AdInfo?) {
            Timber.i("Interstitial onAdOpened!")
        }

        override fun onAdShowSucceeded(p0: AdInfo?) {
            Timber.i("Interstitial onAdShowSucceeded!")
        }

        override fun onAdShowFailed(p0: IronSourceError?, p1: AdInfo?) {
            Timber.i("Interstitial onAdShowFailed!")
        }

        override fun onAdClicked(p0: AdInfo?) {
            Timber.i("Interstitial onAdClicked!")
        }

        override fun onAdClosed(p0: AdInfo?) {
            Timber.i("Interstitial onAdClosed!")
        }
    })
}

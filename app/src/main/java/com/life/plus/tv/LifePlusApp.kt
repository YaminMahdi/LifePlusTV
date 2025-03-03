package com.life.plus.tv

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import com.chesire.lifecyklelog.LifecykleLog
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LifePlusApp: ImageLoaderFactory, Application() {

    override fun onCreate() {
        super.onCreate()
        LifecykleLog.initialize(this)
        LifecykleLog.requireAnnotation = false
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(500)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }
}
package com.freshfoodz.helper

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.freshfoodz.api.ApiConstants
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initGson()
        initRetrofit()
    }

    private fun initRetrofit() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val cacheSize = 40 * 1024 * 1024 // 20 MB
        val cache = Cache(cacheDir, cacheSize.toLong())
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(70, TimeUnit.SECONDS)
            .cache(cache)
            .addNetworkInterceptor { chain: Interceptor.Chain ->
                val originalResponse = chain.proceed(chain.request())
                if (isConnected()) {
                    val maxAge = 60 * 5 // read from cache for 5 minute
                    return@addNetworkInterceptor originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    return@addNetworkInterceptor originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
            }
            .connectTimeout(200, TimeUnit.SECONDS)
            .readTimeout(200,TimeUnit.SECONDS)
            .addInterceptor(interceptor)
           // .addInterceptor(HeaderInterceptor())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private class HeaderInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val authKey =
                "cIjbGRy8nHo:APA91bEscUK1E_IYEupC7uuUkEsp9JafC0b8m48UxyrhFuH8Jkbcys4HGH-1GXDFqqA25I3XwFptdmJ8-XYj79Vfj-2V6yDZ0EA26lx5ltxbcBw3-6SO4ZDjscukyGIDhyNJjBOieNuW"
            val newRequest = chain.request().newBuilder()
                .addHeader("App-Token", authKey)
                .build()
            return chain.proceed(newRequest)
        }
    }

    private fun initGson() {
        gson = Gson()
    }

    companion object {
        var gson: Gson? = null
            private set
        var retrofit: Retrofit? = null
            private set
    }
}
package com.android.wonderlabs.net

import com.android.wonderlabs.model.DataResponse
import com.android.wonderlabs.utils.BASE_URL
import com.android.wonderlabs.utils.GET_VOLUME
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET(GET_VOLUME)
    fun getData(@Query("resource_id") resourceID: String): Observable<DataResponse>

    companion object {
        private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        fun create(): APIService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build()

            return retrofit.create(APIService::class.java)
        }
    }
}
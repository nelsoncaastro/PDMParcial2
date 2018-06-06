package me.nelsoncastro.pdmparcial2.webserver

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GameNewsAPI {

    @FormUrlEncoded
    @POST("/login")
    fun login(@Field("user") username: String, @Field("password") password: String): Single<String>
}
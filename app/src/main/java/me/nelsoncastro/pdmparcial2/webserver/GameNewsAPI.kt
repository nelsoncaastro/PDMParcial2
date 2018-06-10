package me.nelsoncastro.pdmparcial2.webserver

import io.reactivex.Single
import me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle
import retrofit2.http.*

interface GameNewsAPI {

    @FormUrlEncoded
    @POST("/login")
    fun login(@Field("user") username: String, @Field("password") password: String): Single<String>

    @GET("/news")
    fun getNews(@Header("Authorization") auth: String): Single<List<Nouvelle>>
}
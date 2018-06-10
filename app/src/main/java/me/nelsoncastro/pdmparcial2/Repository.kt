package me.nelsoncastro.pdmparcial2

import android.app.Application
import android.arch.lifecycle.LiveData
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import me.nelsoncastro.pdmparcial2.database.NouvelleDao
import me.nelsoncastro.pdmparcial2.database.RoomDatabase
import me.nelsoncastro.pdmparcial2.entities.Nouvelle
import me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI
import me.nelsoncastro.pdmparcial2.webserver.NouvelleDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository(application: Application) {

    var mNouvelleDao: NouvelleDao? = null
    var mAllNouvelle: LiveData<List<Nouvelle>>? = null
    var GameNewsAPI: GameNewsAPI? = null
    val compositeDisposable = CompositeDisposable()
    val compositeeDisposable = CompositeDisposable()

    init {
        val db = RoomDatabase.getDatabase(application)
        mNouvelleDao = db!!.nouvelleDao()
        mAllNouvelle = mNouvelleDao?.getAll()
        GameNewsAPI = createGameNewsAPI()
    }

    fun getAll(): LiveData<List<Nouvelle>>{
        return mAllNouvelle!!
    }


    fun uptodateNouvelles(auth: String){
        compositeeDisposable.add(GameNewsAPI.getNews(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getNewss()))
    }

    fun insert(nouvelle: me.nelsoncastro.pdmparcial2.entities.Nouvelle){
        compositeDisposable.add(Observable
                .fromCallable { mNouvelleDao!!.insert(nouvelle) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    private fun createGameNewsAPI(): GameNewsAPI{
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle::class.java, NouvelleDeserializer())
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI::class.java)
    }

    private fun getNewss(): DisposableSingleObserver<List<me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle>>{
        return  object : DisposableSingleObserver<List<me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle>>(){
            override fun onSuccess(nouvelles: List<me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle>) {
                if (!nouvelles.isEmpty()){
                    for(nou in nouvelles) insert(Nouvelle(nou._id,nou.title,nou.body,nou.description,nou.game,nou.coverImage,nou.created_date))
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

        }
    }
}
package me.nelsoncastro.pdmparcial2.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import me.nelsoncastro.pdmparcial2.database.CategorieDao
import me.nelsoncastro.pdmparcial2.database.RoomDatabase
import me.nelsoncastro.pdmparcial2.entities.Categorie
import me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI
import me.nelsoncastro.pdmparcial2.webserver.deserializers.CategorieDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class CategorieRepository(application: Application) {
    var mCategorieDao: CategorieDao? = null
    var mAllCategorie: LiveData<List<Categorie>>? = null
    var GameNewsAPI: GameNewsAPI? = null
    val compositeDisposable = CompositeDisposable()
    val compositeeDisposable = CompositeDisposable()

    init {
        val db = RoomDatabase.getDatabase(application)
        mCategorieDao = db!!.categorieDao()
        mAllCategorie = mCategorieDao?.getAll()
        GameNewsAPI = createGameNewsAPI()
    }

    fun getAll(): LiveData<List<Categorie>> = mAllCategorie!!

    fun uptodateCategories(auth: String){
        compositeeDisposable.add(GameNewsAPI!!.getCategories(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getCategoriess()))
    }

    fun insert(categorie: Categorie){
        compositeDisposable.add(Observable
                .fromCallable { mCategorieDao!!.insert(categorie) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    private fun createGameNewsAPI(): GameNewsAPI{
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(ArrayList::class.java, CategorieDeserializer())
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI::class.java)
    }

    private fun getCategoriess(): DisposableSingleObserver<List<String>>{
        return object : DisposableSingleObserver<List<String>>(){
            override fun onSuccess(categories : List<String>) {
                if (!categories.isEmpty()){
                    for (cat in categories) insert(Categorie(cat))
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

        }
    }
}
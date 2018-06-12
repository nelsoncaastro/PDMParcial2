package me.nelsoncastro.pdmparcial2.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import me.nelsoncastro.pdmparcial2.database.JoueurDao
import me.nelsoncastro.pdmparcial2.database.RoomDatabase
import me.nelsoncastro.pdmparcial2.entities.Joueur
import me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI
import me.nelsoncastro.pdmparcial2.webserver.deserializers.JoueurDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class JoueurRepository(application: Application) {

    var mJoueurDao: JoueurDao? = null
    var mAllJoueur: LiveData<List<Joueur>>? = null
    var GameNewsAPI: GameNewsAPI? = null
    val compositeDisposable = CompositeDisposable()
    val compositeeDisposable = CompositeDisposable()

    init {
        val db = RoomDatabase.getDatabase(application)
        mJoueurDao = db!!.joueurDao()
        mAllJoueur = mJoueurDao?.getAll()
        GameNewsAPI = createGameNewsAPI()
    }

    fun getAll(): LiveData<List<Joueur>> = mAllJoueur!!

    fun uptodateJoueurs(auth: String){
        compositeeDisposable.add(GameNewsAPI!!.getPlayers(auth).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getJoueurss()))
    }

    fun insert(joueur: Joueur){
        compositeDisposable.add(Observable.fromCallable { mJoueurDao!!.insert(joueur) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    private fun createGameNewsAPI(): GameNewsAPI{
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(me.nelsoncastro.pdmparcial2.entitieesapi.Joueur::class.java, JoueurDeserializer())
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI::class.java)
    }

    private fun getJoueurss(): DisposableSingleObserver<List<me.nelsoncastro.pdmparcial2.entitieesapi.Joueur>>{
        return object : DisposableSingleObserver<List<me.nelsoncastro.pdmparcial2.entitieesapi.Joueur>>(){
            override fun onSuccess(joueurs: List<me.nelsoncastro.pdmparcial2.entitieesapi.Joueur>) {
                if(!joueurs.isEmpty()){
                    for (joue in joueurs) insert(Joueur(joue._id,joue.name,joue.biografia,joue.avatar,joue.game))
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

        }
    }
}
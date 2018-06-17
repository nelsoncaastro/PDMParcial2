package me.nelsoncastro.pdmparcial2.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.widget.Toast
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import me.nelsoncastro.pdmparcial2.R
import me.nelsoncastro.pdmparcial2.database.EtudiantDao
import me.nelsoncastro.pdmparcial2.database.RoomDatabase
import me.nelsoncastro.pdmparcial2.entities.Etudiant
import me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI
import me.nelsoncastro.pdmparcial2.webserver.deserializers.EtudiantDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class EtudiantRepository(application: Application) {

    var mEtudiantDao: EtudiantDao? = null
    var GameNewsAPI: GameNewsAPI? = null
    var mAllEtudiant: LiveData<List<Etudiant>>? = null
    val contexte = application.applicationContext
    val compositeDisposable = CompositeDisposable()
    val compositeeDisposable = CompositeDisposable()

    init {
        val db = RoomDatabase.getDatabase(application)
        mEtudiantDao = db?.etudiantDao()
        mAllEtudiant = mEtudiantDao?.getAll()
        GameNewsAPI = createGameNewsAPI()
    }

    fun getAll(): LiveData<List<Etudiant>> = mAllEtudiant!!

    fun uptodateEtudiants(auth: String) {
        compositeeDisposable.add(GameNewsAPI!!.getUsers(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun insert(etudiant: Etudiant){
        compositeDisposable.add(Observable
                .fromCallable { mEtudiantDao!!.insert(etudiant) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    private fun createGameNewsAPI(): GameNewsAPI{
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(me.nelsoncastro.pdmparcial2.entitieesapi.Etudiant::class.java, EtudiantDeserializer())
                .create()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI::class.java)
    }

    private fun getEtudiantss(): DisposableSingleObserver<List<me.nelsoncastro.pdmparcial2.entitieesapi.Etudiant>>{
        return object : DisposableSingleObserver<List<me.nelsoncastro.pdmparcial2.entitieesapi.Etudiant>>(){
            override fun onSuccess(etuds: List<me.nelsoncastro.pdmparcial2.entitieesapi.Etudiant>) {
                var auxi = ""
                if (!etuds.isEmpty()){
                    for (etud in etuds) insert(Etudiant(etud._id,etud.user,etud.password,etud.favoriteNews!![0]))
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                Toast.makeText(contexte, contexte.getString(R.string.sesion_expiro), Toast.LENGTH_LONG).show()
            }

        }
    }
}
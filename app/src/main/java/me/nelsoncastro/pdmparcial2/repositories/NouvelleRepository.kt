package me.nelsoncastro.pdmparcial2.repositories

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import me.nelsoncastro.pdmparcial2.LoginActivity
import me.nelsoncastro.pdmparcial2.MainActivity
import me.nelsoncastro.pdmparcial2.R
import me.nelsoncastro.pdmparcial2.database.NouvelleDao
import me.nelsoncastro.pdmparcial2.database.RoomDatabase
import me.nelsoncastro.pdmparcial2.entities.Nouvelle
import me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI
import me.nelsoncastro.pdmparcial2.webserver.deserializers.NouvelleDeserializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NouvelleRepository(application: Application) {

    var mNouvelleDao: NouvelleDao? = null
    var mAllNouvelle: LiveData<List<Nouvelle>>? = null
    var GameNewsAPI: GameNewsAPI? = null
    val contexte = application.applicationContext
    val compositeDisposable = CompositeDisposable()
    val compositeeDisposable = CompositeDisposable()
    val compositeeeDisposable = CompositeDisposable()

    init {
        val db = RoomDatabase.getDatabase(application)
        mNouvelleDao = db!!.nouvelleDao()
        mAllNouvelle = mNouvelleDao?.getAll()
        GameNewsAPI = createGameNewsAPI()
    }

    fun getAll(): LiveData<List<Nouvelle>> = mAllNouvelle!!

    fun getAllByJeux(jeux: String): LiveData<List<Nouvelle>> = mNouvelleDao!!.getNouvelleByJeux(jeux)

    fun getAllFavoris(): LiveData<List<Nouvelle>> = mNouvelleDao!!.getAllNouvelleFavoris()

    fun getAllByTitre(titre: String): LiveData<List<Nouvelle>> = mNouvelleDao!!.getNouvelleByTitre(titre)

    fun setFavoris(value: Int, id: String) {
        compositeeeDisposable.add(Observable
                .fromCallable { mNouvelleDao!!.setFavoris(value, id) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun uptodateNouvelles(auth: String, Refreshy: SwipeRefreshLayout) {
        compositeeDisposable.add(GameNewsAPI!!.getNews(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getNewss(Refreshy)))
    }

    fun insert(nouvelle: me.nelsoncastro.pdmparcial2.entities.Nouvelle) {
        compositeDisposable.add(Observable
                .fromCallable { mNouvelleDao!!.insert(nouvelle) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    private fun createGameNewsAPI(): GameNewsAPI {
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle::class.java, NouvelleDeserializer())
                .create()
        val bouncer = OkHttpClient
                .Builder()
                .addInterceptor {
                    chain ->
                    val response = chain.proceed(chain.request())
                    if (response.code() != 401) {response} else {
                        //startLogin(contexte as Activity)
                        chain.proceed(chain.request())
                    }
                 }.build()
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .client(bouncer)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI::class.java)
    }

    private fun getNewss(Refreshy: SwipeRefreshLayout): DisposableSingleObserver<List<me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle>> {
        return object : DisposableSingleObserver<List<me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle>>() {
            override fun onSuccess(nouvelles: List<me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle>) {
                if (!nouvelles.isEmpty()) {
                    Refreshy.isRefreshing = false
                    for (nou in nouvelles) insert(Nouvelle(nou._id, nou.title, nou.body, nou.description, nou.game, nou.coverImage, converterDate(nou.created_date).toInt(), 0))
                }
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                Toast.makeText(contexte, contexte.getString(R.string.sesion_expiro), Toast.LENGTH_LONG).show()
                Refreshy.isRefreshing = false
                Log.d("ERROR", "Falla en el onError ${e.message}")
            }

        }
    }

    private fun converterDate(date: String): Long {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        var lon: Long? = null
        lon = try {
            df.parse(date).time
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
        return lon
    }

    private fun pirata(date: String): Long = try {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(date).time
    } catch (e: ParseException) {
        e.printStackTrace()
        0
    }

    private fun startLogin(activity: Activity){
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finish()
    }
}
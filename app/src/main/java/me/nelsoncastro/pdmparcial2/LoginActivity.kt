package me.nelsoncastro.pdmparcial2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import me.nelsoncastro.pdmparcial2.entitieesapi.Nouvelle
import me.nelsoncastro.pdmparcial2.webserver.GameNewsAPI
import me.nelsoncastro.pdmparcial2.webserver.NouvelleDeserializer
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoginActivity: AppCompatActivity() {

    var usertemp: EditText? = null
    var passtemp: EditText? = null
    var btntemp: Button? = null
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usertemp = findViewById(R.id.edittemp)
        passtemp = findViewById(R.id.edittemp2)
        btntemp = findViewById(R.id.buttontemp)

        val GameNewsAPI = createGameNewsAPI()

        btntemp?.setOnClickListener {
            compositeDisposable.add(GameNewsAPI
                    .login(usertemp?.text.toString(), passtemp?.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(loginObserver()))
        }
    }

    private fun createGameNewsAPI(): GameNewsAPI{
        val retrofit = Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(GameNewsAPI::class.java)
    }

    private fun loginObserver(): DisposableSingleObserver<String>{
        return object : DisposableSingleObserver<String>(){
            override fun onSuccess(token: String) {
                val sharedPref = getSharedPreferences("log", Context.MODE_PRIVATE) ?: return
                with(sharedPref.edit()){
                    putString(getString(R.string.saved_token), token.subSequence(10,token.lastIndex-1).toString())
                    apply()
                }
                //Log.d("MENSAJE",token.subSequence(10,token.lastIndex-1).toString())
                Toast.makeText(applicationContext, sharedPref.getString(getString(R.string.saved_token), "Nelson mi dog"),Toast.LENGTH_LONG).show()
                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            }
            override fun onError(e: Throwable) {
                //tokentemp?.text = e.printStackTrace().toString()
            }

        }
    }
}
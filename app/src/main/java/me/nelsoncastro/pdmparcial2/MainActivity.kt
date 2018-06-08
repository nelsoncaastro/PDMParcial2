package me.nelsoncastro.pdmparcial2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE) ?: return
        val token = sharedPreferences.getString()
        setContentView(R.layout.activity_main)


    }

    private fun checkSharedPref(sharedPreferences: SharedPreferences){
        startActivity(Intent(this.baseContext, LoginActivity::class.java))
        finish()
    }

}

package me.nelsoncastro.pdmparcial2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this.baseContext, LoginActivity::class.java))
        finish()
        setContentView(R.layout.activity_main)


    }

}

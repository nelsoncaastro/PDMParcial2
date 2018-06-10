package me.nelsoncastro.pdmparcial2

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import me.nelsoncastro.pdmparcial2.entities.Nouvelle

class MainActivity : AppCompatActivity() {

    var mNouvelleView: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = this.getSharedPreferences("log", Context.MODE_PRIVATE) ?: return
        /**with(sharedPref.edit()){
            clear()
            apply()
        }**/
        checkToken(if (sharedPref.contains(getString(R.string.saved_token))) sharedPref.getString(getString(R.string.saved_token),"nelson dog") else "nelson dog")
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.tooly)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = NouvelleAdapter(this)

        val manager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 0) 2 else 1
            }
        }

        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        mNouvelleView = ViewModelProviders.of(this).get(ViewModel::class.java)
        mNouvelleView!!.putUp2date("Beared " + sharedPref.getString(getString(R.string.saved_token),"nelson dog"))
        mNouvelleView!!.getAllNouvelles().observe(this, Observer<List<Nouvelle>>{ t ->  adapter.setNouvelles(t!!)})


    }

    private fun checkToken(value: String?){
        if (value.equals("nelson dog")) {
            startActivity(Intent(this.baseContext, LoginActivity::class.java))
            finish()
        }
    }

}



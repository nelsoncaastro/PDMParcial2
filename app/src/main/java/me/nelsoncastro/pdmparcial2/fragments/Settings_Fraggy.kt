package me.nelsoncastro.pdmparcial2.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import me.nelsoncastro.pdmparcial2.LoginActivity
import me.nelsoncastro.pdmparcial2.R

class Settings_Fraggy: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.settings_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val butty = view.findViewById<Button>(R.id.buttoutty)
        butty.setOnClickListener {
            val sharedPref = this.activity?.getSharedPreferences("log", Context.MODE_PRIVATE)
            with(sharedPref!!.edit()){
                clear()
                apply()
            }
            startActivity(Intent(this.activity!!, LoginActivity::class.java))
            this.activity?.finish()
        }
    }
}
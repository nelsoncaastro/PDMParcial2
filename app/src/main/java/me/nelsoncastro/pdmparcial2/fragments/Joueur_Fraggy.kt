package me.nelsoncastro.pdmparcial2.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.nelsoncastro.pdmparcial2.JoueurAdapter
import me.nelsoncastro.pdmparcial2.NouvelleAdapter
import me.nelsoncastro.pdmparcial2.R
import me.nelsoncastro.pdmparcial2.entities.Joueur
import me.nelsoncastro.pdmparcial2.entities.Nouvelle
import me.nelsoncastro.pdmparcial2.viewmodels.JoueurViewModel
import me.nelsoncastro.pdmparcial2.viewmodels.NouvelleViewModel

class Joueur_Fraggy: Fragment() {
    private val CLE = "CLE"
    private var mJoueurView: JoueurViewModel? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            type = it.getString(CLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = view.context.getSharedPreferences("log", Context.MODE_PRIVATE) ?: return

        val swipy = view.findViewById<SwipeRefreshLayout>(R.id.refreshy_home)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHome)
        val adapter = JoueurAdapter(view.context)

        val manager = GridLayoutManager(view.context, 2)

        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter

        mJoueurView = ViewModelProviders.of(this).get(JoueurViewModel::class.java)
        swipy.setOnRefreshListener{mJoueurView!!.putUp2date("Beared " + sharedPref.getString(getString(R.string.saved_token),"nelson dog"), swipy)}
        mJoueurView!!.getAllJoueursByJeux(type!!).observe(this, Observer<List<Joueur>>{ t ->  adapter.setJoueur(t!!)})
    }

    companion object {
        fun newInstance(type: String) =
                Joueur_Fraggy().apply {
                    arguments = Bundle().apply {
                        putString(CLE, type)
                    }
                }
    }
}
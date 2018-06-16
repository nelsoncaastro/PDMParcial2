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
import android.widget.Toast
import me.nelsoncastro.pdmparcial2.NouvelleAdapter
import me.nelsoncastro.pdmparcial2.R
import me.nelsoncastro.pdmparcial2.viewmodels.NouvelleViewModel
import me.nelsoncastro.pdmparcial2.entities.Nouvelle



class Home_Fraggy : Fragment() {

    private val CLE = "CLE"
    private var mNouvelleView: NouvelleViewModel? = null
    private var type: String? = null
    private var adapter: NouvelleAdapter? = null

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

        val manager = GridLayoutManager(view.context, 2, GridLayoutManager.VERTICAL, false)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int = if (position % 3 == 0) 2 else 1
        }

        mNouvelleView = ViewModelProviders.of(this).get(NouvelleViewModel::class.java)
        when (type){
            "home" -> {adapter = NouvelleAdapter(view.context, false)
                mNouvelleView!!.getAllNouvelles().observe(this, Observer<List<Nouvelle>>{ t ->  adapter!!.setNouvelles(t!!)})
                swipy.setOnRefreshListener{mNouvelleView!!.putUp2date("Beared " + sharedPref.getString(getString(R.string.saved_token),"nelson dog"), swipy)}}

            "favoris" -> {adapter = NouvelleAdapter(view.context, true)
                mNouvelleView!!.getAllNouvellesFavoris().observe(this, Observer<List<Nouvelle>>{ t ->  adapter!!.setNouvellesFavoris(t!!)})}

            else -> {adapter = NouvelleAdapter(view.context, false)
                mNouvelleView!!.getAllNouvellesByJeux(type!!).observe(this, Observer<List<Nouvelle>>{ t ->  adapter!!.setNouvelles(t!!)})
                swipy.setOnRefreshListener{mNouvelleView!!.putUp2date("Beared " + sharedPref.getString(getString(R.string.saved_token),"nelson dog"), swipy)}}
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager
    }

    companion object {
        fun newInstance(type: String) =
                Home_Fraggy().apply {
                    arguments = Bundle().apply {
                        putString(CLE, type)
                    }
                }
    }
}
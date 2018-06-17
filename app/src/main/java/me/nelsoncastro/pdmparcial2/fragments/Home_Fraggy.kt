package me.nelsoncastro.pdmparcial2.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.EditText
import android.widget.Toast
import me.nelsoncastro.pdmparcial2.NouvelleAdapter
import me.nelsoncastro.pdmparcial2.R
import me.nelsoncastro.pdmparcial2.viewmodels.NouvelleViewModel
import me.nelsoncastro.pdmparcial2.entities.Nouvelle
import android.widget.TextView





class Home_Fraggy : Fragment() {

    private val CLE = "CLE"
    private var mNouvelleView: NouvelleViewModel? = null
    private var type: String? = null
    private var adapter: NouvelleAdapter? = null
    private var queryResult: List<Nouvelle> = ArrayList()
    private var searchy: SearchView? = null
    private lateinit var sharedPref: SharedPreferences
    private lateinit var swipy: SwipeRefreshLayout
    var conny: ConnectivityManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let {
            type = it.getString(CLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        conny = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        sharedPref = view.context.getSharedPreferences("log", Context.MODE_PRIVATE) ?: return

        swipy = view.findViewById(R.id.refreshy_home)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHome)

        val manager = GridLayoutManager(view.context, 2, GridLayoutManager.VERTICAL, false)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int = if (position % 3 == 0) 2 else 1
        }

        mNouvelleView = ViewModelProviders.of(this).get(NouvelleViewModel::class.java)
        when (type){
            "home" -> {adapter = NouvelleAdapter(view.context, this, false)
                mNouvelleView!!.getAllNouvelles().observe(this, Observer<List<Nouvelle>>{ t ->  adapter!!.setNouvelles(t!!)})
                swipy.setOnRefreshListener{
                    if (checkConnectivity(conny)) {mNouvelleView!!.putUp2date("Beared " + sharedPref.getString(getString(R.string.saved_token),"nelson dog"), swipy)}
                    else {Toast.makeText(activity?.applicationContext, getString(R.string.no_conexion), Toast.LENGTH_LONG).show()
                        swipy.isRefreshing = false} }
            }

            "favoris" -> {adapter = NouvelleAdapter(view.context,this, true)
                mNouvelleView!!.getAllNouvellesFavoris().observe(this, Observer<List<Nouvelle>>{ t ->  adapter!!.setNouvellesFavoris(t!!)})
            }

            else -> {adapter = NouvelleAdapter(view.context,this, false)
                mNouvelleView!!.getAllNouvellesByJeux(type!!).observe(this, Observer<List<Nouvelle>>{ t ->  adapter!!.setNouvelles(t!!)})
                swipy.setOnRefreshListener{
                    if (checkConnectivity(conny)) mNouvelleView!!.putUp2date("Beared " + sharedPref.getString(getString(R.string.saved_token),"nelson dog"), swipy)
                    else {Toast.makeText(activity?.applicationContext, getString(R.string.no_conexion), Toast.LENGTH_LONG).show()
                        swipy.isRefreshing = false} }}
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.searchy_menu, menu)

        searchy = menu?.findItem(R.id.searchy)?.actionView as SearchView
        searchy?.isSubmitButtonEnabled = true

        val query = searchy?.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        query?.hint = getString(R.string.query_hint_main)
        query?.setHintTextColor(Color.WHITE)
        query?.setTextColor(resources.getColor(R.color.colorAccent))

        searchy?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                rechercherNouvelles(query)
                searchy?.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                rechercherNouvelles(newText)
                return true
            }
        })
    }

    private fun riptide(listy: List<Nouvelle>){
        var auxi = ArrayList<Nouvelle>()
        when(type){
            "home" ->{auxi= listy as ArrayList<Nouvelle>
            adapter!!.setNouvelles(auxi)
            }
            "favoris" ->{auxi= listy as ArrayList<Nouvelle>
            adapter!!.setNouvellesFavoris(auxi)
            }
            else ->{
                for (nou in listy){
                    if (nou.game == type)  auxi.add(nou)
                }
                adapter!!.setNouvelles(auxi)
            }
        }
    }

    private fun checkConnectivity(conny: ConnectivityManager?) = conny?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED || conny?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED

    private fun rechercherNouvelles(nou: String)= mNouvelleView!!.getAllNouvellesByTitre("%$nou%").observe(this, Observer<List<Nouvelle>>{ t -> if (t!=null) riptide(t) else return@Observer})

    companion object {
        fun newInstance(type: String) =
                Home_Fraggy().apply {
                    arguments = Bundle().apply {
                        putString(CLE, type)
                    }
                }
    }
}
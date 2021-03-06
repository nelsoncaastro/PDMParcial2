package me.nelsoncastro.pdmparcial2


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import me.nelsoncastro.pdmparcial2.entities.Categorie
import me.nelsoncastro.pdmparcial2.fragments.Fraggy_Model
import me.nelsoncastro.pdmparcial2.fragments.Home_Fraggy
import me.nelsoncastro.pdmparcial2.fragments.Settings_Fraggy
import me.nelsoncastro.pdmparcial2.viewmodels.CategorieViewModel

class MainActivity : AppCompatActivity() {

    var mCategoryView: CategorieViewModel? = null
    var mDrawerLayout: DrawerLayout? = null
    var mNavigationView: NavigationView? = null
    var firstTime = true
    var toolbar: Toolbar? = null
    var conny: ConnectivityManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = this.getSharedPreferences("log", Context.MODE_PRIVATE) ?: return
        checkToken(if (sharedPref.contains(getString(R.string.saved_token))) sharedPref.getString(getString(R.string.saved_token),"nelson dog") else "nelson dog")
        setContentView(R.layout.activity_main)

        conny = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (savedInstanceState != null) firstTime = savedInstanceState.getBoolean("FIRST")

        toolbar = findViewById(R.id.tooly)
        setSupportActionBar(toolbar)

        mDrawerLayout = findViewById(R.id.drawerLayout)
        mNavigationView = findViewById(R.id.navigationView)

        mCategoryView = ViewModelProviders.of(this).get(CategorieViewModel::class.java)
        if(checkConnectivity(conny)) {mCategoryView!!.putUp2date("Beared " + sharedPref.getString(getString(R.string.saved_token),"nelson dog"))}
        mCategoryView!!.getAllCategories().observe(this, Observer<List<Categorie>>{ t ->addMenuItem(t!!)  })

        if(firstTime) setHome()

        mNavigationView!!.setNavigationItemSelectedListener { menuItem ->
            if(!(menuItem.isChecked)) selectDrawerItem(menuItem) else mDrawerLayout!!.closeDrawers()
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean("FIRST", firstTime)
        super.onSaveInstanceState(outState)
    }

    private fun checkToken(value: String?){
        if (value.equals("nelson dog")) {
            startActivity(Intent(this.baseContext, LoginActivity::class.java))
            finish()
        }
    }

    private fun selectDrawerItem(item: MenuItem){
        val fragment = when(item.itemId){
            R.id.item1 -> Home_Fraggy.newInstance("home")
            R.id.item2 -> Home_Fraggy.newInstance("favoris")
            R.id.item6 -> Settings_Fraggy()
            else -> Fraggy_Model.newInstance(item.title.toString().toLowerCase())
        }
        toolbar?.title = item.title.toString()
        item.isChecked = true
        replaceFragment(fragment)
        mDrawerLayout!!.closeDrawers()
    }

    private fun addMenuItem(catty: List<Categorie>){
        mNavigationView!!.menu.findItem(R.id.jeux).subMenu.clear()
        for(cat in catty){
            mNavigationView!!
                    .menu.findItem(R.id.jeux)
                    .subMenu.add(cat.name.toUpperCase())
                    .setCheckable(true)
                    .setIcon(R.drawable.ic_game_generic)
        }
    }

    private fun replaceFragment(fragment: Fragment?){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containermain, fragment)
        fragmentTransaction.commit()
    }

    private fun setHome(){
        firstTime = false
        //mNavigationView!!.menu.findItem(R.id.item1).isChecked = true
        replaceFragment(Home_Fraggy.newInstance("home"))
    }

    private fun checkConnectivity(conny: ConnectivityManager?) = conny?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED || conny?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED

}



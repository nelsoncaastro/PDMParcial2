package me.nelsoncastro.pdmparcial2


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import me.nelsoncastro.pdmparcial2.entities.Categorie
import me.nelsoncastro.pdmparcial2.fragments.Home_Fraggy
import me.nelsoncastro.pdmparcial2.viewmodels.CategorieViewModel

class MainActivity : AppCompatActivity() {

    var mCategoryView: CategorieViewModel? = null
    var mDrawerLayout: DrawerLayout? = null
    var mNavigationView: NavigationView? = null

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

        mDrawerLayout = findViewById(R.id.drawerLayout)
        mNavigationView = findViewById(R.id.navigationView)



        mCategoryView = ViewModelProviders.of(this).get(CategorieViewModel::class.java)
        mCategoryView!!.putUp2date("Beared " + sharedPref.getString(getString(R.string.saved_token),"nelson dog"))
        mCategoryView!!.getAllCategories().observe(this, Observer<List<Categorie>>{ t -> addMenuItem(t!!) })

        setHome()

        mNavigationView!!.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun checkToken(value: String?){
        if (value.equals("nelson dog")) {
            startActivity(Intent(this.baseContext, LoginActivity::class.java))
            finish()
        }
    }

    private fun selectDrawerItem(item: MenuItem){
        var fragment: Fragment? = null
        val fragmentClass = when(item.itemId){
            R.id.item1 -> Home_Fraggy::class.java
            else -> Home_Fraggy::class.java
        }

        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: ClassCastException){
            e.printStackTrace()
        }
        replaceFragment(fragment)
        mDrawerLayout!!.closeDrawers()
    }

    private fun addMenuItem(catty: List<Categorie>){
        mNavigationView!!.menu.findItem(R.id.jeux).subMenu.clear()
        for(cat in catty){
            mNavigationView!!.menu.findItem(R.id.jeux).subMenu.add(cat.name)
        }
    }

    private fun replaceFragment(fragment: Fragment?){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containermain, fragment)
        fragmentTransaction.commit()
    }

    private fun setHome(){
        mNavigationView!!.menu.getItem(0).isChecked = true
        val fragment = Home_Fraggy.newInstance()
        supportFragmentManager.beginTransaction()
                .replace(R.id.containermain, fragment)
                .commit()

    }

}



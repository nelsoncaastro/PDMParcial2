package me.nelsoncastro.pdmparcial2

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ViewerActivity: AppCompatActivity() {

    var curry: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        curry = this.intent.extras.getStringArrayList("CLAVIER")

        val tooly = findViewById<Toolbar>(R.id.toolbarviewer)
        val colly = findViewById<CollapsingToolbarLayout>(R.id.collapsingtoolbarviewer)
        val imngToolbar = findViewById<ImageView>(R.id.app_bar_image_viewer)
        val jeux = findViewById<TextView>(R.id.jeux_viewer)
        val horl = findViewById<TextView>(R.id.horl_viewer)
        val corps = findViewById<TextView>(R.id.body_viewer)
        val butty = findViewById<FloatingActionButton>(R.id.floatty_view)

        Picasso.with(this)
                .load(curry!![3])
                .resize((this.resources.displayMetrics.widthPixels/this.resources.displayMetrics.density).toInt(),256)
                .centerCrop()
                .error(R.drawable.himym)
                .into(imngToolbar)
        setSupportActionBar(tooly)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        colly.title = curry!![0]
        colly.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        colly.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        corps.text = curry!![1]
        jeux.text = curry!![2].toUpperCase()
        horl.text = curry!![4]
        butty.setImageResource(if (curry!![5] == "0") R.drawable.ic_favorite_empty else R.drawable.ic_favorite_full)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            android.R.id.home -> {onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}
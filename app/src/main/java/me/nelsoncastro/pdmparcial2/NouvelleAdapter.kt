package me.nelsoncastro.pdmparcial2

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.nelsoncastro.pdmparcial2.entities.Nouvelle
import me.nelsoncastro.pdmparcial2.viewmodels.NouvelleViewModel

class NouvelleAdapter(private val contexte: Context, private val fraggy: Fragment, private val isFavoris: Boolean): RecyclerView.Adapter<NouvelleAdapter.NouvelleViewHolder>() {

    private var mNouvelle: List<Nouvelle>? = null
    private var mNouvelleFavoris: List<Nouvelle>? = null
    private var mNouvelleView: NouvelleViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NouvelleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_big, parent, false)
        return  NouvelleViewHolder(v, isFavoris)
    }

    override fun getItemCount(): Int {
        return if(!isFavoris) {if (mNouvelle != null) mNouvelle!!.size else 0} else {if (mNouvelleFavoris != null) mNouvelleFavoris!!.size else 0}
    }

    override fun onBindViewHolder(holder: NouvelleViewHolder, position: Int) {
        mNouvelleView = ViewModelProviders.of(fraggy).get(NouvelleViewModel::class.java)

        val curry = if(!isFavoris){mNouvelle!!} else{mNouvelleFavoris!!}[position]
        holder.titlebig.text = curry.title
        holder.descbig.text = curry.title
        Picasso.with(holder.itemView.context)
                .load(curry.coverImage)
                .resize((contexte.resources.displayMetrics.widthPixels/contexte.resources.displayMetrics.density).toInt(),250)
                .centerCrop()
                .error(R.drawable.himym)
                .into(holder.imgbig)
        holder.favo.setImageResource(if (curry.favoris == 1) R.drawable.ic_favorite_full else R.drawable.ic_favorite_empty)

        holder.favo.setOnClickListener {
            if (curry.favoris != 1){
                holder.favo.setImageResource(R.drawable.ic_favorite_full)
                mNouvelleView?.setFavoris(1,curry._id)
            } else{
                holder.favo.setImageResource(R.drawable.ic_favorite_empty)
                mNouvelleView?.setFavoris(0,curry._id)
            }
        }

        holder.imgbig.setOnClickListener {
            contexte.startActivity(Intent(contexte, ViewerActivity::class.java).putStringArrayListExtra(
                    "CLAVIER",
                    arrayListOf(
                            curry.title,
                            curry.body,
                            curry.game,
                            curry.coverImage,
                            curry.created_date.toString(),
                            curry.favoris.toString()
                    )
            ))
        }
    }

    fun setNouvelles(nou: List<Nouvelle>){
        mNouvelle = nou
        notifyDataSetChanged()
    }

    fun setNouvellesFavoris(fou: List<Nouvelle>){
        mNouvelleFavoris = fou
        notifyDataSetChanged()
    }

    class NouvelleViewHolder(itemView: View, internal val fav: Boolean): RecyclerView.ViewHolder(itemView){
        internal val imgbig: ImageView = itemView.findViewById(R.id.image_nouvelle_big)
        internal val titlebig: TextView = itemView.findViewById(R.id.title_nouvelle_big)
        internal val descbig: TextView = itemView.findViewById(R.id.desc_nouvelle_big)
        internal val favo: ImageView = itemView.findViewById(R.id.favo)
    }
}
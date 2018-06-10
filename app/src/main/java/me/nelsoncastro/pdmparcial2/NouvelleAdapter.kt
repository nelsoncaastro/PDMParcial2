package me.nelsoncastro.pdmparcial2

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import me.nelsoncastro.pdmparcial2.entities.Nouvelle

class NouvelleAdapter(private val contexte: Context): RecyclerView.Adapter<NouvelleAdapter.NouvelleViewHolder>() {

    private var mNouvelle: List<Nouvelle>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NouvelleViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_big, parent, false)
        return  NouvelleViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (mNouvelle != null) mNouvelle!!.size else 0
    }

    override fun onBindViewHolder(holder: NouvelleViewHolder, position: Int) {
        val curry = mNouvelle!![position]
        holder.titlebig.text = curry.title
        holder.descbig.text = curry.title
        /**Picasso.with(holder.itemView.context)
                .load(curry.coverImage)
                .centerCrop()
                .into(holder.imgbig)*/
    }

    internal fun setNouvelles(nou: List<Nouvelle>){
        mNouvelle = nou
        notifyDataSetChanged()
    }

    class NouvelleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal val imgbig: ImageView = itemView.findViewById(R.id.image_nouvelle_big)
        internal val titlebig: TextView = itemView.findViewById(R.id.title_nouvelle_big)
        internal val descbig: TextView = itemView.findViewById(R.id.desc_nouvelle_big)
    }
}
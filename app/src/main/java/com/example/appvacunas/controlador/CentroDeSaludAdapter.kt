package com.example.appvacunas.controlador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appvacunas.R
import com.example.appvacunas.modelo.CentrosDeSaludModelo
import com.squareup.picasso.Picasso


class CentroDeSaludAdapter(val dataSet: MutableList<CentrosDeSaludModelo>, val listener: (CentrosDeSaludModelo) -> Unit)
    : RecyclerView.Adapter<CentroDeSaludAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.designer_centros_de_salud,parent,false)
        return Holder(view)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener { listener(dataSet[position]) }
    }

    override fun getItemCount(): Int = dataSet.size

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var viewNombre : TextView
        lateinit var viewDescripcion : TextView
        lateinit var img : ImageView

        fun render (informacion : CentrosDeSaludModelo){

            viewNombre = itemView.findViewById(R.id.tv_nombre_centro)
            viewDescripcion = itemView.findViewById(R.id.tv_consulta_clinica)
            img = itemView.findViewById(R.id.imageView)

            viewNombre.setText(informacion.nombre)
            Picasso.get().load(informacion.foto).into(img);

        }

    }

}
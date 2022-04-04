package com.example.appvacunas.controlador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appvacunas.R
import com.example.appvacunas.modelo.VacunasModelo

open class MisVacunasAdapter (
    val dataSet: MutableList<VacunasModelo>) : RecyclerView.Adapter<MisVacunasAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.designer_mis_vacunas,parent,false)
        return Holder(view)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.render(dataSet[position])
        //holder.itemView.setOnClickListener { listener(dataSet[position]) }

    }

    override fun getItemCount(): Int = dataSet.size

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {

        lateinit var viewNombre : TextView
        lateinit var viewDescripcion : TextView

        fun render (informacion : VacunasModelo){

            viewNombre = itemView.findViewById(R.id.tv_clinica)
            viewDescripcion = itemView.findViewById(R.id.tv_name_vacuna)

            viewNombre.setText(informacion.nombre)
            viewDescripcion.setText(informacion.informacion)

        }

    }


}
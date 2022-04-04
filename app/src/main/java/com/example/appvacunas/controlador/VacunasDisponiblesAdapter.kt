package com.example.appvacunas.controlador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appvacunas.R
import com.example.appvacunas.modelo.VacunasModelo

open class VacunasDisponiblesAdapter(
    val dataSet: MutableList<VacunasModelo>,
    var itemOnclicklister: onClickLister) : RecyclerView.Adapter<VacunasDisponiblesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.designer_vacunas,parent,false)
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
        lateinit var viewSolicitar : TextView

        fun render (informacion : VacunasModelo){

            viewNombre = itemView.findViewById(R.id.tv_nombre_vacuna)
            viewDescripcion = itemView.findViewById(R.id.tv_informacion_vacuna)
            viewSolicitar= itemView.findViewById(R.id.tv_agendar_vacuna)

            viewNombre.setText(informacion.nombre)
            viewDescripcion.setText(informacion.informacion)

            viewSolicitar.setOnClickListener { itemOnclicklister.solicitarVacuna(informacion.id,informacion.nombre) }

        }

    }

    interface onClickLister{
        fun solicitarVacuna(id: String, nombre: String)
    }

}
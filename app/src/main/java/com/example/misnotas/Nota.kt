package com.example.misnotas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.nota_layout.view.*
import java.io.File

data class Nota(var title:String, var content:String)

class AdaptadorNotas: BaseAdapter {
    var context: Context
    var notas = ArrayList<Nota>()

    constructor(context: Context, notes:ArrayList<Nota>){
        this.context = context
        this.notas = notes
    }

    override fun getCount(): Int {
        return notas.size
    }

    override fun getItem(p0: Int): Any {
        return notas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.nota_layout,null)
        var nota = notas[p0]

        vista.tvTitleDet.text = nota.title
        vista.tvContentsDet.text = nota.content

        vista.btnDelete.setOnClickListener {
            eliminar(nota.title)
            notas.remove(nota)
            this.notifyDataSetChanged()
        }

        return vista
    }

    private fun eliminar(titulo:String){
        if (titulo ==""){
            Toast.makeText(context, "Error: título vacío.",Toast.LENGTH_SHORT).show()
        }else{
            try {
                val file = File(ubicacion(), "$titulo.txt")
                file.delete()
                Toast.makeText(context,"Se eliminó el archivo.",Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(context,"Error al eliminar el archivo.", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun ubicacion():String{
        val album = File(context?.getExternalFilesDir(null),"notas")
        if (!album.exists()){
            album.mkdir()
        }
        return album.absolutePath
    }

}
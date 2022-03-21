package com.example.misnotas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {
    var notas = ArrayList<Nota>()
    lateinit var adaptador: AdaptadorNotas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            var intent = Intent(this,AgregarNotaActivity::class.java)
            startActivityForResult(intent,123)
        }

        leerNotas()

        adaptador = AdaptadorNotas(this,notas)
        listView.adapter = adaptador
    }
    private fun leerNotas(){
        notas.clear()
        var file = File(ubicacion())
        if (file.exists()){
            var archives = file.listFiles()
            if (archives!= null){
                for (archive in archives){
                    leerArchivo(archive)
                }
            }
        }

    }

    private fun leerArchivo(archive: File?) {
        val fis = FileInputStream(archive)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData =""

        while (strLine != null){
            myData += strLine
            strLine = br.readLine()
        }
        br.close()
        di.close()
        fis.close()
        var nombre = archive?.name?.substring(0,archive.name.length-4)
        var nota = nombre?.let { Nota(it,myData) }
        nota?.let {
            notas.add(it)
        }
    }

    private fun ubicacion():String{
        val folder = File(getExternalFilesDir(null),"notas")
        if (!folder.exists()){
            folder.mkdir()
        }
        return folder.absolutePath
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){
            leerNotas()
            adaptador.notifyDataSetChanged()
        }
    }

}


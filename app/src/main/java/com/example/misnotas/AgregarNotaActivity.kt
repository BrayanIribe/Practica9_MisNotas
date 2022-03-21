package com.example.misnotas

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_agregar_nota.*
import java.io.File
import java.io.FileOutputStream

class AgregarNotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)
        btnSave.setOnClickListener {
            guardar_nota()
        }

    }

    private fun guardar_nota(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 235)
        }
        else{
            guardar()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            235 ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    guardar()
                }else{
                    Toast.makeText(this,"Error: permisos denegados.",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun guardar(){
        var title = etTitle.text.toString()
        var content = etContent.text.toString()
        if(title == "" || content ==""){
            Toast.makeText(this,"Error: campos vacíos.", Toast.LENGTH_SHORT).show()
        }else{
            try {
                val archive = File(ubicacion(), title+".txt")
                val fos = FileOutputStream(archive)
                fos.write(content.toByteArray())
                fos.close()
                Toast.makeText(this,"Se guardó el archivo en la carpeta pública.",Toast.LENGTH_SHORT).show()
            }catch (e:Exception){
                Toast.makeText(this,"Error: no se guardó el archivo.",Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    private fun ubicacion():String{
        val file = File(getExternalFilesDir(null),"notas")
        if (!file.exists()){
            file.mkdir()
        }
        return file.absolutePath
    }


}
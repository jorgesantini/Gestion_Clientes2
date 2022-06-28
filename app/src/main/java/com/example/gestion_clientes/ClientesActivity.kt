package com.example.gestion_clientes

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ClientesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        //Declaramos las variables CLIENTES_ACTIVITY

        val bt_botonAltaCliente = findViewById<Button>(R.id.bt_editarCliente)

        val ed_nombreCliente = findViewById<EditText>(R.id.ed_nombreCliente)
        val ed_apellidoCliente = findViewById<EditText>(R.id.ed_apellidoCliente)
        val ed_instrumental = findViewById<EditText>(R.id.ed_instrumental)
        val ed_costo = findViewById<EditText>(R.id.ed_costo)

        //VARIABLES PARA BASE DE DATOS

        val helper = MyDBHelperClientes(applicationContext)
        val db = helper.readableDatabase

        //PERSISTIMOS EN BASE DE DATOS AL CLIENTE

        bt_botonAltaCliente.setOnClickListener{
            val cv = ContentValues()
            cv.put("CNAME", ed_nombreCliente.text.toString())
            cv.put("CSURNAME", ed_apellidoCliente.text.toString())
            cv.put("REP", ed_instrumental.text.toString())
            cv.put("COST", ed_costo.text.toString())

            db.insert("CLIENTES", null, cv)

            //Limpia pantalla y enfoca en edit user

            ed_nombreCliente.setText("")
            ed_apellidoCliente.setText("")
            ed_instrumental.setText("")
            ed_costo.setText("")

            ed_nombreCliente.requestFocus()

            //Aviso de creacion satisfactoria de usuario

            Toast.makeText(applicationContext,"CLIENTE REGISTRADO", Toast.LENGTH_LONG).show()
        }
    }
}
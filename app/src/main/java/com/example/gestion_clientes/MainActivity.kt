package com.example.gestion_clientes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tx_titulo = findViewById<TextView>(R.id.tx_titulo)
        val bt_botonIncio = findViewById<Button>(R.id.bt_botonIncio)
        tx_titulo.text = "Gestion de Clientes"
        bt_botonIncio.text = "Inicio"

        //ESCUCHAMOS BOTON INICIO

        bt_botonIncio.setOnClickListener{
            val openlogin = Intent(this, LoginActivity::class.java)
            startActivity(openlogin)
        }
    }
}
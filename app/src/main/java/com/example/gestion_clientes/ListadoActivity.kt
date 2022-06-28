package com.example.gestion_clientes

import android.content.DialogInterface
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.ArrayList

class ListadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        val bt_listarClientes: Button = findViewById(R.id.bt_listarClientes)
        val lv_listado: ListView = findViewById(R.id.lv_listado)
        val helper = MyDBHelperClientes(applicationContext)

        //BOTON LISTAR TODOS LOS CLIENTES

        bt_listarClientes.setOnClickListener {
            val data: Cursor = helper.getListContent()

            if (data.moveToFirst()) {
                val listadoClientesDB: ArrayList<String> = ArrayList()
                do {
                    listadoClientesDB.add((data.getString(2))) // Agrego el nombre del cliente desde la bd al array.
                } while (data.moveToNext())

                val adapter =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, listadoClientesDB)
                lv_listado.adapter = adapter
            }

            }
        }
    }


package com.example.gestion_clientes

import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.ArrayList

class BajaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baja)

        val lv_listadoBaja: ListView = findViewById(R.id.lv_listadoBaja)
        val helper = MyDBHelperClientes(applicationContext)

        val data: Cursor = helper.getListContent()

        if (data.moveToFirst()) {
            val listadoClientesDB: ArrayList<String> = ArrayList()
            do {
                listadoClientesDB.add((data.getString(2))) // Agrego el nombre del cliente desde la bd al array.
            } while (data.moveToNext())

            val adapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, listadoClientesDB)
            lv_listadoBaja.adapter = adapter

            //Click en el listado para borrar el cliente seleccionado

            lv_listadoBaja.setOnItemClickListener { parent, view, position, id ->

                val clienteSeleccionado: String = parent.getItemAtPosition(position).toString()
                val alert = AlertDialog.Builder(this)
                alert.setMessage("Quiere BORRAR el cliente seleccionado?")
                    .setCancelable(false)

                    //Click en Si
                    .setPositiveButton("Si") { _: DialogInterface, _: Int ->

                        val dbe = helper.writableDatabase
                        dbe.execSQL("DELETE FROM CLIENTES WHERE CSURNAME='"+clienteSeleccionado+"'")

                        Toast.makeText(
                            applicationContext,
                            "CLIENTE $clienteSeleccionado BORRADO ",
                            Toast.LENGTH_LONG
                        ).show()

                        //RECARGAR ACTIVITY PARA ACTUALIZAR EL LISTVIEW

                        val intent = Intent(this, BajaActivity::class.java)
                        startActivity(intent)
                        finish() // Finalizar activity para no poder volver al anterior

                    }
                    //Click en No
                    .setNegativeButton("No") { Dialog: DialogInterface, _: Int ->
                        Dialog.cancel()
                    }

                //ventana flotante con botones
                val alertaFlotante: AlertDialog = alert.create()
                alertaFlotante.setTitle("BORRADO")
                alertaFlotante.show()



            }
        }
    }
}



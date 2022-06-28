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

            //Click en el listado para borrar el cliente seleccionado
            lv_listado.setOnItemClickListener { parent, view, position, id ->

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


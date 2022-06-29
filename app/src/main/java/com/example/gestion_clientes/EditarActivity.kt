package com.example.gestion_clientes

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditarActivity : AppCompatActivity() {

    lateinit var clienteSpinnerNombre : String
    lateinit var clienteSpinnerApellido : String
    lateinit var clienteSpinnerInstrumental : String
    lateinit var clienteSpinnerCosto : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        //Declaramos las variables EDITAR_ACTIVITY

        val bt_editarCliente = findViewById<Button>(R.id.bt_editarCliente)
        val sp_spinner = findViewById<Spinner>(R.id.sp_spinner)

        val ed_nombreCliente = findViewById<EditText>(R.id.ed_nombreCliente)
        val ed_apellidoCliente = findViewById<EditText>(R.id.ed_apellidoCliente)
        val ed_instrumental = findViewById<EditText>(R.id.ed_instrumental)
        val ed_costo = findViewById<EditText>(R.id.ed_costo)

        //VARIABLES PARA BASE DE DATOS

        val helper = MyDBHelperClientes(applicationContext)
        val db = helper.readableDatabase

        // CONSTANTES PARA EL SPINNER

        val clienteEncontradoNombre: ArrayList<String> = ArrayList()
        val clienteEncontradoApellido: ArrayList<String> = ArrayList()
        val clienteEncontradoInstrumental: ArrayList<String> = ArrayList()
        val clienteEncontradoCosto: ArrayList<String> = ArrayList()


        // METODOS

        fun spinner(){  //CLIENTES EN SPINNER Y POPULAMOS LOS EDITTEXT AL SELECCIONAR UNO

            val data = db.rawQuery("SELECT * FROM CLIENTES", null)

            if (data.moveToFirst()) {
                do {
                    clienteEncontradoNombre.add((data.getString(1))) // Agrego el nombre del cliente desde la bd al array.
                    clienteEncontradoApellido.add((data.getString(2))) // Agrego el nombre del cliente desde la bd al array.
                    clienteEncontradoInstrumental.add((data.getString(3))) // Agrego el nombre del cliente desde la bd al array.
                    clienteEncontradoCosto.add((data.getString(4))) // Agrego el nombre del cliente desde la bd al array.
                } while (data.moveToNext())

                val adapter =
                    ArrayAdapter(
                        this,
                        //android.R.layout.simple_spinner_item,
                        R.layout.spinner_items,
                        clienteEncontradoApellido.toList()
                    )

                //sp_spinner.adapter = null

                sp_spinner.adapter = adapter
                data.close()
            }
        }


        ///////////////////////////////////////////////////////////////////
        // RUTINA /////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////
        //INSTANCIAMOS METODO SPINNER /////////////////////////////////////


                spinner()

                //Manejamos los click que se hacen al spinner

                sp_spinner.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                        //Toast.makeText(this@EditarActivity, clienteEncontradoApellido[p2], Toast.LENGTH_SHORT).show()

                        //aca carga

                        ed_nombreCliente.setText(clienteEncontradoNombre[p2])
                        ed_apellidoCliente.setText(clienteEncontradoApellido[p2])
                        ed_instrumental.setText(clienteEncontradoInstrumental[p2])
                        ed_costo.setText(clienteEncontradoCosto[p2])

                        ed_nombreCliente.requestFocus()  //foco en el primer campo a llenar

                        clienteSpinnerNombre = clienteEncontradoNombre[p2]
                        clienteSpinnerApellido = clienteEncontradoApellido[p2]
                        clienteSpinnerInstrumental = clienteEncontradoInstrumental[p2]
                        clienteSpinnerCosto = clienteEncontradoCosto[p2]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }


        //PERSISTIMOS EN BASE DE DATOS AL CLIENTE

        bt_editarCliente.setOnClickListener {
                val cv = ContentValues()
                cv.put("CNAME", ed_nombreCliente.text.toString())
                cv.put("CSURNAME", ed_apellidoCliente.text.toString())
                cv.put("REP", ed_instrumental.text.toString())
                cv.put("COST", ed_costo.text.toString())


            val args : Array<String> = arrayOf(clienteSpinnerApellido)
            db.update("CLIENTES", cv, "CSURNAME=?", args) // ANDA

                //Limpia pantalla y enfoca en edit user

                ed_nombreCliente.setText("")
                ed_apellidoCliente.setText("")
                ed_instrumental.setText("")
                ed_costo.setText("")

                ed_nombreCliente.requestFocus()

                //Aviso de creacion satisfactoria de usuario

                Toast.makeText(applicationContext, "CLIENTE: '$clienteSpinnerApellido' FUE MODIFICADO", Toast.LENGTH_SHORT).show()
                // ANDA, muestra el toast con el nombre modificado

            //RECARGAR ACTIVITY PARA ACTUALIZAR SPINNER

            val intent = Intent(this, EditarActivity::class.java)
            startActivity(intent)
            finish() // Finalizar activity para no poder volver al anterior
            }
        }
    }

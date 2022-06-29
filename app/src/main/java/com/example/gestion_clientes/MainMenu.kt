package com.example.gestion_clientes

import android.app.DownloadManager
import android.app.VoiceInteractor
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlin.system.exitProcess

class MainMenu : AppCompatActivity() {

    lateinit var tx_nombre_menu: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val usuario: String =
            intent.getStringExtra("Usuario").toString() // Recuperamos al USER del putExtra anterior
        tx_nombre_menu = findViewById(R.id.tx_nombreMenu)
        tx_nombre_menu.text = usuario

        dolar()

    }

    fun dolar() {
        val textDolar = findViewById<TextView>(R.id.tx_valorDolar)
        val url = "https://free.currconv.com/api/v7/convert?q=USD_ARS&compact=ultra&apiKey=993890542ee7a1210956"
        //val url = "http://free.currencyconverterapi.com/api/v5/convert?q=USD_ARS&compact=ultra"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                "${response}".also { textDolar.text = it }
                //"El valor de Dolar hoy es: ${response.toString()}".also { textDolar.text = it }
               // textDolar.text = "El valor de Dolar hoy es: ${response.toString()}"
            },
            { error ->
                textDolar.text = "No se obtuvieron datos"
                error.printStackTrace()
            })
        queue.add(stringRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val usuario:String = intent.getStringExtra("Usuario").toString()
        return when (item.itemId){
            R.id.nav_altaCliente -> {
                //Ir ALTA CLIENTES
                val intent = Intent(this, ClientesActivity::class.java)
                intent.putExtra("Usuario", usuario)
                startActivity(intent)
                true
            }
            R.id.nav_bajaCliente -> {
                //Ir BAJA CLIENTES
                val intent = Intent(this, BajaActivity::class.java)
                intent.putExtra("Usuario", usuario)
                startActivity(intent)
                true
            }
            R.id.nav_modificarCliente -> {
                //Ir a actividad modificar cliente
                val intent = Intent(this, EditarActivity::class.java)
                intent.putExtra("Usuario", usuario)
                startActivity(intent)
                true
            }

            R.id.nav_listarCliente -> {
                //Ir a actividad listar clientes
                val intent= Intent(this, ListadoActivity::class.java)
                intent.putExtra("Usuario", usuario)
                startActivity(intent)
                true
            }
            R.id.nav_Salir -> {

                //Mensaje Alerta para salir

                val alert = AlertDialog.Builder(this)
                alert.setMessage("Quiere salir a la pantalla principal?")
                    .setCancelable(false)

                    //Click en Si: Vamos a la pantalla principal
                    .setPositiveButton("Si") { _: DialogInterface, _: Int ->
                        finish()
                        exitProcess(0)

                    }
                    //Click en No: Cierro el dialog
                    .setNegativeButton("No") { Dialog: DialogInterface, _: Int ->
                        Dialog.cancel()
                    }

                val alertaFlotante: AlertDialog = alert.create()
                alertaFlotante.setTitle("Salida")
                alertaFlotante.show()

                true
            }
            else -> false
        }
    }
}
package com.example.gestion_clientes

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Declaramos las variables

        var bt_buttonRegister = findViewById<Button>(R.id.bt_buttonRegister)
        var bt_buttonlogin = findViewById<Button>(R.id.bt_buttonLogin)

        var ed_loginUser = findViewById<EditText>(R.id.ed_loginUser)
        var ed_password = findViewById<EditText>(R.id.ed_password)

        //VARIABLES PARA BASE DE DATOS

        var helper =MyDBHelper(applicationContext)
        var db = helper.readableDatabase

        //Escuchamos al boton REGISTER

        bt_buttonRegister.setOnClickListener{

            if(ed_loginUser.text.toString().isEmpty() || ed_password.text.toString().isEmpty()){
                Toast.makeText(applicationContext,"LOS CAMPOS NO PUEDEN ESTAR VACIOS", Toast.LENGTH_LONG).show()
            }

            else{
                var cv = ContentValues()
                cv.put("UNAME", ed_loginUser.text.toString())
                cv.put("PWD", ed_password.text.toString())
                db.insert("USERS", null, cv)

                //Limpia pantalla y enfoca en edit user

                ed_loginUser.setText("")
                ed_password.setText("")
                ed_loginUser.requestFocus()

                //Aviso de creacion satisfactoria de usuario

                Toast.makeText(applicationContext,"USUARIO REGISTRADO", Toast.LENGTH_LONG).show()
            }
        }

        //ESCUCHAMOS BOTON LOGIN

        bt_buttonlogin.setOnClickListener{

            // Chequeamos que los campos no esten vacios.

            if(ed_loginUser.text.toString().isEmpty() || ed_password.text.toString().isEmpty()){
                Toast.makeText(applicationContext,"LOS CAMPOS NO PUEDEN ESTAR VACIOS", Toast.LENGTH_LONG).show()
            }

            // Buscamos en BD al usuario y contrasena cargados en los editText
            else{
                val args = listOf<String>(ed_loginUser.text.toString(), ed_password.text.toString()).toTypedArray()
                val rs = db.rawQuery("SELECT * FROM USERS WHERE UNAME = ? AND PWD = ?", args)
                if (rs.moveToNext()) {
                    Toast.makeText(applicationContext, "ACCESO SATISFACTORIO", Toast.LENGTH_LONG).show()
                    //VAMOS A CLIENTES ACTIVITY
                    val openCliente = Intent(this, MainMenu::class.java)
                    openCliente.putExtra("Usuario", ed_loginUser.text.toString()) // Nos llevamos al USER con el putExtra
                    startActivity(openCliente)

                    // Para que no vuelva a la pantalla LOGIN
                    finish()
                }

                else
                    Toast.makeText(applicationContext,"ACCESO DENEGADO", Toast.LENGTH_LONG).show()
            }
        }
    }
}
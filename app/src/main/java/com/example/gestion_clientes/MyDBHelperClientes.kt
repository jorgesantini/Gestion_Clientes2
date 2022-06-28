package com.example.gestion_clientes

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Aca manejamos la base de datos que contiene a los clientes.

class MyDBHelperClientes  (context: Context) : SQLiteOpenHelper(context, "CLIENTESDB", null, 2) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE CLIENTES(CLIENTEID INTEGER PRIMARY KEY AUTOINCREMENT, CNAME TEXT, CSURNAME TEXT, REP TEXT, COST MONEY)")

        //POPULAMOS PARA TESTEAR LA APP

        db?.execSQL("INSERT INTO CLIENTES(CNAME, CSURNAME, REP, COST) VALUES('CARLOS', 'PEREZ', 'PINZA', '1000')")
        db?.execSQL("INSERT INTO CLIENTES(CNAME, CSURNAME, REP, COST) VALUES('PABLO', 'SUAREZ', 'RETRACTOR', '2000')")
        db?.execSQL("INSERT INTO CLIENTES(CNAME, CSURNAME, REP, COST) VALUES('RAUL', 'STRASEN', 'MONITOR', '3000')")
        db?.execSQL("INSERT INTO CLIENTES(CNAME, CSURNAME, REP, COST) VALUES('CRISTIAN', 'SALOMON', 'CAMARA', '4000')")
        db?.execSQL("INSERT INTO CLIENTES(CNAME, CSURNAME, REP, COST) VALUES('SAUL', 'MARTINEZ', 'GRASPER', '5000')")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //Metodo para traer a todos los clientes de la base de datos

    public fun getListContent(): Cursor {
       var bd: SQLiteDatabase = this.readableDatabase
       var data: Cursor = bd.rawQuery("SELECT * FROM CLIENTES", null)
       return data
    }
}
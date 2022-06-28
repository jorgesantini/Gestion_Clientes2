package com.example.gestion_clientes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.AccessControlContext

// Aca se crea la base de datos que contiene a los usuarios que se van a loguear.

class MyDBHelper (context:Context) : SQLiteOpenHelper (context, "USERDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, UNAME TEXT, PWD TEXT)")

        //USUARIOS POR DEFECTO

        db?.execSQL("INSERT INTO USERS(UNAME, PWD) VALUES('tomi', '1000')")
        db?.execSQL("INSERT INTO USERS(UNAME, PWD) VALUES('1', '1')")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}



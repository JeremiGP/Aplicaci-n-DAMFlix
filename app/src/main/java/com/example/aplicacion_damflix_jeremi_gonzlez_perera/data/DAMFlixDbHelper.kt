package com.example.aplicacion_damflix_jeremi_gonzlez_perera.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DAMFlixDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "damflix_native.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_PELICULAS = "peliculas"
        const val COLUMN_ID = "id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_DIRECTOR = "director"
        const val COLUMN_FECHA_ESTRENO = "fecha_estreno"
        const val COLUMN_GENERO = "genero"
        const val COLUMN_PENDIENTE = "pendiente"
        const val COLUMN_ES_VISTA = "es_vista"
        const val COLUMN_ES_FAVORITA = "es_favorita"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableSql = """
            CREATE TABLE $TABLE_PELICULAS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITULO TEXT NOT NULL,
                $COLUMN_DIRECTOR TEXT NOT NULL,
                $COLUMN_FECHA_ESTRENO TEXT NOT NULL,
                $COLUMN_GENERO TEXT NOT NULL,
                $COLUMN_PENDIENTE INTEGER NOT NULL DEFAULT 0,
                $COLUMN_ES_VISTA INTEGER NOT NULL DEFAULT 0,
                $COLUMN_ES_FAVORITA INTEGER NOT NULL DEFAULT 0
            )
        """.trimIndent()
        db.execSQL(createTableSql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PELICULAS")
        onCreate(db)
    }
}

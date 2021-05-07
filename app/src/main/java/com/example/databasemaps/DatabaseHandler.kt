package com.example.databasemaps

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler (context: Context) : SQLiteOpenHelper(context , DATABASE_NAME , null , DATABASE_VERSION){

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "employeDatabase"

        private val TABLE_CONTACTS = "employeTable"

        private val KEY_ID = "_id"
        private val KEY_KEGIATAN = "kegiatan"
        private val KEY_WAKTU = "waktu"
        private val KEY_LOKASI = "lokasi"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_KEGIATAN + " TEXT,"
                + KEY_WAKTU + " TEXT,"
                + KEY_LOKASI + " TEXT)")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addEmployee(emp:EmpModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_KEGIATAN , emp.kegiatan)
        contentValues.put(KEY_WAKTU , emp.waktu)
        contentValues.put(KEY_LOKASI , emp.lokasi)

        val success = db.insert(TABLE_CONTACTS, null ,contentValues)
        db.close()
        return success
    }

    fun viewEmployee(): ArrayList<EmpModel> {
        val empList: ArrayList<EmpModel> = ArrayList<EmpModel>()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"

        val db = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var kegiatan: String
        var waktu: String
        var lokasi: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                kegiatan = cursor.getString(cursor.getColumnIndex(KEY_KEGIATAN))
                waktu = cursor.getString(cursor.getColumnIndex(KEY_WAKTU))

                lokasi = cursor.getString(cursor.getColumnIndex(KEY_LOKASI))

                val emp = EmpModel(id = id, kegiatan = kegiatan, waktu = waktu, lokasi = lokasi)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

    // Tombol Delete
    fun deleteEmployee(emp: EmpModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.id)

        val success = db.delete(TABLE_CONTACTS, KEY_ID + "=" + emp.id, null)
        db.close()
        return success
    }

}
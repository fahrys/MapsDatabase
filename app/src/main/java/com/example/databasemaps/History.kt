package com.example.databasemaps

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setupListOfDataIntoRecyclerView()

        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true) // Tombol Back
    }

    // Tombol Back
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // method untuk mendapatkan jumlah record
    private fun getItemList(): ArrayList<EmpModel> {
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val empList: ArrayList<EmpModel> = databaseHandler.viewEmployee()
        rvitem.layoutManager = LinearLayoutManager(this)
        rvitem.adapter = ItemAdapter(this, empList)
        return empList

    }


    // method untuk menampilkan emplist ke recycler view
    private fun setupListOfDataIntoRecyclerView() {
        if (getItemList().size > 0) {
            rvitem.visibility = View.VISIBLE
        } else {
            rvitem.visibility = View.GONE
        }

    }

    // Bagian Delete
    fun deleteRecordAlertDialog(empModel: EmpModel) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Hapus?")
        builder.setMessage("Hapus Data Terpilih?")
        builder.setIcon(android.R.drawable.ic_delete)

        // menampilkan tombol yes
        builder.setPositiveButton("Yes") { dialog: DialogInterface, which ->
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            val status = databaseHandler.deleteEmployee(EmpModel(empModel.id, "", "", ""))

            if (status > -1) {
                Toast.makeText(this, "Berhasil Menghapus", Toast.LENGTH_SHORT).show()
                setupListOfDataIntoRecyclerView()
            }

            dialog.dismiss()
        }
        // menampilkan tombol no
        builder.setNegativeButton("No") { dialog: DialogInterface, which ->
            //Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        // menampilkan user menekan tombol yes or no
        alertDialog.setCancelable(false)
        // menampilkan kotak dialog
        alertDialog.show()
    }

}
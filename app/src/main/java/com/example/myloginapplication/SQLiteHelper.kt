package com.example.myloginapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import java.util.*


class SQLiteHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){


    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "APP_DB"
        private const val USER_TABLE = "userTable"
        private const val ID = "id"
        private const val NAME = "name"
        private const val SURNAME = "surname"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val query_create = ("CREATE TABLE " + USER_TABLE + "("+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                            NAME + " VARCHAR(255),"+ SURNAME + " VARCHAR(255),"+ EMAIL + " VARCHAR(255),"+
                            PASSWORD + " VARCHAR(255));")


        db?.execSQL(query_create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $USER_TABLE")
        onCreate(db)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertUser(user: User): Long {

        var pass = encryptString(user.password)
        println(user.name)

        //user.id = getUserId(user.name.toString(),user.surname.toString(),user.email.toString(),pass.toString() )
        println("ID")
        //println(user.id)
        val db = this.writableDatabase
        val contentValue = ContentValues()
        //contentValue.put(ID,user.id)
        contentValue.put(NAME,user.name)
        contentValue.put(SURNAME,user.surname)
        contentValue.put(EMAIL,user.email)
        contentValue.put(PASSWORD,encryptString(user.password))
        val success = db.insert(USER_TABLE,null, contentValue)
        db.close()
        return success
    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)

        fun getUserId(name_input : String, surname_input: String, email_input: String, password_input: String): Int {
        println("dentro user id")
        val db = this.readableDatabase
        var pass = encryptString(password_input)
        val query = "SELECT "+ID +" FROM userTable WHERE "+ NAME + " = '" + name_input.toString() +"' AND "+ SURNAME +" = '"+surname_input.toString()+"' AND "+
                                                                                                     EMAIL + " = '" + email_input.toString() + "' AND "+ PASSWORD +" = '"+pass.toString()+"'"
        val cursor = db.rawQuery(query, null)
        var id : Int = 0

        println("cursor")
        println(cursor.toString())
        if (cursor != null && cursor.moveToFirst()) {
            do {

                val id = cursor.getInt(cursor.getColumnIndex("id"))
                println("dentro query")
                println(id)
                return id

            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()
        println("ho trovato id")
        println(id)
        return id

    }

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
     fun getUser(email_input: String, password_input: String): User {
        val db = this.readableDatabase
        var pass = encryptString(password_input)
        val query = "SELECT * " +" FROM userTable WHERE "+
                EMAIL + " = '" + email_input.toString() + "' AND "+ PASSWORD +" = '"+pass.toString()+"'"
        val cursor = db.rawQuery(query, null)
        var id : Int = 0
        var user : User = User()


        if (cursor != null && cursor.moveToFirst()) {
            do {

                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val surname = cursor.getString(cursor.getColumnIndex("surname"))
                val email = cursor.getString(cursor.getColumnIndex("email"))
                val password = cursor.getString(cursor.getColumnIndex("password"))
                user = User(name.toString(),surname.toString(),email.toString(),password.toString())

            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return user

    }

    @SuppressLint("Range")
    fun getUsers(){
        val db = this.readableDatabase
        val query = "SELECT * FROM userTable"
        val cursor = db.rawQuery(query, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val surname = cursor.getString(cursor.getColumnIndex("surname"))
                val email = cursor.getString(cursor.getColumnIndex("email"))
                val password = cursor.getString(cursor.getColumnIndex("password"))

                // Puoi utilizzare i dati come desideri
                // Esempio: stampare i valori
                println("ID: $id, Name: $name, Surname: $surname, Email: $email, Password: $password")
            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    fun existUser(Emailinput: String, Passwordinput: String): Boolean {
        val db = this.readableDatabase
        var pass = encryptString(Passwordinput)
        val query = "SELECT "+EMAIL+","+ PASSWORD+" FROM userTable WHERE "+ EMAIL + " = '" + Emailinput +"' AND "+ PASSWORD +" = '"+pass.toString()+"'"
        val cursor = db.rawQuery(query, null)
        var email : String = ""
        var password : String = ""

        if (cursor != null && cursor.moveToFirst()) {
            do {

                email = cursor.getString(cursor.getColumnIndex("email"))
                password = cursor.getString(cursor.getColumnIndex("password"))
                println(" Email: $email, Password: $password")
            } while (cursor.moveToNext())
        }

        var Passw = encryptString(Passwordinput)
        cursor?.close()
        db.close()
        return email.equals(Emailinput.toString())  || password.toString().equals(pass.toString())



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptString(plaintext: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(plaintext.toByteArray(Charsets.UTF_8))
        val encryptedBytes = md.digest()
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }



}
package com.example.navigationview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat

class MainActivity : AppCompatActivity(){
    lateinit var drawer:DrawerLayout
    lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_uteq)
    }
    fun enviarDatos(view: View){
        val intent= Intent(this,NavegacionInterface::class.java)
        val txtNombre=findViewById<EditText>(R.id.txt_usuario)
        val txtPassword=findViewById<EditText>(R.id.txt_password)
        if(txtNombre.text.toString()=="vchunt"&& txtPassword.text.toString()=="12345678"){
            intent.putExtra("usuario",txtNombre.text.toString())
            intent.putExtra("password",txtPassword.text.toString())
            intent.putExtra("rol","Administrador")
            startActivity(intent)
        }else if(txtNombre.text.toString()=="elian"&& txtPassword.text.toString()=="123"){
            intent.putExtra("usuario",txtNombre.text.toString())
            intent.putExtra("password",txtPassword.text.toString())
            intent.putExtra("rol","user")
            startActivity(intent)
        }else{
            Toast.makeText(this,"Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
        }

    }


}
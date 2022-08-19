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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

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
        val cola = Volley.newRequestQueue(this)
        val intent= Intent(this,NavegacionInterface::class.java)
        val txtNombre=findViewById<EditText>(R.id.txt_usuario)
        val txtPassword=findViewById<EditText>(R.id.txt_password)

        val url="https://62ff92289350a1e548e1bee5.mockapi.io/users"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val usersJSON = JSONArray(response.toString());
                for(i in 0 until usersJSON.length()){
                    val user: JSONObject = usersJSON.getJSONObject(i);
                    if(txtNombre.text.toString() == user.getString("user") && txtPassword.text.toString() == user.getString("password")){
                        intent.putExtra("usuario",user.getString("name"))
                        intent.putExtra("password",user.getString("password"))
                        intent.putExtra("rol",user.getString("rol"))
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            {
                Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
            })
        cola.add(stringRequest)

    }


}
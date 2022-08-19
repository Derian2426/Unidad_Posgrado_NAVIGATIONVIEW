package com.example.navigationview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class NavegacionInterface : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawer: DrawerLayout
    lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navegacion_interface)
        val url="https://62ff92289350a1e548e1bee5.mockapi.io/menu_Postgrado"
        val cola = Volley.newRequestQueue(this)

        val bundle=intent.extras
        var toolbar: Toolbar =findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer=findViewById(R.id.drawer_layout)
        toggle= ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        var navigationView:NavigationView=findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        var headerView: View=navigationView.getHeaderView(0)
        var userName:TextView=headerView.findViewById(R.id.profile_name)
        userName.setText(bundle?.getString("usuario"))
        var imagenView:ImageView=navigationView.getHeaderView(0).findViewById(R.id.profile_image)
        var menu:Menu=navigationView.menu
        var submenu:SubMenu
        if(bundle?.getString("rol")=="Administrador"){
            Toast.makeText(this,"Hola "+bundle?.getString("rol"), Toast.LENGTH_SHORT).show()
            imagenView.setImageResource(R.drawable.admin)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val listaMenu = JSONArray(response.toString());
                    for(i in 0 until listaMenu.length()){
                        val menuItem: JSONObject = listaMenu.getJSONObject(i);
                        menu.add(menuItem.getString("opc01"))
                        menu.add(menuItem.getString("opc02"))
                        menu.add(menuItem.getString("opc03"))
                        menu.add(menuItem.getString("opc04"))
                        submenu=menu.addSubMenu(menuItem.getString("opc05"))
                        submenu.add(menuItem.getString("opc06"))
                        submenu.add(menuItem.getString("opc07"))
                        submenu.add(menuItem.getString("opc08"))

                    }
                },
                {
                    Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
                })
            cola.add(stringRequest)


        }else if (bundle?.getString("rol")=="User"){
            Toast.makeText(this,"Hola "+bundle?.getString("usuario"), Toast.LENGTH_SHORT).show()
            imagenView.setImageResource(R.drawable.user)
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val listaMenu = JSONArray(response.toString());
                    for(i in 0 until listaMenu.length()){
                        val menuItem: JSONObject = listaMenu.getJSONObject(i);
                        menu.add(menuItem.getString("opc02"))
                        menu.add(menuItem.getString("opc03"))
                        submenu=menu.addSubMenu(menuItem.getString("opc05"))
                        menu.add(menuItem.getString("opc06"))
                        submenu.add(menuItem.getString("opc07"))
                        submenu.add(menuItem.getString("opc08"))

                    }
                },
                {
                    Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
                })
            cola.add(stringRequest)

        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when(item.title){
            "Inicio"-> {
                Toast.makeText(this,"Inicio Horarios Posgrado", Toast.LENGTH_SHORT).show()
            }
            "Maestrias"-> {
                fragment= Maestrias()
                Toast.makeText(this,"Programas de maestrías", Toast.LENGTH_SHORT).show()
            }
            "Docente"-> {
                fragment= Docente()
                Toast.makeText(this,"Docentes", Toast.LENGTH_SHORT).show()
            }
            "Administracion"-> {
                fragment= Administracion()
                Toast.makeText(this,"Administración", Toast.LENGTH_SHORT).show()
            }
            "Horarios Docentes"-> Toast.makeText(this,"Horarios Docentes", Toast.LENGTH_SHORT).show()
            "Horarios Asesorias"-> Toast.makeText(this,"Horarios Asesorias", Toast.LENGTH_SHORT).show()
            "Horarios Sustentaciones"-> Toast.makeText(this,"Horarios Sustentaciones", Toast.LENGTH_SHORT).show()
        }
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()

            item.setChecked(true)
            getSupportActionBar()?.setTitle(item.getTitle());
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
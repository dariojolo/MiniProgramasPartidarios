package ar.com.androidappsdhj.miniprogramaspartidarios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Bundle bundle;
    private SharedPreferences prefs;
    private RealmResults<Programa> programas;

    private int id;
    private Bundle extras;

    private int fragment_recuperado;
    private Realm realm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        extras = getIntent().getExtras();

        if (extras != null){
            try {
                Log.d("Algo llego", "TAG");
                id = Integer.parseInt(extras.getString("ID"));
                Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                intent.putExtra("Programa", id);
                intent.putExtra("Fragment", 1);
                startActivity(intent);
            }catch (Exception ex){

            }
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);

        setToolbar();
        try {
            bundle = getIntent().getExtras();
            int _fragment = bundle.getInt("Fragment");
            //Toast.makeText(this, "Fragment: " + _fragment, Toast.LENGTH_LONG).show();
            Fragment frag;

            if (_fragment == 0) {
                prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                fragment_recuperado = prefs.getInt("fragment", -1);
                if (fragment_recuperado == -1){
                    setFragmentByDefault();
                }else{
                    verFragment(fragment_recuperado);
                }
            }else{
                verFragment(_fragment);
            }

        } catch (Exception ex) {
            setFragmentByDefault();
        }

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //       Toast.makeText(MainActivity.this,"OPEN", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //      Toast.makeText(MainActivity.this,"CLOSE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransition = false;
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.radioam:
                        fragment = new AmFragment();
                        fragmentTransition = true;
                        break;
                }
                if (fragmentTransition) {
                    changeFragment(fragment, item);
                    drawer.closeDrawers();
                }
                return true;
            }
        });
    }

    private void verFragment(int fragment_recuperado) {
        Fragment frag;
        if (fragment_recuperado == 1) {
            frag = new AmFragment();
            changeFragment(frag, navigationView.getMenu().getItem(0));
        }
    }

    //Probando si este metodo funciona, intentar recuperar la ultima pantalla visitada

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void changeFragment(Fragment fragment, MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    private void setFragmentByDefault() {
        //changeFragment(new MainFragment(), navigationView.getMenu().getItem(0));
        changeFragment(new AmFragment(), navigationView.getMenu().getItem(0));
    }

    //Inflamos el layout del menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    //Manejamos la funcionalidad del menu de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Logica de menu lateral
                drawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.notificarTodos:
                agregarTodasLasNotificaciones();
                return true;
            case R.id.notificarNinguno:
                eliminarTodasLasNotificaciones();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void eliminarTodasLasNotificaciones() {
        realm = Realm.getDefaultInstance();
        programas = realm.where(Programa.class).findAll();
        for (Programa programa : programas){
            FirebaseMessaging.getInstance().unsubscribeFromTopic(programa.getTopicNotificacion());
            realm.beginTransaction();
            programa.setNotificar(false);
            realm.copyToRealmOrUpdate(programa);
            realm.commitTransaction();
        }
        Toast.makeText(this,"Se han eliminado las notificaciones de los programas",Toast.LENGTH_SHORT).show();
    }

    private void agregarTodasLasNotificaciones() {
        realm = Realm.getDefaultInstance();
        programas = realm.where(Programa.class).findAll();
        for (Programa programa : programas){
            FirebaseMessaging.getInstance().subscribeToTopic(programa.getTopicNotificacion());
            realm.beginTransaction();
            programa.setNotificar(true);
            realm.copyToRealmOrUpdate(programa);
            realm.commitTransaction();
        }
        Toast.makeText(this,"Se ha suscripto a las notificaciones de todos los programas",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        try {
            prefs.edit().remove("fragment").apply();
        }catch (Exception ex){

        }
    }
}

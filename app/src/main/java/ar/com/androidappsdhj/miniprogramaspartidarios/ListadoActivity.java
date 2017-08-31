package ar.com.androidappsdhj.miniprogramaspartidarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ListadoActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Programa>> {

    private List<Programa> programas;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private int contador = 0;
    private Boolean isLunes = false;
    private Boolean isMartes = false;
    private Boolean isMiercoles = false;
    private Boolean isJueves = false;
    private Boolean isViernes = false;
    private Boolean isSabado = false;
    private Boolean isDomingo = false;

    private Realm realm;
    private RealmResults<Programa> programasR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        programasR = getAllProgramasR();
        programasR.addChangeListener(this);
        // programas = getAllProgramas();
        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MyAdapter(programasR, R.layout.list_item_recycler, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Programa programa, int position) {
                //deletePrograma(position);
                Intent intent = new Intent(ListadoActivity.this,DetalleActivity.class);
                Toast.makeText(getApplicationContext(), "Posicion antes de enviarse "+ position, Toast.LENGTH_LONG).show();
                intent.putExtra("Programa", programa.getId());
                startActivity(intent);
            }
        });

        adapter.notifyDataSetChanged();
        //Este metodo se puede usar cuando sabemos que el layout del recycler no van a cambiar de tamaño
        recycler.setHasFixedSize(true);
        //Se le agrega una animacion por defecto
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;

    }


    private RealmResults<Programa>getAllProgramasR(){
        return realm.where(Programa.class).findAll();
    }


    private void addPrograma(int position){
        //  programas.add(position, new Programa("Pelicula: " + ++contador, R.drawable.programanuevo));
        //  adapter.notifyItemInserted(position);
        //  layoutManager.scrollToPosition(0);
        //showAlertParaNuevoPrograma("Nuevo programa partidario", "");

    }


    private void deletePrograma(int position){
        programas.remove(position);
        adapter.notifyItemRemoved(position);
    }


    @Override
    public void onChange(RealmResults<Programa> element) {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }
}

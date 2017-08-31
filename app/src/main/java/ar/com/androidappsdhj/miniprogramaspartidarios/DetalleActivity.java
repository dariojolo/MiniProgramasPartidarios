package ar.com.androidappsdhj.miniprogramaspartidarios;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import io.realm.Realm;

public class DetalleActivity extends AppCompatActivity {

    private ImageView imagen;
    private TextView txtNombre;
    private TextView txtEmisora;
    private TextView txtDias;
    private TextView txtDia1;
    private TextView txtDia2;
    private TextView txtConductores;
    private TextView txtWeb;
    private TextView txtTel;
    private TextView txtFace;
    private TextView txtTwi;
    private int _fragment;
    //private WebView webview;
    private android.support.design.widget.FloatingActionButton fab;
    private android.support.design.widget.FloatingActionButton fabNotification;
    private android.support.design.widget.FloatingActionButton fabfavorite;
    private LinearLayout notificationLinear;
    private LinearLayout favouriteLinear;
    private boolean favorito;
    private final int INTERNET_CODE = 100;
    private final int PHONE_CODE = 101;
    private final int READ_EXTERNAL_STORAGE_CODE = 102;

    public Programa programa;
    private ImageView imgEscuchar;
    private TextView txtEscuchar;

    private SharedPreferences prefs;

    InterstitialAd interstitialAd; // Publicidad Pantalla Completa

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);


        if (MyApp.contadorPantallas % 5 == 0) {

            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId("ca-app-pub-2411851199893992/8385419738");
            AdRequest adRequest1 = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest1);
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    interstitialAd.show();
                }
            });
        }
        MyApp.contadorPantallas++;

        setToolbar();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        int _id;
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        _id =  prefs.getInt("IDPrograma", -1);

        if (_id != -1){
            _fragment = 0;
            prefs.edit().remove("IDPrograma").apply();
        }else{
            Bundle bundle = getIntent().getExtras();
            _id = bundle.getInt("Programa");
            _fragment = bundle.getInt("Fragment");
        }


      /*  }catch(Exception e){
            Log.i("TAG","No encontramos el ID");
            Intent i = new Intent(DetalleActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("Fragment", _fragment);
            startActivity(i);
        }
       // _fragment = bundle.getInt("Fragment");*/

        imagen = (ImageView) findViewById(R.id.imagenPrograma);
        txtNombre = (TextView) findViewById(R.id.txtNombre2);
        txtEmisora = (TextView) findViewById(R.id.txtEmisora);
        txtDia1 = (TextView) findViewById(R.id.txtDia1);
        txtDia2 = (TextView) findViewById(R.id.txtDia2);
        txtConductores = (TextView) findViewById(R.id.txtConductores);
        //webview = (WebView) findViewById(R.id.webview);
        txtWeb = (TextView) findViewById(R.id.txtWeb);
        txtTel = (TextView) findViewById(R.id.txtTelefono);
        txtFace = (TextView) findViewById(R.id.txtFacebook);
        txtTwi = (TextView) findViewById(R.id.txtTwitter);
        txtEscuchar = (TextView) findViewById(R.id.txtEscuchar);
        imgEscuchar = (ImageView) findViewById(R.id.imgEscuchar);


        txtEscuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertEscuchar("Escuchar en vivo", "Para poder escuchar saldra de la aplicacion y puede generarle consumos de su plan de datos");
            }
        });
        imgEscuchar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertEscuchar("Escuchar en vivo", "Para poder escuchar saldra de la aplicacion y puede generarle consumos de su plan de datos");
            }
        });
        final Realm realm = Realm.getDefaultInstance();

        programa = realm.where(Programa.class).equalTo("Id", _id).findFirst();

        if (programa.getMedio().equals("TV")) {
            LinearLayout linearEscuchar = (LinearLayout) findViewById(R.id.linearEscuchar);
            linearEscuchar.setVisibility(View.INVISIBLE);
        }
        this.setTitle(programa.getNombre());
        Picasso.with(this)
                .load(programa.getImagen())
                .fit()
                .into(imagen);
        imagen.setImageResource(programa.getImagen());
        txtNombre.setText(programa.getNombre());
        txtEmisora.setText(programa.getEmisora());
        txtDia1.setText(programa.getDiaUno());
        if (programa.getDiaDos() != null && !programa.getDiaDos().equals("")) {
            txtDia2.setText(" -- " + programa.getDiaDos());
        }
        txtConductores.setText(programa.getConductores());
        txtTel.setText("   " + programa.getTelefono());
        txtWeb.setText("   " + programa.getWeb());
        txtFace.setText("   " + programa.getFacebook());
        txtTwi.setText("   " + programa.getTwitter());

        txtTwi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTwi.getText().toString() != "") {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(programa.getTwitter().toString()));
                    startActivity(i);
                }
            }
        });
        txtFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtFace.getText().toString() != "") {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(programa.getFacebook().toString()));
                    startActivity(i);
                }
            }
        });
        txtWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(programa.getWeb().toString()));
                startActivity(i);
            }
        });

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        //Comprobamos version de android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           // NewerVersion();
        } else {
            OlderVersion(programa);
        }

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        if (programa.isFavorito()) {
            floatingActionButton1.setImageResource(R.drawable.ic_favorite_on); //on
        } else {
            floatingActionButton1.setImageResource(R.drawable.ic_favorite_off); //off
        }
        if (programa.isNotificar()) {
            floatingActionButton2.setImageResource(R.drawable.ic_notifications_on); //on
        } else {
            floatingActionButton2.setImageResource(R.drawable.ic_notifications_off); //off
        }

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (programa.isFavorito()) {
                    Toast.makeText(DetalleActivity.this, programa.getNombre() + " fue eliminado de favoritos", Toast.LENGTH_SHORT).show();
                    floatingActionButton1.setImageResource(R.drawable.ic_favorite_off); //off
                    updateFavorito(programa, false, realm);
                } else {
                    Toast.makeText(DetalleActivity.this, programa.getNombre() + " fue agregado a favoritos", Toast.LENGTH_SHORT).show();
                    floatingActionButton1.setImageResource(R.drawable.ic_favorite_on); //on
                    updateFavorito(programa, true, realm);
                }
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (programa.isNotificar()) {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(programa.getTopicNotificacion());
                    floatingActionButton2.setImageResource(R.drawable.ic_notifications_off); //off
                    Toast.makeText(DetalleActivity.this, " Se desuscribió de las notificaciones de " + programa.getNombre(), Toast.LENGTH_SHORT).show();
                    updateNotificar(programa, false, realm);
                } else {
                    FirebaseMessaging.getInstance().subscribeToTopic(programa.getTopicNotificacion());
                    floatingActionButton2.setImageResource(R.drawable.ic_notifications_on); //on
                    Toast.makeText(DetalleActivity.this, " Se suscribió a las notificaciones de " + programa.getNombre(), Toast.LENGTH_SHORT).show();
                    updateNotificar(programa, true, realm);
                }
            }
        });
    }

    private void updateNotificar(Programa programa, boolean notificar, Realm realm) {
        realm.beginTransaction();
        programa.setNotificar(notificar);
        realm.copyToRealmOrUpdate(programa);
        realm.commitTransaction();
    }


    private void updateFavorito(Programa programa, boolean favorito, Realm realm) {
        realm.beginTransaction();
        programa.setFavorito(favorito);
        realm.copyToRealmOrUpdate(programa);
        realm.commitTransaction();
    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_flechita);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetalleActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("Fragment", _fragment);
                startActivity(i);
            }
        });
    }

    private boolean CheckPermissions(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void OlderVersion(Programa programa) {
        if (CheckPermissions(Manifest.permission.INTERNET)) {
            //String VIDEO_URL = "http://tunein.com/embed/player/s248138/";

            //String VIDEO_URL = programa.getLink();
            //String data_html = "<!DOCTYPE html><html> <head></head> <body> <iframe src=\"" + VIDEO_URL + "\" style=\"width:100%;height:100px;\" scrolling=\"no\" frameborder=\"no\"></iframe> </body> </html> ";
            //webview.loadDataWithBaseURL("http://tunein.com/", data_html, "text/html", "UTF-8", null);
        } else {
            Toast.makeText(this, "No tiene permisos", Toast.LENGTH_SHORT).show();
        }
    }

    //private void NewerVersion() {
    //    requestPermissions(new String[]{Manifest.permission.INTERNET}, INTERNET_CODE);
    //}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String permission;
        int result;
        switch (requestCode) {
            case INTERNET_CODE:
                permission = permissions[0];
                result = grantResults[0];
                if (permission.equals(Manifest.permission.INTERNET)) {
                    //Comprobar si ha sido aceptado o denegado el permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //ha concedido el permiso
                        //String VIDEO_URL = "http://tunein.com/embed/player/s248138/";
                        //String VIDEO_URL = programa.getLink();
                        //String data_html = "<!DOCTYPE html><html> <head></head> <body> <iframe src=\"" + VIDEO_URL + "\" style=\"width:100%;height:100px;\" scrolling=\"no\" frameborder=\"no\"></iframe> </body> </html> ";
                        //webview.loadDataWithBaseURL("http://tunein.com/", data_html, "text/html", "UTF-8", null);
                    } else {
                        //No ha concedido el permiso
                        Toast.makeText(this, "No concedió permisos", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case PHONE_CODE:
                permission = permissions[0];
                result = grantResults[0];
                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //Comprobar si ha sido aceptado o denegado el permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //ha concedido el permiso
                        //Meter codigo de llamada
                    } else {
                        //No ha concedido el permiso
                        Toast.makeText(this, "No concedió permisos", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }


    }

    private void showAlertEscuchar(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (titulo != null) {
            builder.setTitle(titulo);
        }
        if (mensaje != null) {
            builder.setMessage(mensaje);
        }
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.layout_escucharenvivo, null);
        builder.setView(viewInflated);
        //final ToggleButton btnLunes = (ToggleButton)viewInflated.findViewById(R.id.btnLunes);
        //final EditText txtSubject = (EditText)viewInflated.findViewById(R.id.txtSubject);
        //final EditText txtTexto = (EditText)viewInflated.findViewById(R.id.txtTexto);

        builder.setPositiveButton("Escuchar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //intent2.setType("*/*");
                Uri webpage = Uri.parse(programa.getLink());
                Intent intent2 = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent2.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent2);
                }
                //Toast.makeText(getApplicationContext(),"Gracias por ponerse en contacto con nosotros", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

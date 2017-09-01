package ar.com.androidappsdhj.miniprogramaspartidarios;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by DarioHJ on 31/08/2017.
 */

public class MyApp extends Application {
    private Realm realm;
    public static AtomicInteger ProgramaID = new AtomicInteger();
    private SharedPreferences prefs;
    public static int contadorPantallas = 1;


    @Override
    public void onCreate() {
        super.onCreate();
        //SystemClock.sleep(3000);
        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        initRealm();


    }

    private boolean validarFirstTime() {
        return prefs.getBoolean("firstTime", false);
    }

    private void saveOnPreferences(String clave){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(clave, true);
        editor.apply();
    }
    private void initRealm(){
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("programas.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

        ProgramaID = setAtomicId(realm,Programa.class);

        if (!validarFirstTime()) {
            //removeAll();
            iniciarListaProgramas();
        }else if(!validarHayUpdate()) {
            actualizarListaProgramas();
            Toast.makeText(getApplicationContext(), "Actualizacion", Toast.LENGTH_LONG).show();
        }
        realm.close();
    }

    private void actualizarListaProgramas() {
        //Update A todo san lorenzo
        Programa programa = realm.where(Programa.class).equalTo("nombre","A Todo San Lorenzo").findFirst();
        realm.beginTransaction();
        programa.setTwitter("https://twitter.com/ATodoSanLorenzo");
        realm.copyToRealmOrUpdate(programa);
        realm.commitTransaction();
        //Update A todo ciclon
        programa = realm.where(Programa.class).equalTo("nombre","A Todo Ciclón").findFirst();
        realm.beginTransaction();
        programa.setLink("https://tunein.com/radio/radio-federal-am-810-s293827/");
        programa.setFacebook("https://www.facebook.com/Atodociclonsl-791938394307854/");
        realm.copyToRealmOrUpdate(programa);
        realm.commitTransaction();
        //Update El plateista
        programa = realm.where(Programa.class).equalTo("nombre","El Plateista").findFirst();
        realm.beginTransaction();
        programa.setLink("https://tunein.com/radio/radio-federal-am-810-s293827/");
        realm.copyToRealmOrUpdate(programa);
        realm.commitTransaction();
        //Update Mundo Azulgrana Radio
        programa = realm.where(Programa.class).equalTo("nombre","Mundo Azulgrana radio").findFirst();
        realm.beginTransaction();
        programa.setLink("https://tunein.com/radio/radio-federal-am-810-s293827/");
        realm.copyToRealmOrUpdate(programa);
        realm.commitTransaction();

        //Insert new program
        Programa pr = new Programa("La Botica de Boedo", R.drawable.laboticadeboedo, "Daniel Hector Aspiro", "AM970","No disponible","No disponible","https://twitter.com/BoticaBoedo","No disponible","No disponible",false,false,false,false,true,false,false,false,"Viernes 22Hs","","AM","http://tunein.com/radio/Radio-G%C3%A9nesis-970-s228340/",false,false,"LaBoticaDeBoedo",false,false,true);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(pr);
        realm.commitTransaction();


        saveOnPreferences("aplicaUpdate");

    }

    private boolean validarHayUpdate() {
        return prefs.getBoolean("aplicaUpdate", false);
    }

    private <T extends RealmObject>  AtomicInteger setAtomicId(Realm realm, Class<T>anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size()>0)? new AtomicInteger(results.max("Id").intValue()): new AtomicInteger();
    }
    private void removeAll(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }
    private void iniciarListaProgramas(){
        realm.executeTransaction(new Realm.Transaction(){

            @Override
            public void execute(Realm realm) {
                Programa p1 = new Programa("A Todo Ciclón", R.drawable.atodociclonnew, "Oscar Sacco", "AM810","saccofutbol@gmail.com","No disponible","https://twitter.com/atodociclonsl","https://www.facebook.com/Atodociclonsl-791938394307854/","No disponible",true,false,false,false,true,false,false,false,"Lunes y Viernes 19Hs","","AM","https://tunein.com/radio/radio-federal-am-810-s293827/",false,false,"ATodoCiclon",false,true,false);
                Programa p2 = new Programa("A Todo San Lorenzo", R.drawable.atodosanlorenzonew, "Carlos García - Fernando Coria", "AM1490","coriafutbol@hotmail.com","No disponible","https://twitter.com/ATodoSanLorenzo","https://www.facebook.com/ATodoSanLorenzoGama/","4218-5333 / 15-4565-2090",true,false,false,true,false,false,false,true,"Lunes y Jueves 21Hs","Días de partidos","AM","http://tunein.com/radio/Radio-Gama-1490-s86865/",false,false,"ATodoSanLorenzo",false,false,true);
                Programa p3 = new Programa("Buenas y Santas", R.drawable.buenasysantas, "", "AM1090","buenasysantasok@yahoo.com","No disponible","https://twitter.com/buenasysantasok","No disponible","4926-1623",false,true,false,false,false,false,false,false,"Martes 19Hs","","AM","http://www.radio1090.com.ar/streaming/radioonline.html",false,false,"BuenasYSantas",false,true,false);
                Programa p4 = new Programa("Cuervos Prohibidos", R.drawable.cuervosprohibidos,"D. Stasevich", "FM 92.5","prohibidoscuervos@gmail.com","No disponible", "https://twitter.com/cuervoprohibido","https://www.facebook.com/CuervosProhibidos","No disponible",true,false,false,false,false,false,false,false,"Lunes 22hs","","FM","http://tunein.com/radio/Signos-FM-925-s84810/",false,false,"CuervosProhibidos",false,false,true);
                Programa p5 = new Programa("Cuervos y basta", R.drawable.cuervosybasta, "Jorge Galeazzo y Franco Landajuela", "FM 105.1 San Nicolas","radio105sn@gmail.com","No disponible","No disponible","No disponible","(0336) 4439254 / whatsapp (336) 4574447",true,false,false,false,false,false,false,false,"Lunes 19Hs","","FM","http://tunein.com/radio/La-Cinco--de-San-Nicol%C3%A1s-1051-s109221/",false,false,"CuervosYBasta",false,true,false);
                Programa p6 = new Programa("El café del ciclón", R.drawable.elcafedelciclon, "Mariano Bongiorno", "AM1290","elcafedelciclon@yahoo.com.ar","http://www.elcafedelciclon.com.ar/","https://twitter.com/elcafedelciclon","No disponible","No disponible",false,false,false,true,false,false,false,false,"Jueves 17hs","","AM","http://us7.maindigitalstream.com/2259/",false,false,"ElCafeDelCiclon",false,true,false);
                Programa p7 = new Programa("El Plateista", R.drawable.elplateista, "Carlos Canissa", "AM810","ccanissa@gmail.com","http://www.elplateista.com.ar/","https://twitter.com/elplateista","https://www.facebook.com/ElPlateista","",false,false,true,false,false,false,false,false,"Miércoles 21Hs","","AM","https://tunein.com/radio/radio-federal-am-810-s293827/",false,false,"ElPlateista",false,false,true);
                Programa p8 = new Programa("Equipo Desafío", R.drawable.equipodesafio, "Julio Axel / Maximiliano Berardo", "AM990","mensajes@equipodesafio.com","http://www.equipodesafio.com/","https://twitter.com/equipodesafio","https://www.facebook.com/Equipo-Desaf%C3%ADo-362011670569256/","No disponible",false,true,false,false,false,false,false,true,"Martes 21Hs","Días de partidos","AM","http://www.splendid990.com.ar/",false,false,"EquipoDesafio",false,false,true);
                Programa p9 = new Programa("Estación Boedo", R.drawable.estacionboedo,"Antonella González - Juan Cruz De Rosa - Matías Ávila - Claudio Pandelo","FM105.9","estacionboedo6@gmail.com","No disponible","https://twitter.com/estacionboedo","https://www.facebook.com/Estacion-Boedo-PQV-792475577446550/","4912-1059 / 15-4079-2402 (WhatsApp)",true,false,false,false,false,false,false,false,"Lunes 21hs","","FM","",false,false,"EstacionBoedo",false,false,true);
                Programa p10 = new Programa("Frenesi Azulgrana", R.drawable.frenesiazulgrana,"Alejandro Marrero , Ale Romero y Pepe Vázquez","AM1050","frenesiazulgranaradio@outlook.com","http://www.frenesiazulgrana.com/","https://twitter.com/FrenesiAzulgran","https://www.facebook.com/frenesiazulgrana/","4381-1672 / 4381 - 2569",false,true,false,true,false,false,false,false,"Martes y Jueves 19Hs","","AM","http://tunein.com/radio/Radio-General-G%C3%BCemes-1050-s135700/",false,false,"FrenesiAzulgrana",false,true,false);
                Programa p11 = new Programa("Gente de San Lorenzo", R.drawable.gentedesanlorenzo,"Claudio Morrone","AM840","No disponible","No disponible","No disponible","No disponible","No disponible",false,false,false,false,true,false,false,false,"Viernes 20Hs","","AM","http://tunein.com/radio/Radio-General-Belgrano-840-s84325/",false,false,"GenteDeSanLorenzo",false,false,true);
                Programa p12 = new Programa("Glorioso San Lorenzo", R.drawable.gloriososanlorenzo,"No disponible","FM88.1","marcosnext@hotmail.com"," No disponible","https://twitter.com/gloriosocasla"," No disponible"," No disponible",false,false,true,false,false,false,false,true,"Miércoles 21Hs","Días de partidos","FM","",false,false,"GloriosoSanLorenzo",false,false,true);
                Programa p13 = new Programa("Hablemos de San Lorenzo", R.drawable.hablemosdesanlorenzo, "Cristian Pagliaro, Rodrigo Castellano y Nicolas Morandi", "AM770","cris_pagliaro05@yahoo.com.ar","http://www.desanlorenzo.com/","https://twitter.com/hablemosdesl","https://www.facebook.com/hablemosdesl/","5275-0770/71/72",true,true,true,true,true,false,true,false,"Lunes a Viernes 22Hs","Domingos 22Hs","AM","http://tunein.com/radio/Radio-Cooperativa-770-s101604/",false,false,"HablemosDeSanLorenzo",false,false,true);
                Programa p14 = new Programa("La Cicloneta", R.drawable.lacicloneta, "Leandro Alves", "AM970","lacicloneta@gmail.com","No disponible","https://twitter.com/lacicloneta","https://www.facebook.com/LaCiclonetaRadio/","4912-0497/0906",false,false,true,false,true,false,true,false,"Miércoles y viernes 21hs","Domingos 22hs","AM","http://tunein.com/radio/Radio-G%C3%A9nesis-970-s228340/",false,false,"LaCicloneta",false,false,true);
                Programa p15 = new Programa("La Cuervería", R.drawable.lacuerveria,"Cristian Paladino","AM840","lacuerveria840@yahoo.com.ar","http://www.semilleroazulgrana.com.ar/","https://twitter.com/lacuerveria","https://www.facebook.com/pg/LaCuerveriaOficial","No disponible",true,false,false,false,false,false,false,false,"Lunes 20Hs","","AM","http://tunein.com/radio/Radio-General-Belgrano-840-s84325/",false,false,"LaCuerveria",false,false,true);
                Programa p16 = new Programa("La hora del Ciclón", R.drawable.lahoradelciclon,"Mario Massi","AM890","alejogarcia090380@gmail.com.ar","No disponible","https://twitter.com/lahoradelciclon","https://www.facebook.com/marioeduardo.massi","No disponible",true,true,true,false,true,false,false,false,"Lunes, martes, miércoles y viernes 19Hs","","AM","http://tunein.com/radio/Radio-Libre-890-s137009/",false,false,"LaHoraDelCiclon",false,true,false);
                Programa p17 = new Programa("Mundo Azulgrana radio", R.drawable.mundoazulgrana,"Rubén Becerra - Mario Benigni","AM810","No disponible","http://mundoazulgrana.com.ar","https://twitter.com/mundoazulgrana","https://www.facebook.com/mundoazulgrana/","4641-3920 /4600-3298",true,false,true,false,true,true,false,false,"Lunes, Miércoles y Viernes 11Hs","","AM","https://tunein.com/radio/radio-federal-am-810-s293827/",false,false,"MundoAzulgranaRadio",true,false,true);
                Programa p18 = new Programa("Opinión azulgrana", R.drawable.opinionazulgrana,"Carlos Perez Castex","AM1490","carlosperezcastex@hotmail.com.ar","No disponible","https://twitter.com/opazulgrana","No disponible","No disponible",false,true,true,true,false,false,true,false,"Domingo, Martes, Miércoles y Jueves 23Hs","","AM","http://www.ustream.tv/channel/22120664",false,false,"OpinionAzulGrana",false,false,true);
                Programa p19 = new Programa("San Lorenzo ayer, hoy y siempre", R.drawable.sanlorenzoayerhoyysiempre,"Adolfo Res y Diego Resnik","AM970","sanlorenzoayerhoysiempre@yahoo.com.ar","http://volveavenidalaplata.com.ar/","https://twitter.com/caslahistoria","https://www.facebook.com/CASLAhistoria/","4926-1894/4926-0041",false,false,false,false,false,false,true,false,"Domingos 13Hs","","AM","http://tunein.com/radio/Radio-G%C3%A9nesis-970-s228340/",false,false,"SanLorenzoAyerHoyYSiempre",false,true,false);
                Programa p20 = new Programa("San Lorenzo de América", R.drawable.sanlorenzodeamerica,"Diego Arvilly","FM93.1","nicomormandi22@gmail.com","No disponible","https://twitter.com/SLDAradio","https://www.facebook.com/SLDAradio/","No disponible",false,true,false,false,false,false,false,false,"Martes 22Hs","","FM","http://tunein.com/radio/Late-931-FM-s184735/",false,false,"SanLorenzoDeAmerica",false,false,true);
                Programa p21 = new Programa("San Lorenzo en acción", R.drawable.sanlorenzoenaccion,"Sebastian Cambas - Lucas Celada","AM1590","sanlorenzoenaccion@hotmail.com","No disponible","https://twitter.com/slenaccion","San Lorenzo en Acción","4382-4293",false,false,false,true,false,false,false,true,"Jueves 20Hs","Días de partidos","AM","http://www.am1590.com.ar/",false,false,"SanLorenzoEnAccion",false,false,true);
                Programa p22 = new Programa("San Lorenzo eterno", R.drawable.sanlorenzoeternonew,"Marcelo Culotta","AM970","sanlorenzoeterno@gmail.com","No disponible","https://twitter.com/sleterno","https://www.facebook.com/SanLorenzoEterno/","4926-1622",false,true,false,false,false,false,false,false,"Martes 20Hs","","AM","http://tunein.com/radio/Radio-G%C3%A9nesis-970-s228340/",false,false,"SanLorenzoEterno",false,false,true);
                Programa p23 = new Programa("San Lorenzo mi pasión", R.drawable.sanlorenzomipasionnew,"Víctor Federico","AM970","victorfederico970@gmail.com","No disponible","https://twitter.com/vueltaboedo","https://www.facebook.com/victor.federico.7","No disponible",false,false,false,false,false,true,false,false,"Sábados 12Hs","","AM","http://tunein.com/radio/Radio-G%C3%A9nesis-970-s228340/",false,false,"SanLorenzoMiPasion",true,false,false);
                Programa p24 = new Programa("Sentimiento Azulgrana", R.drawable.sentimientoazulgrana,"Mario Massi","AM890","alejogarcia10@hotmail.com","No disponible","No disponible","https://www.facebook.com/profile.php?id=100006642735130","No disponible",false,false,false,false,false,false,false,true,"Días de partidos","","AM","http://tunein.com/radio/Radio-Libre-890-s137009/",false,false,"SentimientoAzulGrana",false,false,false);
                //Programa p25 = new Programa("Simplemente San Lorenzo", R.drawable.simplementesanlorenzo,"Gustavo Bennasar y Adrián Disabato", "AM610", "simplementesanlorenzo@hotmail.com","http://simplementesanlorenzoweb.blogspot.com.ar/","No disponible","https://www.facebook.com/SimplementeSanLorenzo/","4542-6500",false,false,false,false,true,false,false,false,"Viernes 14hs","","AM","http://tunein.com/radio/AM610-Radio-General-San-Martin-s253609/",false,false,"SimplementeSanLorenzo",false,true,true);
                Programa p26 = new Programa("Soy San Lorenzo", R.drawable.soysanlorenzo,"Mario Andrés Benigni","AM690","soysanlorenzo@gmail.com","http://www.soysanlorenzo.com.ar/","https://twitter.com/cirujanomb","https://www.facebook.com/soysan.lorenzo.3", "46425533 / 46425315 / 15-5335-0310",true,true,true,true,true,false,false,false,"Lunes a Viernes 23Hs","","AM","http://tunein.com/radio/K24-s288566/",false,false,"SoySanLorenzo",false,false,true );
                Programa p27 = new Programa("Equipo Desafío TV", R.drawable.equipodesafio,"Julio Axel yPablo Sassone","Canal 360TV","mensajes@equipodesafio.com","http://www.equipodesafio.com/","https://twitter.com/equipodesafio","https://www.facebook.com/Equipo-Desaf%C3%ADo-362011670569256/","No disponible",false,true,true,false,false,false,false,false,"Martes 20Hs","Miércoles 13Hs","TV","",false,false,"EquipoDesafioTV",false,true,true );
                //Programa p28 = new Programa("La Botica de Boedo", R.drawable.laboticadeboedo, "Daniel Hector Aspiro", "AM970","No disponible","No disponible","https://twitter.com/BoticaBoedo","No disponible","No disponible",false,false,false,false,true,false,false,false,"Viernes 22Hs","","AM","http://tunein.com/radio/Radio-G%C3%A9nesis-970-s228340/",false,false,"LaBoticaDeBoedo",false,false,true);

                realm.copyToRealmOrUpdate(p1);
                realm.copyToRealmOrUpdate(p2);
                realm.copyToRealmOrUpdate(p3);
                realm.copyToRealmOrUpdate(p4);
                realm.copyToRealmOrUpdate(p5);
                realm.copyToRealmOrUpdate(p6);
                realm.copyToRealmOrUpdate(p7);
                realm.copyToRealmOrUpdate(p8);
                realm.copyToRealmOrUpdate(p9);
                realm.copyToRealmOrUpdate(p10);
                realm.copyToRealmOrUpdate(p11);
                realm.copyToRealmOrUpdate(p12);
                realm.copyToRealmOrUpdate(p13);
                realm.copyToRealmOrUpdate(p14);
                realm.copyToRealmOrUpdate(p15);
                realm.copyToRealmOrUpdate(p16);
                realm.copyToRealmOrUpdate(p17);
                realm.copyToRealmOrUpdate(p18);
                realm.copyToRealmOrUpdate(p19);
                realm.copyToRealmOrUpdate(p20);
                realm.copyToRealmOrUpdate(p21);
                realm.copyToRealmOrUpdate(p22);
                realm.copyToRealmOrUpdate(p23);
                realm.copyToRealmOrUpdate(p24);
                //realm.copyToRealmOrUpdate(p25);
                realm.copyToRealmOrUpdate(p26);
                realm.copyToRealmOrUpdate(p27);
                //realm.copyToRealmOrUpdate(p28);

                saveOnPreferences("firstTime");
               // saveOnPreferences("aplicaUpdate");
            }
        });
    }
}

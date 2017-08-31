package ar.com.androidappsdhj.miniprogramaspartidarios;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DarioHJ on 31/08/2017.
 */

public class Programa extends RealmObject implements Serializable {

    @PrimaryKey
    private int Id;
    private String nombre;
    private String conductores;
    private String emisora;
    private String eMail;
    private String web;
    private String twitter;
    private String facebook;
    private String telefono;
    private int imagen;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;
    private boolean sabado;
    private boolean domingo;
    private boolean diaPartido;
    private String diaUno;
    private String diaDos;
    private String medio;
    private String link;
    private boolean favorito;
    private boolean notificar;
    private String topicNotificacion;
    private boolean manana;
    private boolean tarde;
    private boolean noche;

    public Programa() {
    } //Solo para Realm

    public Programa(String nombre, int imagen, String conductores, String emisora, String eMail,String web, String twitter, String facebook,
                    String telefono, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes, boolean sabado,
                    boolean domingo, boolean diaPartido, String diaUno, String diaDos, String medio, String link,boolean favorito, boolean notificar,String topicNotificacion,boolean manana,
                    boolean tarde, boolean noche) {
        Id = MyApp.ProgramaID.incrementAndGet();
        this.nombre = nombre;
        this.imagen = imagen;
        this.conductores = conductores;
        this.emisora = emisora;
        this.eMail = eMail;
        this.web = web;
        this.twitter = twitter;
        this.facebook = facebook;
        this.telefono = telefono;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
        this.diaPartido = diaPartido;
        this.diaUno = diaUno;
        this.diaDos = diaDos;
        this.medio = medio;
        this.favorito = favorito;
        this.manana = manana;
        this.tarde = tarde;
        this.noche = noche;
        this.link = link;
        this.notificar = notificar;
        this.topicNotificacion = topicNotificacion;
    }

    public Programa clone(){
        Programa p = new Programa();
        p.Id = this.Id;
        p.nombre = this. nombre;
        p.imagen = this. imagen;
        p.conductores = this. conductores;
        p.emisora = this. emisora;
        p.eMail = this. eMail;
        p.web = this. web;
        p.twitter = this. twitter;
        p.facebook = this. facebook;
        p.telefono = this. telefono;
        p.lunes = this. lunes;
        p.martes = this. martes;
        p.miercoles = this. miercoles;
        p.jueves = this. jueves;
        p.viernes = this. viernes;
        p.sabado = this. sabado;
        p.domingo = this. domingo;
        p.diaPartido = this. diaPartido;
        p.diaUno = this. diaUno;
        p.diaDos = this. diaDos;
        p.medio = this. medio;
        p.favorito = this. favorito;
        p.manana = this. manana;
        p.tarde = this. tarde;
        p.noche = this. noche;
        p.link = this. link;
        p.notificar = this. notificar;
        p.topicNotificacion = this. topicNotificacion;
        return p;

    }
    public int getId() {
        return Id;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getConductores() {
        return conductores;
    }

    public void setConductores(String conductores) {
        this.conductores = conductores;
    }

    public String getEmisora() {
        return emisora;
    }

    public void setEmisora(String emisora) {
        this.emisora = emisora;
    }

    public boolean isLunes() {
        return lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public void setDiaPartido(boolean diaPartido) {
        this.diaPartido = diaPartido;
    }

    public boolean isViernes() {
        return viernes;
    }

    public boolean isSabado() {
        return sabado;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public boolean isDiaPartido() {
        return diaPartido;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDiaUno() {
        return diaUno;
    }

    public void setDiaUno(String diaUno) {
        this.diaUno = diaUno;
    }

    public String getDiaDos() {
        return diaDos;
    }

    public void setDiaDos(String diaDos) {
        this.diaDos = diaDos;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public boolean isManana() {
        return manana;
    }

    public void setManana(boolean manana) {
        this.manana = manana;
    }

    public boolean isTarde() {
        return tarde;
    }

    public void setTarde(boolean tarde) {
        this.tarde = tarde;
    }

    public boolean isNoche() {
        return noche;
    }

    public void setNoche(boolean noche) {
        this.noche = noche;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isNotificar() {
        return notificar;
    }

    public void setNotificar(boolean notificar) {
        this.notificar = notificar;
    }

    public String getTopicNotificacion() {
        return topicNotificacion;
    }

    public void setTopicNotificacion(String topicNotificacion) {
        this.topicNotificacion = topicNotificacion;
    }
}

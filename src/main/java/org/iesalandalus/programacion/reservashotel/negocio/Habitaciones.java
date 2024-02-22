package org.iesalandalus.programacion.reservashotel.negocio;

import org.iesalandalus.programacion.reservashotel.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;

public class Habitaciones {
    private int capacidad;
    private int tamano;
    private Habitacion[] coleccionHabitaciones;

    public Habitaciones(int capacidad){
        if (capacidad<1){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        coleccionHabitaciones=new Habitacion[capacidad];
    }
    public Habitacion[] get(){
        coleccionHabitaciones=copiaProfundaHabitaciones();
        return coleccionHabitaciones;
    }
    private Habitacion[] copiaProfundaHabitaciones(){
        tamano=getTamano();
        if (tamano<1){
            throw new NullPointerException("ERROR: No es posible copiar una colección vacía");
        }
        if (coleccionHabitaciones==null)
            throw new NullPointerException("ERROR:Colección vacía5");
        Habitacion[] copiaProfundaHabitaciones = new Habitacion[getCapacidad()];
        for (int i = 0; i < tamano; i++) {
            copiaProfundaHabitaciones[i] = new Habitacion(coleccionHabitaciones[i]);
        }
        return copiaProfundaHabitaciones;
    }
    public Habitacion[] get(TipoHabitacion tipoHabitacion){
        tamano=getTamano();
        int j=0;
        if (tamano<1){
            throw new NullPointerException("ERROR: No es posible copiar una colección vacía");
        }
        if (coleccionHabitaciones==null)
            throw new NullPointerException("ERROR:Colección vacía5");
        Habitacion[] copiaProfundaHabitacionesFiltro = new Habitacion[getCapacidad()];
        for (int i = 0; i < tamano; i++) {
            if (coleccionHabitaciones[i].getTipoHabitacion().equals(tipoHabitacion)){
                copiaProfundaHabitacionesFiltro[j] = new Habitacion(coleccionHabitaciones[i]);
                j++;
            }
        }
        return copiaProfundaHabitacionesFiltro;
    }

    public int getTamano() {
        for (int t = 0; t < getCapacidad() ; t++) {
            if (coleccionHabitaciones[t] == null) {
                return t;
            }
        }
        return getCapacidad();
    }

    public int getCapacidad() {
        capacidad=coleccionHabitaciones.length;
        return  capacidad;
    }
    public void insertar (Habitacion habitacion) throws OperationNotSupportedException {
        if (coleccionHabitaciones==null)
            throw new NullPointerException("ERROR:Colección inexistente");
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        if (buscarIndice(habitacion)<getCapacidad()){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        if (getTamano()<getCapacidad()){
            coleccionHabitaciones[getTamano()]=new Habitacion(habitacion);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No se aceptan más habitaciones.");
        }
    }
    private int buscarIndice(Habitacion habitacion){
        for(int i = 0; i < getTamano(); i++){
            if (coleccionHabitaciones[i]==null){
                return getCapacidad()+1;
            }
            else if (coleccionHabitaciones[i].equals(habitacion)){
                return i;
            }
        }
        return getCapacidad()+1;
    }
    private Boolean tamanoSuperado(int indice){
        if (indice<0){
            throw new IllegalArgumentException("ERROR: Indice tamaño incorrecto");
        }
        else if (indice >0 && indice<getTamano()){
            return false;
        }
        return true;
    }
    private Boolean capacidadSuperada(int indice){
        if (indice<0){
            throw new IllegalArgumentException("ERROR: Indice capacidad incorrecto");
        }
        else if (indice >0 && indice<getCapacidad()){
            return false;
        }
        return true;
    }
    public Habitacion buscar(Habitacion habitacion){
        if (habitacion==null)
            throw new NullPointerException("ERROR:Buscar habitación nula");
        if (coleccionHabitaciones==null)
            throw new NullPointerException("ERROR:Colección vacía");
        for(int i = 0; i < getTamano(); i++){
            if (coleccionHabitaciones[i].equals(habitacion)){//huesped.getDni().equals(coleccionHuespedes[i].getDni())){ //coleccionHuespedes[i].equals(huesped)
                return new Habitacion(habitacion);
            }
        }
        return null;
    }
    public void borrar (Habitacion habitacion) throws OperationNotSupportedException{
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        int indice = buscarIndice(habitacion);
        if (indice<=getCapacidad()){
            coleccionHabitaciones[indice]=null;
            desplazarUnaPosicionIzquierda(indice);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
    }
    private void desplazarUnaPosicionIzquierda(int indice){
        for (int i=indice ; i<getCapacidad()-1 ; i++){
            if (coleccionHabitaciones[i+1]!=null){
                coleccionHabitaciones[i]=new Habitacion(coleccionHabitaciones[i+1]);
                coleccionHabitaciones[i+1]=null;
            }
            else return;
        }
    }
}

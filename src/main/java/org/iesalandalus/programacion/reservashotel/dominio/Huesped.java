package org.iesalandalus.programacion.reservashotel.dominio;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;
    //Array para la validaci�n del DNI
    private static final List<Character> ER_DNI = Arrays.asList('T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E');
    private static final String ER_TELEFONO = "[0-9]{9}";
    private static final String ER_CORREO = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento) throws NullPointerException {
        if (nombre==null){
            throw new NullPointerException("ERROR: El nombre de un hu�sped no puede ser nulo.");
        }
        else if (dni==null){
            throw new NullPointerException("ERROR: El dni de un hu�sped no puede ser nulo.");
        }
        else if (correo==null){
            throw new NullPointerException("ERROR: El correo de un hu�sped no puede ser nulo.");
        }
        else if (telefono==null){
            throw new NullPointerException("ERROR: El tel�fono de un hu�sped no puede ser nulo.");
        }
        else if (fechaNacimiento==null){
            throw new NullPointerException("ERROR: La fecha de nacimiento de un hu�sped no puede ser nula.");
        }
        else {
            setNombre(nombre);
            setDni(dni);
            setCorreo(correo);
            setTelefono(telefono);
            setFechaNacimiento(fechaNacimiento);
        }
    }

    // Constructor copia
    public Huesped(Huesped huesped){
        if(huesped==null)
            throw new NullPointerException("ERROR: No es posible copiar un hu�sped nulo.");
        this.nombre=huesped.nombre;
        this.dni=huesped.dni;
        this.correo=huesped.correo;
        this.telefono=huesped.telefono;
        this.fechaNacimiento=huesped.fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws NullPointerException,IllegalArgumentException {
       if(nombre==null)
           throw new NullPointerException("ERROR: El nombre de un hu�sped no puede ser nulo.");

       if (nombre.isBlank()||nombre.isEmpty())
           throw new IllegalArgumentException("ERROR: El nombre de un hu�sped no puede estar vac�o.");

       else{
           this.nombre = formateaNombre(nombre);
       }
    }

    private String formateaNombre(String nombre){

        //Eliminamos los espacios dobles
        String regex = "\\s+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);

        // Corregimos las may�sculas y min�sculas
        String nombreFormateado = "";
        boolean inicioNombre = true;

        String[] palabras = nombre.split(" ");

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                if (inicioNombre) {
                    nombreFormateado = Character.toUpperCase(palabra.charAt(0))+palabra.substring(1).toLowerCase();
                    inicioNombre = false;
                } else {
                    nombreFormateado = nombreFormateado+" "+Character.toUpperCase(palabra.charAt(0))+palabra.substring(1).toLowerCase();
                }
            }
        }
        nombre=nombreFormateado;
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) throws IllegalArgumentException,NullPointerException
    {
        if(telefono==null)
            throw new NullPointerException("ERROR: El tel�fono de un hu�sped no puede ser nulo.");

        if (telefono.isBlank()||telefono.isEmpty())
            throw new IllegalArgumentException("ERROR: El tel�fono del hu�sped no tiene un formato v�lido.");

        else {
            if (telefono.matches(ER_TELEFONO)) {
                this.telefono = telefono;
            } else {
                throw new IllegalArgumentException("ERROR: El tel�fono del hu�sped no tiene un formato v�lido.");
            }
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if(correo==null)
            throw new NullPointerException("ERROR: El correo de un hu�sped no puede ser nulo.");

        if (correo.isBlank()||correo.isEmpty())
            throw new IllegalArgumentException("ERROR: El correo del hu�sped no tiene un formato v�lido.");

        else {
            Pattern pattern = Pattern.compile(ER_CORREO);
            Matcher matcher = pattern.matcher(correo);
            if (matcher.matches()) {
                this.correo = correo;
            } else {
                throw new IllegalArgumentException("ERROR: El correo del hu�sped no tiene un formato v�lido.");
            }
        }
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni)
    {
        if (comprobarLetraDni(dni)){
            this.dni = dni;
        }
        else {
            throw new IllegalArgumentException("ERROR: La letra del dni del hu�sped no es correcta.");
        }
    }

    private static boolean comprobarLetraDni(String dni){
        if (dni == null || dni.length() != 9) {
            throw new IllegalArgumentException("ERROR: El dni del hu�sped no tiene un formato v�lido.");
        }

        // Partimos el DNI en dos, separando n�meros y letra
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letra = dni.charAt(8);

        if (numero < 0 || numero > 99999999) {
            return false;
        }

        int resto = numero % 23;
        return letra == ER_DNI.get(resto);
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    private void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento.isBefore(LocalDate.now())){
            this.fechaNacimiento = fechaNacimiento;
        }
        else {
            System.out.println("ERROR: La fecha de nacimiento no puede ser posterior a la actual.");
        }
    }

    private String getIniciales(){
        if (nombre == null || nombre.isEmpty()) {
            return "";
        }

        String iniciales = "";
        String[] partes = nombre.split(" "); // parte el nombre por "palabras"

        for (String parte : partes) {
            if (!parte.isEmpty()) {
                iniciales = iniciales.concat(String.valueOf(parte.charAt(0))); // a�ade a la cadena la primera letra de cada parte
            }
        }

        return iniciales.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Huesped huesped)) return false;
        return Objects.equals(dni, huesped.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, telefono, correo, dni, fechaNacimiento);
    }

    @Override
    public String toString() {
        return "nombre=" + nombre + " ("+getIniciales()+")" +
                ", DNI=" + dni  +
                ", correo=" + correo +
                ", tel�fono=" + telefono +
                ", fecha nacimiento=" + String.format("%02d/%02d/%d", fechaNacimiento.getDayOfMonth(), fechaNacimiento.getMonthValue(), fechaNacimiento.getYear());
    }
}

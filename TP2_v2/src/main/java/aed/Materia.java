package aed;

import java.util.ArrayList;

public class Materia {
    private int[] _docentes;
    private ArrayList<String> _alumnado;
    

public Materia(){
this._docentes = new int[4];
this._alumnado = new ArrayList<String>();
}
public void inscribir (String libreta){
    this._alumnado.add(libreta);
    
}
public int cantidadAlumnos(){
    return this._alumnado.size();
    
}
public void agregarProfesor(){
    this._docentes[0] = this._docentes[0]+1;
}
public void agregarJtp(){
    this._docentes[1] = this._docentes[1]+1;
}
public void agregarAy1(){
    this._docentes[2] = this._docentes[2]+1;
}
public void agregarAy2(){
    this._docentes[3] = this._docentes[3]+1;
}
public int[] obtenerDocentes(){
    return this._docentes;
}
public boolean excedeCupo(){
    int cantAlumnos = this._alumnado.size();
    boolean excedeProfesores = cantAlumnos > 250 * this._docentes[0];
    boolean excedeJtp = cantAlumnos > 100 * this._docentes[1];
    boolean excedeAy1 = cantAlumnos > 20 * this._docentes[2];
    boolean excedeAy2 = cantAlumnos > 30 * this._docentes[3];

    return excedeProfesores || excedeJtp || excedeAy1 || excedeAy2;
}


}
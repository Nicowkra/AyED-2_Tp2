package aed;

public class Alumno {
    private int _cantidadMaterias;

public Alumno(){
    this._cantidadMaterias = 0;
}
public void dejarMateria(){
    if(this._cantidadMaterias > 0){
        this._cantidadMaterias -= 1;
    }
}
public void agregarMateria(){
    this._cantidadMaterias += 1;
}
public int cantidadMaterias(){
    return this._cantidadMaterias;
}

}

package aed;


//Invariante de representación: _cantidadMaterias es mayor o igual a 0 y menor o igual a la cantidad de materias que hay. Representa la cantidad de materias a las que está inscripto el alumno.  
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
//Todas las implementaciones son O(1)

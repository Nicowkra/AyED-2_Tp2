package aed;


//Invariante de representación: En el dictTrie _materias la cantidad de materias es mayor o igual a 0. Cumple con el invariante de representación del DictTrie (escrito en el archivo DictTrie).

public class Carrera {
    private DictTrie<Materia> _materias;

public Carrera(){
    this. _materias = new DictTrie<Materia>();
}

public void crearMateria(String nombreMateria, Materia _materia){
    this._materias.agregar(nombreMateria, _materia);                //O(|m|): Cantidad de caracteres de la materia
}
public Materia obtenerMateria(String nombreMateria){
    return (Materia) this._materias.obtener(nombreMateria);         //O(|m|): Cantidad de caracteres de la materia
}
public void cerrarMateria(String nombreMateria){                    //O(|m|): Cantidad de caracteres de la materia
    this._materias.eliminar(nombreMateria);
}
public String[] materiasEnCarreras() {
    return _materias.palabras();                                    //O(1)
}

}
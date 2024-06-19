package aed;

public class Carrera {
    private DictTrie<Materia> _materias;

public Carrera(){
    this. _materias = new DictTrie<Materia>();
}

public void crearMateria(String nombreMateria, Materia _materia){
    this._materias.agregar(nombreMateria, _materia);
}
public Materia obtenerMateria(String nombreMateria){
    return (Materia) this._materias.obtener(nombreMateria);
}
public void cerrarMateria(String nombreMateria){
    this._materias.eliminar(nombreMateria);
}
public String[] materiasEnCarreras() {
    return _materias.palabras();
}

}
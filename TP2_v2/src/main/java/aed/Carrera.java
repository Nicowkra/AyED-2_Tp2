package aed;

public class Carrera {
    private Trie<Materia> _materias;

public Carrera(){
    this. _materias = new Trie<Materia>();
}

public void crearMateria(String nombreMateria, Materia _materia){
    this._materias.agregar(nombreMateria, _materia);
}
public Materia obtenerMateria(String nombreMateria){
    return this._materias.obtener(nombreMateria);
}

}
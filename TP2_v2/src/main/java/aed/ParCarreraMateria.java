package aed;
//Invariante de representación: ni carrera ni nombreMateria pueden ser null.
public class ParCarreraMateria {
    String carrera;
    String nombreMateria;

    public ParCarreraMateria(String carrera, String nombreMateria) {
        this.carrera = carrera;
        this.nombreMateria = nombreMateria;
    }

    public String getNombreMateria() {
        return this.nombreMateria;
    }

    public String getCarrera() {
        return this.carrera;
    }
}
//Todas las complejidades son O(1)
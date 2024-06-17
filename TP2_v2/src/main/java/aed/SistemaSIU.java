package aed;

import java.util.ArrayList;

public class SistemaSIU {
    private Trie<Carrera> _carreras;
    private Trie<Alumno> _alumnos;
    private ArrayList<Materia> _materias;
    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
       this._carreras = new Trie<Carrera>();
       this._alumnos = new Trie<Alumno>();
       this._materias = new ArrayList<Materia>();
       
    
       for(int i= 0;i < infoMaterias.length; i += 1){
            ParCarreraMateria[] nombres = (infoMaterias[i].getParesCarreraMateria());
            for (int j= 0;j < nombres.length; j += 1){
                String nombreCarrera = nombres[j].carrera;
                String nombreMateria = nombres[j].nombreMateria;
                Carrera actual = this._carreras.obtener(nombreCarrera);
                if (actual == null){
                    Carrera nuevaCarrera = new Carrera();
                    Materia nuevaMateria = new Materia();
                    this._carreras.agregar(nombreCarrera,nuevaCarrera);
                    nuevaCarrera.crearMateria(nombreMateria,nuevaMateria);
                }else{
                    Materia NuevaMateria = new Materia();
                    actual.crearMateria(nombreMateria, NuevaMateria);
                    this._carreras.agregar(nombreMateria, actual);
                }
            }
       }
       for(int i = 0; i < libretasUniversitarias.length; i += 1){
        Alumno alumnoNuevo = new Alumno();
        String libreta = libretasUniversitarias[i];
        this._alumnos.agregar(libreta, alumnoNuevo);
       }	    
    }

    public void inscribir(String estudiante, String carrera, String materia){
       Carrera carreraActual = this._carreras.obtener(carrera);
       Materia materiaActual = carreraActual.obtenerMateria(materia);
       Alumno alumnoActual = this._alumnos.obtener(estudiante);
       alumnoActual.agregarMateria();
       materiaActual.inscribir(estudiante);

    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int[] plantelDocente(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public boolean excedeCupo(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }
}

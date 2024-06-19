package aed;

import java.util.ArrayList;

public class SistemaSIU {
    private DictTrie<Carrera> _carreras;
    private DictTrie<Alumno> _alumnos;
    private ArrayList<Materia> _materias;
    enum CargoDocente{
        AY2,
        AY1,
        JTP,
        PROF
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
       this._carreras = new DictTrie<Carrera>();
       this._alumnos = new DictTrie<Alumno>();
       this._materias = new ArrayList<Materia>();
       
    
       for(int i= 0;i < infoMaterias.length; i += 1){
            Materia nuevaMateria = new Materia();
            ParCarreraMateria[] nombres = (infoMaterias[i].getParesCarreraMateria());
            for (int j= 0;j < nombres.length; j += 1){
                String nombreCarrera = nombres[j].carrera;
                String nombreMateria = nombres[j].nombreMateria;
                Carrera actual = (Carrera) this._carreras.obtener(nombreCarrera);
                if (actual == null){
                    Carrera nuevaCarrera = new Carrera();
                    this._carreras.agregar(nombreCarrera,nuevaCarrera);
                    nuevaCarrera.crearMateria(nombreMateria,nuevaMateria);
                }else{
                    actual.crearMateria(nombreMateria, nuevaMateria);
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
       Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);
       Materia materiaActual = carreraActual.obtenerMateria(materia);
       Alumno alumnoActual = (Alumno) this._alumnos.obtener(estudiante);
       alumnoActual.agregarMateria();
       materiaActual.inscribir(estudiante);

    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);
        Materia materiaActual = carreraActual.obtenerMateria(materia);
       if (cargo == CargoDocente.PROF){
        materiaActual.agregarProfesor();
       }
       else if (cargo == CargoDocente.JTP){
        materiaActual.agregarJtp();
       }
       else if (cargo == CargoDocente.AY1){
        materiaActual.agregarAy1();
       }
       else if (cargo == CargoDocente.AY2){
        materiaActual.agregarAy2();
       }
    }

    public int[] plantelDocente(String materia, String carrera){
        Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);
        Materia materiaActual = carreraActual.obtenerMateria(materia);
        return materiaActual.obtenerDocentes();
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);
        Materia materiaActual = carreraActual.obtenerMateria(materia);
        return materiaActual.cantidadAlumnos();
        }

    public boolean excedeCupo(String materia, String carrera){
        Carrera carreraActual = (Carrera) this._carreras.obtener(carrera);
        Materia materiaActual = carreraActual.obtenerMateria(materia);
        return materiaActual.excedeCupo();
    }

    public String[] carreras(){
        return _carreras.palabras();
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        Alumno alumnoActual = (Alumno) this._alumnos.obtener(estudiante);
        return alumnoActual.cantidadMaterias();
    }
}

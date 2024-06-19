package aed;

import java.util.ArrayList;

public class SistemaSIU {
    private InfoMateria[] infoMaterias;
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
       this.infoMaterias = infoMaterias;
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

    private ParCarreraMateria[] buscarEnInfoMaterias(String materia, String carrera) {
        ParCarreraMateria[] info = null;

        for (int i = 0; i < infoMaterias.length; i++) {
            ParCarreraMateria[] pares = (infoMaterias[i].getParesCarreraMateria());

            for (int j = 0; j < pares.length; j++) {
                    
                if (pares[j].getCarrera()==carrera && 
                    pares[j].getNombreMateria()==materia) {

                    info = pares;
                    break;
                }
            }
            if (info != null) {
                break;
            }
        }
        return info;
    }

    public void cerrarMateria(String materia, String carrera){
        Carrera carreraActual;
        Materia materiaActual = null;
        ParCarreraMateria[] info = buscarEnInfoMaterias(materia, carrera);
        for (int i = 0; i < info.length; i++) {
            
            carreraActual = (Carrera) _carreras.obtener(info[i].getCarrera());

            //lo hace una sola vez
            if (materiaActual == null) {
                materiaActual = (Materia) carreraActual.obtenerMateria(info[i].getNombreMateria());
            }   
            carreraActual.cerrarMateria(info[i].getNombreMateria());
        }
        for (int i = 0; i < materiaActual.cantidadAlumnos(); i++) {
            String LU = materiaActual.obtenerAlumnos().get(i);
            Alumno alumnoActual = (Alumno) _alumnos.obtener(LU);
            alumnoActual.dejarMateria();
        }
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
        Carrera carreraActual = (Carrera) _carreras.obtener(carrera);
        return carreraActual.materiasEnCarreras();
    }

    public int materiasInscriptas(String estudiante){
        Alumno alumnoActual = (Alumno) this._alumnos.obtener(estudiante);
        return alumnoActual.cantidadMaterias();
    }
}

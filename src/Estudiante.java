public class Estudiante implements Comparable<Estudiante> {
    private String id;
    private String nombre;
    private String apellido;
    private String sexo;
    private int ano;
    private boolean militante;
    private boolean becado;

    public Estudiante(String id, String nombre, String apellido,
                      String sexo, int ano, boolean militante, boolean becado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.ano = ano;
        this.militante = militante;
        this.becado = becado;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public int getAno() {
        return ano;
    }

    public boolean isMilitante() {
        return militante;
    }

    public boolean isBecado() {
        return becado;
    }

    @Override
    public int compareTo(Estudiante e) {
        return Integer.compare(this.ano, e.getAno());
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo='" + sexo + '\'' +
                ", ano=" + ano +
                ", militante=" + militante +
                ", becado=" + becado +
                '}';
    }
}

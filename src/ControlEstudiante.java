public class ControlEstudiante {
    private ListaEnlazada<Estudiante> listadoEstudiante = new ListaEnlazada<>();

    public void agregarEstudiante(Estudiante e) {
        listadoEstudiante.agregar(e);
    }

    public ListaEnlazada<Estudiante> getListadoEstudiante() {
        return listadoEstudiante;
    }

    // a) Nombres de los estudiantes que cumplen años en el mes dado ("01".."12").
    public ListaEnlazada<String> listarPorCumpleanos(String mes) {
        ListaEnlazada<String> listado = new ListaEnlazada<>();
        String mesNormalizado = (mes.length() == 1) ? "0" + mes : mes; // acepta "3" o "03"
        Nodo<Estudiante> actual = listadoEstudiante.getCabeza();

        while (actual != null) {
            Estudiante e = actual.getDato();
            String mesNacimiento = e.getId().substring(2, 4);
            if (mesNacimiento.equals(mesNormalizado)) {
                listado.agregar(e.getNombre());
            }
            actual = actual.getSiguiente();   // <- faltaba: sin esto el while no termina nunca
        }
        return listado;
    }

    // b) Militantes de la UJC ordenados por año de menor a mayor (burbuja, estable).
    public ListaEnlazada<Estudiante> listarMilitantes() {
        ListaEnlazada<Estudiante> listadoMilitantes = new ListaEnlazada<>();
        Nodo<Estudiante> actual = listadoEstudiante.getCabeza();

        while (actual != null) {
            Estudiante e = actual.getDato();
            if (e.isMilitante()) {
                listadoMilitantes.agregar(e);
            }
            actual = actual.getSiguiente();
        }

        if (listadoMilitantes.getCabeza() != null) {
            boolean huboIntercambio;
            do {
                huboIntercambio = false;
                Nodo<Estudiante> p = listadoMilitantes.getCabeza();
                while (p.getSiguiente() != null) {
                    Nodo<Estudiante> siguiente = p.getSiguiente();
                    if (p.getDato().compareTo(siguiente.getDato()) > 0) {
                        Estudiante temp = p.getDato();
                        p.setDato(siguiente.getDato());
                        siguiente.setDato(temp);
                        huboIntercambio = true;
                    }
                    p = p.getSiguiente();
                }
            } while (huboIntercambio);
        }
        return listadoMilitantes;
    }

    // c) Cantidad de estudiantes becados.
    public int cantBecados() {
        int contador = 0;
        Nodo<Estudiante> actual = listadoEstudiante.getCabeza();
        while (actual != null) {
            if (actual.getDato().isBecado()) {
                contador++;
            }
            actual = actual.getSiguiente();
        }
        return contador;
    }
}

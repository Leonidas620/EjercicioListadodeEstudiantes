public class Main {

    private static int pasadas = 0;
    private static int falladas = 0;

    public static void main(String[] args) {
        ControlEstudiante control = new ControlEstudiante();

        // CI con formato AAMMDDxxxxx -> el mes de cumpleaños son las posiciones 2 y 3
        control.agregarEstudiante(new Estudiante("05011212345", "Ana",    "Perez",   "F", 1, true,  true));
        control.agregarEstudiante(new Estudiante("04031556789", "Luis",   "Gomez",   "M", 3, true,  false));
        control.agregarEstudiante(new Estudiante("03030811223", "Marta",  "Diaz",    "F", 4, false, true));
        control.agregarEstudiante(new Estudiante("06072033445", "Carlos", "Ruiz",    "M", 2, true,  true));
        control.agregarEstudiante(new Estudiante("02031099887", "Elena",  "Torres",  "F", 5, true,  false));
        control.agregarEstudiante(new Estudiante("05121277665", "Pedro",  "Lopez",   "M", 1, true,  false));
        control.agregarEstudiante(new Estudiante("04052266554", "Sofia",  "Mendez",  "F", 2, false, false));

        System.out.println("=== a) Cumpleaños ===");
        mostrar("Mes 03", control.listarPorCumpleanos("03"));
        mostrar("Mes 01", control.listarPorCumpleanos("01"));
        mostrar("Mes 12 (último de la lista)", control.listarPorCumpleanos("12"));
        mostrar("Mes 05", control.listarPorCumpleanos("05"));
        mostrar("Mes 11 (nadie)", control.listarPorCumpleanos("11"));
        verificarNombres("Cumpleaños mes 03",  control.listarPorCumpleanos("03"), "Luis", "Marta", "Elena");
        verificarNombres("Cumpleaños mes 01",  control.listarPorCumpleanos("01"), "Ana");
        verificarNombres("Cumpleaños mes 12 (último nodo)", control.listarPorCumpleanos("12"), "Pedro");
        verificarNombres("Cumpleaños mes 05",  control.listarPorCumpleanos("05"), "Sofia");
        verificarNombres("Cumpleaños mes 11 (vacío)", control.listarPorCumpleanos("11"));
        verificarNombres("Cumpleaños '3' sin cero", control.listarPorCumpleanos("3"), "Luis", "Marta", "Elena");

        System.out.println("\n=== b) Militantes ordenados por año ===");
        ListaEnlazada<Estudiante> militantes = control.listarMilitantes();
        Nodo<Estudiante> n = militantes.getCabeza();
        while (n != null) {
            System.out.println("  " + n.getDato());
            n = n.getSiguiente();
        }
        // Ana(1) y Pedro(1) empatan: se espera que conserven su orden de inserción
        verificarOrdenMilitantes("Militantes por año", militantes, "Ana", "Pedro", "Carlos", "Luis", "Elena");
        verificar("Militantes: tamaño 5", militantes.getTamano() == 5);

        System.out.println("\n=== c) Cantidad de becados ===");
        System.out.println("  Becados: " + control.cantBecados());
        verificar("Becados = 3 (Ana, Marta, Carlos)", control.cantBecados() == 3);

        System.out.println("\n=== Casos borde ===");
        ControlEstudiante vacio = new ControlEstudiante();
        verificarNombres("Lista vacía: cumpleaños", vacio.listarPorCumpleanos("03"));
        verificar("Lista vacía: militantes vacío", vacio.listarMilitantes().isEmpty());
        verificar("Lista vacía: 0 becados", vacio.cantBecados() == 0);

        ControlEstudiante uno = new ControlEstudiante();
        uno.agregarEstudiante(new Estudiante("05011212345", "Solo", "Uno", "M", 2, true, true));
        verificarNombres("Un solo estudiante: cumpleaños mes 01", uno.listarPorCumpleanos("01"), "Solo");
        verificarOrdenMilitantes("Un solo estudiante: militantes", uno.listarMilitantes(), "Solo");
        verificar("Un solo estudiante: 1 becado (el único nodo cuenta)", uno.cantBecados() == 1);

        ControlEstudiante nadaMilitante = new ControlEstudiante();
        nadaMilitante.agregarEstudiante(new Estudiante("05011212345", "A", "X", "F", 1, false, false));
        nadaMilitante.agregarEstudiante(new Estudiante("05021212345", "B", "Y", "M", 2, false, false));
        verificar("Sin militantes: lista vacía", nadaMilitante.listarMilitantes().isEmpty());
        verificar("Sin becados: 0", nadaMilitante.cantBecados() == 0);

        ControlEstudiante inverso = new ControlEstudiante();   // peor caso de la burbuja: años 5,4,3,2,1
        for (int a = 5; a >= 1; a--) {
            inverso.agregarEstudiante(new Estudiante("0501121234" + a, "E" + a, "Z", "F", a, true, false));
        }
        verificarOrdenMilitantes("Orden inverso de entrada", inverso.listarMilitantes(), "E1", "E2", "E3", "E4", "E5");

        System.out.println("\nRESULTADO: " + pasadas + " pasadas, " + falladas + " falladas");
    }

    // ---------- utilidades de prueba ----------

    private static void mostrar(String titulo, ListaEnlazada<String> lista) {
        StringBuilder sb = new StringBuilder();
        Nodo<String> n = lista.getCabeza();
        while (n != null) {
            sb.append(n.getDato());
            if (n.getSiguiente() != null) sb.append(", ");
            n = n.getSiguiente();
        }
        System.out.println("  " + titulo + ": [" + sb + "]");
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (condicion) pasadas++; else falladas++;
        System.out.println("  [" + (condicion ? "PASA " : "FALLA") + "] " + descripcion);
    }

    private static void verificarNombres(String descripcion, ListaEnlazada<String> obtenida, String... esperados) {
        boolean ok = obtenida.getTamano() == esperados.length;
        Nodo<String> n = obtenida.getCabeza();
        for (int i = 0; ok && i < esperados.length; i++) {
            ok = n != null && n.getDato().equals(esperados[i]);
            if (n != null) n = n.getSiguiente();
        }
        verificar(descripcion, ok);
    }

    private static void verificarOrdenMilitantes(String descripcion, ListaEnlazada<Estudiante> obtenida, String... nombresEsperados) {
        boolean ok = obtenida.getTamano() == nombresEsperados.length;
        Nodo<Estudiante> n = obtenida.getCabeza();
        for (int i = 0; ok && i < nombresEsperados.length; i++) {
            ok = n != null && n.getDato().getNombre().equals(nombresEsperados[i]);
            if (n != null) n = n.getSiguiente();
        }
        verificar(descripcion, ok);
    }
}

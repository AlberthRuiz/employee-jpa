package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entiity.Departamento;
import org.example.entiity.Empleado;
import org.example.entiity.Proyecto;
import org.example.service.DepartamentoService;
import org.example.service.EmpleadoService;
import org.example.service.ProyectoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("====== INICIANDO APP JPA ======");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmpleadosPU");
        EntityManager em = emf.createEntityManager();

        try {
            DepartamentoService deptService = new DepartamentoService();
            EmpleadoService empService = new EmpleadoService();
            ProyectoService proyService = new ProyectoService();

            crearEntidadesIndividuales(em, deptService, empService, proyService);
            crearEntidadesEnLotes(em, deptService, empService, proyService);
            operacionesEspecificas(em, deptService, empService, proyService);
            consultarRelaciones(em, deptService, proyService);
            operacionesCrudAvanzadas(em, empService);
            generarReportes(em, empService, proyService);

        } catch (Exception e) {
            System.err.println("Error en la aplicación: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

        System.out.println("====== FINALIZANDO APP ======");
    }

    private static void crearEntidadesIndividuales(EntityManager em, DepartamentoService deptService,
                                                   EmpleadoService empService, ProyectoService proyService) {
        System.out.println("\n======= CREACIÓN INDIVIDUAL =======");

        Departamento it = new Departamento("Tecnología", "Piso 5", 500000.0);
        deptService.crearDepartamento(em, it);

        deptService.crearDepartamento(em, "Recursos Humanos", "Piso 2", 200000.0);

        Empleado dev1 = new Empleado("Ana García", LocalDate.of(2022, 1, 15),
                new BigDecimal("7000"), "ana.garcia@empresa.com");
        empService.crearEmpleado(em, dev1);

        empService.crearEmpleado(em, "Carlos Rodríguez", LocalDate.of(2021, 6, 10),
                new BigDecimal("8500"), "carlos.rodriguez@empresa.com");

        deptService.asignarEmpleadoADepartamento(em, dev1, 1L);

        Proyecto web = new Proyecto("Sistema Web", "Plataforma web corporativa",
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31), 150000.0);
        web.setEstado(Proyecto.EstadoProyecto.EN_PROGRESO);
        proyService.crearProyecto(em, web);

        proyService.asignarEmpleadoAProyecto(em, 1L, 1L);
    }

    private static void crearEntidadesEnLotes(EntityManager em, DepartamentoService deptService,
                                              EmpleadoService empService, ProyectoService proyService) {
        System.out.println("\n======= CREACIÓN EN LOTES =======");

        List<Departamento> departamentos = Arrays.asList(
                new Departamento("Ventas", "Piso 1", 800000.0),
                new Departamento("Marketing", "Piso 3", 300000.0)
        );
        deptService.crearDepartamentos(em, departamentos);

        List<Empleado> empleados = Arrays.asList(
                new Empleado("María López", LocalDate.of(2020, 3, 20),
                        new BigDecimal("6000"), "maria.lopez@empresa.com"),
                new Empleado("Pedro Martínez", LocalDate.of(2023, 2, 10),
                        new BigDecimal("5500"), "pedro.martinez@empresa.com"),
                new Empleado("Laura Sánchez", LocalDate.of(2022, 8, 5),
                        new BigDecimal("6200"), "laura.sanchez@empresa.com")
        );
        empService.crearEmpleados(em, empleados);

        deptService.asignarEmpleadosADepartamentos(em, empleados);

        List<Proyecto> proyectos = Arrays.asList(
                new Proyecto("App Mobile", "Aplicación móvil", LocalDate.of(2024, 6, 1),
                        LocalDate.of(2025, 3, 31), 80000.0),
                new Proyecto("Sistema IA", "Inteligencia artificial", LocalDate.of(2024, 9, 1),
                        LocalDate.of(2025, 6, 30), 200000.0)
        );
        proyectos.get(0).setEstado(Proyecto.EstadoProyecto.PLANIFICACION);
        proyectos.get(1).setEstado(Proyecto.EstadoProyecto.PLANIFICACION);

        proyService.crearProyectos(em, proyectos);
        proyService.asignarEmpleadosAProyectos(em);
    }

    private static void operacionesEspecificas(EntityManager em, DepartamentoService deptService,
                                               EmpleadoService empService, ProyectoService proyService) {
        System.out.println("\n======= OPERACIONES ESPECÍFICAS =======");

        Empleado empleado = empService.buscarEmpleadoPorId(em, 1L);
        empService.actualizarSalario(em, 1L, new BigDecimal("7500"));
        proyService.asignarEmpleadoAProyecto(em, 3L, 2L);
        proyService.removerEmpleadoDeProyecto(em, 1L, 1L);
    }

    private static void consultarRelaciones(EntityManager em, DepartamentoService deptService,
                                            ProyectoService proyService) {
        System.out.println("\n======= CONSULTANDO RELACIONES =======");

        deptService.consultarDepartamentosConEmpleados(em);
        proyService.consultarProyectosConEmpleados(em);
        proyService.consultarEmpleadosConProyectos(em);
    }

    private static void operacionesCrudAvanzadas(EntityManager em, EmpleadoService empService) {
        System.out.println("\n======= OPERACIONES CRUD AVANZADAS =======");

        empService.leerEmpleados(em);
        empService.actualizarEmpleado(em, 1L, "Ana García Actualizada",
                new BigDecimal("8000"), "ana.nueva@empresa.com");
        empService.buscarEmpleadosPorDepartamento(em, "Tecnología");
        empService.buscarEmpleadosPorRangoSalario(em, new BigDecimal("6000"), new BigDecimal("8000"));
        empService.consultasAvanzadas(em, new BigDecimal("6000"));
    }

    private static void generarReportes(EntityManager em, EmpleadoService empService,
                                        ProyectoService proyService) {
        System.out.println("\n======= REPORTES Y ESTADÍSTICAS =======");

        empService.mostrarEstadisticas(em);
        proyService.generarReporteProyectos(em);
    }
}
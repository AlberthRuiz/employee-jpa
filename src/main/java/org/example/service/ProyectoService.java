package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.entiity.Empleado;
import org.example.entiity.Proyecto;

import java.time.LocalDate;
import java.util.List;

public class ProyectoService {

    public void crearProyecto(EntityManager em, Proyecto proyecto) {
        System.out.println("\n--- CREANDO PROYECTO ---");

        em.getTransaction().begin();

        try {
            em.persist(proyecto);
            em.getTransaction().commit();

            System.out.println("Proyecto creado: " + proyecto);

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al crear proyecto: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void crearProyectos(EntityManager em, List<Proyecto> proyectos) {
        System.out.println("\n--- CREANDO PROYECTOS ---");

        em.getTransaction().begin();

        try {
            for (Proyecto proyecto : proyectos) {
                em.persist(proyecto);
                System.out.println("Proyecto creado: " + proyecto);
            }

            em.getTransaction().commit();
            System.out.println("Todos los proyectos creados exitosamente (" + proyectos.size() + " proyectos)");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al crear proyectos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void crearProyecto(EntityManager em, String nombre, String descripcion,
                              LocalDate fechaInicio, LocalDate fechaFin, Double presupuesto,
                              Proyecto.EstadoProyecto estado) {
        Proyecto proyecto = new Proyecto(nombre, descripcion, fechaInicio, fechaFin, presupuesto);
        proyecto.setEstado(estado);
        crearProyecto(em, proyecto);
    }

    public void asignarEmpleadoAProyecto(EntityManager em, Long empleadoId, Long proyectoId) {
        System.out.println("\n--- ASIGNANDO EMPLEADO A PROYECTO ---");

        em.getTransaction().begin();

        try {
            Empleado empleado = em.find(Empleado.class, empleadoId);
            Proyecto proyecto = em.find(Proyecto.class, proyectoId);

            if (empleado != null && proyecto != null) {
                proyecto.agregarEmpleado(empleado);

                em.getTransaction().commit();
                System.out.println("Empleado " + empleado.getNombre() + " asignado a proyecto " + proyecto.getNombre());
            } else {
                System.out.println("Empleado o proyecto no encontrado");
                em.getTransaction().rollback();
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al asignar empleado a proyecto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void asignarEmpleadosAProyectos(EntityManager em) {
        System.out.println("\n--- ASIGNANDO EMPLEADOS A PROYECTOS (MANY TO MANY) ---");

        em.getTransaction().begin();

        try {
            List<Empleado> empleados = em.createQuery("SELECT e FROM Empleado e", Empleado.class)
                    .getResultList();
            List<Proyecto> proyectos = em.createQuery("SELECT p FROM Proyecto p", Proyecto.class)
                    .getResultList();

            if (!empleados.isEmpty() && !proyectos.isEmpty()) {
                System.out.println("Asignando empleados a proyectos usando patrón ManyToMany...");

                for (int i = 0; i < empleados.size(); i++) {
                    Empleado emp = empleados.get(i);

                    switch (i % 3) {
                        case 0:
                            if (proyectos.size() >= 2) {
                                proyectos.get(0).agregarEmpleado(emp);
                                proyectos.get(1).agregarEmpleado(emp);
                                System.out.println("   - " + emp.getNombre() + " asignado a " +
                                        proyectos.get(0).getNombre() + " y " + proyectos.get(1).getNombre());
                            }
                            break;
                        case 1:
                            if (proyectos.size() >= 3) {
                                proyectos.get(0).agregarEmpleado(emp);
                                proyectos.get(2).agregarEmpleado(emp);
                                System.out.println("   - " + emp.getNombre() + " asignado a " +
                                        proyectos.get(0).getNombre() + " y " + proyectos.get(2).getNombre());
                            }
                            break;
                        case 2:
                            if (proyectos.size() >= 3) {
                                proyectos.get(1).agregarEmpleado(emp);
                                proyectos.get(2).agregarEmpleado(emp);
                                System.out.println("   - " + emp.getNombre() + " asignado a " +
                                        proyectos.get(1).getNombre() + " y " + proyectos.get(2).getNombre());
                            }
                            break;
                    }
                }
            }

            em.getTransaction().commit();
            System.out.println("Asignaciones ManyToMany completadas exitosamente");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error en asignaciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removerEmpleadoDeProyecto(EntityManager em, Long empleadoId, Long proyectoId) {
        System.out.println("\n--- REMOVIENDO EMPLEADO DE PROYECTO ---");

        em.getTransaction().begin();

        try {
            Empleado empleado = em.find(Empleado.class, empleadoId);
            Proyecto proyecto = em.find(Proyecto.class, proyectoId);

            if (empleado != null && proyecto != null) {
                proyecto.removerEmpleado(empleado);

                em.getTransaction().commit();
                System.out.println("Empleado " + empleado.getNombre() + " removido del proyecto " + proyecto.getNombre());
            } else {
                System.out.println("Empleado o proyecto no encontrado");
                em.getTransaction().rollback();
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al remover empleado de proyecto: " + e.getMessage());
        }
    }

    public void consultarProyectosConEmpleados(EntityManager em) {
        System.out.println("\n--- CONSULTANDO PROYECTOS CON EMPLEADOS ---");

        TypedQuery<Proyecto> query = em.createQuery(
                "SELECT DISTINCT p FROM Proyecto p LEFT JOIN FETCH p.empleados ORDER BY p.nombre",
                Proyecto.class
        );

        List<Proyecto> proyectos = query.getResultList();

        System.out.println("Proyectos y sus equipos:");
        for (Proyecto proyecto : proyectos) {
            System.out.println("\nProyecto: " + proyecto.getNombre() + " (" + proyecto.getEstado() + ")");
            System.out.println("   Descripcion: " + proyecto.getDescripcion());
            System.out.println("   Fechas: " + proyecto.getFechaInicio() + " - " + proyecto.getFechaFin());
            System.out.println("   Presupuesto: $" + proyecto.getPresupuesto());
            System.out.println("   Equipo (" + proyecto.getEmpleados().size() + " personas):");

            for (Empleado emp : proyecto.getEmpleados()) {
                System.out.println("      - " + emp.getNombre() +
                        " (" + emp.getDepartamento().getNombre() + ") - $" + emp.getSalario());
            }
        }
    }

    public void consultarEmpleadosConProyectos(EntityManager em) {
        System.out.println("\n--- CONSULTANDO EMPLEADOS CON SUS PROYECTOS ---");

        TypedQuery<Empleado> query = em.createQuery(
                "SELECT DISTINCT e FROM Empleado e LEFT JOIN FETCH e.proyectos ORDER BY e.nombre",
                Empleado.class
        );

        List<Empleado> empleados = query.getResultList();

        System.out.println("Empleados y sus asignaciones:");
        for (Empleado emp : empleados) {
            System.out.println("\nEmpleado: " + emp.getNombre() + " (" + emp.getDepartamento().getNombre() + ")");
            System.out.println("   Salario: $" + emp.getSalario());
            System.out.println("   Email: " + emp.getEmail());
            System.out.println("   Proyectos asignados (" + emp.getProyectos().size() + "):");

            if (emp.getProyectos().isEmpty()) {
                System.out.println("      - Sin proyectos asignados");
            } else {
                for (Proyecto proyecto : emp.getProyectos()) {
                    System.out.println("      - " + proyecto.getNombre() + " (" + proyecto.getEstado() + ")");
                }
            }
        }
    }

    public void generarReporteProyectos(EntityManager em) {
        System.out.println("\n--- REPORTE AVANZADO DE PROYECTOS ---");

        TypedQuery<Object[]> queryEstado = em.createQuery(
                "SELECT p.estado, COUNT(p) FROM Proyecto p GROUP BY p.estado",
                Object[].class
        );

        List<Object[]> resultadosEstado = queryEstado.getResultList();
        System.out.println("\nProyectos por estado:");
        for (Object[] resultado : resultadosEstado) {
            System.out.println("   - " + resultado[0] + ": " + resultado[1] + " proyectos");
        }

        TypedQuery<Object[]> queryOcupados = em.createQuery(
                "SELECT e.nombre, COUNT(p) as numProyectos FROM Empleado e LEFT JOIN e.proyectos p " +
                        "GROUP BY e.nombre ORDER BY COUNT(p) DESC",
                Object[].class
        );

        List<Object[]> empleadosOcupados = queryOcupados.getResultList();
        System.out.println("\nEmpleados más ocupados:");
        for (int i = 0; i < Math.min(3, empleadosOcupados.size()); i++) {
            Object[] resultado = empleadosOcupados.get(i);
            System.out.println("   " + (i+1) + ". " + resultado[0] + ": " + resultado[1] + " proyectos");
        }

        TypedQuery<Object[]> queryPresupuesto = em.createQuery(
                "SELECT d.nombre, SUM(p.presupuesto) FROM Departamento d " +
                        "JOIN d.empleados e JOIN e.proyectos p " +
                        "GROUP BY d.nombre ORDER BY SUM(p.presupuesto) DESC",
                Object[].class
        );

        List<Object[]> presupuestoDept = queryPresupuesto.getResultList();
        System.out.println("\nPresupuesto de proyectos por departamento:");
        for (Object[] resultado : presupuestoDept) {
            System.out.println("   - " + resultado[0] + ": $" + resultado[1]);
        }
    }
}
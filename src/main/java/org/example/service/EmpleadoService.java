package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.entiity.Empleado;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public class EmpleadoService {

    public void crearEmpleado(EntityManager em, Empleado empleado) {
        System.out.println("\n--- CREANDO EMPLEADO ---");

        em.getTransaction().begin();

        try {
            em.persist(empleado);
            em.getTransaction().commit();

            System.out.println("Empleado creado: " + empleado);

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al crear empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void crearEmpleados(EntityManager em, List<Empleado> empleados) {
        System.out.println("\n--- CREANDO EMPLEADOS ---");

        em.getTransaction().begin();

        try {
            for (Empleado emp : empleados) {
                em.persist(emp);
                System.out.println("Empleado creado: " + emp);
            }

            em.getTransaction().commit();
            System.out.println("Todos los empleados creados exitosamente (" + empleados.size() + " empleados)");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al crear empleados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void crearEmpleado(EntityManager em, String nombre, LocalDate fechaIngreso,
                              BigDecimal salario, String email) {
        Empleado empleado = new Empleado(nombre, fechaIngreso, salario, email);
        crearEmpleado(em, empleado);
    }

    public void leerEmpleados(EntityManager em) {
        System.out.println("\n--- LEYENDO EMPLEADOS ---");

        TypedQuery<Empleado> query = em.createQuery(
                "SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento ORDER BY e.nombre",
                Empleado.class
        );

        List<Empleado> empleados = query.getResultList();

        System.out.println("Lista de empleados:");
        for (Empleado emp : empleados) {
            System.out.println("   - " + emp);
        }

        if (!empleados.isEmpty()) {
            Empleado empleadoPorId = em.find(Empleado.class, empleados.get(0).getId());
            if (empleadoPorId != null) {
                System.out.println("Empleado encontrado por ID " + empleadoPorId.getId() + ": " + empleadoPorId);
            }
        }
    }

    public Empleado buscarEmpleadoPorId(EntityManager em, Long empleadoId) {
        System.out.println("\n--- BUSCANDO EMPLEADO POR ID ---");

        TypedQuery<Empleado> query = em.createQuery(
                "SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento LEFT JOIN FETCH e.proyectos WHERE e.id = :id",
                Empleado.class
        );
        query.setParameter("id", empleadoId);

        List<Empleado> empleados = query.getResultList();

        if (!empleados.isEmpty()) {
            Empleado empleado = empleados.get(0);
            System.out.println("Empleado encontrado: " + empleado);
            return empleado;
        } else {
            System.out.println("Empleado con ID " + empleadoId + " no encontrado");
            return null;
        }
    }

    public void actualizarEmpleado(EntityManager em, Long empleadoId, String nuevoNombre,
                                   BigDecimal nuevoSalario, String nuevoEmail) {
        System.out.println("\n--- ACTUALIZANDO EMPLEADO ---");

        em.getTransaction().begin();

        try {
            Empleado empleado = em.find(Empleado.class, empleadoId);

            if (empleado != null) {
                System.out.println("Empleado antes: " + empleado);

                // Actualizar solo los campos que no son null
                if (nuevoNombre != null) {
                    empleado.setNombre(nuevoNombre);
                }
                if (nuevoSalario != null) {
                    empleado.setSalario(nuevoSalario);
                }
                if (nuevoEmail != null) {
                    empleado.setEmail(nuevoEmail);
                }

                em.getTransaction().commit();

                System.out.println("Empleado después: " + empleado);
            } else {
                System.out.println("Empleado con ID " + empleadoId + " no encontrado");
                em.getTransaction().rollback();
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al actualizar empleado: " + e.getMessage());
        }
    }

    public void actualizarSalario(EntityManager em, Long empleadoId, BigDecimal nuevoSalario) {
        actualizarEmpleado(em, empleadoId, null, nuevoSalario, null);
    }

    public void eliminarEmpleado(EntityManager em, Long empleadoId) {
        System.out.println("\n--- ELIMINANDO EMPLEADO ---");

        em.getTransaction().begin();

        try {
            TypedQuery<Empleado> query = em.createQuery(
                    "SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento LEFT JOIN FETCH e.proyectos WHERE e.id = :id",
                    Empleado.class
            );
            query.setParameter("id", empleadoId);

            List<Empleado> empleados = query.getResultList();

            if (!empleados.isEmpty()) {
                Empleado empleado = empleados.get(0);
                System.out.println("Eliminando: " + empleado);


                if (empleado.getDepartamento() != null) {
                    empleado.getDepartamento().removerEmpleado(empleado);
                }


                empleado.getProyectos().forEach(proyecto -> proyecto.removerEmpleado(empleado));

                em.remove(empleado);
                em.getTransaction().commit();
                System.out.println("Empleado eliminado exitosamente");
            } else {
                System.out.println("Empleado con ID " + empleadoId + " no encontrado");
                em.getTransaction().rollback();
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al eliminar empleado: " + e.getMessage());
        }
    }


    public void consultasAvanzadas(EntityManager em, BigDecimal salarioMinimo) {
        System.out.println("\n--- CONSULTAS AVANZADAS ---");

        TypedQuery<Empleado> queryPorSalario = em.createQuery(
                "SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento WHERE e.salario > :salarioMinimo ORDER BY e.salario DESC",
                Empleado.class
        );
        queryPorSalario.setParameter("salarioMinimo", salarioMinimo);

        List<Empleado> empleadosConBuenSalario = queryPorSalario.getResultList();

        System.out.println("Empleados con salario mayor a $" + salarioMinimo + ":");
        for (Empleado emp : empleadosConBuenSalario) {
            String departamento = emp.getDepartamento() != null ? emp.getDepartamento().getNombre() : "Sin asignar";
            System.out.println("   - " + emp.getNombre() + ": $" + emp.getSalario() + " (" + departamento + ")");
        }

        // Estadísticas generales
        mostrarEstadisticas(em);
    }

    /**
     * Mostrar estadísticas de empleados
     */
    public void mostrarEstadisticas(EntityManager em) {
        System.out.println("\n--- ESTADÍSTICAS DE EMPLEADOS ---");

        // Total de empleados
        TypedQuery<Long> queryConteo = em.createQuery(
                "SELECT COUNT(e) FROM Empleado e",
                Long.class
        );
        Long totalEmpleados = queryConteo.getSingleResult();

        // Salario promedio
        TypedQuery<Double> queryPromedio = em.createQuery(
                "SELECT AVG(e.salario) FROM Empleado e",
                Double.class
        );
        Double salarioPromedio = queryPromedio.getSingleResult();

        // Salario máximo y mínimo
        TypedQuery<BigDecimal> queryMax = em.createQuery(
                "SELECT MAX(e.salario) FROM Empleado e",
                BigDecimal.class
        );
        BigDecimal salarioMaximo = queryMax.getSingleResult();

        TypedQuery<BigDecimal> queryMin = em.createQuery(
                "SELECT MIN(e.salario) FROM Empleado e",
                BigDecimal.class
        );
        BigDecimal salarioMinimo = queryMin.getSingleResult();

        System.out.println("Total de empleados: " + totalEmpleados);
        System.out.println("Salario promedio: $" + String.format("%.2f", salarioPromedio));
        System.out.println("Salario máximo: $" + salarioMaximo);
        System.out.println("Salario mínimo: $" + salarioMinimo);
    }

    /**
     * Buscar empleados por departamento
     */
    public void buscarEmpleadosPorDepartamento(EntityManager em, String nombreDepartamento) {
        System.out.println("\n--- EMPLEADOS POR DEPARTAMENTO ---");

        TypedQuery<Empleado> query = em.createQuery(
                "SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento d WHERE d.nombre = :nombreDept ORDER BY e.nombre",
                Empleado.class
        );
        query.setParameter("nombreDept", nombreDepartamento);

        List<Empleado> empleados = query.getResultList();

        System.out.println("Empleados en departamento '" + nombreDepartamento + "':");
        if (empleados.isEmpty()) {
            System.out.println("   - No hay empleados en este departamento");
        } else {
            for (Empleado emp : empleados) {
                System.out.println("   - " + emp.getNombre() + " ($" + emp.getSalario() + ") - " + emp.getEmail());
            }
        }
    }

    public void buscarEmpleadosPorRangoSalario(EntityManager em, BigDecimal salarioMin, BigDecimal salarioMax) {
        System.out.println("\n--- EMPLEADOS POR RANGO DE SALARIO ---");

        TypedQuery<Empleado> query = em.createQuery(
                "SELECT e FROM Empleado e LEFT JOIN FETCH e.departamento WHERE e.salario BETWEEN :min AND :max ORDER BY e.salario DESC",
                Empleado.class
        );
        query.setParameter("min", salarioMin);
        query.setParameter("max", salarioMax);

        List<Empleado> empleados = query.getResultList();

        System.out.println("Empleados con salario entre $" + salarioMin + " y $" + salarioMax + ":");
        for (Empleado emp : empleados) {
            String departamento = emp.getDepartamento() != null ? emp.getDepartamento().getNombre() : "Sin asignar";
            System.out.println("   - " + emp.getNombre() + ": $" + emp.getSalario() + " (" + departamento + ")");
        }
    }
}
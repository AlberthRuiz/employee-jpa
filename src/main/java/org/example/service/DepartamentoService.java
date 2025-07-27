package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.entiity.Departamento;
import org.example.entiity.Empleado;

import java.util.Arrays;
import java.util.List;

public class DepartamentoService {

    public void crearDepartamento(EntityManager em, Departamento departamento) {
        System.out.println("\n--- CREANDO DEPARTAMENTO ---");

        em.getTransaction().begin();

        try {
            em.persist(departamento);
            em.getTransaction().commit();

            System.out.println("Departamento creado: " + departamento);

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al crear departamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void crearDepartamentos(EntityManager em, List<Departamento> departamentos) {
        System.out.println("\n--- CREANDO DEPARTAMENTOS ---");

        em.getTransaction().begin();

        try {
            for (Departamento dept : departamentos) {
                em.persist(dept);
                System.out.println("Departamento creado: " + dept);
            }

            em.getTransaction().commit();
            System.out.println("Todos los departamentos creados exitosamente (" + departamentos.size() + " departamentos)");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al crear departamentos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void crearDepartamento(EntityManager em, String nombre, String ubicacion, Double presupuesto) {
        Departamento departamento = new Departamento(nombre, ubicacion, presupuesto);
        crearDepartamento(em, departamento);
    }

    public void asignarEmpleadoADepartamento(EntityManager em, Empleado empleado, Long departamentoId) {
        System.out.println("\n--- ASIGNANDO EMPLEADO A DEPARTAMENTO ---");

        em.getTransaction().begin();

        try {
            Departamento departamento = em.find(Departamento.class, departamentoId);

            if (departamento != null) {
                departamento.agregarEmpleado(empleado);
                em.persist(empleado);

                em.getTransaction().commit();
                System.out.println("Empleado " + empleado.getNombre() + " asignado a " + departamento.getNombre());
            } else {
                System.out.println("Departamento no encontrado con ID: " + departamentoId);
                em.getTransaction().rollback();
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al asignar empleado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void asignarEmpleadosADepartamentos(EntityManager em, List<Empleado> empleados) {
        System.out.println("\n--- ASIGNANDO EMPLEADOS A DEPARTAMENTOS ---");

        em.getTransaction().begin();

        try {
            List<Departamento> departamentos = em.createQuery(
                    "SELECT d FROM Departamento d ORDER BY d.id",
                    Departamento.class
            ).getResultList();

            if (departamentos.isEmpty()) {
                System.out.println("No hay departamentos disponibles");
                em.getTransaction().rollback();
                return;
            }

            for (int i = 0; i < empleados.size(); i++) {
                Empleado emp = empleados.get(i);
                Departamento dept = departamentos.get(i % departamentos.size());

                dept.agregarEmpleado(emp);
                em.persist(emp);

                System.out.println("Empleado " + emp.getNombre() + " asignado a " + dept.getNombre());
            }

            em.getTransaction().commit();
            System.out.println("Todos los empleados asignados exitosamente");

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("Error al asignar empleados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void consultarDepartamentosConEmpleados(EntityManager em) {
        System.out.println("\n--- CONSULTANDO DEPARTAMENTOS CON EMPLEADOS ---");

        TypedQuery<Departamento> query = em.createQuery(
                "SELECT d FROM Departamento d LEFT JOIN FETCH d.empleados ORDER BY d.nombre",
                Departamento.class
        );

        List<Departamento> departamentos = query.getResultList();

        System.out.println("Departamentos y sus empleados:");
        for (Departamento dept : departamentos) {
            System.out.println("\nDepartamento: " + dept.getNombre() + " (" + dept.getUbicacion() + ")");
            System.out.println("   Presupuesto: $" + dept.getPresupuesto());
            System.out.println("   Empleados (" + dept.getEmpleados().size() + "):");

            for (Empleado emp : dept.getEmpleados()) {
                System.out.println("      - " + emp.getNombre() + " ($" + emp.getSalario() + ") - " + emp.getEmail());
            }
        }
    }
}
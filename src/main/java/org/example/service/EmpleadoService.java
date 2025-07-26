package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.entiity.Empleado;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmpleadoService {
    public void crearEmpleado(EntityManager em, Empleado empleado ){
        em.getTransaction().begin();
        try{
            em.persist(empleado);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Ocurrio un error ->" + e.getMessage());
        }
    }

}

package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.entiity.Departamento;
import org.example.entiity.Empleado;


import java.math.BigDecimal;
import java.time.LocalDate;

public class DepartamentoService {

    public void crearDepartamento(EntityManager em, Departamento departamento){
        em.getTransaction().begin();
        try{
            em.persist(departamento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Ocurrio un error ->" + e.getMessage());
        }
    }

    public void crearDepartamentoConEmpleados(EntityManager em, Departamento departamento){
        em.getTransaction().begin();
        try {
            em.persist(departamento);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Ocurrio un error ->" + e.getMessage());
        }
    }

    public void asignarEmpleadoaDepartamento(EntityManager em, Empleado empleado, Long departamento_id){
        em.getTransaction().begin();
        try {
            Departamento departamento = em.find(Departamento.class, departamento_id);
            if( departamento != null ){
                departamento.agregarEmpleados(empleado);
                em.persist(empleado);;
                em.getTransaction().commit();

            }else{
                em.getTransaction().rollback();
                System.out.println("Departamento no existe");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Ocurrio un error ->" + e.getMessage());
        }
    }
}

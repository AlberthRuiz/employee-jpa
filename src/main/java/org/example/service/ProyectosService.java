package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.entiity.Empleado;
import org.example.entiity.Proyecto;

import java.time.LocalDate;

public class ProyectosService {
    public void crearProyecto(EntityManager em, Proyecto proyecto){
        em.getTransaction().begin();
        try{
            em.persist(proyecto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error en ejecucion ->" + e.getMessage());
        }
    }

    public void asignarEmpleadoAPRoyecto(EntityManager em, Long empleado_id, Long proyecto_id){
        em.getTransaction().begin();

        try{
            Proyecto proyecto = em.find(Proyecto.class, proyecto_id);
            Empleado empleado = em.find(Empleado.class, empleado_id);
            if(proyecto != null  && empleado!=null){
                proyecto.agregarEmpelado(empleado);
                em.getTransaction().commit();
            }else{
                em.getTransaction().rollback();
                System.out.println("No se encontro proyecto");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error en ejecucion ->" + e.getMessage());
        }

    }
}

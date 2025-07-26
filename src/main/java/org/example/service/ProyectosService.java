package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.entiity.Proyecto;

import java.time.LocalDate;

public class ProyectosService {
    public void crearProyecto(EntityManager em){
        em.getTransaction().begin();
        try{
            Proyecto proyecto = new Proyecto();
            proyecto.setNombre("Camelot");
            proyecto.setDescripcion("Despliegue de pods en EKS para migracion de arq monolito");
            proyecto.setFechaInicio(LocalDate.of(2025,1, 15));
            proyecto.setFechaFin(LocalDate.of(2025,4, 30));
            proyecto.setPresupuesto(125000d);
            proyecto.setEstado(Proyecto.EstadoProyecto.EN_PROGRESO);
            em.persist(proyecto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error en ejecucion ->" + e.getMessage());
        }
    }
}

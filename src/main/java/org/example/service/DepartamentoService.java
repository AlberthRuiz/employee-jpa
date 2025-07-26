package org.example.service;

import jakarta.persistence.EntityManager;
import org.example.entiity.Departamento;
import org.example.entiity.Empleado;


import java.math.BigDecimal;
import java.time.LocalDate;

public class DepartamentoService {

    public void crearDepartamento(EntityManager em){
        em.getTransaction().begin();
        try{
            Departamento departamento = new Departamento();
            departamento.setNombre("Oficina Control Interno");
            departamento.setUbicacion("Piso 2");
            departamento.setPresuepuesto(1250000d);
            em.persist(departamento);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Ocurrio un error ->" + e.getMessage());
        }
    }

    public void crearDepartamentoConEmpleados(EntityManager em){
        em.getTransaction().begin();
        try {
            Departamento departamentoTec = new Departamento();
            departamentoTec.setNombre("Tecnologia");
            departamentoTec.setUbicacion("Piso 7");
            departamentoTec.setPresuepuesto(3547007d);

            Departamento departamentoRRHH = new Departamento();
            departamentoRRHH.setNombre("Recurso Humanos");
            departamentoRRHH.setUbicacion("Piso 8");
            departamentoRRHH.setPresuepuesto(2580001d);

            Empleado dev1 = new Empleado();

            dev1.setNombre("Maria Flores");
            dev1.setFechaIngreso(LocalDate.of(2022,1,15));
            dev1.setSalario(7850.0);

            Empleado dev2 = new Empleado();

            dev2.setNombre("Renato Cabrera");
            dev2.setFechaIngreso(LocalDate.of(2021,7,1));
            dev2.setSalario(6850.0);

            departamentoTec.agregarEmpleados(dev1);
            departamentoTec.agregarEmpleados(dev2);
            em.persist(departamentoTec);
            em.persist(departamentoRRHH);
            em.persist(dev1);
            em.persist(dev2);

            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Ocurrio un error ->" + e.getMessage());
        }
    }
}

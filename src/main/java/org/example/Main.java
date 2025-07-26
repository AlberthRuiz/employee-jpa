package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.service.DepartamentoService;
import org.example.service.EmpleadoService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("====== INICIANDO APP ======");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmpleadosPU");
        EntityManager em = emf.createEntityManager();

        try{
           // EmpleadoService empleadoService = new EmpleadoService();
            //empleadoService.crearEmpleado(em);
            DepartamentoService departamentoService = new DepartamentoService();
            //departamentoService.crearDepartamento(em);
            departamentoService.crearDepartamentoConEmpleados(em);



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            em.close();
            emf.close();
        }
        System.out.println("====== FINALIZANDO APP ======");
    }
}
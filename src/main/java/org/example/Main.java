package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entiity.Departamento;
import org.example.entiity.Empleado;
import org.example.entiity.Proyecto;
import org.example.service.DepartamentoService;
import org.example.service.EmpleadoService;
import org.example.service.ProyectosService;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("====== INICIANDO APP ======");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmpleadosPU");
        EntityManager em = emf.createEntityManager();
        EmpleadoService empleadoService = new EmpleadoService();
        DepartamentoService departamentoService = new DepartamentoService();
        ProyectosService proyectosService = new ProyectosService();
        try{
            crearEntidadesIndividuales(em,  departamentoService,empleadoService, proyectosService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            em.close();
            emf.close();
        }
        System.out.println("====== FINALIZANDO APP ======");
    }
    public static void crearEntidadesIndividuales(EntityManager em, DepartamentoService departamentoService, EmpleadoService empleadoService,
                                                  ProyectosService proyectosService){
        System.out.println("CREANDO ENTIDADES INDIVIDUALES");
        Departamento it = new Departamento();
        it.setNombre("Tecnologia");
        it.setUbicacion("Piso 5");
        it.setPresuepuesto(500000.0);
        departamentoService.crearDepartamento(em,it);

        Departamento rrhh = new Departamento();
        rrhh.setNombre("Recurso Humanos");
        rrhh.setUbicacion("Piso 2");
        rrhh.setPresuepuesto(15000747d);
        departamentoService.crearDepartamento(em,rrhh);

        Empleado  dev1= new Empleado();
        dev1.setNombre("Ana Garcia");
        dev1.setFechaIngreso(LocalDate.of(2022,1,25));
        dev1.setSalario(4500d);

        Empleado  dev2= new Empleado();
        dev2.setNombre("Mauro Villegas");
        dev2.setFechaIngreso(LocalDate.of(2018,7,24));
        dev2.setSalario(7500d);

        departamentoService.asignarEmpleadoaDepartamento(em,dev1, 1L);
        departamentoService.asignarEmpleadoaDepartamento(em,dev2, 1L);

        Proyecto web = new Proyecto();
        web.setNombre("Aplicacion web bancaria");
        web.setDescripcion("Aplicacion web que permitira gestionar los prestamos y/o evaluaciones de los clientes");
        web.setFechaInicio(LocalDate.of(2025,4,25));
        web.setFechaFin(LocalDate.of(2025,7,15));
        web.setPresupuesto(125000d);
        web.setEstado(Proyecto.EstadoProyecto.EN_PROGRESO);
        proyectosService.crearProyecto(em, web);
        proyectosService.asignarEmpleadoAPRoyecto(em, 1L, 1L);



    }
}
package org.example.entiity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre", length = 150, nullable = false)
    private String nombre;
    @Column(name = "descripcion", length = 200)
    private String descripcion;
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    @Column(name = "presupuesto")
    private Double presupuesto;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoProyecto estado;

    @ManyToMany(mappedBy = "proyectos")
    private List<Empleado> empleados = new ArrayList<Empleado>();

    public enum EstadoProyecto{
        PLANIFICACION, EN_PROGRESO, COMPLETADO, CANCELADO
    }

    public void agregarEmpelado(Empleado emp){
        this.empleados.add(emp);
        emp.getProyectos().add(this);
    }

    public void removerEmpleado(Empleado emp){
        this.empleados.remove(emp);
        emp.getProyectos().remove(emp);
    }

    public Proyecto(){

    }

    public Proyecto(Long id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, Double presupuesto, EstadoProyecto estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.estado = estado;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public EstadoProyecto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProyecto estado) {
        this.estado = estado;
    }
}

package org.example.entiity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entidad Proyecto que demuestra relación ManyToMany con Empleado
 */
@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 500)
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
    private List<Empleado> empleados = new ArrayList<>();


    public enum EstadoProyecto {
        PLANIFICACION, EN_PROGRESO, COMPLETADO, CANCELADO
    }

    public Proyecto() {
    }

    public Proyecto(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, Double presupuesto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.estado = EstadoProyecto.PLANIFICACION;
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

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }


    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        empleado.getProyectos().add(this);
    }

    public void removerEmpleado(Empleado empleado) {
        empleados.remove(empleado);
        empleado.getProyectos().remove(this);
    }

    public boolean estaActivo() {
        return estado == EstadoProyecto.EN_PROGRESO || estado == EstadoProyecto.PLANIFICACION;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", numEmpleados=" + empleados.size() +
                '}';
    }
}
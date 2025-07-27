package org.example.entiity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "ubicacion", length = 200)
    private String ubicacion;

    @Column(name = "presupuesto")
    private Double presupuesto;

    /**
     * Relación OneToMany: Un departamento tiene muchos empleados
     * mappedBy = "departamento": El lado propietario está en Empleado.departamento
     * cascade = CascadeType.ALL: Las operaciones se propagan a los empleados
     * fetch = FetchType.LAZY: Los empleados se cargan solo cuando se necesitan
     */
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Empleado> empleados = new ArrayList<>();

    public Departamento() {
    }

    public Departamento(String nombre, String ubicacion, Double presupuesto) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.presupuesto = presupuesto;
    }

    public void agregarEmpleado(Empleado empleado) {
        empleados.add(empleado);
        empleado.setDepartamento(this);
    }

    public void removerEmpleado(Empleado empleado) {
        empleados.remove(empleado);
        empleado.setDepartamento(null);
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", presupuesto=" + presupuesto +
                ", numEmpleados=" + empleados.size() +
                '}';
    }
}
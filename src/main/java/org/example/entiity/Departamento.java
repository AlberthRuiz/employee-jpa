package org.example.entiity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="departamento")
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;
    @Column (name = "ubicacion", length = 150, nullable = false)
    private String ubicacion;
    @Column (name = "presupuesto")
    private Double presuepuesto;

    // Empleado
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Empleado> empleados = new ArrayList<>();

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

    public Double getPresuepuesto() {
        return presuepuesto;
    }

    public void setPresuepuesto(Double presuepuesto) {
        this.presuepuesto = presuepuesto;
    }

    public Departamento() {
    }

    public Departamento(Long id, String nombre, String ubicacion, Double presuepuesto) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.presuepuesto = presuepuesto;
    }
    public void agregarEmpleados(Empleado empleado){
        this.empleados.add(empleado);
        empleado.setDepartamento(this);
    }
    public void removerEmpleado(Empleado empleado){
        empleados.remove(empleado);
        empleado.setDepartamento(null);
    }

}

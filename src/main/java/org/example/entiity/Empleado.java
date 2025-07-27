package org.example.entiity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "salario", precision = 10, scale = 2)
    private BigDecimal salario;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    /**
     * Relación ManyToOne: Muchos empleados pertenecen a un departamento
     * @JoinColumn: Especifica la columna de clave foránea en la tabla empleados
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    /**
     * Relación ManyToMany: Empleados pueden trabajar en múltiples proyectos
     * (Por ahora solo declaramos, crearemos Proyecto en el siguiente paso)
     */
    @ManyToMany
    @JoinTable(
            name = "empleado_proyecto",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private List<Proyecto> proyectos = new ArrayList<>();



    public Empleado() {
    }

    public Empleado(String nombre, LocalDate fechaIngreso, BigDecimal salario) {
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.salario = salario;
    }

    // Nuevo constructor con email
    public Empleado(String nombre, LocalDate fechaIngreso, BigDecimal salario, String email) {
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.salario = salario;
        this.email = email;
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

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", salario=" + salario +
                ", email='" + email + '\'' +
                ", departamento=" + (departamento != null ? departamento.getNombre() : "Sin asignar") +
                '}';
    }
}
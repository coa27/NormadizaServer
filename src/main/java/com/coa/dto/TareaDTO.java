package com.coa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TareaDTO {

    private Long id;

    @NotBlank
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private Boolean finalizado;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate updatedAt;

    private Long idTablero;

    public TareaDTO(Long id, String nombre, String descripcion, Boolean finalizado, LocalDate fechaInicio, LocalDate fechaFin, LocalDate createAt, LocalDate updatedAt, Long idTablero) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.finalizado = finalizado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.idTablero = idTablero;
    }

    public Boolean getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
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

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getIdTablero() {
        return idTablero;
    }

    public void setIdTablero(Long idTablero) {
        this.idTablero = idTablero;
    }
}

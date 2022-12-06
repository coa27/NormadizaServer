package com.coa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class TableroDTO {

    private Long idTablero;

    private String nombre;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate updatedAt;

    private Long idUsuario;

    private Integer cantidadTotalTareas;

    private Integer cantidadTotalTareasCompletas;

    public TableroDTO(Long idTablero, String nombre, LocalDate createAt, LocalDate updatedAt, Long idUsuario, Integer cantidadTotalTareas, Integer cantidadTotalTareasCompletas) {
        this.idTablero = idTablero;
        this.nombre = nombre;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.idUsuario = idUsuario;
        this.cantidadTotalTareas = cantidadTotalTareas;
        this.cantidadTotalTareasCompletas = cantidadTotalTareasCompletas;
    }

    public Integer getCantidadTotalTareas() {
        return cantidadTotalTareas;
    }

    public void setCantidadTotalTareas(Integer cantidadTotalTareas) {
        this.cantidadTotalTareas = cantidadTotalTareas;
    }

    public Integer getCantidadTotalTareasCompletas() {
        return cantidadTotalTareasCompletas;
    }

    public void setCantidadTotalTareasCompletas(Integer cantidadTotalTareasCompletas) {
        this.cantidadTotalTareasCompletas = cantidadTotalTareasCompletas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}

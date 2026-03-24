package edu.eci.dosw.techcup_futbol.imageservice.model.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "imagenes")
public class ImagenDocument {

    // MongoDB document id.
    @Id
    private String id;
    // Original file metadata and binary payload.
    private String nombre;
    private String tipoContenido;
    private Long tamano;
    private byte[] datos;
    // Upload timestamp and business reference key.
    private LocalDateTime fechaCarga;
    private String referenciaExterna;

    public ImagenDocument() {
    }

    public ImagenDocument(String nombre, String tipoContenido, Long tamano, byte[] datos,
                          LocalDateTime fechaCarga, String referenciaExterna) {
        this.nombre = nombre;
        this.tipoContenido = tipoContenido;
        this.tamano = tamano;
        this.datos = datos;
        this.fechaCarga = fechaCarga;
        this.referenciaExterna = referenciaExterna;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public Long getTamano() {
        return tamano;
    }

    public void setTamano(Long tamano) {
        this.tamano = tamano;
    }

    public byte[] getDatos() {
        return datos;
    }

    public void setDatos(byte[] datos) {
        this.datos = datos;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    public void setReferenciaExterna(String referenciaExterna) {
        this.referenciaExterna = referenciaExterna;
    }
}

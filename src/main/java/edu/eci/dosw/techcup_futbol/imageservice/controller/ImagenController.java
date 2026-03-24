package edu.eci.dosw.techcup_futbol.imageservice.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.eci.dosw.techcup_futbol.imageservice.model.document.ImagenDocument;
import edu.eci.dosw.techcup_futbol.imageservice.service.ImagenService;

@RestController
@RequestMapping("/imagenes")
public class ImagenController {

    private final ImagenService imagenService;

    public ImagenController(ImagenService imagenService) {
        this.imagenService = imagenService;
    }

    // Uploads an image with its external reference.
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImagenDocument subirImagen(@RequestParam("archivo") MultipartFile archivo,
                                      @RequestParam("referenciaExterna") String referenciaExterna) throws IOException {
        return imagenService.guardar(archivo, referenciaExterna);
    }

    // Returns all stored images.
    @GetMapping
    public List<ImagenDocument> listar() {
        return imagenService.listar();
    }

    // Returns the raw image bytes for a given id.
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable String id) {
        ImagenDocument imagen = imagenService.buscarPorId(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imagen.getNombre() + "\"")
                .contentType(MediaType.parseMediaType(imagen.getTipoContenido()))
                .body(imagen.getDatos());
    }

    // Returns images linked to the same external reference.
    @GetMapping("/referencia/{referenciaExterna}")
    public List<ImagenDocument> listarPorReferencia(@PathVariable String referenciaExterna) {
        return imagenService.listarPorReferencia(referenciaExterna);
    }

    // Deletes an image by id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        imagenService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

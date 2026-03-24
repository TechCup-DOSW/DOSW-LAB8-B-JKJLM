package edu.eci.dosw.techcup_futbol.imageservice.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import edu.eci.dosw.techcup_futbol.imageservice.model.document.ImagenDocument;
import edu.eci.dosw.techcup_futbol.imageservice.repository.ImagenRepository;

@Service
// Handles image storage operations over MongoDB.
public class ImagenService {

    private final ImagenRepository imagenRepository;

    public ImagenService(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    // Validates and stores the uploaded file in MongoDB.
    public ImagenDocument guardar(MultipartFile archivo, String referenciaExterna) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar un archivo valido");
        }

        ImagenDocument imagen = new ImagenDocument(
                archivo.getOriginalFilename(),
                archivo.getContentType(),
                archivo.getSize(),
                archivo.getBytes(),
                LocalDateTime.now(),
                referenciaExterna
        );

        return imagenRepository.save(imagen);
    }

    // Retrieves a single image by id or returns 404 if it does not exist.
    public ImagenDocument buscarPorId(String id) {
        return imagenRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada"));
    }

    // Returns all stored images.
    public List<ImagenDocument> listar() {
        return imagenRepository.findAll();
    }

    // Returns all images associated with the provided external reference.
    public List<ImagenDocument> listarPorReferencia(String referenciaExterna) {
        return imagenRepository.findByReferenciaExterna(referenciaExterna);
    }

    // Deletes an image by id and fails with 404 when the id is unknown.
    public void eliminar(String id) {
        if (!imagenRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Imagen no encontrada");
        }
        imagenRepository.deleteById(id);
    }
}

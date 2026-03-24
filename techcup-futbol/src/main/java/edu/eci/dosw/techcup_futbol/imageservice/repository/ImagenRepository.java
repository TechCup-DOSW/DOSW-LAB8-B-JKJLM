package edu.eci.dosw.techcup_futbol.imageservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.eci.dosw.techcup_futbol.imageservice.model.document.ImagenDocument;

public interface ImagenRepository extends MongoRepository<ImagenDocument, String> {

    // Finds all images associated with the given external reference.
    List<ImagenDocument> findByReferenciaExterna(String referenciaExterna);
}

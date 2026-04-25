package main.java.com.bibliotech.service;

import main.java.com.bibliotech.exception.LibraryException;
import main.java.com.bibliotech.model.Resource;
import main.java.com.bibliotech.repository.RepositoryResourceInterface; // Importamos la interfaz específica
import java.util.List;

public class ResourceService {
    // Cambiamos el tipo a la interfaz que sí tiene autor, año y categoría
    private final RepositoryResourceInterface resourceRepo;

    public ResourceService(RepositoryResourceInterface resourceRepo) {
        this.resourceRepo = resourceRepo;
    }

    public List<Resource> getAllResources() {
        return resourceRepo.searchAll();
    }

    public Resource getByIsbn(String isbn) throws LibraryException {
        return resourceRepo.searchById(isbn)
                .orElseThrow(() -> new LibraryException("Resource not found with ISBN: " + isbn));
    }

    public Resource getByName(String name) throws LibraryException {
        return resourceRepo.searchByName(name)
                .orElseThrow(() -> new LibraryException("Resource not found with name: " + name));
    }

    public Resource getByAuthor(String author) throws LibraryException {
        return resourceRepo.searchByAutor(author)
                .orElseThrow(() -> new LibraryException("No resources found by author: " + author));
    }

    public Resource getByYear(int year) throws LibraryException {
        return resourceRepo.searchByYear(year)
                .orElseThrow(() -> new LibraryException("No resources found from year: " + year));
    }

    public Resource getByCategory(String category) throws LibraryException {
        return resourceRepo.searchByCategory(category)
                .orElseThrow(() -> new LibraryException("No resources found in category: " + category));
    }
}
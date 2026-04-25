package main.java.com.bibliotech.repository;
import main.java.com.bibliotech.model.Resource;
import java.util.Optional;
import java.util.List;
// Interfaz exclusiva para Recursos
public interface RepositoryResourceInterface extends Repository<Resource, String> {
    Optional<Resource> searchByYear(int year);
    Optional<Resource> searchByCategory(String category);
    Optional<Resource> searchByName(String name);
    Optional<Resource> searchByAutor(String autor);
    List<Resource> searchAll();



}

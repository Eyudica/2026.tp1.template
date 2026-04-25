package main.java.com.bibliotech.repository;
import java.util.Optional;
import java.util.List;
public interface Repository<T, ID> {
    void save(T entity); //Recibe un objeto y lo guarda en el sistema de almacenamiento.
    Optional<T> searchById(ID id);
}

//T=type          ID=identifier
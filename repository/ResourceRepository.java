package main.java.com.bibliotech.repository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import main.java.com.bibliotech.model.Resource;
import main.java.com.bibliotech.model.Resource;


public class ResourceRepository implements RepositoryResourceInterface {
    private Map<String, Resource> data = new HashMap<>();

    @Override
    public void save(Resource resource) {
        data.put(resource.getIsbn(), resource);
    }

    @Override
    public Optional<Resource> searchById(String isbn) {
        return Optional.ofNullable(data.get(isbn));
    }

    @Override
    public Optional<Resource> searchByName(String name) {
        return data.values().stream()
                .filter(r -> r.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public Optional<Resource> searchByAutor(String autor) {
        return data.values().stream()
                .filter(r -> r.getAutor().equalsIgnoreCase(autor))
                .findFirst();
    }

    @Override
    public Optional<Resource> searchByYear(int year) {
        return data.values().stream()
                .filter(r -> r.getYear() == year)
                .findFirst();
    }

    @Override
    public Optional<Resource> searchByCategory(String category) {
        return data.values().stream()
                .filter(r -> r.getCategory().equalsIgnoreCase(category))
                .findFirst();
    }

    @Override
    public List<Resource> searchAll() {
        return new ArrayList<>(data.values());
    }
}
package main.java.com.bibliotech.model;

public abstract class Resource {
    String isbn;
    String name;
    String autor;
    String category;
    int year;
    int stock;


    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategory() {
        return category;
    }
    public int getYear(){return year;}
    public int getStock() {
        return stock;
    }
    public void setStock(int stock){
        this.stock=stock;
    }


}

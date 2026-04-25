package main.java.com.bibliotech.model;
// si no cree el constructor en la padre no hace falta el super
public class Book extends Resource{
    public Book(String isbn, String name, String autor, String category,int stock,int year){
        this.isbn = isbn;
        this.name = name;
        this.autor = autor;
        this.category = category;
        this.stock = stock;
        this.year=year;
    }
    public void decreaseStock(){
        this.stock --;
    }
    public void increaseStock(){
        this.stock++;
    }
}

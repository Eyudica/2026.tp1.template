package main.java.com.bibliotech.model;

public class EBook extends Resource {
    private String link;
    public EBook(String isbn, String name, String autor, String category, String link,int year) {

        this.isbn = isbn;
        this.name = name;
        this.autor = autor;
        this.category = category;
        this.link=link;
        this.year=year;
    }
    @Override
    public int getStock(){

        return 999;
    }
    public String getLink(){return link;}


}

package main.java.com.bibliotech.model;


import java.time.LocalDate;
import java.util.Optional;


public class Loan {
    private Resource resource;
    private LocalDate loanDate;
    private LocalDate returnDate;
    public Loan(Resource resource) {
        this.resource = resource;
        this.loanDate = LocalDate.now();
    }

    public Resource getResource() { return resource; }
    public LocalDate getLoanDate() { return loanDate; }

    public LocalDate getReturnDate(){
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate){
        this.returnDate=returnDate;
    }
}
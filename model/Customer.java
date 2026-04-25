package main.java.com.bibliotech.model;
import java.util.List;
import java.util.ArrayList;

public abstract class Customer {
    public int dni;
    public String name;
    public String email;
    public List<Loan> activeLoans=new ArrayList<>();
    public List<Loan> previousLoans=new ArrayList<>();
    public String type;
    public abstract int getMaxLimit();

    public boolean canRequestMoreResources() {
        return activeLoans.size() < getMaxLimit();
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }

    public int getDni() {
        return dni;
    }
    public List<Loan> getActiveLoans() {
        return activeLoans;
    }
    public List<Loan> getPreviousLoans(){return previousLoans; }
    public String getType(){return type;}
}
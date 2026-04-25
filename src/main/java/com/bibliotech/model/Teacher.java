package main.java.com.bibliotech.model;

public class Teacher extends Customer {
    public Teacher(int dni, String name, String email,String type){
        this.dni=dni;
        this.name=name;
        this.email=email;
        this.type=type;
    }
    @Override
    public int getMaxLimit(){

        return 5;
    }
}

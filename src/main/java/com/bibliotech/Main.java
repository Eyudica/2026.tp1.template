package main.java.com.bibliotech;

import main.java.com.bibliotech.exception.LibraryException;
import main.java.com.bibliotech.model.*;
import main.java.com.bibliotech.repository.*;
import main.java.com.bibliotech.service.*;
import java.util.Scanner;
import java.util.Optional;
public class Main {
    public static void main(String[] args) {
        ResourceRepository resourceRepo = new ResourceRepository();
        CustomerRepository customerRepo = new CustomerRepository();

        LoanService LoanService = new LoanService(resourceRepo, customerRepo);

        CustomerService customerService = new CustomerService(customerRepo);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Welcome to Bibliotech ---");
            System.out.println("Type the number of the functionality you wanna use:");
            System.out.println("1 - Resource register");
            System.out.println("2 - Resource finder");
            System.out.println("3 - Customer register");
            System.out.println("4 - Loan manager");
            System.out.println("5 - Customer finder");
            System.out.println("0 - Exit");

            int option = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            try {
                switch (option) {
                    case 1 -> registerResource(scanner, resourceRepo);
                    case 2 -> getResourceInfo(scanner, resourceRepo);
                    case 3 -> registerCustomer(scanner, customerService);
                    case 4 -> manageLoan(scanner, LoanService);
                    case 5 -> getCustomerInfo(scanner, customerService);
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void getCustomerInfo(Scanner sc, CustomerService service) {
        System.out.print("Type customer DNI: ");

        if (!sc.hasNextInt()) {
            System.out.println("Error: DNI must be a numeric value.");
            sc.nextLine();
            return;
        }

        int dni = sc.nextInt();
        sc.nextLine();

        service.searchCustomer(dni).ifPresentOrElse(
                customer -> {
                    System.out.println("\n==============================");
                    System.out.println("       CUSTOMER PROFILE       ");
                    System.out.println("==============================");
                    System.out.println("Name:  " + customer.getName());
                    System.out.println("DNI:   " + customer.getDni());

                    // 1. Mostrar Préstamos Activos (los que tiene ahora)
                    System.out.println("\n--- Currently Borrowed ---");
                    if (customer.getActiveLoans().isEmpty()) {
                        System.out.println("No books currently borrowed.");
                    } else {
                        customer.getActiveLoans().forEach(loan -> {
                            System.out.println("[ACTIVE]: " + loan.getResource().getName() +
                                    " | ISBN: "+ loan.getResource().getIsbn()+
                                    " | Date: " + loan.getLoanDate());
                        });
                    }

                    // 2. Mostrar Préstamos Anteriores (Historial)
                    System.out.println("\n--- Previous Loans (History) ---");
                    if (customer.getPreviousLoans() == null || customer.getPreviousLoans().isEmpty()) {
                        System.out.println("No previous loan history found.");
                    } else {
                        customer.getPreviousLoans().forEach(loan ->
                                System.out.println("[RETURNED] " + loan.getResource().getName() + " ISBN: " + loan.getResource().getIsbn() +
                                        " Date of creation: " + loan.getLoanDate() +
                                        " Date of return: " + loan.getReturnDate()));
                    }
                    System.out.println("==============================\n");
                },
                () -> System.out.println("\n[!] Error: Customer with DNI " + dni + " not found.")
        );
    }


    private static void getResourceInfo(Scanner sc, ResourceRepository repo) {
        System.out.println("\n--- Resource Finder ---");
        System.out.println("Search by: 1-ISBN, 2-Name, 3-Year, 4-Author, 5-Category");

        if (!sc.hasNextInt()) {
            System.out.println("Error: Please enter a number.");
            sc.nextLine();
            return;
        }

        int option = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        // Variable para guardar el resultado de cualquiera de las búsquedas
        Optional<Resource> result = Optional.empty();

        try {
            switch (option) {
                case 1 -> {
                    System.out.print("Enter ISBN: ");
                    result = repo.searchById(sc.nextLine());
                }
                case 2 -> {
                    System.out.print("Enter Name: ");
                    result = repo.searchByName(sc.nextLine());
                }
                case 3 -> {
                    System.out.print("Enter Year: ");
                    int year = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer
                    result = repo.searchByYear(year);
                }
                case 4 -> {
                    System.out.print("Enter Author: ");
                    result = repo.searchByAutor(sc.nextLine());
                }
                case 5 -> {
                    System.out.print("Enter Category: ");
                    result = repo.searchByCategory(sc.nextLine());
                }
                default -> System.out.println("Invalid option.");
            }

            result.ifPresentOrElse(
                    res -> {
                        System.out.println("\n--- RESOURCE FOUND ---");
                        System.out.println("Name: " + res.getName());
                        System.out.println("Author: " + res.getAutor());
                        System.out.println("Category: " + res.getCategory());
                        System.out.println("Year: " + res.getYear());
                        System.out.println("ISBN: " + res.getIsbn());

                        if (res instanceof Book b) {
                            System.out.println("Type: Physical Book | Stock: " + b.getStock());
                        } else if (res instanceof EBook eb) {
                            System.out.println("Type: E-Book | Link: " + eb.getLink());
                        }
                    },
                    () -> System.out.println("\n[!] No resource found matching those criteria.")
            );

        } catch (Exception e) {
            System.out.println("Error during search: " + e.getMessage());
        }
    }
    private static void registerResource(Scanner sc, ResourceRepository repo) {
        System.out.println("Type: 1-Book, 2-EBook");
        int type = sc.nextInt(); sc.nextLine();
        System.out.print("ISBN: "); String isbn = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Author: "); String author = sc.nextLine();
        System.out.print("Category: "); String category = sc.nextLine();
        System.out.print("Year: "); int year = sc.nextInt();




        if (type == 1) {
            System.out.print("Stock: "); int stock = sc.nextInt();
            repo.save(new Book(isbn, name, author, category, stock,year));
        } else {
            System.out.print("Link: "); String link = sc.nextLine();
            repo.save(new EBook(isbn, name, author, category, link,year));
        }
        System.out.println("Resource saved!");
    }

    private static void registerCustomer(Scanner sc, CustomerService service) {
        System.out.println("Type: 1-Student, 2-Teacher");
        int type = sc.nextInt(); sc.nextLine();
        System.out.print("DNI (numbers only): "); int dni = sc.nextInt(); sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();

        if (type == 1) {
            service.registerCustomer(new Student(dni, name, email,"Student"));
        } else {
            service.registerCustomer(new Teacher(dni, name, email,"Teacher"));
        }
        System.out.println("Customer registered!");
    }

    private static void manageLoan(Scanner sc, LoanService service) throws LibraryException {
        System.out.println("\n--- Loan Management ---");
        System.out.println("1 - Create New Loan");
        System.out.println("2 - Return Resource (Delete Loan)");
        System.out.print("Select an option: ");

        int choice = sc.nextInt(); sc.nextLine();

        System.out.print("Customer DNI: ");
        int dni = sc.nextInt(); sc.nextLine();
        System.out.print("Resource ISBN: ");
        String isbn = sc.nextLine();
        try {
            if (choice == 1) {
                service.makeLoan(isbn, dni);
                System.out.println("Success: loan registered");
            } else if (choice == 2) {
                service.deleteLoan(isbn, dni);
                System.out.println("Success: loan deleted");
            } else {
                System.out.println("Invalid choice");
            }
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
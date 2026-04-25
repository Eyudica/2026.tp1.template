package main.java.com.bibliotech.service;
import main.java.com.bibliotech.model.Resource;
import main.java.com.bibliotech.repository.Repository;
import main.java.com.bibliotech.model.Book;
import main.java.com.bibliotech.model.Loan;
import main.java.com.bibliotech.model.Customer;
import java.time.LocalDate;
import main.java.com.bibliotech.exception.LibraryException;
public class LoanService {
    private final Repository<Resource, String> resourceRepo;
    private final Repository<Customer, Integer> customerRepo;

    // Inyección por constructor
    public LoanService(Repository<Resource, String> resourceRepo,Repository<Customer, Integer> customerRepo) {
        this.resourceRepo = resourceRepo;
        this.customerRepo = customerRepo;
    }

    public void makeLoan(String isbn, int customerDni) throws LibraryException {
        Resource resource = resourceRepo.searchById(isbn).orElseThrow(() -> new LibraryException("Book not found"));
        Customer customer = customerRepo.searchById(customerDni).orElseThrow(() -> new LibraryException("Customer not found"));


        boolean alreadyHasIt = customer.getActiveLoans().stream()
                .anyMatch(loan -> loan.getResource().getIsbn().equals(isbn));

        if (alreadyHasIt) {
            throw new LibraryException("Operation failed: Customer '" + customer.getName() +
                    "' already has the resource with ISBN " + isbn + ".");
        }
        if (resource instanceof Book book)
            if (book.getStock() <=0 ){
                throw new LibraryException("There is no stock available");
            }else{
                book.decreaseStock();
            }

        if (!customer.canRequestMoreResources()){
            throw new LibraryException("Cant request more books");
        }
        Loan newLoan =  new Loan(resource);
        customer.getActiveLoans().add(newLoan);


    }
    public void deleteLoan(String isbn, int customerDni) throws LibraryException {
        Customer customer = customerRepo.searchById(customerDni).orElseThrow(() -> new LibraryException("Customer not found"));

        Resource resource = resourceRepo.searchById(isbn).orElseThrow(() -> new LibraryException("Book not found"));


        Loan loanToReturn = customer.getActiveLoans().stream()
                .filter(l -> l.getResource().getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new LibraryException("The customer does not have a loan with ISBN: " + isbn));

        loanToReturn.setReturnDate(LocalDate.now());
        customer.getActiveLoans().remove(loanToReturn);
        customer.getPreviousLoans().add(loanToReturn);

        if (resource instanceof Book book) {
            book.increaseStock();
        }


    }
}
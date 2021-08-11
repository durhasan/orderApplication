package com.readingisgood.controller;

import com.readingisgood.entity.Book;
import com.readingisgood.exception.ResourceNotFoundException;
import com.readingisgood.repository.BookRepository;
import com.readingisgood.service.SequenceGeneratorService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "api/book", produces = { MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8" }, consumes = {
        MediaType.APPLICATION_JSON_VALUE})
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @GetMapping(value = "/list", consumes = {MediaType.ALL_VALUE})
    public List<Book> getBooks(){
        log.info("Listing all books");
        return bookRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        book.setId(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME));
        return ResponseEntity.ok(bookRepository.save(book));
    }

        @PutMapping("/updateStock/{id}")
        public ResponseEntity<Book> updateStock(@PathVariable(value = "id") Long bookId,
                @RequestBody Book book) throws ResourceNotFoundException {
            Book bookFound = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
            bookFound.setStock(book.getStock());
            return ResponseEntity.ok(bookRepository.save(bookFound));
    }
}

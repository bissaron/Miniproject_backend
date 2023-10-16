package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.model.Todo;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")

public class TransactionController {
    
    @Autowired
    TransactionRepository transactionRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/transaction")
	public ResponseEntity<Object> getTransaction() {
     try {		
	     List<Transaction> transactions = transactionRepository.findAll(); 	
		 return new ResponseEntity<>(transactions, HttpStatus.OK);
	} catch (Exception e) {	
		return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	}

	
	@PostMapping("/transaction")
public ResponseEntity<Object> addTransaction(@RequestBody Transaction body) {
	
	try {
		Transaction transaction =  transactionRepository.save(body);
		return new ResponseEntity<>(transaction, HttpStatus.CREATED);
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	@DeleteMapping("transaction/{transactionId}")
	public ResponseEntity<Object> deleteTransaction(@PathVariable Integer transactionId) {
        
		try {
			Optional<Transaction> transaction = transactionRepository.findById(transactionId);

			if (transaction.isPresent()) {
				transactionRepository.delete(transaction.get());

				return  new ResponseEntity<>("DELETE SUCSESS", HttpStatus.OK );
			} else {
				return new ResponseEntity<>("Transaction not found", HttpStatus.BAD_REQUEST);
			}			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	@PutMapping("transaction/{transactionId}")
	public ResponseEntity<Object> updateTransaction(@PathVariable Integer transactionId, @RequestBody Transaction body) {

		try {
			Optional<Transaction> transaction = transactionRepository.findById(transactionId);

			if (transaction.isPresent()) {
				Transaction transactionEdit = transaction.get();
				transactionEdit.setDate(body.getDate());
				transactionEdit.setAmount(body.getAmount());
				transactionEdit.setType(body.getType());
				transactionEdit.setCategory(body.getCategory());
				transactionEdit.setDescription(body.getDescription());

				transactionRepository.save(transactionEdit);

				return new ResponseEntity<>(transactionEdit, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Reserve not found", HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
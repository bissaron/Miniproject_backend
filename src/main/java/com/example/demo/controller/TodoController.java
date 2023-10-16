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
import com.example.demo.model.Todo;
import com.example.demo.model.User;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;

@RestController
@CrossOrigin(origins = "*")

public class TodoController {

	@Autowired
	TransactionRepository transactionRepository;
    
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TodoRepository todoRepository;
	
	@GetMapping("/todo")
	public ResponseEntity<Object> getTodo() {
     try {		
	     List<Todo> todos = todoRepository.findAll(); 	
		 return new ResponseEntity<>(todos, HttpStatus.OK);
	} catch (Exception e) {	
		return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	}

	
	@PostMapping("/todo")
public ResponseEntity<Object> addTodo(@RequestBody Todo body) {
	
	try {
		Todo todo =  todoRepository.save(body);
		return new ResponseEntity<>(todo, HttpStatus.CREATED);
	} catch (Exception e) {
		e.printStackTrace();
		return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}
	
	@DeleteMapping("todo/{todoId}")
	public ResponseEntity<Object> deleteTodo(@PathVariable Integer todoId) {
        
		try {
			Optional<Todo> todo = todoRepository.findById(todoId);

			if (todo.isPresent()) {
				todoRepository.delete(todo.get());

				return  new ResponseEntity<>("DELETE SUCSESS", HttpStatus.OK );
			} else {
				return new ResponseEntity<>("Todo not found", HttpStatus.BAD_REQUEST);
			}			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	@PutMapping("todo/{todoId}")
	public ResponseEntity<Object> updateTodo(@PathVariable Integer todoId, @RequestBody Todo body) {

		try {
			Optional<Todo> todo = todoRepository.findById(todoId);

			if (todo.isPresent()) {
				Todo todoEdit = todo.get();
				todoEdit.setText(body.getText());
				todoEdit.setDone(body.isDone());
				todoRepository.save(todoEdit);

				return new ResponseEntity<>(todoEdit, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Reserve not found", HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
package com.giosoft.base.main.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.giosoft.base.main.dtos.BaseDTO;
import com.giosoft.base.main.services.IGenericService;

public abstract class GenericController<D extends BaseDTO,S extends IGenericService<D>> {
	protected static final String DEFAULT_SIZE = "10"; 
	@Autowired
	protected S service;
	
	@GetMapping("/count")
	@Transactional
	public ResponseEntity<?> getCount(@RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size){
		try {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body("{\"pages\": " + service.countPages(size) + "}");
		} catch (Exception e) {
			return null;
		}
	}//getCount()
	
	@GetMapping("/")
	@Transactional
	public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size){
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(service.findAll(page, size));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}//getAll()
	
	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<?> getOne(@PathVariable int id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}//getOne()
	
	@PostMapping("/")
	@Transactional
	public ResponseEntity<?> post(@RequestBody D dtoForm){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dtoForm));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}//post()
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<?> put(@PathVariable int id, @RequestBody D dtoForm){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dtoForm));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}//put()
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable int id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}//delete()
}

package com.giosoft.base.main.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.giosoft.base.main.dtos.BaseDTO;
import com.giosoft.base.main.entities.BaseEntity;

public abstract class GenericBaseService<D extends BaseDTO, E extends BaseEntity, R extends JpaRepository<E, Integer>>
		implements IGenericService<D> {

	// Atributos
	@Autowired
	protected R repo;

	// Para utilizar con ModelMapper
	protected Class<D> dtoClass;
	protected Class<E> entityClass;

	@Override
	public D findById(int id) throws Exception {
		D dto;
		try {
			Optional<E> eOptional = repo.findById(id);

			E entity = eOptional.get();
			ModelMapper modelMapper = new ModelMapper();
			dto = (D) modelMapper.map(entity, dtoClass);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return dto;
	}// findByID

	@Override
	public List<D> findAll(int page, int size) throws Exception {
		List<D> dtos = new ArrayList<D>();
		try {
			ModelMapper modelMapper = new ModelMapper();
			Pageable pageable = PageRequest.of(page, size);
			Page<E> entities = repo.findAll(pageable);

			for (E entity : entities.getContent()) {
				D dto = (D) modelMapper.map(entity, dtoClass);
				dtos.add(dto);
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return dtos;
	}// findAll()

	@Override
	public int countPages(int size) throws Exception {
		try {
			Pageable pageable = PageRequest.of(0, size);
			return repo.findAll(pageable).getTotalPages();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}// countPages()

	@Override
	public D save(D dto) throws Exception {
		E entity;
		ModelMapper modelMapper = new ModelMapper();

		try {
			entity = (E) modelMapper.map(dto, entityClass);
			entity = repo.save(entity);

			dto = (D) modelMapper.map(entity, dtoClass);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return dto;
	}// save()

	@Override
	public D update(int id, D dto) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		Optional<E> eOptional;
		E entity;
		
		try {
			if (repo.existsById(id)) {
				entity = modelMapper.map(dto, entityClass);
				entity = repo.save(entity);
				
				dto = modelMapper.map(entity, dtoClass);
			} else {
				throw new Exception("Error: The required registry doesn't exists");
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return dto;
	}//update()

	@Override
	public boolean delete(int id) throws Exception {
		try {
			if (repo.existsById(id)) {
				repo.deleteById(id);
			} else {
				throw new Exception("Error: The required registry doesn't exists");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return !repo.existsById(id);
	}//delete()

}

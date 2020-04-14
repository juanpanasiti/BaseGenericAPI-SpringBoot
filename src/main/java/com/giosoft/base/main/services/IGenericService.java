package com.giosoft.base.main.services;

import java.util.List;

import com.giosoft.base.main.dtos.BaseDTO;

public interface IGenericService<D extends BaseDTO> {
	//D es por DTO
	
	//Buscar un registro por ID
	public D findById(int id) throws Exception;
	
	//Buscar un conjunto (paginado)
	public List<D> findAll(int page, int size) throws Exception;
	
	//Contar páginas según una cantidad de registros dada
	public int countPages(int size) throws Exception;
	
	//Guardar un registro
	public D save(D dto) throws Exception;
	
	//Actualizar un registro
	public D update(int id, D dto) throws Exception;
	
	//Eliminar un registro
	public boolean delete(int id) throws Exception;
}

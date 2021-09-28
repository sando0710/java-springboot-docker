package com.example.demo.repositries;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.models.ItemForm;

@Repository
public interface ItemRepository extends JpaRepository<ItemForm, String>{
	Optional<ItemForm> findById(String id);
	List<ItemForm> findAll();

}
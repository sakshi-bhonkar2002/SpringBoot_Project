package com.fire.spring.datajpa.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fire.spring.datajpa.model.firesystem;
import com.fire.spring.datajpa.repository.firesystemRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class firesystemController {

	@Autowired
	firesystemRepository firesystemRepository;

	@GetMapping("/tutorials")
	public ResponseEntity<List<firesystem>> getAllFires(@RequestParam(required = false) String title) {
		try {
			List<firesystem> firesystems = new ArrayList<firesystem>();

			if (title == null)
				firesystemRepository.findAll().forEach(firesystems::add);
			else
				firesystemRepository.findByTitleContaining(title).forEach(firesystems::add);

			if (firesystems.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(firesystems, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/fires/{id}")
	public ResponseEntity<firesystem> getFireById(@PathVariable("id") long id) {
		Optional<firesystem> fireData = firesystemRepository.findById(id);

		if (fireData.isPresent()) {
			return new ResponseEntity<>(fireData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/fires")
	public ResponseEntity<firesystem> createFire(@RequestBody firesystem firesystem) {
		try {
			firesystem _tutorial = firesystemRepository
					.save(new firesystem(firesystem.getTitle(), firesystem.getDescription(), false));
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/fires/{id}")
	public ResponseEntity<firesystem> updateFire(@PathVariable("id") long id, @RequestBody firesystem firesystem) {
		Optional<firesystem> fireData = firesystemRepository.findById(id);

		if (fireData.isPresent()) {
			firesystem _fire = fireData.get();
			_fire.setTitle(firesystem.getTitle());
			_fire.setDescription(firesystem.getDescription());
			_fire.setPublished(firesystem.isPublished());
			return new ResponseEntity<>(firesystemRepository.save(_fire), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/fires/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			firesystemRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/fires")
	public ResponseEntity<HttpStatus> deleteAllFires() {
		try {
			firesystemRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/fires/published")
	public ResponseEntity<List<firesystem>> findByPublished() {
		try {
			List<firesystem> firesystems = firesystemRepository.findByPublished(true);

			if (firesystems.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(firesystems, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

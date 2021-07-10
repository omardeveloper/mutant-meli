package com.meli.mutant.controller;

import com.meli.mutant.service.MutantService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meli")
public class MutantController {

	@Autowired
	MutantService mutantService;

	@PostMapping("/mutant")
	public ResponseEntity isMutant(@RequestBody Map<String, String[]> data) {
		return mutantService.isMutant(data.get("dna")) ? new ResponseEntity(HttpStatus.OK)
				: new ResponseEntity(HttpStatus.FORBIDDEN);
	}

	@GetMapping("/stats")
	public ResponseEntity stats() {
		return new ResponseEntity<>(mutantService.stats(), HttpStatus.OK);
	}

}

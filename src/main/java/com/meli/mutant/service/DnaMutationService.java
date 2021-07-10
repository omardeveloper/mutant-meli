package com.meli.mutant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.mutant.pojo.DnaMutation;
import com.meli.mutant.repository.DnaMutationRepository;

@Service
public class DnaMutationService {
	
	@Autowired
	DnaMutationRepository dnaRepository;
	
	/**This Method get all data of redis
	 * @return list of DnaMutation
	 */
	public List<DnaMutation> getAll(){
        return dnaRepository.getAll();
    }
	
	/**This Method search for dna
	 * @param dna
	 * @return DnaMutation
	 */
	public DnaMutation getDnaMutation(String dna){
        return dnaRepository.findByIdDna(dna);
    }
	
	/**This Method get all data
	 * @param dnaMutation
	 * @return DnaMutation
	 */
	public DnaMutation save(DnaMutation dnaMutation){
        return dnaRepository.save(dnaMutation);
    }
	
}

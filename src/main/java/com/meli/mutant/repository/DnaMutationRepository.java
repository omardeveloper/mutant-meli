package com.meli.mutant.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.meli.mutant.pojo.DnaMutation;


@Repository
public class DnaMutationRepository {

	public static final String HASH_KEY = "DnaMutation";

	@Autowired
	private RedisTemplate redisTemplate;

	
	/** This Method save the DnaMutation in redis
	 * @param dnaMutation
	 * @return DnaMutation
	 */
	public DnaMutation save(DnaMutation dnaMutation) {
		redisTemplate.opsForHash().put(HASH_KEY, dnaMutation.getIdDna(), dnaMutation);
		return dnaMutation;
	}

	/**This Method search in database for IdDna
	 * @param idDna
	 * @return DnaMutation
	 */
	public DnaMutation findByIdDna(String idDna) {
		DnaMutation dnaFound = (DnaMutation) redisTemplate.opsForHash().get(HASH_KEY, idDna);
		return dnaFound;
	}

	/**This Method get all data from redis
	 * @return list of DnaMutation
	 */
	public List<DnaMutation> getAll() {
		return (List<DnaMutation>) redisTemplate.opsForHash().values(HASH_KEY);
	}
}

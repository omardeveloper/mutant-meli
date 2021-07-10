package com.meli.mutant.pojo;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@RedisHash("DnaMutation")
public class DnaMutation implements Serializable {

	private String idDna;
	private String[] dna;
	private int mutations;

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		for (String line : dna) {
			builder.append("[").append(line).append("]");
		}

		return builder.toString();
	}
}

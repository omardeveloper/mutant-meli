package com.meli.mutant.pojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DnaStats {
	private int countMutant;
	private int countHuman;
	private double ratio;
}

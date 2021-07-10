package com.meli.mutant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.meli.mutant.pojo.DnaMutation;
import com.meli.mutant.pojo.DnaStats;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class MutantService {
    
	@Value("${mutant.quantity.sequence}")
	private int numSequences;
	
	@Value("${mutant.double.places}")
	private int placesRound;
	
	@Autowired
	DnaMutationService dnaMutationService;
	
    /**
     * This Method verify if the String[] have sequence mutant
     * @param dna
     * @return true of false
     */
    public boolean isMutant(String[] dna) {

        //validate the length and characters 
        if (!validateDNA(dna)){
            return false;
        }
        
        int mutations = getMutations(dna);
        
        DnaMutation adnMutation = DnaMutation.builder()
        							.dna(dna)
        							.mutations(mutations)
        							.build();        		
        
        String dnaSearch = adnMutation.toString();
        adnMutation.setIdDna(dnaSearch);
		if (dnaMutationService.getDnaMutation(dnaSearch) == null) {
			dnaMutationService.save(adnMutation);
		}	
        
        if (mutations >= numSequences) {
			return true;
		}
        return false;
    }
        
    
    /**
     * This Method get the number of mutations
     * @param adnChar
     * @return mutations
     */
    public int getMutations(String[] dna) {
    	
    	//allowed movements in the matrix
        int[][] movements = new int[][]{ {1,0},{1,1},{0,1},{-1,1} };

        //matrix declaration
        char[][] adnChar = new char[dna.length][dna.length];

        //mapping the multidimensional array
        for(int i = 0; i < dna.length; i++){
            adnChar[i] = dna[i].toCharArray();
        }
    	
    	int mutations = 0;
    	int positions = 4;
    	
    	//we go through the matrix and validate
        for(int i = 0; i < adnChar.length; i++){
            for(int j = 0; j < adnChar[i].length; j++){
                for(int k = 0; k < movements.length; k++){
                	//get the value in pos x and y
                    char charToValidate = adnChar[i][j];
                    if(validateMovement(adnChar, positions, j, i, movements[k], charToValidate)){
                        mutations++;
                    }
                }
            }
        }
        
        return mutations;
    }

    /**
     * This Method is using bactraking for search the results that match the character to Validate
     * @param dna
     * @param missingMovements
     * @param xPos
     * @param yPos
     * @param movement
     * @param charToValidate
     * @return true of false
     */
    boolean validateMovement(char[][] dna, int missingMovements, int xPos, int yPos, int[] movement, char charToValidate){
        //validate the position in x
        if(xPos >= dna[0].length || xPos < 0){
            return false;
        }

        //validate the position in y
        if(yPos >= dna.length || yPos < 0){
            return false;
        }

        //get the value in pos x and y
        char nextChar = dna[yPos][xPos];

        //we validate if the values ​​are equal
        if(nextChar == charToValidate){
            if(missingMovements == 1){
                return true;
            } else {
                return validateMovement(dna, missingMovements -1, xPos + movement[0], yPos + movement[1], movement, charToValidate);
            }
        } else {
            return false;
        }

    }

    /**
     * This Method verify if the String[] have characters allowed and the length be correct
     * @param dna
     * @return true of false
     */
    public boolean validateDNA(String[] dna){
        boolean validate = true;
        int lengthChars = dna.length;
        //
        for (String line: dna) {
            List<String> myCodes = new ArrayList<String>(Arrays.asList(line.split("")));
            long discard = myCodes.stream()
                    .filter(x -> x.matches("[^ATCGatcg]+"))
                    .count();
            if (line.length() != lengthChars || discard > 0){
                validate = false;
                break;
            }                       
        }
        return  validate;
    }
    
    /**
     * This Method get the statistics of dna verifications 
     * @return json
     */
    public DnaStats stats() {
    	//first get all data from our database
    	List<DnaMutation> dnaMutationList = dnaMutationService.getAll();  
    	DnaStats dnaStats =  DnaStats.builder().countHuman(0).countMutant(0).ratio(0.0).build();
    	if (dnaMutationList.size() > 0) {
    		int countMutant = (int)dnaMutationList.stream().filter(dna -> dna.getMutations() >= numSequences).count();
    		int countHuman = (int)dnaMutationList.stream().filter(dna -> dna.getMutations() < numSequences).count();
    		double ratio = countHuman > 0 ? ((double)countMutant/(double)countHuman) : 0.0;
    		dnaStats.setRatio(roundDouble(ratio));
    		dnaStats.setCountMutant(countMutant);
    		dnaStats.setCountHuman(countHuman);
		}
    	
    	return dnaStats;
    }
    
    
    /**This Method round a double
     * @param d
     * @return double
     */
    private double roundDouble(double d) {    	 
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(placesRound, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}

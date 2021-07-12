package com.meli.mutant.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import com.meli.mutant.pojo.DnaMutation;

@Service
public class SqsReceiverService {
	
//	@Autowired
//	DnaMutationService dnaMutationService;
//	
//	@SqsListener("dna-cache-details")
//	 public void userCacheListener(DnaMutation dnaMutation) {
//		System.out.println("Received Message for dna..." + dnaMutation.getIdDna());	
//		
//		dnaMutationService.save(dnaMutation);
//	    System.out.println("Save Message in Cache");
//	}
}

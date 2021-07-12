package com.meli.mutant.service;

//import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;

//import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.meli.mutant.pojo.DnaMutation;


@Service
public class SqsSenderService {

//	private QueueMessagingTemplate queueMessagingTemplate;
//	
//	public SqsSenderService(AmazonSQSAsync amazonSQSAsync) {
//		this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
//	}
//	
//	public void send(DnaMutation dnaMutation) {
//		queueMessagingTemplate.convertAndSend("dna-cache-details", dnaMutation);
//		System.out.println("Sending adnMutation to SQS..." + dnaMutation.getIdDna());
//	}
}

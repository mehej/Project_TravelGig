package com.synergy.client;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BookingClient {
	
	public JsonNode confirmBooking(JsonNode json) {		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8484/confirmBooking/",request, Object.class);
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	    return returnObj;
		
	}
	
	public JsonNode chkAvlblty(JsonNode json) {		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8484/chkAvlblty/",request, Object.class);
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	        return returnObj;
	}
	
	public JsonNode getBookingsByUser(String usermobile) {	
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);        
	    RestTemplate restTemplate = new RestTemplate();
	    System.out.println("inside booking client travel gig");
	    ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8484/getBookingsByUser/"+usermobile, Object.class);
	    Object objects = responseEntity.getBody();
	    System.out.println("inside booking client travel gig success");
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	        return returnObj;
	        
	}	

public String changeBookingStatus(int bookingId) {
		
		System.out.println("inside booking client changeBookingStatus");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);        
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8484/changeBookingStatus/"+bookingId, String.class);
	    System.out.println("inside booking changeBookingStatus");
	    String test=responseEntity.getBody();
	    //Object objects = responseEntity.getBody();    
	   //ObjectMapper mapper = new ObjectMapper();    
	    //JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	    //String returnObj = mapper.convertValue(objects , String.class);
	    return test; //returnObj;
	}



}

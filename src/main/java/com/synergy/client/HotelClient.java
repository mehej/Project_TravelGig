package com.synergy.client;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class HotelClient {

	public JsonNode GetHotelsBytext(String text) {	
	HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);        
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/getHotelsBytext/"+text, Object.class);
    Object objects = responseEntity.getBody();    
    ObjectMapper mapper = new ObjectMapper();    
    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
        return returnObj;
        
}	
	
	
	public JsonNode getAllRoomType(String searchtext) {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);        
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/getAllRoomType/"+searchtext, Object.class);
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	        return returnObj;
	}


	public JsonNode findHotels(JsonNode json) {		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8383/findHotels/",request, Object.class);
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	        return returnObj;
	}
	
	
	
	
	public JsonNode getHotelRoomsDetails(String Hotelid,String roomtype) {		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);        
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/getHotelRoomsDetails/"+ Hotelid +"/" +roomtype,Object.class);
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	    return returnObj;
	}


	public JsonNode getDiscountPrice(int hotelroomid) {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);        
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/getDiscountPrice/"+hotelroomid, Object.class);
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	        return returnObj;
		
		
	}


	


	public JsonNode getHotelName(String hotelid) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);        
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/getHotelName/"+hotelid, Object.class);
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	    return returnObj;
		
	}
	
	public JsonNode addReview(JsonNode json) {
		System.out.println("Inside booking client travel gig");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> request = new HttpEntity<String>(json.toString(), headers);
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.postForEntity("http://localhost:8383/addReview/",request, Object.class);
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	        return returnObj;
	}
	
	public JsonNode getHotelReview(int hotelid) {
		System.out.println("inside booking client changeBookingStatus");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);        
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/getHotelReview/"+hotelid, Object.class);
	    System.out.println("inside booking changeBookingStatus");
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);
	    return returnObj;
	}
	
	public JsonNode getAllQuestions() {
		
		System.out.println("inside booking client changeBookingStatus");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);        
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Object> responseEntity = restTemplate.getForEntity("http://localhost:8383/getAllQuestions/", Object.class);
	    System.out.println("inside booking getAllQuestions");
	    Object objects = responseEntity.getBody();    
	    ObjectMapper mapper = new ObjectMapper();    
	    JsonNode returnObj = mapper.convertValue(objects, JsonNode.class);	    
	    
	        return returnObj;
		
	}


	
	
}

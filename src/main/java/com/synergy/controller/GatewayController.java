package com.synergy.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.synergy.client.BookingClient;
import com.synergy.client.HotelClient;
import com.synergy.domain.User;
import com.synergy.repository.UserRepository;


@RestController
public class GatewayController {

	@Autowired
	HotelClient hotelClient;
	
	@Autowired
	BookingClient  bookingClient;
	
	@Autowired
	UserRepository userrepository;
	
	@RequestMapping(value="/GetHotelsBytext/{text}")
	public JsonNode GetHotelsBytext(@PathVariable String text) {
	return hotelClient.GetHotelsBytext(text);
		
	}
	
	
	
	@RequestMapping(value="/getAllRoomType/{searchtext}")
	public JsonNode getAllRoomType(@PathVariable String searchtext) {
	return hotelClient.getAllRoomType(searchtext);
		
	}
	
	
	
	@RequestMapping(value="/findHotels" ,method = RequestMethod.POST)
	public JsonNode findHotels(@RequestBody JsonNode json) {
	return hotelClient.findHotels(json);
	
	}	
	
	@RequestMapping(value="/chkAvlblty" ,method = RequestMethod.POST)
	public JsonNode chkAvlblty(@RequestBody JsonNode json) {
	return bookingClient.chkAvlblty(json);
	
	}	
	
	@RequestMapping(value="/getHotelRoomsDetails/{hotelid}/{roomtype}")
	public JsonNode getHotelRoomsDetails(@PathVariable String hotelid,@PathVariable  String roomtype ) {
		System.out.println("inside gateway controller");
	return hotelClient.getHotelRoomsDetails(hotelid,roomtype);
		
	}
	
	
	
	@RequestMapping(value="/getDiscountPrice/{hotelroomid}")
	public JsonNode getDiscountPrice(@PathVariable int hotelroomid) {
	return hotelClient.getDiscountPrice(hotelroomid);		
	}
	
	@RequestMapping(value="/getUserMobile")
	public String getUserMobile(Principal principal) {  
		String username=principal.getName();
		User user=userrepository.findByUserName(username);		
	    return user.getUserPhone();
	}
	
	
	@RequestMapping(value="/getUser")
	public String getUser(Principal principal) {  
		String username=principal.getName();
		//User user=userrepository.findByUserName(username);		
	    return username;
	}
	
	@RequestMapping(value="/gettimestamp/{checkInDate}/{checkOutDate}" )
	public List<Timestamp> gettimestamp(@PathVariable String checkInDate,@PathVariable String checkOutDate) {
		List<Timestamp> listts=new ArrayList<>();
		Date date= new Date();		
		Timestamp currentTimestamp = new Timestamp(date.getTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date checkInDate1 = format.parse(checkInDate);
			Date checkOutDate1 = format.parse(checkOutDate);
			Timestamp checkInDateTimestamp = new Timestamp(checkInDate1.getTime());
	        Timestamp checkOutDateTimestamp = new Timestamp(checkOutDate1.getTime());
	        listts.add(currentTimestamp);
	        listts.add(checkInDateTimestamp);
	        listts.add(checkOutDateTimestamp);
	        
		}catch (ParseException e) {
			  e.printStackTrace();
			}
	    return listts;
	}
	
	
	@RequestMapping(value="/confirmbooking" ,method = RequestMethod.POST)
	public JsonNode confirmBooking(@RequestBody JsonNode json) {
	return bookingClient.confirmBooking(json);
	
	}
	
	@RequestMapping(value="/getMybookings" )
	public JsonNode getMybookings(Principal principal) {
		String username=principal.getName();
		User user=userrepository.findByUserName(username);
		String usermobile=user.getUserPhone();		
	    return bookingClient.getBookingsByUser(usermobile);		
	}
	
	
	@RequestMapping(value="/changeBookingStatus/{bookingId}")
	public String changeBookingStatus(@PathVariable int bookingId) {		
	return bookingClient.changeBookingStatus(bookingId);		
	}
	
	
	@RequestMapping(value="/getAllQuestions")
	public JsonNode getAllQuestions() {	
	//return bookingClient.getAllQuestions();
	return hotelClient.getAllQuestions();
		
	}

	
	@RequestMapping(value="/addReview" ,method = RequestMethod.POST)
	public JsonNode addReview(@RequestBody JsonNode json) {
	return hotelClient.addReview(json);	
	}
	
	
	@RequestMapping(value="/getHotelReview/{hotelid}")
	public JsonNode getHotelReview(@PathVariable int hotelid) {
    System.out.println("inside gateway controller getHotelReview");
	//return bookingClient.getHotelReview(hotelid);	
    return hotelClient.getHotelReview(hotelid);
	}
	

	@RequestMapping(value="/authperson")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String authperson() {
	return "Updating inventory";	
	}
	
		
}
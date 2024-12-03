$(document).ready(function(){
	
	var slider = document.getElementById("priceRange");
    var output = document.getElementById("priceValue");
    output.innerHTML = slider.value;
    slider.oninput = function(){
	output.innerHTML = this.value;
    }
                            
    var inventory=0;
    var bookId;
	var hoteId;
	var mobile;
	

    
                            
    $("#searchBtn").click(function(){	
	var searchtext = $("#searchLocation").val();
	
	
	 $.ajax({
		type : "GET",
		url : "http://localhost:8282/GetHotelsBytext/"+searchtext,
		success: function(result){
			$("#tblHotels tr").not('.hotelheader').remove();
			$(result).each(function() {	
			   var hotel = $(this);
			   
	           $("#tblHotels").append("<tr><td style=display:none>"+hotel[0].hotelId+ "</td>"+
	           "<td><img src='"+hotel[0].imageURL +"' width='150' height='100' ></td>"+
	           "<td><a  class='openlink' href='#'>" +  hotel[0].hotelName + "</a> </td>"+
	           "<td>"+hotel[0].starRating+" Star </td><td>"+hotel[0].mobile+"</td>"+
	           "<td><a  class='reviewlink' href='#'>Reviews</a></td><td>"+Math.round(hotel[0].rating)+" Rating </td> </tr>")
			});
		},
		error : function(e) {
			console.log("ERROR: ", e);
		}
		});	
	});
	
	$("#tblHotels").on('click','.reviewlink',function(){
		
		var hotelid=parseInt($(this).parent().parent().find("td:eq(0)").text());
		$.ajax({
			type : "GET",
			url : "http://localhost:8282/getHotelReview/"+hotelid,
			success: function(result){				
				
				$("#showreviewmodal").modal("show");
				$("#tblshowreview tr").remove();
				
				$(result).each(function() {
				var review = $(this);						
					
						$('#tblshowreview').append(
		                '<tr><td><div><span >'+review[0].username+'                 Rating : '+review[0].ratings+'</span><br>'+
		                '------------------'+		                
		                '<p>'+review[0].comments +' </p></div></td></tr>');
		                	
		        
				});
			},	  				
			error : function(e) {
			alert("Please Login");
			console.log("ERROR: ", e);
			}
		 })
	 });
	
	
	
	$("#tblHotels").on('click','.openlink',function(){	
	
  	var searchtext=$(this).text();
  	var col1=parseInt($(this).parent().parent().find("td:eq(0)").text());
  	
  	$("#modal_hotelId").val(col1);  	
  	
	    $("#myModal").modal(
		$("#modal_hotelName").val($(this).text()),
		$("#modal_noRooms").val($("#noRooms").val()),
		$("#modal_noGuests").val($("#noGuests").val()),
		$("#modal_checkInDate").val($("#checkInDate").val()),
		$("#modal_checkOutDate").val($("#checkOutDate").val()),		
		$.ajax({
			type : "GET",
			url : "http://localhost:8282/getAllRoomType/"+searchtext,
			success: function(result){	
				$('#select_roomTypes').empty();
				$('#select_roomTypes').append( new Option('Select','Select'));
				$('#select_hotelRoom').empty();
		        $('#select_hotelRoom').append( new Option('Select','Select'));
				$.each(result, function(val, text) {
                $('#select_roomTypes').append( new Option(text,val) );
                 });                  	
			},	  				
			error : function(e) {
				$("#myModal").modal("hide");
				alert("Please Login or Create account if you do not have one");
				
				console.log("ERROR: ", e);
				
				
			}
		})
		
		
		); 
	
	});	
	
	
	
	$("#filterBtn").click(function(){
		
		var searchtext = $("#searchLocation").val();
		if(searchtext==''){
		$("#Message").modal();
                           }
	else{
	  	var rating = [];
              $.each($("input[class='star_rating form-check-input']:checked"), function(){
                  rating.push($(this).val());
              });
        var pricerange=$("#priceRange").val();
        var amenities = [];
              $.each($("input[class='hotel_amenity form-check-input']:checked"), function(){
                  amenities.push($(this).val());
              });    
            var formData = {
			"searchText":searchtext,
			"starRating" : rating,
			"priceRange": pricerange,
			"amenities": amenities	
		}
		
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "http://localhost:8282/findHotels",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				
				$("#tblHotels tr").not('.hotelheader').remove();
				
				$(result).each(function() {					
				
				var hotel = $(this);
					
				$("#tblHotels").append("<tr><td style=display:none>"+hotel[0].hotelId+ "</td><td><img src='"+hotel[0].imageURL +"' width='150' height='100' ></td>"+
				"<td><a  class='openlink' href='#'>" +  hotel[0].hotelName + "</a> </td><td>"+hotel[0].starRating+" Star </td>"+
				"<td>"+hotel[0].mobile+"</td><td><a  class='reviewlink' href='#'>Reviews</a><td>"+Math.round(hotel[0].rating)+" Rating </td></tr>")
				
				});
				
				},
				
			error : function(e) {	
				alert("Error occurred");
				console.log("ERROR: ", e);
							
			}
		});	
		
		}	
		
			});	
			
			
	$("#myModal").on( "click", "#ChkAval", function() {
   
      var hotelroomid= $("#select_hotelRoom").children("option:selected").val();
      $("#booking_hotelRoomId").val(hotelroomid);
      var hotelrid=$("#booking_hotelRoomId").val();      
      var hotelId=$("#modal_hotelId").val();
      var hotelName=$("#modal_hotelName").val();
	  var noRooms=	$("#modal_noRooms").val();
	  var noGuests=$("#modal_noGuests").val();
	  var checkInDate=$("#modal_checkInDate").val();
	  var checkOutDate=$("#modal_checkOutDate").val();
	  var roomType = $("#select_roomTypes").children("option:selected").text(); 
	  
	  
	  
	  
	  

	  
	  var formData = {
		    "hotelId":hotelId,
		    "hotelRoomId":hotelroomid,
			"hotelName":hotelName,
			"noRooms" : noRooms,
			"noGuests": noGuests,
			"checkInDate": checkInDate,
			"checkOutDate": checkOutDate,
			"roomType": roomType,
			"inventory":inventory	
		}
		
		
		
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "http://localhost:8282/chkAvlblty",
			data : JSON.stringify(formData),
			dataType : 'json',
			success : function(result) {
				if(result==false)
					alert("Please Choose different dates");
			    else {
					alert("Rooms are available. Please continue booking.")
				$("#myModal").modal("hide");	
			    $("#bookingHotelRoomModal").modal("show");
			    $("#bookingHotelRoomModal").modal(
		        $("#booking_hotelName").val(hotelName),
		        $("#booking_noRooms").val(noRooms),
		$("#booking_noGuests").val(noGuests),
		$("#booking_checkInDate").val(checkInDate),
		$("#booking_checkOutDate").val(checkOutDate),
		$("#booking_roomType").val(roomType),
		
		
		
		)
			    }
			    
			    
			    			
				},
			error : function(e) {	
				alert("Error occurred");
				console.log("ERROR: ", e);
			}
	  });
		});
		
		
		
		$('#select_roomTypes').on('change', function (e) {
       
        var roomtype = $("#select_roomTypes").children("option:selected").text();          
        var hotelid= $("#modal_hotelId").val();
        
       
        
        $.ajax({
			type : "GET",
			url : "http://localhost:8282/getHotelRoomsDetails/"+hotelid +"/"+roomtype,
			success: function(result){	
				$('#select_hotelRoom').empty();
				$('#select_hotelRoom').append( new Option('Select','Select'));
			
				$.each(result, function(val, text) {					
					var hotelRoom = $(this);
					text=hotelRoom[0].description;
					val=hotelRoom[0].hotelRoomId;
                $('#select_hotelRoom').append( new Option(text,val) );
                              
                 });
              
			},	  				
			error : function(e) {
				alert("Please Login");
				console.log("ERROR: ", e);
				
				
			}
		})
                                                          });
                                                          
            $('#select_hotelRoom').on('change', function (e) {  
				
			var hotelroomid=$("#select_hotelRoom").children("option:selected").val();
   	
				$.ajax({
			type : "GET",
			url : "http://localhost:8282/getDiscountPrice/"+hotelroomid,
			success: function(result){	
				$("#booking_discount").text(result.discount);			
				$("#booking_price").text(result.price);
				inventory=result.noRooms;
			
				
				
				
			},	  				
			error : function(e) {
				console.log("ERROR: ", e);
				
				
			}
		})
		
				
				
			});    
			
			
		 $("#Confirm").click( function() {
			 
			 const myDiv = document.querySelector("#mod_in");
             const myDivChildNodes=myDiv.childNodes;
             var guestList=[];
             var fname;
             var lname;
             var gender;
             var age;
             var count=0;
     for (var i=0;i<myDivChildNodes.length;i++) {
			
		 var node =myDivChildNodes[i];
		 var isInput = node.nodeName;
	     if(isInput=="INPUT"){
			if (node.className==="guestfname"){ 
			fname=node.value;
			count++;
			}
			if (node.className==="guestlname"){
			lname=node.value;
			count++;
			}
			if (node.className==="guestgender"){
			gender=node.value;
			count++;
			}
			if (node.className==="guestage"){
			age=node.value;
			count++;
			}
		
		
		
		 }
		 if (count==4){		 	 
		 guestList.push(new Guest(fname,lname,gender,age));
		 count=0;
		 fname="";
		 lname="";
		 gender="";
		 age="";
		 }
		
	 }
		
			
			//var mobile;	
			var currenttimestamp;
			var checkintimestamp;
			var checkouttimestamp;
			var hotelId=$("#modal_hotelId").val();
			var hotelroomid=$("#booking_hotelRoomId").val();
		    var noRooms=$("#booking_noRooms").val();
		    var noGuests=$("#booking_noGuests").val();
		    var checkInDate=$("#booking_checkInDate").val();
		    var checkOutDate=$("#booking_checkOutDate").val();
		    var roomType=$("#booking_roomType").val();
		    var discount=$("#booking_discount").text();			
			var price=$("#booking_price").text();
			
			//var date=new Date();
			//alert(date);
			//const timestamp1 = date.getTime();
			//alert(timestamp1);
		  	//var date2=new Date(checkInDate);
		  	//alert(date2);
		  	//const timestamp2 = date2.getTime();
		  	//alert(timestamp2);
			
			$.ajax({
			type : "GET",
			url : "http://localhost:8282/getUserMobile",
			success: function(result){	
			mobile=result;
			$.ajax({
		   type : "GET",
			url : "http://localhost:8282/gettimestamp/"+checkInDate +"/"+checkOutDate,
			success: function(result){	
				currenttimestamp=result[0];
				checkintimestamp=result[1];
				checkouttimestamp=result[2];
				//alert("currenttimestamp" +currenttimestamp);
				 var formData = {
			"bookedOnDate":	currenttimestamp,
			"checkInDate": checkintimestamp,
			"checkOutDate": checkouttimestamp,
			"customerMobile":mobile,
			"discount":discount,
		    "hotelId":hotelId,
		    "hotelRoomId":hotelroomid,		
			"noRooms" : noRooms,
			"price":price,
			"roomType": roomType,			
			"status":"upcoming",
			"guests":guestList
		    }
		    
		    $.ajax({
			type : "POST",
			contentType : "application/json",
			url : "http://localhost:8282/confirmbooking",
			data : JSON.stringify(formData),
			dataType : 'json',
			success: function(result){	
				alert("Your Booking is confirmed.Your confirmation number is " + result.bookingId);	
				$("#bookingHotelRoomModal").modal("hide");
				$("#modal_hotelId").val("");
				$("#booking_hotelRoomId").val("");
			    $("#booking_noRooms").val("");
			    $("#booking_noGuests").val("");
			    $("#booking_checkInDate").val("");
			    $("#booking_checkOutDate").val("");
			    $("#booking_roomType").val("");
			    $("#booking_discount").text("");			
				$("#booking_price").text("");
				
			
			},	  				
			error : function(e) {
				alert("error on confirm booking");
				console.log("ERROR: ", e);
			}
		   });  
			},	  				
			error : function(e) {
				alert("error on current timestamp");
				console.log("ERROR: ", e);
			}
		   });
		   },	  				
			error : function(e) {
				alert("error on user mobile");
				console.log("ERROR: ", e);
			}
		   }); 			 
		  });
		  
		  
	   $(".guest").click(function(){  
        
	   newRowAdd =           
	            '<label for="fname">First name:</label><br>'+
	  '<input type="text"  class="guestfname" id="fname" name="fname" ><br>'+
	  '<label for="lname">Last name:</label><br>'+
	  '<input type="text"  class="guestlname" id="lname" name="lname" ><br>' +
	  '<label for="mob">Gender:</label><br>'+
	  '<input type="text" class="guestgender" id="gender" name="gen" ><br>'+
	  '<label for="age">Age:</label><br>'+
	  '<input type="text" class="guestage" id="age" name="age"><br><br>'+
	  '------------------------------------------------------------------------------------------------------------------';
	  
	    var noGuests=$("#booking_noGuests").val();
	    for(var i=1;i<=noGuests;i++)  
	    $('#mod_in').append(newRowAdd);
	     });
     
     
            $("#bookings").click( function() {
		   
				$.ajax({
			    type : "GET",
				url : "http://localhost:8282/getMybookings" ,
				success: function(result){	
					//alert("success in bookings");
								
					$("#listof").text("List Of Bookings : ");
					$("#tblHotels tr").not('.hotelheader').remove();
					$("#tblHotels").append("<tr><th>Hotel Name</th><th>Room Type</th><th>Check In</th><th>Check Out</th><th>No of Rooms</th><th>Status</th><tr>")
				    $(result).each(function() {	
				    var booking = $(this);
				    if (booking[0].status=='upcoming')				       			       
				    $("#tblHotels").append("<tr><td style=display:none >"+booking[0].bookingId+"</td><td style=display:none >"+booking[0].hotelId+"</td><td>"+booking[0].hotelName+"</td><td><center>"+booking[0].roomType+"</center></td><td>"+booking[0].checkInDate +"</td><td>"+booking[0].checkOutDate +"</td><td><center>"+booking[0].noRooms +"</center></td><td class='status'>"+booking[0].status +"</td><td><input type='button' value='Cancel' class='cancelClass'/></td></tr>")
				    else if((booking[0].status=='completed')	) 
				    $("#tblHotels").append("<tr><td style=display:none >"+booking[0].bookingId+"</td><td style=display:none >"+booking[0].hotelId+"</td><td>"+booking[0].hotelName+"</td><td><center>"+booking[0].roomType+"</center></td><td>"+booking[0].checkInDate +"</td><td>"+booking[0].checkOutDate +"</td><td><center>"+booking[0].noRooms +"</center></td><td class='status'>"+booking[0].status +"</td><td><input type='button' value='Review' class='reviewClass'/></td></tr>")			    				   
				    else
				    $("#tblHotels").append("<tr><td style=display:none>"+booking[0].bookingId+"</td><td style=display:none >"+booking[0].hotelId+"</td><td>"+booking[0].hotelName+"</td><td><center>"+booking[0].roomType+"</center></td><td>"+booking[0].checkInDate +"</td><td>"+booking[0].checkOutDate +"</td><td><center>"+booking[0].noRooms +"</center></td><td class='status'>"+booking[0].status +"</td></tr>")
				    
				    });
				   
				    },	
				    error : function(e) {
					alert("error on my booking");
					console.log("ERROR: ", e);
				    }	    
				   					    				
				    });
				    
			    
               });
               
               
                  $("#tblHotels").on('click','.cancelClass',function(){
				   
				   
				   
				   var bookingId=$(this).parent().parent().find("td:eq(0)").text();
				   
				   var row = $(this).closest("tr").index();
				 
				   
				   
				   
				   
				   $.ajax({
			       type : "GET",
			       url : "http://localhost:8282/changeBookingStatus/"+bookingId,
			       success: function(result){
					   
				   alert("result" +result);
				   
				   $("#tblHotels tr").eq(row).find("td:eq(7)").text(result);					   
				   alert("Your Booking has been successfully cancelled");
				  
				   
			       },	  				
			       error : function(e) {
				   alert("Error in booking status change");
				   console.log("ERROR: ", e);
			       }
		           })
			     });
			     
			     
			     
			     $("#tblHotels").on('click','.reviewClass',function(){
					 
					var bookingId=parseInt($(this).parent().parent().find("td:eq(0)").text());
					
					var hotelId=parseInt($(this).parent().parent().find("td:eq(1)").text());
					 
					
					 
					 bookid=bookingId;
					 hoteid=hotelId;
					 
					
					
					 
					 $("#review_modal").modal("show");
					 $.ajax({
			         type : "GET",
			         url : "http://localhost:8282/getAllQuestions",
			         success: function(result){	
					 
					 $("#tblreview tr").not('.reviewheader').remove();
					 var i=1;
						 
					 $(result).each(function() { 
						q=$(this);	
						$('#tblreview').append(
		                '<tr><td align="left"><span  class="question">'+q[0].question+'</span></td>'+
		                '<td ><input class="rating1" type="radio" name="rating'+i+'" value="1" id="rate-1">'+
		                '<label for="rate-1">1</label>'+ 
		                '<input class="rating1" type="radio" name="rating'+i+'" value="2" id="rate-2">'+
		                '<label for="rate-2">2</label>'+ 
		                '<input class="rating1" type="radio" name="rating'+i+'" value="3" id="rate-3" ">'+
		                '<label for="rate-3" >3</label>'+ 
		                '<input  class="rating1" type="radio" name="rating'+i+'" value="4" id="rate-4" ">'+
		                '<label for="rate-4" >4</label>'+
		                '<input  class="rating1" type="radio" name="rating'+i+'" value="5" id="rate-5" ">'+
		                '<label for="rate-5">5</label></td></tr>');	
		                i++;
					 });
			         },	  				
			         error : function(e) {
				     alert("Please Login");
				     console.log("ERROR: ", e);
			         }
		             })
			     });
			     
			     
			     
			     $("#submitReview").click(function(e){
					 
					 
					
			        var rating = 0;
			        $(".rating1").each(function(){
			            if($(this).is(':checked')){
			                rating = rating + parseInt($(this).val());
			                
			               
			            }
			            else if($(this).next("input").is(':checked')){
			                rating = rating + parseInt($(this).val());
			                
			            }
			            else if($(this).next("input").next("input").is(':checked')){
			                rating = rating + parseInt($(this).val());
			                
			            }
			            else if($(this).next("input").next("input").next("input").is(':checked')){
			                rating = rating + parseInt($(this).val());
			                
			            }
			            else if($(this).next("input").next("input").next("input").next("input").is(':checked')){
			                rating = rating + parseInt($(this).val());
			                
			            }
			            
			           });
				        rating=rating/4;
				        var textarea=$("#reviewtext").val();
				       
				        
						
						
						$.ajax({
							type : "GET",
							url : "http://localhost:8282/getUser",
							success: function(result){									
							
							
							var formData = {
								"comments":textarea,
								"ratings": rating,
								"hotelId": hoteid,
								"bookingId":bookid,
								"username":result
								}							 
					        $.ajax({
								type : "POST",
								contentType : "application/json",
								url : "http://localhost:8282/addReview",
								data : JSON.stringify(formData),
								dataType : 'json',
								success : function(result) {
								alert("Thank you for you review!!!");
								
								$("#review_modal").modal("hide");
								$("#reviewtext").val("");
								},
								error : function(e) {	
								alert("Error in add review");
								console.log("ERROR: ", e);
								}
						    });
						    },	
						    error : function(e) {	
							    alert("Error in username in review");
								console.log("ERROR: ", e);
					    	}
					    	});
		
			        });
					 
               
			    
			    });
			    
	     
          
    
    
               
               
               
  function Guest( fname,  lname,  gender,  age)
  {
	this.firstName=fname;
	this.lastName=lname;
	this.gender=gender;
	this.age=age;	
  }
              
	
	
	
	 
	
	
			
	
	
		

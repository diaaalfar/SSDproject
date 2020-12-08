package ssdPackage;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;


@Path("/class")

public class Marhaba {

	
	static Logger logger= Logger.getLogger(Marhaba.class);
	static JsonWebKey myJwk =null;		
	
	
	public static Map<String, Grades> gradesMap= new HashMap<>(); 	
	private static Map<String, Users> usersMap= new HashMap<>(); 	


	
	//-------------------------------------------------------------------------------------------------
	
	
	
	public boolean isAlpha(String name) {
	    char[] chars = name.toCharArray();

	    for (char c : chars) {
	        if(!Character.isLetter(c)) {
	            return false;
	        }
	    }

	    return true;
	}
	
	
	
	//-------------------------------------------------------------------------------------------------
	

	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response visitclass(){
		logger.info("Visiting the main Class. "+Status.OK.getStatusCode());
		return Response.status(Status.OK.getStatusCode()).entity("Welcome to the Main Class.").build();}


	
	@GET
	@Path("/welcome")
	@Produces(MediaType.TEXT_PLAIN)
	public Response welcome(){
		logger.info("Showing the welcoming message. "+Status.OK.getStatusCode());
		return Response.status(Status.OK.getStatusCode()).entity("Welcome to the project of SSD \n "
				+ "Which is the practical project to apply the REST web services.").build();}
	
	@GET
	@Path("/info")
	@Produces(MediaType.TEXT_PLAIN)
	public Response into(){
		logger.info("Printing the information of the project. "+Status.OK.getStatusCode());
		return Response.status(Status.OK.getStatusCode()).entity("SSD - RESTful Web services Info:\n"
				+ "Representational State Transfer (REST) is an architectural style that specifies constraints,\n"
				+ "such as the uniform interface, that if applied to a web service induce desirable properties, \n"
				+ "such as performance, scalability, and modifiability, that enable services to work best on the Web.").build();}
	
	
	@GET
	@Path("/timedate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response timedate() {
		    LocalDateTime myTimeDate = LocalDateTime.now();
		    DateTimeFormatter myTimeDateFormated = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    String formattedTimeDate = myTimeDate.format(myTimeDateFormated);
		    logger.info("Printing the Time and Date. "+Status.OK.getStatusCode());
		    return Response.status(Status.OK.getStatusCode()).entity("Current Time and Date is: " + 
		    formattedTimeDate).build();
		}
	
	@GET
	@Path("/contactus")
	@Produces(MediaType.TEXT_PLAIN)
	public Response contactus() {
		    logger.info("Visiting Contact Us. "+Status.OK.getStatusCode());
		    return Response.status(Status.OK.getStatusCode()).entity("Name: Ameed.  Email: ameedabughazal@gmail.com\n" + 
		    		"Name: Dia.  Email: Diaa.i.alfar@gmail.com\n" + "Name: Yousef.  Email: yousef.tyh@gmail.com").build();
		}
	
	
	
	//-------------------------------------------------------------------------------------------------
	
	
	
	@GET
	@Path("/printgrades")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Grades> printstudents() {
			return gradesMap;
	}
	
	
	@POST
	@Path("/addgrade")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String addgrade(Grades grade)
	{
		try {
			//check if the type of the student name input is valid not boolean, not empty, does not contain any numbers
			if(grade.getStudent()!=null&&(grade.getStudent().matches(".*\\d.*")||
					grade.getStudent().equalsIgnoreCase("true")||grade.getStudent().equalsIgnoreCase("false")))
				return "Invalid Student Name Type \n Hint: Username could not contain any numbers.";
			
			//check if the student name is valid, not empty not {}, length <=22 characters, and it's only alphabetical characters  
			if(grade.getStudent()!=null&&!grade.getStudent().equals("")&&grade.getStudent().length()<=22)
			{
				
				//check if the student year is valid, not empty not, value between 1-6
				if(grade.getYear()!=0&&grade.getYear()>0&&grade.getYear()<=6)
				{
					
					//check if the student year is valid, an integer with 8 digits 
					if(grade.getStudent_id()>=10000000&&grade.getStudent_id()<=99999999) {
						
						//check if the student grade is valid, double value from 0 to 100 
						if(grade.getGrade()>=0.0&&grade.getGrade()<=100.0) 
						{
							//if there is no such student grade create a new one 
							if (gradesMap.get(grade.getStudent())==null) {
								Grades newGrade = new Grades();
								newGrade.setStudent(grade.getStudent());
								newGrade.setYear(grade.getYear());
								newGrade.setStudent_id(grade.getStudent_id());
								newGrade.setGrade(grade.getGrade());
								gradesMap.put(newGrade.getStudent(), newGrade);

								return "New student grade has been added.\n";
								}
						}
						else
							return "Invalid Student Grade.\n Hint: Grade must be double value between 0.0-100.0.";
					}
					else
						return "Invalid Student ID number.\n Hint: ID must have 8 digits number.";	
				}
				else
					return "Invalid Student Year.\n Hint: years must be from 1-6";
			}		
			else 
				return "Invalid Student Name.\n Name should be less than 22 characters and does not contain any numbers or symbols";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "An Error Occared... Please Check Your Input \n Hint: Your input could not be empty.";
		}
		return "Student Name is already graded you can use PUT to modify the grade.";
	}
	
		

	//modifying an existing user in the Users map
	@PUT
	@Path("/modifygrade/{student}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String modifygrade(@PathParam("student") String std, Grades xgrade) {
		gradesMap.put(std, xgrade);
		return "Student grade and info has been modified.";
		}
	
	
	//deleting an existing user from the Users map
	@DELETE
	@Path("/deletegrade/{student}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deletegrade(@PathParam("student") String student) {
		gradesMap.remove(student);
		return "Student grade and info has been removed.";
		}
	
	
	
	//-------------------------------------------------------------------------------------------------

	
	
	//printing the users map with the apikey 
	@GET
	@Path("/secured/printusersmap")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Users> printusersmap() {
		
		logger.info("Printing the Users Map. "+Status.OK.getStatusCode());
		return usersMap;
	}
	
	
	//adding new user to the Users map and print it's apikey
	@POST
	@Path("/secured/adduser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response adduser(Users myUser)
	{
		try {
			//check if the type of the username input is valid not boolean, not empty, does not contain any numbers
			if(myUser.getUser()!=null&&(myUser.getUser().matches(".*\\d.*")||
					myUser.getUser().equalsIgnoreCase("true")||myUser.getUser().equalsIgnoreCase("false")))
			{
				logger.info("Invalid Content Type. "+Status.UNSUPPORTED_MEDIA_TYPE.getStatusCode());
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE)
						.entity("Invalid Content Type \n Hint: Username could not contain any numbers.").build();
			}
			
			//check if the username is valid, not empty not {}, length <=10 characters, and it's only alphabetical characters  
			if(myUser.getUser()!=null&&!myUser.getUser().equals("")&&myUser.getUser().length()<=10&&isAlpha(myUser.getUser()))
			{
				
				//check if the password is valid, not empty not, length between 6 and 16 characters
				if(myUser.getPass()!=null&&myUser.getPass().length()>5&&myUser.getPass().length()<=16)
				{
		
					//if there is no such user create a new one with the generated apikey
					if (usersMap.get(myUser.getUser())==null) {
						UUID apikey = UUID.randomUUID();
						Users newUser = new Users();
						newUser.setUser(myUser.getUser());
						newUser.setPass(myUser.getPass());
						newUser.setApiKey(apikey.toString());
						usersMap.put(newUser.getUser(), newUser);
						
						logger.info("New user has been added: "+myUser.getUser()+"  "+Status.CREATED.getStatusCode());
						return Response.status(Status.CREATED.getStatusCode()).entity("New user has been added.\n"+
						"Your Apikey is:\n" + apikey.toString()).build();
						}
				}
				else
				{
					logger.info("Invalid Password. "+Status.BAD_REQUEST.getStatusCode());
					return Response.status(Status.BAD_REQUEST.getStatusCode()).entity("Invalid Passowrd.").build();}
				
			}
			else {
				logger.info("Invalid Username. "+Status.BAD_REQUEST.getStatusCode());
				return Response.status(Status.BAD_REQUEST.getStatusCode()).entity("Invalid Username.").build();}
			
		} 
		
		catch (Exception e) {
			e.printStackTrace();
			logger.info("Empty login content. "+Status.BAD_REQUEST.getStatusCode());
			return Response.status(Status.BAD_REQUEST.getStatusCode()).entity("An Error Occared... Please Check Your Input \n"
					+ "Hint: Your input could not be empty.").build();
		}
		
		logger.info("Repeted Username: "+myUser.getUser()+"  "+Status.CONFLICT.getStatusCode());
		return Response.status(Status.CONFLICT.getStatusCode()).entity("You Used a Repeted Username.").build();
	}
	
	
	
	//check if the user password combination is correct with the corresponding apikey
	@POST
	@Path("/secured/userlogin")
	@Produces(MediaType.TEXT_PLAIN)
	public Response userlogin (Users myUser,  @HeaderParam("apikey") String apikey) {

		if (usersMap.get(myUser.getUser())!=null)
		if (usersMap.get(myUser.getUser()).getApiKey().equals(apikey))
		
		{
			logger.info("Successfull login to the system by: "+myUser.getUser()+"  "+Status.OK.getStatusCode());
			return Response.status(Status.OK.getStatusCode()).entity("Welcome "+myUser.getUser()+" to our system.").build();
		}

		logger.info("Unauthorized attempt to loging to the system. "+Status.UNAUTHORIZED.getStatusCode());
		return Response.status(Status.UNAUTHORIZED.getStatusCode()).entity("Unauthorized Login or Wrong Username/Password Compination.").build();
	}
	
	
	//modifying an existing user in the Users map
	@PUT
	@Path("/secured/modifyuser/{user}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String modifyuser(@PathParam("user") String user, Users myUser) {
		UUID apikey = UUID.randomUUID();
		myUser.setApiKey(apikey.toString());
		usersMap.put(user, myUser);
		logger.info("The user has been modified. "+Status.OK.getStatusCode());
		return "User has been modified.";
		}
	
	
	//deleting an existing user from the Users map
	@DELETE
	@Path("/secured/deleteuser/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteuser(@PathParam("user") String user) {
		usersMap.remove(user);
		logger.info("The user has been deleted. "+Status.OK.getStatusCode());
		return "User has been Removed.";
		}


	
	//-------------------------------------------------------------------------------------------------
	
	
	
	
	@Path("/getJWT")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response
	authenticateCredentials(@HeaderParam("username") String username, @HeaderParam("password") String password)
	throws JsonGenerationException,JsonMappingException, IOException {
		Users user = new Users();
		user.setUser(username);
		user.setPass(password);
		RsaJsonWebKey jwk = null;
		try {
		jwk = RsaJwkGenerator.generateJwk(2048);
		jwk.setKeyId("1");
		myJwk=jwk;} 
		catch (JoseException e) { e.printStackTrace(); }
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("uca");
		claims.setExpirationTimeMinutesInTheFuture(10);
		claims.setGeneratedJwtId();
		claims.setIssuedAtToNow();
		claims.setNotBeforeMinutesInThePast(2);
		claims.setSubject(user.getUser());
		claims.setStringListClaim("roles", "basicRestUser");
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKeyIdHeaderValue(jwk.getKeyId());
		jws.setKey(jwk.getPrivateKey());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
		String jwt = null;
		try { jwt = jws.getCompactSerialization();} 
		catch (JoseException e) {System.out.println (e);}
		catch(Exception e) {Response.status(404).entity(jwt).build();}
		user.setApiKey(jwt);
		return Response.status(200).entity(jwt).build();
	}
	
	
	
	@POST
	@Path("/verifyJWT")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response testJW (@HeaderParam("token") String token, String	myName)
	throws JsonGenerationException,
	JsonMappingException, IOException {
		JsonWebKey jwk = myJwk;
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
		.setRequireExpirationTime()
		.setAllowedClockSkewInSeconds(30)
		.setRequireSubject()
		.setExpectedIssuer("uca")
		.setVerificationKey(jwk.getKey())
		.build();
		try {
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			System.out.println("JWT validation succeeded! " + jwtClaims);} 
		catch (InvalidJwtException e) 
		{
			return Response.status(Status.FORBIDDEN.getStatusCode()).entity("Forbidden").build();
		}
		return Response.status(Status.OK.getStatusCode()).entity("Hello "+myName).build();
		}
	
	
	
	//-------------------------------------------------------------------------------------------------
	
	
	
	
	


}


	







	
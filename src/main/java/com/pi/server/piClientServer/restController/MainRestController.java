package com.pi.server.piClientServer.restController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pi.server.piClientServer.Entity.Client;
import com.pi.server.piClientServer.Entity.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class MainRestController {
	
	@Autowired
	private ClientRepository repository;
	
	@GetMapping("/getAll")
	public List<Client> getAll(){
		return repository.findAll();
	}
	
	@GetMapping("/getClient")
	public Client getClientByToken(@RequestParam ("token") String token){
		Client c = repository.findByToken(token);
		return c;
	}	

	@PostMapping("/createClient")
	public Map<String,String> insert(@RequestBody String payload){
		JsonObject jsonOb = new Gson().fromJson(payload, JsonObject.class);
		String id = jsonOb.get("id").getAsString();
		String name = jsonOb.get("name").getAsString();
		String birthday = jsonOb.get("birthday").getAsString();
		String phone = jsonOb.get("phone").getAsString();
		String email = jsonOb.get("email").getAsString();
		String address1 = jsonOb.get("address1").getAsString();
		String address2 = jsonOb.get("address2").getAsString();
		String address3 = jsonOb.get("address3").getAsString();
		
		boolean r =repository.insert(
				 id
				, name
				, birthday
				, phone
				, email
				, address1
				, address2
				, address3
				);
		String result = (r)? "Y" :"N";
		Map<String,String> respMap = new HashMap<>();
		respMap.put("result", result);
		return respMap;
	}
	
	@PostMapping("/updateClient")
	public Map<String,String> update(@RequestParam ("id") String id,@RequestBody String payload) throws Exception{
		
		String name = null, phone = null,email = null,address1 = null,address2 = null, address3 = null;
		
		JsonObject jsonOb = new Gson().fromJson(payload, JsonObject.class);
		JsonElement nameJE =  jsonOb.get("name");
		if(nameJE != null) {
			name = nameJE.getAsString();
		}
		JsonElement phoneJE =  jsonOb.get("phone");
		if(phoneJE != null) {
			phone = phoneJE.getAsString();
		}
		JsonElement emailJE =  jsonOb.get("email");
		if(emailJE != null) {
			email = emailJE.getAsString();
		}
		JsonElement address1JE =  jsonOb.get("address1");
		if(address1JE != null) {
			address1 = address1JE.getAsString();
		}
		JsonElement address2JE =  jsonOb.get("address2");
		if(address2JE != null) {
			address2 = address2JE.getAsString();
		}
		JsonElement address3JE =  jsonOb.get("address3");
		if(address3JE != null) {
			address3 = address3JE.getAsString();
		}
		String result = (repository.update(id,name,phone,email,address1,address2,address3))? "Y" :"N";
		Map<String,String> respMap = new HashMap<>();
		respMap.put("result", result);
		return respMap;
	}
}

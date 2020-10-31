package com.pi.server.piClientServer.Entity;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class ClientRepository {
	@Autowired
	EntityManager em;
	
	@Transactional 
	public boolean insert(
			String id
			,String name_
			,String birthday
			,String phone
			,String email
			,String address1
			,String address2
			,String address3
			) {
		try {
			em.createNativeQuery("insert into client(token,id,name,birthday,phone,email,address1,address2,address3) values (?,?,?,?,?,?,?,?,?) ")
			.setParameter(1, UUID.randomUUID().toString().replace("-", ""))
			.setParameter(2, id)
			.setParameter(3, name_)
			.setParameter(4, birthday)
			.setParameter(5, phone)
			.setParameter(6, email)
			.setParameter(7, address1)
			.setParameter(8, address2)
			.setParameter(9, address3)
			.executeUpdate();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		 
	}
	
	@Transactional 
	public boolean update(
			String id
			,String name_
			,String phone
			,String email
			,String address1
			,String address2
			,String address3
			) {
		StringBuilder sb = new StringBuilder(" update client ");
		
		
		if(StringUtils.hasText(name_)) {
			sb.append(" set name = :name ,");
		}
		if(StringUtils.hasText(phone)) {
			sb.append(" set phone = :phone ,");
		}
		if(StringUtils.hasText(email)) {
			sb.append(" set email = :email ,");
		}
		if(StringUtils.hasText(address1)) {
			sb.append(" set address1 = :address1 ,");
		}
		if(StringUtils.hasText(address2)) {
			sb.append(" set address2 = :address2 ,");
		}
		if(StringUtils.hasText(address3)) {
			sb.append(" set address3 = :address3 ,");
		}
		if(",".equals(String.valueOf(sb.charAt( sb.length() -1)))) {
			sb.deleteCharAt(sb.length() -1);
		}
		sb.append(" where id = :id ");
		
		Query query = em.createNativeQuery(sb.toString());
		if(StringUtils.hasText(name_)) {
			query.setParameter("name", name_);
		}
		if(StringUtils.hasText(phone)) {
			query.setParameter("phone", phone);
		}
		if(StringUtils.hasText(email)) {
			query.setParameter("email", email);
		}
		if(StringUtils.hasText(address1)) {
			query.setParameter("address1", address1);
		}
		if(StringUtils.hasText(address2)) {
			query.setParameter("address2", address2);
		}
		if(StringUtils.hasText(address3)) {
			query.setParameter("address3", address3);
		}
		query.setParameter("id", id);
		try {
			query.executeUpdate();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Client findByToken(String token) {
		return em.find(Client.class, token);
	}
	
	@SuppressWarnings("unchecked")
	public List<Client> findAll() {
		Query query = em.createNativeQuery("select * from client ", Client.class);
		List<Client> clients = (List<Client>) query.getResultList();
		return clients;
	}
}

package com.estsoft.jpabookmall;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.estsoft.jpabookmall.domain.Book;

public class App {
	public static void main( String[] args ){
		
		//manager factory 생성
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabookmall"); 
        //manager 생성
		EntityManager em = emf.createEntityManager();
		//get transaction
		EntityTransaction tx = em.getTransaction();
		//begin transaction
		tx.begin();
		
		try{
			// <---- business logic ---->
			//insertLogic(em);
			//insertAndUpdate(em);
			//findOneLogic(em);
		}catch(Exception e){
			tx.rollback();
		}
			
		//tx commit
		tx.commit();
		
		
		em.close();
		emf.close();
    }
	
	public static void insertLogic(EntityManager em){
		Book book = new Book();
		book.setTitle("god of java");
		book.setPrice(20000L);
		
		em.persist(book);
	}
	
	public static void insertAndUpdate(EntityManager em){
		Book book = new Book();
		book.setTitle("routine of java");
		book.setPrice(30000L);
		
		em.persist(book);		
	}
	
	public static void findOneLogic(EntityManager em){
		Book book = em.find(Book.class, 2L);
		System.out.println(book);
		
		book.setTitle("conquer java");
	}
	
	public static void findListLogic(EntityManager em){
		//(JPQL)
		TypedQuery<Book> query = em.createQuery("select m from Book m", Book.class);
		List<Book> list = query.getResultList();
		for( Book book : list){
			System.out.println(book);
		}
	}
	
	public static void findAndRemoveLogic(EntityManager em){
		Book book = em.find(Book.class, 1L);
		em.remove(book);
	}
}

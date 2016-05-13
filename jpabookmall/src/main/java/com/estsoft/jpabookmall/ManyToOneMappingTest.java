package com.estsoft.jpabookmall;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.estsoft.jpabookmall.domain.Book;
import com.estsoft.jpabookmall.domain.Category;

public class ManyToOneMappingTest {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("jpabookmall");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try {
			// testSave(em);
			testInsertCategories(em);
			testInsertBooks(em);
			testFindBook(em);
			testFindBook2(em);

			testUpadateBook(em);
			testRemoveRelation(em);
			testRemoveCategory(em);
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		// tx commit
		tx.commit();

		em.close();
		emf.close();
	}
	
	public static void testRemoveCategory(EntityManager em) {
		Category cat = em.find(Category.class, 4L);
		em.remove(cat);
	}
	
	public static void testRemoveRelation(EntityManager em) {
		Book b = em.find(Book.class, 1L);
		b.setCategory(null);
	}
	
	public static void testUpadateBook2(EntityManager em) {
		Category category = new Category();
		category.setName("IT");
		em.persist( category );
		
		TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
		List<Book> list = query.getResultList();
		for (Book book : list) {
			book.setCategory(category);
		}
	}
	public static void testUpadateBook(EntityManager em) {
		Category category = em.find(Category.class, 2L);
		Book book = em.find(Book.class, 1L);
		
		book.setCategory(category);
	}
	public static void testFindBook2(EntityManager em) {
		//PSQL JOIN
		String psql = "select b from Book b join b.category c where b.title = :title";
		TypedQuery<Book> q = em.createQuery(psql, Book.class);
		q.setParameter("title", "Effective Java");
		List<Book> list = q.getResultList();
		for (Book book : list) {
			System.out.println(book);
		}
	}

	public static void testFindBook(EntityManager em) {

		Book book = em.find(Book.class, 1L);
		System.out.println(book);

		Category category = book.getCategory();
		System.out.println(category);
	}

	public static void testSave(EntityManager em) {

		Category category = new Category();
		category.setName("programming");
		em.persist(category); // 따로 영속화 해주어야 한다

		Book book = new Book();
		book.setTitle("god of java");
		book.setPrice(20000L);
		book.setCategory(category);
		em.persist(book);
	}

	public static void testInsertBooks(EntityManager em) {

		Category cat1 = em.find(Category.class, 1L);

		Book b1 = new Book();
		b1.setTitle("Effective Java");
		b1.setPrice(200L);
		b1.setCategory(cat1);

		em.persist(b1);

		Category cat2 = em.find(Category.class, 2L);

		Book b2 = new Book();
		b2.setTitle("Spring in Action");
		b2.setPrice(234L);
		b2.setCategory(cat2);

		em.persist(b2);

		Category cat3 = em.find(Category.class, 3L);

		Book b3 = new Book();
		b3.setTitle("The C programming language");
		b3.setPrice(456L);
		b3.setCategory(cat3);

		em.persist(b3);

	}

	public static void testInsertCategories(EntityManager em) {

		Category cat1 = new Category();
		cat1.setName("java programming");
		em.persist(cat1);

		Category cat2 = new Category();
		cat2.setName("Spring Framework");
		em.persist(cat2);

		Category cat3 = new Category();
		cat3.setName("C programming");
		em.persist(cat3);

	}

}
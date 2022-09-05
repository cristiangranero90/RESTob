package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entity.Book;
import com.example.obrestdatajpa.repositories.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;


@SpringBootApplication
public class ObRestDatajpaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);

		//Crear un libro
		Book book1 = new Book(null, "Homo Deus", "Yuval Noah", 400, 99.99, LocalDate.of(2018, 12, 1), true);
		Book book2 = new Book(null, "Homo Sapiens", "Yuval Noah", 400, 19.99, LocalDate.of(2013, 11, 1), true);
		//Guardar un libro
		repository.save(book1);
		repository.save(book2);
		//Recuperar todos los libros
		System.out.println("Num de libros guardados hasta el momento: " + repository.findAll().size());

		//Borras un libro
		//repository.deleteById(1L);

		System.out.println("Num de libros luego de eliminar: " + repository.findAll().size());
	}

}

package br.com.saraiva.dao.jpa;

import java.util.List;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.saraiva.domain.Livro;

/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */

@SuppressWarnings("unchecked")
@Repository
public class LivroRepository  extends RepositoryGeneric<Livro, Long>{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	 
	
	public LivroRepository() {
		super();
		clazz = Livro.class;
	}
	
	public List<Livro> findLivrosByPriceAndLimit(Double price, Integer limit ) {
		  log.info("EXEC.LivroRepository.findLivrosByPrice" );
		  ////////// 
		  List<Livro> livros = null;
		  String jql = "SELECT l FROM Livro l where l.price < ?1 "; 
		  Query query = this.em.createQuery(jql) ;
		  //ADICIONANDO LIMITES NA CONSULTA //
		  query.setFirstResult(0);
		  query.setMaxResults(limit);
		  query.setParameter(1, price);
		  livros = query.getResultList();
		  return livros;
	  }
	
	public List<Livro> findLivrosByPrice(Double price) {
		  log.info("EXEC.LivroRepository.findLivrosByPrice" );
		  ////////// 
		  List<Livro> livros = null;
		  String jql = "SELECT l FROM Livro l where l.price < ?1 "; 
		  Query query = this.em.createQuery(jql) ;
		  query.setFirstResult(0); // CONSULTA PELO 1o REGISTRO // 
		  query.setParameter(1, price);
		  livros = query.getResultList();
		  return livros;
	  }
	
	public List<Livro> findLivrosByLimit(Integer limit ) {
		  log.info("EXEC.LivroRepository.findLivrosByPrice" );
		  ////////// 
		  List<Livro> livros = null;
		  String jql = "SELECT l FROM Livro l "; 
		  Query query = this.em.createQuery(jql) ;
		  //ADICIONANDO LIMITES NA CONSULTA //
		  query.setFirstResult(0);
		  query.setMaxResults(limit);
		  livros = query.getResultList();
		  return livros;
	  }
	
    

	public List<Livro> findAll() {
		 log.info("EXEC.LivroRepository.findAll" );
		 ////
		 String q = "SELECT l FROM Livro l  ";
		 Query query = this.em.createQuery(q) ;
		 List<Livro> livros = query.getResultList();
		 return livros;
    }
	
	@Transactional
	public void delete(Long sku) {
		log.info("EXEC.delete.delete" );
		String q = "DELETE FROM Livro l where l.sku = ?1 ";
		Query query = this.em.createQuery(q) ;
		query.setParameter(1, sku);
		query.executeUpdate();
		
    }
	
	

}

package br.com.saraiva.dao.jpa;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */

public abstract class RepositoryGeneric<T , ID extends Serializable>{
	
	@Autowired
	EntityManager em;
	
	 public Class<T> clazz;
	 
    
    @Transactional
    public void save(T t ) {
        em.merge(t);
    }
    
    
    @Transactional
    public T findOne(Long sku ) {
    	return em.find( clazz, sku);
    }
	
	
	
}

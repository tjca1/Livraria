package br.com.saraiva.domain;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */
@Entity
@Table(name = "tb_livros")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Livro {
	 @Transient
	 private final Logger log = LoggerFactory.getLogger(this.getClass());
	 

    @Id
    private Long sku;
    
    @Column(nullable = false)
    private String name;

    @Column()
    private String brand;

    @Column()
    private double price;

	public Long getSku() {
		return sku;
	}

	public void setSku(Long sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public void mockLivro(){
		   Random r = new Random();
		   setSku(r.nextInt() + 0l);
		   setName("Mock Name");
		   setBrand("Mock Brand");
		   setPrice(85.00);
		   log.info("MOCKS ENTITY LIVRO" + toString());
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (sku ^ (sku >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (sku != other.sku)
			return false;
		return true;
	}

    
    
    
    
  
}

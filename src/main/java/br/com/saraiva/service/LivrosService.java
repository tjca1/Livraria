package br.com.saraiva.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.saraiva.dao.jpa.LivroRepository;
import br.com.saraiva.domain.Livro;
import br.com.saraiva.dto.StatusResponse;
import br.com.saraiva.exeception.ResourceNotFoundException;
import br.com.saraiva.util.Msgs;
import br.com.saraiva.util.StringUtil;

/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Service
public class LivrosService {

    private static final Logger log = LoggerFactory.getLogger(LivrosService.class);

    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private StatusResponse statusResponse ;

    public LivrosService() {
    }

    public StatusResponse createLivro(Long sku) {
    	
    	String variavel_ambiente_pod_container_docker_os = "https://api.saraiva.com.br/sc/produto/pdp/";
    	Map<String,Object> mLivroWs = wsSaraiva(variavel_ambiente_pod_container_docker_os+sku+"/0/0/0/");
    	Livro livroWs = (Livro) mLivroWs.get("livro");
    	Integer categoryId = (Integer) mLivroWs.get("categoryId");
    	if(StringUtil.isNull(categoryId)) {
    		categoryId = -1;
    	}
    	if(categoryId == 1565){
    		/*SIGNIFICA QUE O LIVRO NÃO EXISTE NA BASE, AINDA...*/
    		livroRepository.save(livroWs); // GRAVA O LIVRO NA BASE //
    		statusResponse.setMessage(Msgs.LIVRO_INCLUDED_SUCCESS + "Nome do Livro:" + livroWs.getName());
            statusResponse.setStatus(HttpStatus.CREATED.value());
            statusResponse.setTimestamp(new Date().getTime());
		}else if(categoryId != -1) {
			String catInvalid = "Categoria: "+categoryId + " invalida, por favor procure o sku com a categoria(1565) correta !";
			log.info(catInvalid);
			statusResponse.setMessage(catInvalid);
			statusResponse.setStatus(401);
            statusResponse.setTimestamp(new Date().getTime());
		}
    	else {
    		log.info(Msgs.LIVRO_NO_INCLUDED_SUCCESS);
    		statusResponse.setMessage(Msgs.LIVRO_NO_INCLUDED_SUCCESS);
    		statusResponse.setStatus(201);
            statusResponse.setTimestamp(new Date().getTime());
		}
    	
    	return statusResponse;
    }
    
     public Livro selectLivro(Long sku) {
    	Livro livro = livroRepository.findOne (sku);
    	return livro;
    }
    

    public List<Livro> selectLivrosByPriceAndLimit(Double price, Integer limit) {
    	 List<Livro> livros = null;
    	 livros = livroRepository.findLivrosByPriceAndLimit(price, limit);
    	 return livros; 
    }
    
    public List<Livro> selectLivrosByPrice(Double price) {
	   	 List<Livro> livros = null;
	   	 livros = livroRepository.findLivrosByPrice(price);
	   	 return livros; 
    }
    
    public List<Livro> selectLivrosByLimit(Integer limit) {
	   	 List<Livro> livros = null;
	   	 livros = livroRepository.findLivrosByLimit(limit);
	   	 return livros; 
    }
    
    
    public List<Livro> selectAll() {
    	List<Livro> livros = (List<Livro>) livroRepository.findAll();
    	return livros; 
    }
    
    
    public void deleteLivro(Long sku) {
    	livroRepository.delete(sku);
    }
    
    
    
    
    private Map<String,Object> wsSaraiva(String url) {
    	    Map<String,Object> rEsult = new HashMap<String,Object>();
    	    Integer categoryId = null;
    	    Livro livro = new Livro();
	    	try {
		    	/////////////////////////////////////////////////////////////////////////////////////
		    	URL obj = new URL(url);
		    	HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();
		    	conn.setDoInput(true);
		    	//////////////////////////////////////////////////////////////////////////////////////
		    	BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String lnJson = "";
		        StringBuffer response = new StringBuffer();
		        int codStatus = conn.getResponseCode();
		        if(codStatus == 200) {
		        	while ((lnJson = in.readLine()) != null) 
		            { 
		                 response.append(lnJson); 
		            }
		        }else {
		            in.close();
		        	throw new ResourceNotFoundException("Erro:"+codStatus+" - url:"+url);
		        }
		        in.close();
		        
				Map<String,Object> result = new ObjectMapper().readValue(response.toString(), HashMap.class);
				if(null != result) {
				    categoryId = (Integer)result.get("categoryId");
					livro.setSku(StringUtil.toLong((String)result.get("sku")));
					livro.setName((String)result.get("name"));
					livro.setBrand((String)result.get("brand"));
				}
				///////////////////////////////////////////////////////////////////////////////////////////
				Map price = (Map)result.get("price");
				if(null != price) {
					Map bestPrice =  (Map) price.get("bestPrice");
					if(null != bestPrice) {
						String value =  (String) bestPrice.get("value");
							livro.setPrice(StringUtil.toDouble(value));
					}else {
						livro = null;
					}
	    	    }else {
					livro = null;
				}
	    	}catch(IOException e) {
	    		throw new ResourceNotFoundException(e);
	    	}

			rEsult.put("livro", livro);
			rEsult.put("categoryId", categoryId);
			return rEsult;
    }
    
    
}

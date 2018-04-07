package br.com.saraiva.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.saraiva.domain.Livro;
import br.com.saraiva.dto.StatusResponse;
import br.com.saraiva.service.LivrosService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author Thiago Araujo  - tjca1@hotmail.com - 07/04/2018
 *
 */

@RestController
@RequestMapping(value = "/book")
public class LivrosController extends AbstractRestHandler {
	private final Logger log = LoggerFactory.getLogger(this.getClass());


	
    @Autowired
    private LivrosService livrosService;
    @Autowired
    private StatusResponse statusResponse ;
    
    /****EXCLUIR******/
    @RequestMapping(value = "/{sku}",
            method = RequestMethod.DELETE,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "deleta 1 livro", notes = "localizado na base pelo cl sku.")
    public
    @ResponseBody
    void deleteLivro(@ApiParam(value = "id do livro.", required = true)
                             @PathVariable("sku") Long sku,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
    	log.info("LivrosController.deleteLivro");
        this.livrosService.deleteLivro(sku);
        /////////////////////////////////////////////////////////////////////////////////////////
        /*
        statusResponse.setMessage(Msgs.LIVRO_DELETED_SUCCESS);
        statusResponse.setStatus(HttpStatus.CREATED.value());
        statusResponse.setTimestamp(new Date().getTime());
        statusResponse.setPath(request.getServletPath());
        return statusResponse;
        */
    }
    
    
    
    /****ADICIONA******/
    @RequestMapping(value = "/{sku}",
            method = RequestMethod.POST,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "incluir 1 livro", notes = "localizado na base pelo cl sku.")
    public
    @ResponseBody
    StatusResponse  insertLivro(@ApiParam(value = "id do livro.", required = true)
				    @PathVariable("sku") Long sku,
				    HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	log.info("LivrosController.insertLivro");
        //////////////////////////////////////////////////////////////////////////////////////////
	    	statusResponse =  this.livrosService.createLivro(sku);
	        statusResponse.setPath(request.getServletPath());
        if(statusResponse.getStatus() != 401)
        	statusResponse.setStatus(HttpStatus.CREATED.value());
        /////////////////////////////////////////////////////////////////////////////////////////
        return statusResponse;
    }
    
    /****SELECIONA******/
    @RequestMapping(value = "/{sku}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "seleciona 1 livro", notes = "localizado na base pelo cl sku.")
    public
    @ResponseBody
    Livro  selectLivro(@ApiParam(value = "id do livro.", required = true)
				    @PathVariable("sku") Long sku,
				    HttpServletRequest request, HttpServletResponse response) throws Exception {
       log.info("selectLivro.insertLivro");
       Livro livro = this.livrosService.selectLivro(sku);
       return livro;
    }
    
    /****SELECIONA N******/
    @RequestMapping(value = "/",
            method = RequestMethod.POST,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "seleciona 1 livro", notes = "localizado na base pelo cl sku.")
    public
    @ResponseBody
    List<Livro>  selectLivros(@RequestParam(name = "price", required = false, defaultValue = "-1D" )
    						  Double price,
    						  @RequestParam(name = "limit", required = false, defaultValue = "-1" )
    						  Integer limit
				             ,HttpServletRequest request, HttpServletResponse response) throws Exception {
    	log.info("selectLivro.selectLivros");
    	List<Livro> livros = null;
    	
    	//*Listar	limit livros (se	parâmetro	limit	não	for	passado,devolver	todos): 
    	//retornar	as	informações	de	
    	//limit	livros	salvos	no	banco	da	aplicação.
    	
    	//* Listar	livros	com	preço	até	price (se	parâmetro	price	não	for	passado,	
    	//devolver	todos): retornar	as	
    	//informações	de	livros	salvos	no	banco	da	aplicação	com	valoraté	price.
    	/*FOI OQ ENTENDEI....*/
    	
    	if(price < 0 && limit < 0) {
    		log.info("LivrosController.selectLivros.this.livrosService.selectAll()");
    		livros = this.livrosService.selectAll();
    	}else if(price >= 0 && limit < 0) {
    		log.info("LivrosController.selectLivros.this.livrosService.selectLivrosByPrice(price);");
    		/* SE TIVER PREÇO E N LIMITE BUSCA POR PREÇO */
    		livros = this.livrosService.selectLivrosByPrice(price);
    	}else if(price <= 0 && limit >= 0) {
    		log.info("LivrosController.selectLivros.this.livrosService.selectLivrosByLimit(limit);");
    		/* SE N TIVER PREÇO E TIVER LIMITE BUSCA POR TODOS COM LIMIT DE LINHAS */
    		livros = this.livrosService.selectLivrosByLimit(limit);
    	}else if(price >= 0 && limit >= 0) {
    		log.info("LivrosController.selectLivros.this.livrosService.selectLivrosByPriceAndLimit(price, limit);");
    		/* SE  TIVER PREÇO E LIMITE BUSCA COM TODOS OS CRITERIO  */
    		livros = this.livrosService.selectLivrosByPriceAndLimit(price, limit);
    	}
        return livros;
    }
    

    
}

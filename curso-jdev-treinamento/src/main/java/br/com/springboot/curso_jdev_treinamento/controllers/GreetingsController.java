package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
    
	@Autowired /* IC/CD ou CDI - Injeção de dependência */
	private UsuarioRepository usuarioRepository;
	
	
	/**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario); /* Grava no banco de dados*/
    	
    	return "Ola mundo " + nome;
    }
    
    @GetMapping(value= "listatodos") /*Primeiro método de API */
    @ResponseBody /* Retorna os dados para o corpo da resposta */
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); /*Executa a consulta no banco de dados*/
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /*Retorna a lista em JSON */
    }
    
    @PostMapping(value = "salvar") /*mapeia a url */
    @ResponseBody /* Descrição da resposta*/
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { /*Recebe os dados para salvar */
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);	
    }
    
    @DeleteMapping(value = "delete") 
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long iduser) {
    	
    	usuarioRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
    }
    
    
    @GetMapping(value = "buscaruserid") 
    @ResponseBody
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser) {
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @PutMapping(value = "atualizar") 
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
    	
    	if(usuario.getId() == null) {
    		return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    
    @GetMapping(value = "buscarPorNome") 
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {
    	
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
    
    
}

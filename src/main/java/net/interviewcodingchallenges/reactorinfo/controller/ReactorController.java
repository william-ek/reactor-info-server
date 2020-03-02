package net.interviewcodingchallenges.reactorinfo.controller;



import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.interviewcodingchallenges.reactorinfo.exceptions.ValueNotVerifiedException;
import net.interviewcodingchallenges.reactorinfo.model.Reactor;
import net.interviewcodingchallenges.reactorinfo.service.ReactorService;

@Api(tags = "Reactor Info Service")
@RestController
@RequestMapping("/reactor")
public class ReactorController {
	
    private final Logger logger = LoggerFactory.getLogger(ReactorController.class);
    
	@Autowired
	MessageSource messageSource;
	
    @Autowired
	ReactorService service;
    
    @ApiOperation(value = "Retrives a list of all Images in the repository", response = Reactor.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
    })
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Reactor> getReactors() {
		logger.debug("Get Reactors");
		return service.getReactors();
	}
	
	@ApiOperation("Retrieves a spacific Reactor by Id")
	@ApiResponses(value = {@ApiResponse(code=200, message="Service completed successfully"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code=404, message="Reactor was not found")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public Optional<Reactor> getReactorById(@PathVariable String id) {
		logger.debug("Get Reactor By Id");
		Optional<Reactor> reactor = service.getReactorById(id);
		if (reactor == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageSource.getMessage("messages.reactorNotFound", null, "Reactor not found", Locale.ENGLISH));
		}
		return reactor;
	}
	
	@ApiOperation("Adds an Image to the repository")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Reactor postReactor(@Valid @RequestBody Reactor reactor, BindingResult bindingResult) {
    	logger.debug("Post Image");
    	
        if (bindingResult.hasErrors()) {
        	StringBuilder errorMessages = new StringBuilder();
        	bindingResult.getAllErrors().forEach(error -> {
        		errorMessages.append(error.getDefaultMessage()  + "; ");
        	});
            throw new ValueNotVerifiedException(errorMessages.toString());
        }
    	
    	return service.createReactor(reactor);

    }
    
	@ApiOperation("Replaces an Image in the repository")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping()
    public Reactor putReactor(@Valid @RequestBody Reactor reactor, BindingResult bindingResult) {
    	logger.debug("Put Reactor");
    	
        if (bindingResult.hasErrors()) {
        	StringBuilder errorMessages = new StringBuilder();
        	bindingResult.getAllErrors().forEach(error -> {
        		errorMessages.append(error.getDefaultMessage()  + "; ");
        	});
        	throw new ValueNotVerifiedException(errorMessages.toString());
        }
    	
    	return service.replaceReactor(reactor);

    }
    
	@ApiOperation("Deletes a Reactor from the repository")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteReactorById(@PathVariable String id) {
    	logger.debug("Delete Image By Name: " + id);
    	service.deleteReactorById(id);
    }

}


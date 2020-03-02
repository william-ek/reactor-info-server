package net.interviewcodingchallenges.reactorinfo.service;


import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import net.interviewcodingchallenges.reactorinfo.model.Reactor;
import net.interviewcodingchallenges.reactorinfo.repository.ReactorRepository;

@Service
@Transactional
public class ReactorService {
	
	private final Logger logger = LoggerFactory.getLogger(ReactorService.class);
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ReactorRepository repository;
	
	/**
	 * Retrieve all Reactors
	 * @return List<Reactor>
	 */
	public List<Reactor> getReactors() {
		logger.debug("Get All Reactors");
		return repository.findAll();
	}
	
	/**
	 * Retrieve an Reactor by its id
	 * @param id
	 * @return
	 */
	public Optional<Reactor> getReactorById(String id) {
		logger.debug("Get Reactor By Id: " + id);
		return repository.findById(id);
	}
	
	/**
	 * Creates a new Reactor
	 * @param Reactor
	 * @return Reactor
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public Reactor createReactor(Reactor reactor) {
		
		logger.debug("Create Reactor: " + reactor.getId());
		/*
		 * A new Reactor will not yet have a version number
		 */
		reactor.setVersion(null);
		return repository.save(reactor);
	}
	
	/**
	 * Replaces an Reactor
	 * @param Reactor
	 * @return Reactor
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public Reactor replaceReactor(Reactor reactor) {
		logger.debug("Replace Reactor: " + reactor.getId());
		Optional<Reactor> returnedReactor = repository.findById(reactor.getId());
		if (!returnedReactor.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageSource.getMessage("messages.ReactorNotFound", null, "Reactor not found", Locale.ENGLISH));
		}
		return repository.save(reactor);
	}
	
	/**
	 * Delete an Reactor by its 
	 * @param 
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
	public void deleteReactorById(String id) {
		logger.debug("Delete Reactor By Id: " + id);
		repository.deleteById(id);
	}
	

}

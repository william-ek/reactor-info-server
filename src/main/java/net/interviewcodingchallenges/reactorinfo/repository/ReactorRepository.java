package net.interviewcodingchallenges.reactorinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.interviewcodingchallenges.reactorinfo.model.Reactor;


public interface ReactorRepository extends JpaRepository<Reactor, String> {
	
	
}

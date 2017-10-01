package com.twofactor.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.twofactor.model.ScratchCode;
import com.twofactor.model.User;

@Repository("scratchJPARepository")
public interface ScratchCodeJPARepository extends CrudRepository<ScratchCode, Serializable>{

	public abstract List<ScratchCode> findAllByUser(User user);
	
	public abstract ScratchCode findByUserAndCode(User user,int code);
}

package com.congoal.cert.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.congoal.cert.pojo.User;

@Repository
public class AdminDao extends BaseDao<User> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}

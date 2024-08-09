package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Project;

public interface ProjectDao extends JpaRepository<Project, Long> {

}

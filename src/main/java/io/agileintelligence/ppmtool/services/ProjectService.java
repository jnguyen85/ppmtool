package io.agileintelligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return this.projectRepository.save(project);
		}
		catch (Exception e) {
			throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exist");
		}
	}
	
	public Project findProjectByIndentifier(String projectId) {
		Project project = this.projectRepository.findByProjectIdentifier(projectId);
		
		if (project == null) {
			throw new ProjectIdException("Project ID '" + projectId + "' does not exist"); 
		}
		return this.projectRepository.findByProjectIdentifier(projectId.toUpperCase());
	}
	
	public Iterable<Project> findAllProjects() {
		
		return this.projectRepository.findAll();
	}
}

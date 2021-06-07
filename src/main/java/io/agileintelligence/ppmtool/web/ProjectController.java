package io.agileintelligence.ppmtool.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.services.MapValidationErrorService;
import io.agileintelligence.ppmtool.services.ProjectService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorService mapValicationErrorService;
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(
			@Valid @RequestBody Project project,
			BindingResult result) {
		
		ResponseEntity<?> errorMap = this.mapValicationErrorService.MapValicationService(result);
		if (errorMap != null) return errorMap;
		
		Project project1 = this.projectService.saveOrUpdateProject(project);
		return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
		
		Project project = this.projectService.findProjectByIndentifier(projectId);
		
		return new ResponseEntity<Project>(project, HttpStatus.OK);
		
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAllProjects() {
		return this.projectService.findAllProjects();
	}
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
		this.projectService.deleteProjectByIdentifier(projectId);
		return new ResponseEntity<String>("Project with ID: '" + projectId + "' was deleted", HttpStatus.OK);
	}
}

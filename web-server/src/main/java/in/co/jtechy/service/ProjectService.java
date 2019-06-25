package in.co.jtechy.service;

import domain.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
//    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl = "http://localhost:8081";

    public List<Project> projects() {
        Project[] projects = restTemplate.getForObject(serviceUrl + "/projects", Project[].class);
        return Arrays.asList(projects);
    }

    public Project getProject(Integer id) {
        Project project = restTemplate.getForObject(serviceUrl + "/project/" + id, Project.class);
        return project;
    }

    public Project saveProject(Project project) {
        Project result = restTemplate.postForObject(serviceUrl + "/projectsave", project, Project.class);
        return result;
    }

    public Integer deleteProject(Integer id) {
        Integer projectId = restTemplate.getForObject(serviceUrl + "/projectdelete/" + id, Integer.class);
        return projectId;
    }

}

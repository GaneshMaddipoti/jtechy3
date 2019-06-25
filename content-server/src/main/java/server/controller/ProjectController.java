package server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.Field;
import domain.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import persistance.repository.ProjectRepository;
import server.util.FieldUtil;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    protected ObjectMapper mapper;

    @RequestMapping("/projects")
    public Iterable<Project> projects() throws IOException {
        Iterable<Project> projects = projectRepository.findAll();
        for(Project project : projects){
            List<Field> fields = mapper.readValue(project.getDetails(), new TypeReference<List<Field>>() {});
            project.setFields(fields);
        }
        return projects;
    }

    @RequestMapping(value = "/project/{id}")
    public Project projectDetails(@PathVariable Integer id) throws IOException {
        Project project = projectRepository.findById(id).orElse(new Project());
        List<Field> fields = mapper.readValue(project.getDetails(), new TypeReference<List<Field>>() {});
        project.setFields(fields);
        return project;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/projectsave")
    public Project saveProject(@RequestBody Project arg) throws Exception{
        String jsonStr = mapper.writeValueAsString(arg);
        Project project = mapper.readValue(jsonStr, Project.class);
        project.setDetails(mapper.writeValueAsString(FieldUtil.filterFields(project.getFields())));
        projectRepository.save(project);
        return project;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/projectdelete/{id}")
    public Integer deleteProject(@PathVariable Integer id) {
        projectRepository.deleteById(id);
        return id;
    }

}

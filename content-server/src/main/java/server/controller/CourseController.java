package server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.Field;
import domain.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import persistance.repository.CourseRepository;
import server.util.FieldUtil;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    protected ObjectMapper mapper;

    @RequestMapping("/public")
    public Iterable<Course> getAll() {
        return courseRepository.findByActive(false);
    }

    @RequestMapping("/courses")
    public Iterable<Course> courses() {
        return courseRepository.findByActive(true);
    }

    @RequestMapping("/course_categories")
    public Iterable<String> courseCategories() {
        Iterable<String> categories = courseRepository.findDistinctCategories();
        return categories;
    }

    @RequestMapping(value = "/course/{id}")
    public Course courseDetails(@PathVariable Integer id) throws IOException {
        Course course = courseRepository.findById(id).orElse(new Course());
        List<Field> fields = mapper.readValue(course.getDetails(), new TypeReference<List<Field>>() {
        });
        course.setFields(fields);
        return course;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/savecourse")
    public Course saveProject(@RequestBody Course t) throws Exception {
        String jsonStr = mapper.writeValueAsString(t);
        Course course = mapper.readValue(jsonStr, Course.class);
        course.setDetails(mapper.writeValueAsString(FieldUtil.filterFields(course.getFields())));
        courseRepository.save(course);
        return course;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deletecourse/{id}")
    public void deleteProject(@PathVariable Integer id) {
        courseRepository.deleteById(id);
    }

}

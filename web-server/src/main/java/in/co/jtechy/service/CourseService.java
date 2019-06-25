package in.co.jtechy.service;

import domain.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CourseService {

    @Autowired
//    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl = "http://localhost:8081";

    public List<Course> courses() {
        Course[] courses = restTemplate.getForObject(serviceUrl + "/courses", Course[].class);
        return Arrays.asList(courses);
    }

    public Course getCourse(Integer id) {
        Course course = restTemplate.getForObject(serviceUrl + "/course/" + id, Course.class);
        return course;
    }

    public Course saveCourse(Course course) {
        Course result = restTemplate.postForObject(serviceUrl + "/savecourse", course, Course.class);
        return result;
    }

    public Integer deleteCourse(Integer id) {
        Integer courseId = restTemplate.getForObject(serviceUrl + "/coursedelete/" + id, Integer.class);
        return courseId;
    }

}

package in.co.jtechy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.AbstractEntity;
import domain.model.Course;
import in.co.jtechy.service.CourseService;
import in.co.jtechy.util.FieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    protected ObjectMapper mapper;

    @RequestMapping("/courses")
    public String courses(Model model) {
        Map<String, List<Course>> resultMap = new LinkedHashMap<>();

        List<Course> sortedList = courseService.courses();
        sortedList.sort(Comparator.comparingInt(AbstractEntity::getRank));
        for (Course course : sortedList) {
            if (resultMap.get(course.getCategory()) == null) {
                List<Course> courses = new ArrayList<Course>();
                courses.add(course);
                resultMap.put(course.getCategory(), courses);
            } else {
                List<Course> courses = resultMap.get(course.getCategory());
                courses.add(course);
            }
        }
        model.addAttribute("courses", resultMap);
        return "courses";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/coursenew")
    public String coursenew(Model model)  {
        Course course = new Course();
        course.setFields(new ArrayList<>());
        model.addAttribute("course", course);
        return "coursenew";
    }

    @RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
    public String courseview(@PathVariable Integer id, Model model) {
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course);
        return "courseview";
    }

    @RequestMapping(value = "/coursemodify/{id}", method = RequestMethod.GET)
    public String coursemodify(@PathVariable Integer id, Model model) {
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course);
        return "coursenew";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/coursesave")
    public String saveCourse(@ModelAttribute Course t, Model model) throws Exception{
        String jsonStr = mapper.writeValueAsString(t);
        Course course = mapper.readValue(jsonStr, Course.class);
        course.setDetails(mapper.writeValueAsString(FieldUtil.filterFields(course.getFields())));
        courseService.saveCourse(course);
        model.addAttribute("course", course);
        return "courseview";
    }

    @RequestMapping(value = "/coursedelete/{id}", method = RequestMethod.GET)
    public String courseDelete(@PathVariable Integer id, Model model) {
        courseService.deleteCourse(id);
        model.addAttribute("course", new Course());
        return "coursenew";
    }



}

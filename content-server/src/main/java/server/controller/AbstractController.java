package server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.AbstractEntity;
import domain.model.Field;
import server.strategy.EntitiesByCategoryStrategy;
import server.util.CRUD_OPERATION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

import static server.util.CRUD_OPERATION.*;
import static server.util.MODEL.COURSE;

public class AbstractController<T extends AbstractEntity> {

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    protected EntitiesByCategoryStrategy entitiesByCategoryStrategy;

    protected CrudRepository<T, Integer> crudRepository;
    protected Class<T> classType;

    @RequestMapping(value = "/common/details/{id}", method = RequestMethod.GET)
    public ModelAndView courseDetails(@PathVariable Integer id) throws Exception {
        T t = crudRepository.findById(id).orElse(classType.newInstance());
        if(t.getDetails() != null) {
            List<Field> fields = mapper.readValue(t.getDetails(), new TypeReference<List<Field>>() {         });
            fields.forEach(field -> field.setFieldValue(field.getFieldValue().replaceAll("\r\n", "<br/>")));
            t.setFields(fields);
        }
        ModelAndView model = new ModelAndView();
        model.addObject(classType.getSimpleName().toLowerCase(), t);
        model.setViewName(VIEW.toString() + classType.getSimpleName().toLowerCase());
        return model;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/common/get/{operation}/{id}")
    public ModelAndView getOperations(@PathVariable String operation, @PathVariable Integer id) throws Exception {
        ModelAndView model = new ModelAndView();
        switch (CRUD_OPERATION.valueOf(operation.toUpperCase())) {
            case LIST:
                model.addObject("resultMap", entitiesByCategoryStrategy.entitiesByCategory(crudRepository.findAll()));
                model.setViewName(LIST.toString() + classType.getSimpleName().toLowerCase());
                break;
            case ADD:
                model.addObject(COURSE.toString(), classType.newInstance());
                model.setViewName(ADD.toString() + classType.getSimpleName().toLowerCase());
                break;
            case MODIFY:
                T t = crudRepository.findById(id).orElse(classType.newInstance());
                if(t.getDetails() != null) {
                    List<Field> fields = mapper.readValue(t.getDetails(), new TypeReference<List<Field>>() {    });
                    t.setFields(fields);
                }
                model.addObject(classType.getSimpleName().toLowerCase(), t);
                model.setViewName(ADD.toString() + classType.getSimpleName().toLowerCase());
                break;
            case DELETE:
                crudRepository.deleteById(id);
                model.addObject("resultMap", entitiesByCategoryStrategy.entitiesByCategory(crudRepository.findAll()));
                model.setViewName(LIST.toString() + classType.getSimpleName().toLowerCase());
                break;
        }
        return model;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/common/post/{operation}")
    public ModelAndView postOperations(@PathVariable String operation, @ModelAttribute T t) throws Exception {
        ModelAndView model = new ModelAndView();
        switch (CRUD_OPERATION.valueOf(operation.toUpperCase())) {
            case SAVE:
                String jsonStr = mapper.writeValueAsString(t);
                T entity = mapper.readValue(jsonStr, classType);
                if(!CollectionUtils.isEmpty(entity.getFields())){
                    List filteredList = entity.getFields().stream().filter(field -> !field.getFieldName().isEmpty()).collect(Collectors.toList());
                    entity.setDetails(mapper.writeValueAsString(filteredList));
                }
                crudRepository.save(entity);
                model.addObject(classType.getSimpleName().toLowerCase(), entity);
                model.setViewName(VIEW.toString() + classType.getSimpleName().toLowerCase());
                break;
        }
        return model;
    }
}

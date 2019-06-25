package server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.Field;
import domain.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import persistance.repository.BlogRepository;
import server.util.FieldUtil;

import java.io.IOException;
import java.util.List;

@RestController
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    protected ObjectMapper mapper;

    @RequestMapping("/blogs")
    public Iterable<Blog> blogs() {
        return blogRepository.findAll();
    }

    @RequestMapping(value = "/blog/{id}")
    public Blog blogDetails(@PathVariable Integer id) throws IOException {
        Blog blog = blogRepository.findById(id).orElse(new Blog());
        List<Field> fields = mapper.readValue(blog.getDetails(), new TypeReference<List<Field>>() {});
        blog.setFields(fields);
        return blog;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveblog")
    public Blog saveProject(@ModelAttribute Blog t) throws Exception{
        String jsonStr = mapper.writeValueAsString(t);
        Blog blog = mapper.readValue(jsonStr, Blog.class);
        blog.setDetails(mapper.writeValueAsString(FieldUtil.filterFields(blog.getFields())));
        blogRepository.save(blog);
        return blog;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteblog/{id}")
    public void deleteProject(@PathVariable Integer id) {
        blogRepository.deleteById(id);
    }

}

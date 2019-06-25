package server.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.model.Tool;
import domain.model.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import persistance.repository.ToolRepository;
import server.util.FieldUtil;

import java.io.IOException;
import java.util.List;

@RestController
public class ToolController {

    @Autowired
    private ToolRepository toolRepository;
    @Autowired
    protected ObjectMapper mapper;

    @RequestMapping("/tools")
    public Iterable<Tool> tools() {
        return toolRepository.findAll();
    }

    @RequestMapping(value = "/tool/{id}")
    public Tool toolDetails(@PathVariable Integer id) throws IOException {
        Tool tool = toolRepository.findById(id).orElse(new Tool());
        List<Field> fields = mapper.readValue(tool.getDetails(), new TypeReference<List<Field>>() {});
        tool.setFields(fields);
        return tool;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/savetool")
    public Tool saveProject(@ModelAttribute Tool t) throws Exception{
        String jsonStr = mapper.writeValueAsString(t);
        Tool tool = mapper.readValue(jsonStr, Tool.class);
        tool.setDetails(mapper.writeValueAsString(FieldUtil.filterFields(tool.getFields())));
        toolRepository.save(tool);
        return tool;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deletetool/{id}")
    public void deleteProject(@PathVariable Integer id) {
        toolRepository.deleteById(id);
    }

}

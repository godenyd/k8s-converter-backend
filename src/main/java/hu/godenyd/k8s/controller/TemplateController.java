package hu.godenyd.k8s.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.godenyd.k8s.util.TemplateUtil;

@RestController
@RequestMapping("/templates")
public class TemplateController {

    @GetMapping(path = "/checkName/{templateName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> checkTemplateName(@PathVariable final String templateName) {

        String response = "false";

        if (TemplateUtil.checkTemplateName(templateName)) {
            response = "true";
        }
        
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/templates")
    ResponseEntity<List<String>> getTemplates() {
        
        return ResponseEntity.ok(TemplateUtil.getTemplateList());
    }

    @GetMapping(path = "/template/{templateName}")
    ResponseEntity<String> getTemplate(@PathVariable final String templateName) {

        try {
            return ResponseEntity.ok(TemplateUtil.getTemplate(templateName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/template/{templateName}")
    ResponseEntity<String> saveTemplate(@PathVariable final String templateName, @RequestBody String templateString) {

        boolean isError = false;

        try {
            TemplateUtil.SaveTemplate(templateName, templateString);
        } catch (IOException e) {
            isError = true;
        }

        if (!isError) {
            return ResponseEntity.ok("Saved");
        }

        return ResponseEntity.badRequest().build();
    }

}

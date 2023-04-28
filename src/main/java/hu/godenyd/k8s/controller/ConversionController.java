package hu.godenyd.k8s.controller;

import javax.json.Json;
import javax.json.JsonObject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.godenyd.k8s.converter.deployment.DeploymentBuilder;
import hu.godenyd.k8s.converter.service.ServiceBuilder;

@RestController
public class ConversionController {
    
    @PostMapping(path="/convert", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    String convertJsonToYaml(@RequestBody String description) {

        StringBuilder sb = new StringBuilder();

        sb.append(ServiceBuilder.buildServiceAsString(description));
        sb.append("---\n");
        sb.append(DeploymentBuilder.buildDeploymentAString(description));

        JsonObject object = Json.createObjectBuilder().add("configuration", sb.toString()).build();

        return object.toString();
    }

    @GetMapping("/convert")
    String getConvert() {
        return "Hey, this is get /convert. Sup?";
    }
}

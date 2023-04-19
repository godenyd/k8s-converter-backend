package hu.godenyd.k8s.controller;

import javax.json.JsonObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hu.godenyd.k8s.converter.deployment.DeploymentBuilder;
import hu.godenyd.k8s.converter.service.ServiceBuilder;

@RestController
public class ConversionController {
    
    @PostMapping("/convert")
    String convertJsonToYaml(@RequestBody JsonObject description) {

        StringBuilder sb = new StringBuilder();

        sb.append(ServiceBuilder.buildServiceAsString(description));
        sb.append("---\n");
        sb.append(DeploymentBuilder.buildDeploymentAString(description));

        return sb.toString();
    }
}

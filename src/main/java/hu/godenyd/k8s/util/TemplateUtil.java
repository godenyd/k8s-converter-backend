package hu.godenyd.k8s.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * TODO: Make this class thread safe
 */
@Component
public class TemplateUtil {

    private static String templatesPath;

    public static boolean checkTemplateName(String name) {

        return getTemplateList().stream()
                .anyMatch(template -> template.equalsIgnoreCase(name));

    }

    public static List<String> getTemplateList() {

        new File(templatesPath);

        return Stream.of(new File(templatesPath).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public static String getTemplate(String name) throws IOException {

        return readFile(new File(templatesPath + "/" + name));
    }

    public static void SaveTemplate(String name, String contents) throws IOException {

        if (!name.endsWith(".json")) {
            name = name.concat(".json");
        }

        File templateFile = new File(templatesPath + "/" + name);

        if (!templateFile.exists()) {
            writeToFile(templateFile, contents);
        }
    }

    private static String readFile(File file) throws IOException {

        return FileUtils.readFileToString(file, "UTF-8");

    }

    private static void writeToFile(File file, String contents) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(contents);

        writer.close();
    }

    @Value("${templates.folder.path}")
    public void setTemplatesPath(String path) {
        TemplateUtil.templatesPath = path;
    }
}

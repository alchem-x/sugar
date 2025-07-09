package sugar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.UncheckedIOException;

public class SugarSpring {

    public static <T> T readYaml(String path, Class<T> clazz) {
        try {
            return new ObjectMapper(new YAMLFactory()).readValue(new ClassPathResource(path).getInputStream(), clazz);
        } catch (IOException ex) {
            throw new UncheckedIOException("read yaml failed", ex);
        }
    }
}

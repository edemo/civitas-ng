package civitas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;

@Controller
public class GetReaderForFile {
	public BufferedReader apply(String keyFile) throws IOException {
		return Files.newBufferedReader(Paths.get(keyFile));
	}
}

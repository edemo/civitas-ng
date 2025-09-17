package civitas.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.springframework.stereotype.Controller;

@Controller
public class GetReaderForFile {
	public BufferedReader apply(String keyFile) throws FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(keyFile));
		return reader;
	}

}

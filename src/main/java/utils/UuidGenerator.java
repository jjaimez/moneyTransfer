package utils;

import java.util.UUID;

public class UuidGenerator implements IdGenerator {

	public String generate() {
		return UUID.randomUUID().toString();
	}
	
	
}

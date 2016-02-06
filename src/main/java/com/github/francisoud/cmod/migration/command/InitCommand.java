package com.github.francisoud.cmod.migration.command;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class InitCommand implements Command {

	public static final String ID = "init";
	
	@Override
	public void execute(String folder) {
		try {
			Files.createDirectories(Paths.get(folder));
			Files.createDirectories(Paths.get(folder, "changelogs"));
			
//			final InputStream odchangelogXsd = getClass().getClassLoader().getResourceAsStream("odchangelog.xsd");
//			Files.copy(odchangelogXsd, Paths.get(folder, "odchangelog.xsd"), StandardCopyOption.REPLACE_EXISTING);

			final InputStream changelogXml = getClass().getResourceAsStream("changelog.xml");
			Files.copy(changelogXml, Paths.get(folder, "changelog.xml"), StandardCopyOption.REPLACE_EXISTING);

			final InputStream initialSchemaXml = getClass().getResourceAsStream("changelogs/00000000000000_initial_schema.xml");
			Files.copy(initialSchemaXml, Paths.get(folder, "changelogs", "00000000000000_initial_schema.xml"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

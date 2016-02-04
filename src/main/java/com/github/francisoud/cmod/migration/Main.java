package com.github.francisoud.cmod.migration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.github.francisoud.cmod.migration.command.InitCommand;

public class Main {

	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		Option help = new Option("h", "help", false, "print this message");
		options.addOption(help);
		Option version = new Option("V", "version", false, "print the version information and exit");
		options.addOption(version);
		/*
		Option quiet = new Option("q", "quiet", false, "be extra quiet");
		options.addOption(quiet);
		Option verbose = new Option("v", "verbose", false, "be extra verbose");
		options.addOption(verbose);
		Option debug = new Option("d", "debug", false, "print debugging information");
		options.addOption(debug);
		*/
		options.addOption(Option.builder("i").longOpt(InitCommand.ID).hasArg().argName("folder").build());

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		if (cmd.getOptions().length == 0 || cmd.hasOption("help")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("odmig", options);
		}

		if (cmd.hasOption("version")) {
			System.out.println(Version.VERSION);
		}

		if (cmd.hasOption(InitCommand.ID)) {
			InitCommand init = new InitCommand();
			String folder = cmd.getOptionValue(InitCommand.ID, ".");
			init.execute(folder);
		}
	}
}

package com.github.francisoud.cmod.migration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.github.francisoud.cmod.migration.command.InitCommand;
import com.github.francisoud.cmod.migration.command.UpgradeCommand;

public class Main {

	public static void main(String[] args) throws ParseException {
		final Options options = new Options();
		final Option help = new Option("h", "help", false, "print this message");
		options.addOption(help);
		final Option version = new Option("V", "version", false, "print the version information and exit");
		options.addOption(version);
		/*
		Option quiet = new Option("q", "quiet", false, "be extra quiet");
		options.addOption(quiet);
		Option verbose = new Option("v", "verbose", false, "be extra verbose");
		options.addOption(verbose);
		Option debug = new Option("d", "debug", false, "print debugging information");
		options.addOption(debug);
		*/
		options.addOption(Option.builder("i").longOpt(InitCommand.ID).desc("create initial files").hasArg().argName("folder").build());
		options.addOption(Option.builder("u").longOpt("upgrade").desc("upgrade cmod").hasArg().argName("folder").build());

		final CommandLineParser parser = new DefaultParser();
		final CommandLine cmd = parser.parse(options, args);

		if (cmd.getOptions().length == 0 || cmd.hasOption("help")) {
			final HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("odmig", options);
		}

		if (cmd.hasOption("version")) {
			System.out.println(Version.VERSION);
		}

		if (cmd.hasOption(InitCommand.ID)) {
			final InitCommand initCommand = new InitCommand();
			String folder = cmd.getOptionValue(InitCommand.ID);
			initCommand.execute(folder);
		}

		if (cmd.hasOption("upgrade")) {
			final UpgradeCommand upgradeCommand = new UpgradeCommand();
			String folder = cmd.getOptionValue("upgrade");
			upgradeCommand.execute(folder);
		}
	}
}

package org.dambaron.mower2020.application;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MowingSimulatorTest {

	@Test
	void testCmd() throws ParseException {
		// use the GNU parser
		CommandLineParser parser = new DefaultParser();
		var options = new Options();

		options.addRequiredOption("c1", "config1", true, "Use given file as config1");

		var option = Option.builder("c2")
			.required()
			.hasArg()
			.longOpt("config2")
			.desc("Use given file as config")
			.build();

		options.addOption(option);

		var args = new String[]{
			"-c1", "file1.txt",
			"-c2", "file2.txt"
		};

		CommandLine line = parser.parse(options, args);

		String[] c1Opts = line.getOptionValues("c1");
		String[] c2Opts = line.getOptionValues("c2");
//        assertThat(opts[0]).isEqualTo("property");
//        assertThat(opts[1]).isEqualTo("value");
//        assertThat(opts[2]).isEqualTo("property1");
//        assertThat(opts[3]).isEqualTo("value1");

		// check single value
		assertThat(line.getOptionValue("c")).isEqualTo("file.txt");

		// check option
		assertThat(line.hasOption("c")).isTrue();

	}

	@Test
	void testAnt() throws ParseException {
		// use the GNU parser
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("help", false, "print this message");
		options.addOption("projecthelp", false, "print project help information");
		options.addOption("version", false, "print the version information and exit");
		options.addOption("quiet", false, "be extra quiet");
		options.addOption("verbose", false, "be extra verbose");
		options.addOption("debug", false, "print debug information");
		options.addOption("logfile", true, "use given file for log");
		options.addOption("logger", true, "the class which is to perform the logging");
		options.addOption("listener", true, "add an instance of a class as a project listener");
		options.addOption("buildfile", true, "use given buildfile");
		options.addOption(OptionBuilder.withDescription("use value for given property")
			.hasArgs()
			.withValueSeparator()
			.create('D'));
		//, null, true, , false, true );
		options.addOption("find", true, "search for buildfile towards the root of the filesystem and use it");

		String[] args = new String[]{
			"-buildfile", "mybuild.xml",
			"-Dproperty=value",
			"-Dproperty1=value1",
			"-projecthelp"
		};

		CommandLine line = parser.parse(options, args);

		// check multiple values
		String[] opts = line.getOptionValues("D");
		assertThat(opts[0]).isEqualTo("property");
		assertThat(opts[1]).isEqualTo("value");
		assertThat(opts[2]).isEqualTo("property1");
		assertThat(opts[3]).isEqualTo("value1");

		// check single value
		assertThat(line.getOptionValue("buildfile")).isEqualTo("mybuild.xml");

		// check option
		assertThat(line.hasOption("projecthelp")).isTrue();
	}
}

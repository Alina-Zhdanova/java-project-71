package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")
class App implements Runnable {

    @Parameters(paramLabel = "filepath1", description = "Path to first file.")
    String filepath1;

    @Parameters(paramLabel = "filepath2", description = "Path to second file.")
    String filepath2;

    @Option(names = {"-f", "--format"}, description = "Output format [default: stylish].",
        defaultValue = "stylish", paramLabel = "format")
    String formatName;

    @Override
    public void run() {
        try {
            var result = Differ.generate(filepath1, filepath2, formatName);
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}

package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")
class App implements Runnable {

    @Parameters(paramLabel = "filepath1", description = "Path to first file.")
    File file1;

    @Parameters(paramLabel = "filepath2", description = "Path to second file.")
    File file2;

    @Option(names = {"-f", "--format"}, description = "Output format [default: stylish].", paramLabel = "format")
    private String format;

    @Override
    public void run() {
        System.out.println("Hello World!");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
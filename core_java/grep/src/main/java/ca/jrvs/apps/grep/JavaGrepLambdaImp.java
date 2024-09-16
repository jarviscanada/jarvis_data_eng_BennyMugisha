package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");

        }

        //creating JavaGrepLambdaImp instead of JavaGrepImp
        //JavaGrepLambdaImp inherits all method except two override methods in
        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            javaGrepLambdaImp.logger.error("Error: Unable to process", ex);
        }

    }

    /**
     * Implement using lambda ad stream APIs
     */
    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            Stream<String> linesStream = reader.lines();
            lines = linesStream.collect(Collectors.toList());
            reader.close();
        } catch (IOException e) {
            logger.error("Error: unable to read from file", e);
        }
        return lines;

    }

    @Override
    public void process() {
        List<String> matchedLines = listFiles(getRootPath()).stream()
                .flatMap(file -> readLines(file).stream())
                .filter(this::containsPattern)
                .collect(Collectors.toList());

        writeToFile(matchedLines);

    }

    @Override
    public void writeToFile(List<String> lines) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getOutFile()));
            lines.forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            logger.error("Error: Unable to write to file", e);
                        }
                    });

            writer.close();
        } catch (IOException e) {
            logger.error("Error: Unable to write to file", e);
        }
    }
}

package org.example;

import jdepend.xmlui.JDepend;

import java.io.*;
import java.util.Scanner;

public class tesJdepend {
    public static void main(String[] args) throws IOException, InterruptedException {
        try(PrintWriter printWriter = new PrintWriter(new FileOutputStream("rs.xml"))) {
            JDepend depend = new JDepend(printWriter);
            depend.addDirectory("T:\\Library-Assistant-master");
            depend.analyze();
            System.out.println("done");
            String[] command = {"C:\\Program Files\\nodejs\\npm.cmd", "run", "T:\\PhamThanhSon\\jdepend-ui", "T:\\son\\untitled\\rs.xml", "xml"};

            // Execute command
            String npmCommand = "npm run jdepend-ui \"T:\\son\\untitled\\rs.xml\" \"xml\"";
            String workingDirectory = "T:\\PhamThanhSon\\jdepend-ui";

            try {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", npmCommand);
                processBuilder.directory(new File(workingDirectory));
                processBuilder.redirectErrorStream(true); // Redirect error stream to output stream
                Process process = processBuilder.start();

                // Read output
                InputStream is = process.getInputStream();
                Scanner scanner = new Scanner(is, "UTF-8");
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }

                // Wait for the process to finish
                int exitCode = process.waitFor();
                System.out.println("Command exited with code " + exitCode);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

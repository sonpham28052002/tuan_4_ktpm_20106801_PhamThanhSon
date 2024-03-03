package org.example;

import jdepend.xmlui.JDepend;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class tesJdepend {
    public static void main(String[] args) throws IOException, InterruptedException {
        String projectUI = "T:\\PhamThanhSon\\jdepend-ui";
        String pathReport = "T:\\son\\untitled\\rs.xml";
        String scanProject = "T:\\Library-Assistant-master";

        try(PrintWriter printWriter = new PrintWriter(new FileOutputStream("rs.xml"))) {
            JDepend depend = new JDepend(printWriter);
            depend.addDirectory(scanProject);
            depend.analyze();
            System.out.println("done");
            runCommand(pathReport,projectUI);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void runCommand(String pathReport, String projectUI){
        String npmCommand = "npm run jdepend-ui \""+pathReport+"\" \"xml\"";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", npmCommand);
            processBuilder.directory(new File(projectUI));
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
            File file = new File(projectUI+"\\index.html");
            if (file.exists()){
                Desktop.getDesktop().open(file);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

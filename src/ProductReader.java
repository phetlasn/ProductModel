import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec;

        ArrayList<String> lines = new ArrayList<>();

        String number = "ID#";
        String ptName = "Product Name";
        String desc = "Description";
        String cash = "Cost";
        String divider = "================================================================================";

        String id;
        String product;
        String description;
        String record = "";
        double cost;

        final int FIELDS_LENGTH = 4;

        try {
            File workingDirectory = new File(System.getProperty("user,dir"));
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                int line = 0;
                while (reader.ready()){
                    rec = reader.readLine();
                    lines.add(rec);
                    line++;

                    System.out.printf("\\nLine %4d %-60s ", line, rec);
                }
                reader.close();
                System.out.println("\n\nData file read");

                System.out.printf("%-8s%-25s%-35s%-6s", number, ptName, desc, cash);
                System.out.printf("\n");
                System.out.println(divider);

                String[] fields;
                for (String l:lines){
                    fields = l.split(",");

                    if (fields.length == FIELDS_LENGTH){
                        id = fields[0].trim();
                        product = fields[1].trim();
                        description = fields[2].trim();
                        cost = Double.parseDouble(fields[3].trim());
                        System.out.printf("\\n%-8s%-25s%-35s%-6.2f", id, product, description, cost);
                    }
                    else {
                        System.out.println("Record that may be corrupt found: ");
                        System.out.println(l);
                    }
                }
            }
            else {
                System.out.println("Failure to choose a file");
                System.out.println("Run the program again");
                System.exit(0);
            }
        }
        catch (FileNotFoundException ex){
            System.out.println("File not found");
            ex.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }
}

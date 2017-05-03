import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Builds a R.java file full of stuff in your resource dir. Modify
 */
public class BuildResources {

    public static void main(String[] args) throws Exception {
        new BuildResources("src/main/resources", "com.klinker.platformer2d").build();
    }

    private File resRoot;
    private String packageName;
    private String resDir;
    private File javaFile;

    private BuildResources(String resDir, String packageName) throws Exception {
        this.packageName = packageName;
        this.resDir = resDir;

        buildXml();
    }

    // region Build R.java
    private void build() throws IOException {
        // setup the java file
        javaFile = new File("../src/main/java/" + packageName.replace('.', '/'), "R.java");
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        String fileBody = "package " + packageName + ";\n" +
                '\n' +
                "public class R {" + '\n';

        // set up the res loop
        resRoot = new File("../" + resDir);
        for (File file : resRoot.listFiles()) {
            if (file.isDirectory() && file.getName().equalsIgnoreCase("fonts"))
                fileBody += createFonts(file);
            else if (file.isDirectory() && file.getName().equalsIgnoreCase("strings"))
                fileBody += createStrings(file);
            else if (file.isDirectory())
                fileBody += createGeneral(file, 1);
        }
        fileBody += "}";

        FileWriter fWriter = new FileWriter(javaFile);
        BufferedWriter writer = new BufferedWriter(fWriter);
        writer.write(fileBody);
        writer.close();
        fWriter.close();
    }

    private String createGeneral(File dir, int depth) {
        String tabs = "";
        for (int i = 0; i < depth; i++) tabs += "    ";
        String body = tabs;
        if (dir.isDirectory() && !dir.getName().contains(".")) {
            body += "public static class " + dir.getName() + " {\n";
            for (File child : dir.listFiles()) {
                body += createGeneral(child, depth + 1);
            }
            body += tabs + "}\n";
        } else {
            if (dir.getName().endsWith(".psd")) return "";
            else {
                String varName = dir.getName().substring(0, dir.getName().indexOf('.')).toUpperCase().replace('-', '_');
                if (Character.isDigit(varName.charAt(0))) varName = Character.toUpperCase(dir.getParentFile().getName().charAt(0)) + varName;
                body += "public static final String " + varName +
                        " = \"" + dir.getPath().replace('\\', '/').replace("../", "") + "\";\n";
            }
        }
        return body;
    }

    // TODO: 3/26/2017 Multiple language support
    private String createStrings(File stringDir) {
        String body = "    public static class strings {\n";
        for (File file : stringDir.listFiles()) {
            try (FileReader fReader = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fReader)) {
                String line = "";
                while ((line = reader.readLine()) != null) if (!line.equals("") && !line.startsWith("#")) {
                    line = line.trim();
                    String originalName = line.substring(0, line.indexOf(":"));
                    String varName = originalName.toUpperCase().replace('-', '_');//line.split(":")[0].toUpperCase().replace('-', '_');
                    String varValue = line.replace(originalName, "").substring(1);//line.split(":")[1];
                    body += "        public static final String " + varName + " = " + varValue + ";\n";
                }
            } catch (IOException e) {

            }
        }
        return body + "    }\n";
    }

    private String createFonts(File fontDir) {
        String body = "    public static class fonts {\n";
        for (File font : fontDir.listFiles()) {
            if (font.isDirectory()) {
                String varName = font.getName().toUpperCase().replace('-', '_');
                body += "        public static final String " + varName +
                        " = \"" + font.getPath().replace('\\', '/').replace("../", "") + "\";\n";
            }
        }
        return body + "    }\n";
    }
    // endregion


    // region Building XML
    private void buildXml() throws Exception {
        File template = new File("../src/main/resources/layouts", "template.xsd");
        File output = new File("../src/main/resources/layouts", "layout-definition.xsd");

        Scanner scanner = new Scanner(template);
        String templateData = "";
        while (scanner.hasNextLine()) templateData += scanner.nextLine() + '\n';

        LinkedList<String> files = new LinkedList<>();
        LinkedList<File> queue = new LinkedList<>();
        queue.add(new File("../src/main/resources/textures"));
        while (!queue.isEmpty()) {
            for (File file : queue.remove().listFiles()) {
                if (file.isDirectory()) queue.add(file);
                else files.add(file.getPath().replace("..\\src\\main\\resources\\textures", "R.textures").replace("\\", "."));
            }
        }
        String replacment = "";
        int i = 0;
        for (String s : files) {
            replacment += "            <xs:enumeration value=\"" + s + "\"/>";
            if (i != files.size() - 1) replacment += '\n';
            i++;
        }
        String fileOutput = templateData.replace("<!--##########-->", replacment);

        FileWriter fWriter = new FileWriter(output);
        BufferedWriter writer = new BufferedWriter(fWriter);
        writer.write(fileOutput);
        writer.close();
        fWriter.close();
    }
    // endregion

}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Builds a R.java file full of stuff in your resource dir. Modify
 */
public class BuildResources {

    public static void main(String[] args) throws IOException {
        new BuildResources("res", "com.klinker.platformer2d").build();
    }

    private File resRoot;
    private String packageName;
    private String resDir;
    private File javaFile;

    private BuildResources(String resDir, String packageName) {
        this.packageName = packageName;
        this.resDir = resDir;
    }

    private void build() throws IOException {
        // setup the java file
        javaFile = new File("../src/" + packageName.replace('.', '/'), "R.java");
        javaFile.getParentFile().mkdirs();
        javaFile.createNewFile();
        String fileBody = "package " + packageName + ";\n" +
                '\n' +
                "public class R {" + '\n';

        // set up the res loop
        resRoot = new File("../" + resDir);
        for (File file : resRoot.listFiles()) {
           if (file.getName().equals("fonts")) fileBody += createFonts(file);
           else if (file.isDirectory()) fileBody += createSubClass(file, 1);
        }
        fileBody += "}";

        FileWriter fWriter = new FileWriter(javaFile);
        BufferedWriter writer = new BufferedWriter(fWriter);
        writer.write(fileBody);
        writer.close();
        fWriter.close();
    }

    private String createSubClass(File dir, int depth) {
        String tabs = "";
        for (int i = 0; i < depth; i++) tabs += "    ";
        String body = tabs;
        if (dir.isFile()) {
            if (dir.getName().endsWith(".psd")) return "";
            else {
                String varName = dir.getName().substring(0, dir.getName().lastIndexOf('.')).toUpperCase().replace('-', '_');
                if (Character.isDigit(varName.charAt(0))) varName = Character.toUpperCase(dir.getParentFile().getName().charAt(0)) + varName;
                body += "public static final String " + varName +
                        " = \"" + dir.getPath().replace('\\', '/').replace("../", "") + "\";\n";
            }
        } else {
            body += "public static class " + dir.getName() + " {\n";
            for (File child : dir.listFiles()) {
                body += createSubClass(child, depth + 1);
            }
            body += tabs + "}\n";
        }
        return body;
    }

    private String createFonts(File fontDir) {
        String body = "    public static class fonts {\n" +
                "        public static String getFont(String fontName, String style) {\n" +
                "            return \"" + fontDir.getPath().replace('\\', '/').replace("../", "") + "/" + "\" + fontName + '/' + style;\n" +
                "        }\n";

        for (File font : fontDir.listFiles()) {
            if (font.isDirectory()) {
                String varName = font.getName().toUpperCase().replace('-', '_');
                body += "        public static final String " + varName +
                        " = \"" + font.getPath().replace('\\', '/').replace("../", "") + "\";\n";
            }
        }

        return body + "    }\n";
    }

}

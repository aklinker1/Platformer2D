import java.io.*;

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
           if (file.isDirectory()) fileBody += createSubClass(file, 1);
        }
        fileBody += "}";

        FileWriter fWriter = new FileWriter(javaFile);
        BufferedWriter writer = new BufferedWriter(fWriter);
        writer.write(fileBody);
        writer.close();
        fWriter.close();
    }

    private String createSubClass(File file, int depth) {
        String tabs = "";
        for (int i = 0; i < depth; i++) tabs += "    ";
        String body = tabs;
        if (file.isFile()) {
            if (file.getName().endsWith(".psd")) return "";
            else {
                String varName = file.getName().substring(0, file.getName().lastIndexOf('.')).toUpperCase().replace('-', '_');
                if (Character.isDigit(varName.charAt(0))) varName = Character.toUpperCase(file.getParentFile().getName().charAt(0)) + varName;
                body += "public static final String " + varName +
                        " = \"" + file.getPath().replace('\\', '/').replace("../", "") + "\";\n";
            }
        } else {
            body += "public static class " + file.getName() + " {\n";
            for (File child : file.listFiles()) {
                body += createSubClass(child, depth + 1);
            }
            body += tabs + "}\n";
        }
        return body;
    }

}

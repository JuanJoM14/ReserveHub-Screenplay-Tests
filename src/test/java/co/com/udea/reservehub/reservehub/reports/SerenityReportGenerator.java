package co.com.udea.reservehub.reservehub.reports;

import net.thucydides.core.reports.html.HtmlAggregateStoryReporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SerenityReportGenerator {

    public static void main(String[] args) throws Exception {
        String projectName = "ReserveHub Screenplay Tests";
        File projectDirectory = new File(".").getCanonicalFile();
        File testResultsDirectory = new File(projectDirectory, "target/site/serenity");
        File htmlOutputDirectory = new File(projectDirectory, "target/site/serenity-report");
        htmlOutputDirectory.mkdirs();

        HtmlAggregateStoryReporter reporter = new HtmlAggregateStoryReporter(projectName);
        reporter.setProjectDirectory(projectDirectory.getAbsolutePath());
        reporter.setTestRoot("target/site/serenity");
        setOutputDirectory(reporter, htmlOutputDirectory);
        reporter.generateReportsForTestResultsFrom(testResultsDirectory);
        copySerenityAssets(htmlOutputDirectory);
    }

    private static void setOutputDirectory(Object target, File outputDirectory) throws IllegalAccessException {
        Class<?> current = target.getClass();

        while (current != null) {
            for (Field field : current.getDeclaredFields()) {
                if ("outputDirectory".equals(field.getName()) && File.class.equals(field.getType())) {
                    field.setAccessible(true);
                    field.set(target, outputDirectory);
                    return;
                }
            }
            current = current.getSuperclass();
        }

        throw new IllegalStateException("No se encontro el campo outputDirectory en HtmlAggregateStoryReporter");
    }

    private static void copySerenityAssets(File outputDirectory) throws Exception {
        String userHome = System.getProperty("user.home");
        File cacheRoot = new File(userHome, ".gradle/caches/modules-2/files-2.1/net.serenity-bdd/serenity-report-resources/4.1.0");
        File jarFile = findFirstJar(cacheRoot);

        if (jarFile == null) {
            throw new IllegalStateException("No se encontro el jar serenity-report-resources en el cache de Gradle");
        }

        try (JarFile jar = new JarFile(jarFile)) {
            jar.stream()
                    .filter(entry -> !entry.isDirectory())
                    .filter(entry -> entry.getName().startsWith("report-resources/"))
                    .forEach(entry -> copyJarEntry(jar, entry, outputDirectory.toPath()));
        }
    }

    private static void copyJarEntry(JarFile jar, JarEntry entry, Path outputDirectory) {
        String relativePath = entry.getName().replaceFirst("^report-resources/", "");
        Path destination = outputDirectory.resolve(relativePath);

        try {
            Files.createDirectories(Objects.requireNonNull(destination.getParent()));
            try (InputStream input = jar.getInputStream(entry);
                 FileOutputStream output = new FileOutputStream(destination.toFile())) {
                input.transferTo(output);
            }
        } catch (Exception e) {
            throw new RuntimeException("No fue posible copiar el recurso de Serenity: " + entry.getName(), e);
        }
    }

    private static File findFirstJar(File root) throws Exception {
        if (!root.exists()) {
            return null;
        }

        return Files.walk(root.toPath())
                .map(Path::toFile)
                .filter(file -> file.isFile() && file.getName().endsWith(".jar"))
                .findFirst()
                .orElse(null);
    }
}

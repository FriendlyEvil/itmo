package ru.ifmo.rain.krivopaltsev.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;

/**
 * Implementation class for {@link JarImpler} interface
 */
public class Implementor implements Impler, JarImpler {
    /**
     * Line separator constant string depending on Operation System for generated classes
     */
    private static final String SEPARATOR = System.lineSeparator();
    /**
     * Space symbol string constant for generated classes
     */
    private static final String SPACE = " ";
    /**
     * Tab (4 spaces) string constant for generated classes
     */
    private static final String TAB = SPACE.repeat(4);
    /**
     * Comma symbol string constant for generated classes
     */
    private static final String COMMA_SPACE = ", ";

    /**
     * Check token and root to not {@code null} and check token to valid type of class
     *
     * @param token a token of a class that is being implemented
     * @param root  a directory path where an implementation class package should be
     * @throws ImplerException if any of the arguments given is {@code null} or token type is invalid
     */
    private static void validate(Class<?> token, Path root) throws ImplerException {
        if (token == null) {
            throw new ImplerException("Class must be not-null");
        }
        if (root == null) {
            throw new ImplerException("Path must be not-null");
        }
        if (token.isPrimitive() || token.isArray() || token == Enum.class || Modifier.isFinal(token.getModifiers())) {
            throw new ImplerException("Incorrect class for implementing/extending");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        validate(token, root);
        root = getPath(root, token, "java");
        createDirectories(root);

        try (Writer writer = Files.newBufferedWriter(root)) {
            writeStartPart(token, writer);
            if (!token.isInterface()) {
                overrideConstructors(token, writer);
            }
            overrideAbstractMethod(token, writer);
            writeEndPart(writer);
        } catch (IOException e) {
            throw new ImplerException("Error: failed write to file");
        }
    }

    /**
     * Returns a string representing template type arguments in triangular brackets
     *
     * @param types an array of type variables which types to paste in brackets
     * @return a string representing template type arguments in triangular brackets
     */
    private static String getTemplate(TypeVariable<?>[] types) {
        if (types.length == 0)
            return "";
        return Arrays.stream(types).
                map(TypeVariable::getName).
                collect(Collectors.joining(COMMA_SPACE, "<", ">"));
    }

    /**
     * Generates a return type and name of an executable object
     *
     * @param executable executable object which return type and name are returned
     * @return a string containing return type and name
     */
    private static String getDeclaration(Executable executable) {
        if (executable instanceof Method) {
            Method method = (Method) executable;
            return getTemplate(method.getTypeParameters()) + method.getGenericReturnType().getTypeName().replace('$', '.') + SPACE + method.getName();
        } else {
            return getClassName(((Constructor<?>) executable).getDeclaringClass());
        }
    }

    /**
     * Generates declaration, arguments, exceptions signatures and body function of an executable object given
     *
     * @param executable executable object which arguments and exceptions are returned
     * @return a string containing declaration, arguments, exceptions signatures and body function
     */
    private static String getExecutable(Executable executable) {
        StringBuilder builder = new StringBuilder();
        builder.append(TAB).
                append(Modifier.toString(executable.getModifiers()
                        & ~Modifier.ABSTRACT & ~Modifier.TRANSIENT & ~Modifier.NATIVE)).
                append(SPACE).
                append(getDeclaration(executable)).
                append(getParameters(executable)).
                append(getException(executable)).
                append(SPACE).
                append('{').
                append(SEPARATOR).
                append(TAB).
                append(TAB).
                append(getBody(executable)).
                append(';').
                append(SEPARATOR).
                append(TAB).
                append('}').
                append(SEPARATOR);
        return builder.toString();
    }

    /**
     * Generates body function of an executable object given
     *
     * @param executable executable object which arguments and exceptions are returned
     * @return a string containing body function
     */
    private static String getBody(Executable executable) {
        if (executable instanceof Method) {
            return "return" + SPACE + getReturn(((Method) executable).getReturnType());
        } else {
            return "super" + getArgumentsForConstructor(executable);
        }
    }

    /**
     * Generates exceptions signatures of an executable object given
     *
     * @param executable executable object which arguments and exceptions are returned
     * @return a string containing exceptions signatures
     */
    private static String getException(Executable executable) {
        StringBuilder builder = new StringBuilder();
        Class<?>[] exceptionTypes = executable.getExceptionTypes();
        if (exceptionTypes.length > 0) {
            builder.append(SPACE).
                    append("throws").
                    append(SPACE);
        }
        builder.append(Arrays.stream(exceptionTypes).
                map(Class::getCanonicalName).
                collect(Collectors.joining(COMMA_SPACE)));
        return builder.toString();
    }

    /**
     * Joins with prefix symbol, coma and suffix symbol an array with a given function applied to each argument
     *
     * @param <T>      an array type
     * @param param    an initial array
     * @param pref     prefix string for concatenation
     * @param suf      suffix string for concatenation
     * @param function a function to apply to an array's elements  of type {@code T} turning it to a string
     * @return a result of concatenation of the elements arisen from function application
     */
    private static <T> String lambdaParameters(T[] param, Function<T, String> function, String pref, String suf) {
        return Arrays.stream(param).
                map(function).
                collect(Collectors.joining(COMMA_SPACE, pref, suf));
    }

    /**
     * Joins with comma and default bracket ('(' and ')') an parameters of executable with a given function applied to each argument
     *
     * @param executable executable object
     * @param function   a function to apply to an array parameters of  turning it to a string
     * @return a result of concatenation of the elements arisen from function application
     */
    private static String getParametersDefaultBracket(Executable executable, Function<Parameter, String> function) {
        return lambdaParameters(executable.getParameters(), function, "(", ")");
    }

    /**
     * Generates arguments of an executable object given
     *
     * @param executable executable object
     * @return a string containing arguments of executable
     */
    private static String getParameters(Executable executable) {
        return getParametersDefaultBracket(executable, p -> p.toString().replace("$", "."));
    }

    /**
     * Generates a constructor body for a specified constructo
     *
     * @param executable constructor object
     * @return a string containing generated constructor body
     */
    private static String getArgumentsForConstructor(Executable executable) {
        return getParametersDefaultBracket(executable, Parameter::getName);
    }

    /**
     * Generate and writes a constructors a class using a given writer.
     *
     * @param token  a token of a class being implemented
     * @param writer a writer being used to write the results
     * @throws ImplerException if there are no appropriate constructors in a class to implement
     * @throws IOException     if an error occurred trying to write using a given writer
     */
    private static void overrideConstructors(Class<?> token, Writer writer) throws ImplerException, IOException {
        Constructor<?>[] constructors = Arrays.stream(token.getDeclaredConstructors()).
                filter(constructor -> !Modifier.isPrivate(constructor.getModifiers())).
                toArray(Constructor[]::new);
        if (constructors.length == 0) {
            throw new ImplerException("Constructors not found in class " + token.getCanonicalName());
        }
        for (Constructor<?> c : constructors) {
            writer.write(getExecutable(c));
        }
    }

    /**
     * A helping function to filter a set of methods of a class being implemeneted.
     * It is filtered with a predicate depending on the flag and wrapped by a {@link MethodWrapper}
     *
     * @param flag    extending class is interface
     * @param methods array of methods extending class
     * @param set     a hashset to collect the results
     */
    private void filterAbstractMethod(Boolean flag, Method[] methods, Set<MethodWrapper> set) {
        Arrays.stream(methods)
                .filter(method -> flag ? Modifier.isAbstract(method.getModifiers()) :
                        !Modifier.isStatic(method.getModifiers())).map(MethodWrapper::new)
                .collect(Collectors.toCollection(() -> set));
    }

    /**
     * A private class that stores a {@link Method}.
     * Main feature differentiang it from a simple {@link Method}
     * it's {@link MethodWrapper#equals(Object)} and {@link MethodWrapper#hashCode()} methods
     */
    private class MethodWrapper {
        /**
         * A method being stored inside a wrapper
         */
        private Method method;
        /**
         * Integer constant for calculate hash
         */
        private final static int BASE = 31;

        /**
         * Constructs a method wrapper by a given method
         *
         * @param method method to store
         */
        MethodWrapper(Method method) {
            this.method = method;
        }

        /**
         * Override equals method.
         * Compares method wrappers by their method's names, parameter types and return types
         *
         * @param obj a method wrapper to compare
         * @return whether a method wrapper given equals the stored one
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof MethodWrapper) {
                MethodWrapper other = (MethodWrapper) obj;
                return Arrays.equals(method.getParameterTypes(), other.method.getParameterTypes()) &&
                        method.getReturnType().equals(other.method.getReturnType()) &&
                        method.getName().equals(other.method.getName());
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return Arrays.hashCode(method.getParameterTypes()) + BASE * method.getReturnType().hashCode() +
                    BASE * BASE * method.getName().hashCode();
        }

        /**
         * Gets a method stored
         *
         * @return a method stored
         */
        public Method getMethod() {
            return method;
        }
    }

    /**
     * Write methods of class class that is to be implemented
     *
     * @param token  a token of a class that is being implemented
     * @param writer a writer used  to write a string generated
     * @throws IOException if an error occurred trying to write using a writer
     */
    private void overrideAbstractMethod(Class<?> token, Writer writer) throws IOException {
        HashSet<MethodWrapper> methods = new HashSet<>();
        filterAbstractMethod(!token.isInterface(), token.getMethods(), methods);
        while (token != null) {
            filterAbstractMethod(!token.isInterface(), token.getDeclaredMethods(), methods);
            token = token.getSuperclass();
        }
        for (MethodWrapper method : methods) {
            writer.write(getExecutable(method.getMethod()));
        }
    }

    /**
     * Returns a simple name of an implementation class
     *
     * @param token a token of a class that is being implemented
     * @return a simple name of an implementation class
     */
    private static String getClassName(Class<?> token) {
        return token.getSimpleName() + "Impl";
    }

    /**
     * Write header of class (package name and class declaration) using writer given
     *
     * @param token  a token of a class that is being implemented
     * @param writer a writer used  to write a string generated
     * @throws IOException if an error occurred trying to write using a writer
     */
    private static void writeStartPart(Class<?> token, Writer writer) throws IOException {
        writePackage(token, writer);
        writeStartClass(token, writer);
    }

    /**
     * Write class declaration using writer given
     *
     * @param token  a token of a class that is being implemented
     * @param writer a writer used  to write a string generated
     * @throws IOException if an error occurred trying to write using a writer
     */
    private static void writeStartClass(Class<?> token, Writer writer) throws IOException {
        StringBuilder builder = new StringBuilder();
        String gen = getTemplate(token.getTypeParameters());
        builder.append("public class").
                append(SPACE).
                append(getClassName(token)).
                append(gen).
                append(SPACE).
                append(token.isInterface() ? "implements" : "extends").
                append(SPACE).
                append(token.getCanonicalName()).
                append(gen).
                append('{').
                append(SEPARATOR).
                append(SEPARATOR);
        writer.write(builder.toString());
    }

    /**
     * Write package using writer given
     *
     * @param token  a token of a class that is being implemented
     * @param writer a writer used  to write a string generated
     * @throws IOException if an error occurred trying to write using a writer
     */
    private static void writePackage(Class<?> token, Writer writer) throws IOException {
        writer.write("package " + token.getPackageName() + ";" + SEPARATOR + SEPARATOR);
    }

    /**
     * Write close bracket and separator to end of file using writer given
     *
     * @param writer a writer used  to write a string generated
     * @throws IOException if an error occurred trying to write using a writer
     */
    private static void writeEndPart(Writer writer) throws IOException {
        writer.write("}" + SEPARATOR);
    }

    /**
     * Creates the directories for the file path if they don't exist
     *
     * @param path file's path
     * @throws ImplerException if error occurred trying to create folders
     */
    private static void createDirectories(Path path) throws ImplerException {
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new ImplerException("Unable to implementInterface directories for output file", e);
            }
        }
    }

    /**
     * Gets default return value of given class
     *
     * @param type class to get default value
     * @return {@link String} representing value
     */
    private static String getReturn(Class<?> type) {
        if (void.class.equals(type)) {
            return "";
        } else if (boolean.class.equals(type)) {
            return "false";
        } else if (type.isPrimitive()) {
            return "0";
        } else {
            return "null";
        }
    }

    /**
     * Implements a given class and stores it in a given path.
     * Usage: {@code <options> <class name> <path to store>}
     * Without any flags you get a java source file implementation.
     * And using {@code -jar} option you get a jar file implementation.
     *
     * @param args arguments of a described rules
     */
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            System.out.println("We need 2 or 3 arguments");
            return;
        }
        try {
            if (args.length == 2) {
                Implementor imp = new Implementor();
                imp.implement(Class.forName(args[0]), Paths.get(args[1]));
            } else if ("-jar".equals(args[0])) {
                Implementor imp = new Implementor();
                imp.implementJar(Class.forName(args[1]), Paths.get(args[2]));
            }
        } catch (ImplerException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Incorrect class name: " + args[1]);
        }
    }


    /**
     * Writes <tt>file</tt> to <tt>.jar</tt> file <tt>jarFile</tt>.
     *
     * @param token   a token of a class that is being implemented
     * @param jarFile {@link Path} to .jar file where writes file
     * @param tmp     {@link Path} to file needs to be written to jarFile
     * @throws IOException if an error occurred trying to write to jarFile
     */
    private static void createJarFile(Class<?> token, Path jarFile, Path tmp) throws IOException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream out = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            out.putNextEntry(new ZipEntry(token.getCanonicalName().replace('.', '/') + "Impl.class"));
            Files.copy(tmp.resolve(getPath(tmp, token, "class")), out);
        }
    }

    /**
     * Generates a path where an implementation class should be considering its package
     *
     * @param root   a directory path where an implementation class package should be
     * @param token  a token of a class that is being implemented
     * @param suffix an extension of an implementation file
     * @return a path where an implementation class should be
     */
    private static Path getPath(Path root, Class<?> token, String suffix) {
        return root.resolve(token.getPackageName().replace('.', File.separatorChar))
                .resolve(getClassName(token) + "." + suffix);
    }


    /**
     * Generates and compile implementation class
     *
     * @param token               a token of a class that is being implemented
     * @param pathToTempDirectory a directory path where an implementation class package should be
     */
    private void implementAndCompile(Class<?> token, Path pathToTempDirectory) throws ImplerException {
        String cp;
        try {
            CodeSource source = token.getProtectionDomain().getCodeSource();
            if (source == null) {
                cp = ".";
            } else {
                cp = Path.of(source.getLocation().toURI()).toString();
            }
        } catch (URISyntaxException e) {
            throw new ImplerException("Cannot resolve classpath " + e.getMessage());
        }
        implement(token, pathToTempDirectory);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Java compiler not found");
        }
        String[] args = new String[]{
                "-cp", System.getProperty("java.class.path") + File.pathSeparator + pathToTempDirectory + File.pathSeparator + cp,
                pathToTempDirectory.resolve(getPath(pathToTempDirectory, token, "java")).toString()
        };
        int compileResult = compiler.run(null, null, null, args);

        if (compileResult != 0) {
            throw new ImplerException("Failed to compile java classes, compiler returned " + compileResult);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        createDirectories(jarFile);

        Path tempDir;
        try {
            tempDir = Files.createTempDirectory(jarFile.toAbsolutePath().getParent(), "tmp");
        } catch (IOException e) {
            throw new ImplerException("Failed to make a temporary directory" + e.getMessage());
        }
        jarFile = getPath(jarFile, token, "jar");
        try {
            implementAndCompile(token, tempDir);
            createJarFile(token, jarFile, tempDir);
        } catch (IOException e) {
            throw new ImplerException("Cannot create jar file with implementation: " + e.getMessage());
        }
        try {
            Files.walk(tempDir).map(Path::toFile).forEach(File::delete);
        } catch (IOException e) {
            throw new ImplerException("Failed deleting temporary files in " + tempDir.toString());
        }
    }
}
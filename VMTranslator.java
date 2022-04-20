import java.io.File;
import java.io.FileWriter;

/**
 * This is the main program that drives the translation process, using the services of a <code>Parser<code> and a
 * <code>CodeWriter<code>. The program gets the name of the input source file, say Prog<code>.vm<code>, from the
 * command-line argument. It constructs a <code>Parser<code> for parsing the input file Prog<code>.vm<code> and creates
 * an output file, Prog<code>.asm<code>, into which it will write the translated assembly instructions. The program then
 * enters a loop that iterates through the VM commands in the input file. For each command, the program uses the
 * <code>Parser<code> and the <code>CodeWriter<code> services for parsing the command into its fields and then
 * generating from them a sequence of assembly instructions. The instructions are written into the output
 * Prog<code>.asm<code> file.
 *
 * @author Maarten Derks
 */
class VMTranslator {

    private static final int MAX_ARGS = 1;
    private static final int FIRST_ARG = 0;
    private static final int FIRST_CHAR = 0;
    private static final String VM_EXT = ".vm";
    private static final String ASM_EXT = ".asm";
    private static final String CRLF = "\r\n";

    public static void main(String[] args) throws Exception {
        if (args.length == MAX_ARGS) {
            File inputFile = new File(args[FIRST_ARG]);
            if (inputFile.exists()) {
                String fileName = inputFile.getName();
                if (fileName.endsWith(VM_EXT)) {
                    if (Character.isUpperCase(fileName.charAt(FIRST_CHAR))) {
                        FileWriter fileWriter = new FileWriter(inputFile.getParent() + "/" + fileName.substring(FIRST_CHAR, fileName.indexOf('.')) + ASM_EXT);
                        Parser parser = new Parser(inputFile);
                        while (parser.hasMoreLines()) {
                            parser.advance();
                            fileWriter.write(parser.currentCommand + CRLF);
                        }
                        fileWriter.close();
                    } else {
                        System.out.println("First character in file name must be an uppercase letter");
                    }
                } else {
                    System.out.println("Invalid file extension");
                }
            } else  {
                System.out.println("No such file or directory");
            }
        } else {
            System.out.println("Invalid number of arguments");
        }
    }
}
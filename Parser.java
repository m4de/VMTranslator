import java.io.File;
import java.util.Scanner;

/**
 * This module handles the parsing of a single <code>.vm</code> file. The parser provides services for reading a VM
 * command, unpacking the command into its various components, and providing convenient access to these components. In
 * addition, the parser ignores all white space and comments. The parser is designed to handle all the VM commands,
 * including the branching and function command that will be implemented in chapter 8.
 *
 * For example, if the current command is <code>push local 2</code>, then calling <code>arg1()</code> and
 * <code>arg2()</code> would return, respectively, <code>"local"</code> and <code>2</code>. If the current command is
 * <code>add</code>, then calling <code>arg1()</code> would return <code>"add"</code>, and <code>arg2()</code> would not
 * be called.
 *
 * @author Maarten Derks
 */
class Parser {

    private Scanner sc;
    private String cmd;

    /**
     * Opens the input file / stream, and gets ready to parse it.
     *
     * @param file Input file / stream
     * @throws Exception
     */
    Parser(File file) throws Exception {
        sc = new Scanner(file);
    }

    /**
     * Are there more lines in the input?
     *
     * @return boolean
     */
    boolean hasMoreLines() {
        return sc.hasNextLine();
    }

    /**
     * Reads the next command from the input and makes it the current command.
     * This routing should be called only if {@link #hasMoreLines() hasMoreLines} is true.
     * Initially there is no current command.
     */
    void advance() {
        do {
            cmd = sc.nextLine().replaceFirst("//.+", "").trim();
        } while (cmd.equals("") || cmd.startsWith("//"));
    }

    /**
     * Returns a constant representing the type of the current command.
     * C_ARITHMETIC is returned for all the arithmetic/logical commands.
     *
     * @return  constant representing the type of the current command
     */
    CommandType commandType() {
        if (cmd.startsWith("push")) return CommandType.C_PUSH;
        if (cmd.startsWith("pop")) return CommandType.C_POP;
        if (cmd.startsWith("label")) return CommandType.C_LABEL;
        if (cmd.startsWith("goto")) return CommandType.C_GOTO;
        if (cmd.startsWith("if-goto")) return CommandType.C_IF;
        if (cmd.startsWith("function")) return CommandType.C_FUNCTION;
        if (cmd.startsWith("call")) return CommandType.C_CALL;
        if (cmd.startsWith("return")) return CommandType.C_RETURN;
        return CommandType.C_ARITHMETIC;
    }

    /**
     * Returns the first argument of the current command. In the case of C_ARITHMETIC,
     * the command itself (add, sub, etc.) is returned.
     * Should not be called if the current command is C_RETURN.
     *
     * @return  first argument of the current command
     */
    String arg1() {
        if (commandType() == CommandType.C_ARITHMETIC) return cmd;
        return cmd.split("\\s+")[1];
    }

    /**
     * Returns the second argument of the current command. Should be called only
     * if the current command is C_PUSH, C_POP, C_FUNCTION, or C_CALL.
     *
     * @return second argument of the current command
     */
    int arg2() {
        return Integer.parseInt(cmd.split("\\s+")[2]);
    }
}

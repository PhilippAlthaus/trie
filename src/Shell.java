import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command line application to handle basic operations of a trie, i.e. creation, insertion,
 * deletion, etc.
 */
public class Shell {

  // smallest possible value for entries
  private static final int MIN_POINTS_VALUE = 0;

  // most common error messages
  private static final String WRONG_POINTS_VALUE_MESSAGE =
      "Error! Only numbers greater than " + MIN_POINTS_VALUE + " are allowed.";
  private static final String WRONG_INPUT_PARAMETERS_MESSAGE =
      "Error! Wrong input parameters. Use <help> for further instructions";
  private static final String ENTRY_DOESNT_EXIST_MESSAGE = "Error! This entry does not exist.";
  private static final String ONLY_LOWER_CASE_ALLOWED_MESSAGE =
      "Error! Only lower case letters [a-z] are allowed.";

  private Shell() {
    // prohibit instantiation
    throw new AssertionError();
  }

  public static void main(String[] args) throws IOException {
    final BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
    execute(stdin);
  }

  private static void execute(BufferedReader stdin) throws IOException {
    Trie passedData = new Trie();
    boolean run = true;

    while (run) {
      System.out.print("trie> ");
      final String input = stdin.readLine();
      if (input == null) {
        break;
      }
      // split input on white spaces
      final String[] tokens = input.trim().split("\\s+");
      final String firstInput = tokens[0];
      final ShellCommand command = identifyCommand(firstInput, tokens);

      switch (command) {
        case NEW:
          passedData = new Trie();
          break;
        case ADD:
          addEntry(passedData, tokens);
          break;
        case CHANGE:
          changeEntry(passedData, tokens);
          break;
        case DELETE:
          deleteEntry(passedData, tokens);
          break;
        case POINTS:
          points(passedData, tokens);
          break;
        case TRIE:
          System.out.println(passedData.toString());
          break;
        case HELP:
          help();
          break;
        case QUIT:
          run = false;
          break;
        case WRONG_PARAMETER:
          System.out.println(WRONG_INPUT_PARAMETERS_MESSAGE);
          break;
        default:
          System.out.println(
              "Error! This command does not exist. " + "Use help for further instructions.");
          break;
      }
    }
  }

  /**
   * Checks if a given command is valid, i.e. it exists and has the associated number of parameters.
   */
  private static ShellCommand identifyCommand(String possibleCommand, String[] parameters) {
    possibleCommand = possibleCommand.toLowerCase();
    ShellCommand identifiedCommand = ShellCommand.UNKNOWN;

    if (possibleCommand.trim().isEmpty()) {
      // check if the command is an emptry string
      return ShellCommand.UNKNOWN;
    }
    for (ShellCommand cmd : ShellCommand.values()) {
      if (cmd.getCommandAsString().startsWith(possibleCommand)) {
        identifiedCommand = cmd;
      }
    }
    if (identifiedCommand == ShellCommand.UNKNOWN) {
      // command does not exist
      return ShellCommand.UNKNOWN;
    }
    if (identifiedCommand.getParameterNumber() == parameters.length) {
      return identifiedCommand;
    } else {
      // command exists but has the wrong number of parameters
      return ShellCommand.WRONG_PARAMETER;
    }
  }

  /**
   * Returns true if the string consists only of characters [a-z], i.e., ASCII-values are between 97
   * and 122.
   */
  private static boolean isOnlyLowerCase(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) < 97 || str.charAt(i) > 122) {
        // smallest ASCII-value: 'a' = 97
        // biggest ASCII-value: 'z' = 122
        return false;
      }
    }
    return true;
  }

  /**
   * Helper method for the add command.
   */
  private static void addEntry(Trie trie, String[] tokens) {
    final String name = tokens[1];
    if (!isOnlyLowerCase(name)) {
      System.out.println(ONLY_LOWER_CASE_ALLOWED_MESSAGE);
      return;
    }

    final String possiblePointsValue = tokens[2];
    int points = 0;
    try {
      points = Integer.parseInt(possiblePointsValue);
    } catch (NumberFormatException e) {
      // input was not a number
      System.out.println(WRONG_POINTS_VALUE_MESSAGE);
      return;
    }
    if (points < MIN_POINTS_VALUE) {
      System.out.println(WRONG_POINTS_VALUE_MESSAGE);
      return;
    }

    if (trie.add(name, points)) {
      trie.add(name, points);
    } else {
      System.out.println("Error! This entry already exists.");
    }
  }

  /**
   * Helper method for the change command.
   */
  private static void changeEntry(Trie trie, String[] tokens) {
    final String name = tokens[1];
    if (!isOnlyLowerCase(name)) {
      System.out.println(ONLY_LOWER_CASE_ALLOWED_MESSAGE);
      return;
    }

    final String possiblePointsValue = tokens[2];
    int points = 0;
    try {
      points = Integer.parseInt(possiblePointsValue);
    } catch (NumberFormatException e) {
      // input was not a number
      System.out.println(WRONG_POINTS_VALUE_MESSAGE);
      return;
    }
    if (points < MIN_POINTS_VALUE) {
      System.out.println(WRONG_POINTS_VALUE_MESSAGE);
      return;
    }
    // check if the name exists
    if (trie.change(name, points)) {
      trie.change(name, points);
    } else {
      System.out.println(ENTRY_DOESNT_EXIST_MESSAGE);
    }
  }

  /**
   * Helper method for the delete command.
   */
  private static void deleteEntry(Trie trie, String[] tokens) {
    final String name = tokens[1];
    if (!isOnlyLowerCase(name)) {
      System.out.println(ONLY_LOWER_CASE_ALLOWED_MESSAGE);
      return;
    }
    if (trie.delete(name)) {
      trie.delete(name);
    } else {
      System.out.println(ENTRY_DOESNT_EXIST_MESSAGE);
    }
  }

  /**
   * Helper method for the points command.
   */
  private static void points(Trie trie, String[] tokens) {
    final String name = tokens[1];
    if (!isOnlyLowerCase(name)) {
      System.out.println(ONLY_LOWER_CASE_ALLOWED_MESSAGE);
      return;
    } else if (trie.points(name) != null) {
      System.out.println(trie.points(name));
    } else {
      System.out.println(ENTRY_DOESNT_EXIST_MESSAGE);
    }

  }

  /**
   * Helper method for the help command.
   */
  private static void help() {
    System.out.println("The following commands are possible: \n");

    System.out.println("new: \n" + "\t Creates a new trie.");

    System.out.println(
        "add <name> <points>: \n" + "\t Creates a new entry with <name> and <points>.");

    System.out.println(
        "change <name> <points>: \n" + "\t Changes the points of the entry <name> to <points>.");

    System.out.println("delete <name>: \n" + "\t Deletes the entry <name>.");

    System.out.println("points <name>: \n" + "\t Returns the point value of the entry <name>.");

    System.out.println("trie: \n" + "\t Returns the trie.");

    System.out.println("quit: \n" + "\t Terminates the application.");

    System.out.println("\nAlso consider: \n" + "<name> should be lowercase. \n"
        + "<points> should be greater than zero."
        + "It is not possible to override existing entries.\n\n");
  }

}

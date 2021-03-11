/**
 * Represents all possible commands in lower case letters and the associated number of parameters
 * (including the command itself).
 */
enum ShellCommand {
  NEW("new", 1),
  ADD("add", 3),
  CHANGE("change", 3),
  DELETE("delete", 2),
  POINTS("points", 2),
  TRIE("trie", 1),
  HELP("help", 1),
  QUIT("quit", 1),
  WRONG_PARAMETER("wrong", 0),
  UNKNOWN("unknown", 0);

  private final String command;
  private final int numberOfExpectedParameters;

  private ShellCommand(String command, int numberOfExpectedParameters) {
    this.command = command;
    this.numberOfExpectedParameters = numberOfExpectedParameters;
  }

  int getParameterNumber() {
    return numberOfExpectedParameters;
  }

  String getCommandAsString() {
    return command;
  }

}

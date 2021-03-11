/**
 * Speichert alle Befehle als String in Kleinbuchstaben, sowie die Anzahl an
 * Parametern (den Befehl selbst eingeschlossen), die die jeweiligen Befehle benoetigen.
 */
enum ShellCommand {
  NEW("new", 1),
  ADD("add", 3),
  CHANGE("change", 3),
  DELETE("delete", 2),
  POINTS("points",2),
  TRIE("trie", 1),
  HELP("help", 1),
  QUIT("quit", 1),
  // zu wenig/viele Parameter eingegeben
  WRONG_PARAMETER("wrong", 0),
  // kein gueltiger Befehl eingegeben
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

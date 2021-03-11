import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Kommandoschnittstelle zur Benutzung der Klasse Trie.
 */
public class Shell {

  // kleinstmoeglicher Punktwert fuer Eintraege im Trie
  private static final int MIN_POINTS_VALUE = 0;

  // haeufigste Fehlermeldungen
  private static final String WRONG_POINTS_VALUE_MESSAGE =
      "Error! Es sind nur ganze Zahlen groesser gleich " + MIN_POINTS_VALUE
      + " als Eingabe fuer Punkte erlaubt.";
  private static final String WRONG_INPUT_PARAMETERS_MESSAGE =
      "Error! Dieser Befehl erwartet andere Parameter. Benutzen Sie help fuer mehr Informationen.";
  private static final String ENTRY_DOESNT_EXIST_MESSAGE =
      "Error! Dieser Eintrag existiert nicht.";
  private static final String ONLY_LOWER_CASE_ALLOWED_MESSAGE =
      "Error! Es sind nur Kleinbuchstaben im Bereich [a-z] als Eingabe fuer Namen erlaubt.";

  private Shell() {
    // Utility-Klasse, das Erstellen von Objekten ist nicht vorgesehen
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
          System.out.println("Error! Dieser Befehl existiert nicht. "
              + "Benutzen Sie help fuer eine Liste aller moeglichen Befehle.");
          break;
      }
    }
  }
  
  /**
   * Prueft, ob ein String ein Praefix eines moeglichen Befehls ist. Ist dies der
   * Fall wird zusaetzlich geprueft, ob der Benutzer die korrekte Anzahl an Parametern
   * uebergeben hat. Bei Uebereinstimmung wird der passende Befehl zurueckgegeben.
   * Existiert der Befehl nicht oder wurden zu wenig/viele Parameter uebergeben wird
   * ein "Fehler"-Befehl zurueckgegeben.
   */
  private static ShellCommand identifyCommand(String possibleCommand, String[] parameters) {
    possibleCommand = possibleCommand.toLowerCase();
    ShellCommand identifiedCommand = ShellCommand.UNKNOWN;
    
    if (possibleCommand.trim().isEmpty()) {
      // leere Eingabe abfangen
      return ShellCommand.UNKNOWN;
    }
    for (ShellCommand cmd : ShellCommand.values()) {
      if (cmd.getCommandAsString().startsWith(possibleCommand)) {
        identifiedCommand = cmd;
      }
    }
    if (identifiedCommand == ShellCommand.UNKNOWN) {
      // Befehl existiert nicht
      return ShellCommand.UNKNOWN;
    }
    if (identifiedCommand.getParameterNumber() == parameters.length) {
      return identifiedCommand;
    } else {
      // Befehl hat falsche Parameteranzahl uebergeben bekommen
      return ShellCommand.WRONG_PARAMETER;
    }
  }

  /**
   * Gibt true zurueck, wenn ein String ausschliesslich aus chars im Bereich [a-z]
   * besteht, d.h. die ASCII-Werte liegen zwischen 97 und 122.
   */
  private static boolean isOnlyLowerCase(String str) {
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) < 97 || str.charAt(i) > 122) {
        // ASCII-Wert fuer den kleinsten Wert: 'a' = 97
        // ASCII-Wert fuer den groessten Wert: 'z' = 122
        // Ist ein Wert ausserhalb dieses Intervalls stellt dies keine korrekte Eingabe dar
        return false;
      }
    }
    return true;
  }
  
  /**
   * Hilfsmethode fuer den Befehl "add". Fuegt in den uebergebenen Trie einen
   * neuen Eintrag ein. Prueft ausserdem, ob alle Eingaben korrekt sind und gibt
   * bei falschen Eingaben Fehlermeldungen aus. Hat der Benutzer fuer den zweiten
   * Parameter keine ganze Zahl eingegeben wird die dadurch auftretende NumberFormatException
   * gefangen und ein Hinweis ausgegeben.
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
      // Eingabe war kein Integer
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
      System.out.println("Error! Dieser Eintrag existiert schon.");
    }
  }

  /**
   * Hilfsmethode fuer den Befehl "change". Aendert im uebergebenen Trie den Punktwert
   * eines bestehenden Eintrags. Es werden alle Eingaben auf Korrektheit geprueft und
   * bei falschen Eingaben Fehlermeldungen ausgegeben. Wird als zweiter Parameter keine
   * Zahl eingegeben tritt eine NumberFormatException auf, die gefangen wird und ein
   * Hinweis an den Benutzer wird ausgegeben.
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
      // Eingabe war kein Integer
      System.out.println(WRONG_POINTS_VALUE_MESSAGE);
      return;
    }
    if (points < MIN_POINTS_VALUE) {
      System.out.println(WRONG_POINTS_VALUE_MESSAGE);
      return;
    }
    // Pruefe, ob der eingegebene Name existiert
    if (trie.change(name, points)) {
      trie.change(name, points);
    } else {
      System.out.println(ENTRY_DOESNT_EXIST_MESSAGE);
    }
  }

  /**
   * Hilfsmethode fuer den Befehl "delete". Loescht im uebergebenen Trie einen bestehenden
   * Eintrag. Es werden alle Eingaben geprueft und bei falschen Eingaben Fehlermeldungen
   * ausgegeben.
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
   * Hilfsmethode fuer den Befehl "points". Gibt den Punktwert eines Eintrags aus.
   * Es werden alle Eingaben geprueft und bei falschen Eingaben Fehlermeldungen ausgegeben.
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
   * Hilfsmethode fuer den Befehl "help". Gibt alle moeglichen Befehle sowie deren
   * Funktion auf der Konsole aus, sofern die Eingabeparameter korrekt sind.
   */
  private static void help() {
    System.out.println("Folgende Eingaben sind moeglich: \n");

    System.out.println("new: \n"
        + "\t Legt einen neuen Trie an und verwirft die alte Datenstruktur.");

    System.out.println("add <name> <punkte>: \n"
        + "\t Fuegt einen neuen Eintrag <name> mit Punktwert <punkte> hinzu.");

    System.out.println("change <name> <punkte>: \n"
        + "\t Aendert den Punktwert <punkte> des Eintrags <name>.");

    System.out.println("delete <name>: \n"
        + "\t Loescht den Eintrag an der Stelle <name>");

    System.out.println("points <name>: \n"
        + "\t Gibt den Punktwert des Eintrags <name> aus.");

    System.out.println("trie: \n"
        + "\t Gibt den Inhalt des Tries aus.");

    System.out.println("quit: \n"
        + "\t Beendet das Programm.");

    System.out.println("\nBitte beachten Sie folgende Formalitaeten: \n"
        + "<name> akzeptiert nur kleingeschriebene Namen ohne Umlaute. \n"
        + "<punkte> akzeptiert nur positive ganze Zahlen."
        + "Bereits bestehende Eintraege koennen nicht mit neuen ueberschrieben werden.\n\n");
  }

}

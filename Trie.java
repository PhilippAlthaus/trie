/**
 * Diese Klasse dient zum Erstellen und Verwalten eines Tries. Ein Trie ist eine spezielle
 * Form eines Suchbaums mit einem relativ hohen Verzweigungsgrad. Beim Eintragen eines Wertes
 * in den Baum wird der zugehörige Schlüssel zeichenweise betrachtet. Für jedes Zeichen des
 * Schlüssels erfolgt ein Abstieg im Baum über die dem Zeichen entsprechende Kante. Jeder Knoten
 * kann somit genausoviele Kinder haben wie es Buchstaben im Alphabet der Schlüssel gibt. Hier
 * sind dies 26 Stück für die Kleinbuchstaben von [a-z]. Hat man den Schlüssel vollständig
 * abgearbeitet, so wird der Wert an der aktuellen Stelle im Baum eingetragen.
 */
public class Trie {
  
  private Node root;
  
  /**
   * Erzeugt einen neuen Trie, bestehend aus seinem Wurzelknoten.
   */
  public Trie() {
    this.root = new Node();
  }
  
  /**
   * Fuegt einen neuen Eintrag mit dem uebergeben Punktwert hinzu. Dies ist
   * nur moeglich, wenn der einzufuegende Eintrag noch nicht existiert.
   * 
   * @param key       Name des Eintrags
   * @param points    Punktwert des Eintrags
   * @return          true, falls das Einfuegen erfolgreich war
   */
  public boolean add(String key, Integer points) {
    Node currentNode = root;
    char currentLetter = ' ';
    
    for (int i = 0; i < key.length(); i++) {
      currentLetter = key.charAt(i);
      if (currentNode.getChild(currentLetter) == null) {
        // Knoten mit aktuellem Buchstaben existiert noch nicht
        new Node(currentLetter, currentNode);
      }
      currentNode = currentNode.getChild(currentLetter);
    }
    // ab jetzt: currentNode = Knoten mit dem letzten Buchstaben
    if (currentNode.getPoints() == null) {
      currentNode.setPoints(points);
      return true;
    } else {
      // Eintrag existiert bereits
      return false;
    }
  }
  
  /**
   * Loescht einen bestehenden Eintrag. Sollte der zu loeschende Eintrag nicht
   * existieren wird false zurueckgegeben.
   * 
   * @param key     Name des zu loeschenden Eintrags
   * @return        true, falls das Loeschen erfolgreich war
   */
  public boolean delete(String key) {
    if (root.find(key) != null) {
      Node lastNode = root.find(key);
      if (lastNode.getPoints() != null) {
        lastNode.deleteMe();
        return true;
      }
    }
    // Eintrag existiert nicht
    return false;
  }
  
  /**
   * Aendert den Punktwert eines bestehenden Eintrags. Dies ist nur moeglich,
   * wenn der Eintrag existiert.
   * 
   * @param key       Name des zu aendernden Eintrags
   * @param points    neuer Punktwert des Eintrags key
   * @return          true, falls das Aendern erfolgreich war
   */
  public boolean change(String key, Integer points) {
    if (root.find(key) != null) {
      Node keyNode = root.find(key);
      if (keyNode.getPoints() != null) {
        keyNode.setPoints(points);
        return true;
      }
    }
    // Eintrag existiert nicht
    return false;
  }
  
  /**
   * Gibt den Punktwert eines Eintrags zurueck. Gibt null zurueck, falls der
   * Eintrag nicht existiert.
   */
  public Integer points(String key) {
    if (root.find(key) != null) {
      return root.find(key).getPoints();
    }
    // Eintrag existiert nicht
    return null;
  }
  
  /**
   * Gibt den Inhalt des Tries zurueck.
   */
  @Override
  public String toString() {
    return root.toString();
  }
  
}
/**
 * Diese Klasse ist das Rueckgrat des Tries. Sie dient dem Erstellen und Verwalten
 * von Knoten innerhalb des Tries. Kanten zu Knoten werden implizit mithilfe einer
 * Instanzvariable modelliert.
 */
public class Node {
  
  private static final int ALPHABET_LAENGE = 26;
  
  private char ch;
  private Node[] children;
  private Node parent;
  private Integer points;
  
  /**
   * Erzeugt einen neuen Knoten. Sollte nur zum Erstellen des Wurzelknotens benutzt werden.
   */
  public Node() {
    this.ch = '+';
    this.children = new Node[ALPHABET_LAENGE];
  }

  /**
   * Erzeugt einen neuen Knoten anhand der uebergebenen Werte. Dient primaer dem
   * Erstellen von Kindknoten.
   * 
   * @param ch        Buchstabe im Bereich [a-z]. Repraesentiert die Kante zu diesem Knoten.
   * @param parent    Elternknoten des zu erzeugenden Knotens
   */
  public Node(char ch, Node parent) {
    this.ch = ch;
    this.children = new Node[ALPHABET_LAENGE];
    this.parent = parent;
    this.points = null;
    parent.setChild(ch, this);
  }

  /**
   * Setzt den Verweis an der Stelle ch im children-Array auf den Knoten child.
   */
  private void setChild(char ch, Node child) {
    this.children[ch - 'a'] = child;
  }

  /**
   * Gibt den Kindknoten an der Stelle ch zurueck. Existiert kein Kindknoten
   * an dieser Stelle wird null zurueckgegeben.
   */
  public Node getChild(char ch) {
    return children[ch - 'a'];
  }

  /**
   * Navigiert entsprechend der Zeichenfolge key durch den aktuellen Unterbaum und sucht
   * nach dem Knoten an dieser Stelle. Gibt null zurück, wenn kein passender Knoten existiert.
   * 
   * @param key    Name des zu suchenden Eintrags
   * @return       Knoten des letzten Buchstabens, null falls der Eintrag nicht existiert
   */
  public Node find(String key) {
    Node keyNode = this;
    for (int i = 0; i < key.length(); i++) {
      if (keyNode != null) {
        keyNode = keyNode.getChild(key.charAt(i));
      } else {
        // Eintrag existiert nicht
        return null;
      }
    }
    return keyNode;
  }

  /**
   * Entfernt den Punktwert des Knotens und loescht alle dadurch unnoetig gewordenen Knoten.
   */
  public void deleteMe() {
    points = null;
    cleanup();
  }

  /**
   * Loescht alle unnoetig gewordenen Knoten. Ein Knoten ist unnoetig, falls er keinen
   * Punktwert oder keine Kindknoten mehr hat. Die Wurzel ist nie unnoetig.
   */
  private void cleanup() {
    Node parentNode = parent;
    boolean nodeIsNeeded = (hasChildren() || points != null);
    // die Wurzel hat als einzigster Knoten keinen Elternknoten
    boolean isRoot = parentNode == null;
    
    if (!isRoot && !nodeIsNeeded) {
      parentNode.children[ch - 'a'] = null;
      parentNode.cleanup();
    }
  }
  
  /**
   * Gibt den aufgerufenen Knoten und (sofern vorhanden) den damit verbundenen
   * Teilbaum mit einem Tiefendurchlauf zurueck. Gibt es Kindknoten, so wird
   * dies durch runde Klammern repraesentiert. Sollte ein Knoten einen Punktwert
   * besitzen wird dieser hinter den ihm zugeordneten Buchstaben in eckigen Klammern
   * hinzugefuegt.
   */
  @Override
  public String toString() {
    final StringBuilder result = new StringBuilder();
    result.append(Character.toString(ch));
    if (getPoints() != null) {
      result.append("[" + getPoints() + "]");
    }
    
    if (hasChildren()) {
      result.append("(" + goDown() + ")");
    }
    return result.toString();
  }
  
  /**
   * Gibt die Kinder eines Knotens mit einem Tiefendurchlauf in alphabetischer
   * Reihenfolge zurueck. Die textuelle Darstellung der einzelnen Knoten erfolgt
   * mit {@link #toString()}.
   */
  private String goDown() {
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < ALPHABET_LAENGE; i++) {
      if (children[i] != null) {
        result.append(children[i].toString());
      }
    }
    return result.toString();
  }

  /**
   * Setzt den Punktwert eines Knotens auf den uebergebenen Wert.
   */
  public void setPoints(Integer points) {
    this.points = points;
  }

  /**
   * Gibt den Punktwert eines Knotens zurueck.
   */
  public Integer getPoints() {
    return points;
  }
  
  /**
   * Gibt true zurueck, falls ein Knoten mindestens einen Kindknoten hat.
   */
  private boolean hasChildren() {
    for (int i = 0; i < ALPHABET_LAENGE; i++) {
      if (children[i] != null) {
        return true;
      }
    }
    return false;
  }
  
}

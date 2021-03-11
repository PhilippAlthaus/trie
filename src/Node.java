/**
 * Representation of a node in a trie.
 */
public class Node {

  private static final int ALPHABET_LENGTH = 26;

  /**
   * Character of this node.
   */
  private char ch;

  /**
   * Child nodes of this node.
   */
  private Node[] children;

  /**
   * Parent node of this node.
   */
  private Node parent;

  /**
   * Point value of this node.
   */
  private Integer points;

  /**
   * Creates a new node.
   */
  public Node() {
    this.ch = '+';
    this.children = new Node[ALPHABET_LENGTH];
  }

  /**
   * Creates a new node according to the given parameters. Should only be used to create child
   * nodes.
   * 
   * @param ch Character from [a-z], represents the edge to this node
   * @param parent Parent node of this node
   */
  public Node(char ch, Node parent) {
    this.ch = ch;
    this.children = new Node[ALPHABET_LENGTH];
    this.parent = parent;
    this.points = null;
    parent.setChild(ch, this);
  }

  /**
   * Creates a child node at character index ch.
   */
  private void setChild(char ch, Node child) {
    this.children[ch - 'a'] = child;
  }

  /**
   * Returns the child at index ch. If there is no child returns {@code null} instead.
   */
  public Node getChild(char ch) {
    return children[ch - 'a'];
  }

  /**
   * Searches the children of this node for a given string. If the string exists the node containing
   * the last character is returned, otherwise {@code null}.
   * 
   * @param key name of the entry to be searched
   * @return node of the last character, if not existent {@code null}
   */
  public Node find(String key) {
    Node keyNode = this;
    for (int i = 0; i < key.length(); i++) {
      if (keyNode != null) {
        keyNode = keyNode.getChild(key.charAt(i));
      } else {
        // entry does not exist
        return null;
      }
    }
    return keyNode;
  }

  /**
   * Removes the point value of this node.
   */
  public void deleteMe() {
    points = null;
    cleanup();
  }

  /**
   * Removes unnecessary nodes. A node is unnecessary if it does not contain an associated point
   * value or it does not have any child nodes.
   */
  private void cleanup() {
    Node parentNode = parent;
    boolean nodeIsNeeded = (hasChildren() || points != null);
    // the root node has no parent node
    boolean isRoot = parentNode == null;

    if (!isRoot && !nodeIsNeeded) {
      parentNode.children[ch - 'a'] = null;
      parentNode.cleanup();
    }
  }

  /**
   * Returns a string representation of this node and its associated subtree in depth-first order.
   * Child nodes are denoted by round brackets and point values by square ones.
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
   * Returns the string representation of the children of a node with a depth-first traversal along
   * with {@link #toString()}.
   */
  private String goDown() {
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < ALPHABET_LENGTH; i++) {
      if (children[i] != null) {
        result.append(children[i].toString());
      }
    }
    return result.toString();
  }

  /**
   * Sets the point value of a node.
   */
  public void setPoints(Integer points) {
    this.points = points;
  }

  /**
   * Returns the point value of a node.
   */
  public Integer getPoints() {
    return points;
  }

  /**
   * Returns {@code true} if the node has at least one child node.
   */
  private boolean hasChildren() {
    for (int i = 0; i < ALPHABET_LENGTH; i++) {
      if (children[i] != null) {
        return true;
      }
    }
    return false;
  }

}

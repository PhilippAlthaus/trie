/**
 * Represents the trie data structure. A trie is a search tree for a limited set of keys.
 * Additionally one can store a point value to an existing entry.
 * 
 * @see https://en.wikipedia.org/wiki/Trie
 */
public class Trie {

  private Node root;

  /**
   * Creates a new trie.
   */
  public Trie() {
    this.root = new Node();
  }

  /**
   * Adds a new entry with the given point value. If this entry already exists it is not possible to
   * override it.
   * 
   * @param key name of the entry
   * @param points point value of the entry
   * @return {@code true} if the insertion was successful, {@code false} if the entry does already
   *         exists
   */
  public boolean add(String key, Integer points) {
    Node currentNode = root;
    char currentLetter = ' ';

    for (int i = 0; i < key.length(); i++) {
      currentLetter = key.charAt(i);
      if (currentNode.getChild(currentLetter) == null) {
        // node with current letter does not exist
        new Node(currentLetter, currentNode);
      }
      currentNode = currentNode.getChild(currentLetter);
    }

    if (currentNode.getPoints() == null) {
      currentNode.setPoints(points);
      return true;
    } else {
      // entry already exists
      return false;
    }
  }

  /**
   * Deletes an existing entry.
   * 
   * @param key name of the entry to be deleted
   * @return {@code true} if the deletion was successful, {@code false} if the entry does not exist
   */
  public boolean delete(String key) {
    if (root.find(key) != null) {
      Node lastNode = root.find(key);
      if (lastNode.getPoints() != null) {
        lastNode.deleteMe();
        return true;
      }
    }
    // entry does not exist
    return false;
  }

  /**
   * Changes the point value of a given entry. This is only possible if the entry exists.
   * 
   * @param key name of the entry to be changed
   * @param points new point value
   * @return {@code true} if the change was successful
   */
  public boolean change(String key, Integer points) {
    if (root.find(key) != null) {
      Node keyNode = root.find(key);
      if (keyNode.getPoints() != null) {
        keyNode.setPoints(points);
        return true;
      }
    }
    // entry does not exist
    return false;
  }

  /**
   * Returns the point value of a given entry. If the entry does not exist {@code null} is returned
   * instead.
   */
  public Integer points(String key) {
    if (root.find(key) != null) {
      return root.find(key).getPoints();
    }
    // entry does not exist
    return null;
  }

  /**
   * Returns a string representation of this trie.
   */
  @Override
  public String toString() {
    return root.toString();
  }

}

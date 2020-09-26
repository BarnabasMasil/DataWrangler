// --== CS400 File Header Information ==--
// Name: Barnabas Masil Adrian anak Christopher
// Email: adriananakch@wisc.edu
// Team: GA
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader:

/**
 * This is a generic class that takes 2 generic values which are the KeyType and the ValueType.
 * Instances of this class represent the key-value pairings
 * 
 * @author barnabas
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class Element<KeyType, ValueType> {

  private ValueType value;
  private KeyType key;

  public Element(ValueType value, KeyType key) {
    this.value = value;
    this.key = key;
  }

  public ValueType getValue() {
    return value;
  }

  public KeyType getKey() {
    return key;
  }

}

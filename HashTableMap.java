// --== CS400 File Header Information ==--
// Name: Barnabas Masil Adrian anak Christopher
// Email: adriananakch@wisc.edu
// Team: GA
// TA: Daniel Kiel
// Lecturer: Gary Dahl
// Notes to Grader:

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class represents the Hash Table Map that implements the MapADT interface with 2 generic
 * values
 * 
 * @author Barnabas
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  private LinkedList<Element<KeyType, ValueType>>[] hashTable;

  private int capacity = 0;
  private int size = 0;

  @SuppressWarnings("unchecked")
  public HashTableMap(int capacity) {
    hashTable = new LinkedList[capacity];
    this.capacity = capacity;

  }

  @SuppressWarnings("unchecked")
  public HashTableMap() {
    hashTable = new LinkedList[10];
    capacity = 10;
  }

  /**
   * This method adds elements into the hash table. It will return true if it can place an key-value
   * pair in the hash table and false otherwise
   * 
   * @param key   The KeyType to be paired with a value
   * @param value The ValueType to be paired with a key
   * 
   * @return true if elements can be added to the hash table and false otherwise
   */
  @Override
  public boolean put(KeyType key, ValueType value) {
    // Checks if the array needs to grow
    if ((double) (size + 1) / capacity >= 0.8) {
      growHashTable();
    }

    // Hashes the key by using the hasCode() method and modulus operator
    int hashIndex = Math.abs(key.hashCode()) % capacity;

    // Adding to an empty address in the array
    if (hashTable[hashIndex] == null) {

      hashTable[hashIndex] = new LinkedList<Element<KeyType, ValueType>>();
      hashTable[hashIndex].add(new Element<>(value, key));
      size++;
      return true;

    } else { // Adding to an existing linkedlist with the same hashIndex

      for (int i = 0; i < hashTable[hashIndex].size(); i++) {
        if (key.equals(hashTable[hashIndex].get(i).getKey())) {
          return false;
        }
      }
      hashTable[hashIndex].add(new Element<>(value, key));
      size++;
      return true;
    }
  }

  /**
   * This method returns a ValueType object with respect to its key that it was paired with
   * 
   * @param key The chosen KeyType
   * @return the ValueType object that it was paired with the key
   * 
   * @throws NoSuchElementException if a key argument has not pairs with any ValueType object
   */
  @Override
  public ValueType get(KeyType key) {

    // Hashes the key by using the hasCode() method and modulus operator
    int hashIndex = Math.abs(key.hashCode()) % capacity;
    ValueType temp;

    if (hashTable[hashIndex] == null)
      throw new NoSuchElementException("No such key-value pair found");

    for (int i = 0; i < hashTable[hashIndex].size(); i++) {
      if (key.equals(hashTable[hashIndex].get(i).getKey())) {
        temp = hashTable[hashIndex].get(i).getValue();
        return temp;
      }

      if (i == hashTable[hashIndex].size() - 1) {
        throw new NoSuchElementException("No such key-value pair found");
      }
    }
    return null;
  }

  /**
   * This method returns the number of key-value pairings currently present in the hash table
   * 
   * @return size
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * This method returns true if a key argument is paired with a valueType object in the hash table
   * and false otherwise
   * 
   * @param key The KeyType argument to be search for its valueType pairing
   * @return boolean
   */
  @Override
  public boolean containskey(KeyType key) {

    // Hashes the key by using the hasCode() method and modulus operator
    int hashIndex = Math.abs(key.hashCode()) % capacity;

    if (hashTable[hashIndex] == null)
      return false;

    for (int i = 0; i < hashTable[hashIndex].size(); i++) {
      if (key.equals(hashTable[hashIndex].get(i).getKey())) {
        return true;
      }
    }

    return false;
  }

  /**
   * this method removes a key-pairing according to the key and returns the valueType object from
   * the hash table
   * 
   * @param key The KeyType argument to be removed with its valueType pairing
   * @return temp The valueType object
   */
  @Override
  public ValueType remove(KeyType key) {
    // Hashes the key by using the hasCode() method and modulus operator
    int hashIndex = Math.abs(key.hashCode()) % capacity;
    ValueType temp;

    if (hashTable[hashIndex] == null)
      return null;

    if (hashTable[hashIndex].size() == 1) {
      temp = hashTable[hashIndex].get(0).getValue();
      hashTable[hashIndex] = null;
      size--;
      return temp;
    }

    for (int i = 0; i < hashTable[hashIndex].size(); i++) {
      if (key.equals(hashTable[hashIndex].get(i).getKey())) {
        temp = hashTable[hashIndex].get(i).getValue();
        hashTable[hashIndex].remove(i);
        size--;
        return temp;
      }
    }

    return null;
  }

  /**
   * This method clears and empties the hash table
   */
  @Override
  public void clear() {
    hashTable = new LinkedList[capacity];
    size = 0;
  }

  /**
   * This is a helper method to help for regrowing the hash table array twice its original capacity.
   */
  private void growHashTable() {

    LinkedList<Element<KeyType, ValueType>>[] temp;
    temp = new LinkedList[capacity * 2];

    for (int i = 0; i < capacity; i++) {
      if (hashTable[i] != null) {
        for (int j = 0; j < hashTable[i].size(); j++) {
          KeyType tempKey = hashTable[i].get(j).getKey();
          ValueType tempValue = hashTable[i].get(j).getValue();
          insert(tempKey, tempValue, temp);
        }
      }

    }

    capacity *= 2;
    this.hashTable = temp;
  }

  /**
   * This method is a helper method to help in the growth of the hash table where each key-value
   * pairings are rehashed according to the new capacity. The elements in the old hash tables are
   * then placed within the new hash table.
   * 
   * @param key       The KeyType of the key-value pairing
   * @param value     The ValueType of the key-value pairing
   * @param hashTable The new hash table
   * @return boolean
   */
  private boolean insert(KeyType key, ValueType value,
      LinkedList<Element<KeyType, ValueType>>[] hashTable) {
    int capacity = hashTable.length;
    int hashIndex = Math.abs(key.hashCode()) % (capacity);

    if (hashTable[hashIndex] == null) {
      hashTable[hashIndex] = new LinkedList<Element<KeyType, ValueType>>();
      hashTable[hashIndex].add(new Element<>(value, key));
      return true;
    } else {
      for (int i = 0; i < hashTable[hashIndex].size(); i++) {
        if (key.equals(hashTable[hashIndex].get(i).getKey())) {
          return false;
        }
      }
      hashTable[hashIndex].add(new Element<>(value, key));
      return true;
    }
  }
}
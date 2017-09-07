
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.io.*;

/**
 * A converter that reads in a file and stores the information in a map.
 */
public class InputConverter{

  /** A special purpose exception class to indicate errors when reading 
   *  the input for the FileCorrector.
   */
  public static class FormatException extends Exception {
    public FormatException(String msg) {
      super(msg);
    }
  }

   /** @param r The sequence of characters to parse 
   * @throws IOException for an io error while reading
   * @throws InputConverter.FormatException for an invalid line
   * @throws IllegalArgumentException if the provided reader is null
   */
  
  private BufferedReader reader;
  private String string;
  private double gpa;
  private Map<String, Integer> inputs = new TreeMap<String, Integer>();
  private ArrayList<String> obstacles = new ArrayList<String>();
  
  public InputConverter(Reader r) throws IOException, FormatException {
    if (r == null) throw new IllegalArgumentException();
    int counter = 0;
    reader = new BufferedReader(r);
    
    while (reader.ready()) {
      counter++;
      string = reader.readLine();
      string = string.trim();
      if(counter == 1) {
          if(string.equals(","))
              continue;
          else
              gpa = Double.parseDouble(string);
      }
      
      else if(counter <= 12) {
          int indexOfComma = string.indexOf(',');
          if (indexOfComma == -1 || !(indexOfComma == string.lastIndexOf(','))) {
            throw new FormatException("Line contains multiple or no commas");
          }
    
          if (string.charAt(0) == ',' || string.charAt(string.length() - 1) == ',') 
              continue;
          
          int firstWordEndIndex = -1;
          for (int i = indexOfComma - 1; i >= 0; i--) {
            if (!(string.charAt(i) == ' ')) {
              firstWordEndIndex = i;
              break;
            }
          }
          if (firstWordEndIndex == -1) throw new FormatException("Incorrect comma spacing formatting");
          
          String firstWord = string.substring(0, firstWordEndIndex + 1);
          firstWord = firstWord.toUpperCase();
          
          int numberStartIndex = -1;
          for (int i = indexOfComma + 1; i < string.length(); i++) {
            if (!(string.charAt(i) == ' ')) {
              numberStartIndex = i;
              break;
            }
          }
          if (numberStartIndex == -1) throw new FormatException("Incorrect comma spacing formatting");
          
          Character numberChar = string.charAt(numberStartIndex);
          int digit = Character.getNumericValue(numberChar);
          
          inputs.put(firstWord, digit);
          } else {
              if (string.equals(""))
                  continue;
              else 
                  string = string.toUpperCase();
                  obstacles.add(string);
          }
      }
  }

  /** Construct an InputConverter from a file.
   *
   * @param filename of file to read from
   * @throws IOException if error while reading
   * @throws InputConverter.FormatException for an invalid line
   * @throws FileNotFoundException if file cannot be opened
   */
  public static InputConverter make(String filename) throws IOException, FormatException {
      Reader r = new FileReader(filename);
      InputConverter ic;
      try {
          ic = new InputConverter(r);
      } finally {
          if (r != null) { r.close(); }
     }
      return ic;
  }
  /**
   * Return the gpa that the user inputs
   */
  public Double getGpa() {
      return gpa;
  }
  
   /**
   * Return the map of work inputs from the user.
   */
  public Map<String, Integer> getWorkInputs() {
      return inputs;
  }
  
  /**
   * Return the set of obstacle names.
   */
  public ArrayList<String> getObstacles() {
      return obstacles;             
  }
}


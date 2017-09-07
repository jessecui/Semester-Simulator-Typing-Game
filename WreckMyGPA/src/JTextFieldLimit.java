import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**This was pulled from StackOverFlow
 * http://stackoverflow.com/questions/3519151/how-to-limit-the-number-of-characters-in-jtextfield
 */
@SuppressWarnings("serial")
public class JTextFieldLimit extends PlainDocument {
    private int limit;

    JTextFieldLimit(int limit) {
    super();
    this.limit = limit;
    }

    public void insertString( int offset, String  str, AttributeSet attr ) 
        throws BadLocationException {
        if (str == null) return;

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
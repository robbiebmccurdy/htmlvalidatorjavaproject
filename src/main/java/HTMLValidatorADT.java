import java.io.FileNotFoundException;

public interface HTMLValidatorADT {
    public String printOpenTags();
    public String printCloseTags();
    public boolean validTag(String tag);
    public boolean voidTag(String tag);
    public boolean validateTags() throws FileNotFoundException;
    public boolean validateHTML() throws FileNotFoundException;
}

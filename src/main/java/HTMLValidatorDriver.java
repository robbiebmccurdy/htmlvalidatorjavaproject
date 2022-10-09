import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HTMLValidatorDriver {
    public static void main(String[] args) throws FileNotFoundException {
        HTMLValidator page = new HTMLValidator("page1.txt", "validTags.txt", "voidTags.txt");

        if(page.validateTags())
            System.out.println(("Validated HTML tags"));
        else
            System.out.println("Some invalid HTML tags");
        System.out.println("Size of open tags is " + page.getSizeOpenTags() + "\nSize of close tags is " + page.getSizeCloseTags());
        System.out.println("\n--------------\nOpen Tags\n" + page.printOpenTags());
        System.out.println("\n--------------\nClose Tags\n" + page.printCloseTags());

        if(page.validateHTML())
            System.out.println(("Validated HTML"));
        else
            System.out.println("Not Validated HTML");

    }
}

// CSCI 211
// Robert McCurdy
// Student ID 10715877
// Program 2
// In keeping with the UM Honor Code, I have neither given nor received assistance from anyone other
// than the instructor.


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class HTMLValidator {
    private Stack<String> openTags;
    private Stack<String> closeTags;
    private Stack<String> validateTags;
    private String filename;
    private ArrayList<String> validTags;
    private ArrayList<String> voidTags;

    /**
     * constructor: HTMLValidator - rename HTMLValidator_Starter - both class and constructor - as HTMLValidator
     *
     * @param filename
     * @param validHTML
     * @param voidHTML
     * @throws FileNotFoundException
     */

    public HTMLValidator(String filename, String validHTML, String voidHTML) throws FileNotFoundException {
        //TODO: Instantiate Stacks and ArrayLists.
        // Assign filename to filename instance variable. The filename is used in validateTags and validateHTML methods.
        // Populate ArrayLists with valid and void html5 tags.
        this.filename = filename;

        Scanner file = new Scanner(new File(validHTML));
        validTags = new ArrayList<>();
        voidTags = new ArrayList<>();
        openTags = new Stack<>();
        closeTags = new Stack<>();
        validateTags = new Stack<>();
        while (file.hasNext()) {
            validTags.add(file.next());
        }
        Scanner file2 = new Scanner(new File(voidHTML));
        while (file2.hasNext()) {
            voidTags.add(file2.next());
        }

    }

    public int getSizeOpenTags() {
        return openTags.size();
    }

    public int getSizeCloseTags() {
        return closeTags.size();
    }

    /**
     * method: printOpenTags prints contents of openTags stack
     *
     * @return
     */
    public String printOpenTags() {
        String output = "";
        // TODO: Use a temp stack and pop openTags, pushing on temp. Also, output value.
        // Then, to preserve contents of stack, pop from temp and push on openTags.
        Stack<String> temp = new Stack<String>();

        while (!openTags.isEmpty()) {
            temp.push(openTags.pop());
            output += temp.peek() + "\n";
        }

        while (!temp.isEmpty()) {
            openTags.push(temp.pop());
        }

        return output;
    }

    /**
     * method: printCloseTags prints contents of closeTags stack
     *
     * @return
     */
    public String printCloseTags() {
        String output = "";
        // TODO: Use a temp stack and pop closeTags, pushing on temp. Also, output value.
        // Then, to preserve contents of stack, pop from temp and push on closeTags.
        Stack<String> temp = new Stack<String>();

        while (!closeTags.isEmpty()) {
            temp.push(closeTags.pop());
            output += temp.peek() + "\n";
        }

        while (!temp.isEmpty()) {
            closeTags.push(temp.pop());
        }

        return output;
    }


    /**
     * method: validTag returns true if "tag" is a valid html5 tag. Otherwise, return false
     *
     * @param tag
     * @return
     */
    public boolean validTag(String tag) throws FileNotFoundException {
        // TODO: You may use ArrayList's contains method to determine if the tag is in the validTags ArrayList

        ArrayList<String> vta = new ArrayList<String>();
        Scanner file = new Scanner(new File("validTags.txt"));

        while (file.hasNext()) {
            vta.add(file.next());
        }

        String tag2 = tag.replace("/", "");

        if (vta.contains(tag2)) {
            return true;
        }

        file.close();

        return false;
    }

    /**
     * method: voidTag returns true if "tag" is a valid html5 void tag (doesn't require a closing tag). Otherwise, return false
     *
     * @param tag
     * @return
     */
    public boolean voidTag(String tag) throws FileNotFoundException {
        //TODO: You may use ArrayList's contains method to determine if the tag is in the voidTags ArrayList
        ArrayList<String> vota = new ArrayList<String>();
        Scanner file = new Scanner(new File("voidTags.txt"));

        while (file.hasNext()) {
            vota.add(file.next());
        }

        String tag2 = tag.replace("/", "");

        if (vota.contains(tag2)) {
            return true;
        }

        file.close();

        return false;
    }

    /**
     * method: validateTags steps through an html file, determining whether or not a tag is valid.
     * If valid, push on either openTags or closeTags stacks.
     *
     * @return
     * @throws FileNotFoundException
     */
    public boolean validateTags() throws FileNotFoundException {
        String oneLine = "", tag = "";
        int space, closeTag;
        Scanner file = new Scanner(new File(filename));

        boolean tf = true;


        //TODO: Step through file determining whether or not tags are valid or invalid
        // When you encounter and open bracket < throw and InvalidHTMLException if there is no closing bracket >
        // If all is good
        //    push open tags on openTags stack  (only the str[i], NOT additional attributes - e.g., <meta> only)
        //    push closing tags on closeTags stack
        //    do not push comments - <!... is a comment
        // If either an open or close str[i] is NOT found in the validTags ArrayList, return false
        // If all tags are in the validTags ArrayList, return true

        while (file.hasNextLine()) {

            int count = 0, open = 0, close = 0;

            while (count != -1) {
                count = oneLine.indexOf("<", count);
                if (count != -1) {
                    open++;
                    count++;
                }
            }
            count = 0;
            while (count != -1) {
                count = oneLine.indexOf(">", count);
                if (count != -1) {
                    close++;
                    count++;
                }
            }

            //if open and close brackets do not equal, meaning there is an open bracket that doesn't have a closing bracket or vice versea, return false
            if (open != close) {
                tf = false;
            }

            tag = "";
            oneLine = file.nextLine();
            String[] one = oneLine.split(" |>");
            if (oneLine.contains("<") && !oneLine.contains(">")) {
                throw new InvalidHTMLException();
            }
            for (String str : one) {
                if (str.contains("<!")) {
                    break;
                }
                else if (str.contains("<")) {
                    if (!str.contains(">")) {
                        str += ">";
                    }
                    tag = str.substring(str.indexOf("<"));
                }
                if (tag.contains("</")) {
                    tag = tag.replace("/", "");
                    closeTags.push(tag);
                } else if (tag.contains("<"))
                    openTags.push(tag);
                tag = "";
            }
        }
        file.close();

        if (!tf) {
            return false;
        }
        for (String op : openTags) {
            boolean isThere = validTag(op);
            if (!isThere) {
                return false;
            }
        }
        for (String op : closeTags) {
            boolean isThere = validTag(op);
            if (!isThere) {
                return false;
            }
        }

        file.close();
        return true;
    }

    /**
     * method:  validateHTML steps through an html file, determining whether or not html can be validated
     *          All open tags that are not void must have a matching closing tag, embedded like ( )
     *          All open tags that are void may or may not have a matching closing tag
     * @return
     * @throws FileNotFoundException
     */
    public boolean validateHTML() throws FileNotFoundException {
        Scanner file = new Scanner(new File(filename));
        String oneLine, tag = "";
        int closeTag, space;
        // TODO: Step through file to determine whether or not the html can be validated
        // Push open tags on validTags stack (not comment tags)
        // Close tags must match open tags. However, if popped open tag is a void tag you must do more checking. You may have the following scenarios
        //      Popped open tag matches close tag
        //      Popped open tag is also a void tag and it matches close tag, which is also a void tag (obviously)
        //      Popped open tag is also a void tag and does not match close tag - you must pop stack until either a non-void tag is popped or the tag matches the close tag (in this latter scenario the close tag will be a void tag)
        //            Example 1: I may be trying to match </body>, when I pop the stack, I have <meta>, which is a void tag. I pop again, I may have a <br> tag, which is also void
        //                     I then pop a third time and have <body>. They match. If, however, I were to pop <html> instead of <body>, I return false as I cannot validate the HTML
        //            Example 2: I may be trying to match </br>, which is a void tag. When I pop the stack, as I did in Example 1, I have <meta>, which is a void tag.
        //                     I pop again, and I have a <br> tag, which matches so I'm done.
        //
        //      I have invalid HTML if the popped open tag does not match the closed tag and they are both non-void tags.
        //      I also have invalid HTML if my closed tag IS a void tag and does not match the first popped open tag.
        //      And, of course, I have invalid HTML if the stack is empty and I haven't matched all my closing tags.

        while(file.hasNextLine()) {
            oneLine = file.nextLine();
            String[] one = oneLine.split(" |>");
            for(String star : one) {
                if(star.contains("<!")) {
                    break;
                }
                else if(star.contains("<") && !star.contains(">")) {
                        tag = star + ">";
                    tag = tag.substring(tag.indexOf("<"));
                }
                if(tag.contains("</")) {
                    String temp;
                    tag = tag.replace("/","");
                    closeTags.push(tag);
                    tag = closeTags.pop();
                    if(openTags.isEmpty()) {
                        return false;
                    }
                    else {
                        temp = openTags.pop();
                    }
                    while(!tag.equals(temp)) {
                        if(voidTag(tag)) {
                            return false;
                        }
                        if(!voidTag(temp) && !voidTag(tag)) {
                            return false;
                        }
                        temp = openTags.pop();
                    }
                }
                else
                if(star.contains("<")) {
                    openTags.push(tag);
                }
            }
        }

        file.close();
        return validateTags.isEmpty();
    }
}

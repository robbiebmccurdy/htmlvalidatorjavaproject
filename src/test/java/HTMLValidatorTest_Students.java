import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class HTMLValidatorTest_Students {
    @Test
    public void testValidTag2() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page1.txt", "validTags.txt", "voidTags.txt");
        assertFalse(evaluator.validTag("</div"));
    }

    @Test
    public void testVoidTag2() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page1.txt", "validTags.txt", "voidTags.txt");
        assertFalse(evaluator.voidTag("<div>"));
    }

    @Test
    public void testPrintOpenTags2() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page4.txt", "validTags.txt", "voidTags.txt");
        evaluator.validateTags();
        assertEquals("<div>\n<body>\n<meta>\n<head>\n<html>\n", evaluator.printOpenTags());
    }


    @Test
    public void testPrintCloseTags2() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page4.txt", "validTags.txt", "voidTags.txt");
        evaluator.validateTags();
        assertEquals("<html>\n<body>\n<div>\n<head>\n", evaluator.printCloseTags());
    }


    @Test
    public void testValidateTags1() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page1.txt", "validTags.txt", "voidTags.txt");
        evaluator.validateTags();
        assertEquals(5, evaluator.getSizeOpenTags());
    }

    @Test
    public void testValidateTags2() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page1.txt", "validTags.txt", "voidTags.txt");
        evaluator.validateTags();
        assertEquals(4, evaluator.getSizeCloseTags());
    }

    //Has tags with attributes
    @Test
    public void testValidateTags9() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page1.txt", "validTags.txt", "voidTags.txt");
        assertTrue(evaluator.validateTags());
    }
    //Tests that html comments are ignored and not pushed on stack
    @Test
    public void testValidateTags10() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page2.txt", "validTags.txt", "voidTags.txt");
        assertTrue(evaluator.validateTags());
    }

    @Test
    public void testValidateTags12() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page4.txt", "validTags.txt", "voidTags.txt");
        assertTrue(evaluator.validateTags());
    }


    @Test
    public void testValidateTags15() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page6.txt", "validTags.txt", "voidTags.txt");
        assertFalse(evaluator.validateTags());
    }


    //Test Exceptions
    @Test
    public void testValidateTags19() throws FileNotFoundException {
        assertThrows(InvalidHTMLException.class, () -> {
            HTMLValidator evaluator = new HTMLValidator("page10.txt", "validTags.txt", "voidTags.txt");
            evaluator.validateTags();
        });
    }
    @Test
    public void testValidateHTML1() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page1.txt", "validTags.txt", "voidTags.txt");
        assertTrue(evaluator.validateHTML());
    }
    @Test
    public void testValidateHTML2() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page2.txt", "validTags.txt", "voidTags.txt");
        assertTrue(evaluator.validateHTML());
    }
    //Test for a void tag having a close tag even though HTML does not require it
    @Test
    public void testValidateHTML3() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page11.txt", "validTags.txt", "voidTags.txt");
        assertTrue(evaluator.validateHTML());
    }

    //Empty stack of open tags but encounter close tag </ul>
    @Test
    public void testValidateHTML7() throws FileNotFoundException {
        HTMLValidator evaluator = new HTMLValidator("page15.txt", "validTags.txt", "voidTags.txt");
        assertFalse(evaluator.validateHTML());
    }
}
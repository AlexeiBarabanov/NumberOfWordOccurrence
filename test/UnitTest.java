import com.company.DocumentParser;
import com.company.DocumentReader;
import com.company.DocumentThread;
import com.company.WordFrequency;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    @BeforeAll
    static void init() {

    }

    @Test
    public void documentReaderTest() throws FileNotFoundException, DocumentParser.UnacceptableSymbolFound {

        String text = new DocumentReader().read("test/test.txt");
        DocumentParser dp = new DocumentParser(text);
        dp.start();
        assertEquals(13, DocumentParser.totalNumberOfWords());
    }

    @Test
    public void documentParserTestReflection() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, FileNotFoundException, DocumentParser.UnacceptableSymbolFound {

        String text = new DocumentReader().read("test/test.txt");
        DocumentParser dp = new DocumentParser(text);
        dp.start();
        Field field = Class.forName("com.company.DocumentParser").getDeclaredField("wordFrequencies");
        field.setAccessible(true);
        int counter = 0;
        for (Map.Entry<String, WordFrequency> entry : ((Map<String, WordFrequency>) field.get(null)).entrySet()) {
            counter += entry.getValue().getFrequency();
        }
        assertEquals(13, DocumentParser.totalNumberOfWords());
    }

    @Test
    public void documentParserTest() {
        String str1 = "первое второе первое третье Третье, слова";
        DocumentParser parser1 = new DocumentParser(str1, "");
        try {
            parser1.start();
            WordFrequency wf = parser1.getWord("первое");
            assertTrue(wf.getWord().equals("первое"));
            assertEquals(wf.getFrequency(), 2);

            wf = parser1.getWord("второе");
            assertNotNull(wf);
            assertTrue(wf.getWord().equals("второе"));
            assertEquals(wf.getFrequency(), 1);

            wf = parser1.getWord("третье");
            assertNotNull(wf);
            assertTrue(wf.getWord().equals("третье"));
            assertEquals(wf.getFrequency(), 1);

            wf = parser1.getWord("Третье");
            assertNotNull(wf);
            assertTrue(wf.getWord().equals("Третье"));
            assertEquals(wf.getFrequency(), 1);
        } catch (DocumentParser.UnacceptableSymbolFound unacceptableSymbolFound) {
            unacceptableSymbolFound.printStackTrace();
        }
    }

    @Test
    public void documentParserFailTest() {
        String str2 = "тестовая строка с запрещенными символами f из латинского алфавита";
        assertThrows(com.company.DocumentParser.UnacceptableSymbolFound.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new DocumentParser(str2).start();
            }
        });
    }

    @AfterEach
    public void afterEach() {
        DocumentParser.resetFlag();
        DocumentParser.flushCollection();
    }
}

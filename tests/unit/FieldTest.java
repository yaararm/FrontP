package unit;

import domain.Enums.FieldType;
import domain.Impl.Field;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

public class FieldTest {

    private static Field field;

    @BeforeClass
    public static void before_class(){
        field = new Field(5000,"Tel-Aviv","bigband", FieldType.Tournament);
    }

    @Test
    public void test_editAsset() throws Exception {
        HashMap<String,String> fieldInfo = new HashMap<>();
        fieldInfo.put("seats","5500");
        fieldInfo.put("name","inbarsField");
        field.editAsset(fieldInfo);
        boolean afterEdit = field.getSeats()==5500 && field.getName().equals("inbarsField");
        assertTrue(afterEdit);
    }
}

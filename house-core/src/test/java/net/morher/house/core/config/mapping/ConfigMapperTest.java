package net.morher.house.core.config.mapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import net.morher.house.core.config.EditableConfigurationElement;
import net.morher.house.core.config.SimpleConfigurationElement;

public class ConfigMapperTest {

    @Test
    public void mapBooleanValue() {
        ConfigMapper<Boolean> mapper = ConfigMapper.of(Boolean.class);

        Assertions.assertTrue(mapper instanceof BooleanMapper);

        EditableConfigurationElement config = SimpleConfigurationElement.create().set("true");

        Assertions.assertEquals(mapper.readConfig(config), true);
    }

    @Test
    public void mapIntegerValue() {
        ConfigMapper<Integer> mapper = ConfigMapper.of(Integer.class);

        Assertions.assertTrue(mapper instanceof IntegerMapper);

        EditableConfigurationElement config = SimpleConfigurationElement.create().set("1");

        Assertions.assertEquals(mapper.readConfig(config), Integer.valueOf(1));
    }

    @Test
    public void mapStringValue() {
        ConfigMapper<String> mapper = ConfigMapper.of(String.class);

        Assertions.assertTrue(mapper instanceof StringMapper);

        EditableConfigurationElement config = SimpleConfigurationElement.create().set("String value");

        Assertions.assertEquals(mapper.readConfig(config), "String value");
    }

    @Test
    public void mapObjectValueEmpty() {
        ConfigMapper<TestConfig> mapper = ConfigMapper.of(TestConfig.class);

        Assertions.assertTrue(mapper instanceof ObjectMapper);

        EditableConfigurationElement config = SimpleConfigurationElement
                .create();

        TestConfig obj = mapper.readConfig(config);
        Assertions.assertNotNull(obj);
        Assertions.assertEquals(null, obj.getBooleanValue());
        Assertions.assertEquals(false, obj.getBoolValue());
        Assertions.assertNull(obj.getIntegerValue());
        Assertions.assertEquals(0, obj.getIntValue());
        Assertions.assertNotNull(obj.getSubObject());
        Assertions.assertNull(obj.getSubObject().getValue());
        Assertions.assertNotNull(obj.getOptionalStringValue());
        Assertions.assertFalse(obj.getOptionalStringValue().isPresent());
        Assertions.assertNotNull(obj.getOptionalSubObject());
        Assertions.assertNotNull(obj.getOptionalSubObject().get());
        Assertions.assertNotNull(obj.getCollection());
        Assertions.assertTrue(obj.getCollection().isEmpty());
        Assertions.assertNotNull(obj.getList());
        Assertions.assertTrue(obj.getList().isEmpty());
    }

    @Test
    public void mapObjectValueFilled() {
        ConfigMapper<TestConfig> mapper = ConfigMapper.of(TestConfig.class);

        Assertions.assertTrue(mapper instanceof ObjectMapper);

        EditableConfigurationElement config = SimpleConfigurationElement
                .create()
                .set("booleanValue", "true")
                .set("boolValue", "true")
                .set("integerValue", "1")
                .set("intValue", "2")
                .set("stringValue", "String value")
                .set("subObject.value", "Sub object value")
                .set("optionalStringValue", "Optional String value")
                .set("optionalSubObject.value", "Optional sub object value");

        config.with("collection").add().set("value", "Collection value 1");
        config.with("collection").add().set("value", "Collection value 2");
        config.with("collection").add();

        config.with("list").add().set("value", "List value 1");
        config.with("list").add().set("value", "List value 2");
        config.with("list").add();

        TestConfig obj = mapper.readConfig(config);
        Assertions.assertNotNull(obj);
        Assertions.assertEquals(Boolean.TRUE, obj.getBooleanValue());
        Assertions.assertEquals(true, obj.getBoolValue());
        Assertions.assertEquals((Integer) 1, obj.getIntegerValue());
        Assertions.assertEquals(2, obj.getIntValue());
        Assertions.assertNotNull(obj.getSubObject());
        Assertions.assertEquals("Sub object value", obj.getSubObject().getValue());
        Assertions.assertNotNull(obj.getOptionalStringValue());
        Assertions.assertEquals("Optional String value", obj.getOptionalStringValue().get());
        Assertions.assertNotNull(obj.getOptionalSubObject());
        Assertions.assertEquals("Optional sub object value", obj.getOptionalSubObject().get().getValue());

        Assertions.assertNotNull(obj.getCollection());
        Assertions.assertEquals(3, obj.getCollection().size());
        Iterator<SubObject> collectionIterator = obj.getCollection().iterator();
        Assertions.assertEquals("Collection value 1", collectionIterator.next().getValue());
        Assertions.assertEquals("Collection value 2", collectionIterator.next().getValue());
        Assertions.assertNull(collectionIterator.next().getValue());

        Assertions.assertNotNull(obj.getList());
        Assertions.assertEquals(3, obj.getList().size());
        Iterator<SubObject> listIterator = obj.getList().iterator();
        Assertions.assertEquals("List value 1", listIterator.next().getValue());
        Assertions.assertEquals("List value 2", listIterator.next().getValue());
        Assertions.assertNull(listIterator.next().getValue());
    }

    public static class TestConfig {
        private Boolean booleanValue;
        private boolean boolValue;
        private Integer integerValue;
        private int intValue;
        private String stringValue;
        private SubObject subObject;
        private Optional<String> optionalStringValue;
        private Optional<SubObject> optionalSubObject;
        private Collection<SubObject> collection;
        private List<SubObject> list;

        public Boolean getBooleanValue() {
            return booleanValue;
        }

        public boolean getBoolValue() {
            return boolValue;
        }

        public Integer getIntegerValue() {
            return integerValue;
        }

        public int getIntValue() {
            return intValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public SubObject getSubObject() {
            return subObject;
        }

        public Optional<String> getOptionalStringValue() {
            return optionalStringValue;
        }

        public Optional<SubObject> getOptionalSubObject() {
            return optionalSubObject;
        }

        public Collection<SubObject> getCollection() {
            return collection;
        }

        public List<SubObject> getList() {
            return list;
        }
    }

    public static class SubObject {
        private String value;

        public String getValue() {
            return value;
        }
    }
}

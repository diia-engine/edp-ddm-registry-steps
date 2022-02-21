package platform.qa.base;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Convertor {

    public static XmlMapper getXmlObjectMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return xmlMapper;
    }

    /**
     * Returns converted XML file to POJO.
     * XML file - not using whole XML file, taking just a part of it starting from {@param attributeName}
     * @param file - file to convert
     * @param attributeName - attribute name to start converting XML file from
     * @param clazzValue - converted POJO type
     * @return POJO representation of XML file
     */
    @SneakyThrows(IOException.class)
    public static <T> T convertPartOfXmlFileToObject(File file, String attributeName, Class<T> clazzValue) {
        JsonNode jsonNode = getXmlObjectMapper().readValue(file, JsonNode.class).get(attributeName);
        return getXmlObjectMapper().convertValue(jsonNode, clazzValue);
    }
}

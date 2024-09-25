package org.acme.account.json;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class StringListMapDeserializer extends JsonDeserializer<Object>
{

  @Override
  public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    JsonNode jsonNode = jsonParser.readValueAsTree();
    Iterator<Map.Entry<String, JsonNode>> itr = jsonNode.fields();
    Map<String, List<String>> map = new HashMap<>();
    while (itr.hasNext()) {
      Map.Entry<String, JsonNode> e = itr.next();
      List<String> values = new LinkedList<>();
      if (!e.getValue().isArray()) {
        values.add((e.getValue().isNull()) ? null : e.getValue().asText());
      } else {
        ArrayNode a = (ArrayNode) e.getValue();
        Iterator<JsonNode> vitr = a.elements();
        while (vitr.hasNext()) {
          JsonNode node = vitr.next();
          values.add((node.isNull() ? null : node.asText()));
        }
      }
      map.put(e.getKey(), values);
    }
    return map;
  }
}

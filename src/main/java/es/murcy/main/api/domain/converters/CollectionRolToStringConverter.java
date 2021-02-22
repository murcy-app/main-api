package es.murcy.main.api.domain.converters;

import es.murcy.main.api.domain.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static es.murcy.main.api.util.StringUtils.EMPTY;

@Converter
public class CollectionRolToStringConverter implements AttributeConverter<Collection<User.Rol>, String> {

  @Override
  public String convertToDatabaseColumn(Collection<User.Rol> inputCollection) {
    if(inputCollection == null) {
      return EMPTY;
    }
    return inputCollection.stream()
            .map(User.Rol::toString)
            .collect(Collectors.joining(","));
  }

  @Override
  public Collection<User.Rol> convertToEntityAttribute(String inputString) {
    if(inputString == null) {
      return Collections.emptySet();
    }
    return Arrays.stream(inputString.split(","))
            .map(User.Rol::valueOf)
            .collect(Collectors.toSet());
  }

}

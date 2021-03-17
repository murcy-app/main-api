package es.murcy.main.api.service.validators;

public interface ValidateUpdate<T> {

  /**
   * Validates if incoming entity is update Valid
   * @param entity validate entity
   * */
  void validateUpdate(T entity);
}

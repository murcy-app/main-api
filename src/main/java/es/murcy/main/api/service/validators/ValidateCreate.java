package es.murcy.main.api.service.validators;

public interface ValidateCreate<T> {

  /**
   * Validates if incoming entity is create Valid
   * @param entity
   * */
  void validateCreate(T entity);
}

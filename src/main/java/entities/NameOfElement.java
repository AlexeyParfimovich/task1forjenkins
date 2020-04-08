/**
 * Интерфейс для поиска элементов на старницах PageObject в рантайме
 */
package entities;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // указывает, в какой момент жизни программного кода будет доступна аннотация
@Target(ElementType.FIELD) // указывает, какой элемент программы будет использоваться аннотацией - FIELD - поля-свойства класса
public @interface NameOfElement
{
    String value() default "";
}
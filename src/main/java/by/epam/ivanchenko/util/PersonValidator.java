package by.epam.ivanchenko.util;

import by.epam.ivanchenko.model.Person;
import by.epam.ivanchenko.service.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personService.getByPersonName(person.getPersonName()).isPresent()) {
                errors.rejectValue("personName", "", "Такой пользователь существует!");
            }
        }
    }

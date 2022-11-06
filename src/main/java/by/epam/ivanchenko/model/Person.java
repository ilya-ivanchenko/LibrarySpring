package by.epam.ivanchenko.model;

import javax.validation.constraints.*;

public class Person {
    private int personId;

    @Pattern(regexp = "[А-ЯA-Z][а-яa-z]+ [А-ЯA-Z][а-яa-z]+ [А-ЯA-Z][а-яa-z]+", message = "Пожалуйста, введите ФИО в формате: Фамилия Имя Отчество")
    private String personName;

 //  @Pattern(regexp = "\\d{4}", message = "Введите год в формате XXXX")
    @Min(value = 1900, message = "Год рождения не может быть меньше 1900")
    private int birthYear;

    public Person( String personName, int birthYear) {
        this.personName = personName;
        this.birthYear = birthYear;
    }
    public Person() {
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}

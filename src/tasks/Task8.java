package tasks;

import common.Person;
import common.Task;

import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

    private long count;

    //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
    // удалять из списка, который мы не копируем плохая практика за некоторое количество операций можем удалить весь список
    // лучше использовать skip
    public List<String> getNames(List<Person> persons) {
        if (persons.size() == 0) {
            return Collections.emptyList();
        }
//        persons.remove(0); данный кодом пользоваться опасно!!! он одно максимум одноразовый и то это не так
        return persons.stream().skip(1).map(Person::getFirstName).collect(Collectors.toList());
    }

    //ну и различные имена тоже хочется
    //в множество будут входить только уникальные элементы
    public Set<String> getDifferentNames(List<Person> persons) {
        return new HashSet<>(getNames(persons));
    }

    //Для фронтов выдадим полное имя, а то сами не могут
    // можно более компактно
    public String convertPersonToString(Person person) {
        return Optional.of(person.getFirstName()).orElse("")
                + person.getFirstName() != null ? " " : ""
                + Optional.of(person.getSecondName()).orElse("");
    }

    // словарь id персоны -> ее имя
    // в документации написано, что возвращает имя, на самом деле возвращает ещё и фамилию, это мы конечно поравим
    // соберем из данной коллекции сразу нужный словарь, код станет более читабельным
    public Map<Integer, String> getPersonNames(Collection<Person> persons) {
        return persons.stream()
                .collect(Collectors.toMap(Person::getId, Person::getFirstName));
    }

    // есть ли совпадающие в двух коллекциях персоны?
    // код делает лишние действия, лучше было бы выполнять return true, сразу как мы нашли такого человека
    // по быстродействию, после исправления выше вряд ли можно сильно ускорить, но можно значительно повысить читаймость
    public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
        return persons1.stream()
                .filter(person -> persons2.contains(person))
                .findAny()
                .isPresent();
    }

    //метод находит количество четных
    // улучшим читаймость кода
    // (num+1)%2 для четных будет равняться 1, для нечетных 0, если найдём сумму, то найдём количество четных
    public long countEven(Stream<Integer> numbers) {
        return numbers.map(num -> (num + 1) % 2).reduce(0, (x, y) -> x + y);
    }

    @Override
    public boolean check() {
        System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
        boolean codeSmellsGood = true;
        boolean reviewerDrunk = false;
        return codeSmellsGood || reviewerDrunk;
    }
}

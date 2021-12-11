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
        String[] names = {person.getFirstName(), person.getSecondName()};
        return Arrays.stream(names).collect(Collectors.joining(" : "));
    }

    // словарь id персоны -> ее имя
    // в документации написано, что возвращает имя, на самом деле возвращает ещё и фамилию, это мы конечно поравим
    // соберем из данной коллекции сразу нужный словарь, код станет более читабельным
    // Хочу понять, есть смысл в использовании distinct?
    // По сути HashMap будет содержать в себе одну записать для каждого человека,
    // даже в том случае, если одного человека передадут несколько раз
    // а stream.distinct требует затрат по времени
    public Map<Integer, String> getPersonNames(Collection<Person> persons) {
        Set<Integer> seenIds = new HashSet<>();
        // идея заключается в том, что equals может работать намного медленее, чем сравнение по id
        return persons.stream().filter(person ->
                {
                    if (seenIds.contains(person.getId())) return false;
                    seenIds.add(person.getId());
                    return true;

                })
                .collect(Collectors.toMap(Person::getId, Person::getFirstName));
//        return persons.stream().distinct() // так можно было бы использовать distinct
//                .collect(Collectors.toMap(Person::getId, Person::getFirstName));
    }

    // есть ли совпадающие в двух коллекциях персоны?
    // код делает лишние действия, лучше было бы выполнять return true, сразу как мы нашли такого человека
    // мы не в курсе какая коллекция, создание set из уникальных значений - o(n^2)
    // anyMatch - o(n^2)
    // если в коллекциях много повторов, то такой подход может стоить того
    public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
        Set<Integer> persons1Ids = persons1.stream().map(Person::getId).collect(Collectors.toSet());
        Set<Integer> persons2Ids = persons2.stream().map(Person::getId).collect(Collectors.toSet());
        return persons2Ids.stream().anyMatch(persons1Ids::contains);
    }

    //метод находит количество четных
    // улучшим читаймость кода
    public long countEven(Stream<Integer> numbers) {
        return numbers.filter(num -> num % 2 == 0).count();
    }

    @Override
    public boolean check() {
        System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
        boolean codeSmellsGood = false;
        boolean reviewerDrunk = true;
        return codeSmellsGood || reviewerDrunk;
    }
}

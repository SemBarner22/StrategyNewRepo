package com.mygdx.game.Entities.Functional;


public class Event {
    // тут идет множесто всего, что они изменяют
    // глобально это будет выглядеть так. Вылетает ивент. Если он происходит, то в государстве появляется модификатор (изменяется его характеристика).
    // То есть в формуле подстчета модификатора есть величина, указывающая на активность модификатора и на его показательи
    // все тексты будут леать исклчительно в файлах, как и сведенья о евентах и модификаторах. Соответственно при запуске надо будет это читать.
    // Сюда видимо стои положить разные штуки типо текста, надпи, а потом будет выподать ивент пропорционально его частоте.
    // Для ботов стоит держать тут то, с какой частотой бот будет выбирать тот или иной вариант.
    // Номер нужет чисто для того, чтобы отличать ивенты. Смыслово нагрузки он не несут (разве что вызыват ивет легче)
    // в идеале еще включение модификатора обновляло бы все необходимое. Но в целом будет нормально, если модификаторы будут работать
    // Только со следующего хода
    private int choiseNum;
    private int number;
    private int[] modificators;
    private int probability;
    private String text;
    private String[] textsAns;
    private int weight = 1;

    private int choiceBot(){
        return 0;
    }
    public int getProbability(){
        probability = 1;
        return probability;
    }
    public int getModNum(int i){
        return modificators[i];
    }

    public Event(int choiseNum, int number, int[] modificators, String text, String[] textsAns) {
        this.choiseNum = choiseNum;
        this.number = number;
        this.modificators = modificators;
        this.text = text;
        this.textsAns = textsAns;
    }
}

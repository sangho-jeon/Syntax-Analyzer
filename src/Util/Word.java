package Util;

public class Word{ // word that goes into ari stack;
    public WORD_TYPE wordName;
    public String value;

    public Word(WORD_TYPE wordName, String value){
        this.wordName = wordName;
        this.value = value;
    }

    public void out(){
        System.out.println(wordName + " " + value);
    }
}

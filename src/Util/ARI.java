package Util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class ARI{
    public Deque<Word> wordStack;

    public ARI(){
        this.wordStack = new ArrayDeque<>();
    }

    public void pushReturnAddress(String value){
        wordStack.push(new Word(WORD_TYPE.RETURN_ADDRESS, value));
    }

    public void pushDynamicLink(String value){
        wordStack.push(new Word(WORD_TYPE.DYNAMIC_LINK, value));
    }

    public void pushLocalVariable(String value){
        wordStack.push(new Word(WORD_TYPE.LOCAL_VARIABLE, value));
    }

    public void out(){
        for (Word t:
             wordStack) {
            System.out.println(t);
        }
    }
}

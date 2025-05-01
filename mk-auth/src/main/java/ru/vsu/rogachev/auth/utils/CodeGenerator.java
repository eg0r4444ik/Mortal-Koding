package ru.vsu.rogachev.auth.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class CodeGenerator {

    private Random rnd = new Random();
    private static final int CODE_LENGTH = 10;

    public String generateActivationCode(){
        StringBuilder code = new StringBuilder();

        for(int i = 0; i < CODE_LENGTH; i++){
            int var = rnd.nextInt(2);
            if(var == 0){
                char sym = (char)(rnd.nextInt(26) + 'a');
                code.append(sym);
            }else{
                char sym = (char)(rnd.nextInt(26) + 'A');
                code.append(sym);
            }
        }

        return code.toString();
    }

}

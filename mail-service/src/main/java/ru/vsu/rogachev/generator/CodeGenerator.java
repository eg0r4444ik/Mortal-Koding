package ru.vsu.rogachev.generator;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeGenerator {

    private Random rnd = new Random();
    private final int CODE_LENGTH = 10;

    public String generateActivationCode(){
        StringBuilder code = new StringBuilder();

        for(int i = 0; i < CODE_LENGTH; i++){
            char sym = (char)(rnd.nextInt(26) + 'a');
            code.append(sym);
        }

        return code.toString();
    }

}

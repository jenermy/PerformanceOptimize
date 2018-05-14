package com.example.greendaodao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class GreenDaoGenerator {
    public static void main(String[] args) throws Exception{
        Schema schema = new Schema(1,"com.example.greendao");
        addNote(schema);
        new DaoGenerator().generateAll(schema,"../app/src/main/java-gen");
    }
    private static void addNote(Schema schema){
        Entity entity = schema.addEntity("Note");
        entity.addIdProperty();
        entity.addStringProperty("text").notNull();
        entity.addStringProperty("content");
        entity.addDateProperty("date");
    }
}

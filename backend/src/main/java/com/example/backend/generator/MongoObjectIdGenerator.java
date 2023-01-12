package com.example.backend.generator;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class MongoObjectIdGenerator implements IdGenerator {
    @Override
    public String generateRandomId() {
        return new ObjectId().toString();
    }
}

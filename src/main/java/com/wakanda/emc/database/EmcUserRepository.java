package com.wakanda.emc.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;
import com.wakanda.emc.model.EmcUser;

public interface EmcUserRepository extends MongoRepository<EmcUser, ObjectId> {
    EmcUser findByUserHandle(String userHandle);
}

package com.wakanda.emc.database;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.wakanda.emc.model.EmcTask;

public interface EmcTaskRepository extends MongoRepository<EmcTask, ObjectId> {

        EmcTask findByOrgHandleAndEventNameAndName(String orgHandle, String eventName, String name);
        List<EmcTask> findByOrgHandle(String orgHandle, String eventName);

}
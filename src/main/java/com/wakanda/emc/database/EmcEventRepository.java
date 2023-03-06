package com.wakanda.emc.database;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.wakanda.emc.model.EmcEvent;

public interface EmcEventRepository extends MongoRepository<EmcEvent, ObjectId> {

        EmcEvent findByOrgHandleAndName(String orgHandle, String name);
        List<EmcEvent> findByOrgHandle(String orgHandle);
        List<EmcEvent> findByOrgHandleAndApprovedVolunteers(String orgHandle, String approvedVolunteer);
        List<EmcEvent> findByOrgHandleAndCreator(String orgHandle, String creator);

}
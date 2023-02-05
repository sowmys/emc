package com.wakanda.emc.database;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.wakanda.emc.model.EmcOrganization;

public interface EmcOrganizationRepository extends MongoRepository<EmcOrganization, ObjectId> {

        List<EmcOrganization> findByName(String name);
}

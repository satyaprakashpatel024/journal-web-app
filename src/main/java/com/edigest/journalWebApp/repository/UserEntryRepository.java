package com.edigest.journalWebApp.repository;

import com.edigest.journalWebApp.Entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<Users, ObjectId> {
    Users findByUserName(String userName);
    Users deleteByUserName(String userName);
}

package com.edigest.journalwebapp.repository;

import com.edigest.journalwebapp.entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<Users, ObjectId> {

    Users findByUserName(String userName);

    Users deleteByUserName(String userName);
}

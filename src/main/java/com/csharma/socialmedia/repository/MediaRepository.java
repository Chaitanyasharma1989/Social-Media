package com.csharma.socialmedia.repository;

import com.csharma.socialmedia.model.MediaPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends MongoRepository<MediaPost, String> {
}

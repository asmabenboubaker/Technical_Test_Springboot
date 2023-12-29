package com.example.beecoders_test.Repository;

import com.example.beecoders_test.Entities.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepo extends CrudRepository<Course, Integer> {
}

package com.example.beecoders_test.Service;


import com.example.beecoders_test.Entities.Course;
import com.example.beecoders_test.Repository.ICourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    @Autowired
    private ICourseRepo iCourseRepo;

    @Override
    public List<Course> retrieveAllCourse() {
        return (List<Course>) iCourseRepo.findAll();
    }

    @Override
    public Course updateCourse(Course ce) {
        return iCourseRepo.save(ce);
    }

    @Override
    public Course addCourse(Course ce) {
       // ce.setImage(imageFile.getBytes());
        return iCourseRepo.save(ce);
    }

    @Override
    public Course retrieveCourse(Integer idCourse) {
        return iCourseRepo.findById(idCourse).get();
    }

    @Override
    public void removeCourse(Integer idCourse) {
     iCourseRepo.deleteById(idCourse);
    }
}
